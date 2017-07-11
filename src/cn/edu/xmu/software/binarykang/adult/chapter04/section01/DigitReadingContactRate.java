package cn.edu.xmu.software.binarykang.adult.chapter04.section01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>4.1.1���ֻ��Ķ��ĽӴ���
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class DigitReadingContactRate extends AdultBaseAction
{
	private final String AREA_DIGIT_READING_CONTACT_RATE_TABLE_KEY = "���������ֻ��Ķ��Ӵ�������";
	private final String LOCAL_DIGIT_READING_CONTACT_RATE_TABLE_KEY = "���ֻ��Ķ���ʽ�Ӵ��ʣ����أ�";
	private final String COUNTRY_DIGIT_READING_CONTACT_RATE_TABLE_KEY = "���ֻ��Ķ���ʽ�Ӵ��ʣ�ȫ����";

	private double localDigitReadingContactRate;
	private double countryDigitReadingContactRate;
	private double localWechatReadingRate;
	private double countryWechatReadingRate;

	private List<BaseRow> areaData;
	private List<DoubleValueRow> lcDataForChart;
	private List<DigitReadingContactRate.TableRow> lcData;

	public DigitReadingContactRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		areaData = ListFactory.getBaseRows();
		lcDataForChart = ListFactory.getDoubleValueRows();
		lcData = new ArrayList<DigitReadingContactRate.TableRow>();
	}

	@Override
	protected void readData()
	{
		// ��ȡ�����滻������
		localDigitReadingContactRate = SingleValue.read(xlsx,
				"4.1.1���ֻ��Ķ��ĽӴ��� ��һ�������е����ֻ��Ķ���ʽ�ĽӴ��ʣ����أ�");
		countryDigitReadingContactRate = SingleValue.read(xlsx,
				"4.1.1���ֻ��Ķ��ĽӴ��� ��һ�������е����ֻ��Ķ���ʽ�ĽӴ��ʣ�ȫ����");
        
		
		//2016-11-9
		localWechatReadingRate = SingleValue.read(xlsx, "΢���Ķ���",8,2);
		countryWechatReadingRate =  SingleValue.read(xlsx, "΢���Ķ���-ȫ��",8,2);
		
		// ��ȡ���������Ķ�����
		int areaDigitReadingContactRateRowIndex = xlsx
				.getRowByKey(AREA_DIGIT_READING_CONTACT_RATE_TABLE_KEY);
		XSSFRow areaRow = xlsx.getRow(areaDigitReadingContactRateRowIndex + 1);
		XSSFRow rateRow = xlsx.getRow(areaDigitReadingContactRateRowIndex + 3);
		for (int i = BEGIN_COL + 2; i < areaRow.getLastCellNum(); ++i)
		{
			BaseRow baseRow = new BaseRow();
			baseRow.key = xlsx.getContent(areaRow, i);
			baseRow.value = new Double(xlsx.getContent(rateRow, i));
			areaData.add(baseRow);
		}

		// ��ȡ���غ�ȫ���Աȵ�����
		XSSFRow localRow = null;
		XSSFRow countryRow = null;
		for (int i = xlsx
				.getRowByKey(LOCAL_DIGIT_READING_CONTACT_RATE_TABLE_KEY) + 1, j = xlsx
				.getRowByKey(COUNTRY_DIGIT_READING_CONTACT_RATE_TABLE_KEY) + 1; (localRow = xlsx
				.getRow(i)).getLastCellNum() != -1; i += 2, j += 2)
		{
			countryRow = xlsx.getRow(j);
			TableRow tableRow = new TableRow();
			tableRow.local = new Double(
					xlsx.getContent(localRow, BEGIN_COL + 2));
			tableRow.country = new Double(xlsx.getContent(countryRow,
					BEGIN_COL + 2));
			tableRow.localMinusCountry = Math.abs(tableRow.local
					- tableRow.country);
			tableRow.localMinusCountryKey = tableRow.local > tableRow.country ? "��"
					: "��";

			lcData.add(tableRow);

			DoubleValueRow row = new DoubleValueRow();
			row.key = xlsx.getContent(localRow, BEGIN_COL);
			row.v1 = tableRow.local;
			row.v2 = tableRow.country;
			lcDataForChart.add(row);
		}

	}

	@Override
	protected void replace()
	{
		// �����滻
		tr("${local_digit_reading_contact_rate_value}",
				pf(localDigitReadingContactRate));
		tr("${country_digit_reading_contact_rate_value}",
				pf(countryDigitReadingContactRate));
		tr("${local_minus_country_digit_reading_contact_rate_value}",
				of(Math.abs(localDigitReadingContactRate
						- countryDigitReadingContactRate)));
		tr("${local_minus_country_digit_reading_contact_rate_key}",
				localDigitReadingContactRate > countryDigitReadingContactRate ? "��"
						: "��");
		// �滻���������ֻ��Ķ��Ӵ���������صı���
//		tr("${best_area_digit_reading_contact_rate_key}", areaData.get(0).key);
//		tr("${best_area_digit_reading_contact_rate_value}",
//				pf(areaData.get(0).value));
//		tr("${second_area_digit_reading_contact_rate_key}", areaData.get(1).key);
//		tr("${second_area_digit_reading_contact_rate_value}",
//				pf(areaData.get(1).value));
//		tr("${third_area_digit_reading_contact_rate_key}", areaData.get(2).key);
//		tr("${third_area_digit_reading_contact_rate_value}",
//				pf(areaData.get(2).value));
		// �滻���غ�ȫ�����ֻ��Ķ��Ӵ��ʵ���ر���
		tr("${local_network_digit_reading_contact_rate}",
				pf(lcData.get(0).local));
		tr("${country_network_digit_reading_contact_rate}",
				pf(lcData.get(0).country));
		tr("${local_minus_country_network_digit_reading_contact_rate_key}",
				lcData.get(0).localMinusCountryKey);
		tr("${local_minus_country_network_digit_reading_contact_rate_value}",
				of(lcData.get(0).localMinusCountry));
		tr("${local_telephone_digit_reading_contact_rate}",
				pf(lcData.get(1).local));
		tr("${country_telephone_digit_reading_contact_rate}",
				pf(lcData.get(1).country));
		tr("${local_minus_country_telephone_digit_reading_contact_rate_key}",
				lcData.get(1).localMinusCountryKey);
		tr("${local_minus_country_telephone_digit_reading_contact_rate_value}",
				of(lcData.get(1).localMinusCountry));
		tr("${local_ebook_digit_reading_contact_rate}", pf(lcData.get(2).local));
		tr("${country_ebook_digit_reading_contact_rate}",
				pf(lcData.get(2).country));
		tr("${local_minus_country_ebook_digit_reading_contact_rate_key}",
				lcData.get(2).localMinusCountryKey);
		tr("${local_minus_country_ebook_digit_reading_contact_rate_value}",
				of(lcData.get(2).localMinusCountry));
		tr("${local_cd_digit_reading_contact_rate}", pf(lcData.get(3).local));
		tr("${country_cd_digit_reading_contact_rate}",
				pf(lcData.get(3).country));
		tr("${local_minus_country_cd_digit_reading_contact_rate_key}",
				lcData.get(3).localMinusCountryKey);
		tr("${local_minus_country_cd_digit_reading_contact_rate_value}",
				of(lcData.get(3).localMinusCountry));
		tr("${local_mp_digit_reading_contact_rate}", pf(lcData.get(4).local));
		tr("${country_mp_digit_reading_contact_rate}",
				pf(lcData.get(4).country));
		tr("${local_minus_country_mp_digit_reading_contact_rate_key}",
				lcData.get(4).localMinusCountryKey);
		tr("${local_minus_country_mp_digit_reading_contact_rate_value}",
				of(lcData.get(4).localMinusCountry));
		//Wechat data
		tr("${local_wechat_reading_contact_rate}", pf(1-localWechatReadingRate));
		tr("${country_wechat_reading_contact_rate}",pf(1-countryWechatReadingRate));
		tr("${local_minus_country_wechat_reading_contact_rate_key}",
				localWechatReadingRate < countryWechatReadingRate ? "��"
						: "��");
		tr("${local_minus_country_wechat_reading_contact_rate_value}",
				of(Math.abs(localWechatReadingRate-countryWechatReadingRate)));
	}

	@Override
	protected void chart()
	{
//		BaseRow.sort(areaData);
//		Collections.reverse(areaData);
//		BaseRow.chart(areaData);// chart20
//		Collections.reverse(areaData);

		String city = xlsx.getContent(xlsx.getRow(0), 1);
		String country = xlsx.getContent(xlsx.getRow(0), 2);

		Map<String, String> replaceData = new LinkedHashMap<String, String>();
		replaceData.put("${city}", city);
		replaceData.put("${country}", country);
		
		DoubleValueRow.sortExceptLastByV1(lcDataForChart); //new
		DoubleValueRow.chart(lcDataForChart, city, country, replaceData);// chart21
	}

	private class TableRow
	{
		private double local;
		private double country;
		private double localMinusCountry;
		private String localMinusCountryKey;
	}

}
