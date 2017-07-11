package cn.edu.xmu.software.binarykang.common.rowtype;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����ΪΪ��ֵ��ȡ�ṩ�����Ĺ�����
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public class SingleValue
{
	/**
	 * ��Excel����ж�ȡ������һ��doubleֵ��ͨ��key�ҵ���Ӧ�ı��Ĭ�ϴӱ�����һ�У���ƫ��2�ĵط���ȡ����
	 * 
	 * @param xlsx
	 *            Excel����Դ
	 * @param key
	 *            ����
	 * @return ��ȡ����doubleֵ
	 */
	public static double read(Xlsx xlsx, String key)
	{
		return read(xlsx, key, 1, 2);
	}

	/**
	 * ��Excel����ж�ȡ������һ��doubleֵ��ͨ��key�ҵ���Ӧ�ı��Ȼ�������ƫ�ƺ���ƫ�ƶ�λ����Ӧ�ı��
	 * 
	 * @param xlsx
	 *            Excel����Դ
	 * @param key
	 *            ����
	 * @param rowOffset
	 *            ��ƫ��
	 * @param colOffset
	 *            ��ƫ��
	 * @return ��ȡ����doubleֵ
	 */
	public static double read(Xlsx xlsx, String key, int rowOffset,
			int colOffset)
	{
		return new Double(xlsx.getContent(
				xlsx.getRow(xlsx.getRowByKey(key) + rowOffset),
				AdultBaseAction.BEGIN_COL + colOffset));
	}

}
