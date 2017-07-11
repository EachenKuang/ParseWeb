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
 * 解析=>8.1.1个人阅读量评价
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public final class PersonalReadingQuantityComment extends AdultBaseAction
{
	private List<BaseRow> personalReadingQuantityComment;
//	private List<DoubleValueRow> uvReadingQuantityComment;

	public PersonalReadingQuantityComment(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		personalReadingQuantityComment = ListFactory.getBaseRows();
//		uvReadingQuantityComment = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, personalReadingQuantityComment, "居民阅读数量评价");
//		DoubleValueRow.read(xlsx, uvReadingQuantityComment, "城乡居民阅读数量评价对比");
	}

	@Override
	protected void replace()
	{
		// 替换居民阅读数量评价相关的变量
		tr("${best_personal_reading_quantity_comment_value}",
				pf(personalReadingQuantityComment.get(0).value));
		tr("${second_personal_reading_quantity_comment_value}",
				pf(personalReadingQuantityComment.get(1).value));
		tr("${third_personal_reading_quantity_comment_value}",
				pf(personalReadingQuantityComment.get(2).value));
		tr("${forth_personal_reading_quantity_comment_value}",
				pf(personalReadingQuantityComment.get(3).value
						+ personalReadingQuantityComment.get(4).value));
		tr("${fifth_personal_reading_quantity_comment_value}",
				pf(personalReadingQuantityComment.get(5).value));
		// 替换城乡居民阅读数量评价对比相关的变量
//		tr("${urban_best_personal_reading_quantity_comment_value}",
//				pf(uvReadingQuantityComment.get(2).v1));
//		tr("${village_best_personal_reading_quantity_comment_value}",
//				pf(uvReadingQuantityComment.get(2).v2));
//		tr("${urban_second_personal_reading_quantity_comment_value}",
//				pf(uvReadingQuantityComment.get(1).v1));
//		tr("${village_second_personal_reading_quantity_comment_value}",
//				pf(uvReadingQuantityComment.get(1).v2));
//		tr("${urban_third_personal_reading_quantity_comment_value}",
//				pf(uvReadingQuantityComment.get(0).v1));
//		tr("${village_third_personal_reading_quantity_comment_value}",
//				pf(uvReadingQuantityComment.get(0).v2));
	}

	@Override
	protected void chart()
	{
//		Collections.reverse(personalReadingQuantityComment);
		BaseRow.chart(personalReadingQuantityComment);// chart48
//		Collections.reverse(personalReadingQuantityComment);
//		DoubleValueRow.reverseValue(uvReadingQuantityComment);
//		DoubleValueRow.chart(uvReadingQuantityComment);// chart49
//		DoubleValueRow.reverseValue(uvReadingQuantityComment);
	}

}
