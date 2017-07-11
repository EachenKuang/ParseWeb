package cn.edu.xmu.software.binarykang.adult.chapter07.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>7.2.2农家书屋满意度
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class VillageStudySatisfactionDegree extends AdultBaseAction
{
	private List<BaseRow> data;

	public VillageStudySatisfactionDegree(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "农家书屋的满意度");
	}

	@Override
	protected void replace()
	{
		tr("${village_be_satisfied_with_study_rate}", pf(data.get(0).value
				+ data.get(1).value));
		tr("${village_not_satisfied_with_study_rate}", pf(data.get(3).value
				+ data.get(4).value));
		tr("${village_think_study_just_so_so_rate}", pf(data.get(2).value));
		tr("${village_have_no_idea_with_study_rate}",
				pf(data.get(data.size() - 1).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart44
	}

}
