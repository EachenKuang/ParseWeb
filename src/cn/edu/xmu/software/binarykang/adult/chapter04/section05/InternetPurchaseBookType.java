package cn.edu.xmu.software.binarykang.adult.chapter04.section05;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.5.6网上购买的出版物类型
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class InternetPurchaseBookType extends AdultBaseAction
{
	private List<BaseRow> data;

	public InternetPurchaseBookType(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "网民互联网购买的出版物类型比例");
	}

	@Override
	protected void replace()
	{
		tr("${has_buy_book_from_internet_rate_value}",
				pf(1 - data.get(data.size() - 1).value));
		tr("${best_internet_purchase_type_key}", data.get(0).key);
		tr("${best_internet_purchase_type_value}", pf(data.get(0).value));
		tr("${second_internet_purchase_type_key}", data.get(1).key);
		tr("${second_internet_purchase_type_value}", pf(data.get(1).value));
		tr("${third_internet_purchase_type_key}", data.get(2).key);
		tr("${third_internet_purchase_type_value}", pf(data.get(2).value));
		tr("${fourth_internet_purchase_type_key}", data.get(3).key);
		tr("${fourth_internet_purchase_type_value}", pf(data.get(3).value));
		tr("${worst_internet_purchase_type_key}", data.get(data.size() - 3).key);
		tr("${worst_internet_purchase_type_value}",
				pf(data.get(data.size() - 3).value));
	}

	@Override
	protected void chart()
	{
		BaseRow lastRow = new BaseRow();
		lastRow.key = data.get(data.size() - 1).key;
		lastRow.value = data.get(data.size() - 1).value;
		data.remove(data.size() - 1);

		BaseRow.sortExceptLast(data);
		data.add(lastRow);

		BaseRow.chart(data);// chart33
	}

}
