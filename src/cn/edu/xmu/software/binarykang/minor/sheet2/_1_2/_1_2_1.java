package cn.edu.xmu.software.binarykang.minor.sheet2._1_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.common.formatter.Format;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_2_1 extends MinorBaseAction
{
	private final String TABLE_KEY_LOCAL = "14-17岁媒介接触率";
	private List<DataMap> tableLocal;
	private List<DataMap> tableTradition;
	
	/*
	private final static String[] TABLE_KEY =
	{ "14-17岁媒介使用目的——了解国内外新闻时事", "14-17岁媒介使用目的——了解国内外观点和思潮",
			"14-17岁媒介使用目的——了解与工作学习有关的信息", "14-17岁媒介使用目的——了解生活/消费资讯",
			"14-17岁媒介使用目的——了解时尚流行趋势", "14-17岁媒介使用目的——休闲娱乐" };
	final int tableNum = TABLE_KEY.length;

	private List<Map<String, Double>> tableList;
	private Map<String, List<Double>> detailInfo;
	*/
	// private Map<String, Double> tableInfo[] = new Map<String,
	// Double>[tableNum];

	public _1_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		//tableList = new ArrayList<Map<String, Double>>();
		//detailInfo = new HashMap<String, List<Double>>();
		tableLocal = MinorUtil.listMapFactory();
		tableTradition = MinorUtil.listMapFactory();
		
		//for (int i = 0; i < tableNum; ++i)
		//{
		//	tableList.add(new HashMap<String, Double>());
		//}
	}

	@Override
	protected void readData()
	{
		//MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL);
		int beginRow = xlsx.getRowByKey(TABLE_KEY_LOCAL);
		XSSFRow row = null;
		for (int i = beginRow + 1; (row = xlsx.getRow(i)).getLastCellNum() != -1; i += 2)
		{
			String key = xlsx.getContent(row, BEGIN_COL);
			Double rate = new Double(xlsx.getContent(row, BEGIN_COL + 2));
			if(key != "")
				tableLocal.add(new DataMap(key, rate));
		}
		for (int i = 0; i<3; i++)
		{
			tableTradition.add(tableLocal.get(i));
		}
				
		/*
		int beginRow;
		XSSFRow row;
		for (int i = 0; i < tableNum; ++i)
		{
			beginRow = xlsx.getRowByKey(TABLE_KEY[i]) + 1;
			for (int j = beginRow + 1; (row = xlsx.getRow(j)).getLastCellNum() != -1; j++)
			{
				String key = xlsx.getContent(row, BEGIN_COL + 1);
				if (key != "")
				{
					Double avgTime = new Double(xlsx.getContent(
							xlsx.getRow(j), BEGIN_COL + 2));
//					System.out.println(key + ":" + avgTime);
					tableList.get(i).put(key, avgTime);
				}
			}
		
		}

		Object[] tableName = tableList.get(0).keySet().toArray();
		for (int i = 0; i < tableName.length; ++i)
		{
			List<Double> num = new ArrayList<Double>();
			for (int j = 0; j < tableNum; ++j)
			{
				num.add(tableList.get(j).get(tableName[i]));
			}
//			System.out.println(tableName[i]);
			detailInfo.put(tableName[i].toString(), num);
		}*/
	}

	@Override
	protected void replace()
	{
		MinorUtil.listSort(tableLocal);
		for (int i = 0; i < 3; i++)
		{
			tr("${sheet_1_1_1_media_contact_" + i + "}", tableLocal.get(i).getKey());
			tr("${sheet_1_1_1_media_contact_" + i + "_rate}",
					perf.format(tableLocal.get(i).getRate()));
		}
		MinorUtil.listSort(tableTradition);
		for (int i = 0; i < 3; i++)
		{
			tr("${sheet_1_1_1_media_contact_tradition_" + i + "}", tableTradition.get(i).getKey());
			tr("${sheet_1_1_1_media_contact_tradition_" + i + "_rate}", 
					perf.format(tableTradition.get(i).getRate()));
		}
	}

	@Override
	protected void chart()
	{
//		String[] localKey = new String[tableLocal.size()];
//		String[][] localValue = new String[tableLocal.size()][1];
//		for (int i = 0; i < tableLocal.size(); i++)
//		{
//			localKey[i] = tableLocal.get(i).getKey();
//			localValue[i][0] = perf.format(tableLocal.get(i).getRate());
//		}
//
//		MinorUtil.changeTable(tableLocal.size(), 1, localKey, localValue, docx,
//				this.getClass());
		
		MinorUtil.table(docx,tableLocal,
				"Resource/singleValueStandard.xml","Resource/singleValueStandard.xml",GF.p);
				//"Resource/odd.xml","Resource/even.xml",GF.p);
		

//		MinorUtil.changeTable(docx, tableLocal,"Resource/minor/sheet2/_1_2/_1_2_1_evenrow.xml", "Resource/minor/sheet2/_1_2/_1_2_1_oddrow.xml",  GF.p );
//		"Resource/minor/sheet2/_1_2/_1_2_1_evenrow.xml"
		/*
		Object[] tableName = tableList.get(0).keySet().toArray();

		String[] key = new String[tableName.length];
		String[][] value = new String[tableName.length][tableNum];
		for (int i = 0; i < tableName.length; i++)
		{
			key[i] = tableName[i].toString();
//			System.out.println(key[i]);
			for (int j = 0; j < tableNum; j++)
				value[i][j] = perf.format(detailInfo.get(tableName[i]).get(j));
		}

		MinorUtil.changeTable(tableName.length, tableNum, key, value, docx,
				this.getClass());
		*/
	}
}
