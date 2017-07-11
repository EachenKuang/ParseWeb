package cn.edu.xmu.software.binarykang.adult.chapter07.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>7.2.1ũ������ʹ����
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public final class VillageStudyUseRate extends AdultBaseAction
{
	private double localUseRate;
	private double countryUseRate;
	private double villageUseTime;
	private List<BaseRow> useRate;

	public VillageStudyUseRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		useRate = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		localUseRate = SingleValue.read(xlsx, "7.2.1ũ������ʹ���ʵ�һ�������е�ũ��������֪�ȣ����أ�",
				2, 2);
		countryUseRate = SingleValue.read(xlsx,
				"7.2.1ũ������ʹ���ʵ�һ�������е�ũ��������֪�ȣ�ȫ����", 2, 2);
		villageUseTime = SingleValue.read(xlsx, "ũ�����ݵ�ʹ��Ƶ�ʾ�ֵ",1,1);
		
		BaseRow.read(xlsx, useRate, "ũ�����ݵ�ʹ����");
//		BaseRow total = new BaseRow();
//		total.key = "�ܼ�";
//		total.value = 1.0;
//		useRate.add(total);
	}

	@Override
	protected void replace()
	{
		tr("${local_study_use_rate_value}", pf(localUseRate));
		tr("${country_study_use_rate_value}", pf(countryUseRate));
		tr("${village_use_time_value}", tf(villageUseTime));
		
//		tr("${local_minus_country_study_use_rate_key}",
//				localUseRate > countryUseRate ? "��" : "��");
		tr("${has_used_study_rate_value}",
				pf(1 - useRate.get(useRate.size() - 2).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.table(docx, useRate,
//				"Resource/adult/chapter07/village_study_use_rate_odd_row.xml",
//				"Resource/adult/chapter07/village_study_use_rate_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml",GF.p,false);		
	}

}
