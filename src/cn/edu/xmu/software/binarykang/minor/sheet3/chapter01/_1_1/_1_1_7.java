package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_1;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_1_7 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "1.1.7课外书拥有量 第一段文字中的课外书人均拥有量";
	private final static String TABLE_KEY = "9-13岁家中课外书拥有量";
	private List<DataMap> tableAVG;
	private List<DataMap> tableInfo;

	public _1_1_7(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableAVG = MinorUtil.listMapFactory();
		tableInfo = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${sheet3_1_1_7_nook_num_avg}",
				twof.format(tableAVG.get(0).getRate()));
//		double lessTwenty = 0;
//		DataMap max = new DataMap("", 0.0);
//		for (int i = 0; i < 4; i++)
//		{
//			if (tableInfo.get(i).getRate() > max.getRate())
//				max = tableInfo.get(i);
//			lessTwenty += tableInfo.get(i).getRate();
//		}
//		tr("${sheet3_1_1_7_nook_num_less_twenty_rate}", perf.format(lessTwenty));
//		tr("${sheet3_1_1_7_nook_num_most}", max.getKey());
//		tr("${sheet3_1_1_7_nook_num_most_rate}", perf.format(max.getRate()));
//		tr("${sheet3_1_1_7_nook_num_more_fifty_rate}", perf.format(moreFifty));
		
		double one2Thirty = 0.0;
		for (int i = 0; i < 6 ; i++)
		{
			tr("${sheet3_1_1_7_nook_num_"+i+"}", perf.format(tableInfo.get(i).getRate()));
			if(i<5 && i>0)
				one2Thirty += tableInfo.get(i).getRate();
		}
		tr("${sheet3_1_1_7_nook_num_thirty}", perf.format(one2Thirty));
		tr("${sheet3_1_1_7_nook_num_30-50}", perf.format(tableInfo.get(5).getRate()+tableInfo.get(6).getRate()));
		
		
		double moreFifty = tableInfo.get(tableInfo.size() - 1).getRate()
				+ tableInfo.get(tableInfo.size() - 2).getRate();
		tr("${sheet3_1_1_7_nook_num_more_fifty_rate}", perf.format(moreFifty));
	}

	@Override
	protected void chart()
	{
//		Collections.reverse(tableInfo);
		DataMap last = tableInfo.get(0);
		tableInfo.remove(0);
		tableInfo.add(last);
		
		MinorUtil.changeChart(tableInfo);
	}

}
