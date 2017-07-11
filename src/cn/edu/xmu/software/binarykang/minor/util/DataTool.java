package cn.edu.xmu.software.binarykang.minor.util;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.log.LogError;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class DataTool
{
	public static String readData(Xlsx xlsx, int beginRow, List<DataMap> data,
			int beginCol, String... args)
	{
		XSSFRow row;

		for (int j = beginRow; (row = xlsx.getRow(j)).getLastCellNum() != -1; ++j)
		{
			String key = xlsx.getContent(row, beginCol);

			try
			{
				Double rate = new Double(xlsx.getContent(xlsx.getRow(j),
						beginCol + 1));
				data.add(new DataMap(key, rate));
			}
			catch (Exception e)
			{
				System.err.println("捕获异常\t出错位置：" + j + "行\t出错原因"
						+ e.getMessage());
				if (!e.getMessage().equals("empty String"))
					LogError.addError("在 " + beginRow + " 行的表格数据格式有误！");
				break;
			}
		}
		if (args.length != 0)
			return MinorUtil.other;
		return "";
	}
}
