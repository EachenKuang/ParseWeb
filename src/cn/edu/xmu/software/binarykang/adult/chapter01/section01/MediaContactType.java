package cn.edu.xmu.software.binarykang.adult.chapter01.section01;

import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Single;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.MultiType;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析1.1.3媒介接触种类
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-11
 */
public final class MediaContactType extends AdultBaseAction
{
	private double mediaContactTypeNum;
	
	private List<BaseRow> genderData;
	private List<BaseRow> ageData;
	private List<BaseRow> educationData;
	private List<BaseRow> occupationData;
	private List<BaseRow> areaData;
	private List<BaseRow> urbanData;
	private List<List<BaseRow>> data;

	public MediaContactType(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		genderData = ListFactory.getBaseRows();
		urbanData = ListFactory.getBaseRows();
		ageData = ListFactory.getBaseRows();
		educationData = ListFactory.getBaseRows();
		occupationData = ListFactory.getBaseRows();
		areaData = ListFactory.getBaseRows();
		data = ListFactory.getVarTypeRows();
		data.add(genderData);
		data.add(urbanData);
		data.add(ageData);
		data.add(educationData);
		data.add(occupationData);
		data.add(areaData);
	}

	@Override
	protected void readData()
	{
		mediaContactTypeNum = SingleValue.read(xlsx, "媒介接触种类", 1, 1);
		MultiType.read(xlsx, data, "不同人口特征人群的媒介接触种类");
	}

	@Override
	protected void replace()
	{
		tr("${contact_type_num}", tf(mediaContactTypeNum));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < occupationData.size(); ++i)
		{
			BaseRow row = occupationData.get(i);
			if (row.value > 5)
			{
				sb.append("、" + "“" + row.key + "”");
			}
		}
		//tr("${occupation_than_5}", sb.substring(1));
        tr("${male_contact_type_num}", tf(genderData.get(0).value));
        tr("${female_contact_type_num}", tf(genderData.get(1).value));
        tr("${gender_contact_type_diff}", genderData.get(0).value>genderData.get(1).value?"高出":"低出");
        tr("${gender_contact_type_diff_num}", tf(Math.abs(genderData.get(0).value - genderData.get(1).value)));
        
        tr("${urban_contact_type_num}", tf(urbanData.get(0).value));
        tr("${village_contact_type_num}", tf(urbanData.get(1).value));
        tr("${urban_contact_type_diff}", urbanData.get(0).value>urbanData.get(1).value?"高出":"低出");
        tr("${urban_contact_type_diff_num}", tf(Math.abs(urbanData.get(0).value - urbanData.get(1).value)));
        
        BaseRow.sort(ageData);
        tr("${age_best_contact_type}", ageData.get(0).key);
        tr("${age_best_contact_type_num}", tf(ageData.get(0).value));
        tr("${age_least_contact_type}", ageData.get(ageData.size()-1).key);
        tr("${age_least_contact_type_num}", tf(ageData.get(ageData.size()-1).value));
        
        BaseRow.sort(educationData);
        tr("${education_best_contact_type}", educationData.get(0).key);
        tr("${education_best_contact_type_num}", tf(educationData.get(0).value));
        tr("${education_least_contact_type}", educationData.get(educationData.size()-1).key);
        tr("${education_least_contact_type_num}", tf(educationData.get(educationData.size()-1).value));
        
        BaseRow.sort(occupationData);
        tr("${occupation_best_contact_type}", occupationData.get(0).key);
        tr("${occupation_second_contact_type}", occupationData.get(1).key);
        tr("${occupation_third_contact_type}" ,occupationData.get(2).key);
        tr("${occupation_best3_contact_type_num}", tf((int)occupationData.get(2).value));
        
        BaseRow.sort(areaData);
        tr("${area_best_contact_type}", areaData.get(0).key);
        tr("${area_best_contact_type_num}", tf(areaData.get(0).value));
        tr("${area_least_contact_type}", areaData.get(areaData.size()-1).key);
        tr("${area_least_contact_type_num}", tf(areaData.get(areaData.size()-1).value));
        /*
		tr("${best_contact_rate_type}", areaData.get(0).key);
		tr("${best_contact_rate_type_num}", tf(areaData.get(0).value));
		tr("${second_contact_rate_type}", areaData.get(1).key);
		tr("${second_contact_rate_type_num}", tf(areaData.get(1).value));
		tr("${third_contact_rate_type}", areaData.get(2).key);
		tr("${third_contact_rate_type_num}", tf(areaData.get(2).value));
		tr("${worst_contact_rate_type}", areaData.get(areaData.size() - 1).key);
		tr("${worst_contact_rate_type_num}",
				tf(areaData.get(areaData.size() - 1).value));*/
	}

	@Override
	protected void chart()
	{
		BaseRow.sort(areaData);
		MultiType.table(docx, data, "${media_contact_rate_key_%d}",
				"${media_contact_rate_value_%d}",
				"Resource/adult/chapter01/media_contact_type_area_row.xml",
				GF.t);
	}

}
