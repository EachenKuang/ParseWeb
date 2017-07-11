package cn.edu.xmu.software.binarykang.adult.chapter06.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>6.2.2.1购买盗版出版物类型
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class PiratePurchaseType extends AdultBaseAction
{
	private List<BaseRow> data;

	public PiratePurchaseType(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "消费者购买的盗版出版物类型");
	}

	@Override
	protected void replace()
	{
		BaseRow.sort(data);
		tr("${best_pirate_book_purchase_key}", data.get(0).key);
		tr("${best_pirate_book_purchase_rate}", pf(data.get(0).value));
		tr("${second_pirate_book_purchase_key}", data.get(1).key);
		tr("${second_pirate_book_purchase_rate}", pf(data.get(1).value));
		tr("${third_pirate_book_purchase_key}", data.get(2).key);
		tr("${third_pirate_book_purchase_rate}", pf(data.get(2).value));
		tr("${forth_pirate_book_purchase_key}", data.get(3).key);
		tr("${forth_pirate_book_purchase_rate}", pf(data.get(3).value));
		tr("${fifth_pirate_book_purchase_key}", data.get(4).key);
		tr("${fifth_pirate_book_purchase_rate}", pf(data.get(4).value));
		
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart41
	}

}
