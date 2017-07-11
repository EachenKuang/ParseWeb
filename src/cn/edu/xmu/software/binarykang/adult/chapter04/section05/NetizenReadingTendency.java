package cn.edu.xmu.software.binarykang.adult.chapter04.section05;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.5.8网民的阅读倾向
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class NetizenReadingTendency extends AdultBaseAction
{
	private List<BaseRow> data;

	public NetizenReadingTendency(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "居民倾向的阅读形式");
	}

	@Override
	protected void replace()
	{
		tr("${total_ereading_internet_tendency_value}", pf(data.get(0).value
				+ data.get(2).value + data.get(4).value));
		BaseRow.sort(data);
		tr("${best_internet_reading_tendency_key}", data.get(0).key);
		tr("${best_internet_reading_tendency_value}", pf(data.get(0).value));
		tr("${second_internet_reading_tendency_key}", data.get(1).key);
		tr("${second_internet_reading_tendency_value}", pf(data.get(1).value));
		tr("${third_internet_reading_tendency_key}", data.get(2).key);
		tr("${third_internet_reading_tendency_value}", pf(data.get(2).value));
		tr("${forth_internet_reading_tendency_key}", data.get(3).key);
		tr("${forth_internet_reading_tendency_value}", pf(data.get(3).value));
		tr("${worst_internet_reading_tendency_key}",
				data.get(data.size() - 1).key);
		tr("${worst_internet_reading_tendency_value}",
				pf(data.get(data.size() - 1).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart36
	}

}
