package cn.edu.xmu.software.binarykang.minor.sheet2._4_4;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_4_3 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁上网从事的主要活动";
	private List<DataMap> tableInfo;

	public _4_4_3(Docx docx, Xlsx xlsx)
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
		for (int i = 0; i < 7; i++)
		{
			tr("${_4_4_3_net_active_" + i + "}", tableInfo.get(i).getKey());
			tr("${_4_4_3_net_active_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		double first7 = tableInfo.get(6).getRate();
		tr("${_4_4_3_net_active_first7_bound}", perf.format((int)(first7*10)/10.0));
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}
}
