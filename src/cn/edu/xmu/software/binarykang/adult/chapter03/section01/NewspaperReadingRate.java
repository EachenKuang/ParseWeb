package cn.edu.xmu.software.binarykang.adult.chapter03.section01;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.MultiType;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>3.1.1报纸阅读率
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class NewspaperReadingRate extends AdultBaseAction
{
	private double localNewspaperReadingRate;
	private double countryNewspaperReadingRate;

	private List<BaseRow> ageData;
	private List<BaseRow> educationData;
	private List<BaseRow> occupationData;
	private List<BaseRow> genderData;
	private List<BaseRow> uvData;
	private List<BaseRow> areaData;
	private List<List<BaseRow>> data;

	public NewspaperReadingRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		genderData = ListFactory.getBaseRows();
		uvData = ListFactory.getBaseRows();
		ageData = ListFactory.getBaseRows();
		educationData = ListFactory.getBaseRows();
		occupationData = ListFactory.getBaseRows();
		areaData = ListFactory.getBaseRows();	// 20160422
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
		localNewspaperReadingRate = SingleValue.read(xlsx,
				"3.1.1报纸阅读率 第一行文字中的报纸阅读率（当地）");
		countryNewspaperReadingRate = SingleValue.read(xlsx,
				"3.1.1报纸阅读率 第一行文字中的报纸阅读率（全国）");

		MultiType.read(xlsx, data, xlsx.getRowByKey("不同群体的报纸阅读率分布") + 3);
	}

	@Override
	protected void replace()
	{
		// 行文替换
		tr("${local_average_newspaper_reading_rate}",
				pf(localNewspaperReadingRate));
		tr("${country_average_newspaper_reading_rate}",
				pf(countryNewspaperReadingRate));
		tr("${local_minus_country_average_newspaper_reading_key}",
				localNewspaperReadingRate>countryNewspaperReadingRate?"高出":"低出");
		tr("${local_minus_country_average_newspaper_reading_value}",
				of(Math.abs(localNewspaperReadingRate-countryNewspaperReadingRate)));
		
		// 替换性别相关的数据
		tr("${male_newspaper_reading_rate_value}", pf(genderData.get(0).value));
		tr("${female_newspaper_reading_rate_value}",pf(genderData.get(1).value));
		tr("${male_minus_female_newspaper_reading_rate_key}",
				genderData.get(0).value > genderData.get(1).value ? "高" : "低");
		tr("${male_minus_female_newspaper_reading_rate_value}",
				of(Math.abs(genderData.get(0).value - genderData.get(1).value)));
		
		// 替换年龄相关的数据
		BaseRow.sort(ageData);
		tr("${best_age_newspaper_reading_rate_key}", ageData.get(0).key);
		tr("${best_age_newspaper_reading_rate_value}", pf(ageData.get(0).value));
		tr("${worst_age_newspaper_reading_rate_key}", ageData.get(ageData.size()-1).key);
		tr("${worst_age_newspaper_reading_rate_value}", pf(ageData.get(ageData.size()-1).value));
		// 替换受教育程度相关的数据
		BaseRow.sort(educationData);
		tr("${best_education_newspaper_reading_rate_key}",
				educationData.get(0).key);
		tr("${best_education_newspaper_reading_rate_value}",
				pf(educationData.get(0).value));
		tr("${worst_education_newspaper_reading_rate_key}",
				educationData.get(educationData.size() - 1).key);
		tr("${worst_education_newspaper_reading_rate_value}",
				pf(educationData.get(educationData.size() - 1).value));
		// 替换职业相关的数据
		BaseRow.sortExceptLast(occupationData);
		tr("${best_occupation_newspaper_reading_rate_key}",
				occupationData.get(0).key);
		tr("${best_occupation_newspaper_reading_rate_value}",
				pf(occupationData.get(0).value));
		tr("${second_occupation_newspaper_reading_rate_key}",
				occupationData.get(1).key);
		tr("${third_occupation_newspaper_reading_rate_key}",
				occupationData.get(2).key);
		tr("${third_occupation_newspaper_reading_rate_value}",
				pf(occupationData.get(2).value));
		tr("${second_last_occupation_newspaper_reading_rate_key}",
				occupationData.get(occupationData.size() - 3).key);
		tr("${worst_occupation_newspaper_reading_rate_key}",
				occupationData.get(occupationData.size() - 2).key);
		tr("${worst_occupation_newspaper_reading_rate_value}",
				pf(occupationData.get(occupationData.size()- 2).value));
		// 替换城乡相关的数据
		tr("${urban_newspaper_reading_rate_value}", pf(uvData.get(0).value));
		tr("${village_newspaper_reading_rate_value}", pf(uvData.get(1).value));
		tr("${urban_minus_village_newspaper_reading_rate_key}",
				uvData.get(0).value > uvData.get(1).value ? "高" : "低");
		tr("${urban_minus_village_newspaper_reading_rate_value}",
				of(Math.abs(uvData.get(0).value-uvData.get(1).value)));
		// area
		BaseRow.sort(areaData);
		tr("${best_area_newspaper_reading_rate_key}", areaData.get(0).key);
		tr("${best_area_newspaper_reading_rate_value}", pf(areaData.get(0).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sort(areaData);
		MultiType.table(docx, data, "${newspaper_reading_rate_key_%d}",
				"${newspaper_reading_rate_value_%d}",
//				"Resource/adult/chapter03/newspaper_reading_rate_row.xml");
				"Resource/adult/chapter01/media_contact_type_area_row.xml");
	}
//	@Override
//	public void process()//0729
//	{
//		readData();
//		replace();
//		chart();
//	}
}
