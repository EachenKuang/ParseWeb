package cn.edu.xmu.software.binarykang.common.formatter;

/**
 * 定义了一个格式化double数字的接口，用于文本解析中出现的各种格式化问题的解决，消除if-else的判断
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public interface Format
{
	public String format(double digit);
}
