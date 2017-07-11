package cn.edu.xmu.software.binarykang.adult.chapter08.section02;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.TripleValueRow;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>8.2.1读书活动/读书节开展情况
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public final class ReadingActivityDevelopment extends AdultBaseAction
{
	private List<BaseRow> development;
//	private List<TripleValueRow> area;

	public ReadingActivityDevelopment(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		development = ListFactory.getBaseRows();
//		area = ListFactory.getTripleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, development, "居民参加读书活动、读书节情况");

//		int beginRowIndex = xlsx.getRowByKey("各区县是否举办读书活动");
//		XSSFRow areaRow = xlsx.getRow(beginRowIndex + 1);
//		XSSFRow v1 = xlsx.getRow(beginRowIndex + 3);
//		XSSFRow v2 = xlsx.getRow(beginRowIndex + 4);
//		XSSFRow v3 = xlsx.getRow(beginRowIndex + 5);
//		for (int i = BEGIN_COL + 2; i < areaRow.getLastCellNum(); i++)
//		{
//			TripleValueRow tripleValueRow = new TripleValueRow();
//			tripleValueRow.key = xlsx.getContent(areaRow, i);
//			tripleValueRow.v1 = new Double(xlsx.getContent(v1, i));
//			tripleValueRow.v2 = new Double(xlsx.getContent(v2, i));
//			tripleValueRow.v3 = new Double(xlsx.getContent(v3, i));
//			area.add(tripleValueRow);
//		}
	}

	@Override
	protected void replace()
	{
		// 替换居民参加读书活动、读书节情况相关的变量
		tr("${know_has_held_reading_activity}", pf(development.get(0).value));
		tr("${know_has_not_held_reading_activity}",
				pf(development.get(1).value));
		tr("${dont_know_has_held_reading_activity}",
				pf(development.get(2).value));
//		// 替换各区县是否举办读书活动相关的变量
//		TripleValueRow.sortByV1(area);
//		tr("${most_has_held_reading_activity_area}", area.get(0).key);
//		tr("${most_has_held_reading_activity_area_value}", pf(area.get(0).v1));
//
//		TripleValueRow.sortByV2(area);
//		tr("${most_has_not_held_reading_activity_area}", area.get(0).key);
//		tr("${most_has_not_held_reading_activity_area_value}",
//				pf(area.get(0).v2));
//
//		TripleValueRow.sortByV3(area);
//		tr("${most_dont_know_has_held_reading_activity_area}", area.get(0).key);
//		tr("${most_dont_know_has_held_reading_activity_area_value}",
//				pf(area.get(0).v3));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(development);// chart53

//		TripleValueRow.chart(area, "有", "没有", "不知道是否举办过");// chart54
	}

}
