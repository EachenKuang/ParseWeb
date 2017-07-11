package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>2.3.8 ${city}居民对图书价格的评价
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookPriceComment extends AdultBaseAction
{
	private List<BaseRow> data;

	public BookPriceComment(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "对当前图书价格的评价");
	}

	@Override
	protected void replace()
	{
		tr("${expensive_book_price_comment}",
				pf(data.get(3).value + data.get(4).value));
		tr("${cheap_book_price_comment}", pf(data.get(0).value
				+ data.get(1).value));
		tr("${suitable_book_price_comment}", pf(data.get(2).value));
		tr("${hard_to_explian_book_price_comment}",
				pf(data.get(data.size() - 1).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart10
	}

}
