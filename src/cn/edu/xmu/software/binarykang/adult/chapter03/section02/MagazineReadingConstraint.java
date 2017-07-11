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
 * 解析=>3.2.1.3期刊阅读的制约因素
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class MagazineReadingConstraint extends AdultBaseAction
{
	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	public MagazineReadingConstraint(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "制约阅读期刊的因素");
		DoubleValueRow.read(xlsx, uvData, "城乡居民制约阅读期刊因素比较");
	}

	@Override
	protected void replace()
	{
		// 替换制约阅读期刊的因素相关的变量
		BaseRow.sortExceptLast(data);
		tr("${best_magazine_reading_constraint_key}", data.get(0).key);
		tr("${best_magazine_reading_constraint_value}", pf(data.get(0).value));
		tr("${second_magazine_reading_constraint_key}", data.get(1).key);
		tr("${second_magazine_reading_constraint_value}", pf(data.get(1).value));
		tr("${third_magazine_reading_constraint_key}", data.get(2).key);
		tr("${third_magazine_reading_constraint_value}", pf(data.get(2).value));
		tr("${fourth_magazine_reading_constraint_key}", data.get(3).key);
		tr("${fourth_magazine_reading_constraint_value}", pf(data.get(3).value));
		tr("${fiveth_magazine_reading_constraint_key}", data.get(4).key);
		tr("${fiveth_magazine_reading_constraint_value}", pf(data.get(4).value));
		tr("${sixth_magazine_reading_constraint_key}", data.get(5).key);
		tr("${sixth_magazine_reading_constraint_value}", pf(data.get(5).value));
		tr("${price_expensive_magazine_reading_constraint_value}", 
				pf(BaseRow.getRowByKey("期刊价格太贵", data).value));
		//tr("${last_second_magazine_reading_constraint_key}",
		//		data.get(data.size() - 3).key);
		//tr("${last_second_magazine_reading_constraint_value}",
		//		pf(data.get(data.size() - 3).value));
		//tr("${worst_magazine_reading_constraint_key}",
		//		data.get(data.size() - 2).key);
		//tr("${worst_magazine_reading_constraint_value}",
		//		pf(data.get(data.size() - 2).value));

		// 替换城乡居民制约阅读期刊因素比较相关的变量
		DoubleValueRow.sortByV1MinusV2(uvData);
		tr("${best_urban_minus_village_key}", uvData.get(0).key);
		tr("${best_urban_minus_village_urban_value}", pf(uvData.get(0).v1));
		tr("${best_urban_minus_village_village_value}", pf(uvData.get(0).v2));
		tr("${best_urban_minus_village_value}",
				of(uvData.get(0).v1 - uvData.get(0).v2));
		tr("${best_village_minus_urban_key}", uvData.get(uvData.size() - 1).key);
		tr("${second_village_minus_urban_key}", uvData.get(uvData.size() - 2).key);
		//tr("${best_village_minus_urban_village_value}", pf(uvData.get(uvData.size()-1).v2));
		//tr("${best_village_minus_urban_value}", 
		//		of(uvData.get(uvData.size()-1).v2-uvData.get(uvData.size()-1).v1));
		//tr("${best_village_minus_urban_magazine_reading_constraint_village_value}",
		//		pf(uvData.get(uvData.size() - 1).v2));
		//tr("${best_village_minus_urban_magazine_reading_constraint_value}",
		//		of(uvData.get(uvData.size() - 1).v2
		//				- uvData.get(uvData.size() - 1).v1));
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		Collections.reverse(data);
		BaseRow.chart(data);// chart12
		Collections.reverse(data);
		
		DoubleValueRow.sortExceptLastByV1(uvData);
		DoubleValueRow
				.table(docx,
						uvData,
						"Resource/doubleValueStandard.xml",
						"Resource/doubleValueStandard.xml");
	}

}
