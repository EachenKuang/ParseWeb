package cn.edu.xmu.software.binarykang.minor.sheet2._2_2;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_2_4 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁影响购书的因素";
	private List<DataMap> tableInfo;

	public _2_2_4(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = new ArrayList<DataMap>();
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
		for (int i = 0; i < 13; i++)
		{
			docx.replace("${_2_2_4_reason_buy_" + i + "}", tableInfo.get(i)
					.getKey());
			docx.replace("${_2_2_4_reason_buy_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
	}
//	${_2_2_4_reason_buy_12}
//	${_2_2_4_reason_buy_11}
//	${_2_2_4_reason_buy_10}
//	${_2_2_4_reason_buy_9}
	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
