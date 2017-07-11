package cn.edu.xmu.software.binarykang.minor.sheet2._3_2;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_2_4 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁最常阅读的期刊类型";
	private List<DataMap> tableInfo;

	public _3_2_4(Docx docx, Xlsx xlsx)
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
		for (int i = 0; i < 12; i++)
		{
			tr("${_3_2_4_favor_" + i + "}", tableInfo.get(i).getKey());
			tr("${_3_2_4_favor_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
	}
//	${_3_2_4_favor_9}
//	${_3_2_4_favor_10}
//	${_3_2_4_favor_9_rate}
//	${_3_2_4_favor_10_rate}
	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
		Collections.reverse(tableInfo);
	}

}
