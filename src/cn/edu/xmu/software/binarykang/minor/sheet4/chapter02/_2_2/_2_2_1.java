package cn.edu.xmu.software.binarykang.minor.sheet4.chapter02._2_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "0-8岁家长对儿童类期刊价格的评价";
	private List<DataMap> tableInfo;

	public _2_2_1(Docx docx, Xlsx xlsx)
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
		tr("${sheet_4_2_2_1_magazine_evaluate_fit}",
				perf.format(tableInfo.get(2).getRate()));
		tr("${sheet_4_2_2_1_magazine_evaluate_cheap}",
				perf.format(tableInfo.get(1).getRate()+tableInfo.get(0).getRate()));
		tr("${sheet_4_2_2_1_magazine_evaluate_expensive}",
				perf.format(tableInfo.get(3).getRate()+tableInfo.get(4).getRate()));
		tr("${sheet_4_2_2_1_magazine_evaluate_not_clear}",
				perf.format(tableInfo.get(tableInfo.size() - 1).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
