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
	 * 从指定的Excel中，根据提供的表格键，默认从表格键对应的行加上2的那一行开始，读取多类型的数据
	 * 
	 * @param xlsx
	 *            Excel数据源
	 * @param data
	 *            读取的数据的存放容器
	 * @param key
	 *            需要读取数据的表格键
	 */
	public static void read(Xlsx xlsx, List<List<BaseRow>> data, String key)
	{
		read(xlsx, data, xlsx.getRowByKey(key) + 2);
	}

	/**
	 * 从指定的Excel中，从指定行开始，读取多类型的数据
	 * 
	 * @param xlsx
	 *            Excel数据源
	 * @param data
	 *            读取的数据的存放容器
	 * @param beginRow
	 *            开始读取数据的行
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
				System.err.println("空行没有解析出来，出错行：" + (i + 1));
				break;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据给定的多类型数据，在指定的docx文档中，使用默认的百分比格式化器，替换指定格式变量的值，动态的生成表格
	 * 
	 * @param docx
	 *            给定的docx文档
	 * @param data
	 *            数据源
	 * @param firstRowKeyFormat
	 *            key变量对应的格式
	 * @param firstRowValueFormat
	 *            value变量对应的格式
	 * @param rowXML
	 *            表格行资源文件的地址
	 */
	public static void table(Docx docx, List<List<BaseRow>> data,
			String firstRowKeyFormat, String firstRowValueFormat, String rowXML)
	{
		table(docx, data, firstRowKeyFormat, firstRowValueFormat, rowXML, GF.p);
	}

	/**
	 * 根据给定的多类型数据，在指定的docx文档中，用给定的格式化器，替换指定格式变量的值，动态的生成表格
	 * 
	 * @param docx
	 *            给定的docx文档
	 * @param data
	 *            数据源
	 * @param firstRowKeyFormat
	 *            key变量对应的格式
	 * @param firstRowValueFormat
	 *            value变量对应的格式
	 * @param rowXML
	 *            表格行资源文件的地址
	 * @param format
	 *            格式化器
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
