package cn.edu.xmu.software.binarykang.adult.chapter04.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.MultiType;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.3.1手机阅读接触率
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class PhoneReadingContactRate extends AdultBaseAction
{
	private double phoneReadingContactRate;
	private List<BaseRow> uvData;
	private List<BaseRow> genderData;
//	private List<BaseRow> areaData;
	private List<BaseRow> ageData;
	private List<List<BaseRow>> data;

	public PhoneReadingContactRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		uvData = ListFactory.getBaseRows();
		genderData = ListFactory.getBaseRows();
//		areaData = ListFactory.getBaseRows();
		ageData = ListFactory.getBaseRows();
		data = ListFactory.getVarTypeRows();

		data.add(genderData);
		data.add(uvData);
//		data.add(areaData);
	}

	@Override
	protected void readData()
	{
		phoneReadingContactRate = SingleValue.read(xlsx,
				"4.3.1手机阅读接触率 第一行文字中的手机阅读接触率");
		MultiType.read(xlsx, data, xlsx.getRowByKey("手机阅读接触率细分表") + 3);
		BaseRow.read(xlsx, ageData, "手机阅读接触人群的年龄分布特征");
	}

	@Override
	protected void replace()
	{
		// 替换手机阅读接触率细分相关的数据
		tr("${average_area_phone_reading_contact_rate_value}",
				pf(phoneReadingContactRate));
		tr("${male_phone_reading_contact_rate_value}",pf(genderData.get(0).value));	
		tr("${female_phone_reading_contact_rate_value}",pf(genderData.get(1).value));
		tr("${male_minus_female_phone_reading_contact_rate_key}",
				genderData.get(0).value > genderData.get(1).value ? "高" : "低");
		tr("${male_minus_female_phone_reading_contact_rate_value}",
				of(Math.abs(genderData.get(0).value - genderData.get(1).value)));
		
		tr("${urbun_phone_reading_contact_rate_value}",pf(uvData.get(0).value));	
		tr("${village_phone_reading_contact_rate_value}",pf(uvData.get(1).value));
		tr("${urbun_minus_village_phone_reading_contact_rate_key}",
				uvData.get(0).value > uvData.get(1).value ? "高" : "低");
		tr("${urbun_minus_village_phone_reading_contact_rate_value}",
				of(Math.abs(uvData.get(0).value - uvData.get(1).value)));
		
//		BaseRow.sort(areaData);
//		tr("${best_area_phone_reading_contact_rate_key}", areaData.get(0).key);
//		tr("${best_area_phone_reading_contact_rate_value}",
//				pf(areaData.get(0).value));

		// 替换年龄相关的变量
		BaseRow.sort(ageData);
		tr("${best_age_phone_reading_key}", ageData.get(0).key);
		tr("${best_age_phone_reading_value}", pf(ageData.get(0).value));
		tr("${second_age_phone_reading_key}", ageData.get(1).key);
		tr("${second_age_phone_reading_value}", pf(ageData.get(1).value));
		tr("${third_age_phone_reading_key}", ageData.get(2).key);
		tr("${third_age_phone_reading_value}", pf(ageData.get(2).value));
		tr("${fourth_age_phone_reading_key}", ageData.get(3).key);
		tr("${fourth_age_phone_reading_value}", pf(ageData.get(3).value));
		tr("${fiveth_age_phone_reading_key}", ageData.get(4).key);
		tr("${fiveth_age_phone_reading_value}", pf(ageData.get(4).value));
	}

	@Override
	protected void chart()
	{
		MultiType.table(docx, data, "${phone_reading_contact_rate_key_%d}",
				"${phone_reading_contact_rate_value_%d}",
				"Resource/adult/chapter01/media_contact_type_area_row.xml");
		BaseRow.chart(ageData);// chart23
//		${phone_reading_contact_rate_key_1} ${phone_reading_contact_rate_key_0}
//		${phone_reading_contact_rate_value_1}${phone_reading_contact_rate_value_0}
	}

}
