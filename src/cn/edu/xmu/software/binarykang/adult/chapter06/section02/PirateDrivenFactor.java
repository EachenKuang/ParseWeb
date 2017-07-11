package cn.edu.xmu.software.binarykang.adult.chapter06.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>6.2.2.2购买盗版出版物的驱动因素
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class PirateDrivenFactor extends AdultBaseAction
{
	private List<BaseRow> data;

	public PirateDrivenFactor(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "购买盗版出版物的原因");
	}

	@Override
	protected void replace()
	{
//		tr("${pirate_book_cheap_reason_rate}", pf(data.get(0).value));
//		tr("${many_type_to_select_reason_rate}", pf(data.get(1).value));
//		tr("${purchase_convenient_reason_rate}", pf(data.get(3).value));
//		tr("${dont_know_which_is_pirate_reason_rate}",
//				pf(data.get(data.size() - 1).value));
//		
//		BaseRow.sort(data);
		tr("${best_pirate_purchase_reason_key}", data.get(0).key);
		tr("${best_pirate_purchase_reason_rate}", pf(data.get(0).value));
		tr("${second_pirate_purchase_reason_key}", data.get(1).key);
		tr("${second_pirate_purchase_reason_rate}", pf(data.get(1).value));
		tr("${third_pirate_purchase_reason_key}", data.get(2).key);
		tr("${third_pirate_purchase_reason_rate}", pf(data.get(2).value));
		tr("${forth_pirate_purchase_reason_key}", data.get(3).key);
		tr("${forth_pirate_purchase_reason_rate}", pf(data.get(3).value));
		tr("${fifth_pirate_purchase_reason_key}", data.get(4).key);
		tr("${fifth_pirate_purchase_reason_rate}", pf(data.get(4).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		BaseRow.chart(data);// chart42
	}

}
