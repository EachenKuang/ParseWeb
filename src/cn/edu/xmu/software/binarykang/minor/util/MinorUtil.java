package cn.edu.xmu.software.binarykang.minor.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.common.formatter.Format;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.minor.parse.Constant;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ���ಿ�ֺ�������ȡ����ӦTool�����棬����������Ϊ�˼�����ǫ����
 * 
 * @author xmujx
 * @since 1.0
 */
public final class MinorUtil
{
	public static String local;
	public static String other;

	/**
	 * ================================================ �Ա�Ķ�̬����
	 * 
	 * @param rowNum
	 *            ����
	 * @param cellNum
	 *            ����
	 * @param key
	 *            �ؼ��ּ���
	 * @param value
	 *            ֵ����
	 * @param docx
	 *            docx����
	 * @param tarClass
	 *            ��ǰ���󣬻�ȡ��Ӧ��xml�����ļ�·��
	 */
	public static void changeTable(int rowNum, int cellNum, String[] key,
			String[][] value, Docx docx, Class<?> tarClass, int... args)
	{
		TableTool
				.changeTable(rowNum, cellNum, key, value, docx, tarClass, args);
	}
	public static void changeTable(Docx docx,List<DataMap> data, String oddRowXML, String evenRowXML,Format format)
	{		
//		Node tableNode = docx.getTableNode();
//		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		Node tableNode = docx.getNodeByTagNameAndIndex("w:tbl",
				Constant.getTabelNum());
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size(); ++i)
		{
//			BaseRow row = data.get(i);
			XMLFragment xmlFragment = null;
			if (i % 2 == 0)
			{
				xmlFragment = new XMLFragment(oddRowXML);
			}
			else
			{
				xmlFragment = new XMLFragment(evenRowXML);
			}

			xmlFragment.replace("${key}", data.get(i).getKey());
			xmlFragment.replace("${value}", format.format(data.get(i).getRate()));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		
		
	}

	public static void table(Docx docx,List<DataMap> data, String oddRowXML, String evenRowXML,Format format)
	{
		Node tableNode = docx.getNodeByTagNameAndIndex("w:tbl",
				Constant.getTabelNum());
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size(); ++i)
		{
			DataMap row = data.get(i);
			XMLFragment xmlFragment = null;
			if (i % 2 == 0)
			{
				xmlFragment = new XMLFragment(oddRowXML);
			}
			else
			{
				xmlFragment = new XMLFragment(evenRowXML);
			}

			xmlFragment.replace("${key}", row.getKey());
			xmlFragment.replace("${value}", format.format(row.getRate()));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}
	/**
	 * =============================================
	 *
	 * ������
	 * 
	 * @param xlsx
	 * @param beginKey
	 *            ��ʼ�ı���
	 * @param data
	 *            ���ݴ�Ŷ����������ʼ��
	 * @param beginCol
	 *            ��ʼ��
	 * @param args
	 *            ��Ϊ���򷵻�other��ֵ
	 * @return
	 */
	public static String readData(Xlsx xlsx, String beginKey,
			List<DataMap> data, int beginCol, String... args)
	{
		int beginRow = xlsx.getRowByKey(beginKey) + 1;
		return readData(xlsx, beginRow, data, beginCol, args);
	}

	public static String readData(Xlsx xlsx, int beginRow, List<DataMap> data,
			int beginCol, String... args)
	{
		return DataTool.readData(xlsx, beginRow, data, beginCol, args);
	}

	/**
	 * ========================================================
	 * 
	 * @param tableInfo
	 */
	public static void changeChart(int need,List<?>... tableInfo)
	{
		ChartTool.changeChart(need,false,tableInfo);
	}
	
	public static void changeChart(List<?>... tableInfo)
	{
		changeChart(false, tableInfo);
	}

	public static void changeChart(boolean genderType, List<?>... tableInfo)
	{
		ChartTool.changeChart(genderType, tableInfo);
	}

	/**
	 * ========================================= ArrayList����
	 * 
	 * @return
	 */
	public static List<DataMap> listMapFactory()
	{
		return new ArrayList<DataMap>();
	}

	/**
	 * ======================================= ���Լ��϶�ȡ���ݣ������������
	 * 
	 * @param data
	 *            ����ļ���
	 */
	public static void testData(List<DataMap> data)
	{
		Iterator<DataMap> iterator = data.iterator();
		while (iterator.hasNext())
			System.out.println(iterator.next());
	}

	/**
	 * ============================================
	 * 
	 * �Լ������򣬡��������������
	 * 
	 * @param list
	 */
	public static void listSort(List<DataMap> list)
	{
		CommonTool.listSort(list);
	}

	/**
	 * ==================================================
	 * 
	 * @param key
	 *            Ҫ��ȡ�Ĺؼ���
	 * @param list
	 *            Ŀ�꼯��
	 * @return ��ȡ�Ĺؼ��ֶ�Ӧ��ֵ
	 */
	public static DataMap getByKey(String key, List<DataMap> list)
	{
		return CommonTool.getByKey(key, list);
	}

	/**
	 * ====================================================
	 * 
	 * @param table
	 *            Ҫ����xlsx������Դ������Ϊ1����
	 * @return
	 */
	public static Map<String, List<BaseRow>> chartAdapterDouble(
			List<?>... table)
	{
		return chartAdapterDouble(false, table);
	}

	/**
	 * 
	 * @param genderType
	 *            �Ƿ�Ϊ�Ա�ؼ��֣�TRUEΪ�Ա𣬷���Ϊ���йؼ���
	 * @param table
	 * @return
	 */
	public static Map<String, List<BaseRow>> chartAdapterDouble(
			boolean genderType, List<?>... table)
	{
		return ChartXlsxTool.chartAdapterDouble(genderType, table);
	}
}
