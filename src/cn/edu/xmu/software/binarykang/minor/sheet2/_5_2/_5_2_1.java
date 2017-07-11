package cn.edu.xmu.software.binarykang.minor.sheet2._5_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _5_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "14-17岁音像电子出版物的价格评价";
	private List<DataMap> tableLocal;

	public _5_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		double fit = MinorUtil.getByKey("合适", tableLocal).getRate();
		double cheap = MinorUtil.getByKey("比较便宜", tableLocal).getRate();
		tr("${_5_2_1_evaluate_fit_rate}", perf.format(fit));
		tr("${_5_2_1_evaluate_little_cheap_rate}", perf.format(cheap));
		tr("${_5_2_1_evaluate_two_part_rate}", perf.format(fit + cheap));
		tr("${_5_2_1_evaluate_little_expensive_rate}",
				perf.format(MinorUtil.getByKey("比较贵", tableLocal).getRate()));

	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
	}

}
