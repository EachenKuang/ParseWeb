package cn.edu.xmu.software.binarykang.minor.sheet2._3_1;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁报纸阅读率";
	private final static String TABLE_KEY_OTHER = "3.1.1报纸阅读率 第一段文字中的全国报纸阅读率";
	private final static String TABLE_KEY_TYPE = "不同群体的报纸阅读率";
	private List<DataMap> tableInfo;
	private List<DataMap> tableInfoOther;
	private double[] tableType;
	private String other = "";

	public _3_1_1(Docx docx, Xlsx xlsx)
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
		docx.replace("${_3_1_1_read_paper_rate}",
				perf.format(tableInfo.get(0).getRate()));
		docx.replace("${_3_1_1_other}", other);

		docx.replace("${_3_1_1_read_paper_other_rate}",
				perf.format(tableInfoOther.get(0).getRate()));
		double localCutOther = tableInfo.get(0).getRate()
				- tableInfoOther.get(0).getRate();
		String judge = localCutOther > 0 ? "多" : "少";

		docx.replace("${_3_1_1_local_other_judge}", judge);
		docx.replace("${_3_1_1_local_other_cut}",
				delLastChar(perf.format(Math.abs(localCutOther))));

		docx.replace("${_3_1_1_no_read_rate}",
				perf.format(tableInfo.get(1).getRate()));
		
		docx.replace("${_3_1_1_read_paper_male_rate}", perf.format(tableType[0]));
		docx.replace("${_3_1_1_read_paper_female_rate}", perf.format(tableType[1]));
		docx.replace("${_3_1_1_read_paper_male_female}", 
				tableType[0] > tableType[1] ? "高":"低");
		docx.replace("${_3_1_1_read_paper_male_female_rate}", 
				onef.format(100*Math.abs(tableType[0] - tableType[1])));
		docx.replace("${_3_1_1_read_paper_city_rate}", perf.format(tableType[2]));
		docx.replace("${_3_1_1_read_paper_village_rate}", perf.format(tableType[3]));
		docx.replace("${_3_1_1_read_paper_city_village}", 
				tableType[2] > tableType[3] ? "高":"低");
		docx.replace("${_3_1_1_read_paper_city_village_rate}", 
				onef.format(100*Math.abs(tableType[2] - tableType[3])));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
