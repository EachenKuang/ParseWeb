package cn.edu.xmu.software.binarykang.minor.sheet2._3_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_2_5 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁期刊价格承受力";
	private final static String TABLE_KEY_AVG = "3.2.5期刊价格承受力 第一段文字中的可接受的期刊平均价格";
	private List<DataMap> tableInfo;
	private List<DataMap> tableAVG;

	public _3_2_5(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = MinorUtil.listMapFactory();
		tableAVG = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
	}

	@Override
	protected void replace()
	{
		double less19 = 0;
		for (int i = 0; i < 4; i++)
		{
			less19 += tableInfo.get(i).getRate();
			//tr("${_3_2_5_afford_" + i + "}", tableInfo.get(i).getKey());
			//tr("${_3_2_5_afford_" + i + "_rate}",
			//		perf.format(tableInfo.get(i).getRate()));
		}
		tr("${_3_2_5_afford_avg_rate}", twof.format(tableAVG.get(0).getRate()));
		tr("${_3_2_5_afford_less_19_rate}", perf.format(less19));
		tr("${_3_2_5_afford_less_3_rate}", perf.format(tableInfo.get(0).getRate()));
		tr("${_3_2_5_afford_3_6_rate}", perf.format(tableInfo.get(1).getRate()));
		tr("${_3_2_5_afford_6_9_rate}", perf.format(tableInfo.get(2).getRate()));
		tr("${_3_2_5_afford_9_19_rate}", perf.format(tableInfo.get(3).getRate()));
		tr("${_3_2_5_afford_higher_19_rate}", perf.format(tableInfo.get(4).getRate()));
		MinorUtil.listSort(tableInfo);
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
	}

}
