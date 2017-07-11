package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_7;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_7_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "0-8岁家长认为适合0—8周岁儿童的阅读形式";
	private final static String TABLE_KEY_OTHER = "1.7 家长认为适合儿童阅读的形式 第一段文字中的全国数据";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
	String other, local = MinorUtil.local;

	public _1_7_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
		tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
				BEGIN_COL + 1, " ");
	}

	@Override
	protected void replace()
	{
		tr("${sheet_4_1_7_1_other}", other);

		MinorUtil.listSort(tableLocal);

		tr("${sheet_4_1_7_1_rate_type_0}", tableLocal.get(0).getKey());
		double localRate = tableLocal.get(0).getRate();
		double otherRate = MinorUtil.getByKey(tableLocal.get(0).getKey(),
				tableOther).getRate();

		tr("${sheet_4_1_7_1_rate_type_0_rate}", perf.format(localRate));
		tr("${sheet_4_1_7_1_rate_type_0_rate_other}", perf.format(otherRate));
		tr("${sheet_4_1_7_1_rate_type_0_rate_judge}",
				localRate > otherRate ? "高" : "低");
		tr("${sheet_4_1_7_1_rate_type_0_rate_cut}",
				delLastChar(perf.format(Math.abs(localRate - otherRate))));

		for (int i = 1; i < 3; i++)
		{
			tr("${sheet_4_1_7_1_rate_type_" + i + "}", tableLocal.get(i)
					.getKey());
			tr("${sheet_4_1_7_1_rate_type_" + i + "_rate}",
					perf.format(tableLocal.get(i).getRate()));
		}
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
		MinorUtil.changeChart(tableLocal);
	}
}
