package cn.edu.xmu.software.binarykang.minor.sheet4.chapter02._2_1;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.Constant;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL_RATE = "2.1期刊阅读率与阅读量 第一段文字中的期刊阅读率（当地）";
	private final static String TABLE_KEY_OTHER_RATE = "2.1期刊阅读率与阅读量 第一段文字中的期刊阅读率（全国）";
	private final static String TABLE_KEY_LOCAL_NUM = "2.1期刊阅读率与阅读量 第二段文字中的人均期刊阅读量（当地）";
	private final static String TABLE_KEY_OTHER_NUM = "2.1期刊阅读率与阅读量 第二段文字中的人均期刊阅读量（全国）";
	private final static String TABLE_KEY_TYPE = "2.1期刊阅读率与阅读量 第二段文字中的不同人群人均期刊阅读量";
//	private final static String TABLE_KEY = "0-8岁儿童期刊阅读量";
	private List<DataMap> tableLocalRate;
	private List<DataMap> tableOtherRate;
	private List<DataMap> tableLocalNum;
	private List<DataMap> tableOtherNum;
	private List<DataMap> tableType;
	private List<DataMap> tableInfo;
	private double readRateBoy;
	private double readRateGirl;
	private double readRateCity;
	private double readRateVillage;
	
	String other, local = MinorUtil.local;

	public _2_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		
		
		tableLocalRate = MinorUtil.listMapFactory();
		tableOtherNum = MinorUtil.listMapFactory();
		tableLocalNum = MinorUtil.listMapFactory();
		tableOtherRate = MinorUtil.listMapFactory();
		tableType = MinorUtil.listMapFactory();
//		tableInfo = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		readRateBoy = SingleValue.read(xlsx, "0-8岁期刊阅读率分人群", 3, 2);
		readRateGirl= SingleValue.read(xlsx, "0-8岁期刊阅读率分人群", 3, 3);
		readRateCity = SingleValue.read(xlsx, "0-8岁期刊阅读率分人群", 3, 4);
		readRateVillage = SingleValue.read(xlsx, "0-8岁期刊阅读率分人群", 3, 5);
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL_NUM, tableLocalNum, BEGIN_COL);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER_NUM, tableOtherNum,
				BEGIN_COL, " ");
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL_RATE, tableLocalRate,
				BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_OTHER_RATE, tableOtherRate,
				BEGIN_COL + 1);
		MinorUtil.readData(xlsx, xlsx.getRowByKey(TABLE_KEY_TYPE) + 2,
				tableType, BEGIN_COL + 1);
//		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_4_2_1_1_other}", other);

		double readRateCut = tableLocalRate.get(0).getRate()
				- tableOtherRate.get(0).getRate();
		tr("${sheet_4_2_1_1_magazine_read_rate}",
				perf.format(tableLocalRate.get(0).getRate()));
		tr("${sheet_4_2_1_1_magazine_read_rate_other}",
				perf.format(tableOtherRate.get(0).getRate()));
		tr("${sheet_4_2_1_1_magazine_read_rate_cut}",
				delLastChar(perf.format(Math.abs(readRateCut))));
		tr("${sheet_4_2_1_1_magazine_read_rate_judge}", readRateCut > 0 ? "高"
				: "低");
		
		tr("${sheet_4_2_1_1_read_magazine_rate_boy}", perf.format(readRateBoy));
		tr("${sheet_4_2_1_1_read_magazine_rate_girl}", perf.format(readRateGirl));
		tr("${sheet_4_2_1_1_read_magazine_rate_city}", perf.format(readRateCity));
		tr("${sheet_4_2_1_1_read_magazine_rate_village}",perf.format(readRateVillage));
		
		//new add
		tr("${sheet_4_2_1_1_read_magazine_rate_gender_judge}",
				readRateBoy - readRateGirl > 0 ? "高" : "低");
		tr("${sheet_4_2_1_1_read_magazine_rate_gender_cut}",
				onef.format(100*Math.abs(readRateBoy - readRateGirl)));
		tr("${sheet_4_2_1_1_read_magazine_rate_city_village_judge}",
				readRateCity - readRateVillage > 0 ? "高" : "低");
		tr("${sheet_4_2_1_1_read_magazine_rate_city_village_cut}",
				onef.format(100*Math.abs(readRateCity - readRateVillage)));
		
		double readNumCut = tableLocalNum.get(0).getRate()
				- tableOtherNum.get(0).getRate();
		tr("${sheet_4_2_1_1_magazine_read_num}",
				twof.format(tableLocalNum.get(0).getRate()));
		tr("${sheet_4_2_1_1_magazine_read_num_other}",
				twof.format(tableOtherNum.get(0).getRate()));
		tr("${sheet_4_2_1_1_magazine_read_num_judge}", readNumCut > 0 ? "高"
				: "低");
		tr("${sheet_4_2_1_1_magazine_read_num_cut}",
				twof.format(Math.abs(readNumCut)));

		
		tr("${sheet_4_2_1_1_magazine_read_city}",
				twof.format(tableType.get(0).getRate()));
		tr("${sheet_4_2_1_1_magazine_read_village}",
				twof.format(tableType.get(1).getRate()));
		tr("${sheet_4_2_1_1_read_magazine_num_city_village_judge}",
				tableType.get(0).getRate() - tableType.get(1).getRate() > 0 ? "高" : "低");
		tr("${sheet_4_2_1_1_read_magazine_num_city_village_cut}",
				twof.format(Math.abs(tableType.get(0).getRate() - tableType.get(1).getRate())));
		
		double boyRate = tableType.get(2).getRate();
		double girlRate = tableType.get(3).getRate();
		double genderCut = boyRate - girlRate;

		tr("${sheet_4_2_1_1_magazine_read_num_boy}", twof.format(boyRate));
		tr("${sheet_4_2_1_1_magazine_read_num_girl}", twof.format(girlRate));
		tr("${sheet_4_2_1_1_magazine_read_gender_cut}",
				twof.format(Math.abs(genderCut)));
		tr("${sheet_4_2_1_1_magazine_read_gender_judge}", genderCut > 0 ? "高"
				: "低");
	}

	@Override
	protected void chart()
	{
//		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableLocalRate);
		Constant.getTabelNum();
		
	}
}
