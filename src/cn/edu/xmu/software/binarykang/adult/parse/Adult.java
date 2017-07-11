package cn.edu.xmu.software.binarykang.adult.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.log.Log;
import cn.edu.xmu.software.binarykang.util.FileInfo;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.OpenXML;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public final class Adult
{
	/**
	 * 通过反射，调用配置文件里面未注释的类
	 * 
	 * @param docx
	 *            需要读取的模版word
	 * @param xlsx
	 *            Excel数据源
	 */
	public static void parse(Docx docx, Xlsx xlsx)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException
	{
		List<String> parseClassName = getParseClassFromConf();

		for (int i = 0; i < parseClassName.size(); ++i)
		{
			Class<?> adultProcessClass = Class.forName(parseClassName.get(i));
			Constructor<?> constructors[] = adultProcessClass.getConstructors();
			AdultBaseAction action;
			action = (AdultBaseAction) constructors[0].newInstance(docx, xlsx);
			try
			{
				action.process();
			} catch (Exception e)
			{
				Log.addException(e);
			}
		}

		modifyCoreXML(docx);// 修改core.xml的配置信息
	}

	/**
	 * 从配置文件中读取需要反射调用的类
	 * 
	 * @return 需要调用的类的字符串列表
	 */
	private static List<String> getParseClassFromConf()
	{
		List<String> parseClassName = ListFactory.getStringList();
		try
		{

			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					FileInfo.INSTANCE.basePath + "adult.conf"));
			BufferedReader br = new BufferedReader(isr);
			String className = null;
			while ((className = br.readLine()) != null)
			{
				if (!className.startsWith("#"))
					parseClassName.add(className);
			}
			br.close();
			isr.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return parseClassName;
	}

	/**
	 * 修改生成的Docx文件的创建者和修改者，以及创建时间和修改时间
	 * 
	 * @param docx
	 *            需要修改的Docx文件
	 */
	private static void modifyCoreXML(Docx docx)
	{
		final String author = "ARDGS";
		SimpleDateFormat dayFormat = new SimpleDateFormat("YYYY-MM-dd");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		Date currentDate = new Date();
		final String date = dayFormat.format(currentDate) + "T"
				+ hourFormat.format(currentDate) + "Z";

		OpenXML coreXML = new OpenXML(FileInfo.INSTANCE.getSavePath()
				+ File.separator + "docProps" + File.separator + "core.xml");
		try
		{
			Document document = coreXML.open();
			Element root = document.getDocumentElement();
			Node creator = root.getElementsByTagName("dc:creator").item(0);
			creator.getFirstChild().setNodeValue(author);
			Node lastModifiedBy = root
					.getElementsByTagName("cp:lastModifiedBy").item(0);
			lastModifiedBy.getFirstChild().setNodeValue(author);
			Node createdTime = root.getElementsByTagName("dcterms:created")
					.item(0);
			createdTime.getFirstChild().setNodeValue(date);
			Node modifiedTime = root.getElementsByTagName("dcterms:modified")
					.item(0);
			modifiedTime.getFirstChild().setNodeValue(date);
			coreXML.saveAndClose();
		} catch (Exception e)
		{
			System.err.println("修改配置文件出错，出错信息：" + e.getMessage());
		}

	}
}
