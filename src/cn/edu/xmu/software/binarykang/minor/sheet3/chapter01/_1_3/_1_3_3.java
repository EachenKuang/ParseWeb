package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_3_3 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13岁家长带孩子逛书店的频次";
	private List<DataMap> tableInfo;
	private double frequence;
	public _1_3_3(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		frequence = SingleValue.read(xlsx, "9-13岁家长带孩子逛书店的频次均值", 1, 1);
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
//		DataMap never = tableInfo.get(tableInfo.size() - 1);
//		tableInfo.remove(never);
//		MinorUtil.listSort(tableInfo);
		tr("${sheet_3_1_3_3_frequence}",twof.format(frequence));
		for (int i = 0; i < 5; i++)
		{
//			tr("${sheet_3_1_3_3_go_book_shop_time_" + i + "}", tableInfo.get(i)
//					.getKey());
			tr("${sheet_3_1_3_3_go_book_shop_time_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tr("${sheet_3_1_3_3_go_book_shop_time_will}",
				perf.format(1-tableInfo.get(tableInfo.size()-1).getRate()));
//		tableInfo.add(never);
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
