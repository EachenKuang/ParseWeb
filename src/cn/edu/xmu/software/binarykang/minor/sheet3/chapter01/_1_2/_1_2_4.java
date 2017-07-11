package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.Constant;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_2_4 extends MinorBaseAction
{
	private final static String TABLE_KEY_TYPE = "9-13岁图书种类的评价";
	private final static String TABLE_KEY_QUALITY = "9-13岁图书质量的评价";
	private List<DataMap> tableType;
	private List<DataMap> tableQuality;

	public _1_2_4(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableType = MinorUtil.listMapFactory();
		tableQuality = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_TYPE, tableType, BEGIN_COL + 1);
		MinorUtil
				.readData(xlsx, TABLE_KEY_QUALITY, tableQuality, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_3_1_2_4_evalute_type_rich}",
				perf.format(tableType.get(0).getRate()
						+ tableType.get(1).getRate()));
		tr("${sheet_3_1_2_4_evalute_type_very_rich}",
				perf.format(tableType.get(0).getRate()));
		tr("${sheet_3_1_2_4_evalute_type_little_rich}",
				perf.format(tableType.get(1).getRate()));
		tr("${sheet_3_1_2_4_evalute_type_general}",
				perf.format(tableType.get(2).getRate()));
		tr("${sheet_3_1_2_4_evalute_type_lack}",
				perf.format(tableType.get(3).getRate()
						+ tableType.get(4).getRate()));
		tr("${sheet_3_1_2_4_evalute_type_little_lack}",
				perf.format(tableType.get(3).getRate()));
		tr("${sheet_3_1_2_4_evalute_type_very_lack}",
				perf.format(tableType.get(4).getRate()));
		tr("${sheet_3_1_2_4_evalute_type_dont_know}",
				perf.format(tableType.get(5).getRate()));

		tr("${sheet_3_1_2_4_evalute_quality_high}",
				perf.format(tableQuality.get(0).getRate()
						+ tableQuality.get(1).getRate()));
		tr("${sheet_3_1_2_4_evalute_quality_very_high}",
				perf.format(tableQuality.get(0).getRate()));
		tr("${sheet_3_1_2_4_evalute_quality_little_high}",
				perf.format(tableQuality.get(1).getRate()));
		tr("${sheet_3_1_2_4_evalute_quality_general}",
				perf.format(tableQuality.get(2).getRate()));
		tr("${sheet_3_1_2_4_evalute_quality_low}",
				perf.format(tableQuality.get(3).getRate()
						+ tableQuality.get(4).getRate()));
		tr("${sheet_3_1_2_4_evalute_type_little_low}",
				perf.format(tableQuality.get(3).getRate()));
		tr("${sheet_3_1_2_4_evalute_type_very_low}",
				perf.format(tableQuality.get(4).getRate()));
		tr("${sheet_3_1_2_4_evalute_quality_dont_know}",
				perf.format(tableQuality.get(5).getRate()));
	}

	@Override
	protected void chart()
	{
		Constant.getTabelNum();
		Constant.getTabelNum();
	}

}
