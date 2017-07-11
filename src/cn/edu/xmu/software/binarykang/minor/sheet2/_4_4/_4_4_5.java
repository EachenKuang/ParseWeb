package cn.edu.xmu.software.binarykang.minor.sheet2._4_4;

import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_4_5 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "4.4.5网络在线阅读花费 第一段文字中平均花费在网络在线阅读的上的金额";
	private final static String TABLE_KEY_LOCAL = "14-17岁网络在线阅读的花费";
	private List<DataMap> tableLocal;
	private double tableAVG;

	public _4_4_5(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		//tableAVG = MinorUtil.listMapFactory();
		tableLocal = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		//MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
		tableAVG = SingleValue.read(xlsx, TABLE_KEY_AVG, 3, 1);
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${_4_4_5_net_read_pay_avg_rate}",
				twof.format(tableAVG));
		tr("${_4_4_5_net_read_pay_rate}", perf.format(1 - tableLocal.get(
				tableLocal.size() - 1).getRate()));
		//tr("${_4_4_5_net_read_never_pay_rate}",
		//		perf.format(tableLocal.get(tableLocal.size() - 1).getRate()));
		tr("${_4_4_5_net_read_less_ten}",
				perf.format(tableLocal.get(0).getRate()));
		//tr("${_4_4_5_net_read_ten_to_twenty}",
		//		perf.format(tableLocal.get(1).getRate()));
		tr("${_4_4_5_net_read_ten_to_fifty}", 
				perf.format(tableLocal.get(1).getRate()+tableLocal.get(2).getRate()+tableLocal.get(3).getRate()));
		tr("${_4_4_5_net_read_higher_fifty}", 
				perf.format(tableLocal.get(4).getRate()+tableLocal.get(5).getRate()+tableLocal.get(6).getRate()));
		/*
		double twentyFifty = 0;
		for (int i = 2; i < 4; i++)
		{
			twentyFifty += tableLocal.get(i).getRate();
		}
		tr("${_4_4_5_net_read_twenty_to_fifty}", perf.format(twentyFifty));
		double moreHundred = 0;
		for (int i = 5; i < 7; i++)
		{
			moreHundred += tableLocal.get(i).getRate();
		}
		tr("${_4_4_5_net_read_more_hundred}", perf.format(moreHundred));
		*/
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
	}
}
