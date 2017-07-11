package cn.edu.xmu.software.binarykang.log;

import java.text.SimpleDateFormat;

/**
 * ʱ��ͳ������������¼����ִ����Ҫ�����ʱ����Ϣ
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-31
 *
 */
public final class TimeCounter
{
	private long beginParseTime;// ��ʼ������ʱ��
	private long zipTime;// ���������У�ѹ��ռ�õ�ʱ��
	private long unZipTime;// ���������У���ѹ��ռ�õ�ʱ��
	private long parseTime;// ���������У�������ռ�õ�ʱ��
	private long totalTime;// �ܹ��Ľ���ʱ��

	/**
	 * ˽�й�������ֻ��ͨ��instance���������ɶ���
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
