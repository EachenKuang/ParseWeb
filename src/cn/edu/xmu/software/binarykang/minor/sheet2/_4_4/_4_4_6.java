package cn.edu.xmu.software.binarykang.minor.sheet2._4_4;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_4_6 extends MinorBaseAction
{
	private final static String TABLE_KEY_TYPE = "14-17岁网购出版物的种类";
	//private final static String TABLE_KEY_REASON = "14-17岁网购出版物的原因";
	//private final static String TABLE_KEY_RESTRICT = "14-17岁网购出版物的制约因素";
	private List<DataMap> tableType;
	//private List<DataMap> tableReason;
	//private List<DataMap> tableRestrict;

	public _4_4_6(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableType = MinorUtil.listMapFactory();
		//tableReason = MinorUtil.listMapFactory();
		//tableRestrict = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_TYPE, tableType, BEGIN_COL + 1);
		//MinorUtil.readData(xlsx, TABLE_KEY_REASON, tableReason, BEGIN_COL + 1);
		//MinorUtil.readData(xlsx, TABLE_KEY_RESTRICT, tableRestrict,
		//		BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${_4_4_6_bought}", perf.format(1-tableType.get(tableType.size()-1).getRate()));
		DataMap tmp = tableType.get(tableType.size() - 1);
		tr("${_4_4_6_buy_type_never_rate}", perf.format(tmp.getRate()));
		tableType.remove(tmp);
		MinorUtil.listSort(tableType);
		for (int i = 0; i < 5; i++)
		{
			tr("${_4_4_6_buy_type_" + i + "}", tableType.get(i).getKey());
			tr("${_4_4_6_buy_type_" + i + "_rate}",
					perf.format(tableType.get(i).getRate()));
		}
		tableType.add(tmp);
		/*
		MinorUtil.listSort(tableReason);
		for (int i = 0; i < 6; i++)
		{
			tr("${_4_4_6_buy_reason_" + i + "}", tableReason.get(i).getKey());
			tr("${_4_4_6_buy_reason_" + i + "_rate}",
					perf.format(tableReason.get(i).getRate()));
		}
		MinorUtil.listSort(tableRestrict);
		for (int i = 0; i < 6; i++)
		{
			tr("${_4_4_6_buy_restrict_reason_" + i + "}", tableRestrict.get(i)
					.getKey());
			tr("${_4_4_6_buy_restrict_reason_" + i + "_rate}",
					perf.format(tableRestrict.get(i).getRate()));
		}*/
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableType);

		//Collections.reverse(tableReason);
		//MinorUtil.changeChart(tableReason);

		//Collections.reverse(tableRestrict);
		//MinorUtil.changeChart(tableRestrict);
	}

}
