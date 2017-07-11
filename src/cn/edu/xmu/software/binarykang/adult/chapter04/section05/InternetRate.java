package cn.edu.xmu.software.binarykang.adult.chapter04.section05;

import java.util.Comparator;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>4.5.1������
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class InternetRate extends AdultBaseAction
{
	private List<BaseRow> localData;
	private List<BaseRow> countryData;
	private double localInternetRate;
	private double countryInternetRate;
	private List<DoubleValueRow> uvData;
	private DoubleValueRow lastRow;

	public InternetRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		localData = ListFactory.getBaseRows();
		countryData = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, localData, "����������");
		localInternetRate = 1 - localData.get(localData.size() - 1).value;
		localData.remove(localData.size() - 1);

		BaseRow.read(xlsx, countryData, "4.5.1������ ��һ�������е�ȫ��������");
		countryInternetRate = 1 - countryData.get(countryData.size() - 1).value;

		DoubleValueRow.read(xlsx, uvData, "��������;���ĳ���Ա�");
		lastRow = uvData.get(uvData.size() - 1);
		// ���µ���һ������
		lastRow.key = "�ۺ�������";
		lastRow.v1 = 1 - lastRow.v1;
		lastRow.v2 = 1 - lastRow.v2;
	}

	@Override
	protected void replace()
	{
		// �滻������������صı���
		BaseRow.sortExceptLast(localData);
		tr("${local_internet_contact_rate_value}", pf(localInternetRate));
		tr("${country_internet_contact_rate_value}", pf(countryInternetRate));
		tr("${local_minus_country_internet_contact_rate_key}",
				localInternetRate > countryInternetRate ? "��" : "��");
		tr("${local_minus_country_internet_contact_rate_value}",
				of(Math.abs(localInternetRate - countryInternetRate)));
		tr("${best_internet_contact_rate_key}", localData.get(0).key);
		tr("${best_internet_contact_rate_value}", pf(localData.get(0).value));
		tr("${second_internet_contact_rate_key}", localData.get(1).key);
		tr("${second_internet_contact_rate_value}", pf(localData.get(1).value));
		// �滻������������ʶԱ���صı���
		tr("${urban_internet_contact_rate_value}",
				pf(uvData.get(uvData.size() - 1).v1));
		tr("${village_internet_contact_rate_value}",
				pf(uvData.get(uvData.size() - 1).v2));
		tr("${urban_minus_village_internet_contact_rate_key}",
				uvData.get(uvData.size() - 1).v1 > uvData.get(uvData.size() - 1).v2 ? "��"
						: "��");
		tr("${urban_minus_village_internet_contact_rate_value}",
				of(Math.abs(uvData.get(uvData.size() - 1).v1
						- uvData.get(uvData.size() - 1).v2)));
		
		tr("${urban_computer_internet_contact_rate_value}",
				pf(uvData.get(0).v1));
		tr("${village_computer_internet_contact_rate_value}",
				pf(uvData.get(0).v2));
		tr("${urban_minus_village_computer_internet_contact_rate_key}",
				uvData.get(0).v1 > uvData.get(0).v2 ? "��" : "��");
		tr("${urban_minus_village_computer_internet_contact_rate_value}",
				of(Math.abs(uvData.get(0).v1 - uvData.get(0).v2)));
		
		tr("${village_phone_internet_contact_rate_value}", pf(uvData.get(1).v2));
		tr("${urban_phone_internet_contact_rate_value}", pf(uvData.get(1).v1));
		tr("${urban_minus_village_phone_internet_contact_rate_key}",
				uvData.get(1).v1 > uvData.get(1).v2 ? "��" : "��");
		tr("${urban_minus_village_phone_internet_contact_rate_value}",
				of(Math.abs(uvData.get(1).v2 - uvData.get(1).v1)));

	}

	@Override
	protected void chart()
	{
		BaseRow.chart(localData);// chart31
		
		uvData.remove(uvData.size()-1);
		uvData.add(lastRow);
//		uvData.sort(new Comparator<DoubleValueRow>()
//		{
//
//			@Override
//			public int compare(DoubleValueRow o1, DoubleValueRow o2)
//			{
//				if (o1.key.equals("����"))
//					return 1;
//				if (o2.key.equals("����"))
//					return -1;
//				if (o1.key.equals("�ۺ�������"))
//					return -1;
//				if (o2.key.equals("�ۺ�������"))
//					return 1;
//				if (o1.v1 < o2.v1)
//					return 1;
//				else if (o1.v1 > o2.v1)
//					return -1;
//				return 0;
//			}
//		});

		DoubleValueRow.chart(uvData, "���о���", "ũ�����");// chart32
	}

	@Override
	public void process()
	{
		readData();
		replace();
		chart();
	}

}
