package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_8;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_8_7 extends MinorBaseAction
{
	private final static String TABLE_KEY_RESTRICt = "0-8岁家长购书时的不便之处";
	private List<DataMap> tableResrict;
	private final static String TABLE_KEY_AVG = "1.8.7 家长带孩子逛书店的次数 第一段文字中的平均每年逛书店次数";
	private final static String TABLE_KEY_AVG_OTHER = "1.8.7 家长带孩子逛书店的次数 第一段文字中的平均每年逛书店次数——全国";
	private final static String TABLE_KEY_LOCAL = "0-8岁儿童家长带孩子逛书店的频次（当地）";
	private final static String TABLE_KEY_OTHER = "0-8岁儿童家长带孩子逛书店的频次（全国）";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
	private List<DataMap> tableAVG;
	private List<DataMap> tableAVGOther;
	String other, local = MinorUtil.local;

	public _1_8_7(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableLocal = MinorUtil.listMapFactory();
		tableOther = MinorUtil.listMapFactory();
		tableAVG = MinorUtil.listMapFactory();
		tableAVGOther = MinorUtil.listMapFactory();
		tableResrict = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
				BEGIN_COL + 1, " ");
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY_AVG_OTHER, tableAVGOther, BEGIN_COL);
		
		MinorUtil.readData(xlsx, TABLE_KEY_RESTRICt, tableResrict,
				BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		DataMap tmp = tableResrict.get(tableResrict.size() - 2);
		tr("${sheet_4_1_8_7_buy_no_resrict}", perf.format(tmp.getRate()));
		tableResrict.remove(tmp);
		MinorUtil.listSort(tableResrict);
		for (int i = 0; i < 6; i++)
		{
			tr("${sheet_4_1_8_7_buy_resrict_" + i + "}", tableResrict.get(i)
					.getKey());
			tr("${sheet_4_1_8_7_buy_resrict_" + i + "_rate}",
					perf.format(tableResrict.get(i).getRate()));
		}
		tableResrict.add(tmp);
		
		
		tr("${sheet_4_1_8_7_go_book_shop_time_avg}",
				twof.format(tableAVG.get(0).getRate()));
		tr("${sheet_4_1_8_7_other}", other);
		tr("${sheet_4_1_8_7_go_book_shop_time_avg_other}",
				twof.format(tableAVGOther.get(0).getRate()));
		tr("${sheet_4_1_8_7_go_book_shop_time_avg_judge}", 
				tableAVG.get(0).getRate()- tableAVGOther.get(0).getRate() > 0 ? "高": "低");
		tr("${sheet_4_1_8_7_go_book_shop_time_avg_cut}",
				twof.format(Math.abs(tableAVG.get(0).getRate()- tableAVGOther.get(0).getRate())));
		
		
//		tr("${sheet_4_1_8_7_go_book_shop_never}", tableLocal.get(0).getKey());
//		tr("${sheet_4_1_8_7_go_book_shop_never_rate}",
//				perf.format(tableLocal.get(0).getRate()));
//		tr("${sheet_4_1_8_7_go_book_shop_never_rate_other}",
//				perf.format(MinorUtil.getByKey(tableLocal.get(0).getKey(),
//						tableOther).getRate()));
//		double neverCut = tableLocal.get(0).getRate()
//				- tableOther.get(0).getRate();
//		tr("${sheet_4_1_8_7_go_book_shop_never_judge}", neverCut > 0 ? "高"
//				: "低");
//		tr("${sheet_4_1_8_7_go_book_shop_never_cut}",
//				delLastChar(perf.format(Math.abs(neverCut))));

//		MinorUtil.listSort(tableLocal);
		tr("${sheet_4_1_8_7_go_book_shop_have_rate}",
				perf.format(1 - tableLocal.get(0).getRate()));
		tr("${sheet_4_1_8_7_go_book_shop_have_rate_other}",
				perf.format(1 - tableOther.get(0).getRate()));
		double shopCut = 
				 tableOther.get(0).getRate() - tableLocal.get(0).getRate();
		tr("${sheet_4_1_8_7_go_book_shop_have_rate_judge}", shopCut > 0 ? "高" : "低");
		tr("${sheet_4_1_8_7_go_book_shop_have_rate_cut}",
				delLastChar(perf.format(Math.abs(shopCut))));
		
		
		tr("${sheet_4_1_8_7_go_book_shop_half_year_rate}",
				perf.format(tableLocal.get(1).getRate()+tableLocal.get(2).getRate()));
		
		for (int i = 1; i < 5; i++)
		{
//			tr("${sheet_4_1_8_7_go_book_shop_" + i + "}", tableLocal.get(i)
//					.getKey());
			tr("${sheet_4_1_8_7_go_book_shop_" + i + "_rate}",
					perf.format(tableLocal.get(i).getRate()));
		}
		
	}

//	@Override
//	public void process()
//	{
//		readData();
//		chart();
//		replace();
//		trReplace();
//	};

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableResrict);
		Collections.reverse(tableLocal);
		Collections.reverse(tableOther);
		MinorUtil.changeChart(tableLocal, tableOther);
	}
}
