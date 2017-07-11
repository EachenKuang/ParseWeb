package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

/**
 * ����=>2.3.6������Լ����
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class RestrictionOfBookPurchase extends AdultBaseAction
{
	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	public RestrictionOfBookPurchase(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "���鲻�������");
		DoubleValueRow.read(xlsx, uvData, "�����������Լ���صıȽ�");
	}

	@Override
	protected void replace()
	{
//		BaseRow.sort(data);
		tr("${best_restriction_of_book_purchase_key}", data.get(0).key);
		tr("${best_restriction_of_book_purchase_value}", pf(data.get(0).value));
		tr("${second_restriction_of_book_purchase_key}", data.get(1).key);
		tr("${second_restriction_of_book_purchase_value}",
				pf(data.get(1).value));
		tr("${third_restriction_of_book_purchase_key}", data.get(2).key);
		tr("${third_restriction_of_book_purchase_value}", pf(data.get(2).value));
		tr("${fourth_restriction_of_book_purchase_key}", data.get(3).key);
		tr("${fourth_restriction_of_book_purchase_value}",
				pf(data.get(3).value));
		tr("${fiveth_restriction_of_book_purchase_key}", data.get(4).key);
		tr("${fiveth_restriction_of_book_purchase_value}",
				pf(data.get(4).value));
		
		DoubleValueRow.sortByV2(uvData);
		if(uvData.get(0).key.equals("û��ʲô������")){
			tr("${best_village_restriction_of_book_purchase_key}", uvData.get(1).key);
			tr("${best_village_restriction_of_book_purchase_value}", pf(uvData.get(1).v2));
			tr("${best_village_urban_restriction_of_book_purchase_value}", pf(uvData.get(1).v1));
			tr("${best_village_minus_urban_restriction_of_book_purchase_key}", 
					uvData.get(1).v1<uvData.get(1).v2? "�߳�" : "�ͳ�");//v-u
			tr("${best_village_minus_urban_restriction_of_book_purchase_value}", 
					pf(Math.abs(uvData.get(1).v1-uvData.get(1).v2)));
		}
		else{
			tr("${best_village_restriction_of_book_purchase_key}", uvData.get(0).key);
			tr("${best_village_restriction_of_book_purchase_value}", pf(uvData.get(0).v2));
			tr("${best_village_urban_restriction_of_book_purchase_value}", pf(uvData.get(0).v1));
			tr("${best_village_minus_urban_restriction_of_book_purchase_key}",
					uvData.get(0).v1<uvData.get(0).v2? "�߳�" : "�ͳ�");
			tr("${best_village_minus_urban_restriction_of_book_purchase_value}", 
					pf(Math.abs(uvData.get(0).v1-uvData.get(0).v2)));
		}
		//tr("${best_village_restriction_of_book_purchase_key}", uvData.get(0).key);
		//tr("${best_village_restriction_of_book_purchase_value}", pf(uvData.get(0).v2));
		//tr("${best_village_urban_restriction_of_book_purchase_value}", pf(uvData.get(0).v1));
		//tr("${best_village_minus_urban_restriction_of_book_purchase_value}", 
		//		pf(uvData.get(0).v1-uvData.get(1).v2));
		DoubleValueRow.sortByV1MinusV2(uvData);
		int[] no_restriction_index = {0,0,0,0};
		for(int i = 0; i<=3; i++){
			if(uvData.get(i).key.equals("û��ʲô������")) no_restriction_index[i] = 1;
			else if(i != 0) no_restriction_index[i] = no_restriction_index[i-1] + 1;
		}
		tr("${best_urban_restriction_of_book_purchase_key}", uvData.get(0+no_restriction_index[0]).key);
		tr("${second_urban_restriction_of_book_purchase_key}", uvData.get(1+no_restriction_index[1]).key);
		tr("${third_urban_restriction_of_book_purchase_key}", uvData.get(2+no_restriction_index[2]).key);
		tr("${fourth_urban_restriction_of_book_purchase_key}", uvData.get(3+no_restriction_index[3]).key);
		tr("${no_urban_restriction_of_book_purchase_value}", 
				pf(DoubleValueRow.getRowByKey("û��ʲô������", uvData).v1));
		tr("${no_village_restriction_of_book_purchase_value}",
				pf(DoubleValueRow.getRowByKey("û��ʲô������", uvData).v2));
		tr("${no_urban_minus_village_restriction_of_book_purchase_key}",
				DoubleValueRow.getRowByKey("û��ʲô������", uvData).v1>DoubleValueRow.getRowByKey("û��ʲô������", uvData).v2?
						"�߳�":"�ͳ�");
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		BaseRow.table(
				docx,
				data,
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
		DoubleValueRow.sortExceptLastByV1(uvData);
		DoubleValueRow
				.table(docx,
						uvData,
						"Resource/doubleValueStandard.xml",
						"Resource/doubleValueStandard.xml");

	}

}
