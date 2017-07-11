package cn.edu.xmu.software.binarykang.minor.sheet2._3_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_2_3 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁阅读期刊的来源";
	private List<DataMap> tableInfo;

	public _3_2_3(Docx docx, Xlsx xlsx)
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
		for (int i = 0; i < 8; i++)
		{
			tr("${_3_2_3_read_source_" + i + "}", tableInfo.get(i).getKey());
			tr("${_3_2_3_read_source_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
