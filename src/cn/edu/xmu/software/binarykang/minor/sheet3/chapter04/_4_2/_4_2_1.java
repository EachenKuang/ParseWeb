package cn.edu.xmu.software.binarykang.minor.sheet3.chapter04._4_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "9-13岁音像电子出版物的价格评价";
	private final static String TABLE_KEY_OTHER = "4.2音像电子出版物价格评价 第一段文字中全国的音像电子出版物价格评价";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
	private String other;

	public _4_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
		tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
				BEGIN_COL + 1, " ");
	}

	@Override
	protected void replace()
	{
		tr("${sheet_3_4_2_1_other}", other);

		double fitEvaluate = tableLocal.get(2).getRate();
		double littleCheap = tableLocal.get(1).getRate();
		double fitEvaluateOther = tableOther.get(2).getRate();
		double littleCheapOther = tableOther.get(1).getRate();
		double littleExpensive = tableLocal.get(3).getRate();
		double veryExpensive = tableLocal.get(4).getRate();

		double fitCut = fitEvaluate - fitEvaluateOther;
		double littleCheapCut = littleCheap - littleCheapOther;

		tr("${sheet_3_4_2_1_radio_public_evaluate_fit}",
				perf.format(fitEvaluate));
		tr("${sheet_3_4_2_1_radio_public_evaluate_fit_other}",
				perf.format(fitEvaluateOther));
		tr("${sheet_3_4_2_1_radio_public_evaluate_fit_judge}", fitCut > 0 ? "高"
				: "低");
		tr("${sheet_3_4_2_1_radio_public_evaluate_fit_cut}",
				delLastChar(perf.format(Math.abs(fitCut))));

		tr("${sheet_3_4_2_1_radio_public_evaluate_little_cheap}",
				perf.format(littleCheap));
		tr("${sheet_3_4_2_1_radio_public_evaluate_little_cheap_other}",
				perf.format(littleCheapOther));
		tr("${sheet_3_4_2_1_radio_public_evaluate_little_cheap_judge}",
				littleCheapCut > 0 ? "高" : "低");
		tr("${sheet_3_4_2_1_radio_public_evaluate_little_cheap_cut}",
				delLastChar(perf.format(Math.abs(littleCheapCut))));

		tr("${sheet_3_4_2_1_radio_public_evaluate_expensive}",
				perf.format(littleExpensive + veryExpensive));
		tr("${sheet_3_4_2_1_radio_public_evaluate_little_expensive}",
				perf.format(littleExpensive));
		tr("${sheet_3_4_2_1_radio_public_evaluate_very_expensive}",
				perf.format(veryExpensive));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
	}
}
