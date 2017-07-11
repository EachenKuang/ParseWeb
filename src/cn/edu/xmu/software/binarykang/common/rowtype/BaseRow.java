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
 * 基本的键值对的数据结构，用来存放只含有一个键和一个值的表格行数据
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * 
 */
public final class BaseRow extends Row
{
	public double value;

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
	public static void read(Xlsx xlsx, List<BaseRow> data, String rowKey)
	{
		read(xlsx, data, xlsx.getRowByKey(rowKey) + 1, AdultBaseAction.BEGIN_COL + 1);
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
				System.err.println("空行没有解析出来，出错行：" + (i + 1));
				if (!e.getMessage().equals("empty String"))
					LogError.addError("在 " + beginRow + " 行的表格数据格式有误！");
				break;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据表键来间隔的读取数据
	 * 
	 * @param xlsx
	 *            表数据源
	 * @param data
	 *            存放读取数据的列表
	 * @param rowKey
	 *            表键
	 */
	public static void readInterval(Xlsx xlsx, List<BaseRow> data, String rowKey)
	{
		readInterval(xlsx, data, xlsx.getRowByKey(rowKey) + 1, AdultBaseAction.BEGIN_COL);
	}

	/**
	 * 根据开始行和开始列，从表格中间隔的读取数据
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
				System.err.println("捕获异常\t出错位置在" + (row.getRowNum() + 1) + "行\t错误原因：" + e.getMessage());
				break;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据传入的列表数据生成一个柱状图或者饼状图
	 * 
	 * @param data
	 *            需要的列表数据
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

			// 修改chart.xml里面的表格数据引用
			chart.changeRef(1, data.size());
			// 创建一个Excel表格用来存放数据
			ChartXlsx chartXlsx = new ChartXlsx();
			chartXlsx.setData(getMap(data, "第一列"));
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
	 * 根据传入的BaseRow列表数据，以及表格行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
	 * 这种表格奇数行和偶数行的样式是一致的。
	 * 
	 * @param docx
	 *            当前打开的word文档
	 * @param data
	 *            BaseRow列表数据
	 * @param format
	 *            格式化器
	 * @param rowXML
	 *            表格行对应的xml文件的地址
	 */
	public static void table(Docx docx, List<BaseRow> data, String rowXML, Format format)
	{
		table(docx, data, rowXML, rowXML, format);
	}

	/**
	 * 根据传入的BaseRow列表数据，以及奇数行和偶数行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
	 * 这种表格奇数行和偶数行的样式，提供一个默认的格式化器（百分比格式化器），默认删除最后一行。
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
	public static void table(Docx docx, List<BaseRow> data, String oddRowXML, String evenRowXML)
	{
		table(docx, data, oddRowXML, evenRowXML, GF.p);
	}

	/**
	 * 根据传入的BaseRow列表数据，以及奇数行和偶数行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
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
	public static void table(Docx docx, List<BaseRow> data, String oddRowXML, String evenRowXML, Format format)
	{
		table(docx, data, oddRowXML, evenRowXML, format, true);
	}

	/**
	 * 根据传入的BaseRow列表数据，以及奇数行和偶数行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
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
	 * 带有下标的表格，即有3列，分别为下标列、键列、值列，这种表格的生成需求也是比较多的。根据传入的BaseRow列表数据，
	 * 以及表格行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
	 * 这种表格奇数行和偶数行的样式。提供一个默认的格式化器（百分比格式化器）
	 * 
	 * @param docx
	 *            当前打开的word文档
	 * @param data
	 *            BaseRow列表数据
	 * @param rowXML
	 *            表格行对应的xml文件的地址
	 */
	public static void tableWithIndex(Docx docx, List<BaseRow> data, String rowXML)
	{
		tableWithIndex(docx, data, rowXML, GF.p);
	}

	/**
	 * 带有下标的表格，即有3列，分别为下标列、键列、值列，这种表格的生成需求也是比较多的。根据传入的BaseRow列表数据，
	 * 以及表格行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格； 这种表格奇数行和偶数行的样式。
	 * 
	 * @param docx
	 *            当前打开的word文档
	 * @param data
	 *            BaseRow列表数据
	 * @param rowXML
	 *            表格行对应的xml文件的地址
	 * @param format
	 *            格式化器
	 */
	public static void tableWithIndex(Docx docx, List<BaseRow> data, String rowXML, Format format)
	{
		tableWithIndex(docx, data, rowXML, rowXML, format);
	}

	/**
	 * 带有下标的表格，即有3列，分别为下标列、键列、值列，这种表格的生成需求也是比较多的。根据传入的BaseRow列表数据，
	 * 以及奇数行和偶数行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
	 * 这种表格奇数行和偶数行的样式。提供一个默认的格式化器（百分比格式化器）
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
	public static void tableWithIndex(Docx docx, List<BaseRow> data, String oddRowXML, String evenRowXML)
	{
		tableWithIndex(docx, data, oddRowXML, evenRowXML, GF.p);
	}

	/**
	 * 带有下标的表格，即有3列，分别为下标列、键列、值列，这种表格的生成需求也是比较多的。根据传入的BaseRow列表数据，
	 * 以及奇数行和偶数行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格； 这种表格奇数行和偶数行的样式。
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
	 * 通过key查找一行的数据
	 * 
	 * @param key
	 *            需要查找的key
	 * @param data
	 *            表格数据
	 * @return key对应的那一行的数据
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
	 * 排序函数，默认为从大到小排序
	 * 
	 * @param data
	 *            需要排序的列表
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
	 * 除了最后一行不参与排序，其他的都参与排序
	 * 
	 * @param data
	 *            需要排序的列表
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
