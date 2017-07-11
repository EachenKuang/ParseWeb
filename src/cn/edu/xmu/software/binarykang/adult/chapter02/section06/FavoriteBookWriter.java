package cn.edu.xmu.software.binarykang.adult.chapter02.section06;

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
 * 解析=>2.6 ${city}居民最喜欢的图书作者
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class FavoriteBookWriter extends AdultBaseAction
{
	private final String FAVORITE_BOOK_WRITER_TABLE_KEY = "居民最喜爱的图书作者排名";

	private List<BaseRow> localData;
	private List<Integer> countryData;

	public FavoriteBookWriter(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		localData = ListFactory.getBaseRows();
		countryData = ListFactory.getIntegerList();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, localData, FAVORITE_BOOK_WRITER_TABLE_KEY);

		XSSFRow row = null;
		for (int i = xlsx.getRowByKey(FAVORITE_BOOK_WRITER_TABLE_KEY) + 1, j = 0; (row = xlsx
				.getRow(i)).getLastCellNum() != -1 && j < 10; ++i, ++j)
		{
			String temp = xlsx.getContent(row, BEGIN_COL + 5);
//			countryData.add(new Integer(temp.substring(0, temp.length() - 2)));
//			countryData.add(new Integer(temp));
			countryData.add((int)(Double.parseDouble(temp)));
		}
	}

	@Override
	protected void replace()
	{
		BaseRow.sortExceptLast(localData);
		tr("${best_favorite_book_writer}", localData.get(0).key);
		tr("${second_favorite_book_writer}", localData.get(1).key);
		tr("${third_favorite_book_writer}", localData.get(2).key);
		tr("${second_country_favorite_book_writer}", countryData.get(1) + "");
		String localDiffFavoriteBookWriter = "";
		String countryDiffFavoriteBookWriter = "";
		boolean flag = true;
		for(int i = 0; i<=9; i++)
		{
			flag = true;
			for(int j=0; j<=9; j++)
			{
//				if((int)Double.parseDouble(localData.get(i).key) == countryData.get(j))
				if((int)Double.parseDouble(localData.get(i).key) == countryData.get(j))
				{
					flag = false;
					break;
				}
			}
			if(flag)
				localDiffFavoriteBookWriter += (localData.get(i).key+"、");
			flag = true;
			for(int j = 0; j<=9; j=j+1)
			{
				if((int)Double.parseDouble(localData.get(j).key) == countryData.get(i))
				{
					flag = false;
					break;
				}
			}
			if(flag)
				countryDiffFavoriteBookWriter += (countryData.get(i).toString()+"、");
		}
		tr("${local_diff_favorite_book_writer}", localDiffFavoriteBookWriter.substring(0, localDiffFavoriteBookWriter.length()-1));
		tr("${country_diff_favorite_book_writer}", countryDiffFavoriteBookWriter.substring(0, countryDiffFavoriteBookWriter.length()-1));
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
			XMLFragment xmlFragment = null;
			xmlFragment = new XMLFragment(
				"Resource/adult/chapter02/favorite_book_writer_even_row.xml");
//			if (i % 2 == 0)
//			{
//				xmlFragment = new XMLFragment(
//						"Resource/adult/chapter02/favorite_book_writer_even_row.xml");
////						"Resource/adult/chapter02/compare_book_reading_source_row.xml");
//			}
//			else
//			{
//				xmlFragment = new XMLFragment(
//						"Resource/adult/chapter02/favorite_book_writer_even_row.xml");
////						"Resource/adult/chapter02/compare_book_reading_source_row.xml");
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
