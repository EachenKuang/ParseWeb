package cn.edu.xmu.software.binarykang.adult.chapter03.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>3.2.3读者对期刊广告的态度
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class AttitudeToMagazineAD extends AdultBaseAction
{
	private List<BaseRow> data;

	public AttitudeToMagazineAD(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "期刊读者可接受的广告面积");
	}

	@Override
	protected void replace()
	{
		tr("${less_than_20_percent_magazine_advertisement_area}",
				pf(data.get(4).value + data.get(5).value + data.get(6).value
						+ data.get(7).value));
		tr("${no_magazine_advertisement_area}",
				pf(data.get(data.size() - 1).value));
		tr("${over_half_magazine_advertisement_arae}", pf(data.get(0).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart19
	}

}
