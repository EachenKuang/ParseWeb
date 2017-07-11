package cn.edu.xmu.software.binarykang.adult.chapter03.section02;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>3.2.2.2 ${city}居民期刊价格承受能力
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class MagazinePriceBearRange extends AdultBaseAction
{
	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	private double localAverageMagazinePriceBearRange;
	private double countryAverageMagazinePriceBearRange;
	private double urbanAverageMagazinePriceBearRange;
	private double villageAverageMagazinePriceBearRange;

	public MagazinePriceBearRange(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "居民期刊价格承受能力");
		DoubleValueRow.read(xlsx, uvData, "城乡期刊价格承受能力对比");

		XSSFRow localRow = xlsx.getRow(xlsx
				.getRowByKey("3.2.2.2 **市居民期刊价格承受能力 第一段文字中的期刊可接受价格（当地）") + 3);
		XSSFRow countryRow = xlsx.getRow(xlsx
				.getRowByKey("3.2.2.2 **市居民期刊价格承受能力 第一段文字中的期刊可接受价格（全国）") + 3);
		localAverageMagazinePriceBearRange = new Double(xlsx.getContent(
				localRow, BEGIN_COL + 1));
		urbanAverageMagazinePriceBearRange = new Double(xlsx.getContent(
				localRow, BEGIN_COL + 2));
		villageAverageMagazinePriceBearRange = new Double(xlsx.getContent(
				localRow, BEGIN_COL + 3));
		countryAverageMagazinePriceBearRange = new Double(xlsx.getContent(
				countryRow, BEGIN_COL + 1));
	}

	@Override
	protected void replace()
	{
		// 行文替换
		tr("${local_magazine_price_bear_range_value}",
				tf(localAverageMagazinePriceBearRange));
		tr("${country_magazine_price_bear_range_value}",
				tf(countryAverageMagazinePriceBearRange));
		tr("${local_minus_country_magazine_price_bear_range_key}",
				localAverageMagazinePriceBearRange > countryAverageMagazinePriceBearRange ? "高"
						: "低");
		tr("${local_minus_country_magazine_price_bear_range_value}",
				tf(Math.abs(localAverageMagazinePriceBearRange-countryAverageMagazinePriceBearRange)));
		//
		tr("${urban_magazine_price_bear_range_value}",
				tf(urbanAverageMagazinePriceBearRange));
		tr("${village_magazine_price_bear_range_value}",
				tf(villageAverageMagazinePriceBearRange));
		tr("${urban_minus_village_magazine_price_bear_range_key}",
				urbanAverageMagazinePriceBearRange > villageAverageMagazinePriceBearRange ? "高"
						: "低");
		// 替换居民期刊价格承受能力相关的变量
		tr("${most_cheap_magazine_price_bear_range_value}",
				pf(1 - data.get(data.size() - 1).value - data.get(0).value));
		tr("${most_cheap_magazine_price_bear_range_key}",
				data.get(data.size() - 1).key);
		tr("${most_expensive_magazine_price_bear_range_value}",
				pf(data.get(data.size() - 1).value));
		BaseRow.sort(data);
		tr("${best_magazine_price_bear_range_key}", data.get(0).key);
		tr("${best_magazine_price_bear_range_value}", pf(data.get(0).value));
		tr("${from_3_to_6_magazine_price_bear_range_value}", pf(BaseRow.getRowByKey("3～6元", data).value));
		tr("${from_6_to_9_magazine_price_bear_range_value}", pf(BaseRow.getRowByKey("6～9元", data).value));
		tr("${from_9_to_19_magazine_price_bear_range_value}", pf(BaseRow.getRowByKey("9～19元", data).value));
		tr("${from_19_magazine_price_bear_range_value}", pf(BaseRow.getRowByKey("19元及以上", data).value));
		// 替换城乡期刊价格承受能力对比相关的变量
		DoubleValueRow.sortByV1(uvData);
		tr("${best_urban_magazine_price_bear_range_key}", uvData.get(0).key);
		tr("${best_urban_magazine_price_bear_range_value}",
				pf(uvData.get(0).v1));
		tr("${best_urban_minus_village_magazine_price_bear_range_key}",
				uvData.get(0).v1 > uvData.get(0).v2 ? "高" : "低");
		tr("${best_urban_minus_village_magazine_price_bear_range_value}",
				of(Math.abs(uvData.get(0).v1 - uvData.get(0).v2)));

		DoubleValueRow.sortByV2(uvData);
		tr("${best_village_magazine_price_bear_range_key}", uvData.get(0).key);
		tr("${best_village_magazine_price_bear_range_value}",
				pf(uvData.get(0).v2));
		tr("${best_village_minus_urban_magazine_price_bear_range_key}",
				uvData.get(0).v2 > uvData.get(0).v1 ? "高" : "低");
		tr("${best_village_minus_urban_magazine_price_bear_range_value}",
				of(Math.abs(uvData.get(0).v2 - uvData.get(0).v1)));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart16
		DoubleValueRow.reverseValue(uvData);
		//DoubleValueRow.chart(uvData);// chart17
		//DoubleValueRow.reverseValue(uvData);
	}

}
