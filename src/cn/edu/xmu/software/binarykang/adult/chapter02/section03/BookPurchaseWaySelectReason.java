package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>2.3.4.2��������ѡ��ԭ��
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class BookPurchaseWaySelectReason extends AdultBaseAction
{
	private List<BaseRow> data;

	public BookPurchaseWaySelectReason(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "����������ѡ��");
	}

	@Override
	protected void replace()
	{
		tr("${distance_book_purchase_way_select_reason_value}", 
				pf(BaseRow.getRowByKey("��ýϽ�", data).value));
		tr("${price_book_purchase_way_select_reason_value}", 
				pf(BaseRow.getRowByKey("�۸��ۿ�", data).value));
		tr("${environment_book_purchase_way_select_reason_value}",
				pf(BaseRow.getRowByKey("���ڻ����á���ʩ��ȫ", data).value));
		tr("${sevice_book_purchase_way_select_reason_value}", 
				pf(BaseRow.getRowByKey("�����˻����Ա̬�����С������", data).value));
		//BaseRow.sort(data);
		//tr("${best_book_purchase_way_select_reason_key}", data.get(0).key);
		tr("${best_book_purchase_way_select_reason_value}",
				pf(BaseRow.getRowByKey("ͼ�������", data).value));
		//tr("${second_book_purchase_way_select_reason_key}", data.get(1).key);
		tr("${second_book_purchase_way_select_reason_value}",
				pf(BaseRow.getRowByKey("ͼ����Ϣ�ḻ", data).value));
		//tr("${third_book_purchase_way_select_reason_key}", data.get(2).key);
		tr("${third_book_purchase_way_select_reason_value}",
				pf(BaseRow.getRowByKey("�������ҵ���Ҫ����", data).value));
		tr("${fourth_book_purchase_way_select_reason_key}", data.get(3).key);
		tr("${fourth_book_purchase_way_select_reason_value}",
				pf(data.get(3).value));
		
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		Collections.reverse(data);
		BaseRow.chart(data);// chart7
		Collections.reverse(data);
		BaseRow.sort(data);
	}

}
