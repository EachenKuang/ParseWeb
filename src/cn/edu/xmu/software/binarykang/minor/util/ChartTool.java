package cn.edu.xmu.software.binarykang.minor.util;

import java.util.List;

import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.log.Log;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.word.Chart;
import cn.edu.xmu.software.binarykang.word.OpenXML;
import cn.edu.xmu.software.binarykang.xlsx.ChartXlsxForList;

public class ChartTool
{
	public static void changeChart(boolean genderType, List<?>... tableInfo)
	{
		OpenXML xml = OpenXML.getMinorXml();
		try
		{
			Chart chart = new Chart(xml.open());

			chart.replace("${local}", MinorUtil.local);
			chart.replace("${other}", MinorUtil.other);
			int size = tableInfo.length;

			for (int i = 0; i < size; i++)
			{
				changeChart(chart, tableInfo[i], i);
			}
			chart.changeRef(size, tableInfo[0].size());
			// 创建一个Excel表格用来存放数据
			ChartXlsxForList chartXlsx = new ChartXlsxForList();
			chartXlsx.setData(ChartXlsxTool.chartAdapterDouble(genderType,
					tableInfo));
			chartXlsx.save();

			xml.saveAndClose();
		} catch (Exception e)
		{
			Log.addException(e);
		}
	}

	public static void changeChart(int need,boolean genderType, List<?>... tableInfo)
	{
		OpenXML xml = OpenXML.getMinorXml();
		try
		{
			Chart chart = new Chart(xml.open());

//			chart.replace("${local}", MinorUtil.local);
//			chart.replace("${other}", MinorUtil.other);
			int size = tableInfo.length;

			for (int i = 0; i < size; i++)
			{
				changeChart(chart, tableInfo[i], i);
			}
			chart.changeRef(size, tableInfo[0].size());
			// 创建一个Excel表格用来存放数据
			ChartXlsxForList chartXlsx = new ChartXlsxForList();
			chartXlsx.setData(ChartXlsxTool.chartAdapterDouble(genderType,
					tableInfo));
			chartXlsx.save();

			xml.saveAndClose();
		} catch (Exception e)
		{
			Log.addException(e);
		}
	}
	/**
	 * ====================================================== 图表的替换
	 * 
	 * @param chart
	 * @param tableInfo
	 *            要替换的数据
	 * @param chartNum
	 *            当前图表中的第几个
	 */
	public static void changeChart(Chart chart, List<?> tableInfo, int chartNum)
	{
		final String C_PT = "c:pt";

		Node strCach = chart.getNodeByTagNameAndIndex("c:strCache",
				chartNum * 2 + 1);
		Node strCach_pt = chart.getChildByIndex(strCach.getChildNodes(), C_PT,
				0);
		Node numCach = chart.getNodeByTagNameAndIndex("c:numCache", chartNum);
		Node numCach_pt = chart.getChildByIndex(numCach.getChildNodes(), C_PT,
				0);

		chart.setAttr(
				chart.getChildByIndex(strCach.getChildNodes(), "c:ptCount", 0),
				"val", tableInfo.size() + "");
		chart.setAttr(
				chart.getChildByIndex(numCach.getChildNodes(), "c:ptCount", 0),
				"val", tableInfo.size() + "");

		for (int i = 0; i < tableInfo.size(); ++i)
		{
			chart.insertBefore(
					strCach,
					chart.generateC_PTNode(i + "",
							((DataMap) tableInfo.get(i)).getKey()), strCach_pt);
			chart.insertBefore(
					numCach,
					chart.generateC_PTNode(i + "",
							((DataMap) tableInfo.get(i)).getRate() + ""),
					numCach_pt);
		}
		strCach.removeChild(strCach_pt);
		numCach.removeChild(numCach_pt);

	}
}
