package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_1;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_1_5 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "1.1.5阅读时长 第一段文字中的课外书阅读时长（当地）";
	private final static String TABLE_KEY_OTHER = "1.1.5阅读时长 第一段文字中的课外书阅读时长（全国）";
	private final static String TABLE_KEY_READE_TIME = "9-13岁日均阅读时长";

	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
	private List<DataMap> tableReadeTime;
	String local = MinorUtil.local;
	private String other;

	public _1_1_5(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
		tableOther = MinorUtil.listMapFactory();
		tableReadeTime = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
				BEGIN_COL, " ");
		MinorUtil.readData(xlsx, TABLE_KEY_READE_TIME, tableReadeTime,
				BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${sheet3_1_1_5_other}", other);
		double localTime = tableLocal.get(0).getRate();
		double otherTime = tableOther.get(0).getRate();
		double cut = localTime - otherTime;
		tr("${sheet3_1_1_5_reade_time_avg_local}", twof.format(localTime));
		tr("${sheet3_1_1_5_reade_time_avg_other}", twof.format(otherTime));
		tr("${sheet3_1_1_5_reade_time_avg_judge}", cut > 0 ? "多" : "少");
		tr("${sheet3_1_1_5_reade_time_avg_cut}", twof.format(Math.abs(cut)));
//		DataMap tenTotwenty = MinorUtil.getByKey("10-20分钟",
//				tableReadeTime);
//		DataMap twentyToThirty = MinorUtil.getByKey("20-30分钟",
//				tableReadeTime);
//		double neverRead = MinorUtil.getByKey("基本不读", tableReadeTime).getRate();
//		double lessFive = MinorUtil.getByKey("5分钟以下", tableReadeTime).getRate();
//		double moreOneH = MinorUtil.getByKey("1小时及以上", tableReadeTime)
//				.getRate();
//		tr("${sheet3_1_1_5_reade_time_ten_thirty}",
//				perf.format(tenTotwenty.getRate() + twentyToThirty.getRate()));
//		DataMap best = tenTotwenty.getRate()
//				- twentyToThirty.getRate() > 0 ? tenTotwenty : twentyToThirty;
		for(int i = 0 ; i < 8 ; i++)
		{
			tr("${sheet3_1_1_5_reade_time_"+i+"}", perf.format(tableReadeTime.get(i).getRate()));
		}
		
//		tr("${sheet3_1_1_5_reade_time_0}", perf.format());
//		tr("${sheet3_1_1_5_reade_time_1}", perf.format(best.getRate()));
//		tr("${sheet3_1_1_5_reade_time_2}", perf.format(neverRead));
//		tr("${sheet3_1_1_5_reade_time_3}", perf.format(lessFive));
//		tr("${sheet3_1_1_5_reade_time_less_five_add_never}",
//				perf.format(neverRead + lessFive));
//		tr("${sheet3_1_1_5_reade_time_more_oneh_rate}", perf.format(moreOneH));
	}

	@Override
	protected void chart()
	{	
		//
		DataMap noRead = tableReadeTime.get(0);
		tableReadeTime.remove(0);
		tableReadeTime.add(noRead);
		
		Collections.reverse(tableReadeTime);
		MinorUtil.changeChart(tableReadeTime);
	}
}
