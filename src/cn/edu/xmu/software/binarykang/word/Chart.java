package cn.edu.xmu.software.binarykang.word;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 用于图表生成的类，包含了动态生成结点，替换结点内容等功能
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class Chart
{
	private List<Node> c_vNodes; // 缓存所有的c:v结点
	private Document document; // 表格XML的文档根节点

	public Chart(Document document)
	{
		c_vNodes = new ArrayList<Node>();
		this.document = document;
		filter();
	}

	/**
	 * 过滤出文档树中所有的c:v结点
	 */
	private void filter()
	{
		NodeList nodeList = document.getDocumentElement().getElementsByTagName(
				"c:v");
		int size = nodeList.getLength();
		for (int i = 0; i < size; ++i)
		{
			Node node = nodeList.item(i);
			c_vNodes.add(node);
		}
	}

	/**
	 * 将文档树中的c:v结点中含有key的结点替换成相应的value
	 * 
	 * @param key
	 *            c:v结点的键
	 * @param value
	 *            需要把键替换成的值
	 */
	public void replace(String key, String value)
	{
		Iterator<Node> iterator = c_vNodes.iterator();
		Node node = null;
		while (iterator.hasNext())
		{
			node = iterator.next();
			String content = node.getTextContent();
			if (content.equals(key))
				node.getFirstChild().setNodeValue(value);
		}
	}

	/**
	 * 查找文档树中，第n个指定类型的结点
	 * 
	 * @param tagName
	 *            结点类型
	 * @param index
	 *            结点下标
	 * @return 查找到的结点
	 */
	public Node getNodeByTagNameAndIndex(String tagName, int index)
	{
		NodeList nodeList = document.getElementsByTagName(tagName);
		return nodeList.item(index);
	}

	/**
	 * 从指定的节点集合中查找指定的tagName的结点
	 * 
	 * @param nodeList
	 *            指定的节点集合
	 * @param tagName
	 *            需要查找的结点的节点名
	 * @param index
	 *            查找第几个结点
	 * @return 查找到的结点
	 */
	public Node getChildByIndex(NodeList nodeList, String tagName, int index)
	{
		int size = nodeList.getLength();
		Node node = null;
		int counter = 0;
		for (int i = 0; i < size; ++i)
		{
			if (nodeList.item(i).getNodeName().equals(tagName))
			{
				if (counter == index)
				{
					node = nodeList.item(i);
					break;
				}
				counter++;
			}
		}
		return node;
	}

	/**
	 * 将指定结点的指定属性的值替换成相应的属性值
	 * 
	 * @param node
	 *            需要替换属性的结点
	 * @param attrName
	 *            指定的属性名称
	 * @param value
	 *            指定的属性的属性值
	 */
	public void setAttr(Node node, String attrName, String value)
	{
		NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); ++i)
		{
			Node attr = attrs.item(i);
			if (attr.getNodeName().equals(attrName))
			{
				attr.setNodeValue(value);
				break;
			}
		}
	}

	/**
	 * 根据传入的两个value生成一个c:pt结点
	 * 
	 * @param attrValue
	 *            属性值
	 * @param nodeValue
	 *            结点值
	 * @return 生成的c:pt结点
	 */
	public Node generateC_PTNode(String attrValue, String nodeValue)
	{
		Node c_pt = document.createElement("c:pt");
		Node c_v = document.createElement("c:v");
		c_v.setTextContent(nodeValue);
		Attr idx = document.createAttribute("idx");
		idx.setValue(attrValue);
		c_pt.getAttributes().setNamedItem(idx);
		c_pt.appendChild(c_v);
		return c_pt;
	}

	/**
	 * 在指定结点的前面插入一个结点
	 * 
	 * @param parentNode
	 *            参照结点的父节点
	 * @param newNode
	 *            需要插入的新结点
	 * @param refNode
	 *            参照结点，新结点就是插入到它的前面
	 */
	public void insertBefore(Node parentNode, Node newNode, Node refNode)
	{
		parentNode.insertBefore(newNode, refNode);
	}

	public int changeRef(int chartTypeNum, int dataSize)
	{
		final int LEAST_CF_NODE = 3;
		NodeList c_fNodes = document.getDocumentElement().getElementsByTagName(
				"c:f");
		for (int i = 0; i < chartTypeNum; i++)
		{
			Node str_c_f = c_fNodes.item(1 + LEAST_CF_NODE * i);
			str_c_f.getFirstChild().setNodeValue(
					"Sheet1!$A$2:$A$" + (dataSize + 1));
			Node num_c_f = c_fNodes.item(2 + LEAST_CF_NODE * i);
			char colChar = (char) ('B' + i);
			num_c_f.getFirstChild().setNodeValue(
					"Sheet1!$" + colChar + "$2:$" + colChar + "$"
							+ (dataSize + 1));
		}
		return chartTypeNum;
	}
}
