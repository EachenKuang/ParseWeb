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
 * 应对拥有一个键两个值的行，这个行在解析中比较常见，故封装出来进行调用。 多见于城乡对比的表格中
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * 
 */
public final class DoubleValueRow extends Row
{
	public double v1;
	public double v2;

	/**
	 * 根据表键来读取数据
	 * 
	 * @param xlsx
	 *            表数据源
	 * @param data
	 *            存放读取数据的列表
	 * @param rowKey
	 *            表键
	 */
	public static void read(Xlsx xlsx, List<DoubleValueRow> data, String rowKey)
	{
		read(xlsx, data, xlsx.getRowByKey(rowKey) + 3, AdultBaseAction.BEGIN_COL + 1);
	}

	/**
	 * 根据开始行和开始列，从表格中读取数据
	 * 
	 * @param xlsx
	 *            表数据源
	 * @param data
	 *            存放读取数据的列表
	 * @param beginRow
	 *            开始读取的行
	 * @param beginCol
	 *            开始读取的列
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
				System.err.println("空行没有解析出来，出错行：" + (i + 1));
				if (!e.getMessage().equals("empty String"))
					LogError.addError("在 " + beginRow + " 行的表格数据格式有误！");
				break;
			}
		}
	}

	/**
	 * 根据两个基本类型的列表生成一个含双值的列表，两个基本类型的列表的key必须相同，且行数相同
	 * 
	 * @param desc
	 *            需要生成的双值列表
	 * @param src1
	 *            第一个基本类型列表
	 * @param src2
	 *            第二个基本类型列表
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
	 * 根据传入的列表数据生成一个柱状图
	 * 
	 * @param data
	 *            需要的列表数据
	 */
	public static void chart(List<DoubleValueRow> data)
	{
		chart(data, "农村", "城镇", null);
	}

	public static void chart(List<DoubleValueRow> data, String colName1, String colName2)
	{
		chart(data, colName1, colName2, null);
	}

	/**
	 * 根据传入的列表数据生成一个柱状图，同时，根据传入的参数对，替换图中的字符串，提供默认的格式化类型，为百分比格式化器
	 * 
	 * @param data
	 *            需要的列表数据
	 * @param colName1
	 *            第一列名称
	 * @param colName2
	 *            第二列名称
	 * @param replaceData
	 *            键值对，表示需要图中需要替换的键和值
	 */
	public static void chart(List<DoubleValueRow> data, String colName1, String colName2,
			Map<String, String> replaceData)
	{
		chart(data, colName1, colName2, replaceData, 1);
	}

	/**
	 * 根据传入的列表数据生成一个柱状图，同时，根据传入的参数对，替换图中的字符串
	 * 
	 * @param data
	 *            需要的列表数据
	 * @param colName1
	 *            第一列名称
	 * @param colName2
	 *            第二列名称
	 * @param replaceData
	 *            键值对，表示需要图中需要替换的键和值
	 * @param formatType
	 *            Excel数据格式化类型
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

			// 修改chart.xml里面的表格数据引用
			chart.changeRef(2, data.size());
			// 创建一个Excel表格用来存放数据
			ChartXlsx chartXlsx = new ChartXlsx();
			chartXlsx.setData(getMap(data, colName1, colName2), formatType);
			chartXlsx.save();

			// 如果图表中有需要替换的字符串，则会在这里进行
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
	 * 根据传入的DoubleValueRow列表数据，以及表格行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
	 * 这种表格奇数行和偶数行的样式是一致的，提供一个默认的格式化器(百分比格式化器)，默认删除最后一行。
	 * 
	 * @param docx
	 *            当前打开的word文档
	 * @param data
	 *            BaseRow列表数据
	 * @param rowXML
	 *            表格行对应的xml文件的地址
	 */
	public static void table(Docx docx, List<DoubleValueRow> data, String rowXML)
	{
		table(docx, data, rowXML, rowXML, GF.p);
	}

	/**
	 * 根据传入的DoubleValueRow列表数据，以及奇数行和偶数行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
	 * 这种表格奇数行和偶数行的样式，提供一个默认的格式化器(百分比格式化器)，默认删除最后一行。
	 * 
	 * @param docx
	 *            当前打开的word文档
	 * @param data
	 *            BaseRow列表数据
	 * @param oddRowXML
	 *            奇数行对应的xml文件的地址
	 * @param evenRowXML
	 *            偶数行对应的xml文件的地址
	 */
	public static void table(Docx docx, List<DoubleValueRow> data, String oddRowXML, String evenRowXML)
	{
		table(docx, data, oddRowXML, evenRowXML, GF.p);
	}

	/**
	 * 根据传入的DoubleValueRow列表数据，以及奇数行和偶数行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
	 * 这种表格奇数行和偶数行的样式，默认删除最后一行。
	 * 
	 * @param docx
	 *            当前打开的word文档
	 * @param data
	 *            BaseRow列表数据
	 * @param oddRowXML
	 *            奇数行对应的xml文件的地址
	 * @param evenRowXML
	 *            偶数行对应的xml文件的地址
	 * @param format
	 *            格式化器
	 */
	public static void table(Docx docx, List<DoubleValueRow> data, String oddRowXML, String evenRowXML, Format format)
	{
		table(docx, data, oddRowXML, evenRowXML, format, true);
	}

	/**
	 * 根据传入的DoubleValueRow列表数据，以及奇数行和偶数行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
	 * 这种表格奇数行和偶数行的样式。
	 * 
	 * @param docx
	 *            当前打开的word文档
	 * @param data
	 *            BaseRow列表数据
	 * @param oddRowXML
	 *            奇数行对应的xml文件的地址
	 * @param evenRowXML
	 *            偶数行对应的xml文件的地址
	 * @param format
	 *            格式化器
	 * @param isDeleteLastRow
	 *            是否删除最后一行
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
	 * 通过key查找一行的数据
	 * 
	 * @param key
	 *            需要查找的key
	 * @param data
	 *            表格数据
	 * @return key对应的那一行的数据
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
	 * 将DoubleValueRow的v1和v2的值对换一下
	 * 
	 * @param data
	 *            需要对换值的行
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
	 * 根据v1的值来对整个列表进行降序排列
	 * 
	 * @param data
	 *            列表数据
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
	 * 根据v2的值来对整个列表进行降序排列
	 * 
	 * @param data
	 *            列表数据
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
	 * 根据v1-v2的差值进行降序排列
	 * 
	 * @param data
	 *            列表数据
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
	 * 根据v2-v1的差值进行降序排列
	 * 
	 * @param data
	 *            列表数据
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
	 * 通过给定的两个列名称和双值列表，生成一个map
	 * 
	 * @param data
	 *            双值列表
	 * @param colName1
	 *            第一列名称
	 * @param colName2
	 *            第二列名称
	 * @return 生成的map
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
