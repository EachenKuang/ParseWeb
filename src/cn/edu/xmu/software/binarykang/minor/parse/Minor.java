package cn.edu.xmu.software.binarykang.minor.parse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xmu.software.binarykang.log.Log;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.util.FileInfo;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class Minor
{
	public static void parse(Docx docx, Xlsx xlsx)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException
	{
		List<String> parseClassName = getParesClassNameConf();

		for (int i = 0; i < parseClassName.size(); ++i)
		{
			Class<?> minorProcessClass = Class.forName(parseClassName.get(i));
			Constructor<?> constructors[] = minorProcessClass.getConstructors();
			MinorBaseAction action = (MinorBaseAction) constructors[0]
					.newInstance(docx, xlsx);
			try
			{
				action.process();
			} catch (Exception e)
			{
				Log.addException(e);
			}
		}

	}

	private static List<String> getParesClassNameConf()
	{
		List<String> parseClassName = new ArrayList<String>();
		try
		{
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					FileInfo.INSTANCE.basePath + "minor.conf"));
			BufferedReader br = new BufferedReader(isr);
			String className = null;
			while ((className = br.readLine()) != null)
			{
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
}
