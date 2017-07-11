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
 * ����2.1.4ͼ���Ķ���
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
				"2.1.4ͼ���Ķ��� ��һ�������е�ͼ���Ķ��������أ�");
		countryBookReadingNum = SingleValue.read(xlsx,
				"2.1.4ͼ���Ķ��� ��һ�������е�ͼ���Ķ�����ȫ����");
		MultiType.read(xlsx, data, "��ͬ�˿�����ͼ���Ķ���");
		BaseRow.read(xlsx, areaData, xlsx.getRowByKey("�������Ķ�������") + 2,
				BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${local_book_reading_num_value}", tf(localBookReadingNum));
		tr("${country_book_reading_num_value}", tf(countryBookReadingNum));
		tr("${local_minus_country_book_reading_num_key}",
				localBookReadingNum > countryBookReadingNum ? "��" : "��");
		tr("${local_minus_country_book_reading_num_value}", tf(Math.abs(localBookReadingNum-countryBookReadingNum)));
		
		// �滻�Ա���ص�����
		//BaseRow.sort(genderData);
		tr("${best_gender_reading_num_value}", tf(genderData.get(0).value));
		tr("${second_gender_reading_num_value}", tf(genderData.get(1).value));
		tr("${male_minus_female_reading_num_key}", 
				genderData.get(0).value-genderData.get(1).value>0?"�߳�":"�ͳ�");
		tr("${male_minus_female_reading_num_value}",
				tf(Math.abs(genderData.get(0).value-genderData.get(1).value)));
		// �滻������ص�����
		tr("${urban_reading_num_value}", tf(urbanData.get(0).value));
		tr("${village_reading_num_value}", tf(urbanData.get(1).value));
		tr("${urban_minus_village_reading_num_key}",
				urbanData.get(0).value > urbanData.get(1).value ? "�߳�" : "�ͳ�");
		tr("${urban_minus_village_reading_num_value}",
				tf(Math.abs(urbanData.get(0).value - urbanData.get(1).value)));
		// �滻������ص�����
		BaseRow.sort(ageData);
		tr("${best_age_reading_num_key}", ageData.get(0).key);
		tr("${best_age_reading_num_value}", tf(ageData.get(0).value));
		tr("${second_age_reading_num_key}", ageData.get(1).key);
		tr("${second_age_reading_num_value}", tf(ageData.get(1).value));
		tr("${worst_second_age_reading_num_key}", ageData.get(ageData.size()-2).key);
		tr("${worst_second_age_reading_num_value}", tf(ageData.get(ageData.size()-2).value));
		tr("${worst_age_reading_num_key}", ageData.get(ageData.size()-1).key);
		tr("${worst_age_reading_num_value}", tf(ageData.get(ageData.size()-1).value));
		// �滻ѧ����ص�����
		tr("${best_education_reading_num_key}", educationData.get(0).key);
		tr("${best_education_reading_num_value}", tf(educationData.get(0).value));
		tr("${worst_education_reading_num_key}", educationData.get(educationData.size()-1).key);
		tr("${worst_education_reading_num_value}", tf(educationData.get(educationData.size()-1).value));
		
		// �滻ְҵ��ص�����
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
				higherThanLocalOccupationReadingNumKeys += ("��"+occupationData.get(i).key+"��"+"��");
		}
		
		tr("${higher_than_local_occupation_reading_num_keys}",
				higherThanLocalOccupationReadingNumKeys.substring(0, higherThanLocalOccupationReadingNumKeys.length()-1));
		// �滻������ص�����
		BaseRow.sort(areaData);
		int higherThanLocalAreaReadingNum = 0;
		String higherThanLocalAreaReadingNumKeys = "";
		for(int i=0; i<=areaData.size()-1; i++){
			if(areaData.get(i).value>localBookReadingNum){
				higherThanLocalAreaReadingNum++;
				higherThanLocalAreaReadingNumKeys += (areaData.get(i).key+"��");
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
		// ��ѧ�����ݽ�������
//		BaseRow.sort(educationData);
//		// ��ְҵ��������ݽ�������
//		BaseRow.sortExceptLast(occupationData);
		data.add(areaData);
		BaseRow.sort(areaData);
		MultiType.table(docx, data, "${reading_num_table_key_%d}",
				"${reading_num_table_value_%d}",
//				"Resource/adult/chapter02/book_reading_rate_row.xml",
				"Resource/adult/chapter01/media_contact_type_area_row.xml",
				GF.t);

		// �Ը��������ݽ�������
//		BaseRow.sort(areaData);
//		BaseRow.tableWithIndex(docx, areaData,
//				"Resource/adult/chapter02/area_book_reading_num_row.xml", GF.t);
	}
}
