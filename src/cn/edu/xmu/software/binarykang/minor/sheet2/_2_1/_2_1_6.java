package cn.edu.xmu.software.binarykang.minor.sheet2._2_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_1_6 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "2.1.6家庭藏书量 第一段文字中的家庭平均藏书量（当地）";
	private final static String TABLE_KEY_OTHER = "2.1.6家庭藏书量 第一段文字中的家庭平均藏书量（全国）";
	private final static String TABLE_KEY_BOOK = "14-17岁家庭藏书量";
	private List<DataMap> tableBook;
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;

	public _2_1_6(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableBook = new ArrayList<DataMap>();
		tableLocal = new ArrayList<DataMap>();
		tableOther = new ArrayList<DataMap>();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_BOOK, tableBook, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther, BEGIN_COL);
	}

	@Override
	protected void replace()
	{
		docx.replace("${_2_1_6_local_book}",
				twof.format(tableLocal.get(0).getRate()));
		docx.replace("${_2_1_6_other_book}",
				twof.format(tableOther.get(0).getRate()));
		Double localCutOther = tableLocal.get(0).getRate()
				- tableOther.get(0).getRate();
		String judge = localCutOther > 0 ? "多" : "少";
		docx.replace("${_2_1_6_judge}", judge);
		docx.replace("${_2_1_6_local_other}",
				twof.format(Math.abs(localCutOther)));
		String other = MinorUtil.other;
		docx.replace("${_2_1_6_other}", other);

		docx.replace("${_2_1_6_bookNum_0}", tableBook.get(0).getKey());
		docx.replace("${_2_1_6_bookNum_0_rate}",
				perf.format(tableBook.get(0).getRate()));
		docx.replace("${_2_1_6_bookNum_1}", tableBook.get(1).getKey());
		docx.replace("${_2_1_6_bookNum_1_rate}",
				perf.format(tableBook.get(1).getRate()));
		docx.replace("${_2_1_6_bookNum_2}", tableBook.get(2).getKey());
		docx.replace("${_2_1_6_bookNum_2_rate}",
				perf.format(tableBook.get(2).getRate()));

		docx.replace(
				"${_2_1_6_bookNum_0_1_2_rate}",
				perf.format(tableBook.get(0).getRate()
						+ tableBook.get(1).getRate()
						+ tableBook.get(2).getRate()));
		double more100 = 0;
		for (int i = 3; i < tableBook.size() - 1; i++)
			more100 += tableBook.get(i).getRate();
		docx.replace("${_2_1_6_bookNum_more_100_rate}", perf.format(more100));
		docx.replace("${_2_1_6_bookNum_none_rate}",
				perf.format(tableBook.get(tableBook.size() - 1).getRate()));

	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableBook);
		MinorUtil.changeChart(tableBook);
	}

}
