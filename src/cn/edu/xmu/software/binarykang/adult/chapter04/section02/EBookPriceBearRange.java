package cn.edu.xmu.software.binarykang.adult.chapter04.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.2.3电子书价格承受能力
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 * 
 */
public final class EBookPriceBearRange extends AdultBaseAction
{
	private double averageLocalEBookPrice;
	private double averageCountryEBookPrice;
	private List<BaseRow> localData;
	private List<BaseRow> countryData;

	public EBookPriceBearRange(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		localData = ListFactory.getBaseRows();
		countryData = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		averageLocalEBookPrice = SingleValue.read(xlsx, "4.2.3电子书价格承受能力 第一段文字中的可接受一本电子书的平均价格", 1, 2);
		averageCountryEBookPrice = SingleValue.read(xlsx, "4.2.3电子书价格承受能力 第一段文字中的可接受一本电子书的平均价格（全国）", 1, 2);

		BaseRow.read(xlsx, localData, "单本电子书价格承受能力");
		BaseRow.read(xlsx, countryData, "4.2.3电子书价格承受能力第一段 能够接受付费下载阅读的比例（全国）");
	}

	@Override
	protected void replace()
	{
		tr("${average_ebook_price_bear_range_value}", tf(averageLocalEBookPrice));
		tr("${country_average_ebook_price_bear_range_value}", tf(averageCountryEBookPrice));
		tr("${local_minus_country_average_ebook_price_bear_range_key}",
				averageLocalEBookPrice > averageCountryEBookPrice ? "高" : "低");
		tr("${local_minus_country_average_ebook_price_bear_range_value}",
				tf(Math.abs(averageLocalEBookPrice - averageCountryEBookPrice)));
		
		tr("${can_accept_pay_ebook_rate}", pf(1 - localData.get(localData.size() - 1).value));
		tr("${country_can_accept_pay_ebook_rate}",pf(1 - countryData.get(localData.size() - 1).value));
		tr("${local_minus_country_can_accept_pay_ebook_rate_key}",
				countryData.get(countryData.size() - 1).value > localData.get(localData.size() - 1).value ? "高" : "低");
		tr("${local_minus_country_can_accept_pay_ebook_rate_value}",
				of(Math.abs(countryData.get(countryData.size() - 1).value - localData.get(localData.size() - 1).value)));
	}

	@Override
	protected void chart()
	{
		BaseRow.table(docx, localData, 
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
	}

}
