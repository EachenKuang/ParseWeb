package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_3_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13岁家长陪孩子读书情况";
	private List<DataMap> tableInfo;

	public _1_3_1(Docx docx, Xlsx xlsx)
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
		DataMap noCompanion = tableInfo
				.get(tableInfo.size() - 2);
		tableInfo.remove(noCompanion);
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 6; i++)
		{
			tr("${sheet_3_1_3_1_companion_identity_" + i + "}", tableInfo
					.get(i).getKey());
			tr("${sheet_3_1_3_1_companion_identity_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
//		tr("${sheet_3_1_3_1_companion_identity_0_cut_1}",
//				delLastChar(perf.format(Math.abs(tableInfo.get(0).getRate()
//						- tableInfo.get(1).getRate()))));
		tr("${sheet_3_1_3_1_no_companion_rate}",
				perf.format(noCompanion.getRate()));
		tableInfo.add(noCompanion);
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
