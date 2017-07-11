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

		// step 1 将上传文件流写入到临时文件
		File tempFile = FileTool.getTempFile();
		FileTool.writeToTempFile(request.getInputStream(), tempFile);

		// step 2从临时文件中取得上传文件流
		RandomAccessFile randomFile = new RandomAccessFile(tempFile, "r");

		// step 3取得文件名称
		String filename = FileTool.getFileName(randomFile);
		RealPath.fileName = filename;
		filename = ZipUtil.getZipPath() + filename;
		RealPath.timeFileName = filename;

		// step 4检查存放文件的目录在不在
		FileTool.checkFold();

		// step 5保存文件
		long fileSize = FileTool.saveFile(randomFile, filename);
		System.out.printf("FileInfo:%s %.2fkb\n", filename, fileSize / 1024.0);
	}

	@Override
	public void init() throws ServletException
	{
		RealPath.realPath = getServletContext().getRealPath("/");
	}
}