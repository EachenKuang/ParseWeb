package cn.edu.xmu.software.binarykang.xlsx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.edu.xmu.software.binarykang.util.FileInfo;
import cn.edu.xmu.software.binarykang.util.ZipUtil;
import cn.edu.xmu.software.binarykang.word.OpenXML;

/**
 * Excel表格类，包含了一些常见的预处理、替换等操作
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * 
 */
public final class Xlsx
{
	private XSSFWorkbook excel;
	private XSSFSheet sheet;
	private Map<String, Integer> keys;

	public Xlsx(String filePath)
	{
		try
		{
			preProcess(filePath);
			excel = new XSSFWorkbook(new FileInputStream(filePath));
			sheet = excel.getSheetAt(0);
			keys = new HashMap<String, Integer>();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		loadAllKeys();
	}

	/**
	 * 通过指定的行号来获取Excel表格中的一行
	 * 
	 * @param rowIndex
	 *            指定的行号
	 * @return Excel表格中的一行
	 */
	public XSSFRow getRow(int rowIndex)
	{
		return sheet.getRow(rowIndex);
	}

	/**
	 * 通过给定一个row，一个列下标来获取一个单元格中的内容
	 * 
	 * @param xssfRow
	 *            给定的row
	 * @param colIndex
	 *            列下标
	 * @return 对应单元格的内容
	 */
	public String getContent(XSSFRow xssfRow, int colIndex)
	{
		String content = "";

		XSSFCell cell = xssfRow.getCell(colIndex);
		if (cell != null)
		{
			switch (cell.getCellType())
			{
			case XSSFCell.CELL_TYPE_STRING:
				content = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				content += cell.getNumericCellValue();
				break;
			default:
				break;
			}
		}

		return content;
	}

	/**
	 * 打开Excel表格之后，先将Excel表格中的表键读出来放入缓存中， 之后的解析会经常用到，这样做是为了提高解析的速度
	 */
	private void loadAllKeys()
	{
		final int KEY_COL = 1;// 表键位于第二列
		int rowNum = 0;
		for (Iterator<?> rows = sheet.iterator(); rows.hasNext(); ++rowNum)
		{
			XSSFRow row = (XSSFRow) rows.next();
			XSSFCell cell = row.getCell(KEY_COL);
			if (cell != null && cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
			{
				keys.put(cell.getStringCellValue(), rowNum);
			}
		}
	}

	/**
	 * 通过指定的key，去key缓存中查找它位于Excel表格中哪一行
	 * 
	 * @param key
	 *            要查找的key
	 * @return 这个key对应的行数
	 */
	public int getRowByKey(String key)
	{
		return keys.get(key);
	}

	/**
	 * 在打开传入的excel文件的时候，先对excel文件做预处理，确保空行也有row与之对应
	 * 
	 * @param filePath
	 *            excel文件路径
	 */
	private void preProcess(String filePath) throws Exception
	{
		String unzipExcelFileName = "UNZIP_EXCEL_" + ZipUtil.getZipPath();
		ZipUtil.unZip(filePath, FileInfo.INSTANCE.getReadPath() + File.separator + unzipExcelFileName);// 解压Excel文件
		// 删除源文件
		File sourceXlsx = new File(filePath);
		sourceXlsx.delete();

		OpenXML xml = new OpenXML(FileInfo.INSTANCE.getReadPath() + File.separator + unzipExcelFileName + ""
				+ File.separator + "xl" + File.separator + "worksheets" + File.separator + "sheet1.xml");
		Document root = xml.open(); // 打开sheet1.xml文件

		addEmptyRow(root);

		xml.saveAndClose();
		ZipUtil.zip(filePath, FileInfo.INSTANCE.getReadPath() + unzipExcelFileName);// 压缩Excel文件
	}

	/**
	 * 遍历传入的DOM树，为空行添加row标签
	 * 
	 * @param root
	 *            DOM树的根结点
	 */
	private void addEmptyRow(Document root)
	{
		final NodeList nodeList = root.getDocumentElement().getElementsByTagName("row");
		int counter = 0;
		int addRowCounter = 0;

		int size = nodeList.getLength();

		for (int i = 0; i < size + addRowCounter; ++i)
		{
			Node node = nodeList.item(i);
			removeChild(node);
			NamedNodeMap attrs = node.getAttributes();
			for (int j = 0; j < attrs.getLength(); ++j)
			{
				Node attr = attrs.item(j);
				if (attr.getNodeName().equals("r"))
				{
					int nodeValue = new Integer(attr.getNodeValue());
					if (nodeValue - counter != 1)
					{
						Node row = root.createElement("row");
						for (int k = 0; k < attrs.getLength(); ++k)
						{
							Node createAttr = root.createAttribute(attrs.item(k).getNodeName());
							if (attrs.item(k).getNodeName().equals("r"))
							{
								createAttr.setNodeValue(counter + 1 + "");
								addRowCounter++;
							}
							else
							{
								createAttr.setNodeValue(attrs.item(k).getNodeValue());
							}
							row.getAttributes().setNamedItem(createAttr);
						}
						node.getParentNode().insertBefore(row, node);
					}
					counter++;
					break;
				}
			}
		}
	}

	private void removeChild(Node node)
	{
		NodeList list = node.getChildNodes();
		int size = list.getLength();
		for (int i = 0; i < size; i++)
		{
			Node childNode = list.item(i);
			int length = childNode.getChildNodes().getLength();

			if (length == 0)
			{
				i--;
				size--;
				node.removeChild(childNode);
			}
		}
	}

}
