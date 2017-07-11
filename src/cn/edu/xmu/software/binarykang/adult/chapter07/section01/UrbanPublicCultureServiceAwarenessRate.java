package cn.edu.xmu.software.binarykang.adult.chapter07.section01;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>7.1.1城镇居民公共文化服务设施知晓率
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class UrbanPublicCultureServiceAwarenessRate extends
		AdultBaseAction
{
	private List<BaseRow> data;
	private double awarenessRate;

	public UrbanPublicCultureServiceAwarenessRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "城镇居民对各类公共文化服务设施的知晓率");
		awarenessRate = 1 - new Double(data.get(data.size() - 1).value);
		data.remove(data.size() - 1);
	}

	@Override
	protected void replace()
	{
		tr("${urban_public_culture_service_awareness_rate}", pf(awarenessRate));
		tr("${best_public_culture_service_awareness_key}", data.get(0).key);
		tr("${best_public_culture_service_awareness_value}",
				pf(data.get(0).value));
		tr("${second_public_culture_service_awareness_key}", data.get(1).key);
		tr("${second_public_culture_service_awareness_value}",
				pf(data.get(1).value));
		tr("${third_public_culture_service_awareness_key}", data.get(2).key);
		tr("${third_public_culture_service_awareness_value}",
				pf(data.get(2).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		Collections.reverse(data);
		BaseRow.chart(data);// chart43
		Collections.reverse(data);
	}

}
