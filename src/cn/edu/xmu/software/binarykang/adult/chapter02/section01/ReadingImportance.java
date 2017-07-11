package cn.edu.xmu.software.binarykang.adult.chapter02.section01;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.adult.parse.DocxHelper;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����2.1.1�Ķ���Ҫ����֪
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-14
 *
 */
public final class ReadingImportance extends AdultBaseAction
{
	private List<BaseRow> localReadingUsageData;
	private List<BaseRow> countryReadingUsageData;
	private List<BaseRow> localReadingImportanceData;
	private List<BaseRow> countryReadingImportanceData;
	private List<BaseRow> areaData;

	public ReadingImportance(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		localReadingUsageData = ListFactory.getBaseRows();
		countryReadingUsageData = ListFactory.getBaseRows();
		localReadingImportanceData = ListFactory.getBaseRows();
		countryReadingImportanceData = ListFactory.getBaseRows();
		areaData = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		// ��ȡ���غ�ȫ����"������Ķ����õ���֪"��ص�����
		BaseRow.read(xlsx, localReadingUsageData, "������Ķ����õ���֪");
		BaseRow.read(xlsx, countryReadingUsageData, "2.1.1�Ķ���Ҫ����֪ ��һ�������е�ȫ������");

		// ��ȡ"�Ķ���Ҫ����֪"��ص�����
		BaseRow.read(xlsx, localReadingImportanceData,
				xlsx.getRowByKey("�Ķ���Ҫ����֪") + 3, BEGIN_COL + 1);
		BaseRow.read(xlsx, countryReadingImportanceData,
				xlsx.getRowByKey("�Ķ���Ҫ����֪��ȫ����") + 3, BEGIN_COL + 1);

		// ��ȡ"�����ض��Ķ���Ҫ����֪����"��ص�����
		BaseRow.read(xlsx, areaData, xlsx.getRowByKey("�����ض��Ķ���Ҫ����֪����") + 3,
				BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${best_two_country_reading_importance_rate}",
				pf(countryReadingUsageData.get(0).value
						+ countryReadingUsageData.get(1).value));
		tr("${best_two_reading_importance_rate}",
				pf(localReadingUsageData.get(0).value
						+ localReadingUsageData.get(1).value));
		tr("${local_minus_country_best_two_reading_importance_rate_key}",
				localReadingUsageData.get(0).value
						+ localReadingUsageData.get(1).value > countryReadingUsageData
						.get(0).value + countryReadingUsageData.get(1).value ? "��"
						: "��");
		tr("${local_minus_country_best_two_reading_importance_rate_value}",
				of(Math.abs(localReadingUsageData.get(0).value
						+ localReadingUsageData.get(1).value - countryReadingUsageData
						.get(0).value - countryReadingUsageData.get(1).value)));
		tr("${worst_two_reading_importance_rate}",
				pf(localReadingUsageData.get(3).value
						+ localReadingUsageData.get(4).value));

		// �滻���Ķ���Ҫ����֪��صı���
		List<BaseRow> genderData = ListFactory.getBaseRows();
		genderData.add(localReadingImportanceData.get(0));
		genderData.add(localReadingImportanceData.get(1));
		BaseRow.sort(genderData);
		tr("${best_gender_reading_importance}", genderData.get(0).key);
		tr("${best_gender_reading_importance_num}", pf(genderData.get(0).value));
		tr("${second_gender_reading_importance}", genderData.get(1).key);
		tr("${second_gender_reading_importance_num}", pf(genderData.get(1).value));
		tr("${best_minus_second_gender_reading_importance_rate}",
				of(genderData.get(0).value - genderData.get(1).value));

		List<BaseRow> urbanData = ListFactory.getBaseRows();
		urbanData.add(localReadingImportanceData.get(2));
		urbanData.add(localReadingImportanceData.get(3));
		BaseRow.sort(urbanData);
		tr("${best_urban_reading_importance}", urbanData.get(0).key);
		tr("${best_urban_reading_importance_num}", pf(urbanData.get(0).value));
		tr("${second_urban_reading_importance}", urbanData.get(1).key);
		tr("${second_urban_reading_importance_num}", pf(urbanData.get(1).value));
		tr("${best_minus_second_urban_reading_importance_rate}",
				of(urbanData.get(0).value - urbanData.get(1).value));

		// �滻�����ض��Ķ���Ҫ����֪����
		tr("${best_area_reading_importance}", areaData.get(0).key);
		tr("${best_area_reading_importance_rate}", pf(areaData.get(0).value));
		tr("${second_area_reading_importance}", areaData.get(1).key);
		tr("${second_area_reading_importance_rate}", pf(areaData.get(1).value));
		tr("${third_area_reading_importance}", areaData.get(2).key);
		tr("${third_area_reading_importance_rate}", pf(areaData.get(2).value));
		tr("${fourth_area_reading_importance}", areaData.get(3).key);
		tr("${fourth_area_reading_importance_rate}", pf(areaData.get(3).value));
	}

	@Override
	protected void chart()
	{
		// �滻��״ͼ
		BaseRow.chart(localReadingUsageData);// chart2

		// �滻�Ķ���Ҫ����֪�ı��
		// ��Ϊ���Ƚ�С�����Ծ�ֱ���滻�����������ﻹ��Ҫ����һ��getTableIndex����ֹȫ�ֱ�����������
		DocxHelper.getTableIndex();
		for (int i = 0; i < localReadingImportanceData.size(); i++)
		{
			tr(String.format("${local_reading_importance_rate_%d}", i),
					pf(localReadingImportanceData.get(i).value));
			tr(String.format("${country_reading_importance_rate_%d}", i),
					pf(countryReadingImportanceData.get(i).value));
		}

		// �滻�����ض��Ķ���Ҫ����֪����
//		BaseRow.sort(areaData);
//		BaseRow.tableWithIndex(docx, areaData,
//				"Resource/adult/chapter02/reading_importance_odd_row.xml",
//				"Resource/adult/chapter02/reading_importance_even_row.xml");
	}

}
