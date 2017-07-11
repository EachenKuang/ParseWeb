package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_2;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_2_3 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "1.2.3儿童图书拥有量 第一段文字中儿童平均拥有多少本图书";
	private final static String TABLE_KEY = "0-8岁人均图书拥有量";
	private List<DataMap> tableInfo;
	private List<DataMap> tableAVG;

	public _1_2_3(Docx docx, Xlsx xlsx)
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
		tr("${sheet_4_1_2_3_have_book_num_avg}",
				twof.format(tableAVG.get(0).getRate()));
		
		tr("${sheet_4_1_2_3_have_book_num_rate}",
				perf.format(1-tableInfo.get(tableInfo.size()-1).getRate()));
		tr("${sheet_4_1_2_3_have_book_num_less_30}",
				perf.format(tableInfo.get(0).getRate()
						+tableInfo.get(1).getRate()
						+tableInfo.get(2).getRate()
						+tableInfo.get(3).getRate()
						));
		tr("${sheet_4_1_2_3_have_book_num_1_5_rate}",
				perf.format(tableInfo.get(0).getRate()));
		tr("${sheet_4_1_2_3_have_book_num_6_10_rate}",
				perf.format(tableInfo.get(1).getRate()));
		tr("${sheet_4_1_2_3_have_book_num_11_20_rate}",
				perf.format(tableInfo.get(2).getRate()));						
		tr("${sheet_4_1_2_3_have_book_num_20_30_rate}",
				perf.format(tableInfo.get(3).getRate()));
		
		tr("${sheet_4_1_2_3_have_book_num_31_50_rate}",
				perf.format(tableInfo.get(4).getRate()+tableInfo.get(5).getRate()));
		tr("${sheet_4_1_2_3_have_book_num_50_more_rate}",
				perf.format(tableInfo.get(6).getRate()+tableInfo.get(7).getRate()));
		tr("${sheet_4_1_2_3_have_book_num_zero_rate}",
				perf.format(tableInfo.get(tableInfo.size()-1).getRate()));
		
	}
	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}
}
