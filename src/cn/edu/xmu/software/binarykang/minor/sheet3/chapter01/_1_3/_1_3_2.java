package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_3_2 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13岁家长为孩子购买的图书类型";
	private List<DataMap> tableInfo;

	public _1_3_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 14; i++)
		{
			tr("${sheet_3_1_3_2_buy_book_type_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet_3_1_3_2_buy_book_type_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
	}

//	${sheet_3_1_3_2_buy_book_type_12_rate}
//	${sheet_3_1_3_2_buy_book_type_13_rate}
//	${sheet_3_1_3_2_buy_book_type_12}
//	${sheet_3_1_3_2_buy_book_type_13}
	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
