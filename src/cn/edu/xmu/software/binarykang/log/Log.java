package cn.edu.xmu.software.binarykang.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 记录日志的类，包含打开日志、输出日志、关闭日志等基本的操作
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-31
 */
public final class Log
{
	private static final String DIVISION_LINE = "======================================================================================================================================================";
	private static FileWriter fw;
	private static BufferedWriter bw;
	private static TimeCounter tc;// 缓存时间信息
	private static List<LogException> exceptions;// 缓存错误信息

	/**
	 * 打开日志文件，初始化错误信息列表
	 * 
	 * @param path
	 *            日志文件的路径
	 */
	public static void open(String path)
	{
		try
		{
			File log = new File(path);
			fw = new FileWriter(log, true);
			bw = new BufferedWriter(fw);
			exceptions = new ArrayList<LogException>();
		} catch (IOException e)
		{
			System.out.println("打开日志文件出错，出错信息：" + e.getMessage());
		}

	}

	/**
	 * 设置日志需要的相关时间信息
	 * 
	 * @param tc
	 *            一个存放了相关时间的对象
	 */
	public static void setTime(TimeCounter tc)
	{
		Log.tc = tc;
	}

	/**
	 * 添加一个异常信息到缓存中
	 * 
	 * @param e
	 *            一个异常信息
	 */
	public static void addException(Exception e)
	{
		addException(e, null);
	}

	/**
	 * 添加一个异常信息到缓存中，同时附加一个客户能读懂的提示信息
	 * 
	 * @param e
	 *            一个异常信息
	 * @param msg
	 *            客户能够读懂的信息
	 */
	public static void addException(Exception e, String msg)
	{
		exceptions.add(new LogException(e, msg));
	}

	/**
	 * 先将缓存里面的内容输出到日志，然后关系输出流，保存日志文件
	 */
	public static void save()
	{
		writeLog();// 将获取到的数据进行写日志
		try
		{
			bw.close();
			fw.close();
		} catch (IOException e)
		{
			System.out.println("关闭日志输出流错误，错误原因：" + e.getMessage());
		}
	}

	/**
	 * 将缓存内容按照格式输出到日志当中去
	 */
	private static void writeLog()
	{
		try
		{
			bw.write(DIVISION_LINE);// 开始分隔行
			bw.newLine();// 换行

			/* 输出时间统计信息 */
			writeTime();
			/* 输出异常信息 */
			writeError();

			bw.write(DIVISION_LINE);// 结束分隔行
			bw.newLine();// 最后输出两行与下一次的日志分隔开来
			bw.newLine();
		} catch (IOException e)
		{
			System.out.println("写日志发生错误，错误原因：" + e.getMessage());
		}
	}

	/**
	 * 输出日志时间信息相关的信息
	 */
	private static void writeTime() throws IOException
	{
		bw.write("ducument parse at time :" + tc.getBeginParseTime());
		bw.newLine();// 换行
		bw.write("UnZip Time:" + tc.getUnZipTime());
		bw.newLine();// 换行
		bw.write("Parse Time:" + tc.getParseTime());
		bw.newLine();// 换行
		bw.write("ZipIn Time:" + tc.getZipTime());
		bw.newLine();// 换行
		bw.write("Total Time:" + tc.getTotalTime());
		bw.newLine();// 换行
	}

	/**
	 * 输出日志错误相关的信息
	 */
	private static void writeError() throws IOException
	{
		int size = exceptions.size();
		if (size == 0)
			return;
		bw.write(String.format("In this parse, have %d errors.", size));
		bw.newLine();// 换行
		for (int i = 0; i < size; i++)
		{
			bw.write(String
					.format("Exception %d ==================================================>",
							i + 1));
			bw.newLine();
			LogException logException = exceptions.get(i);
			String msg = logException.msg;
			if (msg != null)
			{
				bw.write("Message:" + msg);
			}
			bw.write(logException.getClass() + ":"
					+ logException.e.getMessage());
			bw.newLine();// 换行
			StackTraceElement[] elements = logException.e.getStackTrace();
			for (StackTraceElement element : elements)
			{
				bw.write("\tat:" + element + "");
				bw.newLine();
			}
		}
	}

	private static class LogException
	{
		private Exception e;
		private String msg;

		public LogException(Exception e, String msg)
		{
			LogException.this.e = e;
			LogException.this.msg = msg;
		}
	}

}
