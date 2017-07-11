package cn.edu.xmu.software.binarykang.adult.chapter03.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>3.2.2.3 ${city}居民期刊价格评价
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class MagazinePriceComment extends AdultBaseAction
{
	private List<BaseRow> data;

	public MagazinePriceComment(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "居民对期刊价格评价");
	}

	@Override
	protected void replace()
	{
		tr("${suit_magazine_price_comment_value}", pf(data.get(2).value));
		tr("${expensive_magazine_price_comment_value}", pf(data.get(3).value+data.get(4).value));
		tr("${donot_know_magazine_price_comment_value}",
				pf(data.get(data.size() - 1).value));
		tr("${cheap_magazine_price_comment_value}",
				pf(data.get(0).value + data.get(1).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart18
	}

}
