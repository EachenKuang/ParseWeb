package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_1;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_1_3 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13岁喜爱的图书类型";
	private List<DataMap> tableInfo;

	public _1_1_3(Docx docx, Xlsx xlsx)
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
		for (int i = 0; i < 8; i++)
		{
			tr("${sheet3_1_1_3_love_type_" + i + "}", tableInfo.get(i).getKey());
			tr("${sheet3_1_1_3_love_type_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}
}
