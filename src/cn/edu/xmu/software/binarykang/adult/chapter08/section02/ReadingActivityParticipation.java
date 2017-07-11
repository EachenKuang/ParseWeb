package cn.edu.xmu.software.binarykang.adult.chapter08.section02;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>8.2.3�����Ĳ���״��
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public final class ReadingActivityParticipation extends AdultBaseAction
{
	private List<BaseRow> data;
	private List<BaseRow> data2;

	public ReadingActivityParticipation(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		data2 = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "����μӸ������������ڱ���");
		BaseRow.read(xlsx, data2, "����Ը������������������");
	}

	@Override
	protected void replace()
	{
		tr("${reading_activity_participation_degree}",
				pf(1 - data.get(data.size() - 1).value));
		tr("${reading_activity_dont_participation_degree}",
				pf(data.get(data.size() - 1).value));

		BaseRow lastRow = new BaseRow();
		lastRow.key = data.get(data.size() - 1).key;
		lastRow.value = data.get(data.size() - 1).value;
		data.remove(data.size() - 1);

		BaseRow.sortExceptLast(data);
		tr("${best_reading_participation_key}", data.get(0).key);
		tr("${best_reading_participation_value}", pf(data.get(0).value));
		tr("${second_reading_participation_key}", data.get(1).key);
		tr("${second_reading_participation_value}", pf(data.get(1).value));
		tr("${third_reading_participation_key}", data.get(2).key);
		tr("${third_reading_participation_value}", pf(data.get(2).value));

		data.add(lastRow);
		
		tr("${reading_activity_satisfaction_degree_value}",
				pf(data2.get(0).value + data2.get(1).value));
		tr("${reading_activity_general_satisfaction_degree_value}",
				pf(data2.get(2).value));
		tr("${reading_activity_not_satisfaction_degree_value}",
				pf(data2.get(3).value + data2.get(4).value));
	}

	@Override
	protected void chart()
	{
		Collections.reverse(data);
		BaseRow.chart(data);// chart55
		
		BaseRow.chart(data2);
	}

	@Override
	public void process()
	{
		readData();
		replace();
		chart();
	}

}
