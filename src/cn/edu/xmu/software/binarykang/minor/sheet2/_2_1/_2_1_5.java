package cn.edu.xmu.software.binarykang.minor.sheet2._2_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_1_5 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17ÀÍÕº È‘ƒ∂¡¿¥‘¥";
	private List<DataMap> table;

	public _2_1_5(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		table = new ArrayList<DataMap>();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, table, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		MinorUtil.listSort(table);
		for (int i = 0; i < 8; i++)
		{
			docx.replace("${_2_1_5_source_" + i + "}", table.get(i).getKey());
			docx.replace("${_2_1_5_source_" + i + "_rate}",
					perf.format(table.get(i).getRate()));
		}
	}

	@Override
	protected void chart()
	{
		Collections.reverse(table);
		MinorUtil.changeChart(table);
	}
}
