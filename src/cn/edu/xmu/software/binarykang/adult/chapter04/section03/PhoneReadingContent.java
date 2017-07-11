package cn.edu.xmu.software.binarykang.adult.chapter04.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>4.3.5�ֻ��Ķ�����
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class PhoneReadingContent extends AdultBaseAction
{
	private List<BaseRow> phoneReadingContent;
	private List<BaseRow> novelReadingData;
	private List<BaseRow> phoneServicesData;

	public PhoneReadingContent(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		phoneReadingContent = ListFactory.getBaseRows();
		novelReadingData = ListFactory.getBaseRows();
		phoneServicesData = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, phoneReadingContent, "�ֻ��Ķ��Ӵ��ߵ��ֻ��Ķ�����");
		BaseRow.read(xlsx, novelReadingData, "�ֻ��Ķ��Ӵ����Ķ���С˵���ͣ�������û��ͨ���ֻ�����С˵��ѡ�");
		novelReadingData.remove(novelReadingData.size()-1);// ���տͻ�����ɾ����һ������
		BaseRow.read(xlsx, phoneServicesData, "ͨ���ֻ�΢�Ž��й��Ļ");
	}

	@Override
	protected void replace()
	{
		// �滻�ֻ��Ķ�������صı���
		BaseRow.sort(phoneReadingContent);
		tr("${best_phone_reading_content_key}", phoneReadingContent.get(0).key);
		tr("${best_phone_reading_content_value}",pf(phoneReadingContent.get(0).value));
		
		tr("${second_phone_reading_content_key}",phoneReadingContent.get(1).key);
		tr("${second_phone_reading_content_value}",pf(phoneReadingContent.get(1).value));
		
		tr("${third_phone_reading_content_key}", phoneReadingContent.get(2).key);
		tr("${third_phone_reading_content_value}",pf(phoneReadingContent.get(2).value));
		
		tr("${fourth_phone_reading_content_key}",phoneReadingContent.get(3).key);
		tr("${fourth_phone_reading_content_value}",pf(phoneReadingContent.get(3).value));
		
		tr("${fiveth_phone_reading_content_key}",phoneReadingContent.get(4).key);
		tr("${fiveth_phone_reading_content_value}",pf(phoneReadingContent.get(4).value));
		
		tr("${sixth_phone_reading_content_key}", phoneReadingContent.get(5).key);
		tr("${sixth_phone_reading_content_value}",pf(phoneReadingContent.get(5).value));
		
		tr("${seventh_phone_reading_content_key}", phoneReadingContent.get(6).key);
		tr("${seventh_phone_reading_content_value}",pf(phoneReadingContent.get(6).value));
		
		tr("${eighth_phone_reading_content_key}", phoneReadingContent.get(7).key);
		tr("${eighth_phone_reading_content_value}",pf(phoneReadingContent.get(7).value));
		
		tr("${nineth_phone_reading_content_key}", phoneReadingContent.get(8).key);
		tr("${nineth_phone_reading_content_value}",pf(phoneReadingContent.get(8).value));
		
		
		// �滻�ֻ��Ķ�С˵��صı���
		BaseRow.sort(novelReadingData);
		tr("${phone_reading_novel_rate}",
				pf(BaseRow.getRowByKey("���ֻ�С˵", phoneReadingContent).value));
		tr("${best_phone_novel_reading_type_key}", novelReadingData.get(0).key);
		tr("${best_phone_novel_reading_type_value}",
				pf(novelReadingData.get(0).value));
		tr("${second_phone_novel_reading_type_key}",
				novelReadingData.get(1).key);
		tr("${second_phone_novel_reading_type_value}",
				pf(novelReadingData.get(1).value));
		tr("${third_phone_novel_reading_type_key}", novelReadingData.get(2).key);
		tr("${third_phone_novel_reading_type_value}",
				pf(novelReadingData.get(2).value));
		// �滻�ֻ����Ʒ�����صı���
		tr("${has_used_phone_services_rate}",
				pf(1 - phoneServicesData.get(phoneServicesData.size() - 1).value));
		tr("${best_phone_services_key}", phoneServicesData.get(0).key);
		tr("${best_phone_services_value}", pf(phoneServicesData.get(0).value));
		tr("${second_phone_services_key}", phoneServicesData.get(1).key);
		tr("${second_phone_services_value}", pf(phoneServicesData.get(1).value));
		tr("${third_phone_services_key}", phoneServicesData.get(2).key);
		tr("${third_phone_services_value}", pf(phoneServicesData.get(2).value));
		tr("${fourth_phone_services_key}", phoneServicesData.get(3).key);
		tr("${fourth_phone_services_value}", pf(phoneServicesData.get(3).value));
	}

	@Override
	protected void chart()
	{
		// �����ֻ��Ķ����ݱ����ı��
		BaseRow.sortExceptLast(phoneReadingContent);
		BaseRow.table(docx, phoneReadingContent,
//				"Resource/adult/chapter04/phone_reading_content_odd_row.xml",
//				"Resource/adult/chapter04/phone_reading_content_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
		// ����С˵���ͱ����ı��
		BaseRow.sortExceptLast(novelReadingData);
		BaseRow.table(
				docx,
				novelReadingData,
//				"Resource/adult/chapter04/phone_reading_novel_type_odd_row.xml",
//				"Resource/adult/chapter04/phone_reading_novel_type_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
		// �����ֻ����Ʒ���ı��
		BaseRow.sortExceptLast(phoneServicesData);
		BaseRow.table(docx, phoneServicesData,
//				"Resource/adult/chapter04/phone_services_odd_row.xml",
//				"Resource/adult/chapter04/phone_services_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
	}

}
