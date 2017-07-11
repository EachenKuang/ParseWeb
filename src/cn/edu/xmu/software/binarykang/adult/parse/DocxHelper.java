package cn.edu.xmu.software.binarykang.adult.parse;

/**
 * ����Docx�İ����࣬������¼ȫ�ֵ�ǰ�����ı���ͼ���±���Ϣ
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class DocxHelper
{
	private static int tableIndex = 0;// ����±�
	private static int chartIndex = 1;// ��״ͼ���״ͼ��С��

	public static void init(){
		tableIndex = 0;
		chartIndex = 1;
	}
	/**
	 * ��ȡ��ǰ�ǵڼ������
	 * 
	 * @return ��ǰ�����±�
	 */
	public static int getTableIndex()
	{
		return tableIndex++;
	}

	/**
	 * ��ȡ��ǰ�ǵڼ���ͼ
	 * 
	 * @return ��ǰͼ���±�
	 */
	public static int getChartIndex()
	{
		return chartIndex++;
	}

	/**
	 * ���԰����࣬���������ǰ��ͼ�±�
	 */
	public static void showChartIndex()
	{
		System.out.println("chart index = " + chartIndex);
	}

	/**
	 * ���԰����࣬���������ǰ�ı��
	 */
	public static void showTableIndex()
	{
		System.out.println("table index = " + tableIndex);
	}

	/**
	 * ��ȡ��ǰ�ǵڼ���ͼ
	 * 
	 * @return ��ǰͼ���±�
	 */
	public static int chartIndex()
	{
		return chartIndex;
	}

}
