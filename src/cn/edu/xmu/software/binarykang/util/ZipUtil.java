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
 * 压缩与解压缩Docx文件的工具类
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @author xmujx
 * 
 * @since 2014-07-19
 */
public final class ZipUtil
{
	/**
	 * 压缩指定路径的文件为指定的压缩文件名
	 * 
	 * @throws IOException
	 */
	public static void zip(String fileName, String sourcePath)
			throws IOException
	{
		File file = new File(sourcePath);// 要压缩的文件夹
		File zipFile = new File(fileName);// 压缩文件的名称
		RealPath.filePath = fileName;

		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
				zipFile));// 压缩输出流

		recursiveZip(file, sourcePath, zipOut);// 递归的压缩相应目录下的文件

		zipOut.close();// 关闭压缩输出流

		delete(new File(sourcePath));// 压缩完毕之后需要删除解压时候的缓存文件
	}

	/**
	 * 递归的删除解压文件夹里面的内容
	 * 
	 * @param file
	 *            解压文件夹的路径
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
	 * 递归的压缩文件
	 * 
	 * @param file
	 *            需要压缩的文件的路径
	 * @param sourcePath
	 *            压缩文件源路径
	 * @param zipOutput
	 *            压缩文件输出流
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
	 * 解压缩Docx文件
	 * 
	 * @param zipFilePath
	 *            需要解压缩的Docx文件名称
	 * 
	 */
	public static void unZip(String zipFilePath, String targetFile)
			throws ZipException, IOException
	{
		File file = new File(zipFilePath); // 找到压缩文件
		ZipFile zipFile = new ZipFile(file); // 实例化ZipFile对象
		ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file)); // 实例化ZIP输入流
		File outFile = null; // 定义输出的文件对象

		ZipEntry entry = null; // 定义一个ZipEntry对象，用于接收压缩文件中 的每一个实体
		InputStream input = null; // 定义输入流，用于读取每一个ZipEntry
		OutputStream out = null; // 定义输出流，用于输出每一个实体内容

		while ((entry = zipInput.getNextEntry()) != null) // 得到每一个 ZipEntry
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
	 * 获取系统的当前时间，并返回时间字符串。 <br>
	 * 时间字符串格式为：<b>YYYY-MM-dd-HH-mm-ss</b>
	 */
	public static String getZipPath()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"YYYY-MM-dd#HH-mm-ss");
		String zipPath = dateFormat.format(new Date());
		return zipPath;
	}
}
