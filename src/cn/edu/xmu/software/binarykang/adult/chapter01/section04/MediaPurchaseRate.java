package cn.edu.xmu.software.binarykang.adult.chapter01.section04;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析1.4成年人媒介消费状况
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-14
 *
 */
public final class MediaPurchaseRate extends AdultBaseAction
{
	private List<MediaPurchaseRate.TableRow> data;
	private List<MediaPurchaseRate.TableRow> data_origin;

	public MediaPurchaseRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = new ArrayList<MediaPurchaseRate.TableRow>();
		data_origin = new ArrayList<MediaPurchaseRate.TableRow>();
	}

	@Override
	protected void readData()
	{
		int mediaPurchaseRateRowIndex = xlsx.getRowByKey("各类出版物购买率");
		XSSFRow mediaPurchaseRateRow = null;
		for (int i = mediaPurchaseRateRowIndex + 3; (mediaPurchaseRateRow = xlsx
				.getRow(i)).getLastCellNum() != -1; i += 2)
		{
			TableRow tableRow = new TableRow();
			tableRow.mediaType = xlsx.getContent(mediaPurchaseRateRow,
					BEGIN_COL);
			tableRow.average = new Double(xlsx.getContent(mediaPurchaseRateRow,
					BEGIN_COL + 2));
			tableRow.urban = new Double(xlsx.getContent(mediaPurchaseRateRow,
					BEGIN_COL + 3));
			tableRow.village = new Double(xlsx.getContent(mediaPurchaseRateRow,
					BEGIN_COL + 4));
			tableRow.urbanMinusVillage = tableRow.urban - tableRow.village;
			tableRow.male = new Double(xlsx.getContent(mediaPurchaseRateRow,
					BEGIN_COL + 5));
			tableRow.female = new Double(xlsx.getContent(mediaPurchaseRateRow,
					BEGIN_COL + 6));
			tableRow.maleMinusFemale = tableRow.male - tableRow.female;
			tableRow.femaleMinusMale = tableRow.female - tableRow.male;
			data.add(tableRow);
		}
		
	}

	@Override
	protected void replace()
	{
		data_origin = data;
		
		tr("${urban_book_purchase_rate}", pf(data_origin.get(0).urban));
		tr("${urban_village_book_purchase_rate_diff}", data_origin.get(0).urbanMinusVillage>0?"高出":"低出");
		tr("${village_book_purchase_rate}", pf(data_origin.get(0).village));
		tr("${urban_village_book_purchase_rate_diff_num}", of(Math.abs(data_origin.get(0).urbanMinusVillage)));
		
		tr("${urban_newspaper_purchase_rate}", pf(data_origin.get(1).urban));
		tr("${urban_village_newspaper_purchase_rate_diff}", data_origin.get(1).urbanMinusVillage>0?"高出":"低出");
		tr("${village_newspaper_purchase_rate}", pf(data_origin.get(1).village));
		tr("${urban_village_newspaper_purchase_rate_diff_num}", of(Math.abs(data_origin.get(1).urbanMinusVillage)));
		
		tr("${urban_magazine_purchase_rate}", pf(data_origin.get(2).urban));
		tr("${urban_village_magazine_purchase_rate_diff}", data_origin.get(2).urbanMinusVillage>0?"高出":"低出");
		tr("${village_magazine_purchase_rate}", pf(data_origin.get(2).village));
		tr("${urban_village_magazine_purchase_rate_diff_num}", of(Math.abs(data_origin.get(2).urbanMinusVillage)));
		//20160420
		tr("${male_newspaper_purchase_rate}", pf(data_origin.get(1).male));
		tr("${gender_newspaper_purchase_rate_diff}", data_origin.get(1).maleMinusFemale>0?"高出":"低出");
		tr("${female_newspaper_purchase_rate}", pf(data_origin.get(1).female));
		tr("${gender_newspaper_purchase_rate_diff_num}", of(Math.abs(data_origin.get(1).maleMinusFemale)));
		
		tr("${male_book_purchase_rate}", pf(data_origin.get(0).male));
		tr("${gender_book_purchase_rate_diff}", data_origin.get(0).maleMinusFemale>0?"高出":"低出");
		tr("${female_book_purchase_rate}", pf(data_origin.get(0).female));
		tr("${gender_book_purchase_rate_diff_num}", of(Math.abs(data_origin.get(0).maleMinusFemale)));
		//20160420
		tr("${male_cd_purchase_rate}", pf(data_origin.get(4).male));
		tr("${gender_cd_purchase_rate_diff}", 
				(data_origin.get(4).male)>(data_origin.get(4).female)?"高出":"低出");
		tr("${female_cd_purchase_rate}", pf(data_origin.get(4).female));
		tr("${gender_cd_purchase_rate_diff_num}", 
				of(Math.abs(data_origin.get(4).male-data_origin.get(4).female)));
		data.sort(new Comparator<TableRow>()
		{

			@Override
			public int compare(TableRow o1, TableRow o2)
			{
				if (o1.average < o2.average)
					return 1;
				else if (o1.average > o2.average)
					return -1;
				return 0;
			}
		});
		tr("${best_purchase_media}", data.get(0).mediaType);
		tr("${best_purchase_rate}", pf(data.get(0).average));
		tr("${second_purchase_media}", data.get(1).mediaType);
		tr("${second_purchase_rate}", pf(data.get(1).average));
		tr("${third_purchase_media}", data.get(2).mediaType);
		tr("${third_purchase_rate}", pf(data.get(2).average));
		tr("${worst_second_purchase_media}", data.get(data.size()-2).mediaType);
		tr("${worst_third_purchase_media}", data.get(data.size()-3).mediaType);
		tr("${worst_fourth_purchase_media}", data.get(data.size()-4).mediaType);
		tr("${worst_purchase_media}", data.get(data.size() - 1).mediaType);
		tr("${worst_purchase_rate}", pf(data.get(data.size() - 1).average));
		//20160420
//		tr("${urban_book_purchase_rate}", pf(data_origin.get(0).urban));
//		tr("${urban_village_book_purchase_rate_diff}", data_origin.get(0).urbanMinusVillage>0?"高出":"低出");
//		tr("${village_book_purchase_rate}", pf(data_origin.get(0).village));
//		tr("${urban_village_book_purchase_rate_diff_num}", of(Math.abs(data_origin.get(0).urbanMinusVillage)));
//		
//		tr("${urban_newspaper_purchase_rate}", pf(data_origin.get(1).urban));
//		tr("${urban_village_newspaper_purchase_rate_diff}", data_origin.get(1).urbanMinusVillage>0?"高出":"低出");
//		tr("${village_newspaper_purchase_rate}", pf(data_origin.get(1).village));
//		tr("${urban_village_newspaper_purchase_rate_diff_num}", of(Math.abs(data_origin.get(1).urbanMinusVillage)));
//		
//		tr("${urban_magazine_purchase_rate}", pf(data_origin.get(2).urban));
//		tr("${urban_village_magazine_purchase_rate_diff}", data_origin.get(2).urbanMinusVillage>0?"高出":"低出");
//		tr("${village_magazine_purchase_rate}", pf(data_origin.get(2).village));
//		tr("${urban_village_magazine_purchase_rate_diff_num}", of(Math.abs(data_origin.get(2).urbanMinusVillage)));
//		//20160420
//		tr("${male_newspaper_purchase_rate}", pf(data_origin.get(1).male));
//		tr("${gender_newspaper_purchase_rate_diff}", data_origin.get(1).maleMinusFemale>0?"高出":"低出");
//		tr("${female_newspaper_purchase_rate}", pf(data_origin.get(1).female));
//		tr("${gender_newspaper_purchase_rate_diff_num}", of(Math.abs(data_origin.get(1).maleMinusFemale)));
//		
//		tr("${male_book_purchase_rate}", pf(data_origin.get(0).male));
//		tr("${gender_book_purchase_rate_diff}", data_origin.get(0).maleMinusFemale>0?"高出":"低出");
//		tr("${female_book_purchase_rate}", pf(data_origin.get(0).female));
//		tr("${gender_book_purchase_rate_diff_num}", of(Math.abs(data_origin.get(0).maleMinusFemale)));
//		//20160420
//		tr("${male_cd_purchase_rate}", pf(data_origin.get(4).male));
//		tr("${gender_cd_purchase_rate_diff}", 
//				(data_origin.get(4).male)>(data_origin.get(4).female)?"高出":"低出");
//		tr("${female_cd_purchase_rate}", pf(data_origin.get(4).female));
//		tr("${gender_cd_purchase_rate_diff_num}", 
//				of(Math.abs(data_origin.get(4).male-data_origin.get(4).female)));
		
		data.sort(new Comparator<TableRow>()
		{

			@Override
			public int compare(TableRow o1, TableRow o2)
			{
				if (o1.urbanMinusVillage < o2.urbanMinusVillage)
					return 1;
				else if (o1.urbanMinusVillage > o2.urbanMinusVillage)
					return -1;
				return 0;
			}
		});
		tr("${best_urban_minus_village_purchase_media}", data.get(0).mediaType);
		tr("${best_urban_minus_village_purchase_rate}",
				of(data.get(0).urbanMinusVillage));
		tr("${second_urban_minus_village_purchase_media}",
				data.get(1).mediaType);
		tr("${second_urban_minus_village_purchase_rate}",
				of(data.get(1).urbanMinusVillage));
		tr("${third_urban_minus_village_purchase_media}", data.get(2).mediaType);
		tr("${third_urban_minus_village_purchase_rate}",
				of(data.get(2).urbanMinusVillage));

		data.sort(new Comparator<TableRow>()
		{

			@Override
			public int compare(TableRow o1, TableRow o2)
			{
				if (o1.maleMinusFemale < o2.maleMinusFemale)
					return 1;
				else if (o1.maleMinusFemale > o2.maleMinusFemale)
					return -1;
				return 0;
			}
		});
		tr("${best_male_minus_female_purchase_media}", data.get(0).mediaType);
		tr("${best_male_minus_female_purchase_rate}",
				of(data.get(0).maleMinusFemale));
		data.sort(new Comparator<TableRow>()
		{

			@Override
			public int compare(TableRow o1, TableRow o2)
			{
				if (o1.femaleMinusMale < o2.femaleMinusMale)
					return 1;
				else if (o1.femaleMinusMale > o2.femaleMinusMale)
					return -1;
				return 0;
			}
		});
		tr("${best_female_minus_male_purchase_media}", data.get(0).mediaType);
		tr("${best_female_minus_male_purchase_rate}",
				of(data.get(0).femaleMinusMale));
	}

	@Override
	protected void chart()
	{
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size(); ++i)
		{
			TableRow row = data.get(i);
			XMLFragment xmlFragment = new XMLFragment(
					"Resource/adult/chapter01/media_purchase_rate_row.xml");
			xmlFragment.replace("${media_purchase_type}", row.mediaType);
			xmlFragment.replace("${media_purchase_average}", pf(row.average));
			xmlFragment.replace("${media_purchase_urban}", pf(row.urban));
			xmlFragment.replace("${media_purchase_village}", pf(row.village));
			xmlFragment.replace("${media_purchase_male}", pf(row.male));
			xmlFragment.replace("${media_purchase_female}", pf(row.female));

			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

	private class TableRow
	{
		private String mediaType;
		private double average;
		private double urban;
		private double village;
		private double urbanMinusVillage;
		private double male;
		private double female;
		private double maleMinusFemale;
		private double femaleMinusMale;
	}

}
