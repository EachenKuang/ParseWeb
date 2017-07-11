package cn.edu.xmu.software.binarykang.adult.chapter02.section01;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析2.1.5 ${city}居民图书阅读来源
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookReadingSource extends AdultBaseAction
{
	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	public BookReadingSource(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "居民图书阅读来源");
		DoubleValueRow.read(xlsx, uvData, "图书阅读来源城乡比较");
	}

	@Override
	protected void replace()
	{
		tr("${book_bar_value}", pf(BaseRow.getRowByKey("在书店或书吧里看", data).value));
//		BaseRow.sort(data);
		// 替换居民图书阅读来源相关的数据
		tr("${best_book_reading_source_key}", data.get(0).key);
		tr("${best_book_reading_source_value}", pf(data.get(0).value));
		tr("${second_book_reading_source_key}", data.get(1).key);
		tr("${second_book_reading_source_value}", pf(data.get(1).value));
		tr("${third_book_reading_source_key}", data.get(2).key);
		tr("${third_book_reading_source_value}", pf(data.get(2).value));
		
		
		tr("${village_house_book_reading_source_value}", pf(uvData.get(6).v2));
		tr("${village_minus_urban_house_book_reading_source_key}", uvData.get(6).v2>uvData.get(6).v1?"高于":"低于");
		tr("${urban_house_book_reading_source_value}", pf(uvData.get(6).v1));
		// 城镇高于农村的读书来源
		DoubleValueRow.sortByV1MinusV2(uvData);
		tr("${best_urban_minus_village_book_reading_source}", uvData.get(0).key);
		tr("${second_urban_minus_village_book_reading_source}", uvData.get(1).key);
//		tr("${third_urban_minus_village_book_reading_source}", uvData.get(2).key);
		tr("${best_urban_minus_village_book_reading_source_urban_value}", pf(uvData.get(0).v1));
		tr("${second_urban_minus_village_book_reading_source_urban_value}", pf(uvData.get(1).v1));
//		tr("${third_urban_minus_village_book_reading_source_urban_value}", pf(uvData.get(2).v1));
		tr("${best_urban_minus_village_book_reading_source_village_value}", pf(uvData.get(0).v2));
		tr("${second_urban_minus_village_book_reading_source_village_value}", pf(uvData.get(1).v2));
//		tr("${third_urban_minus_village_book_reading_source_village_value}", pf(uvData.get(2).v2));
		
		// 农村高于城镇的读书来源
		DoubleValueRow.sortByV2MinusV1(uvData);
		tr("${best_village_minus_urban_book_reading_source}", uvData.get(0).key);
		tr("${best_village_minus_urban_book_reading_source_village_value}", pf(uvData.get(0).v2));
		tr("${best_village_minus_urban_book_reading_source_urban_value}", pf(uvData.get(0).v1));
		
		tr("${second_village_minus_urban_book_reading_source}",
				uvData.get(1).key);
	}

	@Override
	protected void chart()
	{
		// 生成读书来源的表格
		BaseRow.sortExceptLast(data);
		BaseRow.table(docx, data,
				"Resource/singleValueStandard.xml","Resource/singleValueStandard.xml");

		// 生成城镇和农村读书来源的表格
		DoubleValueRow.sortExceptLastByV1(uvData);
		DoubleValueRow.table(docx, uvData,
				"Resource/doubleValueStandard.xml");
	}

}
