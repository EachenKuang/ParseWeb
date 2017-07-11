package cn.edu.xmu.software.binarykang.adult.chapter08.section02;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>8.2.4参与读书活动的原因
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public final class ReadingActivityParticipateReason extends AdultBaseAction
{
	private List<BaseRow> participateReason;
	private List<BaseRow> notParticipateReason;

	public ReadingActivityParticipateReason(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		participateReason = ListFactory.getBaseRows();
		notParticipateReason = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, participateReason, "参加各类阅读活动的原因");
		BaseRow.read(xlsx, notParticipateReason, "没有参加过各类阅读活动的原因");
	}

	@Override
	protected void replace()
	{
		// 替换参加各类阅读活动的原因相关的变量
//		BaseRow.sortExceptLast(participateReason);
		tr("${best_participation_reason_key}", participateReason.get(0).key);
		tr("${best_participation_reason_value}",
				pf(participateReason.get(0).value));
		tr("${second_participation_reason_key}", participateReason.get(1).key);
		tr("${second_participation_reason_value}",
				pf(participateReason.get(1).value));
		tr("${third_participation_reason_key}", participateReason.get(2).key);
		tr("${third_participation_reason_value}",
				pf(participateReason.get(2).value));
		tr("${fourth_participation_reason_key}", participateReason.get(3).key);
		tr("${fourth_participation_reason_value}",
				pf(participateReason.get(3).value));
		tr("${fiveth_participation_reason_key}", participateReason.get(4).key);
		tr("${fiveth_participation_reason_value}",
				pf(participateReason.get(4).value));
		tr("${sixth_participation_reason_key}", participateReason.get(5).key);
		tr("${sixth_participation_reason_value}",
				pf(participateReason.get(5).value));
		// 替换没有参加过各类阅读活动的原因相关的变量
//		BaseRow.sortExceptLast(notParticipateReason);
		tr("${best_not_participate_reason_key}",
				notParticipateReason.get(0).key);
		tr("${best_not_participate_reason_value}",
				pf(notParticipateReason.get(0).value));
		tr("${second_not_participate_reason_key}",
				notParticipateReason.get(1).key);
		tr("${second_not_participate_reason_value}",
				pf(notParticipateReason.get(1).value));
		tr("${third_not_participate_reason_key}",
				notParticipateReason.get(2).key);
		tr("${third_not_participate_reason_value}",
				pf(notParticipateReason.get(2).value));
		tr("${fourth_not_participate_reason_key}",
				notParticipateReason.get(3).key);
		tr("${fourth_not_participate_reason_value}",
				pf(notParticipateReason.get(3).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(participateReason);
		Collections.reverse(participateReason);
		BaseRow.chart(participateReason);// chart56
		Collections.reverse(participateReason);

		BaseRow.sortExceptLast(notParticipateReason);
		Collections.reverse(notParticipateReason);
		BaseRow.chart(notParticipateReason);// chart57
		Collections.reverse(notParticipateReason);
	}

}
