package cn.edu.xmu.software.binarykang.adult.chapter01.section04;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.MultiType;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析1.4.1图书消费情况
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-14
 *
 */
public final class BookConsumption extends AdultBaseAction
{
	private double averageBookConsumeMoney;

	private List<BaseRow> genderData;
	private List<BaseRow> urbanData;
	private List<BaseRow> areaData;
	private List<BaseRow> occupationData;
	private List<BaseRow> educationData;
	private List<BaseRow> ageData;
	private List<List<BaseRow>> data;

	public BookConsumption(Docx docx, Xlsx xlsx)
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
		// 读取1.4.1图书消费情况第一行文字中的人均购书金额的数据
		averageBookConsumeMoney = SingleValue.read(xlsx,
				"1.4.1图书消费情况第一行文字中的人均购书金额", 1, 1);

		MultiType.read(xlsx, data, "不同人口特征的自费图书消费情况");
	}

	@Override
	protected void replace()
	{
		tr("${average_book_consume_money}", tf(averageBookConsumeMoney));
		// 替换跟性别有关的变量
		BaseRow.sort(genderData);
		tr("${best_gender_book_consumption}", genderData.get(0).key);
		tr("${best_gender_book_consumption_money}", tf(genderData.get(0).value));
		tr("${second_gender_book_consumption}", genderData.get(1).key);
		tr("${second_gender_book_consumption_money}", tf(genderData.get(1).value));
		tr("${gender_book_consumption_diff}", genderData.get(0).value>genderData.get(1).value?"高出":"低出");
		tr("${gender_book_consumption_diff_num}", tf(Math.abs(genderData.get(0).value-genderData.get(1).value)));

		// 替换跟城乡有关的变量
		double urbanConsumptionMoney = urbanData.get(0).value;
		double villageConsumptionMoney = urbanData.get(1).value;
		tr("${urban_book_consumption_money}", tf(urbanConsumptionMoney));
		tr("${village_book_consumption_money}", tf(villageConsumptionMoney));
		tr("${urban_village_book_consumption_diff}", urbanConsumptionMoney
				- villageConsumptionMoney > 0 ? "高出":"低出");
		tr("${urban_village_book_consumption_diff_num}", tf(Math.abs(urbanConsumptionMoney - villageConsumptionMoney)));

		// 替换与年龄有关的变量
		BaseRow.sort(ageData);
		tr("${best_age_book_consumption}", ageData.get(0).key);
		tr("${best_age_book_consumption_money}", tf(ageData.get(0).value));
		//tr("${second_age_book_consumption}", ageData.get(1).key);
		tr("${worst_age_book_consumption}", ageData.get(ageData.size()-1).key);
		tr("${worst_age_book_consumption_money}", tf(ageData.get(ageData.size()-1).value));
		
		// 替换跟学历相关的变量
		BaseRow.sort(educationData);
		tr("${best_education_book_consumption}", educationData.get(0).key);
		tr("${best_education_book_consumption_money}", tf(educationData.get(0).value));
		tr("${worst_education_book_consumption}", educationData.get(educationData.size()-1).key);
		tr("${worst_education_book_consumption_money}", tf(educationData.get(educationData.size()-1).value));
		
		// 替换职业相关的变量
		BaseRow.sortExceptLast(occupationData);
		tr("${best_occupation_book_consumption}", occupationData.get(0).key);
		tr("${best_occupation_book_consumption_money}", tf(occupationData.get(0).value));
		tr("${second_occupation_book_consumption}", occupationData.get(1).key);
		tr("${third_occupation_book_consumption}", occupationData.get(2).key);
		tr("${fourth_occupation_book_consumption}", occupationData.get(3).key);
		tr("${second_occupation_book_consumption_money}", tf(occupationData.get(1).value));
		tr("${third_occupation_book_consumption_money}", tf(occupationData.get(2).value));
		tr("${fourth_occupation_book_consumption_money}", tf(occupationData.get(3).value));
		tr("${occupation_book_consumption_low_bound}", tf((int)occupationData.get(3).value));
		tr("${fifth_occupation_book_consumption}", occupationData.get(4).key);
		tr("${sixth_occupation_book_consumption}", occupationData.get(5).key);
		tr("${seventh_occupation_book_consumption}", occupationData.get(6).key);
		tr("${eighth_occupation_book_consumption}", occupationData.get(7).key);
		tr("${ninth_occupation_book_consumption}", occupationData.get(8).key);
		tr("${fifth_occupation_book_consumption_money}", tf(occupationData.get(4).value));
		tr("${sixth_occupation_book_consumption_money}", tf(occupationData.get(5).value));
		tr("${seventh_occupation_book_consumption_money}", tf(occupationData.get(6).value));
		tr("${eighth_occupation_book_consumption_money}", tf(occupationData.get(7).value));
		tr("${ninth_occupation_book_consumption_money}", tf(occupationData.get(8).value));
		tr("${worst_second_occupation_book_consumption}", occupationData.get(occupationData.size()-3).key);
		tr("${worst_second_occupation_book_consumption_money}", tf(occupationData.get(occupationData.size()-3).value));
		tr("${worst_occupation_book_consumption}", occupationData.get(occupationData.size() - 2).key);
		tr("${worst_occupation_book_consumption_money}", tf(occupationData.get(occupationData.size() - 2).value));
		
		// 替换跟地区相关的变量
		BaseRow.sort(areaData);
		tr("${best_book_consumption_city}", areaData.get(0).key);
		tr("${best_book_consumption_city_money}", tf(areaData.get(0).value));
		tr("${second_book_consumption_city}", areaData.get(1).key);
		tr("${second_book_consumption_city_money}", tf(areaData.get(1).value));
		tr("${third_book_consumption_city}", areaData.get(2).key);
		tr("${third_book_consumption_city_money}", tf(areaData.get(2).value));
		tr("${worst_book_consumption_city}",
				areaData.get(areaData.size() - 1).key);
		tr("${worst_book_consumption_city_money}",
				tf(areaData.get(areaData.size() - 1).value));
		tr("${best_minus_worst_book_consumption_money}",
				tf(areaData.get(0).value
						- areaData.get(areaData.size() - 1).value));

	}

	@Override
	protected void chart()
	{
		BaseRow.sort(areaData);
		MultiType.table(docx, data, "${book_consumption_key_%d}",
				"${book_consumption_value_%d}",
//				"Resource/adult/chapter01/consumption_row.xml",
				"Resource/adult/chapter01/media_contact_type_area_row.xml",
				GF.t);
	}

}
