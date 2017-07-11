package cn.edu.xmu.software.binarykang.minor.sheet2._4_3;

import java.util.List;

import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_3_7 extends MinorBaseAction
{
	private final static String TABLE_KEY = "通过手机微信进行过的活动";
	private List<DataMap> tableInfo;
	String _20_over ="";
	public _4_3_7(Docx docx, Xlsx xlsx)
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
		//tr("${_4_3_7_chat}", perf.format(tableInfo.get(0).getRate()));
		//tr("${_4_3_7_friends_circle}", perf.format(tableInfo.get(1).getRate()));
		//tr("${_4_3_7_friends_circle_article}", perf.format(tableInfo.get(2).getRate()));
		//tr("${_4_3_7_tencent_news}", perf.format(tableInfo.get(3).getRate()));
		//tr("${_4_3_7_official_accounts}", perf.format(tableInfo.get(4).getRate()));
//		MinorUtil.listSort(tableInfo);
		for(int i = 0; i<6; i++){
			tr("${_4_3_7_wechat_" + (i+1) + "_rate}", perf.format(tableInfo.get(i).getRate()));
			tr("${_4_3_7_wechat_" + (i+1) + "_key}", tableInfo.get(i).getKey());
		}
		for(int i = 6; i<11; i++){
			if (tableInfo.get(i).getRate()>=0.2)
				_20_over += "“"+tableInfo.get(i).getKey()+"”、";
			
//			tr("${_4_3_7_wechat_" + (i+1) + "_key}", tableInfo.get(i).getKey());
		}
//		delLastChar(_20_over);
		tr("${over_20}",delLastChar(_20_over));
//		double bound = (int)(tableInfo.get(11).getRate()*100)/10/10;
//		tr("${_4_3_7_wechat_bound}", perf.format(bound));
		
		double noOrder = tableInfo.get(tableInfo.size() - 1).getRate();
		tr("${_4_3_7_phone_service_have_order}", perf.format(noOrder));
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 3; i++)
		{
			tr("${_4_3_7_phone_service_" + i + "}", tableInfo.get(i).getKey());
			tr("${_4_3_7_phone_service_" + i + "_rate}", perf.format(tableInfo.get(i).getRate()));
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
//		String[] key = new String[tableInfo.size()];
//		String[][] value = new String[tableInfo.size()][1];
//		for (int i = 0; i < tableInfo.size(); ++i)
//		{
//			key[i] = tableInfo.get(i).getKey();
//			value[i][0] = perf.format(tableInfo.get(i).getRate());
//		}
//		MinorUtil.changeTable(tableInfo.size(), 1, key, value, docx, this.getClass());
		MinorUtil.table(docx,tableInfo,
				"Resource/singleValueStandard.xml","Resource/singleValueStandard.xml",GF.p);
	}
}
