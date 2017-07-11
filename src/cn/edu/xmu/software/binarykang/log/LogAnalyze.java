package cn.edu.xmu.software.binarykang.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析时间分析器，用来做统计分析用的
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-31
 * 
 */
public final class LogAnalyze
{
	private final String[] PREFIX =
	{ "UnZip", "Parse", "ZipIn", "Total" };
	private FileReader fr;
	private BufferedReader br;
	private List<List<String>> times;
	private List<Long> averageTimes;

	private LogAnalyze()
	{
		times = new ArrayList<List<String>>();
		for (int i = 0; i < PREFIX.length; i++)
			times.add(new ArrayList<String>());
		averageTimes = new ArrayList<Long>();
	}

	public static LogAnalyze instance()
	{
		return new LogAnalyze();
	}

	public void open(String path) throws FileNotFoundException
	{
		fr = new FileReader(new File(path));
		br = new BufferedReader(fr);
	}

	/**
	 * 从指定的日志中读取时间信息，并且做统计分析
	 */
	public void analyze() throws IOException
	{
		// 读取日志中的时间数据
		String line = null;
		while ((line = br.readLine()) != null)
		{
			for (int i = 0; i < PREFIX.length; i++)
			{
				if (line.startsWith(PREFIX[i]))
				{
					times.get(i).add(line.split(":")[1]);
					break;
				}
			}
		}
		// 分析日志中的时间数据
		for (int i = 0; i < PREFIX.length; i++)
		{
			List<String> list = times.get(i);
			int size = list.size();
			long totalTime = 0;
			for (int j = 0; j < size; j++)
			{
				totalTime += Double.valueOf(list.get(j));
			}
			averageTimes.add(totalTime / size);
		}
	}

	public void showResult()
	{
		DecimalFormat pf = new DecimalFormat("0.0%");
		System.out.println(String.format("There are %d parse times.", times
				.get(0).size()));
		System.out.println("Average UnZip Time:" + averageTimes.get(0)
				+"\tPercent:"
				+ pf.format(1.0 * averageTimes.get(0) / averageTimes.get(3)));
		System.out.println("Average Parse Time:" + averageTimes.get(1)
				+"\tPercent:"
				+ pf.format(1.0 * averageTimes.get(1) / averageTimes.get(3)));
		System.out.println("Average ZipIn Time:" + averageTimes.get(2)
				+"\tPercent:"
				+ pf.format(1.0 * averageTimes.get(2) / averageTimes.get(3)));
		System.out.println("Average Total Time:" + averageTimes.get(3));
	}

	public void close() throws IOException
	{
		br.close();
		fr.close();
	}
}
