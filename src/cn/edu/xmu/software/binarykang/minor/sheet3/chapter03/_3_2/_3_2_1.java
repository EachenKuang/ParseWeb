package cn.edu.xmu.software.binarykang.minor.sheet3.chapter03._3_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13岁上网地点";
	//9-13岁上网频率均值  9-13岁上网频率
	private List<DataMap> tableInfo;
	private double internetAVG;
	private List<DataMap> tableInternet;
	
	public _3_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
		tableInternet = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		internetAVG = SingleValue.read(xlsx, "9-13岁上网频率均值", 1, 1);
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, "9-13岁上网频率", tableInternet, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		double moreWeek = 0;
		tr("${sheet_3_3_2_1_on_net_avg}",twof.format(internetAVG));
		for (int i = 0; i < 4; i++)
		{
			tr("${sheet_3_3_2_1_on_net_" + i + "}",
					perf.format(tableInternet.get(i).getRate()));
			moreWeek += tableInternet.get(i).getRate();
		}
		tr("${sheet_3_3_2_1_on_net_more_week}",perf.format(moreWeek));
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 2; i++)
		{
			tr("${sheet_3_3_2_1_on_net_place_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet_3_3_2_1_on_net_place_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		
		
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInternet);
		MinorUtil.changeChart(tableInfo);
	}
}
