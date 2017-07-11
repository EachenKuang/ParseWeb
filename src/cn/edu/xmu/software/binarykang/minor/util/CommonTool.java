package cn.edu.xmu.software.binarykang.minor.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import cn.edu.xmu.software.binarykang.minor.parse.DataMap;

public class CommonTool
{
	/**
	 * ==================================================
	 * 
	 * @param key
	 *            要获取的关键字
	 * @param list
	 *            目标集合
	 * @return 获取的关键字对应的值
	 */
	public static DataMap getByKey(String key, List<DataMap> list)
	{
		Iterator<DataMap> iterator = list.iterator();
		while (iterator.hasNext())
		{
			DataMap dataMap = iterator.next();
			if (dataMap.getKey().equals(key))
				return dataMap;
		}
		return null;
	}
	
	/**
	 * ========================================= 对集合排序，‘其他’排在最后
	 * 
	 * @param list
	 */
	public static void listSort(List<DataMap> list)
	{
		list.sort(new Comparator<DataMap>()
		{
			@Override
			public int compare(DataMap dp1, DataMap dp2)
			{
				if (dp1.getKey().equals("其他"))
					return 1;
				if (dp2.getKey().equals("其他"))
					return -1;
				if (dp1.getRate() > dp2.getRate())
					return -1;
				else if (dp1.getRate() < dp2.getRate())
					return 1;
				return 0;
			}
		});
	}
}
