package cn.edu.xmu.software.binarykang.adult.chapter03.section02;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>3.2.1.4期刊读者的阅读偏好
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 * 
 */
public final class MagazineReaderPreference extends AdultBaseAction
{
	private List<BaseRow> data;
	private List<DoubleValueRow> mfData;
	private List<DoubleValueRow> uvData;

	public MagazineReaderPreference(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		mfData = ListFactory.getDoubleValueRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "不同人群的期刊阅读偏好");
		DoubleValueRow.read(xlsx, mfData, "不同性别的期刊阅读偏好");
		DoubleValueRow.read(xlsx, uvData, "城乡期刊读者的阅读偏好");
	}

	@Override
	protected void replace()
	{
		// 替换不同人群的期刊阅读偏好相关的变量
		BaseRow.sortExceptLast(data);
		tr("${best_magazine_reading_preference_key}", data.get(0).key);
		tr("${best_magazine_reading_preference_value}", pf(data.get(0).value));
		tr("${second_magazine_reading_preference_key}", data.get(1).key);
		tr("${second_magazine_reading_preference_value}", pf(data.get(1).value));
		tr("${third_magazine_reading_preference_key}", data.get(2).key);
		tr("${third_magazine_reading_preference_value}", pf(data.get(2).value));
		tr("${fourth_magazine_reading_preference_key}", data.get(data.size() - 2).key);
		tr("${fourth_magazine_reading_preference_value}", pf(data.get(data.size() - 2).value));
		tr("${fiveth_magazine_reading_preference_key}", data.get(data.size() - 3).key);
		tr("${fiveth_magazine_reading_preference_value}", pf(data.get(data.size() - 3).value));
		tr("${sixth_magazine_reading_preference_key}", data.get(data.size() - 1).key);
		tr("${sixth_magazine_reading_preference_value}", pf(data.get(data.size() - 1).value));
		
		tr("${last_second_magazine_reading_preference_key}", data.get(data.size() - 2).key);
		tr("${last_second_magazine_reading_preference_value}", pf(data.get(data.size() - 2).value));
		tr("${worst_magazine_reading_preference_key}", data.get(data.size() - 1).key);
		tr("${worst_magazine_reading_preference_value}", pf(data.get(data.size() - 1).value));
		// 替换不同性别的期刊阅读偏好相关的变量
		DoubleValueRow.sortByV2MinusV1(mfData);
		tr("${best_female_minus_male_magazine_reading_preference}", mfData.get(0).key);
		tr("${second_female_minus_male_magazine_reading_preference}", mfData.get(1).key);
		tr("${third_female_minus_male_magazine_reading_preference}", mfData.get(2).key);
		tr("${fourth_female_minus_male_magazine_reading_preference}", mfData.get(3).key);
		tr("${fiveth_female_minus_male_magazine_reading_preference}", mfData.get(4).key);

		DoubleValueRow.sortByV1MinusV2(mfData);
		tr("${best_male_minus_female_magazine_reading_preference}", mfData.get(0).key);
		tr("${second_male_minus_female_magazine_reading_preference}", mfData.get(1).key);
		tr("${third_male_minus_female_magazine_reading_preference}", mfData.get(2).key);
		tr("${fourth_male_minus_female_magazine_reading_preference}", mfData.get(3).key);
		tr("${fiveth_male_minus_female_magazine_reading_preference}", mfData.get(4).key);
		tr("${sixth_male_minus_female_magazine_reading_preference}", mfData.get(5).key);

		// 替换城乡期刊读者的阅读偏好相关的变量
		DoubleValueRow.sortByV1MinusV2(uvData);
		tr("${best_urban_minus_village_magazine_reading_preference}", uvData.get(0).key);
		tr("${second_urban_minus_village_magazine_reading_preference}", uvData.get(1).key);
		tr("${third_urban_minus_village_magazine_reading_preference}", uvData.get(2).key);
		tr("${fourth_urban_minus_village_magazine_reading_preference}", uvData.get(3).key);

		DoubleValueRow.sortByV2MinusV1(uvData);
		tr("${best_village_minus_urban_magazine_reading_preference}", uvData.get(0).key);
		tr("${second_village_minus_urban_magazine_reading_preference}", uvData.get(1).key);
		tr("${third_village_minus_urban_magazine_reading_preference}", uvData.get(2).key);
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		BaseRow.table(docx, data, 
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
		DoubleValueRow.sortExceptLastByV1(mfData);
		DoubleValueRow.chart(mfData, "男", "女");// chart13

		DoubleValueRow.sortExceptLastByV1(uvData);
		Collections.reverse(uvData);
		DoubleValueRow.reverseValue(uvData);
		DoubleValueRow.chart(uvData);// chart14
		DoubleValueRow.reverseValue(uvData);
		Collections.reverse(uvData);
	}

}
