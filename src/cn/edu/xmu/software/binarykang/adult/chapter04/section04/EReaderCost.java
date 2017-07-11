package cn.edu.xmu.software.binarykang.adult.chapter04.section04;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>4.4.3�����Ķ����Ķ�����
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class EReaderCost extends AdultBaseAction
{
	private double averageEReaderCost;
	private List<BaseRow> data;

	public EReaderCost(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		averageEReaderCost = SingleValue.read(xlsx, "�����Ķ����Ķ����ѣ�2��");
		BaseRow.read(xlsx, data, "�����Ķ����Ķ����ѣ�1��");
	}

	@Override
	protected void replace()
	{
		tr("${average_ereader_cost}", tf(averageEReaderCost));
		tr("${has_cost_money_on_ereader_rate}",
				pf(1 - data.get(data.size() - 1).value));
		
//		BaseRow.sortExceptLast(data);
		tr("${best_ereader_cost}", pf(data.get(0).value + data.get(1).value + data.get(2).value));
		
		tr("${second_ereader_cost}", pf(data.get(3).value + data.get(4).value));
		
		tr("${third_ereader_cost}", pf(data.get(5).value + data.get(6).value));
		
	}

	@Override
	protected void chart()
	{
		BaseRow.table(docx, data,
//				"Resource/adult/chapter04/ereader_cost_odd_row.xml",
//				"Resource/adult/chapter04/ereader_cost_even_row.xml",
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml",
				GF.p,
				false);
	}

}
