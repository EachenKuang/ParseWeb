package cn.edu.xmu.software.binarykang.minor.sheet2._2_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.parse.Minor;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_1_4 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "14-17岁不读书的原因（当地）";
	//private final static String TABLE_KEY_OTHER = "14-17岁不读书的原因（全国）";

	private List<DataMap> localTable;
	//private List<DataMap> otherTable;
	//private String otherCity = MinorUtil.other;

	public _2_1_4(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		localTable = MinorUtil.listMapFactory();
		//otherTable = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, localTable, BEGIN_COL + 1);
		//MinorUtil.readData(xlsx, TABLE_KEY_OTHER, otherTable, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		//docx.replace("${never_read_reason_class}", perf.format(localTable.get(6).getRate()));
		//docx.replace("${never_read_reason_local_atmosphere}", perf.format(localTable.get(8).getRate()));
		//docx.replace("${never_read_reason_local_no_habit}", perf.format(localTable.get(0).getRate()));
		//docx.replace("${never_read_reason_local_dont_known}", perf.format(localTable.get(1).getRate()));
		//docx.replace("${never_read_reason_local_no_interst}", perf.format(localTable.get(3).getRate()));
		//docx.replace("${never_read_reason_tv}", perf.format(localTable.get(7).getRate()));
		//docx.replace("${never_read_reason_local_internet}", 
		//		perf.format(MinorUtil.getByKey("因上网/玩游戏等而没时间读书", localTable).getRate()));
		docx.replace("${never_read_reason_useless}", 
				perf.format(MinorUtil.getByKey("读书没用", localTable).getRate()));
		MinorUtil.listSort(localTable);
		for (int i = 0; i < 7; i++)
		{
			docx.replace("${never_read_reason_local_" + i + "}", localTable
					.get(i).getKey());
			docx.replace("${never_read_reason_local_" + i + "_rate}",
					perf.format(localTable.get(i).getRate()));
		}
		int i = 1;
		int count = 0;
		while(count < 3){
			if(!localTable.get(localTable.size()-i).getKey().equals("读书没用")){
				docx.replace("${never_read_reason_local_last_" + count + "}", 
						localTable.get(localTable.size()-i).getKey());
				count++;
			}
			i++;
		}
		//docx.replace(
		//		"${never_read_reason_other_0_rate}",
		//		perf.format(MinorUtil.getByKey(localTable.get(0).getKey(),
		//				otherTable).getRate()));
		//double localCutOther = localTable.get(0).getRate()
		//		- MinorUtil.getByKey(localTable.get(0).getKey(), otherTable)
		//				.getRate();
		//String judge = localCutOther > 0 ? "高" : "低";
		//docx.replace("${never_read_reason_judge_0}", judge);
		//docx.replace("${never_read_reason_local_other_0}",
		//		delLastChar(perf.format(Math.abs(localCutOther))));

		//docx.replace("${never_read_reason_other_city}", otherCity);
	}

	@Override
	protected void chart()
	{
//		String[] localKey = new String[localTable.size()];
//		String[][] localValue = new String[localTable.size()][1];
//		for (int i = 0; i < localTable.size(); i++)
//		{
//			localKey[i] = localTable.get(i).getKey();
//			localValue[i][0] = perf.format(localTable.get(i).getRate());
//		}
//
//		MinorUtil.changeTable(localTable.size(), 1, localKey, localValue, docx,
//				this.getClass());
		MinorUtil.table(docx,localTable,
				"Resource/singleValueStandard.xml","Resource/singleValueStandard.xml",GF.p);
	}
}
