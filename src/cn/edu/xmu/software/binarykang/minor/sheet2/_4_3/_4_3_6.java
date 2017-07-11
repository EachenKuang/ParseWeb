package cn.edu.xmu.software.binarykang.minor.sheet2._4_3;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_3_6 extends MinorBaseAction
{
	private final static String TABLE_KEY_TYPE = "14-17岁手机阅读小说类型";
	private List<DataMap> tableType;

	public _4_3_6(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		//tableMale = MinorUtil.listMapFactory();
		//tableFemale = MinorUtil.listMapFactory();
		tableType = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_TYPE, tableType, BEGIN_COL + 1);
		
	}

	@Override
	protected void replace()
	{
		tr("${_4_3_6_no_read}", perf.format(1-tableType.get(tableType.size()-1).getRate()));
		DataMap tmp = tableType.get(tableType.size()-1);
		tableType.remove(tmp);
		MinorUtil.listSort(tableType);
		for (int i = 0; i < 3; i++)
		{
			tr("${_4_3_6_story_type_" + i + "}", tableType.get(i).getKey());
			tr("${_4_3_6_story_type_" + i + "_rate}",
					perf.format(tableType.get(i).getRate()));
		}
//		tableType.add(tmp);
		
	}

	@Override
	protected void chart()
	{
	
//		String[] key = new String[tableType.size()];
//		String[][] value = new String[tableType.size()][1];
//		for (int i = 0; i < tableType.size(); ++i)
//		{
//			key[i] = tableType.get(i).getKey();
//			value[i][0] = perf.format(tableType.get(i).getRate());
//		}
//		MinorUtil.changeTable(tableType.size(), 1, key, value, docx, this.getClass());
		MinorUtil.table(docx,tableType,
				"Resource/singleValueStandard.xml","Resource/singleValueStandard.xml",GF.p);
		
	}
}
