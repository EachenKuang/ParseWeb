package cn.edu.xmu.software.binarykang.minor.sheet2._4_4;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_4_2 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "4.4.2上网频率 第一段文字中的平均上网频次";
	private final static String TABLE_KEY_LOCAL = "14-17岁上网频率";
	private List<DataMap> tableLocal;
	private List<DataMap> tableAVG;

	public _4_4_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableAVG = MinorUtil.listMapFactory();
		tableLocal = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${_4_4_2_avg_frequency}", twof.format(tableAVG.get(0).getRate()));
		//tr("${_4_4_2_surf_net_0_rate}", perf.format(tableLocal.get(0).getRate()));
		//tr("${_4_4_2_surf_net_1_rate}", perf.format(tableLocal.get(1).getRate()));
		//tr("${_4_4_2_surf_net_2_rate}", perf.format(tableLocal.get(2).getRate()));
		//tr("${_4_4_2_surf_net_3_rate}", perf.format(tableLocal.get(3).getRate()));
		double lessOneWeek = 0;
		for (int i = 0; i < 4; ++i)
		{
			lessOneWeek += tableLocal.get(i).getRate();
			tr("${_4_4_2_surf_net_" + i + "_rate}",
					perf.format(tableLocal.get(i).getRate()));
		}
		/*
		MinorUtil.listSort(tableLocal);
		for (int i = 0; i < 4; ++i)
		{
			tr("${_4_4_2_surf_net_" + i + "}", tableLocal.get(i).getKey());
			tr("${_4_4_2_surf_net_" + i + "_rate}",
					perf.format(tableLocal.get(i).getRate()));
		}*/
		tr("${_4_4_2_surf_net_lest_one_rate}", perf.format(lessOneWeek));
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
		MinorUtil.changeChart(tableLocal);
	}

}
