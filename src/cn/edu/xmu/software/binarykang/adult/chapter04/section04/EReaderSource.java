package cn.edu.xmu.software.binarykang.adult.chapter04.section04;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.4.4电子阅读器阅读资源来源
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class EReaderSource extends AdultBaseAction
{
	private List<BaseRow> data;

	public EReaderSource(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "电子阅读器资源的主要获得渠道");
	}

	@Override
	protected void replace()
	{
		BaseRow.sort(data);
		tr("${best_ereader_source_key}", data.get(0).key);
		tr("${best_ereader_source_value}", pf(data.get(0).value));
	}

	@Override
	protected void chart()
	{
		Collections.reverse(data);
		BaseRow.chart(data);// chart30
	}

}
