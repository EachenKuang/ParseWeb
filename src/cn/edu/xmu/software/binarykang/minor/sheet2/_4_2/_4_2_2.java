package cn.edu.xmu.software.binarykang.minor.sheet2._4_2;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_2_2 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁电子书对传统纸质图书销售的影响";
	private List<DataMap> tableInfo;
	private List<DataMap> ebook,staticbook;
	
//	private List<DoubleValueRow> data; //new
	public _4_2_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = MinorUtil.listMapFactory();
		ebook= MinorUtil.listMapFactory();
		staticbook = MinorUtil.listMapFactory();
		
//		data = ListFactory.getDoubleValueRows();//new
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		
//		int rowIndex = xlsx.getRowByKey(TABLE_KEY);
//		XSSFRow firstRow = null;
//		XSSFRow secondRow = null;
//		for (int i = rowIndex + 1; (firstRow = xlsx.getRow(i)).getLastCellNum() != -1; i += 2)
//		{
//			secondRow = xlsx.getRow(i + 1);
//			DoubleValueRow uvRow = new DoubleValueRow();
//			uvRow.key = xlsx.getContent(firstRow, BEGIN_COL);
//			uvRow.v1 = new Double(xlsx.getContent(firstRow, BEGIN_COL + 2));
//			uvRow.v2 = new Double(xlsx.getContent(secondRow, BEGIN_COL + 2));
//			data.add(uvRow);
//		}
	}

	@Override
	protected void replace()
	{
		//tr("${_4_2_2_not_buy_rate}", perf.format(tableInfo.get(1).getRate()));
		//tr("${_4_2_2_buy_rate}", perf.format(tableInfo.get(0).getRate()));
		tr("${_4_2_3_ebook_rate}", perf.format(tableInfo.get(0).getRate()));
		tr("${_4_2_3_emagazine_rate}", perf.format(tableInfo.get(2).getRate()));
	}

	@Override
	protected void chart()
	{
//		DoubleValueRow.chart(data, "更倾向于购买电子版", "更倾向于购买纸质版");
		ebook.add(tableInfo.get(0));
		ebook.add(tableInfo.get(2));
		staticbook.add(tableInfo.get(1));
		staticbook.add(tableInfo.get(3));
		MinorUtil.changeChart(3,ebook,staticbook);
	}
}
