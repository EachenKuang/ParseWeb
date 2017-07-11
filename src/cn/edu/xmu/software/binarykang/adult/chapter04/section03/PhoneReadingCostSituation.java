package cn.edu.xmu.software.binarykang.adult.chapter04.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.3.4为手机读物付费情况
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class PhoneReadingCostSituation extends AdultBaseAction
{
	private List<BaseRow> data;

	public PhoneReadingCostSituation(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "为手机读物付费情况");
	}

	@Override
	protected void replace()
	{
		BaseRow.sort(data);
		tr("${best_phone_reading_cost_situation_key}", data.get(0).key);
		tr("${best_phone_reading_cost_situation_value}", pf(data.get(0).value));
		tr("${second_phone_reading_cost_situation_key}", data.get(1).key);
		tr("${second_phone_reading_cost_situation_value}",
				pf(data.get(1).value));
		tr("${third_phone_reading_cost_situation_key}", data.get(2).key);
		tr("${third_phone_reading_cost_situation_value}", pf(data.get(2).value));
		tr("${fourth_phone_reading_cost_situation_key}", data.get(3).key);
		tr("${fourth_phone_reading_cost_situation_value}",
				pf(data.get(3).value));
		tr("${fiveth_phone_reading_cost_situation_key}", data.get(4).key);
		tr("${fiveth_phone_reading_cost_situation_value}",
				pf(data.get(4).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart25
	}

}
