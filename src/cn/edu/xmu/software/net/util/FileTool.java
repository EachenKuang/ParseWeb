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

	private static String tempFileFolder; // ��ʱ�ļ����Ŀ¼
	private static String fileFolder; // ���ļ���Ŀ¼

	/**
	 * ��fileTool�ĳ�ʼ��
	 * 
	 * @param type
	 *            Ҫ���������ͣ�minor/adult��
	 * 
	 */
	public static void init(String type)
	{
		FileTool.tempFileFolder = RealPath.realPath + "files/_tmp";
		FileTool.fileFolder = RealPath.realPath + "data/" + type + "/input";
	}

	/**
	 * ������ʱ�ļ����� ������ʱĿ¼�Ƿ���ڣ���������ڽ�����Ŀ¼
	 * 
	 * @return ��ʱ�ļ�����
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
	 * ���ϴ���������������ʱ�ļ�
	 * 
	 * @param fileSourcel
	 *            �ϴ���
	 * @param tempFile
	 *            ��ʱ�ļ�
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
	 * ����ʱ�ļ�������ȡ�ļ�����
	 * 
	 * @param randomFile
	 * @return �������ļ�����
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
	 * ��ȡ�ϴ��ļ��Ŀ�ʼλ�� ��ʼλ�û���Ϊfrom ���Ĳ�����ͬ����ͬ ���from��ֻ�ϴ��ļ��Ǵӵ����п�ʼ
	 * ����from������һ��title��input �� ���Դӵڰ��п�ʼ��ÿ��һ�������ͼ����С�
	 * 
	 * @param randomFile
	 * @return �ϴ��ļ��Ŀ�ʼλ��
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
	 * ��ȡ�ϴ��ļ��Ľ���λ�� ����λ�û���Ϊ�ļ����Ͳ�ͬ������ͬ ѹ�����ǵ����ڶ��к�
	 * 
	 * @param randomFile
	 * @return �ļ��Ľ���λ��
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
	 * ���Ҫ�����ļ����ļ����Ƿ����
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
	 * ����ʱ�ļ��������ŵ�ָ�����ļ����Ŀ¼
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
		// ���ϴ��ļ����ݵĿ�ʼλ�õ�����λ�ã�������д�뵽Ҫ������ļ���
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
