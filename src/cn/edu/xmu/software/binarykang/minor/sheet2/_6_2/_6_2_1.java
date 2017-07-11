package cn.edu.xmu.software.binarykang.minor.sheet2._6_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _6_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁最喜爱的动漫题材";
	private List<DataMap> tableInfo;

	public _6_2_1(Docx docx, Xlsx xlsx)
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
		for (int i = 0; i < 3; i++)
		{
			tr("${_6_2_1_favor_cartoon_" + i + "}", tableInfo.get(i).getKey());
			tr("${_6_2_1_favor_cartoon_" + i + "_rate}", perf.format(tableInfo.get(i).getRate()));
		}
		//tr("${_6_2_1_favor_cartoon_0_rate}",
		//		perf.format(tableInfo.get(0).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
