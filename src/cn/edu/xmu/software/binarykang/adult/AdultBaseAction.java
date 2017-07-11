package cn.edu.xmu.software.binarykang.adult;

import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 业务逻辑处理的基类
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-16
 *
 */
public abstract class AdultBaseAction
{
	public static final int BEGIN_COL = 3;
	protected Docx docx;
	protected Xlsx xlsx;

	public AdultBaseAction(Docx docx, Xlsx xlsx)
	{
		this.docx = docx;
		this.xlsx = xlsx;
	}

	/**
	 * 替换函数，直接调用docx的replace函数，方便子类进行调用
	 * 
	 * @param key
	 *            需要替换的变量
	 * @param value
	 *            替换之后的值
	 */
	protected final void tr(String key, String value)
	{
		docx.replace(key, value);
	}

	protected final String pf(double digit)
	{
		return GF.p.format(digit);
	}

	protected final String of(double digit)
	{
		return GF.o.format(digit * 100);
	}

	protected final String tf(double digit)
	{
		return GF.t.format(digit);
	}

	/**
	 * 从数据源Excel文件中读取数据
	 */
	protected abstract void readData();

	/**
	 * 处理替换操作，将模版中的变量替换为相应的数据
	 */
	protected abstract void replace();

	/**
	 * 处理图表操作，根据模版中的数据，动态的生成图表
	 */
	protected abstract void chart();

	/**
	 * 核心处理函数，算法框架，先读取数据，然后生成图表，然后做替换操作。
	 */
	public void process()
	{
		readData();
		chart();
		replace();
	}
}
