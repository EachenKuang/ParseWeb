package cn.edu.xmu.software.binarykang.minor.sheet2._4_4;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_4_4 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁阅读相关的网络活动";
	private final static String TABLE_KEY_TIME = "14-17岁网络在线阅读的时长";
	private final static String TABLE_Time = "4.4.5网络在线阅读时长 第一段文字中的每天网络在线阅读时长";
	
	private List<DataMap> tableInfo;
	private List<DataMap> tableTime;
	private double local;
	private double male;
	private double female;


	public _4_4_4(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = MinorUtil.listMapFactory();
		tableTime = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_TIME, tableTime, BEGIN_COL + 1);
		local = SingleValue.read(xlsx, TABLE_Time, 3, 1);
		male = SingleValue.read(xlsx, TABLE_Time, 3, 2);
		female = SingleValue.read(xlsx, TABLE_Time, 3, 3);
		
	}

	@Override
	protected void replace()
	{
		DataMap never = tableInfo.get(tableInfo.size()-1);
		tableInfo.remove(tableInfo.size()-1);
		MinorUtil.listSort(tableInfo);
		tableInfo.add(never);
		for (int i = 0; i < 4; i++)
		{
			tr("${_4_4_4_net_read_active_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${_4_4_4_net_read_active_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		
		tr("${_4_4_4_local_time}", twof.format(local));
		tr("${_4_4_4_male_time}",twof.format(male));
		tr("${_4_4_4_female_time}",twof.format(female));
		tr("${_4_4_4_male_female_cut}",twof.format(Math.abs(male-female)));
		tr("${_4_4_4_male_female_judge}",male >female?  "多": "少");
		
		tr("${_4_4_4_less_ten_time}", perf.format(tableTime.get(0).getRate()));
		tr("${_4_4_4_twenty_twoh_time}",perf.format(tableTime.get(1).getRate()+
				tableTime.get(2).getRate()+
				tableTime.get(3).getRate()+
				tableTime.get(4).getRate()));
		tr("${_4_4_4_ten_twenty_time}", perf.format(tableTime.get(1).getRate()));
		tr("${_4_4_4_twenty_thirty_time}",
				perf.format((tableTime.get(2).getRate())));
		tr("${_4_4_4_thirty_oneh_time}",
				perf.format((tableTime.get(3).getRate())));
		tr("${_4_4_4_oneh_twoh_time}",
				perf.format((tableTime.get(4).getRate())));
		tr("${_4_4_4_more_twoh_time}",
				perf.format((tableTime.get(5).getRate()+tableTime.get(6).getRate())));
	
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
		Collections.reverse(tableTime);
		MinorUtil.changeChart(tableTime);//aDD
	}
}
