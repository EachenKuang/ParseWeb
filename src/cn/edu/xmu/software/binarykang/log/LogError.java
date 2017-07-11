package cn.edu.xmu.software.binarykang.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LogError
{
	private static FileWriter fw;
	private static BufferedWriter bw;

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
		} catch (IOException e)
		{
			System.out.println("打开日志文件出错，出错信息：" + e.getMessage());
		}

	}

	/**
	 * 先将缓存里面的内容输出到日志，然后关系输出流，保存日志文件
	 */
	public static void save()
	{
		try
		{
			bw.newLine();// 换行
			bw.close();
			fw.close();
		} catch (IOException e)
		{
			System.out.println("关闭日志输出流错误，错误原因：" + e.getMessage());
		}
	}

	public static String read(String readPath, String errorKey)
	{
		String tmpLine = null;
		StringBuilder errorMsg = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(readPath)))
		{
			while ((tmpLine = br.readLine()) != null)
			{
				if (tmpLine.equals(errorKey))
				{
					while (!(tmpLine = br.readLine()).equals(""))
					{
						// System.out.println(tmpLine);
						errorMsg.append(tmpLine + "<br>");
					}
					break;
				}
			}
			errorMsg.delete(errorMsg.length() - 4, errorMsg.length());
		} catch (IOException e)
		{
			System.out.println("打开日志文件出错，出错信息：" + e.getMessage());
		} catch (StringIndexOutOfBoundsException e)
		{
			System.out.println("没有出错信息返回给用户");
		}

		return errorMsg.toString();
	}

	/**
	 * 将缓存内容按照格式输出到日志当中去
	 */
	public static void addError(String errorMsg)
	{
		try
		{
			bw.append(errorMsg);
			bw.newLine();// 换行
		} catch (IOException e)
		{
			System.out.println("写日志发生错误，错误原因：" + e.getMessage());
		}
	}

	public static void setKey(String key)
	{
		addError(key);
	}
}
