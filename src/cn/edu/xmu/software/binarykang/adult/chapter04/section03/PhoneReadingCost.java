package cn.edu.xmu.software.binarykang.adult.chapter04.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.TripleValueRow;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.3.3手机阅读花费
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class PhoneReadingCost extends AdultBaseAction
{
	private List<TripleValueRow> phoneReadingCostData;
	private List<BaseRow> phoneReadingCostCompareData;
	private List<BaseRow> forGenTableData;// 为了更方便的生成表格而读取的临时数据

	public PhoneReadingCost(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		phoneReadingCostData = ListFactory.getTripleValueRows();
		phoneReadingCostCompareData = ListFactory.getBaseRows();
		forGenTableData = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		TripleValueRow.read(xlsx, phoneReadingCostData, "手机阅读接触者在手机阅读上的花费（1）");

		BaseRow.read(xlsx, forGenTableData,
				xlsx.getRowByKey("手机阅读接触者在手机阅读上的花费（1）") + 3, BEGIN_COL + 1);

		BaseRow.read(xlsx, phoneReadingCostCompareData,
				xlsx.getRowByKey("4.3.3手机阅读花费 倒数第三行文字中的人均手机阅读花费") + 1,
				BEGIN_COL);
	}

	@Override
	protected void replace()
	{
		TripleValueRow neverCost = phoneReadingCostData
				.get(phoneReadingCostData.size() - 1);
		tr("${average_never_cost_money_on_phone_reading}", pf(neverCost.v1));
		tr("${urban_never_cost_money_on_phone_reading}", pf(neverCost.v2));
		tr("${village_never_cost_money_on_phone_reading}", pf(neverCost.v3));
		tr("${urban_minus_village_never_cost_money_on_phone_reading}",
				of(Math.abs(neverCost.v2 - neverCost.v3)));
		tr("${less_than_20_phone_reading_cost}",
				pf(phoneReadingCostData.get(0).v1
						+ phoneReadingCostData.get(1).v1));
		tr("${30_to_100_phone_reading_cost}", pf(phoneReadingCostData.get(2).v1
				+ phoneReadingCostData.get(3).v1
				+ phoneReadingCostData.get(4).v1));
		tr("${over_100_phone_reading_cost}", pf(phoneReadingCostData.get(5).v1
				+ phoneReadingCostData.get(6).v1));

		tr("${average_phone_reading_cost_value}",
				tf(phoneReadingCostCompareData.get(2).value));
		tr("${urban_average_phone_reading_cost_value}",
				tf(phoneReadingCostCompareData.get(0).value));
		tr("${village_average_phone_reading_cost_value}",
				tf(phoneReadingCostCompareData.get(1).value));
		tr("${village_minus_urban_average_phone_reading_cost_value}",
				tf(Math.abs(phoneReadingCostCompareData.get(1).value-phoneReadingCostCompareData.get(0).value)));
	}

	@Override
	protected void chart()
	{
		BaseRow.table(docx, forGenTableData,
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml",
				GF.p, false);

	}

}
