package cn.edu.xmu.software.binarykang.adult.chapter02.section01;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����2.1.6 ${city}�����ͥ������
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookCollection extends AdultBaseAction
{
	private double averageLocalBookCollectNum;
	private double averageCountryBookCollectNum;
	private double averageUrbanBookCollectNum;
	private double averageVillageBookCollectNum;

	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	public BookCollection(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		// ��ȡ���ؾ����ƽ��������
		averageLocalBookCollectNum = SingleValue.read(xlsx, "����ļ�ͥ��������2��");
		// ��ȡȫ�������ƽ��������
		averageCountryBookCollectNum = SingleValue.read(xlsx,
				"2.1.6���Ƹ��о����ͥ������ ��һ�������е�ȫ��������");
		// ��ȡ��������ƽ��������
		averageUrbanBookCollectNum = SingleValue.read(xlsx, "�����ͥ��������2��", 3, 1);
		// ��ȡũ������ƽ��������
		averageVillageBookCollectNum = SingleValue.read(xlsx, "�����ͥ��������2��", 3,
				2);

		BaseRow.read(xlsx, data, "����ļ�ͥ��������1��");
		DoubleValueRow.read(xlsx, uvData, "�����ͥ��������1��");

	}

	@Override
	protected void replace()
	{
		// �滻���ؾ����������صı���
		tr("${average_local_book_collection_num}",
				tf(averageLocalBookCollectNum));
		tr("${average_country_book_collection_num}",
				tf(averageCountryBookCollectNum));
		tr("${average_local_minus_country_book_collection_num_key}",
				averageLocalBookCollectNum > averageCountryBookCollectNum ? "��": "��");
		tr("${average_local_minus_country_book_collection_num_value}", 
				tf(Math.abs(averageLocalBookCollectNum - averageCountryBookCollectNum)));
		tr("${local_book_collection_num_over_50_rate}", pf(data.get(2).value
				+ data.get(3).value + data.get(4).value + data.get(5).value
				+ data.get(6).value));
		tr("${local_book_collection_num_over_100_rate}", pf(data.get(3).value
				+ data.get(4).value + data.get(5).value + data.get(6).value));
		// �滻��������������صı���
		tr("${urban_has_book_collection_rate}",
				pf(1 - uvData.get(uvData.size() - 1).v1));
		tr("${village_has_book_collection_rate}",
				pf(1 - uvData.get(uvData.size() - 1).v2));
		tr("${average_urban_book_collection_num}",
				tf(averageUrbanBookCollectNum));
		tr("${average_village_book_collection_num}",
				tf(averageVillageBookCollectNum));
		tr("${average_village_minus_urban_book_collection_num_key}",
				averageUrbanBookCollectNum > averageVillageBookCollectNum ? "����"
						: "�߳�");
		tr("${average_village_minus_urban_book_collection_num_value}",
				tf(Math.abs(averageUrbanBookCollectNum
						- averageVillageBookCollectNum)));
		tr("${village_book_collection_lower_20_rate}", pf(uvData.get(0).v2));
		tr("${village_no_book_collection_rate}",
				pf(uvData.get(uvData.size() - 1).v2));
		tr("${village_no_add_lower_20_book_collection_rate}",
				pf(uvData.get(0).v2 + uvData.get(uvData.size() - 1).v2));
		tr("${village_minus_urban_no_add_lower_20_book_collection_rate_key}",
				uvData.get(0).v2 + uvData.get(uvData.size() - 1).v2 > uvData
						.get(0).v1 + uvData.get(uvData.size() - 1).v1 ? "����"
						: "����");
		tr("${village_minus_urban_no_add_lower_20_book_collection_rate_value}",
				of(Math.abs(uvData.get(0).v2 + uvData.get(uvData.size() - 1).v2
						- uvData.get(0).v1 - uvData.get(uvData.size() - 1).v1)));
		tr("${urban_book_collection_lower_20_rate}", pf(uvData.get(0).v1));
		tr("${urban_no_book_collection_rate}",
				pf(uvData.get(uvData.size() - 1).v1));
	}

	@Override
	protected void chart()
	{
		// ���ɵ��ؾ���������ı��
//		BaseRow.table(
//				docx,
//				data,
//				"Resource/adult/chapter02/local_book_collection_num_odd_row.xml",
//				"Resource/adult/chapter02/local_book_collection_num_even_row.xml",
//				GF.p, false);
		BaseRow.table(
				docx,
				data,
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml",
				GF.p, false);

		// ���ɳ���ԱȾ���������ı��
//		DoubleValueRow
//				.table(docx,
//						uvData,
//						"Resource/adult/chapter02/urban_village_book_collection_num_odd_row.xml",
//						"Resource/adult/chapter02/urban_village_book_collection_num_even_row.xml",
//						GF.p, false);
		DoubleValueRow
		.table(docx,
				uvData,
				"Resource/doubleValueStandard.xml","Resource/doubleValueStandard.xml",
				GF.p, false);
		
	}

}
