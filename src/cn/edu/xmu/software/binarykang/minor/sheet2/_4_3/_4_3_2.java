package cn.edu.xmu.software.binarykang.minor.sheet2._4_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_3_2 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁花费在手机阅读上的时间";
	private final static String TABLE_KEY_AVG = "4.3.2手机阅读时间 第一段文字中的平均每天手机阅读时间";
	private List<DataMap> tableInfo;
//	private List<DataMap> tableInfoFemale;
	private List<DataMap> tableAVG;

	public _4_3_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = MinorUtil.listMapFactory();
//		tableInfoFemale = MinorUtil.listMapFactory();
		tableAVG = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, xlsx.getRowByKey(TABLE_KEY) + 3, tableInfo,
				BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
	}

	@Override
	protected void replace()
	{
		double lessTwenty = 0;
		int i = 0;
		for (i = 0; i < 2; i++)
		{
			lessTwenty += tableInfo.get(i).getRate();
//			lessTwenty += tableInfoFemale.get(i).getRate();
		}
		
		tr("${_4_3_2_less_twenty_time}", perf.format(lessTwenty));
//		tr("${_4_3_2_more_twenty_time}", perf.format(1 - lessTwenty));
		tr("${_4_3_2_twenty_thirty_time}",
				perf.format((tableInfo.get(2).getRate())));
		tr("${_4_3_2_thirty_oneh_time}",
				perf.format((tableInfo.get(3).getRate())));
		tr("${_4_3_2_oneh_twoh_time}",
				perf.format((tableInfo.get(4).getRate())));
		tr("${_4_3_2_more_twoh_time}",
				perf.format((tableInfo.get(5).getRate()+tableInfo.get(6).getRate())));
		tr("${_4_3_2_avg_time}", twof.format(tableAVG.get(0).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
