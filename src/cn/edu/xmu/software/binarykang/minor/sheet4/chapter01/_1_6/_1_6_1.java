package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_6;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_6_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "0-8岁家长经常为儿童选择的图书类型";
	private List<DataMap> tableInfo;

	public _1_6_1(Docx docx, Xlsx xlsx)
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
		for (int i = 0; i < 9; i++)
		{
			tr("${sheet_4_1_6_1_cartoon_type_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet_4_1_6_1_cartoon_type_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
