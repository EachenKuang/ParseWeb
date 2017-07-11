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
	private final static String TABLE_KEY = "14-17����ʦ���Ķ��������̬��";
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
				perf.format(MinorUtil.getByKey("�ǳ��޳�", tableInfo).getRate()
						+ MinorUtil.getByKey("�Ƚ��޳�", tableInfo).getRate()));
		tr("${_7_2_6_oppose_rate}",
				perf.format(MinorUtil.getByKey("�ȽϷ���", tableInfo).getRate()
						+ MinorUtil.getByKey("�ǳ�����", tableInfo).getRate()));
		tr("${_7_2_6_never_mind_rate}",
				perf.format(MinorUtil.getByKey("����ν", tableInfo).getRate()));
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}
}
