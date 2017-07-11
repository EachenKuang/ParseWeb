package cn.edu.xmu.software.binarykang.minor.sheet2._7_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _7_3_2 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "14-17岁使用学校图书馆的频次均值";
	private final static String TABLE_KEY = "14-17岁使用学校图书馆的频次";
	private final static String TABLE_KEY_SATISF = "14-17岁对学校图书馆的满意度";
	private List<DataMap> tableAvg;
	private List<DataMap> tableInfo;
	private List<DataMap> tableDegree;

	public _7_3_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableAvg = MinorUtil.listMapFactory();
		tableInfo = MinorUtil.listMapFactory();
		tableDegree = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAvg, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_SATISF, tableDegree, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${_6_3_2_library_month_times}", twof.format(tableAvg.get(0).getRate()));
		tr("${_6_3_2_library_have_gone}", 
				perf.format(1 - tableInfo.get(tableInfo.size() - 1).getRate()));
		tr("${_6_3_2_library_everyday}", perf.format(tableInfo.get(0).getRate()));
		tr("${_6_3_2_library_4_to_6_everyweek}", perf.format(tableInfo.get(1).getRate()));
		tr("${_6_3_2_library_1_to_3_everyweek}", perf.format(tableInfo.get(2).getRate()));
		tr("${_6_3_2_library_higher_one_time_everyweek}", 
				perf.format(tableInfo.get(0).getRate()
						+ tableInfo.get(1).getRate()
						+ tableInfo.get(2).getRate()));
		tr("${_6_3_2_library_1_to_3_everymonth}", perf.format(tableInfo.get(3).getRate()));
		tr("${_6_3_2_library_less_1_everymonth}", perf.format(tableInfo.get(4).getRate()));
		double moreOne = 0;
		for (int i = 0; i < 4; i++)
		{
			moreOne += tableInfo.get(i).getRate();
		}
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 2; i++)
		{
			tr("${_7_3_2_go_library_time_" + i + "}", tableInfo.get(i).getKey());
			tr("${_7_3_2_go_library_time_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tr("${_7_3_2_go_library_time_more_one_rate}", perf.format(moreOne));

//		tr("${_7_3_2_use_library_satisfaction}",
//				perf.format(MinorUtil.getByKey("非常满意", tableDegree).getRate()
//						+ MinorUtil.getByKey("比较满意", tableDegree).getRate()));
//		tr("${_7_3_2_use_library_not_satisfaction}",
//				perf.format(MinorUtil.getByKey("比较不满意", tableDegree).getRate()
//						+ MinorUtil.getByKey("非常不满意", tableDegree).getRate()));
		tr("${_7_3_2_use_library_satisfaction}",
				perf.format(MinorUtil.getByKey("满意", tableDegree).getRate()));
		tr("${_7_3_2_use_library_not_satisfaction}",
				perf.format(MinorUtil.getByKey("不满意", tableDegree).getRate()));
		tr("${_7_3_2_use_library_general}",
				perf.format(MinorUtil.getByKey("一般", tableDegree).getRate()));
		tr("${_7_3_2_use_dont_know}", 
				perf.format(MinorUtil.getByKey("没去过，不知道", tableDegree).getRate()));
	}

	@Override
	public void process()
	{
		readData();
		chart();
		replace();
		trReplace();
	};

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
		MinorUtil.changeChart(tableDegree);
	}
}
