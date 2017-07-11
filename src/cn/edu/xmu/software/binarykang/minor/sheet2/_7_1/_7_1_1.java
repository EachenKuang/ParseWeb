package cn.edu.xmu.software.binarykang.minor.sheet2._7_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _7_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17���Ķ���������";
	private List<DataMap> tableInfo;

	public _7_1_1(Docx docx, Xlsx xlsx)
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
		tr("${_7_1_1_person_evaluation_lot}", perf.format(tableInfo.get(0).getRate()));
		tr("${_7_1_1_person_evaluation_little}", 
				perf.format(MinorUtil.getByKey("�Ƚ���", tableInfo).getRate()
						+ MinorUtil.getByKey("����", tableInfo).getRate()));
		tr("${_7_1_1_person_evaluate_general}",
				perf.format(MinorUtil.getByKey("һ��", tableInfo).getRate()));
		tr("${_7_1_1_person_evaluate_many}",
				perf.format(MinorUtil.getByKey("�Ƚ϶�", tableInfo).getRate()));
		//tr("${_7_1_1_person_evaluate_less}",
		//		perf.format(MinorUtil.getByKey("�Ƚ���", tableInfo).getRate()));
		//tr("${_7_1_1_person_evaluate_tiny}",
		//		perf.format(MinorUtil.getByKey("����", tableInfo).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
