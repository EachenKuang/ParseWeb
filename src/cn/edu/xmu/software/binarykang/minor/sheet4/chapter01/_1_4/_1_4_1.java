package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_4;

import java.util.List;

import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_4_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "0-8岁家长引导儿童阅读的主要目的";
	private List<DataMap> tableInfo;

	public _1_4_1(Docx docx, Xlsx xlsx)
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
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 5; i++)
		{
			tr("${sheet_4_1_4_1_read_reason_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet_4_1_4_1_read_reason_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
	}

//	@Override
//	public void process()
//	{
//		readData();
//		chart();
//		replace();
//		trReplace();
//	};

	@Override
	protected void chart()
	{
//		String[] key = new String[tableInfo.size()];
//		String[][] value = new String[tableInfo.size()][1];
//		for (int i = 0; i < tableInfo.size(); i++)
//		{
//			key[i] = tableInfo.get(i).getKey();
//			value[i][0] = perf.format(tableInfo.get(i).getRate());
//		}
//
//		MinorUtil.changeTable(tableInfo.size(), 1, key, value, docx,
//				this.getClass(), 4);
		MinorUtil.table(docx,tableInfo,
				"Resource/singleValueStandard.xml","Resource/singleValueStandard.xml",GF.p);
	}
}
