package cn.edu.xmu.software.binarykang.adult.chapter04.section03;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>4.3.6�ֻ��Ķ���ȱ��
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class PhoneReadingProsAndCons extends AdultBaseAction
{
	private List<BaseRow> pros;
	private List<BaseRow> cons;

	public PhoneReadingProsAndCons(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		pros = ListFactory.getBaseRows();
		cons = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, pros, "�ֻ��Ķ���Ⱥ��Ϊ���ֻ��Ķ����ŵ�");
		BaseRow.read(xlsx, cons, "�ֻ��Ķ���Ⱥ��Ϊ���ֻ��Ķ���ȱ��");
	}

	@Override
	protected void replace()
	{
		// �滻�ֻ��Ķ��ŵ���صı���
		tr("${best_phone_reading_pros_key}", pros.get(0).key);
		tr("${best_phone_reading_pros_value}", pf(BaseRow.getRowByKey("������ʱ����Ķ�", pros).value));
		tr("${second_phone_reading_pros_key}", pros.get(1).key);
		tr("${second_phone_reading_pros_value}", pf(BaseRow.getRowByKey("����Я��ͼ��������Ķ��豸�����Ķ�", pros).value));
		tr("${third_phone_reading_pros_key}", pros.get(2).key);
		tr("${third_phone_reading_pros_value}", pf(BaseRow.getRowByKey("�Ķ����ݷ��ýϵͻ����", pros).value));
		tr("${fourth_phone_reading_pros_key}", pros.get(3).key);
		tr("${fourth_phone_reading_pros_value}", pf(BaseRow.getRowByKey("��ϢʱЧ��ǿ", pros).value));
		// �滻�ֻ��Ķ�ȱ����صı���
		tr("${best_phone_reading_cons_key}", cons.get(0).key);
		tr("${best_phone_reading_cons_value}", pf(BaseRow.getRowByKey("���۾��������Ӿ�ƣ��", cons).value));
		tr("${second_phone_reading_cons_key}", cons.get(1).key);
		tr("${second_phone_reading_cons_value}", pf(BaseRow.getRowByKey("�����", cons).value));
		tr("${third_phone_reading_cons_key}", cons.get(2).key);
		tr("${third_phone_reading_cons_value}", pf(BaseRow.getRowByKey("�ֻ���Ļ̫С", cons).value));
		tr("${fourth_phone_reading_cons_key}", cons.get(3).key);
		tr("${fourth_phone_reading_cons_value}", pf(BaseRow.getRowByKey("��һ����������", cons).value));
		tr("${fiveth_phone_reading_cons_key}", cons.get(4).key);
		tr("${fiveth_phone_reading_cons_value}", pf(BaseRow.getRowByKey("û�����Ե�ȱ��", cons).value));
		
//		BaseRow.getRowByKey("������ʱ����Ķ�", pros).value
	}

	@Override
	protected void chart()
	{
		Collections.swap(pros, pros.size() - 2, pros.size() - 1);
		BaseRow.sortExceptLast(pros);
		Collections.reverse(pros);
		BaseRow.chart(pros);// chart26
		Collections.reverse(pros);

		Collections.swap(cons, cons.size() - 2, cons.size() - 1);
		BaseRow.sortExceptLast(cons);
		Collections.reverse(cons);
		BaseRow.chart(cons);// chart27
		Collections.reverse(cons);
	}

}
