package cn.edu.xmu.software.binarykang.common.formatter;

/**
 * Global Formatter，全局的格式化器，包括了3个全局的格式化器
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public class GF
{
	public static final PercentFormat p = new PercentFormat(); // 百分比格式化器
	public static final OneDigitFormat o = new OneDigitFormat(); // 保留一位小数格式化器
	public static final TwoDigitFormat t = new TwoDigitFormat(); // 保留两位小数格式化器
}
