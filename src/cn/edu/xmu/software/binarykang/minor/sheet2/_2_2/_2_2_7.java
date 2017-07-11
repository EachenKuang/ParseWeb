package cn.edu.xmu.software.binarykang.minor.sheet2._2_2;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_2_7 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁图书价格评价";
	private List<DataMap> tableInfo;

	public _2_2_7(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = new ArrayList<DataMap>();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		docx.replace(
				"${_2_2_7_price_expensive_rate}",
				perf.format(tableInfo.get(3).getRate()
						+ tableInfo.get(4).getRate()));
		docx.replace("${_2_2_7_price_fit_rate}",
				perf.format(tableInfo.get(2).getRate()));
		docx.replace("${_2_2_7_price_cheap_rate}",
				perf.format(tableInfo.get(1).getRate() + tableInfo.get(0).getRate()));
		docx.replace("${_2_2_7_price_no_idea_rate}",
				perf.format(tableInfo.get(tableInfo.size() - 1).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
