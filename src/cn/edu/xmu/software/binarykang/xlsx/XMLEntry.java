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
 * ����һ��ѹ���������һ��ѹ���ļ�
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
			System.err.println("������XMLEntry�Ĺ��캯�����׳��쳣");
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ���XML�ļ��ĸ��ڵ�
	 * 
	 * @return ���XML�ļ��ĸ��ڵ�
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * ��һ��ѹ���ļ�ѹ��ѹ������
	 * 
	 * @param zipOutputStream
	 *            ѹ���ļ������
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
			System.err.println("����ѹ���ļ���ʱ�����");
			e.printStackTrace();
		}
	}
}
