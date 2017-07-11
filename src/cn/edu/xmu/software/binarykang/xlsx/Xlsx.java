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
 * Excel����࣬������һЩ������Ԥ�����滻�Ȳ���
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
	 * ͨ��ָ�����к�����ȡExcel����е�һ��
	 * 
	 * @param rowIndex
	 *            ָ�����к�
	 * @return Excel����е�һ��
	 */
	public XSSFRow getRow(int rowIndex)
	{
		return sheet.getRow(rowIndex);
	}

	/**
	 * ͨ������һ��row��һ�����±�����ȡһ����Ԫ���е�����
	 * 
	 * @param xssfRow
	 *            ������row
	 * @param colIndex
	 *            ���±�
	 * @return ��Ӧ��Ԫ�������
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
	 * ��Excel���֮���Ƚ�Excel����еı�����������뻺���У� ֮��Ľ����ᾭ���õ�����������Ϊ����߽������ٶ�
	 */
	private void loadAllKeys()
	{
		final int KEY_COL = 1;// ���λ�ڵڶ���
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
	 * ͨ��ָ����key��ȥkey�����в�����λ��Excel�������һ��
	 * 
	 * @param key
	 *            Ҫ���ҵ�key
	 * @return ���key��Ӧ������
	 */
	public int getRowByKey(String key)
	{
		return keys.get(key);
	}

	/**
	 * �ڴ򿪴����excel�ļ���ʱ���ȶ�excel�ļ���Ԥ����ȷ������Ҳ��row��֮��Ӧ
	 * 
	 * @param filePath
	 *            excel�ļ�·��
	 */
	private void preProcess(String filePath) throws Exception
	{
		String unzipExcelFileName = "UNZIP_EXCEL_" + ZipUtil.getZipPath();
		ZipUtil.unZip(filePath, FileInfo.INSTANCE.getReadPath() + File.separator + unzipExcelFileName);// ��ѹExcel�ļ�
		// ɾ��Դ�ļ�
		File sourceXlsx = new File(filePath);
		sourceXlsx.delete();

		OpenXML xml = new OpenXML(FileInfo.INSTANCE.getReadPath() + File.separator + unzipExcelFileName + ""
				+ File.separator + "xl" + File.separator + "worksheets" + File.separator + "sheet1.xml");
		Document root = xml.open(); // ��sheet1.xml�ļ�

		addEmptyRow(root);

		xml.saveAndClose();
		ZipUtil.zip(filePath, FileInfo.INSTANCE.getReadPath() + unzipExcelFileName);// ѹ��Excel�ļ�
	}

	/**
	 * ���������DOM����Ϊ�������row��ǩ
	 * 
	 * @param root
	 *            DOM���ĸ����
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
