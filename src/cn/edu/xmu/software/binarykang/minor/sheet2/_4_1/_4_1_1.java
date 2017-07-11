package cn.edu.xmu.software.binarykang.minor.sheet2._4_1;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_1_1 extends MinorBaseAction
{
	private final static String TABLE_LOCAL = "14-17岁数字化阅读接触率";
	private final static String TABLE_KEY = "14-17岁数字化阅读方式";
	//private final static String TABLE_KEY_TYPE = "14-17岁阅读方式及比例";
	private List<DataMap> tableLocal;
	private List<DataMap> tableInfo;
	//private List<DataMap> tableInfoType;

	public _4_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
		tableInfo = MinorUtil.listMapFactory();
		//tableInfoType = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_LOCAL, tableLocal, BEGIN_COL+1);
		int beginRow = xlsx.getRowByKey(TABLE_KEY) + 1;
		XSSFRow row;
		for (int j = beginRow; (row = xlsx.getRow(j)).getLastCellNum() != -1; j += 2)
		{
			String key = xlsx.getContent(row, BEGIN_COL);
			if (key != "")
			{
				Double rate = new Double(xlsx.getContent(xlsx.getRow(j),
						BEGIN_COL + 2));
				// System.out.println(key + ":" + rate);
				tableInfo.add(new DataMap(key, rate));
			}
		}

		//MinorUtil.readData(xlsx, TABLE_KEY_TYPE, tableInfoType, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${_4_1_1_read_way_rate}", perf.format(tableLocal.get(0).getRate()));	//615 new
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 5; i++)
		{
			tr("${_4_1_1_read_way_" + i + "}", tableInfo.get(i).getKey());
			tr("${_4_1_1_read_way_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		/*
		MinorUtil.listSort(tableInfoType);
		for (int i = 0; i < 4; i++)
		{
			tr("${_4_1_1_read_type_" + i + "}", tableInfoType.get(i).getKey());
			tr("${_4_1_1_read_type_" + i + "_rate}",
					perf.format(tableInfoType.get(i).getRate()));
		}
		*/
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
		//MinorUtil.changeChart(tableInfoType);
	}

}
