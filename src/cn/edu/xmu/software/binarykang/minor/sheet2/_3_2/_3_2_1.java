package cn.edu.xmu.software.binarykang.minor.sheet2._3_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "3.2.1期刊阅读率 第一段文字中的期刊阅读率（当地）";
	private final static String TABLE_KEY_OTHER = "3.2.1期刊阅读率 第一段文字中的期刊阅读率（全国）";
	private final static String TABLE_KEY_TYPE = "不同群体的期刊阅读率";
	private List<DataMap> tableInfo;
	private List<DataMap> tableInfoOther;
	private double[] tableType;
	private String other = "";

	public _3_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = MinorUtil.listMapFactory();
		tableInfoOther = MinorUtil.listMapFactory();
		tableType = new double[4];
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableInfoOther,
				BEGIN_COL + 1, " ");
		for (int i = 0; i < 4; i++)
		{
			tableType[i] = SingleValue.read(xlsx, TABLE_KEY_TYPE, 3, BEGIN_COL - 1 + i);
		}
	}

	@Override
	protected void replace()
	{
		tr("${_3_2_1_read_rate_local}", perf.format(tableInfo.get(0).getRate()));
		tr("${_3_2_1_other}", other);
		tr("${_3_2_1_read_rate_other}",
				perf.format(tableInfoOther.get(0).getRate()));
		double localCutOther = tableInfo.get(0).getRate()
				- tableInfoOther.get(0).getRate();
		String judge = localCutOther > 0 ? "多" : "少";

		docx.replace("${_3_2_1_judge}", judge);
		docx.replace("${_3_2_1_local_cut_other}",
				delLastChar(perf.format(Math.abs(localCutOther))));
		docx.replace("${_3_2_1_no_read_rate_local}",
				perf.format(tableInfo.get(1).getRate()));
		
		tr("${_3_2_1_read_rate_male_rate}", perf.format(tableType[0]));
		tr("${_3_2_1_read_rate_female_rate}", perf.format(tableType[1]));
		tr("${_3_2_1_read_rate_male_female}", 
				tableType[0]>tableType[1] ? "高":"低");
		tr("${_3_2_1_read_rate_male_female_rate}", 
				onef.format(100*Math.abs(tableType[0]-tableType[1])));
		tr("${_3_2_1_read_rate_city_rate}", perf.format(tableType[2]));
		tr("${_3_2_1_read_rate_village_rate}", perf.format(tableType[3]));
		tr("${_3_2_1_read_rate_city_village}", 
				tableType[2]>tableType[3] ? "高":"低");
		tr("${_3_2_1_read_rate_city_village_rate}",
				onef.format(100*Math.abs(tableType[2]-tableType[3])));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
