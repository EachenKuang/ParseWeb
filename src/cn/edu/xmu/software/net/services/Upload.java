package cn.edu.xmu.software.net.services;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.xmu.software.binarykang.util.ZipUtil;
import cn.edu.xmu.software.net.util.DownLoadTool;
import cn.edu.xmu.software.net.util.FileTool;

public class Upload extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		DownLoadTool.donwLoad(response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		// step 1 ���ϴ��ļ���д�뵽��ʱ�ļ�
		File tempFile = FileTool.getTempFile();
		FileTool.writeToTempFile(request.getInputStream(), tempFile);

		// step 2����ʱ�ļ���ȡ���ϴ��ļ���
		RandomAccessFile randomFile = new RandomAccessFile(tempFile, "r");

		// step 3ȡ���ļ�����
		String filename = FileTool.getFileName(randomFile);
		RealPath.fileName = filename;
		filename = ZipUtil.getZipPath() + filename;
		RealPath.timeFileName = filename;

		// step 4������ļ���Ŀ¼�ڲ���
		FileTool.checkFold();

		// step 5�����ļ�
		long fileSize = FileTool.saveFile(randomFile, filename);
		System.out.printf("FileInfo:%s %.2fkb\n", filename, fileSize / 1024.0);
	}

	@Override
	public void init() throws ServletException
	{
		RealPath.realPath = getServletContext().getRealPath("/");
	}
}