package cn.edu.xmu.software.binarykang.xlsx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import cn.edu.xmu.software.binarykang.minor.parse.Constant;
import cn.edu.xmu.software.binarykang.util.FileInfo;

public class ChartXlsxForList extends ChartXlsx
{
	@Override
	public void save()
	{
		changeCoreXML();

		try
		{
			FileOutputStream outputStream = new FileOutputStream(new File(
					FileInfo.INSTANCE.getSavePath()
							+ File.separator
							+ "word"
							+ File.separator
							+ "embeddings"
							+ File.separator
							+ String.format(EXCEL_NAME,
									Constant.showChartNum() - 1)));

			ZipOutputStream zipOut = new ZipOutputStream(outputStream);
			for (int i = 0; i < xmlEntries.size(); i++)
			{
				xmlEntries.get(i).save(zipOut);
			}
			zipOut.finish();
			outputStream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
