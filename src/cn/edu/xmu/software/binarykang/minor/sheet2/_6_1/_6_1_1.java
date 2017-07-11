package cn.edu.xmu.software.binarykang.minor.sheet2._6_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _6_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "14-17岁最喜爱的动漫形式（当地）";
	private final static String TABLE_KEY_OTHER = "14-17岁最喜爱的动漫形式（全国）";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
	//private String other;

	public _6_1_1(Docx docx, Xlsx xlsx)
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
		//other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
		//		BEGIN_COL + 1, " ");
	}

	@Override
	protected void replace()
	{
		tr("${_6_1_1_no_favor_cartoon_local_rate}", 
				perf.format(tableLocal.get(tableLocal.size()-1).getRate()));
		tr("${_6_1_1_no_favor_cartoon_country_rate}", 
				perf.format(tableOther.get(tableOther.size()-1).getRate()));
		MinorUtil.listSort(tableLocal);
		//tr("${_6_1_1_other}", other);
		//double localCutOther[] = new double[4];
		//String judge[] = new String[4];
		for (int i = 0; i < 5; ++i)
		{
			tr("${_6_1_1_favor_cartoon_" + i + "_local}", tableLocal.get(i)
					.getKey());
			tr("${_6_1_1_favor_cartoon_" + i + "_local_rate}",
					perf.format(tableLocal.get(i).getRate()));
			//tr("${_6_1_1_favor_cartoon_" + i + "_other_rate}",
			//		perf.format(MinorUtil.getByKey(tableLocal.get(i).getKey(),
			//				tableOther).getRate()));
			//localCutOther[i] = tableLocal.get(i).getRate()
			//		- MinorUtil
			//				.getByKey(tableLocal.get(i).getKey(), tableOther)
			//				.getRate();
			//judge[i] = localCutOther[i] > 0 ? "高" : "低";
		}
		//tr("${_6_1_1_favor_cartoon_0_judge}", judge[0]);
		//tr("${_6_1_1_favor_cartoon_3_judge}", judge[3]);
		//tr("${_6_1_1_favor_cartoon_3_local_cut_other}",
		//		delLastChar(perf.format(Math.abs(localCutOther[3]))));
	}

	@Override
	public void process() {
		readData();
		chart();
		replace();
		trReplace();
	};
	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal, tableOther);
	}

}
