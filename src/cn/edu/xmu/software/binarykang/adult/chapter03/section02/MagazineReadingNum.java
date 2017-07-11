package cn.edu.xmu.software.binarykang.adult.chapter03.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.MultiType;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>3.2.1.2期刊阅读量
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class MagazineReadingNum extends AdultBaseAction
{
	private double localMagazineReadingNum;
	private double countryMagazineReadingNum;

	private List<BaseRow> ageData;
	private List<BaseRow> educationData;
	private List<BaseRow> occupationData;
	private List<BaseRow> genderData;
	private List<BaseRow> uvData;
	private List<BaseRow> areaData;
	private List<List<BaseRow>> data;

	public MagazineReadingNum(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		genderData = ListFactory.getBaseRows();
		uvData = ListFactory.getBaseRows();
		ageData = ListFactory.getBaseRows();
		educationData = ListFactory.getBaseRows();
		occupationData = ListFactory.getBaseRows();
		areaData = ListFactory.getBaseRows();
		data = ListFactory.getVarTypeRows();
		data.add(genderData);
		data.add(uvData);
		data.add(ageData);
		data.add(educationData);
		data.add(occupationData);
		data.add(areaData);
	}

	@Override
	protected void readData()
	{
		localMagazineReadingNum = SingleValue.read(xlsx,
				"3.2.1.2 期刊阅读量 第一段文字中的期刊阅读量（当地）");
		countryMagazineReadingNum = SingleValue.read(xlsx,
				"3.2.1.2 期刊阅读量 第一段文字中的期刊阅读量（全国）");
		MultiType.read(xlsx, data, "不同人群的期刊阅读量分布");
	}

	@Override
	protected void replace()
	{
		// 行文替换
		tr("${local_average_magazine_reading_num_value}",
				tf(localMagazineReadingNum));
		tr("${country_average_magazine_reading_num_value}",
				tf(countryMagazineReadingNum));
		tr("${local_minus_country_average_magazine_reading_num_key}",
				localMagazineReadingNum > countryMagazineReadingNum ? "高" : "低");
		tr("${local_minus_country_average_magazine_reading_num_value}",
				tf(Math.abs(localMagazineReadingNum-countryMagazineReadingNum)));
		
		// 替换性别相关的数据
		tr("${male_magazine_reading_num_value}", tf(genderData.get(0).value));
		tr("${female_magazine_reading_num_value}", tf(genderData.get(1).value));
		tr("${male_minus_female_magazine_reading_num_key}",
				genderData.get(0).value > genderData.get(1).value ? "高" : "低");
		tr("${male_minus_female_magazine_reading_num_value}", 
				tf(Math.abs(genderData.get(0).value-genderData.get(1).value)));
		// 替换年龄相关的数据
		BaseRow.sort(ageData);
		tr("${best_age_magazine_reading_num_key}", ageData.get(0).key);
		tr("${best_age_magazine_reading_num_value}", tf(ageData.get(0).value));
		tr("${worst_age_magazine_reading_num_key}", ageData.get(ageData.size()-1).key);
		tr("${worst_age_magazine_reading_num_value}", tf(ageData.get(ageData.size()-1).value));
		//tr("${second_age_magazine_reading_num_key}", ageData.get(1).key);
		//tr("${second_age_magazine_reading_num_value}", tf(ageData.get(1).value));
		//tr("${third_age_magazine_reading_num_key}", ageData.get(2).key);
		//tr("${third_age_magazine_reading_num_value}", tf(ageData.get(2).value));
		// 替换受教育程度相关的数据
		BaseRow.sort(educationData);
		tr("${best_education_magazine_num_key}", educationData.get(0).key);
		tr("${best_education_magazine_num_value}",
				tf(educationData.get(0).value));
		//tr("${second_education_magazine_num_key}", educationData.get(1).key);
		//tr("${second_education_magazine_num_value}",
		//		tf(educationData.get(1).value));
		tr("${worst_education_magazine_num_key}",
				educationData.get(educationData.size() - 1).key);
		tr("${worst_education_magazine_num_value}",
				tf(educationData.get(educationData.size() - 1).value));
		// 替换职业相关的数据
		BaseRow.sortExceptLast(occupationData);
		tr("${best_occupation_magazine_reading_num_key}",
				occupationData.get(0).key);
		tr("${best_occupation_magazine_reading_num_value}",
				tf(occupationData.get(0).value));
		//tr("${second_occupation_magazine_reading_num_key}",
		//		occupationData.get(1).key);
		//tr("${third_occupation_magazine_reading_num_key}",
		//		occupationData.get(2).key);
		//tr("${fourth_occupation_magazine_reading_num_key}",
		//		occupationData.get(3).key);
		tr("${worst_occupation_magazine_reading_num_key}",
				occupationData.get(occupationData.size() - 2).key);
		tr("${worst_occupation_magazine_reading_num_value}",
				tf(occupationData.get(occupationData.size() - 2).value));
		// 替换城乡相关的数据
		tr("${urban_average_magazine_reading_num_value}",
				tf(uvData.get(0).value));
		tr("${village_average_magazine_reading_num_value}",
				tf(uvData.get(1).value));
		tr("${urban_minus_village_magazine_reading_num_key}",
				uvData.get(0).value > uvData.get(1).value ? "高" : "低");
		tr("${urban_minus_village_magazine_reading_num_value}",
				tf(Math.abs(uvData.get(0).value-uvData.get(1).value)));
		// area
		BaseRow.sort(areaData);
		tr("${best_area_magazine_reading_num_key}", areaData.get(0).key);
		tr("${best_area_magazine_reading_num_value}", tf(areaData.get(0).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sort(areaData);
		MultiType.table(docx, data, "${magazine_reading_num_key_%d}",
				"${magazine_reading_num_value_%d}",
//				"Resource/adult/chapter03/newspaper_reading_num_row.xml", 
				"Resource/adult/chapter01/media_contact_type_area_row.xml",GF.t);
	}

}
