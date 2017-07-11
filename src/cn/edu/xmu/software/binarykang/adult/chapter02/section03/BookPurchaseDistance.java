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
 * 解析=>2.3.7 ${city}居民图书购买网点的分布密度
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookPurchaseDistance extends AdultBaseAction
{
	private final String AVERAGE_BOOK_PURCHASE_DISTANCE_TABLE_KEY = "2.3.7**市居民图书购买网点的分布密度第一段文字中的购书点的平均距离";

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
				"2.3.7**市居民图书购买网点的分布密度第一段文字中的购书点的平均距离-全国", 1, 1);
		BaseRow.read(xlsx, data, "居民距最近的购书点距离");
	}

	@Override
	protected void replace()
	{
		tr("${average_country_book_purchase_distance}", tf(averageCountryDistance));
		tr("${average_book_purchase_distance}", tf(averageDistance));
		tr("${average_minus_country_book_purchase_distance_key}", 
				averageDistance>averageCountryDistance?"多出":"少出");
		tr("${average_minus_country_book_purchase_distance_value}", 
				tf(Math.abs(averageDistance-averageCountryDistance)));
		tr("${average_urban_book_purchase_distance}", tf(averageUrbanDistance));
		tr("${average_village_book_purchase_distance}", tf(averageVillageDistance));
		tr("${average_village_minus_urban_book_purchase_distance_value}",
				tf(Math.abs(averageUrbanDistance - averageVillageDistance)));
		tr("${average_village_minus_urban_book_purchase_distance_key}",
				averageVillageDistance > averageUrbanDistance ? "多" : "少");
		//BaseRow.sort(data);
		//tr("${best_book_purchase_distance_key}", data.get(0).key);
		tr("${best_book_purchase_distance_value}", 
				pf(data.get(0).value+data.get(1).value+data.get(2).value+data.get(3).value));
		//tr("${second_book_purchase_distance_key}", data.get(1).key);
		tr("${second_book_purchase_distance_value}", pf(data.get(0).value+data.get(1).value));
		tr("${more_than_three_km_book_purchase_distance_value}", 
				pf(BaseRow.getRowByKey("4公里以内", data).value+BaseRow.getRowByKey("5公里以内", data).value
				+BaseRow.getRowByKey("5～10公里", data).value+BaseRow.getRowByKey("10公里及以上", data).value));
		tr("${ten_km_book_purchase_distance_value}", 
				pf(BaseRow.getRowByKey("10公里及以上", data).value));
		tr("${do_not_know_book_purchase_distance_value}",
				pf(BaseRow.getRowByKey("不清楚∕不知道", data).value));
	}

	@Override
	protected void chart()
	{
		Collections.reverse(data);
		BaseRow.chart(data);// chart9
		Collections.reverse(data);
	}

}
