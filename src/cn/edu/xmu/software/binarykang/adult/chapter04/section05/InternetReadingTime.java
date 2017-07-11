package cn.edu.xmu.software.binarykang.adult.chapter04.section05;


import java.util.Collections;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.3.2阅读时长
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class InternetReadingTime extends AdultBaseAction
{
//	private double averagePhoneReadingTime;
	private List<BaseRow> data;
	private double urbanInternetReadingTime;
	private double villageInternetReadingTime;
	private double localInternetReadingTime;
	private double countryInternetReadingTime;
	
	public InternetReadingTime(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
//		averagePhoneReadingTime = SingleValue.read(xlsx,
//				"4.3.2阅读时长 第一段文字中的每天手机阅读时长");
//		XSSFRow urbanVillageRow = xlsx.getRow(xlsx
//				.getRowByKey("4.5.5  网络在线阅读时长 第一段文字中的平均阅读时长") + 3);
//		localInternetReadingTime = new Double(xlsx.getContent(
//				urbanVillageRow, BEGIN_COL + 2));
//		urbanInternetReadingTime = new Double(xlsx.getContent(
//				urbanVillageRow, BEGIN_COL + 3));
//		villageInternetReadingTime = new Double(xlsx.getContent(
//				urbanVillageRow, BEGIN_COL + 4));
//		countryInternetReadingTime = SingleValue.read(xlsx,
//				"4.5.5  网络在线阅读时长 第一段文字中的平均阅读时长——全国",1,1);
		localInternetReadingTime = SingleValue.read(xlsx, "4.5.5  网络在线阅读时长 第一段文字中的平均阅读时长",3,1);
		urbanInternetReadingTime = SingleValue.read(xlsx, "4.5.5  网络在线阅读时长 第一段文字中的平均阅读时长",3,2);
		villageInternetReadingTime = SingleValue.read(xlsx, "4.5.5  网络在线阅读时长 第一段文字中的平均阅读时长",3,3);
		countryInternetReadingTime = SingleValue.read(xlsx, "4.5.5  网络在线阅读时长 第一段文字中的平均阅读时长——全国",1,1);
		
		BaseRow.read(xlsx, data, "网络在线阅读时长");
	}

	@Override
	protected void replace()
	{
		tr("${local_internet_reading_time}", tf(localInternetReadingTime));
		tr("${country_internet_reading_time}",tf(countryInternetReadingTime));
		tr("${local_minus_country_internet_reading_time_key}",
				localInternetReadingTime > countryInternetReadingTime ? "多":"少");
		tr("${local_minus_country_internet_reading_time_value}",
				tf(Math.abs(localInternetReadingTime - countryInternetReadingTime)));
		
		tr("${urban_internet_reading_time}", tf(urbanInternetReadingTime));
		tr("${village_internet_reading_time}",tf(villageInternetReadingTime));
		tr("${urban_minus_village_internet_reading_time_key}",
				urbanInternetReadingTime > villageInternetReadingTime ? "高":"低");
		tr("${urban_minus_village_internet_reading_time_value}",
				tf(Math.abs(urbanInternetReadingTime - villageInternetReadingTime)));
		
		
		//BaseRow.sort(data);
//		tr("${best_internet_reading_time_key}", data.get(0).key);
		tr("${best_internet_reading_time_value}", pf(data.get(0).value));
//		tr("${second_internet_reading_time_key}", data.get(1).key);
		tr("${second_internet_reading_time_value}", pf(data.get(1).value));
//		tr("${third_internet_reading_time_key}", data.get(2).key);
		tr("${third_internet_reading_time_value}", pf(data.get(2).value));
//		tr("${fourth_internet_reading_time_key}", data.get(3).key);
		tr("${fourth_internet_reading_time_value}", pf(data.get(3).value));
//		tr("${fiveth_internet_reading_time_key}", data.get(4).key);
		tr("${fiveth_internet_reading_time_value}", pf(data.get(4).value));
//		tr("${sixth_internet_reading_time_key}", data.get(5).key);
		tr("${sixth_internet_reading_time_value}", pf(data.get(5).value + data.get(6).value));
		
		tr("${ten_minutes_two_hours_internet_reading_time_value}"
				,pf(data.get(1).value + data.get(2).value + data.get(3).value + data.get(4).value ));
	}

	@Override
	protected void chart()
	{
		Collections.reverse(data);
		BaseRow.chart(data);
		Collections.reverse(data);
	}

}
