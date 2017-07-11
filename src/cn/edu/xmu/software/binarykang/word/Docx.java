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
 * Word�࣬�����˳������滻�����롢���ҵȲ���
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class Docx
{
	private List<Node> wtNodes; // ��Ҫ�滻��wt�ڵ�
	private Document document; // word�ĵ��ĸ��ڵ�
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
	 * �滻���غ�ȫ����Ӧ�ı���
	 * 
	 * @param xlsx
	 *            excel�ļ�
	 */
	private void replaceCityAndCountry(Xlsx xlsx)
	{
		String city = xlsx.getContent(xlsx.getRow(0), 1);
		String country = xlsx.getContent(xlsx.getRow(0), 2);
		replace("${city}", city);
		replace("${country}", country);
	}

	/**
	 * �������к��б�����ʶ��"${"������w:t�ڵ�
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
	 * �滻����������Ӧ��key�滻Ϊ��Ӧ��value
	 * 
	 * @param key
	 *            ��Ҫ�滻�ı���
	 * @param value
	 *            �滻��Ϊ��Ŀ��ֵ
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
		Node copyNode = document.importNode(newNode, true);
		parentNode.insertBefore(copyNode, refNode);
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
	 * ��õ�ǰ�ı���㣬���±���Ϣ�洢��һ��ȫ�ֱ�����
	 * 
	 * @return ���ص�ǰ�ı����
	 */
	public Node getTableNode()
	{
		return tableNodes.item(DocxHelper.getTableIndex());
	}

	/**
	 * �Ӹ����Ľڵ㼯���У��ҳ����һ������tagName�Ľ��
	 * 
	 * @param nodeList
	 *            �����Ľ�㼯��
	 * @param tagName
	 *            ��Ҫ���ҵ����һ����������
	 * @return ���ҵ��Ľ��
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

}
