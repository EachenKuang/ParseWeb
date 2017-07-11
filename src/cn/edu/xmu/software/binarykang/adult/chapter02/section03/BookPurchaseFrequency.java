package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>2.3.2购买图书的频率
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookPurchaseFrequency extends AdultBaseAction
{
	private double averageBookPurchaseFrequency;
	private double countryBookPurchaseFrequency;
	private List<BaseRow> data;

	public BookPurchaseFrequency(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		averageBookPurchaseFrequency = SingleValue.read(xlsx,
				"2.3.2购买图书的频率第一行文字中的人均购书频率", 1, 1);
		countryBookPurchaseFrequency = SingleValue.read(xlsx, 
				"2.3.2购买图书的频率第一行文字中的人均购书频率——全国", 1, 1);
		BaseRow.read(xlsx, data, "图书购买频率");
	}

	@Override
	protected void replace()
	{
		tr("${average_book_purchase_frequency}",
				tf(averageBookPurchaseFrequency));
		tr("${country_book_purchase_frequency", tf(countryBookPurchaseFrequency));
		tr("${average_minus_country_book_purchase_frequency_key}", 
				averageBookPurchaseFrequency>countryBookPurchaseFrequency?"高":"低");
		tr("${average_minus_country_book_purchase_frequency_value}",
				tf(Math.abs(averageBookPurchaseFrequency-countryBookPurchaseFrequency)));
		tr("${half_year_has_purchase_book}", pf(data.get(0).value+data.get(1).value+data.get(2).value+data.get(3).value+data.get(4).value+data.get(5).value));
		tr("${has_purchase_book}", pf(1 - data.get(data.size() - 1).value));
		tr("${once_a_year_book_purchase_frequency}",
				pf(1 - data.get(data.size() - 1).value
						- data.get(data.size() - 2).value));
		tr("${once_two_year_book_purchase_frequency}",
				pf(data.get(data.size() - 2).value));
		tr("${never_purchase_book}", pf(data.get(data.size() - 1).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.table(docx, data,
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
	}

}
