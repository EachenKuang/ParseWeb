package cn.edu.xmu.software.binarykang.common.rowtype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.Format;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class MultiType
{
	/**
	 * ��ָ����Excel�У������ṩ�ı�����Ĭ�ϴӱ�����Ӧ���м���2����һ�п�ʼ����ȡ�����͵�����
	 * 
	 * @param xlsx
	 *            Excel����Դ
	 * @param data
	 *            ��ȡ�����ݵĴ������
	 * @param key
	 *            ��Ҫ��ȡ���ݵı���
	 */
	public static void read(Xlsx xlsx, List<List<BaseRow>> data, String key)
	{
		read(xlsx, data, xlsx.getRowByKey(key) + 2);
	}

	/**
	 * ��ָ����Excel�У���ָ���п�ʼ����ȡ�����͵�����
	 * 
	 * @param xlsx
	 *            Excel����Դ
	 * @param data
	 *            ��ȡ�����ݵĴ������
	 * @param beginRow
	 *            ��ʼ��ȡ���ݵ���
	 */
	public static void read(Xlsx xlsx, List<List<BaseRow>> data, int beginRow)
	{
		int dataIndex = 0;
		int rowIndex = beginRow;
		int beginCol = AdultBaseAction.BEGIN_COL;
		XSSFRow row = null;

		for (int i = beginRow; (row = xlsx.getRow(i)).getLastCellNum() != -1; ++i)
		{
			if (i != rowIndex && xlsx.getContent(row, beginCol) != "")
			{
				dataIndex++;
				rowIndex = i;
			}
			try
			{
				BaseRow baseRow = new BaseRow();
				baseRow.key = xlsx.getContent(row, beginCol + 1);
				baseRow.value = new Double(xlsx.getContent(row,
						AdultBaseAction.BEGIN_COL + 2));
				data.get(dataIndex).add(baseRow);
			} catch (NumberFormatException e)
			{
				// e.printStackTrace();
				// Log.addException(e);
				System.err.println("����û�н��������������У�" + (i + 1));
				break;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ݸ����Ķ��������ݣ���ָ����docx�ĵ��У�ʹ��Ĭ�ϵİٷֱȸ�ʽ�������滻ָ����ʽ������ֵ����̬�����ɱ��
	 * 
	 * @param docx
	 *            ������docx�ĵ�
	 * @param data
	 *            ����Դ
	 * @param firstRowKeyFormat
	 *            key������Ӧ�ĸ�ʽ
	 * @param firstRowValueFormat
	 *            value������Ӧ�ĸ�ʽ
	 * @param rowXML
	 *            �������Դ�ļ��ĵ�ַ
	 */
	public static void table(Docx docx, List<List<BaseRow>> data,
			String firstRowKeyFormat, String firstRowValueFormat, String rowXML)
	{
		table(docx, data, firstRowKeyFormat, firstRowValueFormat, rowXML, GF.p);
	}

	/**
	 * ���ݸ����Ķ��������ݣ���ָ����docx�ĵ��У��ø����ĸ�ʽ�������滻ָ����ʽ������ֵ����̬�����ɱ��
	 * 
	 * @param docx
	 *            ������docx�ĵ�
	 * @param data
	 *            ����Դ
	 * @param firstRowKeyFormat
	 *            key������Ӧ�ĸ�ʽ
	 * @param firstRowValueFormat
	 *            value������Ӧ�ĸ�ʽ
	 * @param rowXML
	 *            �������Դ�ļ��ĵ�ַ
	 * @param format
	 *            ��ʽ����
	 */
	public static void table(Docx docx, List<List<BaseRow>> data,
			String firstRowKeyFormat, String firstRowValueFormat,
			String rowXML, Format format)
	{
		Node tableNode = docx.getTableNode();
		NodeList tableChildNodes = tableNode.getChildNodes();
		List<Node> lastNodes = new ArrayList<Node>();
		for (int i = 0; i < data.size(); ++i)
		{
			docx.replace(String.format(firstRowKeyFormat, i), data.get(i)
					.get(0).key);
			docx.replace(String.format(firstRowValueFormat, i),
					format.format(data.get(i).get(0).value));

			lastNodes.add(docx.getChildByIndex(tableChildNodes, "w:tr",
					2 + 2 * i));
		}

		for (int i = 0; i < data.size(); ++i)
		{
			for (int j = 1; j < data.get(i).size(); ++j)
			{
				BaseRow baseRow = data.get(i).get(j);
				XMLFragment xmlFragment = new XMLFragment(rowXML);
				xmlFragment.replace("${key}", baseRow.key);

				xmlFragment.replace("${value}", format.format(baseRow.value));

				docx.insertBefore(tableNode, xmlFragment.getRootNode(),
						lastNodes.get(i));
			}
		}
		for (int i = 0; i < data.size(); ++i)
		{
			tableNode.removeChild(lastNodes.get(i));
		}
	}

	public static void print(List<List<BaseRow>> data)
	{
		for (int i = 0; i < data.size(); i++)
		{
			System.out.println("Type " + (i + 1));
			Iterator<BaseRow> iterator = data.get(i).iterator();
			while (iterator.hasNext())
			{
				BaseRow baseRow = iterator.next();
				System.out.println("BaseRow [Key = " + baseRow.key
						+ "\tValue = " + baseRow.value + "]");
			}
		}
	}
}
