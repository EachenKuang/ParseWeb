package cn.edu.xmu.software.binarykang.adult.chapter04.section04;

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
 * ����=>4.4.1�����Ķ����Ķ��ĽӴ���������Ķ���ӵ����
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class EReaderContactRate extends AdultBaseAction
{
	private double localEReaderContactRate;
	private double countryEReaderContactRate;
	private double localEReaderContactNum;
//	private double countryEReaderContactNum;
//	private double eReaderOwningRate;

	private List<BaseRow> uvEReaderContactRate;
	private List<BaseRow> genderEReaderContactRate;
	private List<BaseRow> ageEReaderContactRate;
	private List<BaseRow> uvEReaderContactNum;
	private List<BaseRow> genderEReaderContactNum;
	
//	private List<BaseRow> areaEReaderContactRate;

//	private List<BaseRow> uvEReaderOwningRate;
//	private List<BaseRow> genderEReaderOwningRate;
//	private List<BaseRow> areaEReaderOwningRate;

	private List<List<BaseRow>> data;
	private List<List<BaseRow>> data2;
	private List<List<BaseRow>> data3;

	public EReaderContactRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		uvEReaderContactRate = ListFactory.getBaseRows();
		genderEReaderContactRate = ListFactory.getBaseRows();
		ageEReaderContactRate = ListFactory.getBaseRows();
		uvEReaderContactNum= ListFactory.getBaseRows();
		genderEReaderContactNum = ListFactory.getBaseRows();
		
		
		
//		areaEReaderContactRate = ListFactory.getBaseRows();

//		uvEReaderOwningRate = ListFactory.getBaseRows();
//		genderEReaderOwningRate = ListFactory.getBaseRows();
//		areaEReaderOwningRate = ListFactory.getBaseRows();

		data = ListFactory.getVarTypeRows();
		data2 = ListFactory.getVarTypeRows();
		data3 = ListFactory.getVarTypeRows();
	}

	@Override
	protected void readData()
	{
		// ��ȡ�����滻������
		localEReaderContactRate = SingleValue.read(xlsx,
				"4.4.1�����Ķ����Ķ��ĽӴ���������Ķ���ӵ���� ��һ�������еĵ����Ķ����Ӵ��ʣ����أ�");
		countryEReaderContactRate = SingleValue.read(xlsx,
				"4.4.1�����Ķ����Ķ��ĽӴ���������Ķ���ӵ���� ��һ�������еĵ����Ķ����Ӵ��ʣ�ȫ����");
//		eReaderOwningRate = SingleValue.read(xlsx, "*��4��4��2 ǰ�����еĵ����Ķ����Ķ�ӵ����.");
		localEReaderContactNum = SingleValue.read(xlsx,
				"�����Ķ����Ķ�����ֵ",1,1);
//		countryEReaderContactNum = SingleValue.read(xlsx,
//				"�����Ķ����Ķ�����ֵ����ȫ��",1,1);
		
		// ��ȡ������Ա�ĵ����Ķ����Ӵ��ʵ�����
		
		data.add(genderEReaderContactRate);
		data.add(uvEReaderContactRate);
		MultiType.read(xlsx, data, xlsx.getRowByKey("�����Ķ����Ķ��Ӵ���") + 3);
		// ��ȡ��������Ķ����Ӵ��ʵ�����
//		data2.clear();
		data2.add(ageEReaderContactRate);
		MultiType.read(xlsx, data2, xlsx.getRowByKey("�����Ķ����Ӵ���Ⱥ������ֲ�����") + 3);
		
		
		
//		data.clear();
		data3.add(genderEReaderContactNum);
		data3.add(uvEReaderContactNum);
		MultiType.read(xlsx, data3, xlsx.getRowByKey("�����Ķ��������Ķ����Ķ���") + 2);
		
		
		// ��ȡ���ص����Ķ����Ӵ��ʵ�����
//		data.clear();
//		data.add(areaEReaderContactRate);
//		MultiType.read(xlsx, data, xlsx.getRowByKey("��ͬ���ص����Ķ����Ķ��Ӵ���") + 3);
		// ��ȡ���硢�Ա�����صĵ����Ķ���ӵ���ʵ�����
//		data.clear();
//		data.add(uvEReaderOwningRate);
//		data.add(genderEReaderOwningRate);
//		data.add(areaEReaderOwningRate);
//		MultiType.read(xlsx, data, xlsx.getRowByKey("��ͬ���������Ķ����Ķ�ӵ����Ⱥ��������") + 3);
	}

	@Override
	protected void replace()
	{
		// �����滻
		tr("${local_ereader_contact_rate_value}", pf(localEReaderContactRate));
		tr("${country_ereader_contact_rate_value}", pf(countryEReaderContactRate));
		tr("${local_minus_country_ereader_contact_rate_value}",
				of(Math.abs(localEReaderContactRate-countryEReaderContactRate)));
		tr("${local_minus_country_ereader_contact_rate_key}",
				localEReaderContactRate > countryEReaderContactRate ? "��" : "��");

		//		tr("${average_ereader_owning_rate_value}", pf(eReaderOwningRate));
		// �滻������Ա�ĵ����Ķ����Ӵ�����صı���
		tr("${male_ereader_contact_rate_value}",
				pf(genderEReaderContactRate.get(0).value));
		tr("${female_ereader_contact_rate_value}",
				pf(genderEReaderContactRate.get(1).value));
		tr("${female_minus_male_ereader_contact_rate_key}",
				genderEReaderContactRate.get(0).value > genderEReaderContactRate.get(1).value ? "��" : "��");
		tr("${female_minus_male_ereader_contact_rate_value}",
				of(Math.abs(genderEReaderContactRate.get(1).value - genderEReaderContactRate.get(0).value)));
		
		tr("${urban_ereader_contact_rate_value}",
				pf(uvEReaderContactRate.get(0).value));
		tr("${village_ereader_contact_rate_value}",
				pf(uvEReaderContactRate.get(1).value));
		tr("${urban_minus_village_ereader_contact_rate_key}",
				uvEReaderContactRate.get(0).value > uvEReaderContactRate.get(1).value ? "��" : "��");
		tr("${urban_minus_village_ereader_contact_rate_value}",
				of(Math.abs(uvEReaderContactRate.get(0).value - uvEReaderContactRate.get(1).value)));
		
		// �滻��������Ķ����Ӵ�����صı���
		BaseRow.sort(ageEReaderContactRate);
		tr("${best_age_ereader_contact_rate_key}",
				ageEReaderContactRate.get(0).key);
		tr("${best_age_ereader_contact_rate_value}",
				pf(ageEReaderContactRate.get(0).value));
		tr("${second_age_ereader_contact_rate_key}",
				ageEReaderContactRate.get(1).key);
		tr("${second_age_ereader_contact_rate_value}",
				pf(ageEReaderContactRate.get(1).value));
		tr("${third_age_ereader_contact_rate_key}",
				ageEReaderContactRate.get(2).key);
		tr("${third_age_ereader_contact_rate_value}",
				pf(ageEReaderContactRate.get(2).value));
		tr("${fourth_age_ereader_contact_rate_key}",
				ageEReaderContactRate.get(3).key);
		tr("${fourth_age_ereader_contact_rate_value}",
				pf(ageEReaderContactRate.get(3).value));
		tr("${fiveth_age_ereader_contact_rate_key}",
				ageEReaderContactRate.get(4).key);
		tr("${fiveth_age_ereader_contact_rate_value}",
				pf(ageEReaderContactRate.get(4).value));
		
		tr("${local_ereader_contact_rate_num}", tf(localEReaderContactNum));
		tr("${male_ereader_contact_rate_num}", tf(genderEReaderContactNum.get(0).value));
		tr("${female_ereader_contact_rate_num}", tf(genderEReaderContactNum.get(1).value));
		tr("${male_minus_female_ereader_contact_num_key}", 
				genderEReaderContactNum.get(0).value > genderEReaderContactNum.get(1).value ? "��" : "��");
		tr("${male_minus_female_ereader_contact_num_value}",
				tf(Math.abs(genderEReaderContactNum.get(0).value - genderEReaderContactNum.get(1).value)));
		
		tr("${urban_ereader_contact_rate_num}", tf(uvEReaderContactNum.get(0).value));
		tr("${village_ereader_contact_rate_num}", tf(uvEReaderContactNum.get(1).value));
		tr("${urban_minus_village_ereader_contact_num_key}", 
				uvEReaderContactNum.get(0).value > uvEReaderContactNum.get(1).value ? "��" : "��");
		tr("${urban_minus_village_ereader_contact_num_value}",
				tf(Math.abs(uvEReaderContactNum.get(0).value - uvEReaderContactNum.get(1).value)));
		
		// �滻���ص����Ķ����Ӵ�����صı���
//		tr("${best_area_ereader_contact_rate_key}",
//				areaEReaderContactRate.get(0).key);
//		tr("${best_area_ereader_contact_rate_value}",
//				pf(areaEReaderContactRate.get(0).value));
		// �滻���硢�Ա�����ص����Ķ���ӵ������صı���
//		BaseRow.sort(areaEReaderOwningRate);
//		tr("${best_area_ereader_owning_rate_key}",
//				areaEReaderOwningRate.get(0).key);
//		tr("${best_area_ereader_owning_rate_value}",
//				pf(areaEReaderOwningRate.get(0).value));
	}

	@Override
	protected void chart()
	{
		// ���ɳ�����Ա�����Ķ����Ӵ����ۺϱ��
//		data.clear();
//		data.add(uvEReaderContactRate);
//		data.add(genderEReaderContactRate);
		MultiType.table(docx, data, "${ereader_contact_rate_key_%d}",
				"${ereader_contact_rate_value_%d}",
//				"Resource/adult/chapter04/ereader_contact_rate_row.xml");
				"Resource/adult/chapter01/media_contact_type_area_row.xml");
		BaseRow.chart(ageEReaderContactRate);// chart28
		
//		data.clear();
//		data.add(uvEReaderContactNum);
//		data.add(genderEReaderContactNum);
		MultiType.table(docx, data3, "${ereader_contact_num_key_%d}",
				"${ereader_contact_num_value_%d}",
//				"Resource/adult/chapter04/ereader_contact_num_row.xml");
				"Resource/adult/chapter01/media_contact_type_area_row.xml",GF.t);
		// ������������Ķ����Ӵ�����״ͼ
		
		// �������ص����Ķ����Ӵ�����״ͼ
//		BaseRow.sort(areaEReaderContactRate);// chart29
//		BaseRow.chart(areaEReaderContactRate);
		// ���ɳ��硢�Ա�����ص����Ķ���ӵ�����ۺϱ��
//		data.clear();
//		data.add(uvEReaderOwningRate);
//		data.add(genderEReaderOwningRate);
//		data.add(areaEReaderOwningRate);
//		MultiType.table(docx, data, "${ereader_owning_rate_key_%d}",
//				"${ereader_owning_rate_value_%d}",
//				"Resource/adult/chapter04/ereader_contact_rate_row.xml");
	}
	@Override
	public void process()
	{
		readData();
		replace();
		chart();
	}

}
