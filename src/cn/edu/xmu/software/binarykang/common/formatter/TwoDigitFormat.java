package cn.edu.xmu.software.binarykang.common.formatter;

import java.text.DecimalFormat;

/**
 * ������λС��������ʵ����Format�ӿڣ����ڽ�һ��double���͵����ָ�ʽ��Ϊ������λС������ʽ
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
