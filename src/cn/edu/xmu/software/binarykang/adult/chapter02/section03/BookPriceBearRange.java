package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.Collections;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>2.3.9 ${city}����ͼ��۸��������
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 * 
 */
public final class BookPriceBearRange extends AdultBaseAction
{
	private final String AVERAGE_BOOK_PRICE_BEAR_RANGE_TABLE_KEY = "2.3.9**�о���ͼ��۸�������� �ڶ��������е�ͼ��ɽ���ƽ���۸�";

	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	private double localAverageBookPrice;
	private double countryAverageBookPrice;
	private double urbanBookPrice;
	private double villageBookPrice;

	public BookPriceBearRange(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		countryAverageBookPrice = SingleValue.read(xlsx, "2.3.9**�о���ͼ��۸�������� �ڶ��������е�ͼ��ɽ���ƽ���۸�ȫ����", 3, 1);
		XSSFRow averageBookPriceRow = xlsx.getRow(xlsx.getRowByKey(AVERAGE_BOOK_PRICE_BEAR_RANGE_TABLE_KEY) + 3);
		localAverageBookPrice = new Double(xlsx.getContent(averageBookPriceRow, BEGIN_COL + 1));
		urbanBookPrice = new Double(xlsx.getContent(averageBookPriceRow, BEGIN_COL + 2));
		villageBookPrice = new Double(xlsx.getContent(averageBookPriceRow, BEGIN_COL + 3));
		BaseRow.read(xlsx, data, "ͼ��۸�ɽ��ܷ�Χ");
		DoubleValueRow.read(xlsx, uvData, "�������ͼ��۸���������Ա�");
	}

	@Override
	protected void replace()
	{
		// �����滻
		tr("${average_book_price_bear_range}", tf(localAverageBookPrice));
		tr("${country_average_book_price_bear_range}", tf(countryAverageBookPrice));
		tr("${local_minus_country_average_book_price_bear_range_key}",
				localAverageBookPrice > countryAverageBookPrice ? "��" : "��");
		tr("${local_minus_country_average_book_price_bear_range_value}",
				tf(localAverageBookPrice-countryAverageBookPrice));
		tr("${urban_average_book_price_bear_range}", tf(urbanBookPrice));
		tr("${village_average_book_price_bear_range}", tf(villageBookPrice));
		// �滻ͼ��۸�ɽ��ܷ�Χ��صı���
		tr("${from_12_to_20_average_book_price_bear_range}", pf(data.get(3).value));
		tr("${from_20_to_30_average_book_price_bear_range}", pf(data.get(4).value));
		tr("${from_8_to_12_average_book_price_bear_range}", pf(data.get(2).value));
		tr("${from_0_to_4_average_book_price_bear_range}", pf(data.get(0).value));
		tr("${from_30_average_book_price_bear_range}", pf(data.get(5).value));
		tr("${whatever_book_price_bear_range_value}", pf(data.get(data.size() - 1).value));
	
		tr("${urban_book_price_bear_range_value}", tf(urbanBookPrice));
		tr("${village_book_price_bear_range_value}", tf(villageBookPrice));
		tr("${urban_minus_village_book_price_bear_range_key}", 
				urbanBookPrice>villageBookPrice?"�߳�":"�ͳ�");
		tr("${urban_minus_village_book_price_bear_range_value}", tf(Math.abs(urbanBookPrice-villageBookPrice)));
		
		tr("${from_12_to_30_village_book_price_bear_range_value}", 
				pf(DoubleValueRow.getRowByKey("12��20Ԫ", uvData).v2+DoubleValueRow.getRowByKey("20��30Ԫ", uvData).v2));
		tr("${from_12_to_30_urban_book_price_bear_range_value}",
				pf(DoubleValueRow.getRowByKey("12��20Ԫ", uvData).v1+DoubleValueRow.getRowByKey("20��30Ԫ", uvData).v1));
		tr("${from_12_to_30_village_minus_urban_book_price_bear_range_key}", 
				DoubleValueRow.getRowByKey("12��20Ԫ", uvData).v2+DoubleValueRow.getRowByKey("20��30Ԫ", uvData).v2>
				DoubleValueRow.getRowByKey("12��20Ԫ", uvData).v1+DoubleValueRow.getRowByKey("20��30Ԫ", uvData).v1?
				"��":"��");
		tr("${from_12_to_30_village_minus_urban_book_price_bear_range_value}", 
				of(Math.abs(DoubleValueRow.getRowByKey("12��20Ԫ", uvData).v2+DoubleValueRow.getRowByKey("20��30Ԫ", uvData).v2-
				DoubleValueRow.getRowByKey("12��20Ԫ", uvData).v1-DoubleValueRow.getRowByKey("20��30Ԫ", uvData).v1)));
		
		tr("${from_0_to_12_village_book_price_bear_range_value}",
				pf(DoubleValueRow.getRowByKey("4Ԫ����", uvData).v2+DoubleValueRow.getRowByKey("4��8Ԫ", uvData).v2+
						DoubleValueRow.getRowByKey("8��12Ԫ", uvData).v2));
		tr("${from_0_to_12_urban_book_price_bear_range_value}",
				pf(DoubleValueRow.getRowByKey("4Ԫ����", uvData).v1+DoubleValueRow.getRowByKey("4��8Ԫ", uvData).v1+
						DoubleValueRow.getRowByKey("8��12Ԫ", uvData).v1));
		tr("${from_0_to_12_village_minus_urban_book_price_bear_range_key}", 
				DoubleValueRow.getRowByKey("4Ԫ����", uvData).v2+DoubleValueRow.getRowByKey("4��8Ԫ", uvData).v2+
						DoubleValueRow.getRowByKey("8��12Ԫ", uvData).v2>
				DoubleValueRow.getRowByKey("4Ԫ����", uvData).v1+DoubleValueRow.getRowByKey("4��8Ԫ", uvData).v1+
						DoubleValueRow.getRowByKey("8��12Ԫ", uvData).v1?"��":"��");
		tr("${from_0_to_12_village_minus_urban_book_price_bear_range_value}",
				of(Math.abs(DoubleValueRow.getRowByKey("4Ԫ����", uvData).v2+DoubleValueRow.getRowByKey("4��8Ԫ", uvData).v2+
						DoubleValueRow.getRowByKey("8��12Ԫ", uvData).v2-
				DoubleValueRow.getRowByKey("4Ԫ����", uvData).v1-DoubleValueRow.getRowByKey("4��8Ԫ", uvData).v1-
						DoubleValueRow.getRowByKey("8��12Ԫ", uvData).v1)));
		
		tr("${whatever_price_village_book_price_bear_range_value}", 
				pf(DoubleValueRow.getRowByKey("ֻҪϲ���������", uvData).v2));
		tr("${whatever_price_village_minus_urban_book_price_bear_range_key}", 
				DoubleValueRow.getRowByKey("ֻҪϲ���������", uvData).v2>DoubleValueRow.getRowByKey("ֻҪϲ���������", uvData).v1?
						"����":"����");
		tr("${whatever_price_urban_book_price_bear_range_value}",
				pf(DoubleValueRow.getRowByKey("ֻҪϲ���������", uvData).v1));
//		data.remove(data.size() - 1);
//		BaseRow.sort(data);
//		tr("${best_book_price_bear_range_key}", data.get(0).key);
//		tr("${best_book_price_bear_range_value}", pf(data.get(0).value));
//		tr("${second_book_price_bear_range_key}", data.get(1).key);
//		tr("${second_book_price_bear_range_value}", pf(data.get(1).value));
//		tr("${third_book_price_bear_range_key}", data.get(2).key);
//		tr("${third_book_price_bear_range_value}", pf(data.get(2).value));
//		tr("${fourth_book_price_bear_range_key}", data.get(3).key);
//		tr("${fourth_book_price_bear_range_value}", pf(data.get(3).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.table(docx, data,  
				"Resource/singleValueStandard.xml", 
				"Resource/singleValueStandard.xml");
		
		Collections.reverse(uvData);
		DoubleValueRow.reverseValue(uvData);
		DoubleValueRow.chart(uvData);// chart11
		DoubleValueRow.reverseValue(uvData);
		Collections.reverse(uvData);
	}
//	@Override
//	public void process()
//	{
//		readData();
//		replace();
//		chart();
//	}
}
