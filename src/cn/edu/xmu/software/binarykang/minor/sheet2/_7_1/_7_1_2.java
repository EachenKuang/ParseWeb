package cn.edu.xmu.software.binarykang.minor.sheet2._7_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _7_1_2 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁个人阅读情况的满意度";
	private List<DataMap> tableInfo;

	public _7_1_2(Docx docx, Xlsx xlsx)
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
		tr("${_7_1_2_person_general}",
				perf.format(tableInfo.get(2).getRate()));
		tr("${_7_1_2_person_satisfaction}",
				perf.format(tableInfo.get(0).getRate()
						+ tableInfo.get(1).getRate()));
		tr("${_7_1_2_person_not_satisfaction}",
				perf.format(tableInfo.get(3).getRate()
						+ tableInfo.get(4).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
