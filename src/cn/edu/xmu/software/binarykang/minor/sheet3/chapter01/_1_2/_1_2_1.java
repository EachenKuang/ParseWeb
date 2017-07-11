package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_2;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "1.2.1 ͼ�鹺���� ��һ�������еļҳ�Ϊ����ƽ������ͼ����";
	private final static String TABLE_KEY = "9-13��ͼ�鹺����";
//	private List<DataMap> tableAVG;
	private List<DataMap> tableInfo;
	private double purchaseAvg;
	private double purchaseCity;
	private double purchaseVillage;

	public _1_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
//		tableAVG = MinorUtil.listMapFactory();
		tableInfo = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
//		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		purchaseAvg = SingleValue.read(xlsx, TABLE_KEY_AVG, 3, 1);
		purchaseCity = SingleValue.read(xlsx, TABLE_KEY_AVG, 3, 2);
		purchaseVillage = SingleValue.read(xlsx, TABLE_KEY_AVG, 3, 3);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_3_1_2_1_buy_count_avg}",
				twof.format(purchaseAvg));
		tr("${sheet_3_1_2_1_buy_count_city}",
				twof.format(purchaseCity));
		tr("${sheet_3_1_2_1_buy_count_village}",
				twof.format(purchaseVillage));
		tr("${sheet_3_1_2_1_buy_count_city_village_judge}",purchaseCity - purchaseVillage > 0 ? "��"
				: "��");
		tr("${sheet_3_1_2_1_buy_count_city_village_cut}",twof.format(Math.abs(purchaseCity - purchaseVillage)));
		
		double lessTwenty = 0;
		double TwentyFifty = 0;
		for (int i = 0; i < 3; i++)
		{
			lessTwenty += tableInfo.get(i).getRate();
		}
		double moreFifty = 
				 tableInfo.get(tableInfo.size() - 2).getRate()
				+ tableInfo.get(tableInfo.size() - 3).getRate();
		tr("${sheet_3_1_2_1_buy_count_less_twenty_rate}", perf.format(lessTwenty));
		for (int i = 3; i < 6; i++)
		{
			TwentyFifty += tableInfo.get(i).getRate();
		}
		tr("${sheet_3_1_2_1_buy_count_twenty_Fifty_rate}",
				perf.format(TwentyFifty));
		tr("${sheet_3_1_2_1_buy_count_more_Fifty_rate}",
				perf.format(moreFifty));
		tr("${sheet_3_1_2_1_buy_count_never}",
				perf.format(tableInfo.get(tableInfo.size() - 1).getRate()));
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}

}
