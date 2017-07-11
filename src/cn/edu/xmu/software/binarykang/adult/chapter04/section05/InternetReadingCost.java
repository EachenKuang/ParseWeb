package cn.edu.xmu.software.binarykang.adult.chapter04.section05;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.5.5网络在线阅读花费
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class InternetReadingCost extends AdultBaseAction
{
	private double localInternetReadingCost;
	private double countryInternetReadingCost;
	private double urbanInternetReadingCost;
	private double villageInternetReadingCost;
	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	public InternetReadingCost(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "网络在线阅读花费");
		DoubleValueRow.read(xlsx, uvData, "网络在线阅读花费城乡对比（1）");
		localInternetReadingCost = SingleValue.read(xlsx,
				"4.5.5网络在线阅读花费 第一段文字中的网络在线阅读总花费（当地）");
		countryInternetReadingCost = SingleValue.read(xlsx,
				"4.5.5网络在线阅读花费 第一段文字中的网络在线阅读总花费（全国）");
		urbanInternetReadingCost = SingleValue.read(xlsx, "网络在线阅读花费城乡对比（2）", 3,
				1);
		villageInternetReadingCost = SingleValue.read(xlsx, "网络在线阅读花费城乡对比（2）",
				3, 2);
	}

	@Override
	protected void replace()
	{
		tr("${local_average_internet_reading_cost_value}",
				tf(localInternetReadingCost));
		tr("${country_average_internet_reading_cost_value}",
				tf(countryInternetReadingCost));
		tr("${local_minus_country_average_internet_reading_cost_key}",
				localInternetReadingCost > countryInternetReadingCost ? "高"
						: "低");
		tr("${local_minus_country_average_internet_reading_cost_value}",
				tf(Math.abs(localInternetReadingCost - countryInternetReadingCost)) );
		
		tr("${has_paid_internet_reading_cost_key}",
				pf(1 - data.get(data.size() - 1).value));
		
		//BaseRow.sortExceptLast(data);
//		tr("${best_internet_reading_cost_key}", data.get(0).key);
		tr("${best_internet_reading_cost_value}", pf(data.get(0).value));
//		tr("${second_internet_reading_cost_key}", data.get(1).key);
		tr("${second_internet_reading_cost_value}", pf(data.get(1).value));
//		tr("${third_internet_reading_cost_key}", data.get(2).key);
		tr("${third_internet_reading_cost_value}", pf(data.get(2).value+data.get(3).value));
//		tr("${fourth_internet_reading_cost_key}", data.get(3).key);
		tr("${fourth_internet_reading_cost_value}",
				pf( data.get(4).value + data.get(5).value + data.get(6).value));
		
		// 替换城乡对比相关的数据
		tr("${urban_never_cost_money_on_internet_reading_num_value}",
				tf(urbanInternetReadingCost));
		tr("${village_never_cost_money_on_internet_reading_num_value}",
				tf(villageInternetReadingCost));
		tr("${village_minus_urban_never_cost_money_on_internet_reading_num_key}",
				villageInternetReadingCost > urbanInternetReadingCost ? "高"
						: "低");
		tr("${village_minus_urban_never_cost_money_on_internet_reading_num_value}",
				pf(Math.abs(villageInternetReadingCost - urbanInternetReadingCost  )));
		
		tr("${urban_never_cost_money_on_internet_reading_rate_value}",
				pf(uvData.get(uvData.size() - 1).v1));
		tr("${village_never_cost_money_on_internet_reading_rate_value}",
				pf(uvData.get(uvData.size() - 1).v2));
		//tr("${urban_minus_village_never_cost_money_on_internet_reading_rate_key}",
		//		uvData.get(uvData.size() - 1).v1 > uvData.get(uvData.size() - 1).v2 ? "高"
		//				: "低");
		//tr("${urban_minus_village_never_cost_money_on_internet_reading_rate_value}",
		//		of(Math.abs(uvData.get(uvData.size() - 1).v1 - uvData.get(uvData.size() - 1).v2 )));
		//
		tr("${village_minus_urban_never_cost_money_on_internet_reading_rate_key}",
				uvData.get(uvData.size() - 1).v2 > uvData.get(uvData.size() - 1).v1 ? "高"
						: "低");
		tr("${village_minus_urban_never_cost_money_on_internet_reading_rate_value}",
				of(Math.abs(uvData.get(uvData.size() - 1).v2 - uvData.get(uvData.size() - 1).v1)));
		
		tr("${urban_cost_money_on_internet_reading_num_value}", tf(urbanInternetReadingCost));
		tr("${village_cost_money_on_internet_reading_num_value}", tf(villageInternetReadingCost));
		tr("${urban_minus_village_cost_money_on_internet_reading_num_key}",
				villageInternetReadingCost < urbanInternetReadingCost ? "高" : "低");
		tr("${urban_minus_village_cost_money_on_internet_reading_num_value}",
				tf(Math.abs(urbanInternetReadingCost - villageInternetReadingCost )));
	}

	@Override
	protected void chart()
	{
		BaseRow.table(docx, data,
//				"Resource/adult/chapter04/internet_reading_cost_odd_row.xml",
//				"Resource/adult/chapter04/internet_reading_cost_even_row.xml",
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml",
				GF.p, false);

		DoubleValueRow
				.table(docx,
						uvData,
//						"Resource/adult/chapter04/uv_internet_reading_cost_odd_row.xml",
//						"Resource/adult/chapter04/uv_internet_reading_cost_even_row.xml",
						"Resource/doubleValueStandard.xml",
						"Resource/doubleValueStandard.xml",
						GF.p, false);
	}

}
