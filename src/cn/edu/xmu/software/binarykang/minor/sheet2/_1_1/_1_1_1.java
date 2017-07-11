package cn.edu.xmu.software.binarykang.minor.sheet2._1_1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

public class _1_1_1 extends MinorBaseAction
{
	//private final String TABLE_KEY_WORKDAY = "14-17岁媒介接触时长——工作日";
	//private final String TABLE_KEY_OFFDAY = "14-17岁媒介接触时长——休息日";
	private final String TABLE_KEY_LOCAL = "14-17岁媒介接触时长";
	//private List<DataMap> tableWorkday;
	//private List<DataMap> tableOffday;
	private List<DataMap> tableLocal;

	public _1_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		//tableWorkday = MinorUtil.listMapFactory();
		//tableOffday = MinorUtil.listMapFactory();
		tableLocal = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		//MinorUtil.readData(xlsx, TABLE_KEY_OFFDAY, tableOffday, BEGIN_COL);
		//MinorUtil.readData(xlsx, TABLE_KEY_WORKDAY, tableWorkday, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL);
		
		MinorUtil.local = xlsx.getContent(xlsx.getRow(0), 1);
		MinorUtil.other = xlsx.getContent(xlsx.getRow(0), 2);

	}

	@Override
	protected void replace()
	{
		MinorUtil.listSort(tableLocal);

		tr("${sheet_1_1_1_media_content_0_1_diff}", 
				twof.format(tableLocal.get(0).getRate()-tableLocal.get(1).getRate()));
		for (int i = 0; i < 8; i++)
		{
			tr("${sheet_1_1_1_media_content_" + i + "}", tableLocal.get(i).getKey());
			tr("${sheet_1_1_1_media_content_" + i + "_time}",
					twof.format(tableLocal.get(i).getRate()));
			//tr("${sheet_1_1_1_media_content_" + i + "}", tableWorkday.get(i)
			//		.getKey());
			//tr("${sheet_1_1_1_media_content_" + i + "_time}",
			//		twof.format(tableWorkday.get(i).getRate()));
			//tr("${sheet_1_1_1_media_content_" + i + "_time_offday}",
			//		twof.format(MinorUtil.getByKey(
			//				tableWorkday.get(i).getKey(), tableOffday)
			//				.getRate()));
		}
		tr("${sheet_1_1_1_media_content_8}", tableLocal.get(8).getKey());
		tr("${sheet_1_1_1_media_content_9}", tableLocal.get(9).getKey());
		//tr("${sheet_1_1_1_media_content_2_cut_1}",
		//		twof.format(Math.abs(tableWorkday.get(2).getRate()
		//				- tableWorkday.get(1).getRate())));
		//double offDayCut = MinorUtil.getByKey(tableWorkday.get(1).getKey(),
		//		tableOffday).getRate()
		//		- MinorUtil.getByKey(tableWorkday.get(2).getKey(), tableOffday)
		//				.getRate();
		//tr("${sheet_1_1_1_media_content_2_cut_1_offday}",
		//		twof.format(Math.abs(offDayCut)));
		//tr("${sheet_1_1_1_media_content_2_time_judge}", offDayCut > 0 ? "多"
		//		: "少");
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
		MinorUtil.changeChart(tableLocal);
		/*
		OpenXML xml = OpenXML.getMinorXml();
		try
		{
			Chart chart = new Chart(xml.open());

			ChartTool.changeChart(chart, tableWorkday, 0);
			ChartTool.changeChart(chart, tableOffday, 1);
			ChartTool.changeChart(chart, table, 0);

			chart.changeRef(2, tableWorkday.size());
			chart.changeRef(2, table.size());
			// 创建一个Excel表格用来存放数据
			ChartXlsxForList chartXlsx = new ChartXlsxForList();
			chartXlsx.setData(chartAdapterDouble(tableWorkday, tableOffday), 2);
			chartXlsx.save();

			xml.saveAndClose();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		*/
	}
	/*
	public static Map<String, List<BaseRow>> chartAdapterDouble(
			List<?>... table)
	{
		Map<String, List<BaseRow>> map = new LinkedHashMap<String, List<BaseRow>>();
		int tableSize = table[0].size();

		@SuppressWarnings("unchecked")
		List<BaseRow>[] baseRows = (List<BaseRow>[]) new ArrayList<?>[table.length];

		for (int j = 0; j < table.length; j++)
		{
			baseRows[j] = new ArrayList<BaseRow>();
			for (int i = 0; i < tableSize; i++)
			{
				DataMap row = (DataMap) table[j].get(i);
				BaseRow baseRow = new BaseRow();
				baseRow.key = row.getKey();
				baseRow.value = row.getRate();
				baseRows[j].add(baseRow);
			}
		}
		map.put("工作日", baseRows[0]);
		map.put("休息日", baseRows[1]);

		return map;
	}*/
}
