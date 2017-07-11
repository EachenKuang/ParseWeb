package cn.edu.xmu.software.binarykang.adult.chapter02.section05;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>2.5.2����г���͸������
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookStoreMarketPenetrationRate extends AdultBaseAction
{
	private List<BaseRow> data;

	public BookStoreMarketPenetrationRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "�����г���͸������");
	}

	@Override
	protected void replace()
	{
		tr("${best_bookstore_market_penetration_rate_key}", data.get(0).key);
		tr("${best_bookstore_market_penetration_rate_value}",
				pf(data.get(0).value));
		tr("${second_bookstore_market_penetration_rate_key}", data.get(1).key);
		tr("${second_bookstore_market_penetration_rate_value}",
				pf(data.get(1).value));
		tr("${third_bookstore_market_penetration_rate_key}", data.get(2).key);
		tr("${third_bookstore_market_penetration_rate_value}",
				pf(data.get(2).value));
		tr("${fourth_bookstore_market_penetration_rate_key}", data.get(3).key);
		tr("${fourth_bookstore_market_penetration_rate_value}",
				pf(data.get(3).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sort(data);
		BaseRow.tableWithIndex(
				docx,
				data,
				"Resource/adult/chapter02/bookstore_popularity_even_row.xml",
				"Resource/adult/chapter02/bookstore_popularity_even_row.xml");
				//"Resource/adult/chapter02/bookstore_market_penetration_rate_odd_row.xml",
				//"Resource/adult/chapter02/bookstore_market_penetration_rate_even_row.xml");
	}

}
