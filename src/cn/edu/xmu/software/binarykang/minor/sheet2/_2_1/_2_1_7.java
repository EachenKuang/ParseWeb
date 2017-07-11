package cn.edu.xmu.software.binarykang.minor.sheet2._2_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_1_7 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁读书遇到困难的求助对象";
	private List<DataMap> tableInfo;

	public _2_1_7(Docx docx, Xlsx xlsx)
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
		for (int i = 0; i < 3; i++)
		{
			docx.replace("${_2_1_7_ask_" + i + "}", tableInfo.get(i).getKey());
			docx.replace("${_2_1_7_ask_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		docx.replace("${_2_1_7_ask_3}", tableInfo.get(3).getKey());
		docx.replace("${_2_1_7_ask_4}", tableInfo.get(4).getKey());
		docx.replace("${_2_1_7_ask_5}", tableInfo.get(5).getKey());
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}

}
