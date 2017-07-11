package cn.edu.xmu.software.binarykang.xlsx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.parse.DocxHelper;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.util.FileInfo;
import cn.edu.xmu.software.net.services.RealPath;

public class ChartXlsx
{
	protected final String EXCEL_NAME = "Microsoft_Excel____%d.xlsx";
	private final String BASE_PATH = RealPath.realPath + "Resource/xlsx/";

	private Document sheetXMLRootNode;
	private Document sharedStringsXMLRootNode;
	private Document coreXMLRootNode;

	protected List<XMLEntry> xmlEntries;

	public ChartXlsx()
	{
		xmlEntries = ListFactory.getXMLEntries();
		loadTemplates();
	}

	/**
	 * 加载生成Excel文件所需的模版文件
	 */
	private void loadTemplates()
	{
		xmlEntries.add(new XMLEntry(BASE_PATH, "[Content_Types].xml"));

		xmlEntries.add(new XMLEntry(BASE_PATH, "_rels/.rels"));

		xmlEntries.add(new XMLEntry(BASE_PATH, "docProps/app.xml"));
		XMLEntry coreXML = new XMLEntry(BASE_PATH, "docProps/core.xml");
		coreXMLRootNode = coreXML.getDocument();
		xmlEntries.add(coreXML);

		xmlEntries.add(new XMLEntry(BASE_PATH, "xl/styles.xml"));
		xmlEntries.add(new XMLEntry(BASE_PATH, "xl/workbook.xml"));
		xmlEntries.add(new XMLEntry(BASE_PATH, "xl/_rels/workbook.xml.rels"));
		xmlEntries.add(new XMLEntry(BASE_PATH, "xl/theme/theme1.xml"));

		XMLEntry sheetXML = new XMLEntry(BASE_PATH, "xl/worksheets/sheet1.xml");
		sheetXMLRootNode = sheetXML.getDocument();
		xmlEntries.add(sheetXML);

		XMLEntry sharedStringsXML = new XMLEntry(BASE_PATH,
				"xl/sharedStrings.xml");
		sharedStringsXMLRootNode = sharedStringsXML.getDocument();
		xmlEntries.add(sharedStringsXML);
	}

	/**
	 * 为Excel设置值，提供默认的百分比格式化器
	 * 
	 * @param data
	 *            传递过来的数据值
	 */
	public void setData(Map<String, List<BaseRow>> data)
	{
		setData(data, 1);
	}

	/**
	 * 为Excel设置值，根据不同类型的格式化器格式化数字
	 * 
	 * @param data
	 *            传递过来的数据值
	 * @param formatType
	 *            格式化类型，1代表百分比，2代表保留两位小数
	 */
	public void setData(Map<String, List<BaseRow>> data, int formatType)
	{
		addSharedStrings(data);
		addSheetData(data, formatType);
	}

	/**
	 * 为sheet添加数据和值
	 * 
	 * @param data
	 *            传递过来的数据值
	 * @param formatType
	 *            格式化类型，1代表百分比，2代表保留两位小数
	 */
	private void addSheetData(Map<String, List<BaseRow>> data, int formatType)
	{
		List<String> colKeys = new ArrayList<String>(data.keySet());
		final int colNum = colKeys.size();
		final int rowNum = data.get(colKeys.get(0)).size();

		Node sheetDataNode = sheetXMLRootNode.getDocumentElement()
				.getElementsByTagName("sheetData").item(0);
		Node dimensionNode = sheetXMLRootNode.getDocumentElement()
				.getElementsByTagName("dimension").item(0);
		dimensionNode.getAttributes().getNamedItem("ref")
				.setNodeValue("A1:" + (char) ('A' + colNum) + (1 + rowNum));

		Node firstRow = genRowNode(1, colNum);

		for (int i = 0; i < colNum; i++)
		{
			firstRow.appendChild(genCStringNode(1, i + 1, i + ""));
		}
		sheetDataNode.appendChild(firstRow);

		for (int i = 0; i < rowNum; i++)
		{
			Node row = genRowNode(i + 2, colNum);
			row.appendChild(genCStringNode(i + 2, 0, i + colNum + ""));
			for (int j = 0; j < colNum; j++)
			{
				row.appendChild(genCDoubleNode(i + 2, j + 1,
						data.get(colKeys.get(j)).get(i).value + "", formatType));
			}
			sheetDataNode.appendChild(row);
		}
	}

	/**
	 * 生成c结点，这种c结点的值类型是string类型，需要参照外部的引用
	 * 
	 * @param row
	 *            行下标
	 * @param col
	 *            列下标
	 * @param nodeValue
	 *            节点值
	 * @return 生成的c结点
	 */
	private Node genCStringNode(int row, int col, String nodeValue)
	{
		Element cNode = sheetXMLRootNode.createElement("c");
		Node vNode = sheetXMLRootNode.createElement("v");
		vNode.setTextContent(nodeValue);
		cNode.setAttribute("r", "" + (char) ('A' + col) + row);
		cNode.setAttribute("t", "s");
		cNode.appendChild(vNode);

		return cNode;
	}

	/**
	 * 生成c结点，这种c结点的值类型是double类型，需要用合适的格式化器来格式化double数字
	 * 
	 * @param row
	 *            行下标
	 * @param col
	 *            列下标
	 * @param nodeValue
	 *            节点值
	 * @param formatType
	 *            格式化类型，1代表百分比，2代表保留两位小数
	 * @return 生成的c结点
	 */
	private Node genCDoubleNode(int row, int col, String nodeValue,
			int formatType)
	{
		Element cNode = sheetXMLRootNode.createElement("c");
		Node vNode = sheetXMLRootNode.createElement("v");
		vNode.setTextContent(nodeValue);
		cNode.setAttribute("r", "" + (char) ('A' + col) + row);
		cNode.setAttribute("s", "" + formatType);
		cNode.appendChild(vNode);

		return cNode;
	}

	/**
	 * 通过行和列生成一个空的row结点
	 * 
	 * @param row
	 *            行下标，从1开始
	 * @param colNum
	 *            列下标，从0开始，0->A;1->B
	 * @return 生成的row结点
	 */
	private Node genRowNode(int row, int colNum)
	{
		Element rowNode = sheetXMLRootNode.createElement("row");

		rowNode.setAttribute("r", row + "");
		rowNode.setAttribute("spans", "1:" + (1 + colNum));
		rowNode.setAttribute("x14ac:dyDescent", "0.15");

		return rowNode;
	}

	/**
	 * 在sharedStrings.xml文件中，添加string字符串
	 * 
	 * @param data
	 *            需要添加的数据
	 */
	private void addSharedStrings(Map<String, List<BaseRow>> data)
	{
		int stringCounter = 1;// 初始化的时候，sharedStrings.xml文件中是有一个默认的字符串的
		Set<String> colKeys = data.keySet();
		Node root = sharedStringsXMLRootNode.getDocumentElement();
		Node lastChild = root.getLastChild();

		Iterator<String> iterator = colKeys.iterator();
		// 添加列字符串
		while (iterator.hasNext())
		{
			Node siNode = genSINode(iterator.next());
			root.insertBefore(siNode, lastChild);
			stringCounter++;
		}
		// 添加行字符串
		iterator = colKeys.iterator();
		while (iterator.hasNext())
		{
			List<BaseRow> rows = data.get(iterator.next());
			for (int i = 0; i < rows.size(); i++)
			{
				Node siNode = genSINode(rows.get(i).key);
				root.insertBefore(siNode, lastChild);
				stringCounter++;
			}
		}

		NamedNodeMap attrs = root.getAttributes();
		Node count = attrs.getNamedItem("count");
		count.setNodeValue(stringCounter + "");
		Node uniqueCount = attrs.getNamedItem("uniqueCount");
		uniqueCount.setNodeValue(stringCounter + "");
	}

	/**
	 * 根据结点的值，产生一个<si><t>nodeValue</t></si>的结点
	 * 
	 * @param nodeValue
	 *            节点值
	 * @return 产生的si结点
	 */
	private Node genSINode(String nodeValue)
	{
		Node siNode = sharedStringsXMLRootNode.createElement("si");
		Node tNode = sharedStringsXMLRootNode.createElement("t");
		tNode.setTextContent(nodeValue);
		siNode.appendChild(tNode);
		return siNode;
	}

	/**
	 * 修改Excel文件的core.xml文件，更新创建日期、修改日期、创建者、修改者
	 */
	protected void changeCoreXML()
	{
		final String author = "ARDGS";
		SimpleDateFormat dayFormat = new SimpleDateFormat("YYYY-MM-dd");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		Date currentDate = new Date();
		final String date = dayFormat.format(currentDate) + "T"
				+ hourFormat.format(currentDate) + "Z";

		Node creator = coreXMLRootNode.getDocumentElement()
				.getElementsByTagName("dc:creator").item(0);
		creator.setTextContent(author);
		Node lastModifiedBy = coreXMLRootNode.getDocumentElement()
				.getElementsByTagName("cp:lastModifiedBy").item(0);
		lastModifiedBy.setTextContent(author);
		Node createdTime = coreXMLRootNode.getDocumentElement()
				.getElementsByTagName("dcterms:created").item(0);
		createdTime.getFirstChild().setNodeValue(date);
		Node modifiedTime = coreXMLRootNode.getDocumentElement()
				.getElementsByTagName("dcterms:modified").item(0);
		modifiedTime.getFirstChild().setNodeValue(date);
	}

	public void save()
	{
		changeCoreXML();

		try
		{
			FileOutputStream outputStream = new FileOutputStream(new File(
					FileInfo.INSTANCE.getSavePath()
							+ File.separator
							+ "word"
							+ File.separator
							+ "embeddings"
							+ File.separator
							+ String.format(EXCEL_NAME,
									DocxHelper.chartIndex() - 1)));

			ZipOutputStream zipOut = new ZipOutputStream(outputStream);
			for (int i = 0; i < xmlEntries.size(); i++)
			{
				xmlEntries.get(i).save(zipOut);
			}
			zipOut.finish();
			outputStream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
