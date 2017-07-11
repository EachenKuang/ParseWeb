package cn.edu.xmu.software.binarykang.adult.chapter04.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.3.5手机阅读内容
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
		BaseRow.read(xlsx, phoneReadingContent, "手机阅读接触者的手机阅读内容");
		BaseRow.read(xlsx, novelReadingData, "手机阅读接触者阅读的小说类型（不含“没有通过手机读过小说”选项）");
		novelReadingData.remove(novelReadingData.size()-1);// 按照客户需求删除第一个数据
		BaseRow.read(xlsx, phoneServicesData, "通过手机微信进行过的活动");
	}

	@Override
	protected void replace()
	{
		// 替换手机阅读内容相关的变量
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
		
		
		// 替换手机阅读小说相关的变量
		BaseRow.sort(novelReadingData);
		tr("${phone_reading_novel_rate}",
				pf(BaseRow.getRowByKey("看手机小说", phoneReadingContent).value));
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
		// 替换手机定制服务相关的变量
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
		// 生成手机阅读内容比例的表格
		BaseRow.sortExceptLast(phoneReadingContent);
		BaseRow.table(docx, phoneReadingContent,
//				"Resource/adult/chapter04/phone_reading_content_odd_row.xml",
//				"Resource/adult/chapter04/phone_reading_content_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
		// 生成小说类型比例的表格
		BaseRow.sortExceptLast(novelReadingData);
		BaseRow.table(
				docx,
				novelReadingData,
//				"Resource/adult/chapter04/phone_reading_novel_type_odd_row.xml",
//				"Resource/adult/chapter04/phone_reading_novel_type_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
		// 生成手机定制服务的表格
		BaseRow.sortExceptLast(phoneServicesData);
		BaseRow.table(docx, phoneServicesData,
//				"Resource/adult/chapter04/phone_services_odd_row.xml",
//				"Resource/adult/chapter04/phone_services_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
	}

}
