package cn.edu.xmu.software.binarykang.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ��¼��־���࣬��������־�������־���ر���־�Ȼ����Ĳ���
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-31
 */
public final class Log
{
	private static final String DIVISION_LINE = "======================================================================================================================================================";
	private static FileWriter fw;
	private static BufferedWriter bw;
	private static TimeCounter tc;// ����ʱ����Ϣ
	private static List<LogException> exceptions;// ���������Ϣ

	/**
	 * ����־�ļ�����ʼ��������Ϣ�б�
	 * 
	 * @param path
	 *            ��־�ļ���·��
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
			System.out.println("����־�ļ�����������Ϣ��" + e.getMessage());
		}

	}

	/**
	 * ������־��Ҫ�����ʱ����Ϣ
	 * 
	 * @param tc
	 *            һ����������ʱ��Ķ���
	 */
	public static void setTime(TimeCounter tc)
	{
		Log.tc = tc;
	}

	/**
	 * ���һ���쳣��Ϣ��������
	 * 
	 * @param e
	 *            һ���쳣��Ϣ
	 */
	public static void addException(Exception e)
	{
		addException(e, null);
	}

	/**
	 * ���һ���쳣��Ϣ�������У�ͬʱ����һ���ͻ��ܶ�������ʾ��Ϣ
	 * 
	 * @param e
	 *            һ���쳣��Ϣ
	 * @param msg
	 *            �ͻ��ܹ���������Ϣ
	 */
	public static void addException(Exception e, String msg)
	{
		exceptions.add(new LogException(e, msg));
	}

	/**
	 * �Ƚ���������������������־��Ȼ���ϵ�������������־�ļ�
	 */
	public static void save()
	{
		writeLog();// ����ȡ�������ݽ���д��־
		try
		{
			bw.close();
			fw.close();
		} catch (IOException e)
		{
			System.out.println("�ر���־��������󣬴���ԭ��" + e.getMessage());
		}
	}

	/**
	 * ���������ݰ��ո�ʽ�������־����ȥ
	 */
	private static void writeLog()
	{
		try
		{
			bw.write(DIVISION_LINE);// ��ʼ�ָ���
			bw.newLine();// ����

			/* ���ʱ��ͳ����Ϣ */
			writeTime();
			/* ����쳣��Ϣ */
			writeError();

			bw.write(DIVISION_LINE);// �����ָ���
			bw.newLine();// ��������������һ�ε���־�ָ�����
			bw.newLine();
		} catch (IOException e)
		{
			System.out.println("д��־�������󣬴���ԭ��" + e.getMessage());
		}
	}

	/**
	 * �����־ʱ����Ϣ��ص���Ϣ
	 */
	private static void writeTime() throws IOException
	{
		bw.write("ducument parse at time :" + tc.getBeginParseTime());
		bw.newLine();// ����
		bw.write("UnZip Time:" + tc.getUnZipTime());
		bw.newLine();// ����
		bw.write("Parse Time:" + tc.getParseTime());
		bw.newLine();// ����
		bw.write("ZipIn Time:" + tc.getZipTime());
		bw.newLine();// ����
		bw.write("Total Time:" + tc.getTotalTime());
		bw.newLine();// ����
	}

	/**
	 * �����־������ص���Ϣ
	 */
	private static void writeError() throws IOException
	{
		int size = exceptions.size();
		if (size == 0)
			return;
		bw.write(String.format("In this parse, have %d errors.", size));
		bw.newLine();// ����
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
			bw.newLine();// ����
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
