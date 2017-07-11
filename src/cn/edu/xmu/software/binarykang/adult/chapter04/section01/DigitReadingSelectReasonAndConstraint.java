package cn.edu.xmu.software.binarykang.adult.chapter04.section01;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.1.2数字阅读方式的选择原因与制约因素
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class DigitReadingSelectReasonAndConstraint extends
		AdultBaseAction
{
	private List<BaseRow> selectReason;
	private List<BaseRow> noSelectReason;
   private List<BaseRow> use;
	
	public DigitReadingSelectReasonAndConstraint(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		selectReason = ListFactory.getBaseRows();
		noSelectReason = ListFactory.getBaseRows();
		use = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, selectReason, "选择数字阅读方式的原因");
		BaseRow.read(xlsx, noSelectReason, "不选择数字阅读的原因");
	}

	@Override
	protected void replace()
	{
		// 替换选择数字阅读方式的原因相关的变量
		use.add(BaseRow.getRowByKey("获取便利",selectReason));
		use.add(BaseRow.getRowByKey("方便随时随地阅读",selectReason));
		use.add(BaseRow.getRowByKey("方便信息检索",selectReason));
		use.add(BaseRow.getRowByKey("方便复制和分享",selectReason));
		
		BaseRow.sort(use);
		
		tr("${best_digit_reading_select_reason_key}", use.get(0).key);
		tr("${best_digit_reading_select_reason_value}",
				pf(use.get(0).value));
		tr("${second_digit_reading_select_reason_key}", use.get(1).key);
		tr("${second_digit_reading_select_reason_value}",
				pf(use.get(1).value));
		tr("${third_digit_reading_select_reason_key}", use.get(2).key);
		tr("${third_digit_reading_select_reason_value}",
				pf(use.get(2).value));
		tr("${fourth_digit_reading_select_reason_key}", use.get(3).key);
		tr("${fourth_digit_reading_select_reason_value}",
				pf(use.get(3).value));
		
		
//		tr("${fiveth_digit_reading_select_reason_key}", selectReason.get(4).key);
		tr("${fiveth_digit_reading_select_reason_value}",
				pf(BaseRow.getRowByKey("信息量大",selectReason).value));
//		tr("${sixth_digit_reading_select_reason_key}", selectReason.get(5).key);
		tr("${sixth_digit_reading_select_reason_value}",
				pf(BaseRow.getRowByKey("收费少甚至不付费",selectReason).value));

		// 替换不选择数字阅读的原因相关的变量
		tr("${best_digit_reading_no_select_reason_key}",
				noSelectReason.get(0).key);
		tr("${best_digit_reading_no_select_reason_value}",
				pf(noSelectReason.get(0).value));
		tr("${second_digit_reading_no_select_reason_key}",
				noSelectReason.get(1).key);
		tr("${second_digit_reading_no_select_reason_value}",
				pf(noSelectReason.get(1).value));
		tr("${third_digit_reading_no_select_reason_key}",
				noSelectReason.get(2).key);
		tr("${third_digit_reading_no_select_reason_value}",
				pf(noSelectReason.get(2).value));
		tr("${fourth_digit_reading_no_select_reason_key}",
				noSelectReason.get(3).key);
		tr("${fourth_digit_reading_no_select_reason_value}",
				pf(noSelectReason.get(3).value));
//		tr("${fiveth_digit_reading_no_select_reason_key}",
//				noSelectReason.get(4).key);
//		tr("${fiveth_digit_reading_no_select_reason_value}",
//				pf(noSelectReason.get(4).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(selectReason);
		BaseRow.table(
				docx,
				selectReason,
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");

//		BaseRow.sortExceptLast(noSelectReason);
		BaseRow other = noSelectReason.get(noSelectReason.size()-1);
		noSelectReason.remove(other);
		BaseRow.sort(noSelectReason);
		noSelectReason.add(other);
		BaseRow.table(
				docx,
				noSelectReason,
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
	}

}
