package cn.edu.xmu.software.binarykang.minor.sheet2._4_3;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_3_5 extends MinorBaseAction
{
	private final static String TABLE_KEY_AD = "14-17岁手机阅读的优点";
	private final static String TABLE_KEY_DAD = "14-17岁手机阅读的不足之处";
	private List<DataMap> tableDAD;
	private List<DataMap> tableAD;

	public _4_3_5(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableDAD = MinorUtil.listMapFactory();
		tableAD = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_AD, tableAD, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_DAD, tableDAD, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${_4_3_5_timeliness}", perf.format(tableAD.get(2).getRate()));
		tr("${_4_3_5_lowcost}", perf.format(tableAD.get(1).getRate()));
		tr("${_4_3_5_eye_harmness}", perf.format(tableDAD.get(3).getRate()));
		tr("${_4_3_5_radiation}", perf.format(tableDAD.get(0).getRate()));
		tr("${_4_3_5_limitation}", perf.format(tableDAD.get(2).getRate()));
		tr("${_4_3_5_small_screen}", perf.format(tableDAD.get(1).getRate()));
		tr("${_4_3_5_no_disadvantage}" ,perf.format(tableDAD.get(5).getRate()));
		MinorUtil.listSort(tableDAD);
		MinorUtil.listSort(tableAD);
		for (int i = 0; i < 4; i++)
		{
			tr("${_4_3_5_advantage_" + i + "}", tableAD.get(i).getKey());
			tr("${_4_3_5_advantage_" + i + "_rate}",
					perf.format(tableAD.get(i).getRate()));
		}
		for (int i = 0; i < 4; i++)
		{
			tr("${_4_3_5_disadvantage_" + i + "}", tableDAD.get(i).getKey());
			tr("${_4_3_5_disadvantage_" + i + "_rate}",
					perf.format(tableDAD.get(i).getRate()));
		}
	}

	@Override
	protected void chart()
	{
		
		DataMap noAdv = tableAD.get(4);
		tableAD.remove(4);
		MinorUtil.listSort(tableAD);
		tableAD.add(noAdv);
		
		DataMap noDis = tableDAD.get(4);
		tableDAD.remove(4);
		MinorUtil.listSort(tableDAD);
		tableDAD.add(noDis);
		
		Collections.reverse(tableAD);
		Collections.reverse(tableDAD);
		MinorUtil.changeChart(tableAD);
		MinorUtil.changeChart(tableDAD);
	}
}
