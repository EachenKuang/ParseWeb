package cn.edu.xmu.software.binarykang.common.rowtype;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xmu.software.binarykang.xlsx.XMLEntry;

/**
 * 列表工厂类，用来生产3种类型的列表，屏蔽了生产列表的细节，便于将来利用不同的列表类型进行切换
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class ListFactory
{
	/**
	 * 利用工厂生成一个BaseRow类型的ArrayList
	 * 
	 * @return ArrayList<BaseRow>对象
	 */
	public static List<BaseRow> getBaseRows()
	{
		return new ArrayList<BaseRow>();
	}

	/**
	 * 利用工厂生成一个DoubleValueRow类型的ArrayList
	 * 
	 * @return ArrayList<DoubleValueRow>对象
	 */
	public static List<DoubleValueRow> getDoubleValueRows()
	{
		return new ArrayList<DoubleValueRow>();
	}

	/**
	 * 利用工厂生成一个TripleValueRow类型的ArrayList
	 * 
	 * @return ArrayList<TripleValueRow>对象
	 */
	public static List<TripleValueRow> getTripleValueRows()
	{
		return new ArrayList<TripleValueRow>();
	}

	/**
	 * 利用工厂生成一个List<BaseRow>类型的ArrayList
	 * 
	 * @return ArrayList<List<BaseRow>>对象
	 */
	public static List<List<BaseRow>> getVarTypeRows()
	{
		return new ArrayList<List<BaseRow>>();
	}

	/**
	 * 利用工厂生成一个String类型的ArrayList
	 * 
	 * @return ArrayList<String>对象
	 */
	public static List<String> getStringList()
	{
		return new ArrayList<String>();
	}

	/**
	 * 利用工厂生成一个Integer类型的ArrayList
	 * 
	 * @return ArrayList<Integer>对象
	 */
	public static List<Integer> getIntegerList()
	{
		return new ArrayList<Integer>();
	}

	/**
	 * 利用工厂生成一个Double类型的ArrayList
	 * 
	 * @return ArrayList<Double>对象
	 */
	public static List<Double> getDoubleList()
	{
		return new ArrayList<Double>();
	}

	/**
	 * 利用工厂生成一个XMLEntry类型的ArrayList
	 * 
	 * @return ArrayList<XMLEntry>对象
	 */
	public static List<XMLEntry> getXMLEntries()
	{
		return new ArrayList<XMLEntry>();
	}

}
