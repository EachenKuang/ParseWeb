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
 * 一个表格行XML片段
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class XMLFragment
{
	private List<Node> w_tNodes; // 这个xml片段中所有的w:t结点放在此列表中
	private Document document; // 表格行的根节点

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
	 * 获取XML文档树的根节点
	 * 
	 * @return XML文档树的根节点
	 */
	public Element getRootNode()
	{
		return document.getDocumentElement();
	}

	/**
	 * 根据传入的路径，解析之后返回相应的XML文件中文档树根节点
	 * 
	 * @param path
	 *            XML文件的路径
	 * @return XML文档树的根节点
	 */
	private Document parsePath(String path)
			throws ParserConfigurationException, SAXException, IOException
	{
		// 打开document.xml文件
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(new File(path));
	}

	/**
	 * 对读取出来的文档树做个过滤操作，将所有的w:t结点缓存起来，为之后的替换操作提高效率
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
	 * 搜索缓存的w:t结点，将含有指定key的结点替换为相应的value
	 * 
	 * @param key
	 *            需要比对的key
	 * @param value
	 *            需要替换的value
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
