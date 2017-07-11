package cn.edu.xmu.software.binarykang.common.formatter;

import java.text.DecimalFormat;

/**
 * ����һλС��������ʵ����Format�ӿڣ����ڽ�һ��double���͵����ָ�ʽ��Ϊ����һλС������ʽ
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
