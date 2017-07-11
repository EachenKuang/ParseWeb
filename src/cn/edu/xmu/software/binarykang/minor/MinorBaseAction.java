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
	 * ������ԴExcel�ļ��ж�ȡ����
	 */
	protected abstract void readData();

	/**
	 * �����滻��������ģ���еı����滻Ϊ��Ӧ������
	 */
	protected abstract void replace();

	/**
	 * ����ͼ�����������ģ���е����ݣ���̬������ͼ��
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
