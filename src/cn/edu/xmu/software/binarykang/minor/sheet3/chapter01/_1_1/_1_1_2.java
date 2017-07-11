package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_1_2 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "9-13岁对阅读的喜爱程度";
//	private final static String TABLE_KEY_OTHER = "1.1.3儿童喜爱的图书类型 第一段文字中全国的阅读喜爱程度";
	private List<DataMap> tableLocal;
//	private List<DataMap> tableOther;

	public _1_1_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
//		tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
//		MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		double loveRate = 0;
		double loveRateOther = 0;
		for (int i = 0; i < 2; i++)
		{
			loveRate += tableLocal.get(i).getRate();
//			loveRateOther += tableOther.get(i).getRate();
			tr("${sheet3_1_1_2_love_read_" + i + "_rate}",
					perf.format(tableLocal.get(i).getRate()));
		}
		tr("${sheet3_1_1_2_love_read_2_rate}",
				perf.format(tableLocal.get(2).getRate()));

//		double localCutOther = loveRate - loveRateOther;
//		tr("${sheet3_1_1_2_love_read_local_cut_other}",
//				delLastChar(perf.format(Math.abs(localCutOther))));
//		tr("${sheet3_1_1_2_love_read_judge}", localCutOther > 0 ? "多" : "少");
//		tr("${sheet3_1_1_2_love_read_rate}", perf.format(loveRate));
//		tr("${sheet3_1_1_2_love_read_rate_other}", perf.format(loveRateOther));
		
		tr("${sheet3_1_1_2_love_read_rate}", perf.format(loveRate));
	
		
		
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
	}

}
