package cn.edu.xmu.software.binarykang.minor.sheet2._4_4;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_4_8 extends MinorBaseAction{
	
	private final static String TABLE_KEY_TYPE = "14-17岁倾向的阅读形式";
	private List<DataMap> tableType;
	
	public _4_4_8(Docx docx, Xlsx xlsx) {
		super(docx, xlsx);
		tableType = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData() {
		MinorUtil.readData(xlsx, TABLE_KEY_TYPE, tableType, BEGIN_COL + 1);
	}

	@Override
	protected void replace() {
		MinorUtil.listSort(tableType);
		for(int i = 0; i < 5; i++)
		{
			tr("${_4_4_8_read_tendency_" + i + "_rate}", perf.format(tableType.get(i).getRate()));
			tr("${_4_4_8_read_tendency_" + i + "}", tableType.get(i).getKey());
		}
		
	}

	@Override
	protected void chart() {
		
		Collections.reverse(tableType);
		MinorUtil.changeChart(tableType);//aDD
		
	}

	
}
