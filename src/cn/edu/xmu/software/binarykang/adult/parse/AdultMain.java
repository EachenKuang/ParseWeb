package cn.edu.xmu.software.binarykang.adult.parse;

import java.io.File;

import org.w3c.dom.Document;

import cn.edu.xmu.software.binarykang.log.Log;
import cn.edu.xmu.software.binarykang.log.LogError;
import cn.edu.xmu.software.binarykang.log.TimeCounter;
import cn.edu.xmu.software.binarykang.util.FileInfo;
import cn.edu.xmu.software.binarykang.util.ZipUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.OpenXML;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;
import cn.edu.xmu.software.net.services.RealPath;

public final class AdultMain
{

	public static void main(String[] args)
	{
		Log.open(RealPath.realPath + "log/ARDGS-ADULT.LOG");
		LogError.open(RealPath.realPath + "log/ARDGS-ERROR.LOG");
		LogError.setKey(RealPath.timeFileName);
		TimeCounter tc = TimeCounter.instance();
		tc.beginTotal();

		FileInfo fileInfo = FileInfo.INSTANCE; // 获取fileInfo实例
		DocxHelper.init();
		try
		{
			/**
			 * ================================================= 解压docx文件
			 */
			tc.beginUnZip();

			fileInfo.setSaveName(RealPath.timeFileName.split("\\.")[0]); // 按照当前系统时间设置文件的名称
			fileInfo.basePath = RealPath.realPath + "data" + File.separator
					+ "adult" + File.separator;
			fileInfo.setReadPath(fileInfo.basePath + "input" + File.separator); // 按照需求（模块所在位置）设定读取路径
			fileInfo.setSavePath(fileInfo.basePath + "parseFile"
					+ File.separator + fileInfo.getSaveName());

			ZipUtil.unZip(fileInfo.basePath + "adult.docx",
					fileInfo.getSavePath());

			tc.endUnZip();
			System.out.println("zip time:" + tc.getUnZipTime());

			/**
			 * =============================================== 替换操作入口
			 */
			tc.beginParse();

			OpenXML xml = new OpenXML(fileInfo.getSavePath() + File.separator
					+ "word" + File.separator + "document.xml");
			Document root = xml.open(); // 打开document.xml文件

			Xlsx xlsx = new Xlsx(fileInfo.getReadPath() + RealPath.timeFileName);
			Docx docx = new Docx(root, xlsx);
			parseAdult(root, docx, xlsx);

			xml.saveAndClose(); // 关闭并保存document.xml文件

			tc.endParse();
			System.out.println("parse time:" + tc.getParseTime());

			/**
			 * =================================================== 重压缩入口
			 */
			tc.beginZip();

			ZipUtil.zip(fileInfo.getSavePath() + ".docx",
					fileInfo.getSavePath());

			tc.endZip();
			System.out.println("zip time : " + tc.getZipTime());

			tc.endTotal();
			System.out.println("total :" + tc.getTotalTime());

			Log.setTime(tc);
			Log.save();
			LogError.save();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void parseAdult(Document root, Docx docx, Xlsx xlsx)
			throws Exception
	{
		Adult.parse(docx, xlsx);
	}

}
