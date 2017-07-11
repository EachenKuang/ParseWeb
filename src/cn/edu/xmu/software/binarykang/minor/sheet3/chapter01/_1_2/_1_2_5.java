package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_2;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_2_5 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13ËêÍ¼Êé¹ºÂòÇþµÀ";
	private List<DataMap> tableInfo;

	public _1_2_5(Docx docx, Xlsx xlsx)
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
		DataMap OnlineBookshop = tableInfo.get(6);		
		tableInfo.remove(6);
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 4; i++)
		{
			tr("${sheet_3_1_2_5_buy_way_" + i + "}", tableInfo.get(i).getKey());
			tr("${sheet_3_1_2_5_buy_way_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tableInfo.add(OnlineBookshop);
		tr("${sheet_3_1_2_5_buy_way_online}",perf.format( MinorUtil.getByKey("ÍøÉÏÊéµê", tableInfo).getRate()));
		MinorUtil.listSort(tableInfo);
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}
}
