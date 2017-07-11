package cn.edu.xmu.software.binarykang.minor.sheet2._4_4;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_4_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17ÀÍ…œÕ¯∑Ω Ω";
	private List<DataMap> tableInfo;

	public _4_4_1(Docx docx, Xlsx xlsx)
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
		tr("${_4_4_1_internet_rate}", perf.format(1-tableInfo.get(tableInfo.size()-1).getRate()));
		DataMap tmp = tableInfo.get(tableInfo.size() - 1);
		DataMap other = tableInfo.get(tableInfo.size() - 2);
		tableInfo.remove(tmp);
		tableInfo.remove(other);
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 4; i++)
		{
			tr("${_4_4_1_internet_way_" + i + "}", tableInfo.get(i).getKey());
			tr("${_4_4_1_internet_way_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		
		tableInfo.add(other);
		tableInfo.add(tmp);
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}
	
//	@Override
//	public void process()
//	{
//		readData();
//		chart();
//		replace();
//		trReplace();
//	};

}
