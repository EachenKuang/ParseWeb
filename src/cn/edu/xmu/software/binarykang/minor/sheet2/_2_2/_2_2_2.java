package cn.edu.xmu.software.binarykang.minor.sheet2._2_2;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_2_2 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁购书频次";
	private final static String TABLE_KEY_AVG = "2.2.2  购书频率第一行中的人均购书次数";
	private List<DataMap> tableInfo;
	//private List<DataMap> tableAvg;
	private double tableAvg;

	public _2_2_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = new ArrayList<DataMap>();
		//tableAvg = new ArrayList<DataMap>();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		//MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAvg, BEGIN_COL + 2);
		tableAvg = SingleValue.read(xlsx, TABLE_KEY_AVG, 1, 2);
	}

	@Override
	protected void replace()
	{
		docx.replace("${_2_2_2_buy_book_times}", twof.format(tableAvg));
		//docx.replace("${_2_2_2_buy_book_week}", tableInfo.get(0).getKey());
		//docx.replace("${_2_2_2_buy_book_week_rate}",
		//		perf.format(tableInfo.get(0).getRate()));
		//docx.replace("${_2_2_2_buy_book_half_month}", tableInfo.get(1).getKey());
		//docx.replace("${_2_2_2_buy_book_half_month_rate}",
		//		perf.format(tableInfo.get(1).getRate()));
		//docx.replace("${_2_2_2_buy_book_never}",
		//		tableInfo.get(tableInfo.size() - 1).getKey());
		//docx.replace("${_2_2_2_buy_book_never_rate}",
		//		perf.format(tableInfo.get(tableInfo.size() - 1).getRate()));
		double year = 0;
		for (int i = 0; i < 7; i++)
		{
			year += tableInfo.get(i).getRate();
		}
		docx.replace("${_2_2_2_buy_book_year_rate}", perf.format(year));
		//MinorUtil.listSort(tableInfo);
		double quarter = 0;
		for (int i = 0; i < 5; i++)
		{
			quarter += tableInfo.get(i).getRate();
			//docx.replace("${_2_2_2_buy_book_" + i + "}", tableInfo.get(i)
			//		.getKey());
			//docx.replace("${_2_2_2_buy_book_" + i + "_rate}",
			//		perf.format(tableInfo.get(i).getRate()));
		}
		docx.replace("${_2_2_2_buy_book_quarter_rate}", perf.format(quarter));
	}

	@Override
	public void process()
	{
		readData();
		chart();
		replace();
	};

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
