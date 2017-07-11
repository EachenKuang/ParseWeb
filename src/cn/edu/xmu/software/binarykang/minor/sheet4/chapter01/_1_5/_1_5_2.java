package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_5;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_5_2 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "1.5.2 陪读时长 第一段文字中的家长平均每天花费多少分钟陪孩子阅读（当地）";
	private final static String TABLE_KEY_OTHER = "1.5.2 陪读时长 第一段文字中的家长平均每天花费多少分钟陪孩子阅读（全国）";
	private final static String TABLE_KEY = "0-8岁家长陪护儿童阅读的时长";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
	private List<DataMap> tableInfo;

	String other, local;

	public _1_5_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
		tableOther = MinorUtil.listMapFactory();
		tableInfo = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		local = MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal,
				BEGIN_COL, " ");
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
				BEGIN_COL, " ");
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_4_1_5_2_other}", other);

		tr("${sheet_4_1_5_2_read_companion_last_avg}",
				twof.format(tableLocal.get(0).getRate()));
		tr("${sheet_4_1_5_2_read_companion_last_avg_other}",
				twof.format(tableOther.get(0).getRate()));

		double cut = tableLocal.get(0).getRate() - tableOther.get(0).getRate();

		tr("${sheet_4_1_5_2_read_companion_last_avg_judge}", cut > 0 ? "多"
				: "少");
		tr("${sheet_4_1_5_2_read_companion_last_avg_cut}",
				twof.format(Math.abs(cut)));
//
//		double lessTen = tableInfo.get(0).getRate()
//				+ tableInfo.get(1).getRate() + tableInfo.get(2).getRate();
		tr("${sheet_4_1_5_2_read_companion_last_5_10_rate}",
				perf.format(tableInfo.get(2).getRate()));
		tr("${sheet_4_1_5_2_read_companion_last_0_5_rate}",
				perf.format(tableInfo.get(1).getRate()));
//		MinorUtil.listSort(tableInfo);

		double num = 0;
		
		for (int i = 3; i < 7 ; i++)
		{
//			tr("${sheet_4_1_5_2_read_companion_last_" + i + "}",
//					tableInfo.get(i).getKey());
			tr("${sheet_4_1_5_2_read_companion_last_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
			num += tableInfo.get(i).getRate();
		}
		tr("${sheet_4_1_5_2_read_companion_last_more_10_rate}",
				perf.format(num));
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
		
		MinorUtil.changeChart(tableInfo);
	}
}
