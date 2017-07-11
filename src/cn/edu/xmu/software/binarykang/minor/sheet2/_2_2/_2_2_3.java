package cn.edu.xmu.software.binarykang.minor.sheet2._2_2;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_2_3 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17ËêÍ¼Êé¹ºÂòÇþµÀ";
	private List<DataMap> tableInfo;

	public _2_2_3(Docx docx, Xlsx xlsx)
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
		DataMap OnlineBookshop = tableInfo.get(6);
		docx.replace("${_2_2_3_buy_way_Online}", OnlineBookshop.getKey());
		docx.replace("${_2_2_3_buy_way_Online_rate}", perf.format(OnlineBookshop.getRate()));
		
		tableInfo.remove(6);
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 6; i++)
		{
			docx.replace("${_2_2_3_buy_way_" + i + "}", tableInfo.get(i)
					.getKey());
			docx.replace("${_2_2_3_buy_way_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tableInfo.add(OnlineBookshop);
		MinorUtil.listSort(tableInfo);
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
	

}
