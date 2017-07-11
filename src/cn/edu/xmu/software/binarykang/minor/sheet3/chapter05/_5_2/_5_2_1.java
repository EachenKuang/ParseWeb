package cn.edu.xmu.software.binarykang.minor.sheet3.chapter05._5_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _5_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "9-13岁喜爱的电子游戏题材（当地）";
	private final static String TABLE_KEY_OTHER = "9-13岁喜爱的电子游戏题材（全国）";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
	String other, local = MinorUtil.local;

	public _5_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
		tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
				BEGIN_COL + 1, " ");
	}

	@Override
	protected void replace()
	{
		tr("${sheet_3_5_2_1_other}", other);

//		MinorUtil.listSort(tableLocal);
		
		double contactGame = 1-tableLocal.get(tableLocal.size()-1).getRate();
		double contactGameOther = 1-tableOther.get(tableOther.size()-1).getRate();
		tr("${sheet_3_5_2_1_electric_game_theme_avg}",perf.format(contactGame));
		tr("${sheet_3_5_2_1_electric_game_theme_avg_other}",perf.format(contactGameOther));
		tr("${sheet_3_5_2_1_electric_game_theme_avg_judge}",
				contactGame > contactGameOther ? "高" : "低");
		tr("${sheet_3_5_2_1_electric_game_theme_avg_cut}",
				onef.format(100*Math.abs(contactGame - contactGameOther)));
		
		
		DataMap notContact = MinorUtil.getByKey("不接触电子游戏", tableLocal);
		tableLocal.remove(notContact);
		MinorUtil.listSort(tableLocal);
		tableLocal.add(notContact);
		
		for (int i = 0; i < 5; i++)
		{
			tr("${sheet_3_5_2_1_electric_game_theme_" + i + "}", tableLocal
					.get(i).getKey());

			tr("${sheet_3_5_2_1_electric_game_theme_" + i + "_rate}",
					perf.format(tableLocal.get(i).getRate()));
		}
		
		for (int i = 0; i < 2; i++)
		{
			tr("${sheet_3_5_2_1_electric_game_theme_-" + i + "}", tableLocal
					.get(tableLocal.size()-3-i).getKey());

			tr("${sheet_3_5_2_1_electric_game_theme_-" + i + "_rate}",
					perf.format(tableLocal.get(tableLocal.size()-3-i).getRate()));
		}
		
	}

	@Override
	public void process()
	{
		readData();
		chart();
		replace();
		trReplace();
	};

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal,tableOther);
	}
}
