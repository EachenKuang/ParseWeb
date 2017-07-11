package cn.edu.xmu.software.binarykang.common.rowtype;

import java.util.Comparator;
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
 * 应对拥有一个键三个值的行，这个行在解析中比较常见，故封装出来进行调用
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * 
 */
public final class TripleValueRow extends Row
{
	public double v1;
	public double v2;
	public double v3;

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
	public static void read(Xlsx xlsx, List<TripleValueRow> data, String rowKey)
	{
		read(xlsx, data, xlsx.getRowByKey(rowKey) + 3,
				AdultBaseAction.BEGIN_COL + 1);
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
	public static void read(Xlsx xlsx, List<TripleValueRow> data, int beginRow,
			int beginCol)
	{
		XSSFRow row = null;
		for (int i = beginRow; (row = xlsx.getRow(i)).getLastCellNum() != -1; ++i)
		{
			try
			{
				TripleValueRow tripleValueRow = new TripleValueRow();
				tripleValueRow.key = xlsx.getContent(row, beginCol);
				tripleValueRow.v1 = new Double(xlsx.getContent(row,
						beginCol + 1));
				tripleValueRow.v2 = new Double(xlsx.getContent(row,
						beginCol + 2));
				tripleValueRow.v3 = new Double(xlsx.getContent(row,
						beginCol + 3));
				data.add(tripleValueRow);
			} catch (NumberFormatException e)
			{
				System.err.println("空行没有解析出来，出错行：" + (i + 1));
				if (!e.getMessage().equals("empty String"))
					LogError.addError("在 " + beginRow + " 行的表格数据格式有误！");
				break;
			}
		}
	}

	/**
	 * 根据传入的TripleValueRow列表数据，以及奇数行和偶数行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
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
	public static void table(Docx docx, List<TripleValueRow> data,
			String oddRowXML, String evenRowXML)
	{
		table(docx, data, oddRowXML, evenRowXML, GF.p);
	}

	/**
	 * 根据传入的TripleValueRow列表数据，以及奇数行和偶数行对应的xml文件的路径，使用特定的Double格式化器来生成相应的表格；
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
	 */
	public static void table(Docx docx, List<TripleValueRow> data,
			String oddRowXML, String evenRowXML, Format format)
	{
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size(); ++i)
		{
			TripleValueRow row = data.get(i);
			XMLFragment xmlFragment = null;
			if (i % 2 == 0)
			{
				xmlFragment = new XMLFragment(oddRowXML);
			} else
			{
				xmlFragment = new XMLFragment(evenRowXML);
			}

			xmlFragment.replace("${key}", row.key);
			xmlFragment.replace("${v1}", format.format(row.v1));
			xmlFragment.replace("${v2}", format.format(row.v2));
			xmlFragment.replace("${v3}", format.format(row.v3));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

	/**
	 * 绘制三值的柱状图
	 * 
	 * @param data
	 *            需要的数据
	 */
	public static void chart(List<TripleValueRow> data, String colName1,
			String colName2, String colName3)
	{
		OpenXML xml = OpenXML.getChartXml();
		try
		{
			final String C_PT = "c:pt";
			Document root = xml.open();

			Chart chart = new Chart(root);

			Node v1StrCache = chart.getNodeByTagNameAndIndex("c:strCache", 1);
			Node v1StrCacheLastC_PTNode = chart.getChildByIndex(
					v1StrCache.getChildNodes(), C_PT, 0);
			Node v1NumCache = chart.getNodeByTagNameAndIndex("c:numCache", 0);
			Node v1NumCacheLastC_PTNode = chart.getChildByIndex(
					v1NumCache.getChildNodes(), C_PT, 0);
			Node v2StrCache = chart.getNodeByTagNameAndIndex("c:strCache", 3);
			Node v2StrCacheLastC_PTNode = chart.getChildByIndex(
					v2StrCache.getChildNodes(), C_PT, 0);
			Node v2NumCache = chart.getNodeByTagNameAndIndex("c:numCache", 1);
			Node v2NumCacheLastC_PTNode = chart.getChildByIndex(
					v2NumCache.getChildNodes(), C_PT, 0);
			Node v3StrCache = chart.getNodeByTagNameAndIndex("c:strCache", 5);
			Node v3StrCacheLastC_PTNode = chart.getChildByIndex(
					v3StrCache.getChildNodes(), C_PT, 0);
			Node v3NumCache = chart.getNodeByTagNameAndIndex("c:numCache", 2);
			Node v3NumCacheLastC_PTNode = chart.getChildByIndex(
					v3NumCache.getChildNodes(), C_PT, 0);

			// 修改chart.xml里面的表格数据引用
			chart.changeRef(3, data.size());
			// 创建一个Excel表格用来存放数据
			ChartXlsx chartXlsx = new ChartXlsx();
			chartXlsx.setData(getMap(data, colName1, colName2, colName3));
			chartXlsx.save();

			chart.setAttr(chart.getChildByIndex(v1StrCache.getChildNodes(),
					"c:ptCount", 0), "val", data.size() + "");
			chart.setAttr(chart.getChildByIndex(v1NumCache.getChildNodes(),
					"c:ptCount", 0), "val", data.size() + "");
			chart.setAttr(chart.getChildByIndex(v2StrCache.getChildNodes(),
					"c:ptCount", 0), "val", data.size() + "");
			chart.setAttr(chart.getChildByIndex(v2NumCache.getChildNodes(),
					"c:ptCount", 0), "val", data.size() + "");
			chart.setAttr(chart.getChildByIndex(v3StrCache.getChildNodes(),
					"c:ptCount", 0), "val", data.size() + "");
			chart.setAttr(chart.getChildByIndex(v3NumCache.getChildNodes(),
					"c:ptCount", 0), "val", data.size() + "");

			for (int i = 0; i < data.size(); ++i)
			{
				chart.insertBefore(v1StrCache,
						chart.generateC_PTNode(i + "", data.get(i).key),
						v1StrCacheLastC_PTNode);
				chart.insertBefore(v1NumCache,
						chart.generateC_PTNode(i + "", data.get(i).v1 + ""),
						v1NumCacheLastC_PTNode);
				chart.insertBefore(v2StrCache,
						chart.generateC_PTNode(i + "", data.get(i).key),
						v2StrCacheLastC_PTNode);
				chart.insertBefore(v2NumCache,
						chart.generateC_PTNode(i + "", data.get(i).v2 + ""),
						v2NumCacheLastC_PTNode);
				chart.insertBefore(v3StrCache,
						chart.generateC_PTNode(i + "", data.get(i).key),
						v3StrCacheLastC_PTNode);
				chart.insertBefore(v3NumCache,
						chart.generateC_PTNode(i + "", data.get(i).v3 + ""),
						v3NumCacheLastC_PTNode);

			}
			v1StrCache.removeChild(v1StrCacheLastC_PTNode);
			v1NumCache.removeChild(v1NumCacheLastC_PTNode);
			v2StrCache.removeChild(v2StrCacheLastC_PTNode);
			v2NumCache.removeChild(v2NumCacheLastC_PTNode);
			v3StrCache.removeChild(v3StrCacheLastC_PTNode);
			v3NumCache.removeChild(v3NumCacheLastC_PTNode);

			xml.saveAndClose();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 根据v1的值进行降序排列
	 * 
	 * @param data
	 *            需要排序的数据列表
	 */
	public static void sortByV1(List<TripleValueRow> data)
	{
		data.sort(new Comparator<TripleValueRow>()
		{

			@Override
			public int compare(TripleValueRow o1, TripleValueRow o2)
			{
				if (o1.v1 < o2.v1)
					return 1;
				else if (o1.v1 > o2.v1)
					return -1;
				return 0;
			}
		});
	}

	/**
	 * 根据v2的值进行降序排列
	 * 
	 * @param data
	 *            需要排序的数据列表
	 */
	public static void sortByV2(List<TripleValueRow> data)
	{
		data.sort(new Comparator<TripleValueRow>()
		{

			@Override
			public int compare(TripleValueRow o1, TripleValueRow o2)
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
	 * 根据v3的值进行降序排列
	 * 
	 * @param data
	 *            需要排序的数据列表
	 */
	public static void sortByV3(List<TripleValueRow> data)
	{
		data.sort(new Comparator<TripleValueRow>()
		{

			@Override
			public int compare(TripleValueRow o1, TripleValueRow o2)
			{
				if (o1.v3 < o2.v3)
					return 1;
				else if (o1.v3 > o2.v3)
					return -1;
				return 0;
			}
		});
	}

	/**
	 * 通过给定的三个列名称和三值列表，生成一个map
	 * 
	 * @param data
	 *            双值列表
	 * @param colName1
	 *            第一列名称
	 * @param colName2
	 *            第二列名称
	 * @param colName3
	 *            第三列名称
	 * @return 生成的map
	 */
	private static Map<String, List<BaseRow>> getMap(List<TripleValueRow> data,
			String colName1, String colName2, String colName3)
	{
		Map<String, List<BaseRow>> map = new LinkedHashMap<String, List<BaseRow>>();
		List<BaseRow> baseRows1 = ListFactory.getBaseRows();
		List<BaseRow> baseRows2 = ListFactory.getBaseRows();
		List<BaseRow> baseRows3 = ListFactory.getBaseRows();

		for (int i = 0; i < data.size(); i++)
		{
			TripleValueRow tripleValueRow = data.get(i);
			BaseRow baseRow1 = new BaseRow();
			baseRow1.key = tripleValueRow.key;
			baseRow1.value = tripleValueRow.v1;
			baseRows1.add(baseRow1);

			BaseRow baseRow2 = new BaseRow();
			baseRow2.key = tripleValueRow.key;
			baseRow2.value = tripleValueRow.v2;
			baseRows2.add(baseRow2);

			BaseRow baseRow3 = new BaseRow();
			baseRow3.key = tripleValueRow.key;
			baseRow3.value = tripleValueRow.v3;
			baseRows3.add(baseRow3);
		}

		map.put(colName1, baseRows1);
		map.put(colName2, baseRows2);
		map.put(colName3, baseRows3);

		return map;
	}
	
	

}
