package cn.edu.xmu.software.binarykang.minor;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import cn.edu.xmu.software.binarykang.minor.parse.Constant;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public abstract class MinorBaseAction
{
	protected final int BEGIN_COL = Constant.BEGIN_COL;
	protected DecimalFormat perf;
	protected DecimalFormat onef;
	protected DecimalFormat twof;
	protected DecimalFormat threef;
	protected Docx docx;
	protected Xlsx xlsx;

	private Map<String, String> trMap;

	public MinorBaseAction(Docx docx, Xlsx xlsx)
	{
		perf = new DecimalFormat("0.0%");
		onef = new DecimalFormat("0.0");
		twof = new DecimalFormat("0.00");
		threef = new DecimalFormat("0.000");
		trMap = new HashMap<String, String>();
		this.docx = docx;
		this.xlsx = xlsx;
	}

	protected String delLastChar(String string)
	{
		return string.substring(0, string.length() - 1);
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

	protected void tr(String key, String value)
	{
		trMap.put(key, value);
	}

	protected void trReplace()
	{
		for (Map.Entry<String, String> entry : trMap.entrySet())
		{
			docx.replace(entry.getKey(), entry.getValue());
		}
	}

	public void process()
	{
		readData();
		replace();
		chart();
		trReplace();
	}
}
