package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_8;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_8_2 extends MinorBaseAction
{
	private final static String TABLE_KEY = "0-8岁儿童家长购买图书的主要渠道";
	private List<DataMap> tableInfo;

	public _1_8_2(Docx docx, Xlsx xlsx)
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
		docx.replace("${_4_1_8_2_buy_way_Online}", OnlineBookshop.getKey());
		docx.replace("${_4_1_8_2_buy_way_Online_rate}", perf.format(OnlineBookshop.getRate()));
		tableInfo.remove(6);
		
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 4; i++)
		{
			tr("${sheet_4_1_8_2_buy_cartoon_way_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet_4_1_8_2_buy_cartoon_way_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tableInfo.add(OnlineBookshop);
		tr("${sheet_4_1_8_2_buy_cartoon_way_net}", perf.format(MinorUtil.getByKey("网上书店", tableInfo).getRate()));
		//MinorUtil.getByKey("网上书店", tableInfo);
		MinorUtil.listSort(tableInfo);
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
	

	
}
