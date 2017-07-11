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
 * ����=>2.3.7 ${city}����ͼ�鹺������ķֲ��ܶ�
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookPurchaseDistance extends AdultBaseAction
{
	private final String AVERAGE_BOOK_PURCHASE_DISTANCE_TABLE_KEY = "2.3.7**�о���ͼ�鹺������ķֲ��ܶȵ�һ�������еĹ�����ƽ������";

	private List<BaseRow> data;
	private double averageDistance;
	private double averageUrbanDistance;
	private double averageVillageDistance;
	private double averageCountryDistance;

	public BookPurchaseDistance(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		XSSFRow averageDistanceRow = xlsx.getRow(xlsx
				.getRowByKey(AVERAGE_BOOK_PURCHASE_DISTANCE_TABLE_KEY) + 3);
		averageDistance = new Double(xlsx.getContent(averageDistanceRow,
				BEGIN_COL + 1));
		averageUrbanDistance = new Double(xlsx.getContent(averageDistanceRow,
				BEGIN_COL + 2));
		averageVillageDistance = new Double(xlsx.getContent(averageDistanceRow,
				BEGIN_COL + 3));
		averageCountryDistance = SingleValue.read(xlsx,
				"2.3.7**�о���ͼ�鹺������ķֲ��ܶȵ�һ�������еĹ�����ƽ������-ȫ��", 1, 1);
		BaseRow.read(xlsx, data, "���������Ĺ�������");
	}

	@Override
	protected void replace()
	{
		tr("${average_country_book_purchase_distance}", tf(averageCountryDistance));
		tr("${average_book_purchase_distance}", tf(averageDistance));
		tr("${average_minus_country_book_purchase_distance_key}", 
				averageDistance>averageCountryDistance?"���":"�ٳ�");
		tr("${average_minus_country_book_purchase_distance_value}", 
				tf(Math.abs(averageDistance-averageCountryDistance)));
		tr("${average_urban_book_purchase_distance}", tf(averageUrbanDistance));
		tr("${average_village_book_purchase_distance}", tf(averageVillageDistance));
		tr("${average_village_minus_urban_book_purchase_distance_value}",
				tf(Math.abs(averageUrbanDistance - averageVillageDistance)));
		tr("${average_village_minus_urban_book_purchase_distance_key}",
				averageVillageDistance > averageUrbanDistance ? "��" : "��");
		//BaseRow.sort(data);
		//tr("${best_book_purchase_distance_key}", data.get(0).key);
		tr("${best_book_purchase_distance_value}", 
				pf(data.get(0).value+data.get(1).value+data.get(2).value+data.get(3).value));
		//tr("${second_book_purchase_distance_key}", data.get(1).key);
		tr("${second_book_purchase_distance_value}", pf(data.get(0).value+data.get(1).value));
		tr("${more_than_three_km_book_purchase_distance_value}", 
				pf(BaseRow.getRowByKey("4��������", data).value+BaseRow.getRowByKey("5��������", data).value
				+BaseRow.getRowByKey("5��10����", data).value+BaseRow.getRowByKey("10���Ｐ����", data).value));
		tr("${ten_km_book_purchase_distance_value}", 
				pf(BaseRow.getRowByKey("10���Ｐ����", data).value));
		tr("${do_not_know_book_purchase_distance_value}",
				pf(BaseRow.getRowByKey("������M��֪��", data).value));
	}

	@Override
	protected void chart()
	{
		Collections.reverse(data);
		BaseRow.chart(data);// chart9
		Collections.reverse(data);
	}

}
