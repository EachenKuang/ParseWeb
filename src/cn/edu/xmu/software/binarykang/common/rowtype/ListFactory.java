package cn.edu.xmu.software.binarykang.common.rowtype;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xmu.software.binarykang.xlsx.XMLEntry;

/**
 * �б����࣬��������3�����͵��б������������б��ϸ�ڣ����ڽ������ò�ͬ���б����ͽ����л�
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class ListFactory
{
	/**
	 * ���ù�������һ��BaseRow���͵�ArrayList
	 * 
	 * @return ArrayList<BaseRow>����
	 */
	public static List<BaseRow> getBaseRows()
	{
		return new ArrayList<BaseRow>();
	}

	/**
	 * ���ù�������һ��DoubleValueRow���͵�ArrayList
	 * 
	 * @return ArrayList<DoubleValueRow>����
	 */
	public static List<DoubleValueRow> getDoubleValueRows()
	{
		return new ArrayList<DoubleValueRow>();
	}

	/**
	 * ���ù�������һ��TripleValueRow���͵�ArrayList
	 * 
	 * @return ArrayList<TripleValueRow>����
	 */
	public static List<TripleValueRow> getTripleValueRows()
	{
		return new ArrayList<TripleValueRow>();
	}

	/**
	 * ���ù�������һ��List<BaseRow>���͵�ArrayList
	 * 
	 * @return ArrayList<List<BaseRow>>����
	 */
	public static List<List<BaseRow>> getVarTypeRows()
	{
		return new ArrayList<List<BaseRow>>();
	}

	/**
	 * ���ù�������һ��String���͵�ArrayList
	 * 
	 * @return ArrayList<String>����
	 */
	public static List<String> getStringList()
	{
		return new ArrayList<String>();
	}

	/**
	 * ���ù�������һ��Integer���͵�ArrayList
	 * 
	 * @return ArrayList<Integer>����
	 */
	public static List<Integer> getIntegerList()
	{
		return new ArrayList<Integer>();
	}

	/**
	 * ���ù�������һ��Double���͵�ArrayList
	 * 
	 * @return ArrayList<Double>����
	 */
	public static List<Double> getDoubleList()
	{
		return new ArrayList<Double>();
	}

	/**
	 * ���ù�������һ��XMLEntry���͵�ArrayList
	 * 
	 * @return ArrayList<XMLEntry>����
	 */
	public static List<XMLEntry> getXMLEntries()
	{
		return new ArrayList<XMLEntry>();
	}

}
