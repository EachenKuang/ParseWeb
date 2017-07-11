package cn.edu.xmu.software.binarykang.minor.sheet3.chapter04._4_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13岁音像电子出版物的购买渠道";
	private List<DataMap> tableInfo;

	public _4_1_1(Docx docx, Xlsx xlsx)
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
		double neverBuy = tableInfo.get(tableInfo.size() - 1).getRate();
		double netBuy = MinorUtil.getByKey("网上", tableInfo).getRate();
		tr("${sheet_3_4_1_1_radio_public_buy_way_net_rate}",
				perf.format(netBuy));
		tr("${sheet_3_4_1_1_radio_public_never_buy_rate}",
				perf.format(neverBuy));
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 3; i++)
		{
			tr("${sheet_3_4_1_1_radio_public_buy_way_" + i + "}", tableInfo
					.get(i).getKey());
			tr("${sheet_3_4_1_1_radio_public_buy_way_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tableInfo.add(tmp);
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
