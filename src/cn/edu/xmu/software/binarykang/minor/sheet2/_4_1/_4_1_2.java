package cn.edu.xmu.software.binarykang.minor.sheet2._4_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_1_2 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁选择数字阅读的主要原因";
	private List<DataMap> tableInfo;
	private List<DataMap> tableInfoCopy;

	public _4_1_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = MinorUtil.listMapFactory();
		tableInfoCopy = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${_4_1_2_digital_read_reason_biginfo}", perf.format(tableInfo.get(8).getRate()));
		tr("${_4_1_2_digital_read_reason_lowcost}", perf.format(tableInfo.get(2).getRate()));
		
		tableInfoCopy = tableInfo;
		tableInfoCopy.remove(9);
		tableInfoCopy.remove(8);
		tableInfoCopy.remove(6);
		tableInfoCopy.remove(5);
		tableInfoCopy.remove(3);
		tableInfoCopy.remove(2);
		
		
		MinorUtil.listSort(tableInfoCopy);
		for (int i = 0; i < 4; i++)
		{
			tr("${_4_1_2_digital_read_reason_" + i + "}", tableInfoCopy.get(i).getKey());
			tr("${_4_1_2_digital_read_reason_" + i + "_rate}",
					perf.format(tableInfoCopy.get(i).getRate()));
		}
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
