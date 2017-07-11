package cn.edu.xmu.software.binarykang.adult.chapter02.section07;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>2.7 ${city}居民最喜欢的十本书
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class FavoriteTenBook extends AdultBaseAction
{
	private final String FAVORITE_TEN_BOOK_TABLE_KEY = "最喜爱的十本书";

	private List<BaseRow> localData;
	private List<Integer> countryData;

	public FavoriteTenBook(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		localData = ListFactory.getBaseRows();
		countryData = ListFactory.getIntegerList();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, localData, FAVORITE_TEN_BOOK_TABLE_KEY);

		XSSFRow row = null;
		for (int i = xlsx.getRowByKey(FAVORITE_TEN_BOOK_TABLE_KEY) + 1, j = 0; (row = xlsx
				.getRow(i)).getLastCellNum() != -1 && j < 10; ++i, ++j)
		{
			String temp = xlsx.getContent(row, BEGIN_COL + 5);
//			countryData.add(new Integer(temp.substring(0, temp.length() - 2)));//test
			countryData.add((int)(Double.parseDouble(temp)));
//			countryData.add(new Integer(temp));
			
		}
	}

	@Override
	protected void replace()
	{
		BaseRow.sortExceptLast(localData);
		tr("${best_favorite_book}", localData.get(0).key);
		tr("${second_favorite_book}", localData.get(1).key);
		tr("${third_favorite_book}", localData.get(2).key);
		tr("${fourth_favorite_book}", localData.get(3).key);
		String localDiffFavoriteBooks = "";
		String countryDiffFavoriteBooks = "";
		for(int i = 0; i<=9; i++)
		{
			if(!countryData.contains((int)Double.parseDouble(localData.get(i).key)))
				localDiffFavoriteBooks += (localData.get(i).key+"、");
			boolean flag = true;
			for(int j = 0; j<=9; j++)
			{
				if((int)Double.parseDouble(localData.get(j).key) == countryData.get(i))
				{
					flag = false;
					break;
				}
			}
			if(flag)
				countryDiffFavoriteBooks += (countryData.get(i).toString()+"、");
		}
		tr("${local_diff_favorite_books}", localDiffFavoriteBooks.substring(0, localDiffFavoriteBooks.length()-1));
		tr("${country_diff_favorite_books}", countryDiffFavoriteBooks.substring(0, countryDiffFavoriteBooks.length()-1));
	}

	@Override
	protected void chart()
	{
		// 特殊表格，数量很少，没有必要进行统一
		BaseRow.sortExceptLast(localData);
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < countryData.size() && i < 10; ++i)
		{
			BaseRow row = localData.get(i);
			XMLFragment xmlFragment = new XMLFragment(
		"Resource/adult/chapter02/favorite_book_writer_even_row.xml");
//			if (i % 2 == 0)
//			{
//				xmlFragment = new XMLFragment(
//						"Resource/adult/chapter02/favorite_ten_book_odd_row.xml");
//			}
//			else
//			{
//				xmlFragment = new XMLFragment(
//						"Resource/adult/chapter02/favorite_ten_book_even_row.xml");
//			}

			xmlFragment.replace("${local}", row.key);
			xmlFragment.replace("${country}", countryData.get(i) + "");
			xmlFragment.replace("${index}", i + 1 + "");
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

}
