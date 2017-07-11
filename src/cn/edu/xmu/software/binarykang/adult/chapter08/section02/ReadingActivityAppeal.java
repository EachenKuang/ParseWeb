package cn.edu.xmu.software.binarykang.adult.chapter08.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析完=>8.2.2读书活动诉求
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public final class ReadingActivityAppeal extends AdultBaseAction
{
	private List<BaseRow> data;

	public ReadingActivityAppeal(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "当地有关部门是否应该举办读书活动或读书节");
	}

	@Override
	protected void replace()
	{
		tr("${think_should_hold_reading_activity_value}", pf(data.get(0).value));
		tr("${think_should_not_reading_activity_value}",  pf(data.get(1).value));
		tr("${think_just_so_so_reading_activity_value}",  pf(data.get(2).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart58
	}

}
