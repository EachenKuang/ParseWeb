package cn.edu.xmu.software.binarykang.minor.sheet2._2_1;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_1_3 extends MinorBaseAction
{
	private final static String TABLE_KEY_RATE = "14-17岁阅读课外书的目的";

	private List<DataMap> tableData;

	public _2_1_3(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableData = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_RATE, tableData, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		MinorUtil.listSort(tableData);

		for (int i = 0; i < 6; i++)
		{
			docx.replace("${read_goal_" + i + "}", tableData.get(i).getKey());
			docx.replace("${read_goal_" + i + "_rate}",
					perf.format(tableData.get(i).getRate()));
		}
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableData);
		MinorUtil.changeChart(tableData);
	}
}
