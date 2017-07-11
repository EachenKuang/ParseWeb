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
 * ����2.2.2�������ԭ��
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
		BaseRow.read(xlsx, data, "���񲻶���ԭ��");
		DoubleValueRow.read(xlsx, uvData, "������񲻶���ԭ��Ա�");
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
		// �滻�������ԭ����صı���
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

		// �滻���粻�����ԭ����صı���
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
		// ���ɲ��Ķ�ԭ��ı��
		BaseRow.table(docx, data,
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
		
		
		DoubleValueRow.sortExceptLastByV1(uvData);
		// ���ɳ��粻�Ķ�ԭ�����״ͼ
		
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


