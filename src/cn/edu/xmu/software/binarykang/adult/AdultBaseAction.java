package cn.edu.xmu.software.binarykang.adult;

import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ҵ���߼�����Ļ���
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
	 * �滻������ֱ�ӵ���docx��replace����������������е���
	 * 
	 * @param key
	 *            ��Ҫ�滻�ı���
	 * @param value
	 *            �滻֮���ֵ
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

	/**
	 * ���Ĵ��������㷨��ܣ��ȶ�ȡ���ݣ�Ȼ������ͼ��Ȼ�����滻������
	 */
	public void process()
	{
		readData();
		chart();
		replace();
	}
}
