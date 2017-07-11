package cn.edu.xmu.software.binarykang.xlsx;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * 代表一个压缩包里面的一个压缩文件
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-18
 * 
 */
public class XMLEntry {
	private String path;
	private Document document;

	public XMLEntry(String basePath, String externPath) {
		path = externPath;
		try {
			document = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse(new FileInputStream(new File(basePath + externPath)));
		}

		catch (Exception e) {
			System.err.println("在生成XMLEntry的构造函数中抛出异常");
			e.printStackTrace();
		}
	}

	/**
	 * 获取这个XML文件的根节点
	 * 
	 * @return 这个XML文件的根节点
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * 将一个压缩文件压入压缩包中
	 * 
	 * @param zipOutputStream
	 *            压缩文件输出流
	 */
	public void save(ZipOutputStream zipOutputStream) {
		ZipEntry zipEntry = new ZipEntry(path);
		try {
			zipOutputStream.putNextEntry(zipEntry);
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(zipOutputStream);
			TransformerFactory.newInstance().newTransformer()
					.transform(domSource, streamResult);
			zipOutputStream.closeEntry();
		} catch (Exception e) {
			System.err.println("生成压缩文件的时候出错");
			e.printStackTrace();
		}
	}
}
