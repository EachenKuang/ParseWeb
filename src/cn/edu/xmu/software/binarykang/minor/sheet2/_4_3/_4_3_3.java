package cn.edu.xmu.software.binarykang.minor.sheet2._4_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_3_3 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "4.3.3手机阅读花费 第一段文字中的一年的平均阅读总花费";
	private final static String TABLE_KEY_LOCAL = "14-17岁手机阅读总花费情况";
	private List<DataMap> tableLocal;
	private List<DataMap> tableAVG;

	public _4_3_3(Docx docx, Xlsx xlsx)
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
		tr("${_4_3_3_avg_cost}", twof.format(tableAVG.get(0).getRate()));
		tr("${_4_3_3_cost_for_read}", perf.format(1 - tableLocal.get(
				tableLocal.size() - 1).getRate()));
		tr("${_4_3_3_cost_less_20}", 
				perf.format(tableLocal.get(0).getRate()+tableLocal.get(1).getRate()));
		tr("${_4_3_3_cost_20_100}", 
				perf.format(tableLocal.get(2).getRate()+tableLocal.get(3).getRate()+tableLocal.get(4).getRate()));
		double lessFifty = 0;
		for (int i = 0; i < 4; i++)
		{
			lessFifty += tableLocal.get(i).getRate();
		}
		tr("${_4_3_3_cost_less_fifty}", perf.format(lessFifty));
		tr("${_4_3_3_cost_fifty_hundred}",
				perf.format(tableLocal.get(4).getRate()));
		double moreHundred = tableLocal.get(5).getRate() + tableLocal.get(6).getRate();
		tr("${_4_3_3_cost_more_hundred}", perf.format(moreHundred));
		tr("${_4_3_3_cost_never}",
				perf.format(tableLocal.get(tableLocal.size() - 1).getRate()));

	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
	}

}
