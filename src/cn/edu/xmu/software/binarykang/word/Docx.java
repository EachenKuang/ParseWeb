package cn.edu.xmu.software.binarykang.word;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.edu.xmu.software.binarykang.adult.parse.DocxHelper;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * Word类，包含了常见的替换、插入、查找等操作
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class Docx
{
	private List<Node> wtNodes; // 需要替换的wt节点
	private Document document; // word文档的根节点
	private NodeList tableNodes;

	public Docx(Document document, Xlsx xlsx)
	{
		wtNodes = new ArrayList<Node>();
		this.document = document;
		tableNodes = document.getElementsByTagName("w:tbl");
		filter();
		replaceCityAndCountry(xlsx);
	}

	/**
	 * 替换当地和全国对应的变量
	 * 
	 * @param xlsx
	 *            excel文件
	 */
	private void replaceCityAndCountry(Xlsx xlsx)
	{
		String city = xlsx.getContent(xlsx.getRow(0), 1);
		String country = xlsx.getContent(xlsx.getRow(0), 2);
		replace("${city}", city);
		replace("${country}", country);
	}

	/**
	 * 过滤所有含有变量标识符"${"的所有w:t节点
	 */
	private void filter()
	{
		NodeList nodeList = document.getDocumentElement().getElementsByTagName(
				"w:t");
		int size = nodeList.getLength();
		for (int i = 0; i < size; ++i)
		{
			Node node = nodeList.item(i);
			if (node.getTextContent().startsWith("${"))
			{
				wtNodes.add(node);
			}
		}
	}

	/**
	 * 替换函数，将相应的key替换为对应的value
	 * 
	 * @param key
	 *            需要替换的变量
	 * @param value
	 *            替换称为的目的值
	 */
	public void replace(String key, String value)
	{
		Iterator<Node> iterator = wtNodes.iterator();
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
		Node copyNode = document.importNode(newNode, true);
		parentNode.insertBefore(copyNode, refNode);
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
	 * 获得当前的表格结点，其下标信息存储在一个全局变量中
	 * 
	 * @return 返回当前的表格结点
	 */
	public Node getTableNode()
	{
		return tableNodes.item(DocxHelper.getTableIndex());
	}

	/**
	 * 从给定的节点集合中，找出最后一个含有tagName的结点
	 * 
	 * @param nodeList
	 *            给定的结点集合
	 * @param tagName
	 *            需要查找的最后一个结点的名称
	 * @return 查找到的结点
	 */
	public Node getLastChild(NodeList nodeList, String tagName)
	{
		int size = nodeList.getLength();
		Node node = null;
		for (int i = size - 1; i >= 0; --i)
		{
			if (nodeList.item(i).getNodeName().equals(tagName))
			{
				node = nodeList.item(i);
				break;
			}
		}
		return node;
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

}
