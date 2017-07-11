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
 * ����1.3������ʹ�ø����Ķ�����ĳ���
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-14
 *
 */
public final class ReadMediaPlace extends AdultBaseAction
{
	private final String BOOK_READING_TABLE_KEY = "�����Ķ������ʹ�ó�������ͼ���Ķ�";
	private final String MAGAZINE_READING_TABLE_KEY = "�����Ķ������ʹ�ó���������־�Ķ�";
	private final String NEWSPAPER_READING_TABLE_KEY = "�����Ķ������ʹ�ó���������ֽ�Ķ�";
	private final String INTERNET_READING_TABLE_KEY = "�����Ķ������ʹ�ó������������Ķ�";
	private final String TELEPHONE_READING_TABLE_KEY = "�����Ķ������ʹ�ó��������ֻ��Ķ���С˵/���ţ�";
	private final String MP_READING_TABLE_KEY = "�����Ķ������ʹ�ó�������Pad��ƽ����ԣ�";
	private final String ELECTRONIC_READING_TABLE_KEY = "�����Ķ������ʹ�ó������������Ķ�����������/��ֽ�飩";
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
		TableRow home = getRowByKey("����");
		tr("${home_best_media_value}", pf(home.book));
		tr("${home_best_media_key}", "ͼ���Ķ�");
		tr("${home_second_media_value}", pf(home.magazine));
		tr("${home_second_media_key}", "��־�Ķ�");
		tr("${home_third_media_value}", pf(home.newspaper));
		tr("${home_third_media_key}", "��ֽ�Ķ�");
		tr("${home_fourth_media_value}", pf(home.internet));
		tr("${home_fourth_media_key}", "�����Ķ�");
		tr("${home_fifth_media_value}", pf(home.telephone));
		tr("${home_fifth_media_key}", "�ֻ��Ķ�");
		tr("${home_sixth_media_value}", pf(home.mp));
		tr("${home_sixth_media_key}", "Pad��ƽ����ԣ�");
		tr("${home_seventh_media_value}", pf(home.electronic));
		tr("${home_seventh_media_key}", "�����Ķ�����������/��ֽ�飩");
		//20160420
		TableRow school = getRowByKey("ѧУ��λ");
		tr("${school_best_media_value}", pf(school.book));
		tr("${school_best_media_key}", "ͼ���Ķ�");
		tr("${school_second_media_value}", pf(school.magazine));
		tr("${school_second_media_key}","��־�Ķ�");
		tr("${school_third_media_value}", pf(school.newspaper));
		tr("${school_third_media_key}", "��ֽ�Ķ�");
		tr("${school_fourth_media_value}", pf(school.internet));
		tr("${school_fourth_media_key}", "�����Ķ�");
		tr("${school_fifth_media_key}", "�ֻ��Ķ�");
		tr("${school_sixth_media_key}", "Pad��ƽ����ԣ�");
		tr("${school_seventh_media_key}", "�����Ķ�����������/��ֽ�飩");
		tr("${school_fifth_media_value}", pf(school.telephone));
		tr("${school_sixth_media_value}", pf(school.mp));
		tr("${school_seventh_media_value}", pf(school.electronic));
		//20160420
		TableRow bus = getRowByKey("�˽�ͨ����ʱ");
		tr("${bus_electronic_value}", pf(bus.electronic));
		tr("${bus_telephone_value}", pf(bus.telephone));
		TableRow bookstore = getRowByKey("���");
		tr("${bookstore_book_value}", pf(bookstore.book));
		TableRow library = getRowByKey("ͼ���");
		tr("${library_book_value}", pf(library.book));
		
//		TableRow vehicle = getRowByKey("�˽�ͨ����ʱ��");
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
