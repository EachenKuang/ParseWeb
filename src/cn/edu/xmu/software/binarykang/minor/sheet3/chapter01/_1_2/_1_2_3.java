package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_2;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_2_3 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "1.2.3.1 家长对图书的价格承受力 第一段文字中的平均价格承受力";
	private final static String TABLE_KEY_AFFORD = "9-13岁图书价格的承受力";
	private final static String TABLE_KEY_EVALUTE = "9-13岁图书价格的评价";
	private List<DataMap> tableAVG;
	private List<DataMap> tableAfford;
	private List<DataMap> tableEvalute;

	public _1_2_3(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableAVG = MinorUtil.listMapFactory();
		tableAfford = MinorUtil.listMapFactory();
		tableEvalute = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY_AFFORD, tableAfford, BEGIN_COL + 1);
		MinorUtil
				.readData(xlsx, TABLE_KEY_EVALUTE, tableEvalute, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_3_1_2_3_afford_price_avg}",
				twof.format(tableAVG.get(0).getRate()));
		double lessEight = 0;
		double eightThirty = 0;
		for(int i = 0; i < 2;i++)
		{
			lessEight += tableAfford.get(i).getRate();
		}
		tr("${sheet_3_1_2_3_afford_price_less_eight}",perf.format(lessEight));
		for(int i = 2; i < 5;i++)
		{
			tr("${sheet_3_1_2_3_afford_price_"+i+"}",perf.format(tableAfford.get(i).getRate()));
			eightThirty += tableAfford.get(i).getRate();
		}
		tr("${sheet_3_1_2_3_afford_price_eight_thirty}",perf.format(eightThirty));
		
		tr("${sheet_3_1_2_3_afford_price_more_thirty}",perf.format(tableAfford.get(tableAfford.size()-2).getRate()));
		tr("${sheet_3_1_2_3_afford_price_buy}",perf.format(tableAfford.get(tableAfford.size()-1).getRate()));
//		DataMap last = tableAfford.get(tableAfford.size() - 1);
//		tableAfford.remove(last);
//		MinorUtil.listSort(tableAfford);
//		for (int i = 0; i < 2; i++)
//		{
//			tr("${sheet_3_1_2_3_afford_price_" + i + "}", tableAfford.get(i)
//					.getKey());
//			tr("${sheet_3_1_2_3_afford_price_" + i + "_rate}",
//					perf.format(tableAfford.get(i).getRate()));
//		}
//		tr("${sheet_3_1_2_3_afford_price_last_rate}",
//				perf.format(last.getRate()));
//
		tr("${sheet_3_1_2_3_evalute_price_very_high}",
				perf.format(tableEvalute.get(0).getRate()));
		tr("${sheet_3_1_2_3_evalute_price_little_high}",
				perf.format(tableEvalute.get(1).getRate()));
		tr("${sheet_3_1_2_3_evalute_price_high}",
				perf.format(tableEvalute.get(0).getRate()
						+ tableEvalute.get(1).getRate()));
		tr("${sheet_3_1_2_3_evalute_price_general}",
				perf.format(tableEvalute.get(2).getRate()));
		tr("${sheet_3_1_2_3_evalute_price_low}",
				perf.format(tableEvalute.get(3).getRate()
						+ tableEvalute.get(4).getRate()));
		tr("${sheet_3_1_2_3_evalute_price_unclear}",
				perf.format(tableEvalute.get(5).getRate()));
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
		Collections.reverse(tableAfford);
		Collections.reverse(tableEvalute);
		MinorUtil.changeChart(tableAfford);
		MinorUtil.changeChart(tableEvalute);
		Collections.reverse(tableAfford);
		Collections.reverse(tableEvalute);
	}

}
