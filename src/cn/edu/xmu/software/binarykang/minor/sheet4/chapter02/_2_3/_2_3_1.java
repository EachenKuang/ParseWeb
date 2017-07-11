package cn.edu.xmu.software.binarykang.minor.sheet4.chapter02._2_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_3_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "2.3 家长对儿童类期刊的价格承受力 第一段文字中的单本儿童期刊的价格承受力";
	private final static String TABLE_KEY = "0-8岁家长对儿童类期刊的价格承受力";
	private List<DataMap> tableInfo;
	private List<DataMap> tableAVG;

	public _2_3_1(Docx docx, Xlsx xlsx)
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
		tr("${sheet_4_2_3_1_magazine_afford_avg_rate}",
				twof.format(tableAVG.get(0).getRate()));

		tr("${sheet_4_2_3_1_magazine_afford_0_rate}",
				perf.format(tableInfo.get(0).getRate()));
		double num = 0;
		for (int i = 1; i < 6; i++)
		{
			tr("${sheet_4_2_3_1_magazine_afford_"+ i +"_rate}",
					perf.format(tableInfo.get(i).getRate()));
			num += tableInfo.get(i).getRate();
		}
		tr("${sheet_4_2_3_1_magazine_afford_2_12_rate}",
				perf.format(num));
		
		tr("${sheet_4_2_3_1_magazine_afford_more_12}",
				perf.format(tableInfo.get(6).getRate()+tableInfo.get(7).getRate()));

//		MinorUtil.listSort(tableInfo);
//		tr("${sheet_4_2_3_1_magazine_afford_0}", tableInfo.get(0).getKey());
//		tr("${sheet_4_2_3_1_magazine_afford_0_rate}",
//				perf.format(tableInfo.get(0).getRate()));
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
