package cn.edu.xmu.software.binarykang.adult.chapter04.section05;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.common.rowtype.TripleValueRow;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.5.2上网频率
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 * 
 */
public final class InternetFrequency extends AdultBaseAction
{
	private double localInternetFrequency;
	private double countryInternetFrequency;
	private double urbanInternetFrequency;
	private double villageInternetFrequency;
	private double maleInternetFrequency;
	private double femaleInternetFrequency;

	private List<TripleValueRow> data;

	public InternetFrequency(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getTripleValueRows();
	}

	@Override
	protected void readData()
	{
		localInternetFrequency = SingleValue.read(xlsx, "4.5.2上网频率 第一段文字中的平均每月上网次数（当地）", 3, 1);
		countryInternetFrequency = SingleValue.read(xlsx, "4.5.2上网频率 第一段文字中的平均每月上网次数（全国）", 3, 1);
		urbanInternetFrequency = SingleValue.read(xlsx, "4.5.2上网频率 第一段文字中的平均每月上网次数（当地）", 3, 2);
		villageInternetFrequency = SingleValue.read(xlsx, "4.5.2上网频率 第一段文字中的平均每月上网次数（当地）", 3, 3);
		maleInternetFrequency = SingleValue.read(xlsx, "4.5.2上网频率 第一段文字中的平均每月上网次数（当地）", 3,4);
		femaleInternetFrequency = SingleValue.read(xlsx, "4.5.2上网频率 第一段文字中的平均每月上网次数（当地）", 3, 5);
	
		
		TripleValueRow.read(xlsx, data, "上网频率对比");
	}

	@Override
	protected void replace()
	{
		tr("${urban_internet_frequency_value}", tf(urbanInternetFrequency));
		tr("${village_internet_frequency_value}", tf(villageInternetFrequency));
		tr("${urban_minus_village_internet_frequency_key}", urbanInternetFrequency > villageInternetFrequency ? "高"
				: "低");
		tr("${urban_minus_village_internet_frequency_value}",
				tf(Math.abs(urbanInternetFrequency - villageInternetFrequency )));
		
		tr("${local_internet_frequency_value}", tf(localInternetFrequency));
		tr("${country_internet_frequency_value}", tf(countryInternetFrequency));
		tr("${local_minus_country_internet_frequency_key}", localInternetFrequency > countryInternetFrequency ? "高"
				: "低");
		tr("${local_minus_country_internet_frequency_value}",
				tf(Math.abs(localInternetFrequency - countryInternetFrequency )));
		
		tr("${male_internet_frequency_value}", tf(maleInternetFrequency));
		tr("${female_internet_frequency_value}", tf(femaleInternetFrequency));
		tr("${male_minus_female_internet_frequency_key}", maleInternetFrequency > femaleInternetFrequency ? "高"
				: "低");
//		tr("${male_minus_female_internet_frequency_key}",
//				pf(Math.abs(maleInternetFrequency - femaleInternetFrequency )));
		
		tr("${equal_or_more_than_once_a_week_internet_frequency_value}",
				pf(data.get(0).v1 + data.get(1).v1 + data.get(2).v1 + data.get(3).v1));

		TripleValueRow.sortByV1(data);
		tr("${best_internet_frequency_key}", data.get(0).key);
		tr("${best_internet_frequency_value}", pf(data.get(0).v1));
		tr("${second_internet_frequency_key}", data.get(1).key);
		tr("${second_internet_frequency_value}", pf(data.get(1).v1));
		tr("${third_internet_frequency_key}", data.get(2).key);
		tr("${third_internet_frequency_value}", pf(data.get(2).v1));
		tr("${fourth_internet_frequency_key}", data.get(3).key);
		tr("${fourth_internet_frequency_value}", pf(data.get(3).v1));
	}

	@Override
	protected void chart()
	{
		TripleValueRow.table(docx, data, 
				"Resource/adult/chapter04/internet_frequency_even_row.xml",
				"Resource/adult/chapter04/internet_frequency_even_row.xml");
	}

}
