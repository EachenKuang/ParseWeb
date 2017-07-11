package cn.edu.xmu.software.binarykang.minor.sheet4.chapter01._1_8;

import java.util.Collections;
import java.util.List;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_8_1 extends MinorBaseAction
{
	private final static String TABLE_KEY_AVG = "1.8.1 家长购买图书的金额 第一段文字中的平均每年花费买书";
	private final static String TABLE_KEY = "0-8岁儿童家长购买图书的金额";
	private List<DataMap> tableInfo;
	private List<DataMap> tableAVG;

	public _1_8_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableInfo = MinorUtil.listMapFactory();
		tableAVG = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_AVG, tableAVG, BEGIN_COL);
	}

	@Override
	protected void replace()
	{
		tr("${sheet_4_1_8_1_pay_cartoon_avg}",
				twof.format(tableAVG.get(0).getRate()));
		
		
		
//		tr("${sheet_4_1_8_1_pay_cartoon_never_buy}",
//				perf.format(tableInfo.get(0).getRate()));
		tr("${sheet_4_1_8_1_pay_cartoon_buy}",
				perf.format(1-tableInfo.get(0).getRate()));
		tr("${sheet_4_1_8_1_pay_cartoon_fifty_more}",
				perf.format(tableInfo.get(tableInfo.size()-4).getRate()
						+tableInfo.get(tableInfo.size()-2).getRate()
						+tableInfo.get(tableInfo.size()-3).getRate()
						));
		tr("${sheet_4_1_8_1_pay_cartoon_fifty_more_0}",
				perf.format(tableInfo.get(tableInfo.size()-3).getRate()));//100-300
		tr("${sheet_4_1_8_1_pay_cartoon_fifty_more_1}",
				perf.format(tableInfo.get(tableInfo.size()-4).getRate()));//50-100
		tr("${sheet_4_1_8_1_pay_cartoon_fifty_more_2}",
				perf.format(tableInfo.get(tableInfo.size()-2).getRate()));//300-
		
		tr("${sheet_4_1_8_1_pay_cartoon_less_fifty}",
				perf.format(tableInfo.get(1).getRate()
						+tableInfo.get(2).getRate()
						+tableInfo.get(3).getRate()
						+tableInfo.get(4).getRate()
						+tableInfo.get(5).getRate()));
		
		tr("${sheet_4_1_8_1_pay_cartoon_unclear}",
				perf.format(tableInfo.get(tableInfo.size()-1).getRate()));
	}

	@Override
	protected void chart()
	{
		DataMap unclear = tableInfo.get(0);
		tableInfo.remove(unclear);
		tableInfo.add(unclear);	
		MinorUtil.changeChart(tableInfo);
	}
}
