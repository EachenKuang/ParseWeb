package cn.edu.xmu.software.binarykang.word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cn.edu.xmu.software.binarykang.adult.parse.DocxHelper;
import cn.edu.xmu.software.binarykang.minor.parse.Constant;
import cn.edu.xmu.software.binarykang.util.FileInfo;

/**
 * 打开一个XML文件的功能类
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * 
 */
public final class OpenXML
{
	private File file;
	private Document document;
	private String path;

	public OpenXML(String path)
	{
		this.path = path;
	}

	/**
	 * 使用工厂方法来生产OpenXML对象，专门用来打开chart.xml文件
	 * 
	 * @return OpenXML对象
	 */
	public static OpenXML getChartXml()
	{
		return new OpenXML(FileInfo.INSTANCE.getSavePath() + File.separator
				+ "word" + File.separator + "charts" + File.separator + "chart"
				+ DocxHelper.getChartIndex() + ".xml");
	}

	/**
	 * 使用工厂方法来生产OpenXML对象,Minor用
	 * 
	 * @return OpenXML对象
	 */
	public static OpenXML getMinorXml()
	{
		return new OpenXML(FileInfo.INSTANCE.getSavePath() + File.separator
				+ "word" + File.separator + "charts" + File.separator + "chart"
				+ Constant.getChartNum() + ".xml");
	}

	/**
	 * 打开XML文件
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public Document open() throws ParserConfigurationException, SAXException,
			IOException
	{
		// 打开document.xml文件
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		file = new File(this.path);
		document = db.parse(file);
		return document;
	}

	/**
	 * 关闭并保存XML文件
	 * 
	 * @throws TransformerException
	 * @throws IOException
	 */
	public void saveAndClose() throws TransformerException, IOException
	{
		// 关闭并保存document.xml文件
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		FileOutputStream fos = new FileOutputStream(file);
		StreamResult result = new StreamResult(fos);
		transformer.transform(source, result);
		fos.close();
	}
}
