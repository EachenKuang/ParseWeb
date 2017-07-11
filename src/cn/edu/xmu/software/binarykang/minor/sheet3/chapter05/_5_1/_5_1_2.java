package cn.edu.xmu.software.binarykang.minor.sheet3.chapter05._5_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _5_1_2 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13ËêÏ²°®µÄ¶¯ÂþÌâ²Ä";
	private List<DataMap> tableInfo;

	public _5_1_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 4; i++)
		{
			tr("${sheet_3_5_1_2_favor_cartoon_theme_" + i + "}",
					tableInfo.get(i).getKey());
			tr("${sheet_3_5_1_2_favor_cartoon_theme_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		
		for (int i = 8; i>5 ; i--)
		{
			tr("${sheet_3_5_1_2_favor_cartoon_theme_" + i + "}",
					tableInfo.get(i).getKey());
			tr("${sheet_3_5_1_2_favor_cartoon_theme_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
