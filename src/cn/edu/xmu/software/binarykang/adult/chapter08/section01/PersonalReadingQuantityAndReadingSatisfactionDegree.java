package cn.edu.xmu.software.binarykang.adult.chapter08.section01;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>8.1.3个人阅读量与阅读满意度
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public final class PersonalReadingQuantityAndReadingSatisfactionDegree extends
		AdultBaseAction
{
	private List<BaseRow> data;

	public PersonalReadingQuantityAndReadingSatisfactionDegree(Docx docx,
			Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "居民阅读满意度");
	}

	@Override
	protected void replace()
	{
		tr("${best_personal_reading_quantity_and_reading_satisfaction_degree_value}",
				pf(data.get(0).value + data.get(1).value));
		tr("${second_personal_reading_quantity_and_reading_satisfaction_degree_value}",
				pf(data.get(2).value));
		tr("${third_personal_reading_quantity_and_reading_satisfaction_degree_value}",
				pf(data.get(3).value + data.get(4).value));
//		tr("${fourth_personal_reading_quantity_and_reading_satisfaction_degree_value}",
//				pf(data.get(4).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart52
	}

}
