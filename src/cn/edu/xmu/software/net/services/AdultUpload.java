package cn.edu.xmu.software.net.services;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.xmu.software.binarykang.adult.parse.AdultMain;
import cn.edu.xmu.software.binarykang.log.LogError;
import cn.edu.xmu.software.net.util.DownLoadTool;
import cn.edu.xmu.software.net.util.FileTool;

public class AdultUpload extends Upload
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -370517844302501248L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		RealPath.filePath = RealPath.realPath + "data" + File.separator
				+ "adult" + File.separator + "浙江省成人报告.xlsx";
		RealPath.fileName = "成人示例.xlsx";
		DownLoadTool.donwLoad(response, ".xlsx");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		FileTool.init("adult");
		super.doPost(request, response);

		AdultMain.main(new String[]
		{});
		request.getSession().setAttribute(
				"error",
				LogError.read(RealPath.realPath + "log/ARDGS-ERROR.LOG",
						RealPath.timeFileName));
		DownLoadTool.downLoadDeal(request, response);

		// DownLoad.donwLoad(response);
	}

	@Override
	public void init() throws ServletException
	{
		super.init();
	}

}
