package cn.edu.xmu.software.binarykang.common.rowtype;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * Ӧ��ӵ��һ��������ֵ���У�������ڽ����бȽϳ������ʷ�װ�������е��á� ����ڳ���Աȵı����
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * 
 */
public final class DoubleValueRow extends Row
{
	public double v1;
	public double v2;

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
	public static void read(Xlsx xlsx, List<DoubleValueRow> data, String rowKey)
	{
		read(xlsx, data, xlsx.getRowByKey(rowKey) + 3, AdultBaseAction.BEGIN_COL + 1);
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
	public static void read(Xlsx xlsx, List<DoubleValueRow> data, int beginRow, int beginCol)
	{
		XSSFRow row = null;
		for (int i = beginRow; (row = xlsx.getRow(i)).getLastCellNum() != -1; ++i)
		{
			try
			{
				DoubleValueRow uvRow = new DoubleValueRow();
				uvRow.key = xlsx.getContent(row, beginCol);
				uvRow.v1 = new Double(xlsx.getContent(row, beginCol + 1));
				uvRow.v2 = new Double(xlsx.getContent(row, beginCol + 2));
				data.add(uvRow);
			}
			catch (NumberFormatException e)
			{
				System.err.println("����û�н��������������У�" + (i + 1));
				if (!e.getMessage().equals("empty String"))
					LogError.addError("�� " + beginRow + " �еı�����ݸ�ʽ����");
				break;
			}
		}
	}

	/**
	 * ���������������͵��б�����һ����˫ֵ���б������������͵��б��key������ͬ����������ͬ
	 * 
	 * @param desc
	 *            ��Ҫ���ɵ�˫ֵ�б�
	 * @param src1
	 *            ��һ�����������б�
	 * @param src2
	 *            �ڶ������������б�
	 */
	public static void read(List<DoubleValueRow> desc, List<BaseRow> src1, List<BaseRow> src2)
	{
		for (int i = 0; i < src1.size(); i++)
		{
			DoubleValueRow dvRow = new DoubleValueRow();
			dvRow.key = src1.get(i).key;
			dvRow.v1 = src1.get(i).value;
			dvRow.v2 = src2.get(i).value;
			desc.add(dvRow);
		}
	}

	/**
	 * ���ݴ�����б���������һ����״ͼ
	 * 
	 * @param data
	 *            ��Ҫ���б�����
	 */
	public static void chart(List<DoubleValueRow> data)
	{
		chart(data, "ũ��", "����", null);
	}

	public static void chart(List<DoubleValueRow> data, String colName1, String colName2)
	{
		chart(data, colName1, colName2, null);
	}

	/**
	 * ���ݴ�����б���������һ����״ͼ��ͬʱ�����ݴ���Ĳ����ԣ��滻ͼ�е��ַ������ṩĬ�ϵĸ�ʽ�����ͣ�Ϊ�ٷֱȸ�ʽ����
	 * 
	 * @param data
	 *            ��Ҫ���б�����
	 * @param colName1
	 *            ��һ������
	 * @param colName2
	 *            �ڶ�������
	 * @param replaceData
	 *            ��ֵ�ԣ���ʾ��Ҫͼ����Ҫ�滻�ļ���ֵ
	 */
	public static void chart(List<DoubleValueRow> data, String colName1, String colName2,
			Map<String, String> replaceData)
	{
		chart(data, colName1, colName2, replaceData, 1);
	}

	/**
	 * ���ݴ�����б���������һ����״ͼ��ͬʱ�����ݴ���Ĳ����ԣ��滻ͼ�е��ַ���
	 * 
	 * @param data
	 *            ��Ҫ���б�����
	 * @param colName1
	 *            ��һ������
	 * @param colName2
	 *            �ڶ�������
	 * @param replaceData
	 *            ��ֵ�ԣ���ʾ��Ҫͼ����Ҫ�滻�ļ���ֵ
	 * @param formatType
	 *            Excel���ݸ�ʽ������
	 */
	public static void chart(List<DoubleValueRow> data, String colName1, String colName2,
			Map<String, String> replaceData, int formatType)
	{
		OpenXML xml = OpenXML.getChartXml();
		try
		{
			final String C_PT = "c:pt";
			Document root = xml.open();

			Chart chart = new Chart(root);

			Node v1StrCache = chart.getNodeByTagNameAndIndex("c:strCache", 1);
			Node v1StrCacheLastC_PTNode = chart.getChildByIndex(v1StrCache.getChildNodes(), C_PT, 0);
			Node v1NumCache = chart.getNodeByTagNameAndIndex("c:numCache", 0);
			Node v1NumCacheLastC_PTNode = chart.getChildByIndex(v1NumCache.getChildNodes(), C_PT, 0);

			Node v2StrCache = chart.getNodeByTagNameAndIndex("c:strCache", 3);
			Node v2StrCacheLastC_PTNode = chart.getChildByIndex(v2StrCache.getChildNodes(), C_PT, 0);
			Node v2NumCache = chart.getNodeByTagNameAndIndex("c:numCache", 1);
			Node v2NumCacheLastC_PTNode = chart.getChildByIndex(v2NumCache.getChildNodes(), C_PT, 0);

			chart.setAttr(chart.getChildByIndex(v1StrCache.getChildNodes(), "c:ptCount", 0), "val", data.size() + "");
			chart.setAttr(chart.getChildByIndex(v1NumCache.getChildNodes(), "c:ptCount", 0), "val", data.size() + "");
			chart.setAttr(chart.getChildByIndex(v2StrCache.getChildNodes(), "c:ptCount", 0), "val", data.size() + "");
			chart.setAttr(chart.getChildByIndex(v2NumCache.getChildNodes(), "c:ptCount", 0), "val", data.size() + "");

			// �޸�chart.xml����ı����������
			chart.changeRef(2, data.size());
			// ����һ��Excel��������������
			ChartXlsx chartXlsx = new ChartXlsx();
			chartXlsx.setData(getMap(data, colName1, colName2), formatType);
			chartXlsx.save();

			// ���ͼ��������Ҫ�滻���ַ�����������������
			if (replaceData != null)
			{
				Set<String> keys = replaceData.keySet();
				Iterator<String> iterator = keys.iterator();
				while (iterator.hasNext())
				{
					String key = iterator.next();
					chart.replace(key, replaceData.get(key));
				}
			}

			for (int i = 0; i < data.size(); ++i)
			{
				chart.insertBefore(v1StrCache, chart.generateC_PTNode(i + "", data.get(i).key), v1StrCacheLastC_PTNode);
				chart.insertBefore(v1NumCache, chart.generateC_PTNode(i + "", data.get(i).v1 + ""),
						v1NumCacheLastC_PTNode);
				chart.insertBefore(v2StrCache, chart.generateC_PTNode(i + "", data.get(i).key), v2StrCacheLastC_PTNode);
				chart.insertBefore(v2NumCache, chart.generateC_PTNode(i + "", data.get(i).v2 + ""),
						v2NumCacheLastC_PTNode);
			}
			v1StrCache.removeChild(v1StrCacheLastC_PTNode);
			v1NumCache.removeChild(v1NumCacheLastC_PTNode);
			v2StrCache.removeChild(v2StrCacheLastC_PTNode);
			v2NumCache.removeChild(v2NumCacheLastC_PTNode);

			xml.saveAndClose();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ���ݴ����DoubleValueRow�б����ݣ��Լ�����ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı��
	 * ���ֱ�������к�ż���е���ʽ��һ�µģ��ṩһ��Ĭ�ϵĸ�ʽ����(�ٷֱȸ�ʽ����)��Ĭ��ɾ�����һ�С�
	 * 
	 * @param docx
	 *            ��ǰ�򿪵�word�ĵ�
	 * @param data
	 *            BaseRow�б�����
	 * @param rowXML
	 *            ����ж�Ӧ��xml�ļ��ĵ�ַ
	 */
	public static void table(Docx docx, List<DoubleValueRow> data, String rowXML)
	{
		table(docx, data, rowXML, rowXML, GF.p);
	}

	/**
	 * ���ݴ����DoubleValueRow�б����ݣ��Լ������к�ż���ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı��
	 * ���ֱ�������к�ż���е���ʽ���ṩһ��Ĭ�ϵĸ�ʽ����(�ٷֱȸ�ʽ����)��Ĭ��ɾ�����һ�С�
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
	public static void table(Docx docx, List<DoubleValueRow> data, String oddRowXML, String evenRowXML)
	{
		table(docx, data, oddRowXML, evenRowXML, GF.p);
	}

	/**
	 * ���ݴ����DoubleValueRow�б����ݣ��Լ������к�ż���ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı��
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
	public static void table(Docx docx, List<DoubleValueRow> data, String oddRowXML, String evenRowXML, Format format)
	{
		table(docx, data, oddRowXML, evenRowXML, format, true);
	}

	/**
	 * ���ݴ����DoubleValueRow�б����ݣ��Լ������к�ż���ж�Ӧ��xml�ļ���·����ʹ���ض���Double��ʽ������������Ӧ�ı��
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
	public static void table(Docx docx, List<DoubleValueRow> data, String oddRowXML, String evenRowXML, Format format,
			boolean isDeleteLastRow)
	{
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size(); ++i)
		{
			DoubleValueRow row = data.get(i);
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
			xmlFragment.replace("${v1}", format.format(row.v1));
			xmlFragment.replace("${v2}", format.format(row.v2));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		if (isDeleteLastRow)
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
	public static DoubleValueRow getRowByKey(String key, List<DoubleValueRow> data)
	{
		Iterator<DoubleValueRow> iterator = data.iterator();
		while (iterator.hasNext())
		{
			DoubleValueRow doubleValueRow = iterator.next();
			if (doubleValueRow.key.equals(key))
			{
				return doubleValueRow;
			}
		}
		return null;
	}

	/**
	 * ��DoubleValueRow��v1��v2��ֵ�Ի�һ��
	 * 
	 * @param data
	 *            ��Ҫ�Ի�ֵ����
	 */
	public static void reverseValue(List<DoubleValueRow> data)
	{
		Iterator<DoubleValueRow> iterator = data.iterator();
		while (iterator.hasNext())
		{
			DoubleValueRow row = iterator.next();
			double temp = row.v1;
			row.v1 = row.v2;
			row.v2 = temp;
		}
	}

	/**
	 * ����v1��ֵ���������б���н�������
	 * 
	 * @param data
	 *            �б�����
	 */
	public static void sortByV1(List<DoubleValueRow> data)
	{
		data.sort(new Comparator<DoubleValueRow>()
		{

			@Override
			public int compare(DoubleValueRow o1, DoubleValueRow o2)
			{
				if (o1.v1 < o2.v1)
					return 1;
				else if (o1.v1 > o2.v1)
					return -1;
				return 0;
			}
		});
	}
	public static void sortExceptLastByV1(List<DoubleValueRow> data)//new 
	{
		DoubleValueRow doubleValueRow = data.get(data.size()-1);
		data.remove(data.size()-1);
		data.sort(new Comparator<DoubleValueRow>()
		{

			@Override
			public int compare(DoubleValueRow o1, DoubleValueRow o2)
			{
				if (o1.v1 < o2.v1)
					return 1;
				else if (o1.v1 > o2.v1)
					return -1;
				return 0;
			}
		});
		data.add(doubleValueRow);
	}
	/**
	 * ����v2��ֵ���������б���н�������
	 * 
	 * @param data
	 *            �б�����
	 */
	public static void sortByV2(List<DoubleValueRow> data)
	{
		data.sort(new Comparator<DoubleValueRow>()
		{

			@Override
			public int compare(DoubleValueRow o1, DoubleValueRow o2)
			{
				if (o1.v2 < o2.v2)
					return 1;
				else if (o1.v2 > o2.v2)
					return -1;
				return 0;
			}
		});
	}

	/**
	 * ����v1-v2�Ĳ�ֵ���н�������
	 * 
	 * @param data
	 *            �б�����
	 */
	public static void sortByV1MinusV2(List<DoubleValueRow> data)
	{
		data.sort(new Comparator<DoubleValueRow>()
		{

			@Override
			public int compare(DoubleValueRow o1, DoubleValueRow o2)
			{
				if (o1.v1 - o1.v2 < o2.v1 - o2.v2)
					return 1;
				else if (o1.v1 - o1.v2 > o2.v1 - o2.v2)
					return -1;
				return 0;
			}
		});
	}

	/**
	 * ����v2-v1�Ĳ�ֵ���н�������
	 * 
	 * @param data
	 *            �б�����
	 */
	public static void sortByV2MinusV1(List<DoubleValueRow> data)
	{
		data.sort(new Comparator<DoubleValueRow>()
		{

			@Override
			public int compare(DoubleValueRow o1, DoubleValueRow o2)
			{
				if (o1.v2 - o1.v1 < o2.v2 - o2.v1)
					return 1;
				else if (o1.v2 - o1.v1 > o2.v2 - o2.v1)
					return -1;
				return 0;
			}
		});
	}

	/**
	 * ͨ�����������������ƺ�˫ֵ�б�����һ��map
	 * 
	 * @param data
	 *            ˫ֵ�б�
	 * @param colName1
	 *            ��һ������
	 * @param colName2
	 *            �ڶ�������
	 * @return ���ɵ�map
	 */
	private static Map<String, List<BaseRow>> getMap(List<DoubleValueRow> data, String colName1, String colName2)
	{
		Map<String, List<BaseRow>> map = new LinkedHashMap<String, List<BaseRow>>();
		List<BaseRow> baseRows1 = ListFactory.getBaseRows();
		List<BaseRow> baseRows2 = ListFactory.getBaseRows();

		for (int i = 0; i < data.size(); i++)
		{
			DoubleValueRow doubleValueRow = data.get(i);
			BaseRow baseRow1 = new BaseRow();
			baseRow1.key = doubleValueRow.key;
			baseRow1.value = doubleValueRow.v1;
			baseRows1.add(baseRow1);

			BaseRow baseRow2 = new BaseRow();
			baseRow2.key = doubleValueRow.key;
			baseRow2.value = doubleValueRow.v2;
			baseRows2.add(baseRow2);
		}

		map.put(colName1, baseRows1);
		map.put(colName2, baseRows2);

		return map;
	}

	@Override
	public String toString()
	{
		return "v1 = " + v1 + " v2 = " + v2;
	}

}
