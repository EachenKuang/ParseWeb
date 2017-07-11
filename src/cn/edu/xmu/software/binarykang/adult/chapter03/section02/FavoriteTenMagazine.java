package cn.edu.xmu.software.binarykang.adult.chapter03.section02;

import java.util.List;

import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>3.2.4读者喜爱的期刊
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class FavoriteTenMagazine extends AdultBaseAction
{
	private List<BaseRow> data;

	public FavoriteTenMagazine(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "期刊读者最喜爱的期刊排名");
	}

	@Override
	protected void replace()
	{
		tr("${best_favorite_ten_magazine}", data.get(0).key);
		tr("${second_favorite_ten_magazine}", data.get(1).key);
		tr("${third_favorite_ten_magazine}", data.get(2).key);
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size() && i < 10; ++i)
		{
			BaseRow row = data.get(i);
			XMLFragment xmlFragment = new XMLFragment(
					"Resource/adult/chapter03/favorite_ten_magazine_even_row.xml");
//			if (i % 2 == 0)
//			{
//				xmlFragment = new XMLFragment(
//						"Resource/adult/chapter03/favorite_ten_magazine_odd_row.xml");
//			}
//			else
//			{
//				xmlFragment = new XMLFragment(
//						"Resource/adult/chapter03/favorite_ten_magazine_even_row.xml");
//			}

			xmlFragment.replace("${key}", row.key);
			xmlFragment.replace("${index}", i + 1 + "");
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

}
