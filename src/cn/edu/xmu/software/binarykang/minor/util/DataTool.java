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
				System.err.println("�����쳣\t����λ�ã�" + j + "��\t����ԭ��"
						+ e.getMessage());
				if (!e.getMessage().equals("empty String"))
					LogError.addError("�� " + beginRow + " �еı�����ݸ�ʽ����");
				break;
			}
		}
		if (args.length != 0)
			return MinorUtil.other;
		return "";
	}
}
