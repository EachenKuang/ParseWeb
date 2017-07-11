package cn.edu.xmu.software.binarykang.adult.chapter07.section03;

import java.util.Collections;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;

import com.sun.org.apache.regexp.internal.recompile;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>7.3公共文化服务设施存在的不足
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public final class PublicCultureServiceDeficiency extends AdultBaseAction
{
	private List<BaseRow> data;

	public PublicCultureServiceDeficiency(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "公共文化设施的不足之处");
	}

	@Override
	protected void replace()
	{
		tr("${book_newspaper_magazine_so_little_rate}", pf(data.get(0).value));
		tr("${audio_so_little_rate}",pf(data.get(1).value));
		tr("${long_way_rate}",pf(data.get(2).value));
		tr("${open_time_too_short_rate}", pf(data.get(3).value));
		tr("${environment_is_not_ideal_rate}", pf(data.get(4).value));
		tr("${information_update_not_in_time_rate}", pf(data.get(5).value));
		tr("${management_confusion_rate}", pf(data.get(7).value));
		tr("${no_cultural_facilities_rate}",
				pf(data.get(data.size() - 1).value));
	}

	@Override
	protected void chart()
	{
		BaseRow last = data.get(data.size()-1);
		data.remove(data.size()-1);
		BaseRow.sortExceptLast(data);
		data.add(last);
		Collections.reverse(data);
		BaseRow.chart(data);// chart47
		Collections.reverse(data);
	}
	
	@Override
	public void process() {
		readData();
		replace();
		chart();
	}

}
