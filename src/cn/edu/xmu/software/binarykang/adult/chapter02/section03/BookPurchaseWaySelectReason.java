package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>2.3.4.2购书渠道选择原因
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookPurchaseWaySelectReason extends AdultBaseAction
{
	private List<BaseRow> data;

	public BookPurchaseWaySelectReason(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "购书渠道的选择");
	}

	@Override
	protected void replace()
	{
		tr("${distance_book_purchase_way_select_reason_value}", 
				pf(BaseRow.getRowByKey("离得较近", data).value));
		tr("${price_book_purchase_way_select_reason_value}", 
				pf(BaseRow.getRowByKey("价格折扣", data).value));
		tr("${environment_book_purchase_way_select_reason_value}",
				pf(BaseRow.getRowByKey("店内环境好、设施齐全", data).value));
		tr("${sevice_book_purchase_way_select_reason_value}", 
				pf(BaseRow.getRowByKey("店主人或服务员态度亲切、服务好", data).value));
		//BaseRow.sort(data);
		//tr("${best_book_purchase_way_select_reason_key}", data.get(0).key);
		tr("${best_book_purchase_way_select_reason_value}",
				pf(BaseRow.getRowByKey("图书种类多", data).value));
		//tr("${second_book_purchase_way_select_reason_key}", data.get(1).key);
		tr("${second_book_purchase_way_select_reason_value}",
				pf(BaseRow.getRowByKey("图书信息丰富", data).value));
		//tr("${third_book_purchase_way_select_reason_key}", data.get(2).key);
		tr("${third_book_purchase_way_select_reason_value}",
				pf(BaseRow.getRowByKey("很容易找到需要的书", data).value));
		tr("${fourth_book_purchase_way_select_reason_key}", data.get(3).key);
		tr("${fourth_book_purchase_way_select_reason_value}",
				pf(data.get(3).value));
		
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		Collections.reverse(data);
		BaseRow.chart(data);// chart7
		Collections.reverse(data);
		BaseRow.sort(data);
	}

}
