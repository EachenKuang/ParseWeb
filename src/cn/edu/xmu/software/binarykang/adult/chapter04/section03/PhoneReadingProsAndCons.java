package cn.edu.xmu.software.binarykang.adult.chapter04.section03;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.3.6手机阅读优缺点
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
		BaseRow.read(xlsx, pros, "手机阅读人群认为的手机阅读的优点");
		BaseRow.read(xlsx, cons, "手机阅读人群认为的手机阅读的缺点");
	}

	@Override
	protected void replace()
	{
		// 替换手机阅读优点相关的变量
		tr("${best_phone_reading_pros_key}", pros.get(0).key);
		tr("${best_phone_reading_pros_value}", pf(BaseRow.getRowByKey("可以随时随地阅读", pros).value));
		tr("${second_phone_reading_pros_key}", pros.get(1).key);
		tr("${second_phone_reading_pros_value}", pf(BaseRow.getRowByKey("不用携带图书或其他阅读设备就能阅读", pros).value));
		tr("${third_phone_reading_pros_key}", pros.get(2).key);
		tr("${third_phone_reading_pros_value}", pf(BaseRow.getRowByKey("阅读内容费用较低或免费", pros).value));
		tr("${fourth_phone_reading_pros_key}", pros.get(3).key);
		tr("${fourth_phone_reading_pros_value}", pf(BaseRow.getRowByKey("信息时效性强", pros).value));
		// 替换手机阅读缺点相关的变量
		tr("${best_phone_reading_cons_key}", cons.get(0).key);
		tr("${best_phone_reading_cons_value}", pf(BaseRow.getRowByKey("伤眼睛，容易视觉疲劳", cons).value));
		tr("${second_phone_reading_cons_key}", cons.get(1).key);
		tr("${second_phone_reading_cons_value}", pf(BaseRow.getRowByKey("辐射大", cons).value));
		tr("${third_phone_reading_cons_key}", cons.get(2).key);
		tr("${third_phone_reading_cons_value}", pf(BaseRow.getRowByKey("手机屏幕太小", cons).value));
		tr("${fourth_phone_reading_cons_key}", cons.get(3).key);
		tr("${fourth_phone_reading_cons_value}", pf(BaseRow.getRowByKey("有一定操作限制", cons).value));
		tr("${fiveth_phone_reading_cons_key}", cons.get(4).key);
		tr("${fiveth_phone_reading_cons_value}", pf(BaseRow.getRowByKey("没有明显的缺点", cons).value));
		
//		BaseRow.getRowByKey("可以随时随地阅读", pros).value
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
