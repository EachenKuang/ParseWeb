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
		} catch (IOException e)
		{
			System.out.println("����־�ļ�����������Ϣ��" + e.getMessage());
		}

	}

	/**
	 * �Ƚ���������������������־��Ȼ���ϵ�������������־�ļ�
	 */
	public static void save()
	{
		try
		{
			bw.newLine();// ����
			bw.close();
			fw.close();
		} catch (IOException e)
		{
			System.out.println("�ر���־��������󣬴���ԭ��" + e.getMessage());
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
			System.out.println("����־�ļ�����������Ϣ��" + e.getMessage());
		} catch (StringIndexOutOfBoundsException e)
		{
			System.out.println("û�г�����Ϣ���ظ��û�");
		}

		return errorMsg.toString();
	}

	/**
	 * ���������ݰ��ո�ʽ�������־����ȥ
	 */
	public static void addError(String errorMsg)
	{
		try
		{
			bw.append(errorMsg);
			bw.newLine();// ����
		} catch (IOException e)
		{
			System.out.println("д��־�������󣬴���ԭ��" + e.getMessage());
		}
	}

	public static void setKey(String key)
	{
		addError(key);
	}
}
