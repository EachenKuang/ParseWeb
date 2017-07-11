package cn.edu.xmu.software.binarykang.adult.chapter02.section01;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.MultiType;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析2.1.3图书阅读率
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-14
 *
 */
public final class BookReadingRate extends AdultBaseAction
{
	private double localBookReadingRate;
	private double countryBookReadingRate;

	private List<BaseRow> genderData;
	private List<BaseRow> urbanData;
	private List<BaseRow> occupationData;
	private List<BaseRow> educationData;
	private List<BaseRow> ageData;
	private List<List<BaseRow>> data;

	private List<BaseRow> bookReadingRateData;
	private List<BaseRow> synthesisReadingRateData;

	public BookReadingRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		genderData = ListFactory.getBaseRows();
		urbanData = ListFactory.getBaseRows();
		ageData = ListFactory.getBaseRows();
		educationData = ListFactory.getBaseRows();
		occupationData = ListFactory.getBaseRows();
		data = ListFactory.getVarTypeRows();
		data.add(genderData);
		data.add(urbanData);
		data.add(ageData);
		data.add(educationData);
		data.add(occupationData);
		bookReadingRateData = ListFactory.getBaseRows();
		synthesisReadingRateData = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		localBookReadingRate = SingleValue.read(xlsx,
				"2.1.3 图书阅读率 第一段文字中的图书阅读率（当地）");
		countryBookReadingRate = SingleValue.read(xlsx,
				"2.1.3 图书阅读率 第一段文字中的图书阅读率（全国）");
		// 读取不同人口特征群体的图书阅读率分布的数据
		MultiType.read(xlsx, data, xlsx.getRowByKey("不同人口特征群体的图书阅读率分布") + 3);
		// 读取各区县图书阅读率排名数据
		BaseRow.read(xlsx, bookReadingRateData,
				xlsx.getRowByKey("各区县图书阅读率排名") + 3, BEGIN_COL + 1);
		// 读取各区县综合阅读率排名数据
//		BaseRow.read(xlsx, synthesisReadingRateData,
//				xlsx.getRowByKey("各区县综合阅读率排名") + 3, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		// 读取行文替换的数据
		tr("${local_book_reading_rate_single}", pf(localBookReadingRate));
		tr("${country_book_reading_rate_single}", pf(countryBookReadingRate));
		tr("${local_minus_country_book_reading_rate_single_key}",
				localBookReadingRate > countryBookReadingRate ? "高出" : "低出");
		tr("${local_minus_country_book_reading_rate_single_value}",
				of(Math.abs(localBookReadingRate - countryBookReadingRate)));
		// 替换年龄相关的数据
		BaseRow.sort(ageData);
		tr("${best_age_book_reading_rate_key}", ageData.get(0).key);
		tr("${best_age_book_reading_rate_value}", pf(ageData.get(0).value));
		tr("${second_age_book_reading_rate_key}", ageData.get(1).key);
		tr("${second_age_book_reading_rate_value}", pf(ageData.get(1).value));
		tr("${worst_age_book_reading_rate_key}", ageData.get(ageData.size() - 1).key);
		tr("${worst_age_book_reading_rate_value}",
				pf(ageData.get(ageData.size() - 1).value));
		tr("${worst_second_age_book_reading_rate_key}", ageData.get(ageData.size() - 2).key);
		tr("${worst_second_age_book_reading_rate_value}", pf(ageData.get(ageData.size()-2).value));
		
		// education
		BaseRow.sort(educationData);
		tr("${best_education_book_reading_rate_key}", educationData.get(0).key);
		tr("${best_education_book_reading_rate_value}", pf(educationData.get(0).value));
		tr("${second_education_book_reading_rate_key}", educationData.get(1).key);
		tr("${second_education_book_reading_rate_value}", pf(educationData.get(1).value));
		tr("${worst_education_book_reading_rate_key}", educationData.get(educationData.size()-1).key);
		tr("${worst_education_book_reading_rate_value}", pf(educationData.get(educationData.size()-1).value));
		
		// 替换职业相关的数据
		BaseRow.sortExceptLast(occupationData);
		tr("${best_occupation_book_reading_rate_key}", occupationData.get(0).key);
		tr("${best_occupation_book_reading_rate_value}",
				pf(occupationData.get(0).value));
		tr("${second_occupation_book_reading_rate_key}", occupationData.get(1).key);
		tr("${third_occupation_book_reading_rate_key}", occupationData.get(2).key);
		tr("${fourth_occupation_book_reading_rate_key}", occupationData.get(3).key);
		tr("${fifth_occupation_book_reading_rate_key}", occupationData.get(4).key);
		tr("${sixth_occupation_book_reading_rate_key}", occupationData.get(5).key);
		tr("${seventh_occupation_book_reading_rate_key}", occupationData.get(6).key);
		tr("${eighth_occupation_book_reading_rate_key}", occupationData.get(7).key);
		tr("${second_last_occupation_book_reading_rate_key}",
				occupationData.get(occupationData.size() - 3).key);
		tr("${worst_occupation_book_reading_rate_key}",
				occupationData.get(occupationData.size() - 2).key);
		String higherThanLocalOccupationReadingRateKeys = "";
		for(int i=0; i<=occupationData.size()-1; i++){
			if(occupationData.get(i).value>localBookReadingRate)
				higherThanLocalOccupationReadingRateKeys += ("“"+occupationData.get(i).key+"”"+"、");
		}
		
		tr("${higher_than_local_occupation_reading_rate_keys}",
				higherThanLocalOccupationReadingRateKeys.substring(0, higherThanLocalOccupationReadingRateKeys.length()-1));
		// 替换性别相关的数据
		tr("${male_book_reading_rate_value}", pf(genderData.get(0).value));
		tr("${female_book_reading_rate_value}", pf(genderData.get(1).value));
		tr("${male_minus_female_book_reading_rate_key}",
				genderData.get(0).value > genderData.get(1).value ? "高于" : "低于");
		tr("${male_minus_female_book_reading_rate_value}",
				of(Math.abs(genderData.get(0).value - genderData.get(1).value)));
		// 替换城乡相关的数据
		tr("${urban_book_reading_rate_value}", pf(urbanData.get(0).value));
		tr("${village_book_reading_rate_value}", pf(urbanData.get(1).value));
		tr("${urban_minus_village_book_reading_rate_value}",
				of(Math.abs(urbanData.get(0).value - urbanData.get(1).value)));
		tr("${urban_minus_village_book_reading_rate_key}",
				urbanData.get(0).value > urbanData.get(1).value ? "高于" : "低于");
		
		// 替换图书阅读率相关的变量
		BaseRow.sort(bookReadingRateData);
		tr("${best_area_book_book_reading_rate_key}", bookReadingRateData.get(0).key);
		tr("${best_area_book_book_reading_rate_value}",
				pf(bookReadingRateData.get(0).value));
		int count_area_book_reading_rate_higher_than_local = 0;
		String area_book_reading_rate_higher_than_local = "";
		for(BaseRow br: bookReadingRateData){
			if(br.value > localBookReadingRate){
				count_area_book_reading_rate_higher_than_local += 1;
				area_book_reading_rate_higher_than_local += (br.key+"、");
			}
		}
		area_book_reading_rate_higher_than_local = area_book_reading_rate_higher_than_local
				.substring(0, area_book_reading_rate_higher_than_local.length()-1);
		tr("${count_area_book_reading_rate_higher_than_local}", tf(count_area_book_reading_rate_higher_than_local));
		tr("${area_book_reading_rate_higher_than_local}", area_book_reading_rate_higher_than_local);
		// 替换综合阅读率相关的变量
//		tr("${best_area_synthesis_reading_rate_key}",
//				synthesisReadingRateData.get(0).key);
//		tr("${best_area_synthesis_reading_rate_value}",
//				pf(synthesisReadingRateData.get(0).value));
//		tr("${second_area_synthesis_reading_rate_key}",
//				synthesisReadingRateData.get(1).key);
//		tr("${second_area_synthesis_reading_rate_value}",
//				pf(synthesisReadingRateData.get(1).value));
//		tr("${third_area_synthesis_reading_rate_key}",
//				synthesisReadingRateData.get(2).key);
//		tr("${third_area_synthesis_reading_rate_value}",
//				pf(synthesisReadingRateData.get(2).value));

	}

	@Override
	protected void chart()
	{
		data.add(bookReadingRateData);
		BaseRow.sort(bookReadingRateData);
		MultiType.table(docx, data, "${reading_rate_table_key_%d}",
				"${reading_rate_table_value_%d}",
//				"Resource/adult/chapter02/book_reading_rate_row.xml");
				"Resource/adult/chapter01/media_contact_type_area_row.xml");
//		${newspaper_reading_num_key_5}	${newspaper_reading_num_value_5}
		// 替换图书阅读率表格
//		BaseRow.sort(bookReadingRateData);
//		BaseRow.tableWithIndex(docx, bookReadingRateData,
//				"Resource/adult/chapter02/area_book_reading_rate_row.xml");

		// 替换综合阅读率表格
//		BaseRow.sort(synthesisReadingRateData);
//		BaseRow.tableWithIndex(docx, synthesisReadingRateData,
//				"Resource/adult/chapter02/synthesis_reading_rate_odd_row.xml",
//				"Resource/adult/chapter02/synthesis_reading_rate_even_row.xml");
	}
}
