package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.Constant;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;


public class _1_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "9-13岁图书阅读率（1）（当地）";
	private final static String TABLE_KEY_OTHER = "9-13岁图书阅读率（2）（全国）";
	private final static String TABLE_KEY_BOOKS_Rate = "不同人口特征9—13周岁少年儿童图书阅读率";//new add
	private final static String TABLE_KEY_BOOKS_COUNT = "9-13岁图书阅读量";
	private final static String TABLE_KEY_BOOKS_COUNT_OTHER = "1.1.1阅读率与阅读量第二段文字中的全国数据";
	//private final static String TABLE_KEY_EXTRA_BOOKS_COUNT = "9-13岁课外书阅读量";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
//	private List<DataMap> tableBooksRate;//new add
	private List<DataMap> tableBooks;
	private List<DataMap> tableBooksOther;
	private double boyRate;
	private double girlRate;
	private double cityRate;
	private double villageRate;
	
	//private List<DataMap> tableExtraBook;
	private String other;

	public _1_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
		tableOther = MinorUtil.listMapFactory();
//		tableBooksRate = MinorUtil.listMapFactory();//new add
		tableBooks = MinorUtil.listMapFactory();
		tableBooksOther = MinorUtil.listMapFactory();
		//tableExtraBook = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL);
		System.out.println(tableLocal);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther, BEGIN_COL, " ");
//		MinorUtil.readData(xlsx, xlsx.getRowByKey(TABLE_KEY_BOOKS_Rate) + 2, tableBooksRate, BEGIN_COL + 1);//new add
		MinorUtil.readData(xlsx, xlsx.getRowByKey(TABLE_KEY_BOOKS_COUNT) + 2, tableBooks, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, xlsx.getRowByKey(TABLE_KEY_BOOKS_COUNT_OTHER) + 2, tableBooksOther, BEGIN_COL + 1);
		boyRate = SingleValue.read(xlsx, TABLE_KEY_BOOKS_Rate, 3, 1);
		girlRate = SingleValue.read(xlsx, TABLE_KEY_BOOKS_Rate, 3, 2);
		cityRate = SingleValue.read(xlsx, TABLE_KEY_BOOKS_Rate, 3, 3);
		villageRate = SingleValue.read(xlsx, TABLE_KEY_BOOKS_Rate, 3, 4);
		
		//MinorUtil.readData(xlsx, TABLE_KEY_EXTRA_BOOKS_COUNT, tableExtraBook, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		
		tr("${sheet3_1_1_1_read_rate_local}", perf.format(tableLocal.get(0).getRate()));
		tr("${sheet3_1_1_1_read_rate_other}", perf.format(tableOther.get(0).getRate()));
		tr("${sheet3_1_1_1_read_rate_judge}", 
				(tableLocal.get(0).getRate() - tableOther.get(0).getRate() > 0 ? "高"
				: "低"));
		tr("${sheet3_1_1_1_read_rate_judge_cut}", 
				onef.format(100*Math.abs((tableLocal.get(0).getRate() - tableOther.get(0).getRate()))));
		tr("${sheet3_1_1_1_other}", other);

		//new add
		//double avgRate = MinorUtil.getByKey("总计", tableBooksRate).getRate();
//		double boyRate = MinorUtil.getByKey("男", tableBooksRate).getRate();
//		double girlRate = MinorUtil.getByKey("女", tableBooksRate).getRate();
//		double cityRate = MinorUtil.getByKey("城市", tableBooksRate).getRate();
//		double villageRate = MinorUtil.getByKey("农村", tableBooksRate).getRate();
		
		//tr("${sheet3_1_1_1_read_rate_avg}", twof.format(avgCount));
		tr("${sheet3_1_1_1_read_rate_city}", perf.format(cityRate));
		tr("${sheet3_1_1_1_read_rate_village}", perf.format(villageRate));
		tr("${sheet3_1_1_1_read_rate_boy_avg}", perf.format(boyRate));
		tr("${sheet3_1_1_1_read_rate_girl_avg}", perf.format(girlRate));
		
		tr("${sheet3_1_1_1_read_rate_city_village_judge}", cityRate - villageRate > 0 ? "多" : "少");
		tr("${sheet3_1_1_1_read_rate_city_village_cut}", onef.format(100*Math.abs( cityRate - villageRate )));
		tr("${sheet3_1_1_1_read_rate_boy_girl_judge}", boyRate - girlRate > 0 ? "多" : "少");
		tr("${sheet3_1_1_1_read_rate_boy_girl_cut}", onef.format(100*Math.abs( boyRate - girlRate )));
		
		double avgCount = tableBooks.get(4).getRate();
		double boyCount = MinorUtil.getByKey("男", tableBooks).getRate();
		double girlCount = MinorUtil.getByKey("女", tableBooks).getRate();
		double cityCount = MinorUtil.getByKey("城市", tableBooks).getRate();
		double villageCount = MinorUtil.getByKey("农村", tableBooks).getRate();

		tr("${sheet3_1_1_1_read_count_avg}", twof.format(avgCount));
		tr("${sheet3_1_1_1_read_count_city}", twof.format(cityCount));
		tr("${sheet3_1_1_1_read_count_village}", twof.format(villageCount));
		tr("${sheet3_1_1_1_read_count_boy_avg}", twof.format(boyCount));
		tr("${sheet3_1_1_1_read_count_girl_avg}", twof.format(girlCount));

		double cityCutVillage = cityCount - villageCount;
		tr("${sheet3_1_1_1_read_count_city_village_judge}", cityCutVillage > 0 ? "多" : "少");
		tr("${sheet3_1_1_1_read_count_city_village_cut}", twof.format(Math.abs(cityCutVillage)));
		tr("${sheet3_1_1_1_read_count_boy_girl_judge}", boyCount - girlCount > 0 ? "多" : "少");
		tr("${sheet3_1_1_1_read_count_boy_girl_cut}", twof.format(Math.abs( boyCount - girlCount )));
		
		double avgCountOther = tableBooksOther.get(4).getRate();
		
		
//		double cityCountOther = MinorUtil.getByKey("城市", tableBooksOther).getRate();
//		double villageCountOther = MinorUtil.getByKey("农村", tableBooksOther).getRate();

		tr("${sheet3_1_1_1_read_count_avg_other}", twof.format(avgCountOther));
		
		tr("${sheet3_1_1_1_read_count_local_other_judge}", avgCount - avgCountOther > 0 ? "多" : "少");
		tr("${sheet3_1_1_1_read_count_local_other_cut}", twof.format(Math.abs( avgCount - avgCountOther )));
//		tr("${sheet3_1_1_1_read_count_city_other}", twof.format(cityCountOther));
//		tr("${sheet3_1_1_1_read_count_village_other}", twof.format(villageCountOther));
//		tr("${sheet3_1_1_1_read_count_city_other_cut}", twof.format(Math.abs(cityCount - cityCountOther)));
//		tr("${sheet3_1_1_1_read_count_village_other_cut}", twof.format(Math.abs(villageCount) - villageCountOther));
//		tr("${sheet3_1_1_1_read_count_city_other_judge}", (cityCount > cityCountOther) ? "多" : "少");
//		tr("${sheet3_1_1_1_read_count_village_other_judge}", (villageCount > villageCountOther) ? "多" : "少");

//		DataMap neverRead = tableExtraBook.get(tableExtraBook.size() - 1);
//		tableExtraBook.remove(neverRead);
//		DataMap notClear = tableExtraBook.get(tableExtraBook.size() - 1);
//		tableExtraBook.remove(notClear);
//		DataMap moreTen = tableExtraBook.get(tableExtraBook.size() - 1);
//		tableExtraBook.remove(moreTen);
//
//		MinorUtil.listSort(tableExtraBook);
//		double before3 = 0;
//		String before3String = "";
//		for (int i = 0; i < 3; i++)
//		{
//			before3 += tableExtraBook.get(i).getRate();
//			before3String += tableExtraBook.get(i).getKey() + "、";
//		}
//		before3String.substring(1);
//		tr("${sheet3_1_1_1_read_count_before3}", before3String);
//		tr("${sheet3_1_1_1_read_count_before3_rate}", perf.format(before3));
//		tr("${sheet3_1_1_1_read_count_fourth_rate}", perf.format(tableExtraBook.get(3).getRate()));
//		tr("${sheet3_1_1_1_read_count_fourth}", tableExtraBook.get(3).getKey());
//		tr("${sheet3_1_1_1_read_count_more_ten_rate}", perf.format(moreTen.getRate()));
//		tr("${sheet3_1_1_1_read_count_not_clear}", perf.format(notClear.getRate()));
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

//		List<DataMap> tmp = MinorUtil.listMapFactory();
//		tmp.add(new DataMap(MinorUtil.local, tableLocal.get(0).getRate()));
//		tmp.add(new DataMap(other, tableOther.get(0).getRate()));

//		MinorUtil.changeChart(tmp);
//
//		MinorUtil.changeChart(tableExtraBook);
	}

}
