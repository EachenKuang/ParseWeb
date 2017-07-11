package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>2.3.1ͼ����Ϣ�Ļ�ȡ;��
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class AccessBookInfoWay extends AdultBaseAction
{
	private List<BaseRow> data;

	public AccessBookInfoWay(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "�����߻�ȡͼ����Ϣ��;��");
	}

	@Override
	protected void replace()
	{
		
		tr("${internet_access_book_info}",
				pf(BaseRow.getRowByKey("������", data).value));
		tr("${tv_access_book_info}", pf(BaseRow.getRowByKey("����", data).value));
		tr("${social_network_access_book_info}",
				pf(BaseRow.getRowByKey("���ѻ������Ƽ�", data).value));
		tr("${newspaper_magazine_access_book_info}",
				pf(BaseRow.getRowByKey("��ֽ���ڿ�", data).value));
		tr("${ad_access_book_info}", 
				pf(BaseRow.getRowByKey("����ڹ������Ʒ", data).value));
		tr("${recommend_access_book_info}", 
				pf(BaseRow.getRowByKey("������Ա�Ƽ�", data).value));
		tr("${noway_access_book_info}",
				pf(BaseRow.getRowByKey("�޻�ȡ����", data).value));
		
		
//		tr("${internet_access_book_info}",
//				pf(data.get(3).value));
//		tr("${tv_access_book_info}", pf(data.get(1).value));
//		tr("${social_network_access_book_info}",
//				pf(data.get(6).value));
//		tr("${newspaper_magazine_access_book_info}",
//				pf(data.get(0).value));
//		tr("${ad_access_book_info}", 
//				pf(data.get(4).value));
//		tr("${recommend_access_book_info}", 
//				pf(data.get(5).value));
//		tr("${noway_access_book_info}",
//				pf(data.get(8).value));
	}

	@Override
	protected void chart()
	{
		// ���ɳ��粻�Ķ�ԭ�����״ͼ
		BaseRow noAccess = data.get(8);
		data.remove(8);
		BaseRow.sortExceptLast(data);
		data.add(noAccess);
		Collections.reverse(data);
		BaseRow.chart(data);// chart5
		Collections.reverse(data);
		BaseRow.sort(data);
	}

}
