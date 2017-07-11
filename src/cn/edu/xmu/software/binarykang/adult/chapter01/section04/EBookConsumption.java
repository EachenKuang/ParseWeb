package cn.edu.xmu.software.binarykang.adult.chapter01.section04;

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
 * ����1.4.4�������������
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-04-14
 *
 */
public final class EBookConsumption extends AdultBaseAction
{
	private double averageEbookConsumeMoney;
	
	private List<BaseRow> genderData;
	private List<BaseRow> urbanData;
	private List<BaseRow> areaData;
	private List<BaseRow> occupationData;
	private List<BaseRow> educationData;
	private List<BaseRow> ageData;
	private List<List<BaseRow>> data;

	public EBookConsumption(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		genderData = ListFactory.getBaseRows();
		urbanData = ListFactory.getBaseRows();
		ageData = ListFactory.getBaseRows();
		educationData = ListFactory.getBaseRows();
		occupationData = ListFactory.getBaseRows();
		areaData = ListFactory.getBaseRows();
		data = ListFactory.getVarTypeRows();
		data.add(genderData);
		data.add(urbanData);
		data.add(ageData);
		data.add(educationData);
		data.add(occupationData);
		data.add(areaData);
	}

	@Override
	protected void readData()
	{
		averageEbookConsumeMoney = SingleValue.read(xlsx,
				"1.4.4���������������һ�������еĵ����鹺����", 1, 1);
		MultiType.read(xlsx, data, "��ͬ�˿��������Էѵ������������");
	}

	@Override
	protected void replace()
	{
		tr("${average_ebook_consume_money}", tf(averageEbookConsumeMoney));
		
		// �滻�Ա���ص�����
		tr("${male_ebook_consume_money}", tf(genderData.get(0).value));
		tr("${female_ebook_consume_money}", tf(genderData.get(1).value));
		tr("${male_minus_female_consume_key}", genderData.get(0).value > genderData.get(1).value ? "�߳�":"�ͳ�");
		tr("${male_minus_female_consume_value}", tf(Math.abs(genderData.get(0).value-genderData.get(1).value)));
		
		// urban and village
		tr("${urban_ebook_consume_money}", tf(urbanData.get(0).value));
		tr("${village_ebook_consume_money}", tf(urbanData.get(1).value));
		tr("${urban_minus_village_ebook_consume_key}", urbanData.get(0).value>urbanData.get(1).value?"�߳�":"�ͳ�");
		tr("${urban_minus_village_ebook_consume_value}", tf(Math.abs(urbanData.get(0).value-urbanData.get(1).value)));
		
		// age
		BaseRow.sort(ageData);
		tr("${best_age_ebook_consume}", ageData.get(0).key);
		tr("${best_age_ebook_consume_money}", tf(ageData.get(0).value));
		tr("${worst_age_ebook_consume}", ageData.get(ageData.size()-1).key);
		tr("${worst_age_ebook_consume_money}", tf(ageData.get(ageData.size()-1).value));
		
		// �滻ѧ����ص�����
		BaseRow.sort(educationData);
		tr("${best_education_ebook_consume}", educationData.get(0).key);
		tr("${worst_education_ebook_consume}", educationData.get(educationData.size()-1).key);
		tr("${best_education_ebook_consume_money}", tf(educationData.get(0).value));
		tr("${worst_education_ebook_consume_money}", tf(educationData.get(educationData.size()-1).value));
		
		// �滻ְҵ��ص�����
		BaseRow.sortExceptLast(occupationData);
		tr("${best_occupation_ebook_consume}", occupationData.get(0).key);
		tr("${best_occupation_ebook_consume_money}",tf(occupationData.get(0).value));
		tr("${worst_occupation_ebook_consume}", occupationData.get(occupationData.size() - 2).key);
		tr("${worst_occupation_ebook_consume_money}", tf(occupationData.get(occupationData.size() - 2).value));
		
		// �滻������ص�����
		BaseRow.sort(areaData);
		tr("${best_area_ebook_consume}", areaData.get(0).key);
		tr("${best_area_ebook_consume_money}", tf(areaData.get(0).value));
		tr("${worst_area_ebook_consume}", areaData.get(areaData.size()-1).key);
		tr("${worst_area_ebook_consume_money}", tf(areaData.get(areaData.size()-1).value));
		//tr("${second_area_ebook_consume}", areaData.get(1).key);
		//tr("${best_minus_second_area_consume_money}", tf(areaData.get(0).value
		//		- areaData.get(1).value));
		
		
	}

	@Override
	protected void chart()
	{
		BaseRow.sort(areaData);
//		BaseRow.sortExceptLast(occupationData);
		
		MultiType.table(docx, data, "${ebook_consumption_key_%d}",
				"${ebook_consumption_value_%d}",
//				"Resource/adult/chapter01/consumption_row.xml", 
				"Resource/adult/chapter01/media_contact_type_area_row.xml",
				GF.t);
	}
	
//	@Override
//	public void process()
//	{
//		readData();
//		replace();
//		chart();
//	}
}
