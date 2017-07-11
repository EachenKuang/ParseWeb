package cn.edu.xmu.software.binarykang.minor.sheet3.chapter06._6_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _6_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13岁阅读活动参与类型";
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
		tr("${sheet_3_6_2_1_join_read_activity_rate}",
				perf.format(1 - tableInfo.get(tableInfo.size() - 1).getRate()));
		DataMap tmp = tableInfo.get(tableInfo.size() - 1);
		tableInfo.remove(tmp);
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 7; i++)
		{
			tr("${sheet_3_6_2_1_join_read_activity_" + i + "}", tableInfo
					.get(i).getKey());
			tr("${sheet_3_6_2_1_join_read_activity_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tableInfo.add(tmp);
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
