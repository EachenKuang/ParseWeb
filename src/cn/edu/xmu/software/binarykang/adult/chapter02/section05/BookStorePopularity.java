package cn.edu.xmu.software.binarykang.adult.chapter02.section05;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>2.5.1书店知名度
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookStorePopularity extends AdultBaseAction
{
	private List<BaseRow> data;

	public BookStorePopularity(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "书店的知名度排名");
	}

	@Override
	protected void replace()
	{
		BaseRow.sort(data);
		tr("${best_bookstore_popularity_key}", data.get(0).key);
		tr("${best_bookstore_popularity_value}", pf(data.get(0).value));
		tr("${second_bookstore_popularity_key}", data.get(1).key);
		tr("${second_bookstore_popularity_value}", pf(data.get(1).value));
		tr("${third_bookstore_popularity_key}", data.get(2).key);
		tr("${third_bookstore_popularity_value}", pf(data.get(2).value));
		tr("${fourth_bookstore_popularity_key}", data.get(3).key);
		tr("${fourth_bookstore_popularity_value}", pf(data.get(3).value));
		tr("${fifth_bookstore_popularity_key}", data.get(3).key);
		tr("${fifth_bookstore_popularity_value}", pf(data.get(3).value));
		tr("${local_bookstore_popularity_value}", pf(BaseRow.getRowByKey("本地大型书店、图书大厦、图书城等", data).value));
		tr("${local_bookstore_popularity_rank}", tf(data.indexOf(BaseRow.getRowByKey("本地大型书店、图书大厦、图书城等", data))));
		tr("${sh_bookstore_popularity_value}", pf(BaseRow.getRowByKey("上海书城", data).value));
		tr("${sh_bookstore_popularity_rank}", tf(data.indexOf(BaseRow.getRowByKey("上海书城", data))));
		for(int i = 0; i<=data.size()-1; i++)
		{
			if(data.get(i).key == "本地大型书店、图书大厦、图书城等")
				tr("${local_bookstore_popularity_rank}", tf(i+1));
			if(data.get(i).key == "上海书城")
				tr("${sh_bookstore_popularity_rank}", tf(i+1));
		}
	}

	@Override
	protected void chart()
	{
		BaseRow.sort(data);
		BaseRow.tableWithIndex(docx, data,
				"Resource/adult/chapter02/bookstore_popularity_even_row.xml",
				"Resource/adult/chapter02/bookstore_popularity_even_row.xml");
	}
}
