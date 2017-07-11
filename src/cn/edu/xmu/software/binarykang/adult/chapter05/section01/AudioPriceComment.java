package cn.edu.xmu.software.binarykang.adult.chapter05.section01;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>5.2音像电子出版物价格评价
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class AudioPriceComment extends AdultBaseAction
{
	private List<BaseRow> data;

	public AudioPriceComment(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "音像电子出版物的价格评价");
	}

	@Override
	protected void replace()
	{
		tr("${cheap_or_suit_audio_price_comment_value}", pf(data.get(0).value
				+ data.get(1).value + data.get(2).value));
		tr("${expensive_audio_price_comment_value}", pf(data.get(3).value
				+ data.get(4).value));
		tr("${no_care_audio_price_comment_value}",
				pf(data.get(data.size() - 1).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart38
	}

}
