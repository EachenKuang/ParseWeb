package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_8;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_8_8 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "1.8.8 家长对儿童类图书价格的承受力 第一段文字中的图书平均可承受的价格";
	private final static String TABLE_KEY = "0-8岁家长对儿童图书价格的承受力";
	private List<DataMap> tableInfo;
	private List<DataMap> tableAVG;

	public _1_8_8(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
		tableAVG = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_4_1_8_8_afford_avg}",
				twof.format(tableAVG.get(0).getRate()));
		
		tr("${sheet_4_1_8_8_afford_four_thirty_rate}",
				perf.format(tableInfo.get(1).getRate()
						+tableInfo.get(2).getRate()+tableInfo.get(3).getRate()+tableInfo.get(4).getRate()));
		tr("${sheet_4_1_8_8_afford_less_four_rate}",
				perf.format(tableInfo.get(0).getRate()));
		tr("${sheet_4_1_8_8_afford_four_eight_rate}",
				perf.format(tableInfo.get(1).getRate()));
		tr("${sheet_4_1_8_8_afford_eight_twelve_rate}",
				perf.format(tableInfo.get(2).getRate()));
//		tr("${sheet_4_1_8_8_afford_add_rate}",
//				perf.format(tableInfo.get(1).getRate()
//						+ tableInfo.get(2).getRate()));

		tr("${sheet_4_1_8_8_afford_twelve_twenty_rate}",
				perf.format(tableInfo.get(3).getRate()));
		tr("${sheet_4_1_8_8_afford_twenty_thirty_rate}",
				perf.format(tableInfo.get(4).getRate()));		

		tr("${sheet_4_1_8_8_afford_more_thirty_rate}",
				perf.format(tableInfo.get(5).getRate()));
		tr("${sheet_4_1_8_8_afford_all_as_child_love}",
				perf.format(tableInfo.get(tableInfo.size() - 1).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
