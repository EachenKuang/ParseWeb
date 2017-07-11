package cn.edu.xmu.software.binarykang.minor.sheet2._2_1;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.Constant;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_1_2 extends MinorBaseAction
{
	private final static String[] TABLE_KEY_RATE =
	{ "2.1.2图书阅读率和阅读量 第一段文字中的图书阅读率（当地）", "2.1.2图书阅读率和阅读量 第一段文字中的图书阅读率（全国）" };
	private final static String TABLE_KEY_RATE_TYPE = "不同群体的图书阅读率";	//new
	private final static String[] TABLE_KEY_COUNT =
	{ "2.1.2图书阅读率和阅读量 第一段文字中的人均阅读量（当地）", "2.1.2图书阅读率和阅读量 第一段文字中的人均阅读量（全国）" };
	private final static String TABLE_KEY_TYPE = "14-17岁不同群体的人均图书阅读量";
	private final int tableRateNum = TABLE_KEY_RATE.length;

	// private List<Map<String, Double>> tableList;
	private TableInfo bookRateInfo[] = new TableInfo[2];
	private TableInfo typeInfo[] = new TableInfo[4];
	private double readRateTypeInfo[] = new double[4];

	public _2_1_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
	}

	@Override
	protected void readData()
	{
		int beginRowRate;
		int beginRowCount;
		XSSFRow rowRate, row;
		for (int i = 0; i < tableRateNum; ++i)
		{
			beginRowRate = xlsx.getRowByKey(TABLE_KEY_RATE[i]);
			beginRowCount = xlsx.getRowByKey(TABLE_KEY_COUNT[i]);
			for (int j = beginRowRate + 1; (rowRate = xlsx.getRow(j))
					.getLastCellNum() != -1; j += 2)
			{
				int beginCol = BEGIN_COL + 1;
				String key = xlsx.getContent(rowRate, beginCol);
				if (key != "")
				{
					Double avgRate = new Double(xlsx.getContent(xlsx.getRow(j),
							beginCol + 1));
					Double avgCount = new Double(xlsx.getContent(
							xlsx.getRow(j - beginRowRate + beginRowCount),
							beginCol));
					// System.out.println(key + ":" + avgRate + ":" + avgCount);
					bookRateInfo[i] = new TableInfo(key, avgRate, avgCount);
				}
			}
		}
		int beginRow = xlsx.getRowByKey(TABLE_KEY_TYPE) + 2;
		for (int j = 0; (row = xlsx.getRow(beginRow + j)).getLastCellNum() != -1; j += 1)
		{
			int beginCol = BEGIN_COL + 1;
			String key = xlsx.getContent(row, beginCol);
			if (key != "")
			{
				Double avgCount = new Double(xlsx.getContent(
						xlsx.getRow(beginRow + j), beginCol + 1));
				// System.out.println(key + ":" + avgCount);
				typeInfo[j] = new TableInfo(key, avgCount);
			}
		}
		for(int j = 0; j < 4; j++)
		{
			readRateTypeInfo[j] = SingleValue.read(xlsx, TABLE_KEY_RATE_TYPE, 3, 2 + j);
		}
	}

	@Override
	protected void replace()
	{
		// read rate
		docx.replace("${read_rate_local}", perf.format(bookRateInfo[0].rate));
		docx.replace("${read_rate_other}", perf.format(bookRateInfo[1].rate));
		double localCutOther = bookRateInfo[0].rate - bookRateInfo[1].rate;
		String judge = localCutOther > 0 ? "高" : "低";
		docx.replace("${read_rate_judge}", judge);
		docx.replace("${read_rate_local_other}",
				delLastChar(perf.format(Math.abs(localCutOther))));
		
		docx.replace("${read_rate_male_local}", perf.format(readRateTypeInfo[0]));
		docx.replace("${read_rate_female_local}", perf.format(readRateTypeInfo[1]));
		docx.replace("${read_rate_male_female_diff}", 
				readRateTypeInfo[0]>readRateTypeInfo[1] ? "高" : "低");
		docx.replace("${read_rate_male_female_diff_rate}", 
				delLastChar(perf.format(Math.abs(readRateTypeInfo[0]-readRateTypeInfo[1]))));
		docx.replace("${read_rate_city_local}", perf.format(readRateTypeInfo[2]));
		docx.replace("${read_rate_village_local}", perf.format(readRateTypeInfo[3]));
		docx.replace("${read_rate_city_village_diff}", readRateTypeInfo[2]>readRateTypeInfo[3] ? "高" : "低");
		docx.replace("${read_rate_city_village_diff_rate}", 
				delLastChar(perf.format(Math.abs(readRateTypeInfo[2]-readRateTypeInfo[3]))));
		// read count
		docx.replace("${read_count_local}", twof.format(bookRateInfo[0].count));
		docx.replace("${read_count_other}", twof.format(bookRateInfo[1].count));
		docx.replace("${read_count_city_other_judge}", 
				bookRateInfo[0].count>bookRateInfo[1].count ? "高" : "低");
		docx.replace("${read_count_city_other}", 
				twof.format(Math.abs(bookRateInfo[0].count-bookRateInfo[1].count)));

		double femaleCutMale = typeInfo[1].count - typeInfo[0].count;
		if (femaleCutMale < 0)
			judge = "多";
		else
			judge = "少";
		docx.replace("${read_count_gender_judge}", judge);
		docx.replace("${read_count_female_male}",
				twof.format(Math.abs(femaleCutMale)));

		double villageCutCity = typeInfo[3].count - typeInfo[2].count;
		if (villageCutCity < 0)
			judge = "多";
		else
			judge = "少";
		docx.replace("${read_count_type_judge}", judge);
		docx.replace("${read_count_village_city}",
				twof.format(Math.abs(villageCutCity)));

		String other = MinorUtil.other;
		docx.replace("${read_other_city}", other);
	}

	@Override
	protected void chart()
	{
		Constant.getTabelNum();
		Constant.getTabelNum();
		docx.replace("${read_count_male}", twof.format(typeInfo[0].count));
		docx.replace("${read_count_female}", twof.format(typeInfo[1].count));
		docx.replace("${read_count_city}", twof.format(typeInfo[2].count));
		docx.replace("${read_count_village}", twof.format(typeInfo[3].count));
	}

	private class TableInfo
	{
		@SuppressWarnings("unused")
		public String key;
		public Double rate;
		public Double count;

		public TableInfo(String key, Double count)
		{
			this(key, null, count);
		}

		public TableInfo(String key, Double rate, Double count)
		{
			this.key = key;
			this.rate = rate;
			this.count = count;
		}
	}

}
