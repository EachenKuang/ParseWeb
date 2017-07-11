package cn.edu.xmu.software.binarykang.adult.chapter06.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>6.2.1盗版出版物市场占有状况
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class PirateMarket extends AdultBaseAction
{
	private List<BaseRow> data;

	public PirateMarket(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "盗版出版物消费情况");
	}

	@Override
	protected void replace()
	{
		tr("${total_pirate_purchase_rate}", pf(data.get(0).value
				+ data.get(1).value));
		tr("${legalcopy_pirate_all_purchase_rate}", pf(data.get(0).value));
		tr("${all_pirate_purchase_rate}", pf(data.get(1).value));
		tr("${all_legalcopy_purchase_rate}", pf(data.get(3).value));
		tr("${can_not_tell_legalcopy_or_pirate_rate}", pf(data.get(2).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart40
	}

}
