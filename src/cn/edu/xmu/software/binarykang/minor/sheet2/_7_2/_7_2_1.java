package cn.edu.xmu.software.binarykang.minor.sheet2._7_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _7_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁阅读活动知晓率";
	private List<DataMap> tableInfo;

	public _7_2_1(Docx docx, Xlsx xlsx)
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
		tr("${_7_2_1_hava_read_active_rate}",
				perf.format(MinorUtil.getByKey("有", tableInfo).getRate()));
		tr("${_7_2_1_no_read_active_rate}",
				perf.format(MinorUtil.getByKey("没有", tableInfo).getRate()));
		tr("${_7_2_1_dont_konw_read_active_rate}",
				perf.format(MinorUtil.getByKey("不知道是否举办过", tableInfo).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
