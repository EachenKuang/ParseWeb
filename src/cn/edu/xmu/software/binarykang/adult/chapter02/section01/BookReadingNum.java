package cn.edu.xmu.software.binarykang.adult.chapter02.section01;

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
 * 解析2.1.4图书阅读量
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookReadingNum extends AdultBaseAction
{
	private List<BaseRow> genderData;
	private List<BaseRow> urbanData;
	private List<BaseRow> occupationData;
	private List<BaseRow> educationData;
	private List<BaseRow> ageData;
	private List<List<BaseRow>> data;

	private List<BaseRow> areaData;

	private double localBookReadingNum;
	private double countryBookReadingNum;

	public BookReadingNum(Docx docx, Xlsx xlsx)
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
		areaData = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		localBookReadingNum = SingleValue.read(xlsx,
				"2.1.4图书阅读量 第一行文字中的图书阅读量（当地）");
		countryBookReadingNum = SingleValue.read(xlsx,
				"2.1.4图书阅读量 第一行文字中的图书阅读量（全国）");
		MultiType.read(xlsx, data, "不同人口特征图书阅读量");
		BaseRow.read(xlsx, areaData, xlsx.getRowByKey("各区县阅读量排名") + 2,
				BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${local_book_reading_num_value}", tf(localBookReadingNum));
		tr("${country_book_reading_num_value}", tf(countryBookReadingNum));
		tr("${local_minus_country_book_reading_num_key}",
				localBookReadingNum > countryBookReadingNum ? "高" : "低");
		tr("${local_minus_country_book_reading_num_value}", tf(Math.abs(localBookReadingNum-countryBookReadingNum)));
		
		// 替换性别相关的数据
		//BaseRow.sort(genderData);
		tr("${best_gender_reading_num_value}", tf(genderData.get(0).value));
		tr("${second_gender_reading_num_value}", tf(genderData.get(1).value));
		tr("${male_minus_female_reading_num_key}", 
				genderData.get(0).value-genderData.get(1).value>0?"高出":"低出");
		tr("${male_minus_female_reading_num_value}",
				tf(Math.abs(genderData.get(0).value-genderData.get(1).value)));
		// 替换城乡相关的数据
		tr("${urban_reading_num_value}", tf(urbanData.get(0).value));
		tr("${village_reading_num_value}", tf(urbanData.get(1).value));
		tr("${urban_minus_village_reading_num_key}",
				urbanData.get(0).value > urbanData.get(1).value ? "高出" : "低出");
		tr("${urban_minus_village_reading_num_value}",
				tf(Math.abs(urbanData.get(0).value - urbanData.get(1).value)));
		// 替换年龄相关的数据
		BaseRow.sort(ageData);
		tr("${best_age_reading_num_key}", ageData.get(0).key);
		tr("${best_age_reading_num_value}", tf(ageData.get(0).value));
		tr("${second_age_reading_num_key}", ageData.get(1).key);
		tr("${second_age_reading_num_value}", tf(ageData.get(1).value));
		tr("${worst_second_age_reading_num_key}", ageData.get(ageData.size()-2).key);
		tr("${worst_second_age_reading_num_value}", tf(ageData.get(ageData.size()-2).value));
		tr("${worst_age_reading_num_key}", ageData.get(ageData.size()-1).key);
		tr("${worst_age_reading_num_value}", tf(ageData.get(ageData.size()-1).value));
		// 替换学历相关的数据
		tr("${best_education_reading_num_key}", educationData.get(0).key);
		tr("${best_education_reading_num_value}", tf(educationData.get(0).value));
		tr("${worst_education_reading_num_key}", educationData.get(educationData.size()-1).key);
		tr("${worst_education_reading_num_value}", tf(educationData.get(educationData.size()-1).value));
		
		// 替换职业相关的数据
		BaseRow.sortExceptLast(occupationData);
		tr("${best_occupation_reading_num_key}", occupationData.get(0).key);
		tr("${best_occupation_reading_num_value}",
				tf(occupationData.get(0).value));
		tr("${second_last_occupation_reading_num_key}",
				occupationData.get(occupationData.size() - 3).key);
		tr("${worst_occupation_reading_num_key}",
				occupationData.get(occupationData.size() - 2).key);
		String higherThanLocalOccupationReadingNumKeys = "";
		for(int i=0; i<=occupationData.size()-1; i++){
			if(occupationData.get(i).value>localBookReadingNum)
				higherThanLocalOccupationReadingNumKeys += ("“"+occupationData.get(i).key+"”"+"、");
		}
		
		tr("${higher_than_local_occupation_reading_num_keys}",
				higherThanLocalOccupationReadingNumKeys.substring(0, higherThanLocalOccupationReadingNumKeys.length()-1));
		// 替换区县相关的数据
		BaseRow.sort(areaData);
		int higherThanLocalAreaReadingNum = 0;
		String higherThanLocalAreaReadingNumKeys = "";
		for(int i=0; i<=areaData.size()-1; i++){
			if(areaData.get(i).value>localBookReadingNum){
				higherThanLocalAreaReadingNum++;
				higherThanLocalAreaReadingNumKeys += (areaData.get(i).key+"、");
			}
		}
		tr("${higher_than_local_area_reading_num}", tf(higherThanLocalAreaReadingNum));
		tr("${higher_than_local_area_reading_num_keys}", 
				higherThanLocalAreaReadingNumKeys.substring(0, higherThanLocalAreaReadingNumKeys.length()-1));
		tr("${best_area_reading_num_key}", areaData.get(0).key);
		tr("${best_area_reading_num_value}", tf(areaData.get(0).value));
		//tr("${second_area_reading_num_key}", areaData.get(1).key);
		//tr("${second_area_reading_num_value}", tf(areaData.get(1).value));
		//tr("${third_area_reading_num_key}", areaData.get(2).key);
		//tr("${third_area_reading_num_value}", tf(areaData.get(2).value));
	}

	@Override
	protected void chart()
	{
		// 对学历数据进行排序
//		BaseRow.sort(educationData);
//		// 对职业或身份数据进行排序
//		BaseRow.sortExceptLast(occupationData);
		data.add(areaData);
		BaseRow.sort(areaData);
		MultiType.table(docx, data, "${reading_num_table_key_%d}",
				"${reading_num_table_value_%d}",
//				"Resource/adult/chapter02/book_reading_rate_row.xml",
				"Resource/adult/chapter01/media_contact_type_area_row.xml",
				GF.t);

		// 对各区县数据进行排序
//		BaseRow.sort(areaData);
//		BaseRow.tableWithIndex(docx, areaData,
//				"Resource/adult/chapter02/area_book_reading_num_row.xml", GF.t);
	}
}
