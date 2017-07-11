package cn.edu.xmu.software.binarykang.adult.chapter01.section01;

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
 * 解析1.1.1媒介接触率
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-14
 * 
 */
public final class MediaContactRate extends AdultBaseAction
{
	private final String TRADITION_MEDIA = "报纸图书杂志";
	private List<MediaContactRate.TableRow> data;
	private List<MediaContactRate.TableRow> origin_data;
	private List<MediaContactRate.TableRow> traditionMedia;

	public MediaContactRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = new ArrayList<MediaContactRate.TableRow>();
		traditionMedia = new ArrayList<MediaContactRate.TableRow>();
		origin_data  = new ArrayList<MediaContactRate.TableRow>();
	}

	@Override
	protected void readData()
	{
		int beginRow = xlsx.getRowByKey("媒介接触率");
		XSSFRow row = null;
		try
		{
			for (int i = beginRow + 3; (row = xlsx.getRow(i)).getLastCellNum() != -1; i += 2)
			{
				TableRow tableRow = new TableRow();
				tableRow.key = xlsx.getContent(row, BEGIN_COL);
				tableRow.total = new Double(xlsx.getContent(row, BEGIN_COL + 2));
				tableRow.urban = new Double(xlsx.getContent(row, BEGIN_COL + 3));
				tableRow.village = new Double(xlsx.getContent(row, BEGIN_COL + 4));
				tableRow.urbanMinusVillage = tableRow.urban - tableRow.village;
				tableRow.male = new Double(xlsx.getContent(row, BEGIN_COL + 5));
				tableRow.female = new Double(xlsx.getContent(row, BEGIN_COL + 6));
				tableRow.maleMinusFemale = tableRow.male - tableRow.female;
				tableRow.femaleMinusMale = tableRow.female - tableRow.male;
				data.add(tableRow);
				origin_data.add(tableRow);
				if (TRADITION_MEDIA.contains(tableRow.key))
					traditionMedia.add(tableRow);
			}
			
		}
		catch (Exception e)
		{
			System.out.println("最后一行解析出现了问题");
			e.printStackTrace();
		}
	}

	@Override
	protected void replace()
	{
		//保留data原始顺序
		
		//替换第一段文字中
		data.sort(new Comparator<TableRow>()
		{

			@Override
			public int compare(TableRow o1, TableRow o2)
			{
				if (o1.total < o2.total)
					return 1;
				else if (o1.total > o2.total)
					return -1;
				return 0;
			}
		});
		String bestMedia = data.get(0).key;
		double bestMediaRate = data.get(0).total;
		tr("${best_media}", bestMedia);
		tr("${best_media_contact_rate}", pf(bestMediaRate));

		String secondMedia = data.get(1).key;
		double secondMediaRate = data.get(1).total;
		tr("${second_media}", secondMedia);
		tr("${second_media_contact_rate}", pf(secondMediaRate));
		
		String thirdMedia = data.get(2).key;
		double thirdMediaRate = data.get(2).total;
		tr("${third_media}", thirdMedia);
		tr("${third_media_contact_rate}", pf(thirdMediaRate));
		
		//tr("${best_minus_second}", of(bestMediaRate - secondMediaRate));
		//tr("${five_media}", data.get(4).key);
		//tr("${five_media_contact_rate}", pf(data.get(4).total));
		
		// 替换第二段文字中，关于传统媒介的比较
		traditionMedia.sort(new Comparator<TableRow>()
		{

			@Override
			public int compare(TableRow o1, TableRow o2)
			{
				if (o1.total < o2.total)
					return 1;
				else if (o1.total > o2.total)
					return -1;
				return 0;
			}
		});
		tr("${best_tradition}", traditionMedia.get(0).key);
		tr("${best_tradition_contact_rate}", pf(traditionMedia.get(0).total));
		tr("${second_tradition}", traditionMedia.get(1).key);
		tr("${second_tradition_contact_rate}", pf(traditionMedia.get(1).total));
		tr("${third_tradition}", traditionMedia.get(2).key);
		tr("${third_tradition_contact_rate}", pf(traditionMedia.get(2).total));
		
		// 替换第三段手机阅读接触率 20160420
		tr("${mobile_contact_rate}", pf(origin_data.get(8).total));
		
		// 替换第三段文字中，关于城乡媒介接触率的比较
		data.sort(new Comparator<TableRow>()
		{

			@Override
			public int compare(TableRow o1, TableRow o2)
			{
				if ((o1.urbanMinusVillage) < (o2.urbanMinusVillage))
					return 1;
				else if ((o1.urbanMinusVillage) > (o2.urbanMinusVillage))
					return -1;
				return 0;
			}
//			public int compare(TableRow o1, TableRow o2)
//			{
//				if (Math.abs(o1.urbanMinusVillage) < Math.abs(o2.urbanMinusVillage))
//					return 1;
//				else if (Math.abs(o1.urbanMinusVillage) > Math.abs(o2.urbanMinusVillage))
//					return -1;
//				return 0;
//			}
		});
		
		tr("${max_diff}",data.get(0).key);
		tr("${urban_max_contact_rate}", pf(data.get(0).urban));
		tr("${village_max_contact_rate}", pf(data.get(0).village));
		tr("${max_diff_urban_minus_village}", data.get(0).urbanMinusVillage > 0 ? "高出" : "低出");
		tr("${max_diff_rate}", of(Math.abs(data.get(0).urbanMinusVillage)));
		
		tr("${second_diff}",data.get(1).key);
		tr("${urban_second_contact_rate}", pf(data.get(1).urban));
		tr("${village_second_contact_rate}", pf(data.get(1).village));
		tr("${second_diff_urban_minus_village}", data.get(1).urbanMinusVillage > 0 ? "高出" : "低出");
		tr("${second_diff_rate}", of(Math.abs(data.get(1).urbanMinusVillage)));
		
		tr("${third_diff}",data.get(2).key);
		tr("${urban_third_contact_rate}", pf(data.get(2).urban));
		tr("${village_third_contact_rate}", pf(data.get(2).village));
		tr("${third_diff_urban_minus_village}", data.get(2).urbanMinusVillage > 0 ? "高出" : "低出");
		tr("${third_diff_rate}", of(Math.abs(data.get(2).urbanMinusVillage)));
		
		tr("${forth_diff}",data.get(3).key);
		tr("${urban_forth_contact_rate}", pf(data.get(3).urban));
		tr("${village_forth_contact_rate}", pf(data.get(3).village));
		tr("${forth_diff_urban_minus_village}", data.get(3).urbanMinusVillage > 0 ? "高出" : "低出");
		tr("${forth_diff_rate}", of(Math.abs(data.get(3).urbanMinusVillage)));
		
		tr("${fifth_diff}",data.get(4).key);
		tr("${urban_fifth_contact_rate}", pf(data.get(4).urban));
		tr("${village_fifth_contact_rate}", pf(data.get(4).village));
		tr("${fifth_diff_urban_minus_village}", data.get(4).urbanMinusVillage > 0 ? "高出" : "低出");
		tr("${fifth_diff_rate}", of(Math.abs(data.get(4).urbanMinusVillage)));
		
		tr("${sixth_diff}",data.get(5).key);
		tr("${urban_sixth_contact_rate}", pf(data.get(5).urban));
		tr("${village_sixth_contact_rate}", pf(data.get(5).village));
		tr("${sixth_diff_urban_minus_village}", data.get(5).urbanMinusVillage > 0 ? "高出" : "低出");
		tr("${sixth_diff_rate}", of(Math.abs(data.get(5).urbanMinusVillage)));
		
		tr("${worst_diff}", data.get(data.size()-1).key);
		tr("${village_worst_contact_rate}", pf(data.get(data.size()-1).village));
		tr("${urban_worst_contact_rate}", pf(data.get(data.size()-1).urban));
		tr("${worst_diff_village_minus_urban}", data.get(data.size()-1).urbanMinusVillage < 0? "高出" : "低出");
		tr("${worst_diff_rate}", of(Math.abs(data.get(data.size()-1).urbanMinusVillage)));

		// 替换第四段文字中，女性比男性高的媒介接触率
		data.sort(new Comparator<TableRow>()
		{

			@Override
			public int compare(TableRow o1, TableRow o2)
			{
				if (Math.abs(o1.femaleMinusMale) < Math.abs(o2.femaleMinusMale))
					return 1;
				else if (Math.abs(o1.femaleMinusMale) > Math.abs(o2.femaleMinusMale))
					return -1;
				return 0;
			}
		});
		tr("${gender_best_diff_media}", data.get(0).key);
		tr("${gender_second_diff_media}", data.get(1).key);
		tr("${gender_third_diff_media}", data.get(2).key);
		tr("${male_best_media}", pf(data.get(0).male));
		tr("${gender_best_diff_media_male_minus_female}", data.get(0).maleMinusFemale>0?"高出":"低出");
		tr("${female_best_media_rate}", pf(data.get(0).female));
		tr("${gender_best_diff_media_male_minus_female_rate}", of(Math.abs(data.get(0).maleMinusFemale)));
		tr("${male_second_media}", pf(data.get(1).male));
		tr("${gender_second_diff_media_male_minus_female}", data.get(1).maleMinusFemale>0?"高出":"低出");
		tr("${female_second_media_rate}", pf(data.get(1).female));
		tr("${gender_second_diff_media_male_minus_female_rate}", of(Math.abs(data.get(1).maleMinusFemale)));
		tr("${male_third_media_rate}", pf(data.get(2).male));
		tr("${female_third_media_rate}", pf(data.get(2).female));
		tr("${gender_third_diff_media_male_minus_female}", data.get(2).maleMinusFemale>0?"高出":"低出");
		tr("${gender_third_diff_media_male_minus_female_rate}", of(Math.abs(data.get(2).maleMinusFemale)));
		
		//tr("${female_best_media}", data.get(0).key);
		//tr("${female_second_media}", data.get(1).key);
		//tr("${female_best_media_rate}", of(data.get(0).femaleMinusMale));
		//tr("${female_second_media_rate}", of(data.get(1).femaleMinusMale));

		// 替换第四段文字中，男性比女性高的媒介接触率
		/*
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
		tr("${male_best_media}", data.get(0).key);
		tr("${male_best_media_male_rate}",of(data.get(0).male));
		tr("${male_best_media_female_rate}",of(data.get(0).female));
		tr("${male_best_media_rate}", of(data.get(0).maleMinusFemale));
		
		tr("${male_second_media}", data.get(1).key);
		tr("${male_third_media}", data.get(2).key);
		
		tr("${male_second_media_rate}", of(data.get(1).maleMinusFemale));
		tr("${male_third_media_rate}", of(data.get(2).maleMinusFemale));
		*/
		
	}

	@Override
	protected void chart()
	{
		data.sort(new Comparator<TableRow>()
		{

			@Override
			public int compare(TableRow o1, TableRow o2)
			{
				if (o1.total < o2.total)
					return 1;
				else if (o1.total > o2.total)
					return -1;
				return 0;
			}
		});
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size(); ++i)
		{
			TableRow row = data.get(i);
			XMLFragment xmlFragment = new XMLFragment("Resource/adult/chapter01/media_contact_rate_row.xml");
			xmlFragment.replace("${media_type}", row.key);
			xmlFragment.replace("${media_average}", pf(row.total));
			xmlFragment.replace("${media_urban}", pf(row.urban));
			xmlFragment.replace("${media_village}", pf(row.village));
			xmlFragment.replace("${media_male}", pf(row.male));
			xmlFragment.replace("${media_female}", pf(row.female));

			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

	private class TableRow
	{
		public String key;
		public double total;
		public double urban;
		public double village;
		public double urbanMinusVillage;
		public double male;
		public double female;
		public double maleMinusFemale;
		public double femaleMinusMale;

		@Override
		public String toString()
		{
			return "key:" + key + ";total:" + total + ";urban:" + urban + ";village:" + village + ";male:" + male
					+ ";female:" + female;
		}
	}

}
