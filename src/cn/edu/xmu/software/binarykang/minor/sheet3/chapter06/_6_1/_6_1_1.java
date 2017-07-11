package cn.edu.xmu.software.binarykang.minor.sheet3.chapter06._6_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _6_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LIBRARY = "6.1 学校图书馆使用情况 第一段文字中的设有图书馆的比例";
	private final static String TABLE_KEY = "9-13岁学校图书馆的使用度";
	private final static String SATISFICATION_KEY = "9-13岁对学校图书馆的满意度";
	private final static String FREQUENCE_KEY = "9-13岁使用学校图书馆的频次均值";
	
	private List<DataMap> tableLibrary;
	private List<DataMap> tableInfo;
	private List<DataMap> tableSatisfication;
	private List<DataMap> tableFrequence;

	public _6_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableLibrary = MinorUtil.listMapFactory();
		tableInfo = MinorUtil.listMapFactory();
		tableFrequence = MinorUtil.listMapFactory();
		tableSatisfication 	= MinorUtil.listMapFactory();	
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_LIBRARY, tableLibrary, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, SATISFICATION_KEY, tableSatisfication, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, FREQUENCE_KEY, tableFrequence, BEGIN_COL);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_3_6_1_1_have_library_rate}",
				perf.format(tableLibrary.get(0).getRate()));
		tr("${sheet_3_6_1_1_library_frequence}",
				twof.format(tableFrequence.get(0).getRate()));
		
		double goLibrary = 1 - tableInfo.get(tableInfo.size() - 1).getRate();
		tr("${sheet_3_6_1_1_go_library_rate}", perf.format(goLibrary));
//		tr("${sheet_3_6_1_1_never_go_library_rate}", perf.format(1 - goLibrary));

		for (int i = 0; i < 6; i++)
		{
//			tr("${sheet_3_6_1_1_go_library_" + i + "}", tableInfo.get(i)
//					.getKey());
			tr("${sheet_3_6_1_1_go_library_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tr("${sheet_3_6_1_1_library_week}",
				perf.format(tableInfo.get(0).getRate()
						+tableInfo.get(1).getRate()
						+tableInfo.get(2).getRate()));
		for (int i = 0; i < 4; i++)
		{
			tr("${sheet_3_6_1_1_go_library_satisfication_" + i + "_rate}",
					perf.format(tableSatisfication.get(i).getRate()));
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
//		String[] key = new String[tableInfo.size()];
//		String[][] value = new String[tableInfo.size()][1];
//		for (int i = 0; i < tableInfo.size(); i++)
//		{
//			key[i] = tableInfo.get(i).getKey();
//			value[i][0] = perf.format(tableInfo.get(i).getRate());
//		}
//
//		MinorUtil.changeTable(tableInfo.size(), 1, key, value, docx,
//				this.getClass(), 3);
//		
//		
		MinorUtil.table(docx,tableInfo,
				"Resource/singleValueStandard.xml","Resource/singleValueStandard.xml",GF.p);
		MinorUtil.changeChart(tableSatisfication);
	}
}
