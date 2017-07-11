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
 * ����ͼ�����ɵ��࣬�����˶�̬���ɽ�㣬�滻������ݵȹ���
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class Chart
{
	private List<Node> c_vNodes; // �������е�c:v���
	private Document document; // ���XML���ĵ����ڵ�

	public Chart(Document document)
	{
		c_vNodes = new ArrayList<Node>();
		this.document = document;
		filter();
	}

	/**
	 * ���˳��ĵ��������е�c:v���
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
	 * ���ĵ����е�c:v����к���key�Ľ���滻����Ӧ��value
	 * 
	 * @param key
	 *            c:v���ļ�
	 * @param value
	 *            ��Ҫ�Ѽ��滻�ɵ�ֵ
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
	 * �����ĵ����У���n��ָ�����͵Ľ��
	 * 
	 * @param tagName
	 *            �������
	 * @param index
	 *            ����±�
	 * @return ���ҵ��Ľ��
	 */
	public Node getNodeByTagNameAndIndex(String tagName, int index)
	{
		NodeList nodeList = document.getElementsByTagName(tagName);
		return nodeList.item(index);
	}

	/**
	 * ��ָ���Ľڵ㼯���в���ָ����tagName�Ľ��
	 * 
	 * @param nodeList
	 *            ָ���Ľڵ㼯��
	 * @param tagName
	 *            ��Ҫ���ҵĽ��Ľڵ���
	 * @param index
	 *            ���ҵڼ������
	 * @return ���ҵ��Ľ��
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
	 * ��ָ������ָ�����Ե�ֵ�滻����Ӧ������ֵ
	 * 
	 * @param node
	 *            ��Ҫ�滻���ԵĽ��
	 * @param attrName
	 *            ָ������������
	 * @param value
	 *            ָ�������Ե�����ֵ
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
	 * ���ݴ��������value����һ��c:pt���
	 * 
	 * @param attrValue
	 *            ����ֵ
	 * @param nodeValue
	 *            ���ֵ
	 * @return ���ɵ�c:pt���
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
	 * ��ָ������ǰ�����һ�����
	 * 
	 * @param parentNode
	 *            ���ս��ĸ��ڵ�
	 * @param newNode
	 *            ��Ҫ������½��
	 * @param refNode
	 *            ���ս�㣬�½����ǲ��뵽����ǰ��
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
