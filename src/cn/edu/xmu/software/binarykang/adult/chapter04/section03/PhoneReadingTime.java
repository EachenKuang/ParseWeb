package cn.edu.xmu.software.binarykang.adult.chapter04.section03;

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
public final class PhoneReadingTime extends AdultBaseAction
{
//	private double averagePhoneReadingTime;
	private List<BaseRow> data;
	private double urbanPhoneReadingTime;
	private double villagePhoneReadingTime;
	private double localPhoneReadingTime;
	private double countryPhoneReadingTime;
	
	public PhoneReadingTime(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
//		averagePhoneReadingTime = SingleValue.read(xlsx,
////				"4.3.2阅读时长 第一段文字中的每天手机阅读时长");
//		XSSFRow urbanVillageRow = xlsx.getRow(xlsx
//				.getRowByKey("4.3.2阅读时长 第一段文字中的每天手机阅读时长") + 3);
//		localPhoneReadingTime = new Double(xlsx.getContent(
//				urbanVillageRow, BEGIN_COL + 2));
//		urbanPhoneReadingTime = new Double(xlsx.getContent(
//				urbanVillageRow, BEGIN_COL + 3));
//		villagePhoneReadingTime = new Double(xlsx.getContent(
//				urbanVillageRow, BEGIN_COL + 4));
//		countryPhoneReadingTime = SingleValue.read(xlsx,
//				"4.3.2阅读时长 第一段文字中的每天手机阅读时长——全国",1,2);
	
		localPhoneReadingTime =  SingleValue.read(xlsx,"4.3.2阅读时长 第一段文字中的每天手机阅读时长",3,1 );
		urbanPhoneReadingTime =  SingleValue.read(xlsx,"4.3.2阅读时长 第一段文字中的每天手机阅读时长",3,2 );
		villagePhoneReadingTime =  SingleValue.read(xlsx,"4.3.2阅读时长 第一段文字中的每天手机阅读时长",3,3 );
		countryPhoneReadingTime =  SingleValue.read(xlsx,"4.3.2阅读时长 第一段文字中的每天手机阅读时长——全国",1,2 );
		
		
		BaseRow.read(xlsx, data, "手机阅读接触者手机阅读时长");
	}

	@Override
	protected void replace()
	{
		tr("${average_phone_reading_time}", tf(localPhoneReadingTime));
		tr("${country_average_phone_reading_time}",tf(countryPhoneReadingTime));
		tr("${local_minus_country_average_phone_reading_key}",
				localPhoneReadingTime > countryPhoneReadingTime ? "高":"低");
		tr("${local_minus_country_average_phone_reading_time}",
				tf(Math.abs(localPhoneReadingTime - countryPhoneReadingTime)));
		
		tr("${urban_average_phone_reading_time}", tf(urbanPhoneReadingTime));
		tr("${village_average_phone_reading_time}",tf(villagePhoneReadingTime));
		tr("${urban_minus_village_average_phone_reading_key}",
				urbanPhoneReadingTime > villagePhoneReadingTime ? "高":"低");
		tr("${urban_minus_village_average_phone_reading_time}",
				tf(Math.abs(urbanPhoneReadingTime - villagePhoneReadingTime)));
		
		tr("${over_half_hour_phone_reading_time_value}", pf(data.get(1).value
				+ data.get(2).value + data.get(3).value + data.get(4).value));
		
		//BaseRow.sort(data);
		tr("${best_phone_reading_time_key}", data.get(0).key);
		tr("${best_phone_reading_time_value}", pf(data.get(0).value));
		tr("${second_phone_reading_time_key}", data.get(1).key);
		tr("${second_phone_reading_time_value}", pf(data.get(1).value));
		tr("${third_phone_reading_time_key}", data.get(2).key);
		tr("${third_phone_reading_time_value}", pf(data.get(2).value));
		tr("${fourth_phone_reading_time_key}", data.get(3).key);
		tr("${fourth_phone_reading_time_value}", pf(data.get(3).value));
		tr("${fiveth_phone_reading_time_key}", data.get(4).key);
		tr("${fiveth_phone_reading_time_value}", pf(data.get(4).value));
		tr("${sixth_phone_reading_time_key}", data.get(5).key);
		tr("${sixth_phone_reading_time_value}", pf(data.get(5).value + data.get(6).value));
		
	}

	@Override
	protected void chart()
	{
		Collections.reverse(data);
		BaseRow.chart(data);// chart24
		Collections.reverse(data);
	}

}
