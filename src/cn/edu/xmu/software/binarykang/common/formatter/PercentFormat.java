package cn.edu.xmu.software.binarykang.common.formatter;

import java.text.DecimalFormat;

/**
 * 百分比格式化，此类实现了Format接口，用于将一个double类型的数字格式化为百分比的形式
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class PercentFormat implements Format
{
	private DecimalFormat percentFormat;

	public PercentFormat()
	{
		percentFormat = new DecimalFormat("0.0%");
	}

	@Override
	public String format(double digit)
	{
		return percentFormat.format(digit);
	}

}
