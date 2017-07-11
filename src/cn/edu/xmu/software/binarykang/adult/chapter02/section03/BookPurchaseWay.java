package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>2.3.4.1 ${city}居民购书渠道
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookPurchaseWay extends AdultBaseAction
{
	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	public BookPurchaseWay(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "居民购书渠道");
		DoubleValueRow.read(xlsx, uvData, "城乡居民购书渠道");
	}

	@Override
	protected void replace()
	{
//		BaseRow.sortExceptLast(data);
		BaseRow online = BaseRow.getRowByKey("网上购书", data);
		data.remove(online);
		BaseRow other = BaseRow.getRowByKey("其他", data);
		data.remove(other);
		BaseRow.sort(data);
		
		
		tr("${best_book_purchase_way_key}", data.get(0).key);
		tr("${best_book_purchase_way_value}", pf(data.get(0).value));
		tr("${second_book_purchase_way_key}", data.get(1).key);
		tr("${second_book_purchase_way_value}", pf(data.get(1).value));
		tr("${third_book_purchase_way_key}", data.get(2).key);
		tr("${third_book_purchase_way_value}", pf(data.get(2).value));
		tr("${fourth_book_purchase_way_key}", data.get(3).key);
		tr("${fourth_book_purchase_way_value}", pf(data.get(3).value));
		tr("${fifth_book_purchase_way_key}", data.get(4).key);
		tr("${fifth_book_purchase_way_value}", pf(data.get(4).value));
		
		tr("${online_book_purchase_way_value}",
				pf(online.value));
		data.add(online);
		data.add(other);
		// 替换城乡购买渠道相关的变量
		DoubleValueRow.sortByV1MinusV2(uvData);
		tr("${best_urban_minus_village_book_purchase_way}", uvData.get(0).key);
		tr("${second_urban_minus_village_book_purchase_way}", uvData.get(1).key);
		tr("${third_urban_minus_village_book_purchase_way}", uvData.get(2).key);
		tr("${best_village_minus_urban_book_purchase_way}",
				uvData.get(uvData.size() - 1).key);
		tr("${second_village_minus_urban_book_purchase_way}",
				uvData.get(uvData.size() - 2).key);
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		BaseRow.table(docx, data,
				 "Resource/singleValueStandard.xml",
				 "Resource/singleValueStandard.xml");
//		DoubleValueRow other = uvData.get(uvData.size()-1);
//		uvData.remove(uvData.size()-1);
		DoubleValueRow.sortExceptLastByV1(uvData);
		
		DoubleValueRow.table(docx, uvData,
				"Resource/doubleValueStandard.xml",
				"Resource/doubleValueStandard.xml");
	}
//	@Override
//	
//	public void process()
//	{
//		readData();	
//		replace();	
//		chart();
//	};

}
