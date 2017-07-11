package cn.edu.xmu.software.net.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.xmu.software.net.util.DownLoadTool;

public class DownLoad extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7585552352545620927L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		DownLoadTool.donwLoad(response);
	}
}
