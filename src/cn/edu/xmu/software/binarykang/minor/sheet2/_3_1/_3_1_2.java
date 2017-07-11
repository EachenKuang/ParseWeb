package cn.edu.xmu.software.binarykang.minor.sheet2._3_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.Constant;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_1_2 extends MinorBaseAction
{
	private final static String TABLE_KEY_NUM = "3.1.2��ֽ�Ķ��� ��һ�������еı�ֽ�˾��Ķ���";
	private final static String TABLE_KEY_COUNTRY = "3.1.2��ֽ�Ķ��� ��һ�������еı�ֽ�˾��Ķ�������ȫ��";
	private final static String TABLE_KEY_TYPE = "14-17�겻ͬȺ����˾���ֽ�Ķ���";
	private List<DataMap> tableNum;
	private List<DataMap> tableCountry;
	private List<DataMap> tableType;

	public _3_1_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableNum = MinorUtil.listMapFactory();
		tableCountry = MinorUtil.listMapFactory();
		tableType = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_NUM, tableNum, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY_COUNTRY, tableCountry, BEGIN_COL);
		MinorUtil.readData(xlsx, xlsx.getRowByKey(TABLE_KEY_TYPE) + 2,
				tableType, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${_3_1_2_read_num}", twof.format(tableNum.get(0).getRate()));
		tr("${_3_1_2_read_country_num}", twof.format(tableCountry.get(0).getRate()));
		tr("${_3_1_2_read_city_country}", 
				tableNum.get(0).getRate()>tableCountry.get(0).getRate()?"��":"��");
		tr("${_3_1_2_read_city_country_num}", 
				twof.format(Math.abs(tableNum.get(0).getRate()-tableCountry.get(0).getRate())));
		tr("${_3_1_2_read_num_male}", twof.format(tableType.get(0).getRate()));
		tr("${_3_1_2_read_num_female}", twof.format(tableType.get(1).getRate()));
		tr("${_3_1_2_read_num_male_female}", 
				tableType.get(0).getRate()>tableType.get(1).getRate()?"��":"��");
		tr("${_3_1_2_read_num_male_female_rate}",
				twof.format(Math.abs(tableType.get(0).getRate()-tableType.get(1).getRate())));
		tr("${_3_1_2_read_num_city}", twof.format(tableType.get(2).getRate()));
		tr("${_3_1_2_read_num_village}", twof.format(tableType.get(3).getRate()));
		tr("${_3_1_2_read_num_city_village}", 
				tableType.get(2).getRate()>tableType.get(3).getRate()?"��":"��");
		tr("${_3_1_2_read_num_city_village_rate}",
				twof.format(Math.abs(tableType.get(2).getRate()-tableType.get(3).getRate())));
	}

	@Override
	protected void chart()
	{
		Constant.getTabelNum();
		tr("${_3_1_2_read_num_male}", twof.format(tableType.get(0).getRate()));
		tr("${_3_1_2_read_num_female}", twof.format(tableType.get(1).getRate()));
		tr("${_3_1_2_read_num_city}", twof.format(tableType.get(2).getRate()));
		tr("${_3_1_2_read_num_village}",
				twof.format(tableType.get(3).getRate()));
	}

}
