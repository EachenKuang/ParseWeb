package cn.edu.xmu.software.binarykang.common.rowtype;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 单纯为为单值读取提供方法的功能类
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public class SingleValue
{
	/**
	 * 从Excel表格中读取单独的一个double值，通过key找到相应的表格，默认从表格键下一行，列偏移2的地方读取数据
	 * 
	 * @param xlsx
	 *            Excel数据源
	 * @param key
	 *            表格键
	 * @return 读取到的double值
	 */
	public static double read(Xlsx xlsx, String key)
	{
		return read(xlsx, key, 1, 2);
	}

	/**
	 * 从Excel表格中读取单独的一个double值，通过key找到相应的表格，然后根据行偏移和列偏移定位到相应的表格处
	 * 
	 * @param xlsx
	 *            Excel数据源
	 * @param key
	 *            表格键
	 * @param rowOffset
	 *            行偏移
	 * @param colOffset
	 *            列偏移
	 * @return 读取到的double值
	 */
	public static double read(Xlsx xlsx, String key, int rowOffset,
			int colOffset)
	{
		return new Double(xlsx.getContent(
				xlsx.getRow(xlsx.getRowByKey(key) + rowOffset),
				AdultBaseAction.BEGIN_COL + colOffset));
	}

}
