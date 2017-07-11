package cn.edu.xmu.software.binarykang.minor.sheet4.chapter03._3_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "0-8岁家长对儿童类音像电子出版物的价格评价";
	private List<DataMap> tableInfo;

	public _3_2_1(Docx docx, Xlsx xlsx)
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
		tr("${sheet_4_3_2_1_radio_evaluate_cheap}",
				perf.format(tableInfo.get(0).getRate()
						+ tableInfo.get(1).getRate()));
		tr("${sheet_4_3_2_1_radio_evaluate_fit}",
				perf.format(tableInfo.get(2).getRate()));
		tr("${sheet_4_3_2_1_radio_evaluate_expensive}",
				perf.format(tableInfo.get(3).getRate()
						+ tableInfo.get(4).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
