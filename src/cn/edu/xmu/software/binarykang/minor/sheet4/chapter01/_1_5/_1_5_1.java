package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_5;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_5_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "0-8岁家长在0—8周岁儿童阅读时的陪护";
	private final static String TABLE_KEY_OTHER = "0-8岁家长在0—8周岁儿童阅读时的陪护——全国";
	private List<DataMap> tableInfo;
	private List<DataMap> tableInfoOther;

	public _1_5_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
		tableInfoOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableInfoOther, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		DataMap companion = tableInfo.get(tableInfo.size() - 2);
		DataMap companionOther = tableInfoOther.get(tableInfo.size() - 2);;
		
		tr("${sheet_4_1_5_1_have_read_companion}",
				perf.format(1-companion.getRate()));
		tr("${sheet_4_1_5_1_have_read_companion_other}",
				perf.format(1-companionOther.getRate()));
		tr("${sheet_4_1_5_1_have_read_companion_judge}",
				companionOther.getRate() - companion.getRate() > 0 ? "高" : "低");
		tr("${sheet_3_2_1_1_read_read_companion_judge_cut}",
				onef.format(100*Math.abs(companionOther.getRate() - companion.getRate())));
		
		tr("${sheet_4_1_5_1_no_read_companion}",
				perf.format(tableInfo.get(tableInfo.size() - 2).getRate()));
		tableInfo.remove(companion);
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 6; i++)
		{
			tr("${sheet_4_1_5_1_read_companion_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet_4_1_5_1_read_companion_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tableInfo.add(companion);
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
