package cn.edu.xmu.software.binarykang.minor.sheet2._4_3;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_3_4 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "14-17岁手机读物价格承受程度";
	//private final static String TABLE_KEY_OTHER = "4.3.4手机读物价格承受力 第一段文字中的全国数据";
	//private Map<String, Double> tableOther;
	private List<DataMap> tableLocal;

	//private String other = MinorUtil.other;

	public _4_3_4(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
		//tableOther = new HashMap<String, Double>();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		/*
		int beginRow = xlsx.getRowByKey(TABLE_KEY_OTHER) + 1;
		XSSFRow row;
		for (int j = beginRow; (row = xlsx.getRow(j)).getLastCellNum() != -1; ++j)
		{
			String key = xlsx.getContent(row, BEGIN_COL + 1);
			if (key != "")
			{
				Double rate = new Double(xlsx.getContent(xlsx.getRow(j),
						BEGIN_COL + 2));
				// System.out.println(key + ":" + rate);
				tableOther.put(key, rate);
			}
		}
		*/
	}

	@Override
	protected void replace()
	{
		MinorUtil.listSort(tableLocal);
		int i = 0;
		for (i = 0; i < 5; i++)
		{
			tr("${_4_3_4_afford_" + i + "}", tableLocal.get(i).getKey());
			tr("${_4_3_4_afford_" + i + "_rate}",
					perf.format(tableLocal.get(i).getRate()));

			//double localCutOther = tableLocal.get(i).getRate()
			//		- tableOther.get(tableLocal.get(i).getKey());
			//String judge = localCutOther > 0 ? "高" : "低";
			//tr("${_4_3_4_afford_" + i + "_judge}", judge);
			//tr("${_4_3_4_afford_" + i + "_local_cut_other}",
			//		delLastChar(perf.format(Math.abs(localCutOther))));

			//tr("${_4_3_4_afford_" + i + "_rate_other}",
			//		perf.format(tableOther.get(tableLocal.get(i).getKey())));
		}
		//tr("${_4_3_4_other}", other);
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableLocal);
		MinorUtil.changeChart(tableLocal);
	}

}
