package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.Constant;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL_RATE = "1.2.1图书阅读率与阅读量 第一段文字中的图书阅读率（当地）";
	private final static String TABLE_KEY_OTHER_RATE = "1.2.1图书阅读率与阅读量 第一段文字中的图书阅读率（全国）";
	private final static String TABLE_KEY_LOCAL_NUM = "1.2.1图书阅读率与阅读量 第二段文字中的图书阅读量（当地）";
	private final static String TABLE_KEY_OTHER_NUM = "1.2.1图书阅读率与阅读量 第二段文字中的图书阅读量（全国）";
//	private final static String TABLE_KEY = "0-8岁儿童阅读量";
	private List<DataMap> tableLocalRate;
	private List<DataMap> tableOtherRate;
	private List<DataMap> tableLocalNum;
	private List<DataMap> tableOtherNum;
//	private List<DataMap> tableInfo;
	String other, local = MinorUtil.local;
	double boyNum,girlNum,cityNum,villageNum;
	double boyRate,girlRate,cityRate,villageRate;
	
	//0-8岁图书阅读率分人群 0-8岁图书阅读量分人群
	public _1_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocalRate = MinorUtil.listMapFactory();
		tableOtherNum = MinorUtil.listMapFactory();
		tableLocalNum = MinorUtil.listMapFactory();
		tableOtherRate = MinorUtil.listMapFactory();
//		tableInfo = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL_NUM, tableLocalNum, BEGIN_COL);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER_NUM, tableOtherNum,
				BEGIN_COL, " ");
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL_RATE, tableLocalRate,
				BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_OTHER_RATE, tableOtherRate,
				BEGIN_COL + 1);
//		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		boyRate = SingleValue.read(xlsx, "0-8岁图书阅读率分人群", 3, 2);
		girlRate = SingleValue.read(xlsx, "0-8岁图书阅读率分人群", 3, 3);
		cityRate = SingleValue.read(xlsx, "0-8岁图书阅读率分人群", 3, 4);
		villageRate = SingleValue.read(xlsx, "0-8岁图书阅读率分人群", 3, 5);
		boyNum = SingleValue.read(xlsx, "0-8岁图书阅读量分人群", 3, 1);
		girlNum = SingleValue.read(xlsx, "0-8岁图书阅读量分人群", 3, 2);
		cityNum = SingleValue.read(xlsx, "0-8岁图书阅读量分人群", 3, 3);
		villageNum = SingleValue.read(xlsx, "0-8岁图书阅读量分人群", 3, 4);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_4_1_2_1_other}", other);

		double readRateCut = tableLocalRate.get(0).getRate()
				- tableOtherRate.get(0).getRate();
		tr("${sheet_4_1_2_1_read_rate}",
				perf.format(tableLocalRate.get(0).getRate()));
		tr("${sheet_4_1_2_1_read_rate_other}",
				perf.format(tableOtherRate.get(0).getRate()));
		tr("${sheet_4_1_2_1_read_rate_cut}",
				delLastChar(perf.format(Math.abs(readRateCut))));
		tr("${sheet_4_1_2_1_read_rate_judge}", readRateCut > 0 ? "高" : "低");

		double readNumCut = tableLocalNum.get(0).getRate()
				- tableOtherNum.get(0).getRate();
		tr("${sheet_4_1_2_1_read_num}",
				twof.format(tableLocalNum.get(0).getRate()));
		tr("${sheet_4_1_2_1_read_num_other}",
				twof.format(tableOtherNum.get(0).getRate()));
		tr("${sheet_4_1_2_1_read_num_cut}", twof.format(Math.abs(readNumCut)));
		tr("${sheet_4_1_2_1_read_num_judge}", readNumCut > 0 ? "高" : "低");

		
		
		tr("${sheet_4_1_2_1_read_rate_boy}", perf.format(boyRate));
		tr("${sheet_4_1_2_1_read_rate_girl}", perf.format(girlRate));
		tr("${sheet_4_1_2_1_read_rate_city}", perf.format(cityRate));
		tr("${sheet_4_1_2_1_read_rate_village}",perf.format(villageRate));
		
		//new add
		tr("${sheet_4_1_2_1_read_rate_gender_judge}",
				boyRate - girlRate > 0 ? "高" : "低");
		tr("${sheet_4_1_2_1_read__rate_gender_cut}",
				onef.format(100*Math.abs(boyRate - girlRate)));
		tr("${sheet_4_1_2_1_read_rate_city_village_judge}",
				cityRate - villageRate > 0 ? "高" : "低");
		tr("${sheet_4_1_2_1_read__rate_city_village_cut}",
				onef.format(100*Math.abs(cityRate - villageRate)));
		
		tr("${sheet_4_1_2_1_read_num_boy}", twof.format(boyNum));
		tr("${sheet_4_1_2_1_read_num_girl}", twof.format(girlNum));
		tr("${sheet_4_1_2_1_read_num_city}", twof.format(cityNum));
		tr("${sheet_4_1_2_1_read_num_village}",twof.format(villageNum));
		
		//new add
		tr("${sheet_4_1_2_1_read_num_gender_judge}",
				boyNum - girlNum > 0 ? "高" : "低");
		tr("${sheet_4_1_2_1_read_num_gender_cut}",
				twof.format(Math.abs(boyNum - girlNum)));
		tr("${sheet_4_1_2_1_read_num_city_village_judge}",
				cityNum - villageNum > 0 ? "高" : "低");
		tr("${sheet_4_1_2_1_read_num_city_village_cut}",
				twof.format(Math.abs(cityNum - villageNum)));
		
//		double neverRead = tableInfo.get(tableInfo.size() - 1).getRate();
//		double notClear = tableInfo.get(tableInfo.size() - 2).getRate();
//		tableInfo.remove(tableInfo.size() - 1);
//		tableInfo.remove(tableInfo.size() - 1);
//		tr("${sheet_4_1_2_1_read_num_never_read_rate}", perf.format(neverRead));
//		tr("${sheet_4_1_2_1_read_num_not_clear_rate}", perf.format(notClear));
//		MinorUtil.listSort(tableInfo);

//		for (int i = 0; i < 3; i++)
//		{
//			tr("${sheet_4_1_2_1_read_num_" + i + "_rate}",
//					perf.format(tableInfo.get(i).getRate()));
//			tr("${sheet_4_1_2_1_read_num_" + i + "}", tableInfo.get(i).getKey());
//		}
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
		Constant.getTabelNum();
		Constant.getTabelNum();
	}
}
