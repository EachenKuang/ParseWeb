package cn.edu.xmu.software.binarykang.minor.sheet3.chapter05._5_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _5_1_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_LOCAL = "9-13��ϲ���Ķ�����ʽ";
//	private final static String TABLE_KEY_OTHER = "5.1.1 ��������ƫ�ö� ��һ�������е�ȫ����ϲ���Ķ�����ʽ";
	private List<DataMap> tableLocal;
//	private List<DataMap> tableOther;
	private String other;

	public _5_1_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableLocal = MinorUtil.listMapFactory();
//		tableOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
//		other = MinorUtil.readData(xlsx, TABLE_KEY_OTHER, tableOther,
//				BEGIN_COL + 1, " ");
	}

	@Override
	protected void replace()
	{
		DataMap noFavor = tableLocal.get(tableLocal.size() - 1);
		tr("${sheet_3_5_1_1_other}", other);
		tr("${sheet_3_5_1_1_no_favor_cartoon_type_rate}",
				perf.format(noFavor.getRate()));
		tableLocal.remove(noFavor);
		MinorUtil.listSort(tableLocal);

		for (int i = 0; i < 4; i++)
		{
			tr("${sheet_3_5_1_1_favor_cartoon_type_" + i + "}",
					tableLocal.get(i).getKey());
			double localRate = tableLocal.get(i).getRate();
//			double otherRate = tableOther.get(i).getRate();
			tr("${sheet_3_5_1_1_favor_cartoon_type_" + i + "_rate}",
					perf.format(localRate));
//			tr("${sheet_3_5_1_1_favor_cartoon_type_" + i + "_rate_other}",
//					perf.format(otherRate));
//			tr("${sheet_3_5_1_1_favor_cartoon_type_" + i + "_rate_judge}",
//					localRate > otherRate ? "��" : "��");
//			tr("${sheet_3_5_1_1_favor_cartoon_type_" + i + "_rate_cut}",
//					delLastChar(perf.format(Math.abs(localRate - otherRate))));
		}
		tableLocal.add(noFavor);
	}

	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableLocal);
	}
}
