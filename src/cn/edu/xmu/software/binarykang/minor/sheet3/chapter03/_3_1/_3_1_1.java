package cn.edu.xmu.software.binarykang.minor.sheet3.chapter03._3_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "9-13岁上网途径";
	private final static String TABLE_KEY_OTHER = "3.1上网率及上网途径 第一段文字中的全国的上网率";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
	private String other;

	public _3_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
		tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
				BEGIN_COL + 1, " ");
	}

	@Override
	protected void replace()
	{
		tr("${sheet_3_3_1_1_other}", other);
		double onNetRate = 1 - tableLocal.get(tableLocal.size() - 1).getRate();
		double onNetRateOther = 1 - tableOther.get(tableOther.size() - 1)
				.getRate();
		double localCutOther = onNetRate - onNetRateOther;

		tr("${sheet_3_3_1_1_on_net_rate_local}", perf.format(onNetRate));
		tr("${sheet_3_3_1_1_on_net_rate_other}", perf.format(onNetRateOther));
		tr("${sheet_3_3_1_1_on_net_rate_cut}",
				delLastChar(perf.format(Math.abs(localCutOther))));
		tr("${sheet_3_3_1_1_on_net_rate_judge}", localCutOther > 0 ? "高" : "低");
		
		DataMap noNetRate = tableLocal.get(tableLocal.size() - 1);
		tableLocal.remove(tableLocal.size() - 1);
		
		MinorUtil.listSort(tableLocal);
		tableLocal.add(noNetRate);
		for (int i = 0; i < 3; i++)
		{
			tr("${sheet_3_3_1_1_on_net_way_" + i + "_rate}",
					perf.format(tableLocal.get(i).getRate()));
			tr("${sheet_3_3_1_1_on_net_way_" + i + "}", tableLocal.get(i)
					.getKey());
		}
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
	}

}
