package cn.edu.xmu.software.binarykang.adult.chapter01.section03;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析1.3成年人使用各种阅读载体的场所
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-14
 *
 */
public final class ReadMediaPlace extends AdultBaseAction
{
	private final String BOOK_READING_TABLE_KEY = "各种阅读载体的使用场所——图书阅读";
	private final String MAGAZINE_READING_TABLE_KEY = "各种阅读载体的使用场所——杂志阅读";
	private final String NEWSPAPER_READING_TABLE_KEY = "各种阅读载体的使用场所——报纸阅读";
	private final String INTERNET_READING_TABLE_KEY = "各种阅读载体的使用场所——上网阅读";
	private final String TELEPHONE_READING_TABLE_KEY = "各种阅读载体的使用场所——手机阅读（小说/新闻）";
	private final String MP_READING_TABLE_KEY = "各种阅读载体的使用场所——Pad（平板电脑）";
	private final String ELECTRONIC_READING_TABLE_KEY = "各种阅读载体的使用场所——电子阅读器（电子书/电纸书）";
	private List<ReadMediaPlace.TableRow> data;

	public ReadMediaPlace(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = new ArrayList<ReadMediaPlace.TableRow>();
	}

	@Override
	protected void readData()
	{
		int bookRowIndex = xlsx.getRowByKey(BOOK_READING_TABLE_KEY);
		int magazineRowIndex = xlsx.getRowByKey(MAGAZINE_READING_TABLE_KEY);
		int newspaperRowIndex = xlsx.getRowByKey(NEWSPAPER_READING_TABLE_KEY);
		int internetRowIndex = xlsx.getRowByKey(INTERNET_READING_TABLE_KEY);
		int telephoneRowIndex = xlsx.getRowByKey(TELEPHONE_READING_TABLE_KEY);
		int mpRowIndex = xlsx.getRowByKey(MP_READING_TABLE_KEY);
		int electronicRowIndex = xlsx.getRowByKey(ELECTRONIC_READING_TABLE_KEY);

		XSSFRow bookRow = null;

		for (int i = 1; (bookRow = xlsx.getRow(bookRowIndex + i))
				.getLastCellNum() != -1; ++i)
		{
			TableRow tableRow = new TableRow();
			tableRow.place = xlsx.getContent(bookRow, BEGIN_COL + 1);
			tableRow.book = new Double(xlsx.getContent(bookRow, BEGIN_COL + 2));
			tableRow.magazine = new Double(xlsx.getContent(
					xlsx.getRow(magazineRowIndex + i), BEGIN_COL + 2));
			tableRow.newspaper = new Double(xlsx.getContent(
					xlsx.getRow(newspaperRowIndex + i), BEGIN_COL + 2));
			tableRow.internet = new Double(xlsx.getContent(
					xlsx.getRow(internetRowIndex + i), BEGIN_COL + 2));
			tableRow.telephone = new Double(xlsx.getContent(
					xlsx.getRow(telephoneRowIndex + i), BEGIN_COL + 2));
			tableRow.mp = new Double(xlsx.getContent(
					xlsx.getRow(mpRowIndex + i), BEGIN_COL + 2));
			tableRow.electronic = new Double(xlsx.getContent(
					xlsx.getRow(electronicRowIndex + i), BEGIN_COL + 2));
			data.add(tableRow);
		}
	}

	@Override
	protected void replace()
	{
		//20160420
		TableRow home = getRowByKey("家中");
		tr("${home_best_media_value}", pf(home.book));
		tr("${home_best_media_key}", "图书阅读");
		tr("${home_second_media_value}", pf(home.magazine));
		tr("${home_second_media_key}", "杂志阅读");
		tr("${home_third_media_value}", pf(home.newspaper));
		tr("${home_third_media_key}", "报纸阅读");
		tr("${home_fourth_media_value}", pf(home.internet));
		tr("${home_fourth_media_key}", "上网阅读");
		tr("${home_fifth_media_value}", pf(home.telephone));
		tr("${home_fifth_media_key}", "手机阅读");
		tr("${home_sixth_media_value}", pf(home.mp));
		tr("${home_sixth_media_key}", "Pad（平板电脑）");
		tr("${home_seventh_media_value}", pf(home.electronic));
		tr("${home_seventh_media_key}", "电子阅读器（电子书/电纸书）");
		//20160420
		TableRow school = getRowByKey("学校或单位");
		tr("${school_best_media_value}", pf(school.book));
		tr("${school_best_media_key}", "图书阅读");
		tr("${school_second_media_value}", pf(school.magazine));
		tr("${school_second_media_key}","杂志阅读");
		tr("${school_third_media_value}", pf(school.newspaper));
		tr("${school_third_media_key}", "报纸阅读");
		tr("${school_fourth_media_value}", pf(school.internet));
		tr("${school_fourth_media_key}", "上网阅读");
		tr("${school_fifth_media_key}", "手机阅读");
		tr("${school_sixth_media_key}", "Pad（平板电脑）");
		tr("${school_seventh_media_key}", "电子阅读器（电子书/电纸书）");
		tr("${school_fifth_media_value}", pf(school.telephone));
		tr("${school_sixth_media_value}", pf(school.mp));
		tr("${school_seventh_media_value}", pf(school.electronic));
		//20160420
		TableRow bus = getRowByKey("乘交通工具时");
		tr("${bus_electronic_value}", pf(bus.electronic));
		tr("${bus_telephone_value}", pf(bus.telephone));
		TableRow bookstore = getRowByKey("书店");
		tr("${bookstore_book_value}", pf(bookstore.book));
		TableRow library = getRowByKey("图书馆");
		tr("${library_book_value}", pf(library.book));
		
//		TableRow vehicle = getRowByKey("乘交通工具时间");
//		tr("${telephone_reading_in_vehicle}", pf(vehicle.telephone));
//		tr("${mp_reading_in_vehicle}", pf(vehicle.mp));
	}

	private TableRow getRowByKey(String key)
	{
		Iterator<TableRow> iterator = data.iterator();
		while (iterator.hasNext())
		{
			TableRow tableRow = iterator.next();
			if (tableRow.place.equals(key))
				return tableRow;
		}
		return null;
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
					"Resource/adult/chapter01/media_reading_place_row.xml");
			xmlFragment.replace("${media_reading_place}", row.place);
			xmlFragment.replace("${book_reading_percent}", pf(row.book));
			xmlFragment
					.replace("${magazine_reading_percent}", pf(row.magazine));
			xmlFragment.replace("${newspaper_reading_percent}",
					pf(row.newspaper));
			xmlFragment
					.replace("${internet_reading_percent}", pf(row.internet));
			xmlFragment.replace("${telephone_reading_percent}",
					pf(row.telephone));
			xmlFragment.replace("${mp_reading_percent}", pf(row.mp));
			xmlFragment.replace("${electronic_reading_percent}",
					pf(row.electronic));

			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

	private class TableRow
	{
		private String place;
		private double book;
		private double magazine;
		private double newspaper;
		private double internet;
		private double telephone;
		private double mp;
		private double electronic;
	}

}
