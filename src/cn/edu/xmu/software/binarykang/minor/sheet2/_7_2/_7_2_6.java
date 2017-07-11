package cn.edu.xmu.software.binarykang.minor.sheet2._7_2;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _7_2_6 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁老师对阅读课外书的态度";
	private List<DataMap> tableInfo;

	public _7_2_6(Docx docx, Xlsx xlsx)
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
		tr("${_7_2_6_approve_rate}",
				perf.format(MinorUtil.getByKey("非常赞成", tableInfo).getRate()
						+ MinorUtil.getByKey("比较赞成", tableInfo).getRate()));
		tr("${_7_2_6_oppose_rate}",
				perf.format(MinorUtil.getByKey("比较反对", tableInfo).getRate()
						+ MinorUtil.getByKey("非常反对", tableInfo).getRate()));
		tr("${_7_2_6_never_mind_rate}",
				perf.format(MinorUtil.getByKey("无所谓", tableInfo).getRate()));
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}
}
