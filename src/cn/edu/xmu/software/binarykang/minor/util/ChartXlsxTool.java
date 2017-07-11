package cn.edu.xmu.software.binarykang.minor.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;

public class ChartXlsxTool
{
	public static Map<String, List<BaseRow>> chartAdapterDouble(
			boolean genderType, List<?>... table)
	{
		Map<String, List<BaseRow>> map = new LinkedHashMap<String, List<BaseRow>>();
		int tableSize = table[0].size();

		@SuppressWarnings("unchecked")
		List<BaseRow>[] baseRows = (List<BaseRow>[]) new ArrayList<?>[table.length];

		for (int j = 0; j < table.length; j++)
		{
			baseRows[j] = new ArrayList<BaseRow>();
			for (int i = 0; i < tableSize; i++)
			{
				DataMap row = (DataMap) table[j].get(i);
				BaseRow baseRow = new BaseRow();
				baseRow.key = row.getKey();
				baseRow.value = row.getRate();
				baseRows[j].add(baseRow);
			}
		}
		if (genderType)
		{
			for (int j = 0; j < table.length; j++)
			{
				map.put(j == 0 ? "ÄÐÉú" : "Å®Éú", baseRows[j]);
			}
		} else
		{
			for (int j = 0; j < table.length; j++)
			{
				map.put(j == 0 ? MinorUtil.local : MinorUtil.other, baseRows[j]);
			}
		}
		return map;
	}

}
