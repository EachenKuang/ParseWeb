package cn.edu.xmu.software.binarykang.minor.sheet2._2_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_2_5 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁购买图书的制约因素";
	private List<DataMap> tableInfo;

	public _2_2_5(Docx docx, Xlsx xlsx)
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
		DataMap notConvinient = tableInfo.get(7);
		tableInfo.remove(7);
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 7; i++)
		{
			docx.replace("${_2_2_5_reason_to_buy_" + i + "}",
					tableInfo.get(i).getKey());
			docx.replace("${_2_2_5_reason_to_buy_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tableInfo.add(notConvinient);
		docx.replace("${_2_2_5_reason_to_buy_no_rate}",
				perf.format(notConvinient.getRate()));
	}

//	@Override
//	public void process()
//	{
//		readData();
//		chart();
//		replace();
//	};

//	@Override
	protected void chart()
	{
//		DataMap notConvinient = tableInfo.get(0);
//		tableInfo.remove(0);
//		tableInfo.add(notConvinient);
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}

}
