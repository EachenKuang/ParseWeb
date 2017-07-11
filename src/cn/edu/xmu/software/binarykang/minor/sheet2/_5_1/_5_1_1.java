package cn.edu.xmu.software.binarykang.minor.sheet2._5_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _5_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "14-17��������ӳ�����Ĺ������������أ�";
	private final static String TABLE_KEY_OTHER = "14-17��������ӳ�����Ĺ���������ȫ����";
	private List<DataMap> tableLocal;
	private List<DataMap> tableOther;
	private String other;

	public _5_1_1(Docx docx, Xlsx xlsx)
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
		tr("${_5_1_1_other}", other);
		double buyLocal = 1 - tableLocal.get(tableLocal.size() - 1).getRate();
		double buyOther = 1 - tableOther.get(tableOther.size() - 1).getRate();
		double localCutOther = buyLocal - buyOther;
		String judge = localCutOther > 0 ? "��" : "��";
		tr("${_5_1_1_buy_place_buy_rate_local}", perf.format(buyLocal));
		tr("${_5_1_1_buy_place_buy_rate_other}", perf.format(buyOther));
		tr("${_5_1_1_buy_place_buy_judge}", judge);
		tr("${_5_1_1_buy_place_buy_local_cut_other}",
				delLastChar(perf.format(Math.abs(localCutOther))));

		MinorUtil.listSort(tableLocal);
		for (int i = 0; i < 2; i++)
		{
			tr("${_5_1_1_buy_place_" + i + "_local}", tableLocal.get(i)
					.getKey());
			tr("${_5_1_1_buy_place_" + i + "_rate_local}",
					perf.format(tableLocal.get(i).getRate()));
			tr("${_5_1_1_buy_place_" + i + "_rate_other}",
					perf.format(MinorUtil.getByKey(tableLocal.get(i).getKey(),
							tableOther).getRate()));
		}
		tr("${_5_1_1_buy_place_internet_rate}",
				perf.format(MinorUtil.getByKey("����", tableLocal).getRate()));
		tr("${_5_1_1_buy_place_software_store_rate}",
				perf.format(MinorUtil.getByKey("���ר����", tableLocal).getRate()));
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
		MinorUtil.changeChart(tableLocal, tableOther);
	}

}
