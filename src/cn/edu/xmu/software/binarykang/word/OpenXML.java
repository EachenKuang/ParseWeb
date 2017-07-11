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
 * ��һ��XML�ļ��Ĺ�����
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
	 * ʹ�ù�������������OpenXML����ר��������chart.xml�ļ�
	 * 
	 * @return OpenXML����
	 */
	public static OpenXML getChartXml()
	{
		return new OpenXML(FileInfo.INSTANCE.getSavePath() + File.separator
				+ "word" + File.separator + "charts" + File.separator + "chart"
				+ DocxHelper.getChartIndex() + ".xml");
	}

	/**
	 * ʹ�ù�������������OpenXML����,Minor��
	 * 
	 * @return OpenXML����
	 */
	public static OpenXML getMinorXml()
	{
		return new OpenXML(FileInfo.INSTANCE.getSavePath() + File.separator
				+ "word" + File.separator + "charts" + File.separator + "chart"
				+ Constant.getChartNum() + ".xml");
	}

	/**
	 * ��XML�ļ�
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public Document open() throws ParserConfigurationException, SAXException,
			IOException
	{
		// ��document.xml�ļ�
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		file = new File(this.path);
		document = db.parse(file);
		return document;
	}

	/**
	 * �رղ�����XML�ļ�
	 * 
	 * @throws TransformerException
	 * @throws IOException
	 */
	public void saveAndClose() throws TransformerException, IOException
	{
		// �رղ�����document.xml�ļ�
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		FileOutputStream fos = new FileOutputStream(file);
		StreamResult result = new StreamResult(fos);
		transformer.transform(source, result);
		fos.close();
	}
}
