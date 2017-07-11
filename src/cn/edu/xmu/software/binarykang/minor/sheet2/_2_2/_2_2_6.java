package cn.edu.xmu.software.binarykang.minor.sheet2._2_2;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.parse.Minor;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_2_6 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁图书销售网点的分布密度";
	private final static String TABLE_KEY_AVG = "14-17岁购书点平均距离";
	private List<DataMap> tableInfo;
	private double tableAvg;

	public _2_2_6(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = new ArrayList<DataMap>();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		tableAvg = SingleValue.read(xlsx, TABLE_KEY_AVG, 1, 2);
	}

	@Override
	protected void replace()
	{
		docx.replace("${_2_2_6_avg_distance}", twof.format(tableAvg));
		double lessOneKm = 0, lessThreeKm = 0, lessHalfKm = 0, moreThreeKm = 0, noIdea = 0;
		lessOneKm = tableInfo.get(0).getRate() + tableInfo.get(1).getRate();
		//lessHalfKm = tableInfo.get(0).getRate();
		noIdea = tableInfo.get(tableInfo.size() - 1).getRate();
		for (int i = 0; i < tableInfo.size(); i++)
		{
			if (i < 4)
				lessThreeKm += tableInfo.get(i).getRate();
			else if (i < tableInfo.size() - 1)
				moreThreeKm += tableInfo.get(i).getRate();
		}
		docx.replace("${_2_2_6_one_km_rate}", perf.format(lessOneKm));
		docx.replace("${_2_2_6_three_km_rate}", perf.format(lessThreeKm));
		//docx.replace("${_2_2_6_half_km_rate}", perf.format(lessHalfKm));
		docx.replace("${_2_2_6_more_three_km_rate}", perf.format(moreThreeKm));
		docx.replace("${_2_2_6_no_idea_rate}", perf.format(noIdea));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
