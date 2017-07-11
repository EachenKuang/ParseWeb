package cn.edu.xmu.software.binarykang.adult.chapter04.section02;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.2.1电子书报刊阅读率
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class EBookReadingRate extends AdultBaseAction
{
	private double localEBookReadingRate;
	private double countryEBookReadingRate;
	private double localEBookReadingNum;
	private double countryEBookReadingNum;
	private double localENewspaperReadingRate;
	private double countryENewspaperReadingRate;
	private double localEMagazineReadingRate;
	private double countryEMagazineReadingRate;

	public EBookReadingRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
	}

	@Override
	protected void readData()
	{
		localEBookReadingRate = SingleValue.read(xlsx,
				"4.2.1电子书报刊阅读率 第一段文字中的电子书阅读率（当地）");
		countryEBookReadingRate = SingleValue.read(xlsx,
				"4.2.1电子书报刊阅读率 第一段文字中的电子书阅读率（全国）");
		localEBookReadingNum = SingleValue.read(xlsx,
				"4.2.1电子书报刊阅读率 第一段文字中的人均阅读电子书数量（当地）");
		countryEBookReadingNum = SingleValue.read(xlsx,
				"4.2.1电子书报刊阅读率 第一段文字中的人均阅读电子书数量（全国）");
		localENewspaperReadingRate = SingleValue.read(xlsx,
				"4.2.1电子书报刊阅读率 第一段文字中的电子报阅读率（当地）");
		countryENewspaperReadingRate = SingleValue.read(xlsx,
				"4.2.1电子书报刊阅读率 第一段文字中的电子报阅读率（全国）");
		localEMagazineReadingRate = SingleValue.read(xlsx,
				"4.2.1电子书报刊阅读率 第一段文字中的电子杂志阅读率（当地）");
		countryEMagazineReadingRate = SingleValue.read(xlsx,
				"4.2.1电子书报刊阅读率 第一段文字中的电子杂志阅读率（全国）");
	}

	@Override
	protected void replace()
	{
		tr("${local_ebook_reading_rate_value}", pf(localEBookReadingRate));
		tr("${country_ebook_reading_rate_value}", pf(countryEBookReadingRate));
		tr("${local_minus_country_ebook_reading_rate_key}",
				localEBookReadingRate > countryEBookReadingRate ? "高" : "低");
		tr("${local_minus_country_ebook_reading_rate_value}",
				of(Math.abs(localEBookReadingRate - countryEBookReadingRate)));
		
		tr("${local_ebook_reading_rate_num_value}", tf(localEBookReadingNum));
		tr("${country_ebook_reading_rate_num_value}",
				tf(countryEBookReadingNum));
		tr("${local_minus_country_ebook_reading_rate_num_key}",
				localEBookReadingNum > countryEBookReadingNum ? "高" : "低");
		tr("${local_minus_country_ebook_reading_rate_num_value}",
				tf(Math.abs(localEBookReadingNum - countryEBookReadingNum)));
		
		tr("${local_enewspaper_reading_rate_value}",
				pf(localENewspaperReadingRate));
		tr("${country_enewspaper_reading_rate_value}",
				pf(countryENewspaperReadingRate));
		tr("${local_minus_country_enewspaper_reading_rate_num_key}",
				localENewspaperReadingRate > countryENewspaperReadingRate ? "高" : "低");
		tr("${local_minus_country_enewspaper_reading_rate_num_value}",
				of(Math.abs(localENewspaperReadingRate - countryENewspaperReadingRate)));
		
	
		tr("${local_emagazine_reading_rate_value}",
				pf(localEMagazineReadingRate));
		tr("${country_emagazine_reading_rate_value}",
				pf(countryEMagazineReadingRate));
		tr("${local_minus_country_emagzine_reading_rate_num_key}",
				localEMagazineReadingRate > countryEMagazineReadingRate ? "高" : "低");
		tr("${local_minus_country_emagzine_reading_rate_num_value}",
				of(Math.abs(localEMagazineReadingRate - countryEMagazineReadingRate)));
	}

	@Override
	protected void chart()
	{

	}

}
