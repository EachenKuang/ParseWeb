package cn.edu.xmu.software.binarykang.common.formatter;

import java.text.DecimalFormat;

/**
 * 保留两位小数，此类实现了Format接口，用于将一个double类型的数字格式化为保留两位小数的形式
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class TwoDigitFormat implements Format
{
	private DecimalFormat twoDigitFormat;

	public TwoDigitFormat()
	{
		twoDigitFormat = new DecimalFormat("0.00");
	}

	@Override
	public String format(double digit)
	{
		return twoDigitFormat.format(digit);
	}

}
