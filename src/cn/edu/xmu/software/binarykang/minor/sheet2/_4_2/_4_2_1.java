package cn.edu.xmu.software.binarykang.minor.sheet2._4_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "4.2.1电子书价格承受力 第一段文字中的能接受的一本电子书的平均价格";
	private final static String TABLE_KEY_LOCAL = "14-17岁电子书价格承受力";
	//private final static String TABLE_KEY_OTHER = "14-17岁电子书价格承受力（全国）";
	//private List<DataMap> tableOther;
	private List<DataMap> tableLocal;
	private List<DataMap> tableAVG;
	//private String other;

	public _4_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableAVG = MinorUtil.listMapFactory();
		tableLocal = MinorUtil.listMapFactory();
		//tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		//other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
		//		BEGIN_COL + 1, " ");
		
	}

	@Override
	protected void replace()
	{
		tr("${_4_2_1_avg_price}", twof.format(tableAVG.get(0).getRate()));
		//tr("${_4_2_1_other}", other);
		tr("${_4_2_1_no_accept_rate_local}",
				perf.format(tableLocal.get(tableLocal.size() - 1).getRate()));
		//tr("${_4_2_1_no_accept_rate_other}",
		//		perf.format(tableOther.get(tableOther.size() - 1).getRate()));
		/**
		 * no accept
		 */
		//double noAcceptLocal = tableLocal.get(tableLocal.size() - 1).getRate();
		//double noAcceptOther = tableOther.get(tableOther.size() - 1).getRate();
		//double noAccept = noAcceptLocal - noAcceptOther;
		//String noAcceptJudge = noAccept > 0 ? "高" : "低";
		tr("${_4_2_1_no_accept_rate_local}", perf.format(tableLocal.get(tableLocal.size() - 1).getRate()));
		//tr("${_4_2_1_no_accept_rate_other}", perf.format(noAcceptOther));
		//tr("${_4_2_1_no_accpet_judge}", noAcceptJudge);
		//tr("${_4_2_1_no_accpet_local_cut_other}",
		//		delLastChar(perf.format(Math.abs(noAccept))));
		/**
		 * low one
		 */
		double lowOneLocal = tableLocal.get(0).getRate();
		//double lowOneOther = tableOther.get(0).getRate();
		//double lowOne = lowOneLocal - lowOneOther;
		//String lowOneJudge = lowOne > 0 ? "高" : "低";
		tr("${_4_2_1_low_one_rate_local}", perf.format(lowOneLocal));
		//tr("${_4_2_1_low_one_rate_other}", perf.format(lowOneOther));
		//tr("${_4_2_1_low_one_judge}", lowOneJudge);
		//tr("${_4_2_1_low_one_local_cut_other}",
		//		delLastChar(perf.format(Math.abs(lowOne))));
		/**
		 * one to five
		 */
		double twoToFiveLocal = 0;
		//double twoToFiveOther = 0;
		for (int i = 1; i < 5; i++)
		{
			twoToFiveLocal += tableLocal.get(i).getRate();
			//oneToFiveOther += tableOther.get(i).getRate();
		}
		tr("${_4_2_1_two_to_five_rate_local}", perf.format(twoToFiveLocal));
		//double oneToFive = oneToFiveLocal - oneToFiveOther;
		//String oneToFiveJudge = oneToFive > 0 ? "高" : "低";
		//tr("${_4_2_1_one_to_five_rate_local}", perf.format(oneToFiveLocal));
		//tr("${_4_2_1_one_to_five_rate_other}", perf.format(oneToFiveOther));
		//tr("${_4_2_1_one_to_five_judge}", oneToFiveJudge);
		//tr("${_4_2_1_one_to_five_local_cut_other}",
		//		delLastChar(perf.format(Math.abs(oneToFive))));
		/**
		 * six to nine
		 */
		double sixToNineLocal = 0;
		double sixToNineOther = 0;
		for (int i = 5; i < 9; i++)
		{
			sixToNineLocal += tableLocal.get(i).getRate();
			//sixToNineOther += tableOther.get(i).getRate();
		}
		
		double sixToNine = sixToNineLocal - sixToNineOther;
		String sixToNineJudge = sixToNine > 0 ? "高" : "低";
		tr("${_4_2_1_six_to_nine_rate_local}", perf.format(sixToNineLocal));
		//tr("${_4_2_1_six_to_nine_rate_other}", perf.format(sixToNineOther));
		//tr("${_4_2_1_six_to_nine_judge}", sixToNineJudge);
		//tr("${_4_2_1_six_to_nine_local_cut_other}",
		//		delLastChar(perf.format(Math.abs(sixToNine))));
		tr("${_4_2_1_higher_10_rate_local}", perf.format(tableLocal.get(tableLocal.size() - 2).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
	}
}
