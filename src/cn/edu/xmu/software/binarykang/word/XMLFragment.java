package cn.edu.xmu.software.binarykang.word;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.edu.xmu.software.net.services.RealPath;

/**
 * һ�������XMLƬ��
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class XMLFragment
{
	private List<Node> w_tNodes; // ���xmlƬ�������е�w:t�����ڴ��б���
	private Document document; // ����еĸ��ڵ�

	public XMLFragment(String path)
	{
		w_tNodes = new ArrayList<Node>();
		try
		{
			document = parsePath(RealPath.realPath + path);
			filter();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡXML�ĵ����ĸ��ڵ�
	 * 
	 * @return XML�ĵ����ĸ��ڵ�
	 */
	public Element getRootNode()
	{
		return document.getDocumentElement();
	}

	/**
	 * ���ݴ����·��������֮�󷵻���Ӧ��XML�ļ����ĵ������ڵ�
	 * 
	 * @param path
	 *            XML�ļ���·��
	 * @return XML�ĵ����ĸ��ڵ�
	 */
	private Document parsePath(String path)
			throws ParserConfigurationException, SAXException, IOException
	{
		// ��document.xml�ļ�
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(new File(path));
	}

	/**
	 * �Զ�ȡ�������ĵ����������˲����������е�w:t��㻺��������Ϊ֮����滻�������Ч��
	 */
	private void filter()
	{
		NodeList nodeList = document.getDocumentElement().getElementsByTagName(
				"w:t");
		for (int i = 0; i < nodeList.getLength(); ++i)
		{
			w_tNodes.add(nodeList.item(i));
		}
	}

	/**
	 * ���������w:t��㣬������ָ��key�Ľ���滻Ϊ��Ӧ��value
	 * 
	 * @param key
	 *            ��Ҫ�ȶԵ�key
	 * @param value
	 *            ��Ҫ�滻��value
	 */
	public void replace(String key, String value)
	{
		Iterator<Node> iterator = w_tNodes.iterator();
		Node node = null;
		while (iterator.hasNext())
		{
			node = iterator.next();
			String content = node.getTextContent();
			if (content.equals(key))
			{
				node.getFirstChild().setNodeValue(value);
			}
		}
	}

}
