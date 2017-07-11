package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "1.1 儿童阅读的起始时间 第一段文字中的平均阅读起始时间";
	private final static String TABLE_KEY = "0-8岁开始阅读行为的年龄分布";
	private final static String TABLE_KEY_OTHER = "0-8岁开始阅读行为的年龄分布——全国";
	private List<DataMap> tableInfo;
	private List<DataMap> tableAVG;
	private List<DataMap> tableInfoOther;


	public _1_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
		tableAVG = MinorUtil.listMapFactory();
		tableInfoOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableInfoOther, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_4_1_1_1_start_read_avg}",
				twof.format(tableAVG.get(0).getRate()));
		double less3Read = tableInfo.get(0).getRate()+
				tableInfo.get(1).getRate()+
				tableInfo.get(2).getRate();
		double less3ReadOther = tableInfoOther.get(0).getRate()+
				tableInfoOther.get(1).getRate()+
				tableInfoOther.get(2).getRate();
		
		tr("${sheet_4_1_1_1_start_read_less_3}",
				perf.format(less3Read));
		tr("${sheet_4_1_1_1_start_read_less_3_other}",
				perf.format(less3ReadOther));
		tr("${sheet_4_1_1_1_start_read_less_3_judge}",
				less3Read > less3ReadOther ? "高" : "低");
		tr("${sheet_4_1_1_1_start_read_less_3_cut}",
				delLastChar(perf.format(Math.abs(less3Read - less3ReadOther))));
		
//		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 4; i++)
		{
			tr("${sheet_4_1_1_1_start_read_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet_4_1_1_1_start_read_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tr("${sheet_4_1_1_1_start_read_over_8}",
				perf.format(tableInfo.get(4).getRate()+
						tableInfo.get(5).getRate()+
						tableInfo.get(6).getRate()+
						tableInfo.get(7).getRate()));
	}

	@Override
	public void process()
	{
		readData();
		chart();
		replace();
		trReplace();
	};

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo,tableInfoOther);
	}
}
