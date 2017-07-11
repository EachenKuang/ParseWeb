package cn.edu.xmu.software.net.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Random;

import cn.edu.xmu.software.net.services.RealPath;

public final class FileTool
{
	private static final Random RANDOM = new Random();

	private static String tempFileFolder; // 临时文件存放目录
	private static String fileFolder; // 存文件的目录

	/**
	 * 对fileTool的初始化
	 * 
	 * @param type
	 *            要解析的类型（minor/adult）
	 * 
	 */
	public static void init(String type)
	{
		FileTool.tempFileFolder = RealPath.realPath + "files/_tmp";
		FileTool.fileFolder = RealPath.realPath + "data/" + type + "/input";
	}

	/**
	 * 产生临时文件对象 会检查临时目录是否存在，如果不存在将创建目录
	 * 
	 * @return 临时文件对象
	 * @throws IOException
	 */
	public static File getTempFile() throws IOException
	{
		File tempFolder = new File(FileTool.tempFileFolder);
		if (!tempFolder.exists())
		{
			tempFolder.mkdirs();
		}
		String tempFileName = FileTool.tempFileFolder + File.separator
				+ Math.abs(RANDOM.nextInt());
		File tempFile = new File(tempFileName);
		if (!tempFile.exists())
		{
			tempFile.createNewFile();
		}
		return tempFile;
	}

	/**
	 * 将上传的数据流存入临时文件
	 * 
	 * @param fileSourcel
	 *            上传流
	 * @param tempFile
	 *            临时文件
	 * @throws IOException
	 */
	public static void writeToTempFile(InputStream fileSourcel, File tempFile)
			throws IOException
	{
		FileOutputStream outputStream = new FileOutputStream(tempFile);
		byte b[] = new byte[1000];
		int n;
		while ((n = fileSourcel.read(b)) != -1)
		{
			outputStream.write(b, 0, n);
		}
		outputStream.close();
		fileSourcel.close();
	}

	/**
	 * 从临时文件流中提取文件名称
	 * 
	 * @param randomFile
	 * @return 解析的文件名称
	 * @throws IOException
	 */
	public static String getFileName(RandomAccessFile randomFile)
			throws IOException
	{
		String _line;
		while ((_line = randomFile.readLine()) != null
				&& !_line.contains("form-data; name=\"upload\""))
		{
		}
		String filePath = _line;
		String filename = filePath.replace(
				"Content-Disposition: form-data; name=\"upload\"; filename=\"",
				"").replace("\"", "");
		filename = TranCodeingTool.codeString(filename);
		randomFile.seek(0);
		return filename;
	}

	/**
	 * 获取上传文件的开始位置 开始位置会因为from 表单的参数不同而不同 如果from表单只上传文件是从第四行开始
	 * 本例from表单还有一个title的input ， 所以从第八行开始。每多一个参数就加四行。
	 * 
	 * @param randomFile
	 * @return 上传文件的开始位置
	 * @throws IOException
	 */
	private static long getFileEnterPosition(RandomAccessFile randomFile)
			throws IOException
	{
		long enterPosition = 0;
		int forth = 1;
		int n;
		while ((n = randomFile.readByte()) != -1 && (forth <= 4))
		{
			if (n == '\n')
			{
				enterPosition = randomFile.getFilePointer();
				forth++;
			}
		}
		return enterPosition;
	}

	/**
	 * 获取上传文件的结束位置 结束位置会因为文件类型不同，而不同 压缩包是倒数第二行后
	 * 
	 * @param randomFile
	 * @return 文件的结束位置
	 * @throws IOException
	 */
	private static long getFileEndPosition(RandomAccessFile randomFile)
			throws IOException
	{
		randomFile.seek(randomFile.length());
		long endPosition = randomFile.getFilePointer();
		int j = 1;
		while ((endPosition >= 0) && (j <= 2))
		{
			endPosition--;
			randomFile.seek(endPosition);
			if (randomFile.readByte() == '\n')
			{
				j++;
			}
		}
		return --endPosition;
	}

	/**
	 * 检查要保存文件的文件夹是否存在
	 */
	public static void checkFold()
	{
		File file = new File(FileTool.fileFolder);
		if (!file.exists())
		{
			file.mkdirs();
		}
	}

	/**
	 * 将临时文件解析后存放到指定的文件存放目录
	 * 
	 * @param randomFile
	 * @param forthEnterPosition
	 * @param filename
	 * @return fileSize
	 * @throws IOException
	 */
	public static long saveFile(RandomAccessFile randomFile, String filename)
			throws IOException
	{
		File saveFile = new File(FileTool.fileFolder, filename);
		RandomAccessFile randomAccessFile = new RandomAccessFile(saveFile, "rw");

		long forthEnterPosition = getFileEnterPosition(randomFile);
		long endPosition = getFileEndPosition(randomFile);
		// 从上传文件数据的开始位置到结束位置，把数据写入到要保存的文件中
		randomFile.seek(forthEnterPosition);
		long startPoint = randomFile.getFilePointer();
		int readByte = 0;
		byte[] tmp = new byte[1024];
		while (startPoint < endPosition)
		{
			readByte = randomFile.read(tmp);
			randomAccessFile.write(tmp, 0,
					(int) (readByte < (endPosition - startPoint) ? readByte
							: (endPosition - startPoint)));
			startPoint = randomFile.getFilePointer();
		}
		long fileSize = randomAccessFile.length();
		randomAccessFile.close();
		return fileSize;
	}
}
