package cn.edu.xmu.software.binarykang.adult.chapter04.section05;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>4.5.3������Ϊ
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class InternetBehavior extends AdultBaseAction
{
	private List<BaseRow> data;

	public InternetBehavior(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "���ϴ��µ���Ҫ�");
	}

	@Override
	protected void replace()
	{
		tr("${reading_newspaper_internet_behavior}",
				pf(BaseRow.getRowByKey("�Ķ�����", data).value));
		tr("${search_infomation_internet_behavior}",
				pf(BaseRow.getRowByKey("��ѯ������Ϣ", data).value));
		tr("${social_talk_internet_behavior}",
				pf(BaseRow.getRowByKey("��������/����", data).value));
		tr("${listen_music_internet_behavior}",
				pf(BaseRow.getRowByKey("��������/���ظ����͵�Ӱ", data).value));
		tr("${watch_movie_internet_behavior}",
				pf(BaseRow.getRowByKey("����Ƶ", data).value));
		tr("${play_computer_game_internet_behavior}",
				pf(BaseRow.getRowByKey("������Ϸ", data).value));
		tr("${reading_book_internet_behavior}",
				pf(BaseRow.getRowByKey("�Ķ������鼮������", data).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		BaseRow.table(docx, data,
//				"Resource/adult/chapter04/internet_behavior_odd_row.xml",
//				"Resource/adult/chapter04/internet_behavior_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
	}

}
