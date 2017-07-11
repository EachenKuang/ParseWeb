package cn.edu.xmu.software.binarykang.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import cn.edu.xmu.software.net.services.RealPath;

/**
 * ѹ�����ѹ��Docx�ļ��Ĺ�����
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @author xmujx
 * 
 * @since 2014-07-19
 */
public final class ZipUtil
{
	/**
	 * ѹ��ָ��·�����ļ�Ϊָ����ѹ���ļ���
	 * 
	 * @throws IOException
	 */
	public static void zip(String fileName, String sourcePath)
			throws IOException
	{
		File file = new File(sourcePath);// Ҫѹ�����ļ���
		File zipFile = new File(fileName);// ѹ���ļ�������
		RealPath.filePath = fileName;

		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
				zipFile));// ѹ�������

		recursiveZip(file, sourcePath, zipOut);// �ݹ��ѹ����ӦĿ¼�µ��ļ�

		zipOut.close();// �ر�ѹ�������

		delete(new File(sourcePath));// ѹ�����֮����Ҫɾ����ѹʱ��Ļ����ļ�
	}

	/**
	 * �ݹ��ɾ����ѹ�ļ������������
	 * 
	 * @param file
	 *            ��ѹ�ļ��е�·��
	 */
	private static void delete(File file)
	{
		if (file.isFile())
		{
			file.delete();
		} else if (file.isDirectory())
		{
			File[] files = file.listFiles();
			for (File tempFile : files)
			{
				delete(tempFile);
			}
			file.delete();
		}
	}

	/**
	 * �ݹ��ѹ���ļ�
	 * 
	 * @param file
	 *            ��Ҫѹ�����ļ���·��
	 * @param sourcePath
	 *            ѹ���ļ�Դ·��
	 * @param zipOutput
	 *            ѹ���ļ������
	 */
	private static void recursiveZip(File file, String sourcePath,
			ZipOutputStream zipOutput) throws IOException
	{
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			for (File subFile : files)
			{
				recursiveZip(subFile, sourcePath, zipOutput);
			}

		} else
		{
			InputStream input = new FileInputStream(file);
			String subString = file.getPath().replace(
					sourcePath + File.separator, "");
			zipOutput.putNextEntry(new ZipEntry(subString));
			int readByte = 0;
			byte[] buffer = new byte[1024];
			while ((readByte = input.read(buffer)) != -1)
			{
				zipOutput.write(buffer, 0, readByte);
			}
			input.close();
		}
	}

	/**
	 * ��ѹ��Docx�ļ�
	 * 
	 * @param zipFilePath
	 *            ��Ҫ��ѹ����Docx�ļ�����
	 * 
	 */
	public static void unZip(String zipFilePath, String targetFile)
			throws ZipException, IOException
	{
		File file = new File(zipFilePath); // �ҵ�ѹ���ļ�
		ZipFile zipFile = new ZipFile(file); // ʵ����ZipFile����
		ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file)); // ʵ����ZIP������
		File outFile = null; // ����������ļ�����

		ZipEntry entry = null; // ����һ��ZipEntry�������ڽ���ѹ���ļ��� ��ÿһ��ʵ��
		InputStream input = null; // ���������������ڶ�ȡÿһ��ZipEntry
		OutputStream out = null; // ������������������ÿһ��ʵ������

		while ((entry = zipInput.getNextEntry()) != null) // �õ�ÿһ�� ZipEntry
		{
			outFile = new File(targetFile + File.separator + entry.getName());
			if (!outFile.getParentFile().exists())
			{
				outFile.getParentFile().mkdirs();
			}
			if (!outFile.exists())
			{
				outFile.createNewFile();
			}
			input = zipFile.getInputStream(entry);
			out = new FileOutputStream(outFile);

			int readByte = 0;
			byte[] buffer = new byte[1024];
			while ((readByte = input.read(buffer)) != -1)
			{
				out.write(buffer, 0, readByte);
			}
			input.close();
			out.close();
		}
		zipFile.close();
		zipInput.close();
	}

	/**
	 * ��ȡϵͳ�ĵ�ǰʱ�䣬������ʱ���ַ����� <br>
	 * ʱ���ַ�����ʽΪ��<b>YYYY-MM-dd-HH-mm-ss</b>
	 */
	public static String getZipPath()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"YYYY-MM-dd#HH-mm-ss");
		String zipPath = dateFormat.format(new Date());
		return zipPath;
	}
}
