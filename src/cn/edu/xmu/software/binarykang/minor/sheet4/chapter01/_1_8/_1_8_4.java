package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_8;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_8_4 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "1.8.4 购书网点的覆盖密度 第一段文字中的平均多少公里可购书";
	private final static String TABLE_KEY = "0-8岁图书销售网点的分布密度";
//	private final static String TABLE_KEY_RESTRICt = "0-8岁家长购书时的不便之处";
	private List<DataMap> tableInfo;
	private List<DataMap> tableAVG;
//	private List<DataMap> tableResrict;

	public _1_8_4(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
		tableAVG = MinorUtil.listMapFactory();
//		tableResrict = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
//		MinorUtil.readData(xlsx, TABLE_KEY_RESTRICt, tableResrict,
//				BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_4_1_8_4_buy_range_avg}",
				twof.format(tableAVG.get(0).getRate()));
		double notClear = tableInfo.get(tableInfo.size() - 1).getRate();
		double lessThree = 0;
		for (int i = 0; i < 4; i++)
		{
			lessThree += tableInfo.get(i).getRate();
		}
		tr("${sheet_4_1_8_4_buy_range_less_three_km}", perf.format(lessThree));
		tr("${sheet_4_1_8_4_buy_range_more_three_km}",
				perf.format(1 - notClear - lessThree));

		tr("${sheet_4_1_8_4_buy_range_more_ten_km}", 
				perf.format(tableInfo.get(tableInfo.size() - 2).getRate()));
		tr("${sheet_4_1_8_4_buy_range_unclear}", 
				perf.format(notClear));
//		DataMap tmp = tableResrict.get(tableResrict.size() - 2);
//		tr("${sheet_4_1_8_4_buy_no_resrict}", perf.format(tmp.getRate()));
//		tableResrict.remove(tmp);
//		MinorUtil.listSort(tableResrict);
//		for (int i = 0; i < 2; i++)
//		{
//			tr("${sheet_4_1_8_4_buy_resrict_" + i + "}", tableResrict.get(i)
//					.getKey());
//			tr("${sheet_4_1_8_4_buy_resrict_" + i + "_rate}",
//					perf.format(tableResrict.get(i).getRate()));
//		}
//		tableResrict.add(tmp);
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);

//		MinorUtil.changeChart(tableResrict);
	}
}
