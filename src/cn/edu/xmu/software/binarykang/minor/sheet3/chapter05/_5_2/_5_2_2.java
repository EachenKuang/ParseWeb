package cn.edu.xmu.software.binarykang.minor.sheet3.chapter05._5_2;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _5_2_2 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL_AVG = "5.2.2 电子游戏的少年儿童日均接触时长 第一段文字中的日均接触时长（当地）";
	private final static String TABLE_KEY_OTHER_AVG = "5.2.2 电子游戏的少年儿童日均接触时长 第一段文字中的日均接触时长（全国）";
	private final static String TABLE_KEY_LOCAL = "9-13岁玩电子游戏的日均时长";
	
//	private final static String TABLE_KEY_OTHER = "5.2.2 电子游戏的少年儿童日均接触时长 第一段文字中的全国数据";
	private List<DataMap> tableLocal;
//	private List<DataMap> tableOther;
	private List<DataMap> tableAVGLoacl;
	private List<DataMap> tableAVGOther;
	private List<DataMap> tableAttitude;

	private String other;

	public _5_2_2(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableLocal = MinorUtil.listMapFactory();
//		tableOther = MinorUtil.listMapFactory();
		tableAVGLoacl = MinorUtil.listMapFactory();
		tableAVGOther = MinorUtil.listMapFactory();
		tableAttitude = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
//		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
//				BEGIN_COL + 1, " ");
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL_AVG, tableAVGLoacl, BEGIN_COL);
		MinorUtil.readData(xlsx,"9-13岁父母对玩电子游戏的态度", tableAttitude, BEGIN_COL+1);
		MinorUtil.readData(xlsx, TABLE_KEY_OTHER_AVG, tableAVGOther, BEGIN_COL);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_3_5_2_2_other}", other);
		tr("${sheet_3_5_2_2_have_electric_game_time_avg}",
				twof.format(tableAVGLoacl.get(0).getRate()));
		

		tr("${sheet_3_5_2_2_have_electric_game_time_0_rate}",
				perf.format(tableLocal.get(0).getRate()));
		tr("${sheet_3_5_2_2_have_electric_game_time_1_rate}",
				perf.format(tableLocal.get(1).getRate()));
		tr("${sheet_3_5_2_2_have_electric_game_time_2_rate}",
				perf.format(tableLocal.get(2).getRate()+tableLocal.get(3).getRate()+tableLocal.get(4).getRate()));
		tr("${sheet_3_5_2_2_have_electric_game_time_3_rate}",
				perf.format(tableLocal.get(5).getRate()+tableLocal.get(6).getRate()));
		
		tr("${sheet_3_3_3_2_attitude_aprove}",
				perf.format(tableAttitude.get(0).getRate() + tableAttitude.get(1).getRate()));
		tr("${sheet_3_3_3_2_attitude_disaprove}",
				perf.format(tableAttitude.get(3).getRate() + tableAttitude.get(4).getRate()));
		tr("${sheet_3_3_3_2_attitude_aprove_not_care}",
				perf.format(tableAttitude.get(2).getRate()));
		tr("${sheet_3_3_3_2_attitude_aprove_very_disaprove}",
				perf.format(tableAttitude.get(4).getRate()));
		tr("${sheet_3_3_3_2_attitude_aprove_little_disaprove}",
				perf.format(tableAttitude.get(3).getRate()));

	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableLocal);
		MinorUtil.changeChart(tableLocal);
		MinorUtil.changeChart(tableAttitude);
		
	}
}
