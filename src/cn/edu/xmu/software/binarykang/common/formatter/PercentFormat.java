package cn.edu.xmu.software.binarykang.common.formatter;

import java.text.DecimalFormat;

/**
 * �ٷֱȸ�ʽ��������ʵ����Format�ӿڣ����ڽ�һ��double���͵����ָ�ʽ��Ϊ�ٷֱȵ���ʽ
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
