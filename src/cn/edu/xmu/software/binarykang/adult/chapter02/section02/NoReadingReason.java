package cn.edu.xmu.software.binarykang.adult.chapter02.section02;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析2.2.2不读书的原因
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class NoReadingReason extends AdultBaseAction
{
	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	public NoReadingReason(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "居民不读书原因");
		DoubleValueRow.read(xlsx, uvData, "城乡居民不读书原因对比");
	}

	@Override
	protected void replace()
	{
		tr("${useless_reading_reason_value}", pf(data.get(9).value));
		tr("${tv_no_reading_reason_value}", pf(data.get(5).value));
		tr("${internet_no_reading_reason_value}", pf(data.get(3).value));
		tr("${culture_no_reading_reason_value}", pf(data.get(7).value));
		tr("${interest_no_reading_reason_value}", pf(data.get(8).value));
		tr("${notknow_no_reading_reason_value}", pf(data.get(6).value));
		tr("${price_no_reading_reason_value}", pf(data.get(10).value));
		
		DoubleValueRow.sortByV1MinusV2(uvData);
		tr("${best_urban_minus_village_no_reading_reason_key}", uvData.get(0).key);
		tr("${second_urban_minus_village_no_reading_reason_key}", uvData.get(1).key);
		DoubleValueRow.sortByV2MinusV1(uvData);
		tr("${best_village_minus_urban_no_reading_reason_key}", uvData.get(0).key);
		tr("${second_village_minus_urban_no_reading_reason_key}", uvData.get(1).key);
		tr("${third_village_minus_urban_no_reading_reason_key}", uvData.get(2).key);
		// 替换不读书的原因相关的变量
		BaseRow.sort(data);
		tr("${best_no_reading_reason_key}", data.get(0).key);
		tr("${best_no_reading_reason_value}", pf(data.get(0).value));
		tr("${second_no_reading_reason_key}", data.get(1).key);
		tr("${second_no_reading_reason_value}", pf(data.get(1).value));
		tr("${third_no_reading_reason_key}", data.get(2).key);
		tr("${third_no_reading_reason_value}", pf(data.get(2).value));
		tr("${fourth_no_reading_reason_key}", data.get(3).key);
		tr("${fourth_no_reading_reason_value}", pf(data.get(3).value));
		tr("${fiveth_no_reading_reason_key}", data.get(4).key);
		tr("${fiveth_no_reading_reason_value}", pf(data.get(4).value));
		tr("${sixth_no_reading_reason_key}", data.get(5).key);
		tr("${sixth_no_reading_reason_value}", pf(data.get(5).value));
		tr("${seventh_no_reading_reason_key}", data.get(6).key);
		tr("${seventh_no_reading_reason_value}", pf(data.get(6).value));

		// 替换城乡不读书的原因相关的变量
		DoubleValueRow.sortByV2(uvData);
		tr("${village_best_no_reading_reason_key}", uvData.get(0).key);
		tr("${village_best_no_reading_reason_value}", pf(uvData.get(0).v2));
		tr("${village_second_no_reading_reason_key}", uvData.get(1).key);
		tr("${village_second_no_reading_reason_value}", pf(uvData.get(1).v2));

		DoubleValueRow.sortByV1(uvData);
		tr("${urban_best_no_reading_reason_key}", uvData.get(0).key);
		tr("${urban_best_no_reading_reason_value}", pf(uvData.get(0).v1));
		tr("${urban_second_no_reading_reason_key}", uvData.get(1).key);
		tr("${urban_second_no_reading_reason_value}", pf(uvData.get(1).v1));
	}

	
	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		// 生成不阅读原因的表格
		BaseRow.table(docx, data,
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
		
		
		DoubleValueRow.sortExceptLastByV1(uvData);
		// 生成城乡不阅读原因的柱状图
		
		Collections.reverse(uvData);
		DoubleValueRow.reverseValue(uvData);
		DoubleValueRow.chart(uvData);// chart4
		DoubleValueRow.reverseValue(uvData);
		Collections.reverse(uvData);
		
	}
	
//	@Override
//	public void process()
//	{
//		readData();
//		replace();
//		chart();
//		
//	}

}


