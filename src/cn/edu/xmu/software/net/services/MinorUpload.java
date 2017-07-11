package cn.edu.xmu.software.net.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.xmu.software.binarykang.log.LogError;
import cn.edu.xmu.software.binarykang.minor.parse.MinorMain;
import cn.edu.xmu.software.net.util.DownLoadTool;
import cn.edu.xmu.software.net.util.FileTool;

public class MinorUpload extends Upload
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -370517844302501248L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		RealPath.filePath = RealPath.realPath + "data/minor/浙江省未成年.xlsx";
		RealPath.fileName = "未成年示例.xlsx";
		DownLoadTool.donwLoad(response, ".xlsx");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		FileTool.init("minor");
		super.doPost(request, response);

		MinorMain.main(new String[]
		{});
		request.getSession().setAttribute(
				"error",
				LogError.read(RealPath.realPath + "log/ARDGS-ERROR.LOG",
						RealPath.timeFileName));
		DownLoadTool.downLoadDeal(request, response);
	}

	@Override
	public void init() throws ServletException
	{
		super.init();
	}

}
