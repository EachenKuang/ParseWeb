package cn.edu.xmu.software.binarykang.minor.parse;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

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

public class MinorMain
{
	public static void main(String[] args)
	{
		long start = System.nanoTime();

		Log.open(RealPath.realPath + "log" + File.separator + "ARDGS-MINOR.LOG");
		LogError.open(RealPath.realPath + "log" + File.separator
				+ "ARDGS-ERROR.LOG");
		LogError.setKey(RealPath.timeFileName);
		TimeCounter tc = TimeCounter.instance();
		tc.beginTotal();

		FileInfo fileInfo = FileInfo.INSTANCE; // ��ȡfileInfoʵ��
		Constant.init();

		try
		{
			/**
			 * ================================================= ��ѹdocx�ļ�
			 */
			tc.beginUnZip();
			fileInfo.setSaveName(RealPath.timeFileName.split("\\.")[0]); // ���յ�ǰϵͳʱ�������ļ�������
			fileInfo.basePath = RealPath.realPath + "data" + File.separator
					+ "minor" + File.separator;
			fileInfo.setReadPath(fileInfo.basePath + "input" + File.separator); // ��������ģ������λ�ã��趨��ȡ·��
			fileInfo.setSavePath(fileInfo.basePath + "parseFile"
					+ File.separator + fileInfo.getSaveName());

			ZipUtil.unZip(fileInfo.basePath + "minor_model.docx",
					fileInfo.getSavePath());

			long unZip = System.nanoTime();
			tc.endUnZip();
			System.out.println("Uzip:" + (unZip - start) / 1.0e9);

			/**
			 * ================================================== �滻�������
			 */
			tc.beginParse();
			OpenXML xml = new OpenXML(fileInfo.getSavePath() + File.separator
					+ "word" + File.separator + "document.xml");
			Document root = xml.open(); // ��document.xml�ļ�

			Xlsx xlsx = new Xlsx(fileInfo.getReadPath() + RealPath.timeFileName);

			Docx docx = new Docx(root, xlsx);
			parseMinor(root, docx, xlsx);

			xml.saveAndClose(); // �رղ�����document.xml�ļ�

			tc.endParse();
			long replaceCost = System.nanoTime();
			System.out.println("replaceCost:" + (replaceCost - unZip) / 1.0e9);
			/**
			 * =================================================== ��ѹ�����
			 */
			tc.beginZip();
			ZipUtil.zip(fileInfo.getSavePath() + ".docx",
					fileInfo.getSavePath());
			tc.endZip();
			long end = System.nanoTime();
			System.out.println("Zip:" + (end - replaceCost) / 1.0e9);
			System.out.println("Cost:" + (end - start) / 1.0e9);

			tc.endTotal();

			Log.setTime(tc);
			Log.save();
			LogError.save();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void parseMinor(Document root, Docx docx, Xlsx xlsx)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException
	{
		Minor.parse(docx, xlsx);
	}
}
