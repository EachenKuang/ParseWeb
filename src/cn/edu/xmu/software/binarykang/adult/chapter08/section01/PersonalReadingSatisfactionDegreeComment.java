package cn.edu.xmu.software.binarykang.adult.chapter08.section01;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>8.1.2个人阅读满意度评价
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public final class PersonalReadingSatisfactionDegreeComment extends
		AdultBaseAction
{
	private List<BaseRow> data;
//	private List<DoubleValueRow> mfData;

	public PersonalReadingSatisfactionDegreeComment(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
//		mfData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		Collections.reverse(data);
		BaseRow.read(xlsx, data, "居民阅读量变化");
		Collections.reverse(data);
//		DoubleValueRow.read(xlsx, mfData, "居民阅读量变化的性别差异");
	}

	@Override
	protected void replace()
	{
		// 替换居民阅读量变化相关的变量
		tr("${add_digit_reading_quantity_value}", pf(data.get(0).value));
		tr("${add_paper_content_reading_quantity_value}", pf(data.get(1).value));
		tr("${no_change_in_reading_quantity_value}", pf(data.get(2).value));
		tr("${reduce_digit_reading_quantity_value}", pf(data.get(3).value));
		tr("${reduce_paper_content_reading_quantity_value}",
				pf(data.get(4).value));
		// 替换居民阅读量变化的性别差异相关的变量
//		DoubleValueRow noChangeRow = mfData.get(2);
//		tr("${male_no_change_in_reading_quantity_value}", pf(noChangeRow.v1));
//		tr("${male_minus_female_no_change_in_reading_quantity_key1}",
//				noChangeRow.v1 > noChangeRow.v2 ? "多" : "少");
//		tr("${male_minus_female_no_change_in_reading_quantity_key2}",
//				noChangeRow.v1 > noChangeRow.v2 ? "高" : "低");
//		tr("${male_minus_female_no_change_in_reading_quantity_value}",
//				of(Math.abs(noChangeRow.v1 - noChangeRow.v2)));
//
//		DoubleValueRow reducePaperContentRow = mfData.get(4);
//		boolean result = reducePaperContentRow.v1 > reducePaperContentRow.v2;
//		tr("${best_gender_reduce_paper_content_reading_quantity_key}",
//				result ? "男" : "女");
//		tr("${best_gender_reduce_paper_content_reading_quantity_value}",
//				pf(result ? reducePaperContentRow.v1 : reducePaperContentRow.v2));
//		tr("${worst_gender_reduce_paper_content_reading_quantity_key}",
//				result ? "女" : "男");
//		tr("${best_minus_worst_gender_reduce_paper_content_reading_quantity_value}",
//				of(Math.abs(reducePaperContentRow.v1 - reducePaperContentRow.v2)));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart50

//		DoubleValueRow.chart(mfData, "男", "女");// chart51
	}

}
