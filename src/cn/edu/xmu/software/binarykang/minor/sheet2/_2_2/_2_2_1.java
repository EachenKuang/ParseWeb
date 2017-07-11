package cn.edu.xmu.software.binarykang.minor.sheet2._2_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _2_2_1 extends MinorBaseAction
{
	private final static String TABLE_KEY = "14-17岁获取图书信息主要渠道";
	private List<DataMap> tableInfo;

	public _2_2_1(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		tableInfo = new ArrayList<DataMap>();
	}

	@Override
	protected void readData()
	{
		MinorUtil.readData(xlsx, TABLE_KEY, tableInfo, BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		DataMap tmp = tableInfo.get(tableInfo.size() - 2);
		docx.replace("${_2_2_1_get_info_tv_rate}", 
				perf.format(tableInfo.get(1).getRate()));
		docx.replace("${_2_2_1_get_info_newspaper_rate}", 
				perf.format(tableInfo.get(0).getRate()));
		docx.replace("${_2_2_1_get_info_bookstoread_rate}", 
				perf.format(tableInfo.get(4).getRate()));
		docx.replace("${_2_2_1_get_info_teacher_rate}", perf.format(MinorUtil.getByKey("老师或学校推荐", tableInfo).getRate()));
//		MinorUtil.getByKey("老师或学校推荐", tableInfo).getRate()
		docx.replace("${_2_2_1_get_info_internet_rate}", perf.format(MinorUtil.getByKey("互联网", tableInfo).getRate()));
		tableInfo.remove(tmp);
		MinorUtil.listSort(tableInfo);
		for (int i = 0; i < 2; i++)
		{
			docx.replace("${_2_2_1_get_info_" + i + "}", tableInfo.get(i)
					.getKey());
			docx.replace("${_2_2_1_get_info_" + i + "_rate}",
					perf.format(tableInfo.get(i).getRate()));
		}
		tableInfo.add(tmp);
	}

	@Override
	protected void chart()
	{
		Collections.reverse(tableInfo);
		MinorUtil.changeChart(tableInfo);
	}

}
