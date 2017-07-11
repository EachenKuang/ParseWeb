package cn.edu.xmu.software.binarykang.minor.sheet2._7_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _7_2_2 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "14-17岁对读书活动/读书节的认知";
	//private final static String TABLE_KEY_OTHER = "7.2.2读书活动/读书节的认知 第一段文字中全国的“应当”的比例";
	private List<DataMap> tableLocal;
	//private List<DataMap> tableOther;

	public _7_2_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableLocal = MinorUtil.listMapFactory();
		//tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		//MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		double localRate = MinorUtil.getByKey("应当", tableLocal).getRate();
		//double otherRate = MinorUtil.getByKey("应当", tableOther).getRate();
		//String judge = localRate - otherRate > 0 ? "高" : "低";
		tr("${_7_2_2_should_have_read_active}", perf.format(localRate));
		tr("${_7_2_2_should_not_have_read_active}",
				perf.format(MinorUtil.getByKey("不应当", tableLocal).getRate()));
		tr("${_7_2_2_never_mind_have_read_active}",
				perf.format(MinorUtil.getByKey("无所谓", tableLocal).getRate()));

		//tr("${_7_2_2_should_have_read_active_other}", perf.format(otherRate));
		//tr("${_7_2_2_should_have_read_active_judge}", judge);
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
	}
}
