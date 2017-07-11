package cn.edu.xmu.software.binarykang.common.formatter;

import java.text.DecimalFormat;

/**
 * 保留一位小数，此类实现了Format接口，用于将一个double类型的数字格式化为保留一位小数的形式
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class OneDigitFormat implements Format
{
	private DecimalFormat oneDigitFormat;

	public OneDigitFormat()
	{
		oneDigitFormat = new DecimalFormat("0.0");
	}

	@Override
	public String format(double digit)
	{
		return oneDigitFormat.format(digit);
	}

}
