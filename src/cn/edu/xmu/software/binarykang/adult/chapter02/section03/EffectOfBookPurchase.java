package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>2.3.5影响购书的因素
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class EffectOfBookPurchase extends AdultBaseAction
{
	private List<BaseRow> data;

	public EffectOfBookPurchase(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "影响购书的因素");
	}

	@Override
	protected void replace()
	{
//		BaseRow.sort(data);
		tr("${best_effect_of_book_purchase_key}", data.get(0).key);
		tr("${best_effect_of_book_purchase_value}", pf(BaseRow.getRowByKey("图书内容简介", data).value));
		tr("${second_effect_of_book_purchase_key}", data.get(1).key);
		tr("${second_effect_of_book_purchase_value}", pf(BaseRow.getRowByKey("书名或目录", data).value));
		tr("${third_effect_of_book_purchase_key}", data.get(2).key);
		tr("${third_effect_of_book_purchase_value}", pf(BaseRow.getRowByKey("熟人推荐", data).value));
		tr("${fourth_effect_of_book_purchase_key}", data.get(3).key);
		tr("${fourth_effect_of_book_purchase_value}", pf(BaseRow.getRowByKey("店员推荐", data).value));
		tr("${fiveth_effect_of_book_purchase_key}", data.get(4).key);
		tr("${fiveth_effect_of_book_purchase_value}", pf(BaseRow.getRowByKey("价格", data).value));
		
		data.remove(5);
		data.remove(3);
		data.remove(2);
		data.remove(1);
		data.remove(0);
		
		
		
		
		
		BaseRow.sortExceptLast(data);
		tr("${sixth_effect_of_book_purchase_key}", data.get(0).key);
		tr("${sixth_effect_of_book_purchase_value}", pf(data.get(0).value));
		tr("${seventh_effect_of_book_purchase_key}", data.get(1).key);
		tr("${seventh_effect_of_book_purchase_value}", pf(data.get(1).value));
		tr("${eighth_effect_of_book_purchase_key}", data.get(2).key);
		tr("${eighth_effect_of_book_purchase_value}", pf(data.get(2).value));
		tr("${ninth_effect_of_book_purchase_key}", data.get(3).key);
		tr("${ninth_effect_of_book_purchase_value}", pf(data.get(3).value));
		tr("${last_2_effect_of_book_purchase_key}", data.get(data.size()-3).key);
		tr("${last_2_effect_of_book_purchase_value}", pf(data.get(data.size()-3).value));
		tr("${last_effect_of_book_purchase_key}", data.get(data.size()-2).key);
		tr("${last_effect_of_book_purchase_value}", pf(data.get(data.size()-2).value));
//		tr("${ad_effect_of_book_purchase_value}", 
//				pf(data.get(data.size()-3).value));
//		tr("${press_effect_of_book_purchase_value}",
//				pf(data.get(data.size()-2).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart8
	}

}
