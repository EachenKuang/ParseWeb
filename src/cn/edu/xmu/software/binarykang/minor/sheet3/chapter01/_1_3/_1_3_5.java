package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_3_5 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13岁家长为少年儿童购书的不便之处";
	private List<DataMap> tableInfo;

	public _1_3_5(Docx docx, Xlsx xlsx)
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
		DataMap notRestrict = tableInfo
				.get(tableInfo.size() - 2);
		tableInfo.remove(notRestrict);
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 7; i++)
		{
			tr("${sheet_3_1_3_5_restrict_buy_element_" + i + "}", tableInfo
					.get(i).getKey());
			tr("${sheet_3_1_3_5_restrict_buy_element_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tr("${sheet_3_1_3_5_no_restrict_buy_element}",
				perf.format(notRestrict.getRate()));
		tableInfo.add(notRestrict);
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
