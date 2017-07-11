package cn.edu.xmu.software.binarykang.log;

import java.text.SimpleDateFormat;

/**
 * 时间统计器，用来记录程序执行需要的相关时间信息
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-31
 *
 */
public final class TimeCounter
{
	private long beginParseTime;// 开始解析的时间
	private long zipTime;// 解析过程中，压缩占用的时间
	private long unZipTime;// 解析过程中，解压缩占用的时间
	private long parseTime;// 解析过程中，解析所占用的时间
	private long totalTime;// 总共的解析时间

	/**
	 * 私有构造器，只能通过instance函数来生成对象
	 */
	private TimeCounter()
	{

	}

	public static TimeCounter instance()
	{
		return new TimeCounter();
	}

	public void beginZip()
	{
		zipTime = System.currentTimeMillis();
	}

	public void endZip()
	{
		zipTime = System.currentTimeMillis() - zipTime;
	}

	public long getZipTime()
	{
		return zipTime;
	}

	public void beginUnZip()
	{
		unZipTime = System.currentTimeMillis();
	}

	public void endUnZip()
	{
		unZipTime = System.currentTimeMillis() - unZipTime;
	}

	public long getUnZipTime()
	{
		return unZipTime;
	}

	public void beginParse()
	{
		parseTime = System.currentTimeMillis();
	}

	public void endParse()
	{
		parseTime = System.currentTimeMillis() - parseTime;
	}

	public long getParseTime()
	{
		return parseTime;
	}

	public void beginTotal()
	{
		totalTime = System.currentTimeMillis();
		beginParseTime = totalTime;
	}

	public void endTotal()
	{
		totalTime = System.currentTimeMillis() - totalTime;
	}

	public long getTotalTime()
	{
		return totalTime;
	}

	public String getBeginParseTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"YYYY-MM-dd#HH-mm-ss");
		return dateFormat.format(beginParseTime);
	}
}
