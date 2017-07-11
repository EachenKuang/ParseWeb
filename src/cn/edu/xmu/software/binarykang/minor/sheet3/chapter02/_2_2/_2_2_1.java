package cn.edu.xmu.software.binarykang.minor.sheet3.chapter02._2_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "2.2.1�ҳ��Զ�ͯ�ڿ��ļ۸������ ��һ�������еĵ�����ͯ�ڿ��ļ۸������";
	private final static String TABLE_KEY = "9-13���ڿ��ļ۸������";
	private final static String TABLE_TYPE = "2.2.1�ҳ��Զ�ͯ�ڿ��ļ۸������ �ڶ��������еĳ����ڿ��۸������";
	private List<DataMap> tableInfo;
	private List<DataMap> tableAVG;
	private List<DataMap> tableType;

	public _2_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
		tableAVG = MinorUtil.listMapFactory();
		tableType = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);

		int beginRow = xlsx.getRowByKey(TABLE_TYPE) + 1;

		String key = xlsx.getContent(xlsx.getRow(beginRow), BEGIN_COL + 1);
		Double rate = new Double(xlsx.getContent(xlsx.getRow(beginRow + 2),
				BEGIN_COL + 1));
		// System.out.println(key + ":" + rate);
		tableType.add(new DataMap(key, rate));

		key = xlsx.getContent(xlsx.getRow(beginRow), BEGIN_COL + 2);
		rate = new Double(xlsx.getContent(xlsx.getRow(beginRow + 2),
				BEGIN_COL + 2));
		// System.out.println(key + ":" + rate);
		tableType.add(new DataMap(key, rate));
	}

	@Override
	protected void replace()
	{
		tr("${sheet_3_2_2_1_magazine_afford_price_avg}",
				twof.format(tableAVG.get(0).getRate()));

//		MinorUtil.listSort(tableInfo);
		double twoTen = 0;
		
		for (int i = 1; i < 5; i++)
		{
//			tr("${sheet_3_2_2_1_magazine_afford_price_" + i + "}", tableInfo
//					.get(i).getKey());
			tr("${sheet_3_2_2_1_magazine_afford_price_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
			twoTen += tableInfo.get(i).getRate();
		}
		tr("${sheet_3_2_2_1_magazine_afford_price_0_rate}",
				perf.format(tableInfo.get(0).getRate()));
		tr("${sheet_3_2_2_1_magazine_afford_price_two_ten}",
				perf.format(twoTen));
		tr("${sheet_3_2_2_1_magazine_afford_price_more_ten}",
				perf.format(tableInfo.get(tableInfo.size()-1).getRate()
						+tableInfo.get(tableInfo.size()-2).getRate()
						+tableInfo.get(tableInfo.size()-3).getRate()));
		

		tr("${sheet_3_2_2_1_magazine_afford_price_city}",
				twof.format(tableType.get(0).getRate()));
		tr("${sheet_3_2_2_1_magazine_afford_price_village}",
				twof.format(tableType.get(1).getRate()));
		tr("${sheet_3_2_2_1_magazine_afford_price_type_judge}", tableType
				.get(0).getRate() >  tableType.get(1).getRate() ? "��" : "��");
		tr("${sheet_3_2_2_1_magazine_afford_price_type_cut}", twof.format(Math.abs(tableType
				.get(0).getRate() - tableType.get(1).getRate())));

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
		MinorUtil.changeChart(tableInfo);
	}

}
