package cn.edu.xmu.software.binarykang.minor.sheet2._7_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.ChartTool;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Chart;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.OpenXML;
import cn.edu.xmu.software.binarykang.xlsx.ChartXlsxForList;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _7_2_5 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁制约青少年参与读书活动的因素";
	private List<DataMap> tableTotal;
	private List<DataMap> tableCity;
	private List<DataMap> tableVillage;

	public _7_2_5(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableTotal = MinorUtil.listMapFactory();
		tableCity = MinorUtil.listMapFactory();
		tableVillage = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		int beginRow = xlsx.getRowByKey(TABLE_KEY) + 3;
		MinorUtil.readData(xlsx, beginRow, tableCity, BEGIN_COL + 1);
		XSSFRow row;
		for (int j = beginRow; (row = xlsx.getRow(j)).getLastCellNum() != -1; ++j)
		{
			String key = xlsx.getContent(row, BEGIN_COL + 1);
			if (key != "")
			{
				Double rate = new Double(xlsx.getContent(xlsx.getRow(j),
						BEGIN_COL + 3));
				// System.out.println(key + ":" + rate);
				tableVillage.add(new DataMap(key, rate));
			}
		}

		for (int j = beginRow; (row = xlsx.getRow(j)).getLastCellNum() != -1; ++j)
		{
			String key = xlsx.getContent(row, BEGIN_COL + 1);
			if (key != "")
			{
				Double rate = new Double(xlsx.getContent(xlsx.getRow(j),
						BEGIN_COL + 4));
				// System.out.println(key + ":" + rate);
				tableTotal.add(new DataMap(key, rate));
			}
		}
	}

	@Override
	protected void replace()
	{
		MinorUtil.listSort(tableTotal);
		for (int i = 0; i < 4; i++)
		{
			String key = tableTotal.get(i).getKey();
			tr("${_7_2_5_restrict_reason_" + i + "_avg}", key);
			tr("${_7_2_5_restrict_reason_" + i + "_avg_rate}",
					perf.format(tableTotal.get(i).getRate()));
			//double cityRate = MinorUtil.getByKey(key, tableCity).getRate();
			//double villageRate = MinorUtil.getByKey(key, tableVillage)
			//		.getRate();
			//tr("${_7_2_5_restrict_reason_" + i + "_judge}",
			//		(i % 2 == 0 ? cityRate < villageRate
			//				: cityRate > villageRate) ? "高" : "低");
			//tr("${_7_2_5_restrict_reason_" + i + "_city_rate}",
			//		perf.format(cityRate));
			//tr("${_7_2_5_restrict_reason_" + i + "_village_rate}",
			//		perf.format(villageRate));
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
		Collections.reverse(tableTotal);
		MinorUtil.changeChart(tableTotal);
//		OpenXML xml = OpenXML.getMinorXml();
//		try
//		{
//			Chart chart = new Chart(xml.open());
//			chart.replace("${avg}", "总共");
//			chart.replace("${city}", "城镇");
//			chart.replace("${village}", "乡村");
//			ChartTool.changeChart(chart, tableTotal, 0);
//			ChartTool.changeChart(chart, tableCity, 1);
//			ChartTool.changeChart(chart, tableVillage, 2);
//
//			
//			chart.changeRef(3, tableTotal.size());
//			ChartXlsxForList chartXlsx = new ChartXlsxForList();
//			chartXlsx.setData(chartAdapterDouble(tableTotal,tableCity,tableVillage));
//			chartXlsx.save();
//
//			xml.saveAndClose();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
	}
	
//	public static Map<String, List<BaseRow>> chartAdapterDouble(
//			List<?>... table)
//	{
//		Map<String, List<BaseRow>> map = new LinkedHashMap<String, List<BaseRow>>();
//
//		@SuppressWarnings("unchecked")
//		List<BaseRow>[] baseRows = (List<BaseRow>[]) new ArrayList<?>[table.length];
//
//		for (int j = 0; j < table.length; j++)
//		{
//			baseRows[j] = new ArrayList<BaseRow>();
//			for (int i = 0; i < table[0].size(); i++)
//			{
//				DataMap row = (DataMap) table[j].get(i);
//				BaseRow baseRow = new BaseRow();
//				baseRow.key = row.getKey();
//				baseRow.value = row.getRate();
//				baseRows[j].add(baseRow);
//			}
//		}
//
//		map.put("总共", baseRows[0]);
//		map.put("城镇", baseRows[1]);
//		map.put("乡村", baseRows[2]);
//		
//		return map;
//	}
}
