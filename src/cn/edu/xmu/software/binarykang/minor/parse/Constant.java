package cn.edu.xmu.software.binarykang.minor.parse;

public final class Constant
{
	private static int chartNum = 1;
	private static int tableNum = 0;
	public final static int BEGIN_COL = 3;

	public static void init(){
		chartNum = 1;
		tableNum = 0;
	}
	public static int getChartNum()
	{
		return chartNum++;
	}

	public static int getTabelNum()
	{
		return tableNum++;
	}

	public static int showChartNum()
	{
		return chartNum;
	}
}
