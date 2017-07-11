package cn.edu.xmu.software.binarykang.adult.chapter07.section02;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>7.2.3ũ�������������ṩ����ı���
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public final class VillageStudyContent extends AdultBaseAction
{
	private List<BaseRow> alwaysReadType;
	private List<BaseRow> lackType;

	public VillageStudyContent(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		alwaysReadType = ListFactory.getBaseRows();
		lackType = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, alwaysReadType, "ũ�������ũ�����ݾ����Ķ��ĳ���������");
		BaseRow.read(xlsx, lackType, "ũ�����ݱȽ�ȱ�ٵĳ���������");
	}

	@Override
	protected void replace()
	{
		BaseRow.sortExceptLast(alwaysReadType);
		// �滻ũ�������ũ�����ݾ����Ķ��ĳ�����������صı���
		tr("${best_always_reading_type_key}", alwaysReadType.get(0).key);
		tr("${best_always_reading_type_value}", pf(alwaysReadType.get(0).value));
		tr("${second_always_reading_type_key}", alwaysReadType.get(1).key);
		tr("${second_always_reading_type_value}",
				pf(alwaysReadType.get(1).value));
		tr("${third_always_reading_type_key}", alwaysReadType.get(2).key);
		tr("${third_always_reading_type_value}",
				pf(alwaysReadType.get(2).value));
		tr("${fourth_always_reading_type_key}", alwaysReadType.get(3).key);
		tr("${fourth_always_reading_type_value}",
				pf(alwaysReadType.get(3).value));
		tr("${fiveth_always_reading_type_key}", alwaysReadType.get(4).key);
		tr("${fiveth_always_reading_type_value}", pf(alwaysReadType.get(4).value));
		tr("${sixth_always_reading_type_key}", alwaysReadType.get(5).key);
		tr("${sixth_always_reading_type_value}", pf(alwaysReadType.get(5).value));
		tr("${seventh_always_reading_type_key}", alwaysReadType.get(6).key);
		tr("${seventh_always_reading_type_value}", pf(alwaysReadType.get(6).value));
		tr("${eighth_always_reading_type_key}", alwaysReadType.get(7).key);
		tr("${eighth_always_reading_type_value}", pf(alwaysReadType.get(7).value));
		
		BaseRow.sortExceptLast(lackType);
		// �滻ũ�����ݱȽ�ȱ�ٵĳ�����������صı���
		tr("${best_lack_book_type_key}", lackType.get(0).key);
		tr("${best_lack_book_type_value}", pf(lackType.get(0).value));
		
		tr("${second_lack_book_type_key}", lackType.get(1).key);
		tr("${second_lack_book_type_value}", pf(lackType.get(1).value));
		
		tr("${third_lack_book_type_key}", lackType.get(2).key);
		tr("${third_lack_book_type_value}", pf(lackType.get(2).value));
		
		tr("${fourth_lack_book_type_key}", lackType.get(3).key);
		tr("${fourth_lack_book_type_value}", pf(lackType.get(3).value));
		
//		tr("${other_lack_book_type_value}",
//				pf(lackType.get(lackType.size() - 1).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(alwaysReadType);
		Collections.reverse(alwaysReadType);
		BaseRow.chart(alwaysReadType);// chart45
		Collections.reverse(alwaysReadType);

		BaseRow.sortExceptLast(lackType);
		Collections.reverse(lackType);
		BaseRow.chart(lackType);// chart46
		Collections.reverse(lackType);
	}

}
