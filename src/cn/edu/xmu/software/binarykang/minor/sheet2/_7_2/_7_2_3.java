package cn.edu.xmu.software.binarykang.minor.sheet2._7_2;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _7_2_3 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁读书活动的参与类型";
	private List<DataMap> tableInfo;

	public _7_2_3(Docx docx, Xlsx xlsx)
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
		DataMap tmp = tableInfo.get(tableInfo.size() - 1);
		tableInfo.remove(tmp);
		double neverRate = tmp.getRate();
		double haveRate = 1 - neverRate;
		tr("${_7_2_3_read_active_haven_join}", perf.format(haveRate));
		tr("${_7_2_3_read_active_never_join}", perf.format(neverRate));
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 8; i++)
		{
			tr("${_7_2_3_read_active_type_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${_7_2_3_read_active_type_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tableInfo.add(tmp);
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}
}
