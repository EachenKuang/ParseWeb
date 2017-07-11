package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_2;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_2_2 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "1.2.2 图书购买金额 第一段文字中的家长为孩子购书平均花费";
	private final static String TABLE_KEY = "9-13岁图书购买金额";
	private final static String TABLE_KEY_TYPE = "1.2.2 图书购买金额 第二段文字中的城乡图书购买金额";
	private List<DataMap> tableAVG;
	private List<DataMap> tableInfo;
	private List<DataMap> tableType;

	public _1_2_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableAVG = MinorUtil.listMapFactory();
		tableInfo = MinorUtil.listMapFactory();
		tableType = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		int beginRow = xlsx.getRowByKey(TABLE_KEY_TYPE) + 1;
		String key = xlsx.getContent(xlsx.getRow(beginRow), BEGIN_COL + 1);
		Double rate = new Double(xlsx.getContent(xlsx.getRow(beginRow + 2),
				BEGIN_COL + 1));
		tableType.add(new DataMap(key, rate));

		key = xlsx.getContent(xlsx.getRow(beginRow), BEGIN_COL + 2);
		rate = new Double(xlsx.getContent(xlsx.getRow(beginRow + 2),
				BEGIN_COL + 2));
		tableType.add(new DataMap(key, rate));
	}

	@Override
	protected void replace()
	{
		tr("${sheet_3_1_2_2_buy_pay_avg}",
				twof.format(tableAVG.get(0).getRate()));
//		DataMap fiftyToHundred = tableInfo
//				.get(tableInfo.size() - 2);
//		DataMap moreHundred = tableInfo
//				.get(tableInfo.size() - 1);
//		double moreFifty = fiftyToHundred.getRate() + moreHundred.getRate();
//		DataMap max = fiftyToHundred.getRate() > moreHundred
//				.getRate() ? fiftyToHundred : moreHundred;
//		DataMap min = fiftyToHundred.getRate() < moreHundred
//				.getRate() ? fiftyToHundred : moreHundred;
//		tr("${sheet_3_1_2_2_buy_pay_more_fifty_rate}", perf.format(moreFifty));
//		tr("${sheet_3_1_2_2_buy_pay_more_fifty_most}", max.getKey());
//		tr("${sheet_3_1_2_2_buy_pay_more_fifty_most_rate}",
//				perf.format(max.getRate()));
//		tr("${sheet_3_1_2_2_buy_pay_more_fifty_second}", min.getKey());
//		tr("${sheet_3_1_2_2_buy_pay_more_fifty_second_rate}",
//				perf.format(min.getRate()));
		double lessFifty = 0;
		for(int i=0;i<5;i++)
		{
			lessFifty += tableInfo.get(i).getRate();
		}
		tr("${sheet_3_1_2_2_buy_pay_less_fifty}", perf.format(lessFifty));
		tr("${sheet_3_1_2_2_buy_pay_fifty_hundred}", perf.format(tableInfo.get(tableInfo.size() - 2).getRate()));
		tr("${sheet_3_1_2_2_buy_pay_more_hundred}", perf.format(tableInfo.get(tableInfo.size() - 1).getRate()));
		double cityRate = tableType.get(0).getRate();
		double villageRate = tableType.get(1).getRate();
		double cut = cityRate - villageRate;

		tr("${sheet_3_1_2_2_buy_pay_city_rate}", twof.format(cityRate));
		tr("${sheet_3_1_2_2_buy_pay_village_rate}", twof.format(villageRate));
		tr("${sheet_3_1_2_2_buy_pay_judge}", cut > 0 ? "多" : "少");
		tr("${sheet_3_1_2_2_buy_pay_cut_rate}", twof.format(Math.abs(cut)));
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}

}
