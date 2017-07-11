package cn.edu.xmu.software.binarykang.adult.chapter04.section04;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.log.Log;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>4.4.2�����Ķ����Ķ�����
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class EReaderContent extends AdultBaseAction
{
	private List<BaseRow> data;

	public EReaderContent(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "�����Ķ����Ķ�����");
	}

	@Override
	protected void replace()
	{
		try
		{

//			tr("${reading_use_ereader_content_value}", pf(data.get(0).value
//					+ data.get(1).value + data.get(2).value + data.get(4).value
//					+ data.get(6).value));
//			tr("${entertainment_use_ereader_content_value}",
//					pf(data.get(3).value + data.get(5).value));
			BaseRow.sort(data);
			tr("${best_ereader_content_key}", data.get(0).key);
			tr("${best_ereader_content_value}", pf(data.get(0).value));
			tr("${second_ereader_content_key}", data.get(1).key);
			tr("${second_ereader_content_value}", pf(data.get(1).value));
			tr("${third_ereader_content_key}", data.get(2).key);
			tr("${third_ereader_content_value}", pf(data.get(2).value));
			tr("${fourth_ereader_content_key}", data.get(3).key);
			tr("${fourth_ereader_content_value}", pf(data.get(3).value));
			tr("${fiveth_ereader_content_key}", data.get(4).key);
			tr("${fiveth_ereader_content_value}", pf(data.get(4).value));
//			tr("${sixth_ereader_content_key}", data.get(5).key);
//			tr("${sixth_ereader_content_value}", pf(data.get(5).value));
//			tr("${seventh_ereader_content_key}", data.get(6).key);
//			tr("${seventh_ereader_content_value}", pf(data.get(6).value));
		}
		catch (IndexOutOfBoundsException e)
		{
			System.err.println("�����쳣\t����λ���ڣ�4.4.2�����Ķ����Ķ�����\t������Ϣ��"
					+ e.getMessage());
			Log.addException(e);
		}
	}

	@Override
	protected void chart()
	{
		BaseRow.sort(data);
		BaseRow.table(docx, data,
//				"Resource/adult/chapter04/ereader_content_odd_row.xml",
//				"Resource/adult/chapter04/ereader_content_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
	}

}
