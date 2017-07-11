package cn.edu.xmu.software.binarykang.minor.sheet2._7_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _7_3_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "14-17岁学校图书馆普及情况（当地）";
	private final static String TABLE_KEY_OTHER = "14-17岁学校图书馆普及情况（全国）";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;

	public _7_3_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableLocal = MinorUtil.listMapFactory();
		tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		double localRate = tableLocal.get(0).getRate();
		double otherRate = tableOther.get(0).getRate();

		tr("${_7_3_1_have_library_local}", perf.format(localRate));
		tr("${_7_3_1_have_library_other}", perf.format(otherRate));
		tr("${_7_3_1_have_library_judge}", (localRate - otherRate > 0 ? "高"
				: "低"));
		tr("${_7_3_1_have_library_local_other_rate}", 
				delLastChar(perf.format(Math.abs(localRate - otherRate))));
		tr("${_7_3_1_no_library_local}", perf.format(1 - localRate));

	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal, tableOther);
	}
}
