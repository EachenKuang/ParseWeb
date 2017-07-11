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
 * ����=>8.1.2�����Ķ����������
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
		BaseRow.read(xlsx, data, "�����Ķ����仯");
		Collections.reverse(data);
//		DoubleValueRow.read(xlsx, mfData, "�����Ķ����仯���Ա����");
	}

	@Override
	protected void replace()
	{
		// �滻�����Ķ����仯��صı���
		tr("${add_digit_reading_quantity_value}", pf(data.get(0).value));
		tr("${add_paper_content_reading_quantity_value}", pf(data.get(1).value));
		tr("${no_change_in_reading_quantity_value}", pf(data.get(2).value));
		tr("${reduce_digit_reading_quantity_value}", pf(data.get(3).value));
		tr("${reduce_paper_content_reading_quantity_value}",
				pf(data.get(4).value));
		// �滻�����Ķ����仯���Ա������صı���
//		DoubleValueRow noChangeRow = mfData.get(2);
//		tr("${male_no_change_in_reading_quantity_value}", pf(noChangeRow.v1));
//		tr("${male_minus_female_no_change_in_reading_quantity_key1}",
//				noChangeRow.v1 > noChangeRow.v2 ? "��" : "��");
//		tr("${male_minus_female_no_change_in_reading_quantity_key2}",
//				noChangeRow.v1 > noChangeRow.v2 ? "��" : "��");
//		tr("${male_minus_female_no_change_in_reading_quantity_value}",
//				of(Math.abs(noChangeRow.v1 - noChangeRow.v2)));
//
//		DoubleValueRow reducePaperContentRow = mfData.get(4);
//		boolean result = reducePaperContentRow.v1 > reducePaperContentRow.v2;
//		tr("${best_gender_reduce_paper_content_reading_quantity_key}",
//				result ? "��" : "Ů");
//		tr("${best_gender_reduce_paper_content_reading_quantity_value}",
//				pf(result ? reducePaperContentRow.v1 : reducePaperContentRow.v2));
//		tr("${worst_gender_reduce_paper_content_reading_quantity_key}",
//				result ? "Ů" : "��");
//		tr("${best_minus_worst_gender_reduce_paper_content_reading_quantity_value}",
//				of(Math.abs(reducePaperContentRow.v1 - reducePaperContentRow.v2)));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart50

//		DoubleValueRow.chart(mfData, "��", "Ů");// chart51
	}

}
