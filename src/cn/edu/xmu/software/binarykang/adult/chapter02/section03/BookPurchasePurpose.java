package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>2.3.3购书者的购书目的
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookPurchasePurpose extends AdultBaseAction
{
	private List<BaseRow> data;

	public BookPurchasePurpose(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "购书者的购书目的");
	}

	@Override
	protected void replace()
	{
		BaseRow.sort(data);
		tr("${best_book_purchase_purpose_key}", data.get(0).key);
		tr("${best_book_purchase_purpose_value}", pf(data.get(0).value));
		tr("${second_book_purchase_purpose_key}", data.get(1).key);
		tr("${second_book_purchase_purpose_value}", pf(data.get(1).value));
		tr("${third_book_purchase_purpose_key}", data.get(2).key);
		tr("${third_book_purchase_purpose_value}", pf(data.get(2).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart6
	}

}
