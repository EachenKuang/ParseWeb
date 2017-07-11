package cn.edu.xmu.software.binarykang.minor.sheet2._2_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "14-17岁阅读重要性认知（当地）";
	//private final static String TABLE_KEY_OTHER = "14-17岁阅读重要性认知（全国）";

	private List<DataMap> tableLocal;
	//private List<DataMap> tableOther;
	private String other;

	public _2_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();

		//tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		//other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
		//		BEGIN_COL + 1, " ");
	}

	@Override
	protected void replace()
	{
		double importantCity = tableLocal.get(0).getRate()
				+ tableLocal.get(1).getRate();
		//double importantOther = tableOther.get(0).getRate()
		//		+ tableOther.get(1).getRate();
		//double cityCutOther = importantCity - importantOther;
		//String judge = cityCutOther > 0 ? "高" : "低";

		docx.replace("${important_rate_city}", perf.format(importantCity));
		//docx.replace("${important_rate_other}", perf.format(importantOther));
		//docx.replace("${other}", other);
		//docx.replace("${judge}", judge);
		//docx.replace("${city_other}",
		//		delLastChar(perf.format(Math.abs(cityCutOther))));
		docx.replace("${ordinary_rate_city}",
				perf.format(tableLocal.get(2).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
	}
}
