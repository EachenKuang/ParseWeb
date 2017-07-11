package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_1_6 extends MinorBaseAction
{

	private final static String TABLE_KEY = "9-13岁阅读地点";
//	private final static String TABLE_KEY_OTHER = "9-13岁阅读地点（全国）";
	private List<DataMap> tableInfo;
//	private List<DataMap> tableOther;

	public _1_1_6(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
//		tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
//		MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 2; i++)
		{
			tr("${sheet3_1_1_6_reade_place_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet3_1_1_6_reade_place_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));

//			double other = MinorUtil.getByKey(tableInfo.get(i).getKey(),
//					tableOther).getRate();
//			tr("${sheet3_1_1_6_reade_place_" + i + "_rate_other}",
//					perf.format(other));
//			tr("${sheet3_1_1_6_reade_place_1_rate_judge}", tableInfo.get(i)
//					.getRate() > other ? "高" : "低");
		}
		tr("${sheet3_1_1_6_reade_place_2_rate}",            //new add
				perf.format(tableInfo.get(2).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);  // update
	}

}
