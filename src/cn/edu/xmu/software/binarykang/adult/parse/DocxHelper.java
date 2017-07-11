package cn.edu.xmu.software.binarykang.adult.parse;

/**
 * 解析Docx的帮助类，用来记录全局当前解析的表格和图的下标信息
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class DocxHelper
{
	private static int tableIndex = 0;// 表格下标
	private static int chartIndex = 1;// 柱状图或饼状图的小标

	public static void init(){
		tableIndex = 0;
		chartIndex = 1;
	}
	/**
	 * 获取当前是第几个表格
	 * 
	 * @return 当前表格的下标
	 */
	public static int getTableIndex()
	{
		return tableIndex++;
	}

	/**
	 * 获取当前是第几个图
	 * 
	 * @return 当前图的下标
	 */
	public static int getChartIndex()
	{
		return chartIndex++;
	}

	/**
	 * 调试帮助类，用来输出当前的图下标
	 */
	public static void showChartIndex()
	{
		System.out.println("chart index = " + chartIndex);
	}

	/**
	 * 调试帮助类，用来输出当前的表格
	 */
	public static void showTableIndex()
	{
		System.out.println("table index = " + tableIndex);
	}

	/**
	 * 获取当前是第几个图
	 * 
	 * @return 当前图的下标
	 */
	public static int chartIndex()
	{
		return chartIndex;
	}

}
