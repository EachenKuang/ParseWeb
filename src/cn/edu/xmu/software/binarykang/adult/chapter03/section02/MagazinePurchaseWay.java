package cn.edu.xmu.software.binarykang.adult.chapter03.section02;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>3.2.2.1期刊获取渠道
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class MagazinePurchaseWay extends AdultBaseAction
{
	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	public MagazinePurchaseWay(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "期刊获取渠道");
		DoubleValueRow.read(xlsx, uvData, "城乡期刊获取渠道");
	}

	@Override
	protected void replace()
	{
		// 替换期刊获取渠道相关的数据
		BaseRow.sortExceptLast(data);
		tr("${best_magazine_purchase_way_key}", data.get(0).key);
		tr("${best_magazine_purchase_way_value}", pf(data.get(0).value));
		tr("${second_magazine_purchase_way_key}", data.get(1).key);
		tr("${second_magazine_purchase_way_value}", pf(data.get(1).value));
		tr("${third_magazine_purchase_way_key}", data.get(2).key);
		tr("${third_magazine_purchase_way_value}", pf(data.get(2).value));
		tr("${fourth_magazine_purchase_way_key}", data.get(3).key);
		tr("${fourth_magazine_purchase_way_value}", pf(data.get(3).value));
		tr("${fiveth_magazine_purchase_way_key}", data.get(4).key);
		tr("${fiveth_magazine_purchase_way_value}", pf(data.get(4).value));
		tr("${sixth_magazine_purchase_way_key}", data.get(5).key);
		tr("${sixth_magazine_purchase_way_value}", pf(data.get(5).value));
		tr("${seventh_magazine_purchase_way_key}", data.get(6).key);
		tr("${seventh_magazine_purchase_way_value}", pf(data.get(6).value));
		tr("${eighth_magazine_purchase_way_key}", data.get(7).key);
		tr("${eighth_magazine_purchase_way_value}", pf(data.get(7).value));
		tr("${ninth_magazine_purchase_way_key}", data.get(8).key);
		tr("${ninth_magazine_purchase_way_value}", pf(data.get(8).value));
		//tr("${worst_magazine_purchase_way_key}", data.get(data.size() - 1).key);
		//tr("${worst_magazine_purchase_way_value}",
		//		pf(data.get(data.size() - 1).value));

		// 替换城乡期刊获取渠道相关的数据
		DoubleValueRow.sortByV1MinusV2(uvData);
		tr("${best_urban_minus_village_magazine_purchase_way}",
				uvData.get(0).key);
		tr("${second_urban_minus_village_magazine_purchase_way}",
				uvData.get(1).key);
		tr("${third_urban_minus_village_magazine_purchase_way}",
				uvData.get(2).key);
		tr("${fourth_urban_minus_village_magazine_purchase_way}",
				uvData.get(3).key);
		tr("${fiveth_urban_minus_village_magazine_purchase_way}",
				uvData.get(4).key);
		tr("${best_village_minus_urban_magazine_purchase_way}",
				uvData.get(uvData.size() - 1).key);
		tr("${second_village_minus_urban_magazine_purchase_way}",
				uvData.get(uvData.size() - 2).key);
		tr("${third_village_minus_urban_magazine_purchase_way}",
				uvData.get(uvData.size() - 3).key);
		tr("${fourth_village_minus_urban_magazine_purchase_way}",
				uvData.get(uvData.size() - 4).key);
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		BaseRow.table(docx, data,
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");

		DoubleValueRow.sortExceptLastByV1(uvData);
		
		Collections.reverse(uvData);
		DoubleValueRow.reverseValue(uvData);
		DoubleValueRow.chart(uvData);// chart15
		DoubleValueRow.reverseValue(uvData);
		Collections.reverse(uvData);
	}

}
