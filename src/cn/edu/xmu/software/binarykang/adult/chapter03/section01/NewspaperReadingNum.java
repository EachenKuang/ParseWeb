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
 * ����=>3.1.2��ֽ�Ķ���
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
				"3.1.2��ֽ�Ķ��� ��һ�������еı�ֽ�Ķ��������أ�");
		countryNewspaperReadingNum = SingleValue.read(xlsx,
				"3.1.2��ֽ�Ķ��� ��һ�������еı�ֽ�Ķ�����ȫ����");
		MultiType.read(xlsx, data, "��ͬ��Ⱥ�ı�ֽ�Ķ����ֲ�");
	}

	@Override
	protected void replace()
	{
		// �����滻
		tr("${local_average_newspaper_reading_num_key}",
				tf(localNewspaperReadingNum));
		tr("${country_average_newspaper_reading_num_key}",
				tf(countryNewspaperReadingNum));
		tr("${local_minus_country_average_newspaper_reading_num_key}",
				localNewspaperReadingNum > countryNewspaperReadingNum ? "��"
						: "��");
		tr("${local_minus_country_average_newspaper_reading_num_value}",
				tf(Math.abs(localNewspaperReadingNum - countryNewspaperReadingNum)));
		
		// �滻�Ա���ص�����
		tr("${male_newspaper_reading_num_value}", tf(genderData.get(0).value));
		tr("${female_newspaper_reading_num_value}", tf(genderData.get(1).value));
		tr("${male_minus_female_newspaper_reading_num_key}",
				genderData.get(0).value > genderData.get(1).value ? "��" : "��");
		tr("${male_minus_female_newspaper_reading_num_value}",
				tf(Math.abs(genderData.get(0).value - genderData.get(1).value)));
		// �滻������ص�����
		BaseRow.sort(ageData);
		tr("${best_age_newspaper_reading_num_key}", ageData.get(0).key);
		tr("${best_age_newspaper_reading_num_value}", tf(ageData.get(0).value));
		tr("${second_age_newspaper_reading_num_key}", ageData.get(ageData.size()-1).key);
		tr("${second_age_newspaper_reading_num_value}",
				tf(ageData.get(ageData.size()-1).value));
		// �滻�ܽ����̶���ص�����
		BaseRow.sort(educationData);
		tr("${best_education_newspaper_num_key}", educationData.get(0).key);
		tr("${best_education_newspaper_num_value}", tf(educationData.get(0).value));
		//tr("${second_education_newspaper_num_key}", educationData.get(1).key);
		//tr("${second_education_newspaper_num_value}",
		//		tf(educationData.get(1).value));
		tr("${worst_education_newspaper_num_key}", educationData.get(educationData.size()-1).key);
		tr("${worst_education_newspaper_num_value}",
				tf(educationData.get(educationData.size()-1).value));
		// �滻ְҵ��ص�����
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
		// �滻������ص�����
		tr("${urban_average_newspaper_reading_num_value}",
				tf(uvData.get(0).value));
		tr("${village_average_newspaper_reading_num_value}",
				tf(uvData.get(1).value));
		tr("${urban_minus_village_average_newspaper_reading_num_key}",
				uvData.get(0).value > uvData.get(1).value? "��" : "��");
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
