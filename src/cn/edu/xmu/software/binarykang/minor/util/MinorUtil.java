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
 * 该类部分函数已提取到相应Tool类里面，留下来的是为了兼容自谦函数
 * 
 * @author xmujx
 * @since 1.0
 */
public final class MinorUtil
{
	public static String local;
	public static String other;

	/**
	 * ================================================ 对表的动态生成
	 * 
	 * @param rowNum
	 *            列数
	 * @param cellNum
	 *            行数
	 * @param key
	 *            关键字集合
	 * @param value
	 *            值集合
	 * @param docx
	 *            docx对象
	 * @param tarClass
	 *            当前对象，获取对应的xml样板文件路径
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
	 * 读数据
	 * 
	 * @param xlsx
	 * @param beginKey
	 *            开始的标题
	 * @param data
	 *            数据存放对象，在外面初始化
	 * @param beginCol
	 *            开始列
	 * @param args
	 *            不为空则返回other数值
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
	 * ========================================= ArrayList工产
	 * 
	 * @return
	 */
	public static List<DataMap> listMapFactory()
	{
		return new ArrayList<DataMap>();
	}

	/**
	 * ======================================= 测试集合读取数据，输出集合数据
	 * 
	 * @param data
	 *            输出的集合
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
	 * 对集合排序，‘其他’排在最后
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
	 *            要获取的关键字
	 * @param list
	 *            目标集合
	 * @return 获取的关键字对应的值
	 */
	public static DataMap getByKey(String key, List<DataMap> list)
	{
		return CommonTool.getByKey(key, list);
	}

	/**
	 * ====================================================
	 * 
	 * @param table
	 *            要生成xlsx的数据源，可以为1或多个
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
	 *            是否为性别关键字，TRUE为性别，否则为城市关键字
	 * @param table
	 * @return
	 */
	public static Map<String, List<BaseRow>> chartAdapterDouble(
			boolean genderType, List<?>... table)
	{
		return ChartXlsxTool.chartAdapterDouble(genderType, table);
	}
}
