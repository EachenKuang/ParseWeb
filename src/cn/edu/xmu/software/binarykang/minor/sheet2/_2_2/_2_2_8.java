package cn.edu.xmu.software.binarykang.minor.sheet2._2_2;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Single;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_2_8 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁图书价格承受力";
	private final static String TABLE_KEY_AVG_PRICE = "2.2.8图书价格承受力 第二段文字中的图书平均定价";
	private final static String TABLE_KEY_COUNTRY = "2.2.8图书价格承受力 第二段文字中的图书平均定价——全国";
	private List<DataMap> tableInfo;
	private List<DataMap> tableAvgPrice;
	private double tableCountry;

	public _2_2_8(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = new ArrayList<DataMap>();
		tableAvgPrice = new ArrayList<DataMap>();
	}

	@Override
	protected void readData()
	{
		tableCountry = SingleValue.read(xlsx, TABLE_KEY_COUNTRY, 1, 1);
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		int beginRow = xlsx.getRowByKey(TABLE_KEY_AVG_PRICE) + 1;
		XSSFRow rowKey, rowValue;
		rowKey = xlsx.getRow(beginRow);
		rowValue = xlsx.getRow(beginRow + 2);
		for (int j = BEGIN_COL + 1; j < BEGIN_COL + 4; ++j)
		{
			String key = xlsx.getContent(rowKey, j);
			if (key != "")
			{
				Double rate = new Double(xlsx.getContent(rowValue, j));
				tableAvgPrice.add(new DataMap(key, rate));
			}
		}
	}

	@Override
	protected void replace()
	{
		docx.replace("${_2_2_8_country}", twof.format(tableCountry));
		docx.replace("${_2_2_8_avg_price_total}", twof.format(tableAvgPrice.get(0).getRate()));
		docx.replace("${_2_2_8_country_local_key}", tableAvgPrice.get(0).getRate() > tableCountry?
				"高":"低");
		docx.replace("${_2_2_8_country_local_value}", 
				twof.format(Math.abs(tableAvgPrice.get(0).getRate()-tableCountry)));
		
		docx.replace("${_2_2_8_avg_price_4_30}", 
				perf.format(tableInfo.get(1).getRate()+tableInfo.get(2).getRate()
						+tableInfo.get(3).getRate()+tableInfo.get(4).getRate()));
		docx.replace("${_2_2_8_avg_price_4_8}", perf.format(tableInfo.get(1).getRate()));
		docx.replace("${_2_2_8_avg_price_8_12}", perf.format(tableInfo.get(2).getRate()));
		docx.replace("${_2_2_8_avg_price_12_20}", perf.format(tableInfo.get(3).getRate()));
		docx.replace("${_2_2_8_avg_price_20_30}", perf.format(tableInfo.get(4).getRate()));
		docx.replace("${_2_2_8_avg_price_less_4}", perf.format(tableInfo.get(0).getRate()));
		docx.replace("${_2_2_8_avg_price_higher_30}", perf.format(tableInfo.get(5).getRate()));
		docx.replace("${_2_2_8_avg_price_whatever}", 
				perf.format(tableInfo.get(tableInfo.size()-1).getRate()));
		//MinorUtil.listSort(tableInfo);
		//double total = 0;
		//for (int i = 0; i < 2; i++)
		//
		//	docx.replace("${_2_2_8_price_" + i + "}", tableInfo.get(i).getKey());
		//	docx.replace("${_2_2_8_price_" + i + "_rate}",
		//			perf.format(tableInfo.get(i).getRate()));
		//	total += tableInfo.get(i).getRate();
		//}
		//docx.replace("${_2_2_8_price_0_1_rate}", perf.format(total));
		docx.replace("${_2_2_8_avg_price_total}",
				twof.format(tableAvgPrice.get(0).getRate()));
		docx.replace("${_2_2_8_avg_price_city}",
				twof.format(tableAvgPrice.get(1).getRate()));
		docx.replace("${_2_2_8_avg_price_village}",
				twof.format(tableAvgPrice.get(2).getRate()));
		docx.replace("${_2_2_8_avg_price_city_village}", 
				tableAvgPrice.get(1).getRate()>tableAvgPrice.get(2).getRate()?"高":"低");
		docx.replace("${_2_2_8_avg_price_city_village_rate}", 
				twof.format(Math.abs(tableAvgPrice.get(1).getRate()-tableAvgPrice.get(2).getRate())));
	}

	@Override
	public void process()
	{
		readData();
		chart();
		replace();
	};

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}

}
