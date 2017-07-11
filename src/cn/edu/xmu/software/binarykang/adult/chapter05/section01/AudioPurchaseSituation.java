package cn.edu.xmu.software.binarykang.adult.chapter05.section01;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>5.1音像电子出版物购买情况
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class AudioPurchaseSituation extends AdultBaseAction
{
	private List<BaseRow> data;

	public AudioPurchaseSituation(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "购买音像电子出版物的渠道");
	}

	@Override
	protected void replace()
	{
		tr("${total_audio_purchase_rate}",
				pf(1 - data.get(data.size() - 1).value));
		data.remove(data.size() - 1);
		data.remove(data.size() - 1);
		BaseRow.sort(data);
		tr("${best_audio_purchase_place_key}", data.get(0).key);
		tr("${best_audio_purchase_place_value}", pf(data.get(0).value));
		tr("${second_audio_purchase_place_key}", data.get(1).key);
		tr("${second_audio_purchase_place_value}", pf(data.get(1).value));
		tr("${third_audio_purchase_place_key}", data.get(2).key);
		tr("${third_audio_purchase_place_value}", pf(data.get(2).value));
		tr("${fourth_audio_purchase_place_key}", data.get(3).key);
		tr("${fourth_audio_purchase_place_value}", pf(data.get(3).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart37
	}

}
