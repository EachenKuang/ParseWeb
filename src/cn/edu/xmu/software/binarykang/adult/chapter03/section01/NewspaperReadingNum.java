package cn.edu.xmu.software.binarykang.adult.chapter03.section01;

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
 * 解析=>3.1.2报纸阅读量
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class NewspaperReadingNum extends AdultBaseAction
{
	private double localNewspaperReadingNum;
	private double countryNewspaperReadingNum;

	private List<BaseRow> ageData;
	private List<BaseRow> educationData;
	private List<BaseRow> occupationData;
	private List<BaseRow> genderData;
	private List<BaseRow> uvData;
	private List<BaseRow> areaData;
	private List<List<BaseRow>> data;

	public NewspaperReadingNum(Docx docx, Xlsx xlsx)
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
		localNewspaperReadingNum = SingleValue.read(xlsx,
				"3.1.2报纸阅读量 第一段文字中的报纸阅读量（当地）");
		countryNewspaperReadingNum = SingleValue.read(xlsx,
				"3.1.2报纸阅读量 第一段文字中的报纸阅读量（全国）");
		MultiType.read(xlsx, data, "不同人群的报纸阅读量分布");
	}

	@Override
	protected void replace()
	{
		// 行文替换
		tr("${local_average_newspaper_reading_num_key}",
				tf(localNewspaperReadingNum));
		tr("${country_average_newspaper_reading_num_key}",
				tf(countryNewspaperReadingNum));
		tr("${local_minus_country_average_newspaper_reading_num_key}",
				localNewspaperReadingNum > countryNewspaperReadingNum ? "多"
						: "少");
		tr("${local_minus_country_average_newspaper_reading_num_value}",
				tf(Math.abs(localNewspaperReadingNum - countryNewspaperReadingNum)));
		
		// 替换性别相关的数据
		tr("${male_newspaper_reading_num_value}", tf(genderData.get(0).value));
		tr("${female_newspaper_reading_num_value}", tf(genderData.get(1).value));
		tr("${male_minus_female_newspaper_reading_num_key}",
				genderData.get(0).value > genderData.get(1).value ? "多" : "少");
		tr("${male_minus_female_newspaper_reading_num_value}",
				tf(Math.abs(genderData.get(0).value - genderData.get(1).value)));
		// 替换年龄相关的数据
		BaseRow.sort(ageData);
		tr("${best_age_newspaper_reading_num_key}", ageData.get(0).key);
		tr("${best_age_newspaper_reading_num_value}", tf(ageData.get(0).value));
		tr("${second_age_newspaper_reading_num_key}", ageData.get(ageData.size()-1).key);
		tr("${second_age_newspaper_reading_num_value}",
				tf(ageData.get(ageData.size()-1).value));
		// 替换受教育程度相关的数据
		BaseRow.sort(educationData);
		tr("${best_education_newspaper_num_key}", educationData.get(0).key);
		tr("${best_education_newspaper_num_value}", tf(educationData.get(0).value));
		//tr("${second_education_newspaper_num_key}", educationData.get(1).key);
		//tr("${second_education_newspaper_num_value}",
		//		tf(educationData.get(1).value));
		tr("${worst_education_newspaper_num_key}", educationData.get(educationData.size()-1).key);
		tr("${worst_education_newspaper_num_value}",
				tf(educationData.get(educationData.size()-1).value));
		// 替换职业相关的数据
		BaseRow.sortExceptLast(occupationData);
		tr("${best_occupation_newspaper_reading_num_key}",
				occupationData.get(0).key);
		tr("${best_occupation_newspaper_reading_num_value}",
				tf(occupationData.get(0).value));
		//tr("${second_occupation_newspaper_reading_num_key}",
		//		occupationData.get(1).key);
		tr("${worst_occupation_newspaper_reading_num_key}",
				occupationData.get(occupationData.size()-2).key);
		tr("${worst_occupation_newspaper_reading_num_value}",
				tf(occupationData.get(occupationData.size()-2).value));
//		tr("${third_occupation_newspaper_reading_num_key}",
//				occupationData.get(2).key);
//		tr("${fourth_occupation_newspaper_reading_num_key}",
//				occupationData.get(3).key);
//		tr("${last_third_occupation_newspaper_reading_num_key}",
//				occupationData.get(occupationData.size() - 4).key);
//		tr("${last_second_occupation_newspaper_reading_num_key}",
//				occupationData.get(occupationData.size() - 3).key);
//		tr("${worst_occupation_newspaper_reading_num_key}",
//				occupationData.get(occupationData.size() - 2).key);
		// 替换城乡相关的数据
		tr("${urban_average_newspaper_reading_num_value}",
				tf(uvData.get(0).value));
		tr("${village_average_newspaper_reading_num_value}",
				tf(uvData.get(1).value));
		tr("${urban_minus_village_average_newspaper_reading_num_key}",
				uvData.get(0).value > uvData.get(1).value? "多" : "少");
		tr("${urban_minus_village_average_newspaper_reading_num_value}",
				tf(Math.abs(uvData.get(0).value-uvData.get(1).value)));
		
		// area
//		BaseRow.sort(ageData);
		tr("${best_area_newspaper_reading_num_key}", 
				areaData.get(0).key);
		tr("${best_area_newspaper_reading_num_value}", 
				tf(areaData.get(0).value));
		
	}

	@Override
	protected void chart()
	{
		BaseRow.sort(areaData);
		MultiType.table(docx, data, "${newspaper_reading_num_key_%d}",
				"${newspaper_reading_num_value_%d}",
//				"Resource/adult/chapter03/newspaper_reading_num_row.xml", 
				"Resource/adult/chapter01/media_contact_type_area_row.xml",GF.t);
	}

}
