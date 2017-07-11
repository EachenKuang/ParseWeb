package cn.edu.xmu.software.binarykang.minor.sheet3.chapter03._3_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_3_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "9-13岁上网从事的主要内容";
	private final static String TABLE_ATTITUDE_KEY = "9-13岁父母对上网的态度";
	private List<DataMap> tableInfo;
	private List<DataMap> tableAttitude;

	public _3_3_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
		tableAttitude = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_ATTITUDE_KEY, tableAttitude, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 10; i++)
		{
			tr("${sheet_3_3_3_1_on_net_do_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet_3_3_3_1_on_net_do_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tr("${sheet_3_3_3_1_on_net_do_study_rate}",
				perf.format(MinorUtil.getByKey("网上学习", tableInfo).getRate()));
		
		tr("${sheet_3_3_3_1_attitude_aprove}",
				perf.format(tableAttitude.get(0).getRate() + tableAttitude.get(1).getRate()));
		tr("${sheet_3_3_3_1_attitude_disaprove}",
				perf.format(tableAttitude.get(3).getRate() + tableAttitude.get(4).getRate()));
		tr("${sheet_3_3_3_1_attitude_aprove_not_care}",
				perf.format(tableAttitude.get(2).getRate()));
		
		
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
		MinorUtil.changeChart(tableAttitude);
	}
}
