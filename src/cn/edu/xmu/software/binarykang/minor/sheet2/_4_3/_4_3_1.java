package cn.edu.xmu.software.binarykang.minor.sheet2._4_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.parse.Minor;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

public class _4_3_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_TYPE = "14-17岁手机阅读消费类型";
	private final static String TABLE_KEY_GENDER = "14-17岁上网从事活动的性别比较";
	private List<DataMap> tableType;
	private List<DataMap> tableMale;
	private List<DataMap> tableFemale;

	public _4_3_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableType = MinorUtil.listMapFactory();
		tableMale = MinorUtil.listMapFactory();
		tableFemale = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_TYPE, tableType, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, xlsx.getRowByKey(TABLE_KEY_GENDER) + 3,
				tableMale, BEGIN_COL + 1);

		int beginRow = xlsx.getRowByKey(TABLE_KEY_GENDER) + 3;
		XSSFRow row;
		for (int j = beginRow; (row = xlsx.getRow(j)).getLastCellNum() != -1; ++j)
		{
			String key = xlsx.getContent(row, BEGIN_COL + 1);
			if (key != "")
			{
				Double rate = new Double(xlsx.getContent(xlsx.getRow(j),
						BEGIN_COL + 3));
				// System.out.println(key + ":" + rate);
				tableFemale.add(new DataMap(key, rate));
			}
		}
	}

	

	@Override
	protected void replace()
	{
		MinorUtil.listSort(tableType);
		for (int i = 0; i < 12; i++)
		{
			tr("${_4_3_1_phone_active_" + i + "}", tableType.get(i).getKey());
			tr("${_4_3_1_phone_active_" + i + "_rate}",
					perf.format(tableType.get(i).getRate()));
		}
		double first3_bound = tableType.get(2).getRate();
		tr("${_4_3_1_phone_active_first3_bound}", perf.format((int)(first3_bound*10)/10.0));
		double first8_bound = tableType.get(7).getRate();
		tr("${_4_3_1_phone_active_first8_bound}", perf.format((int)(first8_bound*10)/10.0));
		
		List<DataMap> tempMaleList = MinorUtil.listMapFactory();
		List<DataMap> tempFemaleList = MinorUtil.listMapFactory();
		List<DataMap> tableMale1 = MinorUtil.listMapFactory();	//copy tableMale
		List<DataMap> tableFemale1 = MinorUtil.listMapFactory();//copy tableFemale
		for(int i = 0; i < tableMale.size(); i++){
			tableMale1.add(tableMale.get(i));
			tableFemale1.add(tableFemale.get(i));
		}
		for (int i = 0; i < 8; i++)
		{
			int index = 0;
			double max_dis = -1;
			for(int k = 0; k < tableFemale.size(); k++){
				if(tableFemale.get(k).getRate()-tableMale.get(k).getRate()>max_dis){
					max_dis = tableFemale.get(k).getRate()-tableMale.get(k).getRate();
					index = k;
				}
			}
			tr("${_4_3_1_phone_active_female_" + i + "}", tableFemale.get(index).getKey());
			tempFemaleList.add(tableFemale.remove(index));
			tempMaleList.add(tableMale.remove(index));
		}
		for(int i = 0; i < tempMaleList.size(); i++){
			tableFemale.add(tempFemaleList.remove(i));
			tableMale.add(tempMaleList.remove(i));
		}
		for (int i = 0; i < 3; i++)
		{
			int index = 0;
			double max_dis = -1;
			for(int k = 0; k < tableMale1.size(); k++){
				if(tableMale1.get(k).getRate()-tableFemale1.get(k).getRate()>max_dis){
					max_dis = tableMale1.get(k).getRate()-tableFemale1.get(k).getRate();
					index = k;
				}
			}
			tr("${_4_3_1_phone_active_male_" + i + "}", tableMale1.get(index).getKey());
			tempMaleList.add(tableMale1.remove(index));
			tempFemaleList.add(tableFemale1.remove(index));
		}
		for(int i = 0; i < tempMaleList.size(); i++){
			tableMale1.add(tempMaleList.remove(i));
			tableFemale1.add(tempFemaleList.remove(i));
		}
		//MinorUtil.listSort(tableMale);
		//MinorUtil.listSort(tableFemale);
	}

	@Override
	protected void chart()
	{
		MinorUtil.listSort(tableType);
		Collections.reverse(tableType);
		Collections.reverse(tableMale);
		Collections.reverse(tableFemale);
		
		MinorUtil.changeChart(tableType);
		MinorUtil.changeChart(true, tableMale, tableFemale);
		
		
//		DoubleValueRow.sortExceptLastByV1(mfData);
//		DoubleValueRow.chart(mfData, "男", "女");// chart13
	}

	@Override
	public void process()
	{
		readData();
		chart();
		replace();
		trReplace();
	};
}
