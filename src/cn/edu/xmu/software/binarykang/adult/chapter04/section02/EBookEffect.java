package cn.edu.xmu.software.binarykang.adult.chapter04.section02;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.2.2电子书报刊对传统纸质书报刊销售的影响
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class EBookEffect extends AdultBaseAction
{
	private List<DoubleValueRow> data;

	public EBookEffect(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		int rowIndex = xlsx.getRowByKey("电子书报刊对传统纸质书报刊销售的影响");
		XSSFRow firstRow = null;
		XSSFRow secondRow = null;
		for (int i = rowIndex + 1; (firstRow = xlsx.getRow(i)).getLastCellNum() != -1; i += 2)
		{
			secondRow = xlsx.getRow(i + 1);
			DoubleValueRow uvRow = new DoubleValueRow();
			uvRow.key = xlsx.getContent(firstRow, BEGIN_COL);
			uvRow.v1 = new Double(xlsx.getContent(firstRow, BEGIN_COL + 2));
			uvRow.v2 = new Double(xlsx.getContent(secondRow, BEGIN_COL + 2));
			data.add(uvRow);
		}
	}

	@Override
	protected void replace()
	{
		tr("${after_read_ebook_will_buy_rate}", pf(data.get(0).v2));
//		tr("${after_read_ebook_will_not_buy_rate}", pf(data.get(0).v1));
		tr("${after_read_emagazine_will_buy_rate}", pf(data.get(1).v2));
//		tr("${after_read_enewspaper_will_buy_rate}", pf(data.get(2).v2));
	}

	@Override
	protected void chart()
	{
		DoubleValueRow.chart(data, "更倾向于购买电子版", "更倾向于购买纸质版");// chart22
	}

}
