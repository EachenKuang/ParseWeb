package cn.edu.xmu.software.binarykang.adult.chapter04.section05;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.TripleValueRow;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>4.5.7ѡ����������������ԭ�����Լ����
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class InternetPurchaseSelectReasonAndConstraint extends
		AdultBaseAction
{
	private List<BaseRow> internetPurchaseSelectReason;
	private List<TripleValueRow> uvComp;
	private List<BaseRow> noInternetPurchaseReason;
	private List<DoubleValueRow> doubleValueRowList; 

	public InternetPurchaseSelectReasonAndConstraint(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		internetPurchaseSelectReason = ListFactory.getBaseRows();
		uvComp = ListFactory.getTripleValueRows();
		noInternetPurchaseReason = ListFactory.getBaseRows();
		doubleValueRowList = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, internetPurchaseSelectReason, "ѡ���������������Ҫԭ��");
		TripleValueRow.read(xlsx, uvComp, "ѡ���������������Ҫԭ�����Ա�");
		BaseRow.read(xlsx, noInternetPurchaseReason, "��ѡ���������������Ҫԭ��");
		
		
		for(int i =0 ; i<uvComp.size()-1;i++)
		{
			DoubleValueRow temp = new DoubleValueRow();
			temp.key = uvComp.get(i).key;
			temp.v1 = uvComp.get(i).v2;
			temp.v2 = uvComp.get(i).v3;
			doubleValueRowList.add(temp);
		}
	}

	@Override
	protected void replace()
	{
		BaseRow.sortExceptLast(internetPurchaseSelectReason);
//		BaseRow.getRowByKey("",internetPurchaseSelectReason).value;
		tr("${best_internet_purchase_select_reason_key}",
				internetPurchaseSelectReason.get(0).key);
		tr("${best_internet_purchase_select_reason_value}",
				pf(BaseRow.getRowByKey("�۸��Ż�",internetPurchaseSelectReason).value));
		tr("${second_internet_purchase_select_reason_key}",
				internetPurchaseSelectReason.get(1).key);
		tr("${second_internet_purchase_select_reason_value}",
				pf(BaseRow.getRowByKey("��ʡȥ����ʱ��ͷ���",internetPurchaseSelectReason).value));
		tr("${third_internet_purchase_select_reason_key}",
				internetPurchaseSelectReason.get(2).key);
		tr("${third_internet_purchase_select_reason_value}",
				pf(BaseRow.getRowByKey("�ͻ�����",internetPurchaseSelectReason).value));
		tr("${fourth_internet_purchase_select_reason_key}",
				internetPurchaseSelectReason.get(3).key);
		tr("${fourth_internet_purchase_select_reason_value}",
				pf(BaseRow.getRowByKey("�������ҵ���Ҫ����",internetPurchaseSelectReason).value));
		tr("${fiveth_internet_purchase_select_reason_key}",
				internetPurchaseSelectReason.get(4).key);
		tr("${fiveth_internet_purchase_select_reason_value}",
				pf(BaseRow.getRowByKey("ͼ�������",internetPurchaseSelectReason).value));
		tr("${sixth_internet_purchase_select_reason_key}",
				internetPurchaseSelectReason.get(5).key);
		tr("${sixth_internet_purchase_select_reason_value}",
				pf(BaseRow.getRowByKey("�зḻ����Ϣ�����۹��ο�",internetPurchaseSelectReason).value));
		tr("${seventh_internet_purchase_select_reason_key}",
				internetPurchaseSelectReason.get(6).key);
		tr("${seventh_internet_purchase_select_reason_value}",
				pf(BaseRow.getRowByKey("�ṩ��Ʒ����չ�",internetPurchaseSelectReason).value));
		
		
		
		DoubleValueRow.sortByV1(doubleValueRowList);
		tr("${urban_best_internet_purchase_select_reason_key}",
				doubleValueRowList.get(0).key);
		tr("${urban_best_internet_purchase_select_reason_value}",
				pf(doubleValueRowList.get(0).v1));
		DoubleValueRow.sortByV1MinusV2(doubleValueRowList);
		tr("${urban_second_internet_purchase_select_reason_key}",
				doubleValueRowList.get(0).key);
//		tr("${second_internet_purchase_select_reason_value}",
//				pf(internetPurchaseSelectReason.get(1).value));
		tr("${urban_third_internet_purchase_select_reason_key}",
				doubleValueRowList.get(1).key);
//		tr("${urban_third_internet_purchase_select_reason_value}",
//				pf(internetPurchaseSelectReason.get(2).value));
		tr("${urban_fourth_internet_purchase_select_reason_key}",
				doubleValueRowList.get(2).key);
//		tr("${fourth_internet_purchase_select_reason_value}",
//				pf(internetPurchaseSelectReason.get(3).value));
		
		DoubleValueRow.sortByV2(doubleValueRowList);
		tr("${village_best_internet_purchase_select_reason_key}",
				doubleValueRowList.get(0).key);
		tr("${village_best_internet_purchase_select_reason_value}",
				pf(doubleValueRowList.get(0).v2));
		DoubleValueRow.sortByV2MinusV1(doubleValueRowList);
		tr("${village_second_internet_purchase_select_reason_key}",
				doubleValueRowList.get(0).key);
//		tr("${second_internet_purchase_select_reason_value}",
//				pf(internetPurchaseSelectReason.get(1).value));
		tr("${village_third_internet_purchase_select_reason_key}",
				doubleValueRowList.get(1).key);
//		tr("${third_internet_purchase_select_reason_value}",
//				pf(internetPurchaseSelectReason.get(2).value));
		tr("${village_fourth_internet_purchase_select_reason_key}",
				uvComp.get(2).key);
//		tr("${fourth_internet_purchase_select_reason_value}",
//				pf(internetPurchaseSelectReason.get(3).value));
		
//		TripleValueRow.sortByV2(uvComp);
//		tr("${urban_best_internet_purchase_select_reason_key}",
//				uvComp.get(0).key);
//		tr("${urban_best_internet_purchase_select_reason_value}",
//				pf(uvComp.get(0).v2));
//		tr("${urban_second_internet_purchase_select_reason_key}",
//				uvComp.get(1).key);
////		tr("${second_internet_purchase_select_reason_value}",
////				pf(internetPurchaseSelectReason.get(1).value));
//		tr("${urban_third_internet_purchase_select_reason_key}",
//				uvComp.get(2).key);
////		tr("${urban_third_internet_purchase_select_reason_value}",
////				pf(internetPurchaseSelectReason.get(2).value));
//		tr("${urban_fourth_internet_purchase_select_reason_key}",
//				uvComp.get(3).key);
////		tr("${fourth_internet_purchase_select_reason_value}",
////				pf(internetPurchaseSelectReason.get(3).value));
//		
//		TripleValueRow.sortByV3(uvComp);
//		tr("${village_best_internet_purchase_select_reason_key}",
//				uvComp.get(0).key);
//		tr("${village_best_internet_purchase_select_reason_value}",
//				pf(uvComp.get(0).v3));
//		tr("${village_second_internet_purchase_select_reason_key}",
//				uvComp.get(1).key);
////		tr("${second_internet_purchase_select_reason_value}",
////				pf(internetPurchaseSelectReason.get(1).value));
//		tr("${village_third_internet_purchase_select_reason_key}",
//				uvComp.get(2).key);
////		tr("${third_internet_purchase_select_reason_value}",
////				pf(internetPurchaseSelectReason.get(2).value));
//		tr("${village_fourth_internet_purchase_select_reason_key}",
//				uvComp.get(3).key);
////		tr("${fourth_internet_purchase_select_reason_value}",
////				pf(internetPurchaseSelectReason.get(3).value));	
		// �滻��ѡ���������������Ҫԭ����صı���
		tr("${best_no_select_internet_purchase_reason_key}",
				noInternetPurchaseReason.get(0).key);
		tr("${best_no_select_internet_purchase_reason_value}",
				pf(noInternetPurchaseReason.get(0).value));
		tr("${second_no_select_internet_purchase_reason_key}",
				noInternetPurchaseReason.get(1).key);
		tr("${second_no_select_internet_purchase_reason_value}",
				pf(noInternetPurchaseReason.get(1).value));
		tr("${third_no_select_internet_purchase_reason_key}",
				noInternetPurchaseReason.get(2).key);
		tr("${third_no_select_internet_purchase_reason_value}",
				pf(noInternetPurchaseReason.get(2).value));
		tr("${fourth_no_select_internet_purchase_reason_key}",
				noInternetPurchaseReason.get(3).key);
		tr("${fourth_no_select_internet_purchase_reason_value}",
				pf(noInternetPurchaseReason.get(3).value));
		tr("${fifthth_no_select_internet_purchase_reason_key}",
				noInternetPurchaseReason.get(4).key);
		tr("${fifth_no_select_internet_purchase_reason_value}",
				pf(noInternetPurchaseReason.get(4).value));
		tr("${sixth_no_select_internet_purchase_reason_key}",
				noInternetPurchaseReason.get(5).key);
		tr("${sixth_no_select_internet_purchase_reason_value}",
				pf(noInternetPurchaseReason.get(5).value));
		tr("${seventh_no_select_internet_purchase_reason_key}",
				noInternetPurchaseReason.get(6).key);
		tr("${seventh_no_select_internet_purchase_reason_value}",
				pf(noInternetPurchaseReason.get(6).value));

	}

	@Override
	protected void chart()
	{
		// ����ѡ���������������Ҫԭ�����״ͼ
		BaseRow.sort(internetPurchaseSelectReason);
		Collections.reverse(internetPurchaseSelectReason);
		BaseRow.chart(internetPurchaseSelectReason);// chart34
		Collections.reverse(internetPurchaseSelectReason);
		// ����ѡ���������������Ҫԭ�����Աȵı��
		
//		
		DoubleValueRow.sortByV1(doubleValueRowList);
		DoubleValueRow.table(docx, doubleValueRowList,"Resource/doubleValueStandard.xml",
				"Resource/doubleValueStandard.xml");
//		TripleValueRow
//				.table(docx,
//						uvComp,
//						"Resource/adult/chapter04/uv_internet_purchase_book_odd_row.xml",
//						"Resource/adult/chapter04/uv_internet_purchase_book_even_row.xml");
		
		// ���ɲ�ѡ���������������Ҫԭ�����״ͼ
		BaseRow.sortExceptLast(noInternetPurchaseReason);
		Collections.reverse(noInternetPurchaseReason);
		BaseRow.chart(noInternetPurchaseReason);// chart35
		Collections.reverse(noInternetPurchaseReason);
	}

}
