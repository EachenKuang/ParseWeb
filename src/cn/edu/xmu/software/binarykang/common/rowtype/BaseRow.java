package cn.edu.xmu.software.binarykang.common.rowtype;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.Format;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.log.LogError;
import cn.edu.xmu.software.binarykang.word.Chart;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.OpenXML;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.ChartXlsx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * �����ļ�ֵ�Ե����ݽṹ���������ֻ����һ������һ��ֵ�ı��������
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * 
 */
public final class BaseRow extends Row
{
	public double value;

	/**
	 * ���ݱ������ȡ����
	 * 
	 * @param xlsx
	 *            ������Դ
	 * @param data
	 *            ��Ŷ�ȡ���ݵ��б�
	 * @param rowKey
	 *            ���
	 */
	public static void read(Xlsx xlsx, List<BaseRow> data, String rowKey)
	{
		read(xlsx, data, xlsx.getRowByKey(rowKey) + 1, AdultBaseAction.BEGIN_COL + 1);
	}

	/**
	 * ���ݿ�ʼ�кͿ�ʼ�У��ӱ���ж�ȡ����
	 * 
	 * @param xlsx
	 *            ������Դ
	 * @param data
	 *            ��Ŷ�ȡ���ݵ��б�
	 * @param beginRow
	 *            ��ʼ��ȡ����
	 * @param beginCol
	 *            ��ʼ��ȡ����
	 */
	public static void read(Xlsx xlsx, List<BaseRow> data, int beginRow, int beginCol)
	{
		XSSFRow row = null;

		for (int i = beginRow; (row = xlsx.getRow(i)).getLastCellNum() != -1; ++i)
		{
			BaseRow baseRow = new BaseRow();
			try
			{
				baseRow.key = xlsx.getContent(row, beginCol);
				baseRow.value = new Double(xlsx.getContent(row, beginCol + 1));
				data.add(baseRow);
			}
			catch (NumberFormatException e)
			{
				System.err.println("����û�н��������������У�" + (i + 1));
				if (!e.getMessage().equals("empty String"))
					LogError.addError("�� " + beginRow + " �еı�����ݸ�ʽ����");
				break;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ݱ��������Ķ�ȡ����
	 * 
	 * @param xlsx
	 *            ������Դ
	 * @param data
	 *            ��Ŷ�ȡ���ݵ��б�
	 * @param rowKey
	 *            ���
	 */
	public static void readInterval(Xlsx xlsx, List<BaseRow> data, String rowKey)
	{
		readInterval(xlsx, data, xlsx.getRowByKey(rowKey) + 1, AdultBaseAction.BEGIN_COL);
	}

	/**
	 * ���ݿ�ʼ�кͿ�ʼ�У��ӱ���м���Ķ�ȡ����
	 * 
	 * @param xlsx
	 *            ������Դ
	 * @param data
	 *            ��Ŷ�ȡ���ݵ��б�
	 * @param beginRow
	 *            ��ʼ��ȡ����
	 * @param beginCol
	 *            ��ʼ��ȡ����
	 */
	public static void readInterval(Xlsx xlsx, List<BaseRow> data, int beginRow, int beginCol)
	{
		XSSFRow row = null;

		for (int i = beginRow; (row = xlsx.getRow(i)).getLastCellNum() != -1; i += 2)
		{
			BaseRow tableRow = new BaseRow();
			try
			{
				tableRow.key = xlsx.getContent(row, beginCol);
				tableRow.value = new Double(xlsx.getContent(row, beginCol + 2));
				data.add(tableRow);
			}
			catch (NumberFormatException e)
			{
				System.err.println("�����쳣\t����λ����" + (row.getRowNum() + 1) + "��\t����ԭ��" + e.getMessage());
				break;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ݴ�����б���������һ����״ͼ���߱�״ͼ
	 * 
	 * @param data
	 *            ��Ҫ���б�����
	 */
	public static void chart(List<BaseRow> data)
	{
		OpenXML xml = OpenXML.getChartXml();
		try
		{
			final String C_PT = "c:pt";
			Document root = xml.open();
			int size = data.size();

			Chart chart = new Chart(root);
			Node strCache = chart.getNodeByTagNameAndIndex("c:strCache", 1);
			Node strCacheLastC_PTNode = chart.getChildByIndex(strCache.getChildNodes(), C_PT, 0);
			Node numCache = chart.getNodeByTagNameAndIndex("c:numCache", 0);
			Node numCacheLastC_PTNode = chart.getChildByIndex(numCache.getChildNodes(), C_PT, 0);
			chart.setAttr(chart.getChildByIndex(strCache.getChildNodes(), "c:ptCount", 0), "val", size + "");
			chart.setAttr(chart.getChildByIndex(numCache.getChildNodes(), "c:ptCount", 0), "val", size + "");

			// �޸�chart.xml����ı����������
			chart.changeRef(1, data.size());
			// ����һ��Excel��������������
			ChartXlsx chartXlsx = new ChartXlsx();
			chartXlsx.setData(getMap(data, "��һ��"));
			chartXlsx.save();

			for (int i = 0; i < size; ++i)
			{
				chart.insertBefore(strCache, chart.generateC_PTNode(i + "", data.get(i).key), strCacheLastC_PTNode);
				chart.insertBefore(numCache, chart.generateC_PTNode(i + "", data.get(i).value + ""),
						numCacheLastC_PTNode);
			}
			strCache.removeChild(strCacheLastC_PTNode);
			numCache.removeChild(numCacheLastC_PTNode);
			xml.saveAndClose();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ���ݴ����BaseRow�б����ݣ��Լ�����ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı��
	 * ���ֱ�������к�ż���е���ʽ��һ�µġ�
	 * 
	 * @param docx
	 *            ��ǰ�򿪵�word�ĵ�
	 * @param data
	 *            BaseRow�б�����
	 * @param format
	 *            ��ʽ����
	 * @param rowXML
	 *            ����ж�Ӧ��xml�ļ��ĵ�ַ
	 */
	public static void table(Docx docx, List<BaseRow> data, String rowXML, Format format)
	{
		table(docx, data, rowXML, rowXML, format);
	}

	/**
	 * ���ݴ����BaseRow�б����ݣ��Լ������к�ż���ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı��
	 * ���ֱ�������к�ż���е���ʽ���ṩһ��Ĭ�ϵĸ�ʽ�������ٷֱȸ�ʽ��������Ĭ��ɾ�����һ�С�
	 * 
	 * @param docx
	 *            ��ǰ�򿪵�word�ĵ�
	 * @param data
	 *            BaseRow�б�����
	 * @param oddRowXML
	 *            �����ж�Ӧ��xml�ļ��ĵ�ַ
	 * @param evenRowXML
	 *            ż���ж�Ӧ��xml�ļ��ĵ�ַ
	 */
	public static void table(Docx docx, List<BaseRow> data, String oddRowXML, String evenRowXML)
	{
		table(docx, data, oddRowXML, evenRowXML, GF.p);
	}

	/**
	 * ���ݴ����BaseRow�б����ݣ��Լ������к�ż���ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı��
	 * ���ֱ�������к�ż���е���ʽ��Ĭ��ɾ�����һ�С�
	 * 
	 * @param docx
	 *            ��ǰ�򿪵�word�ĵ�
	 * @param data
	 *            BaseRow�б�����
	 * @param oddRowXML
	 *            �����ж�Ӧ��xml�ļ��ĵ�ַ
	 * @param evenRowXML
	 *            ż���ж�Ӧ��xml�ļ��ĵ�ַ
	 * @param format
	 *            ��ʽ����
	 */
	public static void table(Docx docx, List<BaseRow> data, String oddRowXML, String evenRowXML, Format format)
	{
		table(docx, data, oddRowXML, evenRowXML, format, true);
	}

	/**
	 * ���ݴ����BaseRow�б����ݣ��Լ������к�ż���ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı��
	 * ���ֱ�������к�ż���е���ʽ��
	 * 
	 * @param docx
	 *            ��ǰ�򿪵�word�ĵ�
	 * @param data
	 *            BaseRow�б�����
	 * @param oddRowXML
	 *            �����ж�Ӧ��xml�ļ��ĵ�ַ
	 * @param evenRowXML
	 *            ż���ж�Ӧ��xml�ļ��ĵ�ַ
	 * @param format
	 *            ��ʽ����
	 * @param isDeleteLastRow
	 *            �Ƿ�ɾ�����һ��
	 */
	public static void table(Docx docx, List<BaseRow> data, String oddRowXML, String evenRowXML, Format format,
			boolean isDeleteLastRow)
	{
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size(); ++i)
		{
			BaseRow row = data.get(i);
			XMLFragment xmlFragment = null;
			if (i % 2 == 0)
			{
				xmlFragment = new XMLFragment(oddRowXML);
			}
			else
			{
				xmlFragment = new XMLFragment(evenRowXML);
			}

			xmlFragment.replace("${key}", row.key);
			xmlFragment.replace("${value}", format.format(row.value));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		if (isDeleteLastRow)
			tableNode.removeChild(lastNode);
	}

	/**
	 * �����±�ı�񣬼���3�У��ֱ�Ϊ�±��С����С�ֵ�У����ֱ�����������Ҳ�ǱȽ϶�ġ����ݴ����BaseRow�б����ݣ�
	 * �Լ�����ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı��
	 * ���ֱ�������к�ż���е���ʽ���ṩһ��Ĭ�ϵĸ�ʽ�������ٷֱȸ�ʽ������
	 * 
	 * @param docx
	 *            ��ǰ�򿪵�word�ĵ�
	 * @param data
	 *            BaseRow�б�����
	 * @param rowXML
	 *            ����ж�Ӧ��xml�ļ��ĵ�ַ
	 */
	public static void tableWithIndex(Docx docx, List<BaseRow> data, String rowXML)
	{
		tableWithIndex(docx, data, rowXML, GF.p);
	}

	/**
	 * �����±�ı�񣬼���3�У��ֱ�Ϊ�±��С����С�ֵ�У����ֱ�����������Ҳ�ǱȽ϶�ġ����ݴ����BaseRow�б����ݣ�
	 * �Լ�����ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı�� ���ֱ�������к�ż���е���ʽ��
	 * 
	 * @param docx
	 *            ��ǰ�򿪵�word�ĵ�
	 * @param data
	 *            BaseRow�б�����
	 * @param rowXML
	 *            ����ж�Ӧ��xml�ļ��ĵ�ַ
	 * @param format
	 *            ��ʽ����
	 */
	public static void tableWithIndex(Docx docx, List<BaseRow> data, String rowXML, Format format)
	{
		tableWithIndex(docx, data, rowXML, rowXML, format);
	}

	/**
	 * �����±�ı�񣬼���3�У��ֱ�Ϊ�±��С����С�ֵ�У����ֱ�����������Ҳ�ǱȽ϶�ġ����ݴ����BaseRow�б����ݣ�
	 * �Լ������к�ż���ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı��
	 * ���ֱ�������к�ż���е���ʽ���ṩһ��Ĭ�ϵĸ�ʽ�������ٷֱȸ�ʽ������
	 * 
	 * @param docx
	 *            ��ǰ�򿪵�word�ĵ�
	 * @param data
	 *            BaseRow�б�����
	 * @param oddRowXML
	 *            �����ж�Ӧ��xml�ļ��ĵ�ַ
	 * @param evenRowXML
	 *            ż���ж�Ӧ��xml�ļ��ĵ�ַ
	 */
	public static void tableWithIndex(Docx docx, List<BaseRow> data, String oddRowXML, String evenRowXML)
	{
		tableWithIndex(docx, data, oddRowXML, evenRowXML, GF.p);
	}

	/**
	 * �����±�ı�񣬼���3�У��ֱ�Ϊ�±��С����С�ֵ�У����ֱ�����������Ҳ�ǱȽ϶�ġ����ݴ����BaseRow�б����ݣ�
	 * �Լ������к�ż���ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı�� ���ֱ�������к�ż���е���ʽ��
	 * 
	 * @param docx
	 *            ��ǰ�򿪵�word�ĵ�
	 * @param data
	 *            BaseRow�б�����
	 * @param oddRowXML
	 *            �����ж�Ӧ��xml�ļ��ĵ�ַ
	 * @param evenRowXML
	 *            ż���ж�Ӧ��xml�ļ��ĵ�ַ
	 * @param format
	 *            ��ʽ����
	 */
	public static void tableWithIndex(Docx docx, List<BaseRow> data, String oddRowXML, String evenRowXML, Format format)
	{
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size(); ++i)
		{
			BaseRow row = data.get(i);
			XMLFragment xmlFragment = null;
			if (i % 2 == 0)
			{
				xmlFragment = new XMLFragment(oddRowXML);
			}
			else
			{
				xmlFragment = new XMLFragment(evenRowXML);
			}
			xmlFragment.replace("${index}", "" + (i + 1));
			xmlFragment.replace("${key}", row.key);
			xmlFragment.replace("${value}", format.format(row.value));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

	/**
	 * ͨ��key����һ�е�����
	 * 
	 * @param key
	 *            ��Ҫ���ҵ�key
	 * @param data
	 *            �������
	 * @return key��Ӧ����һ�е�����
	 */
	public static BaseRow getRowByKey(String key, List<BaseRow> data)
	{
		Iterator<BaseRow> iterator = data.iterator();
		while (iterator.hasNext())
		{
			BaseRow baseRow = iterator.next();
			if (baseRow.key.equals(key))
			{
				return baseRow;
			}
		}
		return null;
	}

	/**
	 * ��������Ĭ��Ϊ�Ӵ�С����
	 * 
	 * @param data
	 *            ��Ҫ������б�
	 */
	public static void sort(List<BaseRow> data)
	{
		data.sort(new Comparator<BaseRow>()
		{

			@Override
			public int compare(BaseRow o1, BaseRow o2)
			{
				if (o1.value < o2.value)
					return 1;
				else if (o1.value > o2.value)
					return -1;
				return 0;
			}
		});
	}

	/**
	 * �������һ�в��������������Ķ���������
	 * 
	 * @param data
	 *            ��Ҫ������б�
	 */
	public static void sortExceptLast(List<BaseRow> data)
	{
		BaseRow baseRow = new BaseRow();
		baseRow.key = data.get(data.size() - 1).key;
		baseRow.value = data.get(data.size() - 1).value;
		data.remove(data.size() - 1);
		sort(data);
		data.add(baseRow);
	}

	private static Map<String, List<BaseRow>> getMap(List<BaseRow> data, String colName)
	{
		Map<String, List<BaseRow>> map = new LinkedHashMap<String, List<BaseRow>>();
		map.put(colName, data);
		return map;
	}

	@Override
	public String toString()
	{
		return "BaseRow[key = " + key + "value = " + value + "]";
	}

	public static void print(List<BaseRow> data)
	{
		Iterator<BaseRow> iterator = data.iterator();
		while (iterator.hasNext())
		{
			BaseRow baseRow = iterator.next();
			System.out.println("BaseRow [Key = " + baseRow.key + "\tValue = " + baseRow.value + "]");
		}
	}

}
