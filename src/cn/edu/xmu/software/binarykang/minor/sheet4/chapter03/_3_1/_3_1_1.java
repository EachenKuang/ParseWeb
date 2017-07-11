package cn.edu.xmu.software.binarykang.minor.sheet4.chapter03._3_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _3_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "0-8���ͯ��������ӳ�����Ĺ�������";
	private List<DataMap> tableInfo;

	public _3_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		DataMap tmp = tableInfo.get(tableInfo.size() - 1);
		tr("${sheet_4_3_1_1_radio_buy_rate}", perf.format(1 - tmp.getRate()));
		tableInfo.remove(tmp);

		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 2; i++)
		{
			tr("${sheet_4_3_1_1_radio_buy_place_" + i + "}", tableInfo.get(i)
					.getKey());
			tr("${sheet_4_3_1_1_radio_buy_place_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tableInfo.add(tmp);
		tr("${sheet_4_3_1_1_radio_buy_radio_shop_rate}", perf.format(MinorUtil
				.getByKey("���������ר����", tableInfo).getRate()));
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableInfo);
	}
}
