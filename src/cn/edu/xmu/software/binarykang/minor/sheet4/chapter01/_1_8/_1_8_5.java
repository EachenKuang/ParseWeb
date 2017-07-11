package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_8;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_8_5 extends MinorBaseAction
{
	private final static String TABLE_KEY = "0-8岁图书信息的获取途径";
	private List<DataMap> tableInfo;

	public _1_8_5(Docx docx, Xlsx xlsx)
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
		DataMap noInfor = tableInfo.get(tableInfo.size()-1);
		tableInfo.remove(noInfor);
		
		DataMap casul = tableInfo.get(tableInfo.size()-2);
		tableInfo.remove(casul);
		
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 7; i++)
		{
			tr("${sheet_4_1_8_5_get_info_way_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet_4_1_8_5_get_info_way_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tr("${sheet_4_1_8_5_get_info_way_no_rate}",perf.format(noInfor.getRate())) ;
		tr("${sheet_4_1_8_5_get_info_way_usual_rate}",perf.format(casul.getRate())) ;
		tableInfo.add(tableInfo.size()-1, casul);
		tableInfo.add(noInfor);
	}

	@Override
	protected void chart()
	{
		
//		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}
}
