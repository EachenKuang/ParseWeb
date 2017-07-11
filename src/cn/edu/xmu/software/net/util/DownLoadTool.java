package cn.edu.xmu.software.net.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.xmu.software.net.services.RealPath;

public class DownLoadTool
{

	/**
	 * 下载封装函数，传入response会将刚生成的文件返回给请求方
	 * @param response
	 * @param type
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void donwLoad(HttpServletResponse response, String... type)
			throws ServletException, IOException
	{
		File file = new File(RealPath.filePath);
		String fileName = RealPath.fileName.split("\\.")[0]
				+ ((type.length == 0) ? ".docx" : type[0]);

		response.reset();
		response.setContentType("APPLICATION/OCTET-STREAM; charset=UTF-8");
		response.encodeURL(new String(fileName.getBytes(), "UTF-8"));
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ TranCodeingTool.reCodeString(fileName) + "\"");
		ServletOutputStream outputStream = response.getOutputStream();
		InputStream inputStream = new FileInputStream(file);
		byte[] tmp = new byte[1024];
		int len;
		while ((len = inputStream.read(tmp)) > 0)
			outputStream.write(tmp, 0, len);
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
		outputStream.close();
		inputStream.close();
	}

	/**
	 * 对解析完成的页面跳转的集成
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void downLoadDeal(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
//		request.getSession().setAttribute("error", "Hello");

		response.setContentType("text/html; charset=UTF-8");
		response.sendRedirect("../index/download.jsp");
	}
}
