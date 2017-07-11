package cn.edu.xmu.software.binarykang.adult.chapter04.section05;

import java.util.Comparator;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.5.1上网率
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class InternetRate extends AdultBaseAction
{
	private List<BaseRow> localData;
	private List<BaseRow> countryData;
	private double localInternetRate;
	private double countryInternetRate;
	private List<DoubleValueRow> uvData;
	private DoubleValueRow lastRow;

	public InternetRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		localData = ListFactory.getBaseRows();
		countryData = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, localData, "居民上网率");
		localInternetRate = 1 - localData.get(localData.size() - 1).value;
		localData.remove(localData.size() - 1);

		BaseRow.read(xlsx, countryData, "4.5.1上网率 第一段文字中的全国上网率");
		countryInternetRate = 1 - countryData.get(countryData.size() - 1).value;

		DoubleValueRow.read(xlsx, uvData, "居民上网途径的城乡对比");
		lastRow = uvData.get(uvData.size() - 1);
		// 重新调整一下数据
		lastRow.key = "综合上网率";
		lastRow.v1 = 1 - lastRow.v1;
		lastRow.v2 = 1 - lastRow.v2;
	}

	@Override
	protected void replace()
	{
		// 替换居民上网率相关的变量
		BaseRow.sortExceptLast(localData);
		tr("${local_internet_contact_rate_value}", pf(localInternetRate));
		tr("${country_internet_contact_rate_value}", pf(countryInternetRate));
		tr("${local_minus_country_internet_contact_rate_key}",
				localInternetRate > countryInternetRate ? "高" : "低");
		tr("${local_minus_country_internet_contact_rate_value}",
				of(Math.abs(localInternetRate - countryInternetRate)));
		tr("${best_internet_contact_rate_key}", localData.get(0).key);
		tr("${best_internet_contact_rate_value}", pf(localData.get(0).value));
		tr("${second_internet_contact_rate_key}", localData.get(1).key);
		tr("${second_internet_contact_rate_value}", pf(localData.get(1).value));
		// 替换城乡居民上网率对比相关的变量
		tr("${urban_internet_contact_rate_value}",
				pf(uvData.get(uvData.size() - 1).v1));
		tr("${village_internet_contact_rate_value}",
				pf(uvData.get(uvData.size() - 1).v2));
		tr("${urban_minus_village_internet_contact_rate_key}",
				uvData.get(uvData.size() - 1).v1 > uvData.get(uvData.size() - 1).v2 ? "高"
						: "低");
		tr("${urban_minus_village_internet_contact_rate_value}",
				of(Math.abs(uvData.get(uvData.size() - 1).v1
						- uvData.get(uvData.size() - 1).v2)));
		
		tr("${urban_computer_internet_contact_rate_value}",
				pf(uvData.get(0).v1));
		tr("${village_computer_internet_contact_rate_value}",
				pf(uvData.get(0).v2));
		tr("${urban_minus_village_computer_internet_contact_rate_key}",
				uvData.get(0).v1 > uvData.get(0).v2 ? "高" : "低");
		tr("${urban_minus_village_computer_internet_contact_rate_value}",
				of(Math.abs(uvData.get(0).v1 - uvData.get(0).v2)));
		
		tr("${village_phone_internet_contact_rate_value}", pf(uvData.get(1).v2));
		tr("${urban_phone_internet_contact_rate_value}", pf(uvData.get(1).v1));
		tr("${urban_minus_village_phone_internet_contact_rate_key}",
				uvData.get(1).v1 > uvData.get(1).v2 ? "高" : "低");
		tr("${urban_minus_village_phone_internet_contact_rate_value}",
				of(Math.abs(uvData.get(1).v2 - uvData.get(1).v1)));

	}

	@Override
	protected void chart()
	{
		BaseRow.chart(localData);// chart31
		
		uvData.remove(uvData.size()-1);
		uvData.add(lastRow);
//		uvData.sort(new Comparator<DoubleValueRow>()
//		{
//
//			@Override
//			public int compare(DoubleValueRow o1, DoubleValueRow o2)
//			{
//				if (o1.key.equals("其他"))
//					return 1;
//				if (o2.key.equals("其他"))
//					return -1;
//				if (o1.key.equals("综合上网率"))
//					return -1;
//				if (o2.key.equals("综合上网率"))
//					return 1;
//				if (o1.v1 < o2.v1)
//					return 1;
//				else if (o1.v1 > o2.v1)
//					return -1;
//				return 0;
//			}
//		});

		DoubleValueRow.chart(uvData, "城市居民", "农村居民");// chart32
	}

	@Override
	public void process()
	{
		readData();
		replace();
		chart();
	}

}
