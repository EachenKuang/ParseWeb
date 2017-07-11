package cn.edu.xmu.software.binarykang.minor.sheet3.chapter02._2_1;

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
	private final static String TABLE_KEY_LOCAL = "9-13岁期刊阅读率";
	private final static String TABLE_KEY_OTHER = "2.1期刊阅读量与阅读率 第一段文字中的全国期刊阅读率";
	private final static String TABLE_KEY_AVG_NUM = "2.1期刊阅读量与阅读率 第二段文字中的人均期刊阅读量";
	private final static String TABLE_KEY_AVG_NUM_OTHER = "2.1期刊阅读量与阅读率 第二段文字中的人均期刊阅读量——全国";
	private final static String TABLE_KEY_TYPE = "9-13岁不同群体的的人均期刊阅读量";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
	private List<DataMap> tableType;;  
//	private List<DataMap> tableAVG;
//	private List<DataMap> tableAVGOther;
	private double readRateBoy;
	private double readRateGirl;
	private double readRateCity;
	private double readRateVillage;
	private double tableAVG;
	private double tableAVGOther;
	
	private String other;

	public _2_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableLocal = MinorUtil.listMapFactory();
		tableOther = MinorUtil.listMapFactory();
		tableType = MinorUtil.listMapFactory();
//		tableAVG = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		readRateBoy = SingleValue.read(xlsx, "2.1期刊阅读率分人群", 4, 2);
		readRateGirl= SingleValue.read(xlsx, "2.1期刊阅读率分人群", 4, 3);
		readRateCity = SingleValue.read(xlsx, "2.1期刊阅读率分人群", 4, 4);
		readRateVillage = SingleValue.read(xlsx, "2.1期刊阅读率分人群", 4 ,5);
		tableAVG= SingleValue.read(xlsx, TABLE_KEY_AVG_NUM, 1, 1);
		tableAVGOther = SingleValue.read(xlsx, TABLE_KEY_AVG_NUM_OTHER, 1, 1);
		
//		MinorUtil.readData(xlsx, TABLE_KEY_AVG_NUM, tableAVG, BEGIN_COL);
//		MinorUtil.readData(xlsx, TABLE_KEY_AVG_NUM_OTHER, tableAVGOther, BEGIN_COL);
		
		
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
				BEGIN_COL + 1, " ");
		MinorUtil.readData(xlsx, xlsx.getRowByKey(TABLE_KEY_TYPE) + 2,
				tableType, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
//		tr("${sheet_3_2_1_1_read_magazine_num_avg}",
//				twof.format(tableAVG.get(0).getRate()));
//		tr("${sheet_3_2_1_1_read_magazine_num_avg_other}",
//				twof.format(tableAVGOther.get(0).getRate()));
//		tr("${sheet_3_2_1_1_read_magazine_num_avg_judge}",
//				tableAVG.get(0).getRate() - tableAVGOther.get(0).getRate() > 0 ? "高" : "低");
//		tr("${sheet_3_2_1_1_read_magazine_num_avg_cut}",
//				twof.format(Math.abs(tableAVG.get(0).getRate() - tableAVGOther.get(0).getRate())));
		tr("${sheet_3_2_1_1_read_magazine_num_avg}",
				twof.format(tableAVG));
		tr("${sheet_3_2_1_1_read_magazine_num_avg_other}",
				twof.format(tableAVGOther));
		tr("${sheet_3_2_1_1_read_magazine_num_avg_judge}",
				tableAVG - tableAVGOther > 0 ? "高" : "低");
		tr("${sheet_3_2_1_1_read_magazine_num_avg_cut}",
				twof.format(Math.abs(tableAVG - tableAVGOther)));
		
		tr("${sheet_3_2_1_1_read_magazine_rate_boy}", perf.format(readRateBoy));
		tr("${sheet_3_2_1_1_read_magazine_rate_girl}", perf.format(readRateGirl));
		tr("${sheet_3_2_1_1_read_magazine_rate_city}", perf.format(readRateCity));
		tr("${sheet_3_2_1_1_read_magazine_rate_village}",perf.format(readRateVillage));
		
		//new add
		tr("${sheet_3_2_1_1_read_magazine_rate_gender_judge}",
				readRateBoy - readRateGirl > 0 ? "高" : "低");
		tr("${sheet_3_2_1_1_read_magazine_rate_gender_cut}",
				onef.format(100*Math.abs(readRateBoy - readRateGirl)));
		tr("${sheet_3_2_1_1_read_magazine_rate_city_village_judge}",
				readRateCity - readRateVillage > 0 ? "高" : "低");
		tr("${sheet_3_2_1_1_read_magazine_rate_city_village_cut}",
				onef.format(100*Math.abs(readRateCity - readRateVillage)));

			
		
		
		tr("${sheet_3_2_1_1_other}", other);
		tr("${sheet_3_2_1_1_read_rate_magazine_local}",
				perf.format(tableLocal.get(1).getRate()));
		tr("${sheet_3_2_1_1_read_rate_magazine_other}",
				perf.format(tableOther.get(1).getRate()));
		double cut = tableLocal.get(1).getRate() - tableOther.get(1).getRate();
		tr("${sheet_3_2_1_1_read_rate_magazine_judge}", cut > 0 ? "多" : "少");
		tr("${sheet_3_2_1_1_read_rate_magazine_cut}",
				delLastChar(perf.format(Math.abs(cut))));
		tr("${sheet_3_2_1_1_never_read_rate_magazine}",
				perf.format(tableLocal.get(0).getRate()));

		double boyNum = tableType.get(0).getRate();
		double girlNum = tableType.get(1).getRate();
		double cityNum = tableType.get(2).getRate();
		double villageNum = tableType.get(3).getRate();
		tr("${sheet_3_2_1_1_read_magazine_num_boy}", twof.format(boyNum));
		tr("${sheet_3_2_1_1_read_magazine_num_girl}", twof.format(girlNum));
		tr("${sheet_3_2_1_1_read_magazine_num_city}", twof.format(cityNum));
		tr("${sheet_3_2_1_1_read_magazine_num_village}",
				twof.format(villageNum));

		tr("${sheet_3_2_1_1_read_magazine_num_gender_judge}",
				boyNum - girlNum > 0 ? "高" : "低");
		tr("${sheet_3_2_1_1_read_magazine_num_gender_cut}",
				twof.format(Math.abs(boyNum - girlNum)));
		tr("${sheet_3_2_1_1_read_magazine_num_city_village_judge}",
				cityNum - villageNum > 0 ? "高" : "低");
		tr("${sheet_3_2_1_1_read_magazine_num_city_village_cut}",
				twof.format(Math.abs(cityNum - villageNum)));

	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
//		Constant.getTabelNum();
		Constant.getTabelNum();
		Constant.getTabelNum();
	}
}
