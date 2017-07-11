package cn.edu.xmu.software.binarykang.adult.chapter01.section02;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����1.2������ý��ʹ��Ŀ��
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-14
 * 
 */
public final class MediaUsePurpose extends AdultBaseAction
{
	private final String DOMESTIC_AND_INTERNATIONAL_NEWS_KEY = "1.2������ý��ʹ��Ŀ�����һ�������еġ���������ʱ�¡���ע��";
	private final String DOMESTIC_TABLE_KEY = "�˽������Ϣʱ������ý�顪����������ʱ��";
	private final String DOMESTIC = "��������ʱ��";
	private final String INTERNATIONAL_TABLE_KEY = "�˽������Ϣʱ������ý�顪��������۵��˼��";
	private final String INTERNATIONAL = "������۵��˼��";
	private final String WORK_AND_STUDY_TABLE_KEY = "�˽������Ϣʱ������ý�顪���빤��ѧϰ�йص���Ϣ";
	private final String WORK_AND_STUDY = "�빤��ѧϰ�йص���Ϣ";
	private final String FASHION_TABLE_KEY = "�˽������Ϣʱ������ý�顪��ʱ����������";
	private final String FASHION = "ʱ����������";
	private final String CONSUME_TABLE_KEY = "�˽������Ϣʱ������ý�顪������/������Ѷ";
	private final String CONSUME = "����/������Ѷ";
	private final String ENTERTAINMENT_TABLE_KEY = "�˽������Ϣʱ������ý�顪������������Ϣ";
	private final String ENTERTAINMENT = "����������Ϣ";

	private double domesticAndInternationalNewsPercent;
	private List<MediaUsePurpose.TableRow> data;

	public MediaUsePurpose(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = new ArrayList<MediaUsePurpose.TableRow>();
	}

	@Override
	protected void readData()
	{
		// ��ȡ����������ʱ�¡���ע�ȵ�����
		int domesticAndInternationalNewsPercentRowIndex = xlsx.getRowByKey(DOMESTIC_AND_INTERNATIONAL_NEWS_KEY);
		XSSFRow domesticAndInternationalNewsRow = xlsx.getRow(domesticAndInternationalNewsPercentRowIndex + 2);
		domesticAndInternationalNewsPercent = new Double(
				xlsx.getContent(domesticAndInternationalNewsRow, BEGIN_COL + 2));

		// ��ȡ�滻��������
		int domesticRowIndex = xlsx.getRowByKey(DOMESTIC_TABLE_KEY);
		int internationalRowIndex = xlsx.getRowByKey(INTERNATIONAL_TABLE_KEY);
		int workAndStudyRowIndex = xlsx.getRowByKey(WORK_AND_STUDY_TABLE_KEY);
		int fashionRowIndex = xlsx.getRowByKey(FASHION_TABLE_KEY);
		int consumeRowIndex = xlsx.getRowByKey(CONSUME_TABLE_KEY);
		int entertainmentRowIndex = xlsx.getRowByKey(ENTERTAINMENT_TABLE_KEY);

		XSSFRow domesticRow = null;
		try
		{
			for (int i = 1, j = domesticRowIndex + 1; (domesticRow = xlsx.getRow(j)).getLastCellNum() != -1; ++i, ++j)
			{
				TableRow tableRow = new TableRow();
				tableRow.mediaType = xlsx.getContent(domesticRow, BEGIN_COL + 1);
				tableRow.domestic.key = DOMESTIC;
				tableRow.domestic.value = new Double(xlsx.getContent(domesticRow, BEGIN_COL + 2));
				tableRow.international.key = INTERNATIONAL;
				tableRow.international.value = new Double(xlsx.getContent(xlsx.getRow(internationalRowIndex + i),
						BEGIN_COL + 2));
				tableRow.workAndStudy.key = WORK_AND_STUDY;
				tableRow.workAndStudy.value = new Double(xlsx.getContent(xlsx.getRow(workAndStudyRowIndex + i),
						BEGIN_COL + 2));
				tableRow.fashion.key = FASHION;
				tableRow.fashion.value = new Double(xlsx.getContent(xlsx.getRow(fashionRowIndex + i), BEGIN_COL + 2));
				tableRow.consume.key = CONSUME;
				tableRow.consume.value = new Double(xlsx.getContent(xlsx.getRow(consumeRowIndex + i), BEGIN_COL + 2));
				tableRow.entertainment.key = ENTERTAINMENT;
				tableRow.entertainment.value = new Double(xlsx.getContent(xlsx.getRow(entertainmentRowIndex + i),
						BEGIN_COL + 2));
				data.add(tableRow);
			}
		}
		catch (Exception e)
		{
			System.out.println("����1.2������ý��ʹ��Ŀ�� == ��������������");
			e.printStackTrace();
		}

		System.out.println(data);

	}

	@Override
	protected void replace()
	{
		// 1.2������ý��ʹ��Ŀ�����һ�������еġ���������ʱ�¡���ע��
		tr("${domestic_and_international_news_percent}", pf(domesticAndInternationalNewsPercent));
		// �滻��������صı���
		TableRow tvRow = getRowByMediaType("����");
		BaseRow bestTV = tvRow.getBestPurpose();
		BaseRow secondTV = tvRow.getSecondPurpose();
		tr("${best_tv_key}", bestTV.key);
		tr("${best_tv_value}", pf(bestTV.value));
		tr("${second_tv_key}", secondTV.key);
		tr("${second_tv_value}", pf(secondTV.value));

		// �滻����ֽ��صı���
		TableRow newspaperRow = getRowByMediaType("��ֽ");
		BaseRow bestNewspaper = newspaperRow.getBestPurpose();
		BaseRow secondNewspaper = newspaperRow.getSecondPurpose();
		tr("${best_newspaper_key}", bestNewspaper.key);
		tr("${best_newspaper_value}", pf(bestNewspaper.value));
		tr("${second_newspaper_key}", secondNewspaper.key);
		tr("${second_newspaper_value}", pf(secondNewspaper.value));

		// �滻���㲥��صı���
		TableRow broadcastRow = getRowByMediaType("�㲥");
		BaseRow bestBroadcast = broadcastRow.getBestPurpose();
		BaseRow secondBroadcast = broadcastRow.getSecondPurpose();
		tr("${best_broadcast_key}", bestBroadcast.key);
		tr("${second_broadcast_key}", secondBroadcast.key);

		// �滻����־��صı���
		TableRow magazineRow = getRowByMediaType("��־");
		BaseRow bestMagazine = magazineRow.getBestPurpose();
		tr("${best_magazine_key}", bestMagazine.key);

		// �滻��ͼ����صı���
		TableRow bookRow = getRowByMediaType("ͼ��");
		BaseRow bestBook = bookRow.getBestPurpose();
		tr("${best_book_key}", bestBook.key);

		// �滻${internet_than_tv_key}
		TableRow internetRow = getRowByMediaType("������");
		tr("${internet_than_tv_key}", getAThanB(internetRow, tvRow));

		// �滻${telephone_than_newspaper_key}
		TableRow telephoneRow = getRowByMediaType("�ֻ�");
		tr("${telephone_than_newspaper_key}", getAThanB(telephoneRow, newspaperRow));
	}

	@Override
	protected void chart()
	{
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size(); ++i)
		{
			TableRow row = data.get(i);
			XMLFragment xmlFragment = new XMLFragment("Resource/adult/chapter01/media_use_purpose_row.xml");
			xmlFragment.replace("${media_type}", row.mediaType);
			xmlFragment.replace("${media_domestic}", pf(row.domestic.value));
			xmlFragment.replace("${media_international}", pf(row.international.value));
			xmlFragment.replace("${media_word_and_study}", pf(row.workAndStudy.value));
			xmlFragment.replace("${media_consume}", pf(row.consume.value));
			xmlFragment.replace("${media_fashion}", pf(row.fashion.value));
			xmlFragment.replace("${media_entertainment}", pf(row.entertainment.value));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

	private TableRow getRowByMediaType(String key)
	{
		Iterator<TableRow> iterator = data.iterator();
		while (iterator.hasNext())
		{
			TableRow row = iterator.next();
			if (row.mediaType.equals(key))
				return row;
		}
		return null;
	}

	private String getAThanB(TableRow a, TableRow b)
	{
		List<String> list = ListFactory.getStringList();
		if (a.domestic.value > b.domestic.value)
			list.add("��" + a.domestic.key + "��");
		if (a.international.value > b.international.value)
			list.add("��" + a.international.key + "��");
		if (a.workAndStudy.value > b.workAndStudy.value)
			list.add("��" + a.workAndStudy.key + "��");
		if (a.fashion.value > b.fashion.value)
			list.add("��" + a.fashion.key + "��");
		if (a.consume.value > b.consume.value)
			list.add("��" + a.consume.key + "��");
		if (a.entertainment.value > b.entertainment.value)
			list.add("��" + a.entertainment.key + "��");

		return composite(list);
	}

	/**
	 * ���ݴ����string��������ϣ������һ����ֱ�ӷ��ظ�string����������������ء�A��B���������͵�string���������2�������ء�A��B
	 * ...��N���������͵�string
	 * 
	 * @param strings
	 *            string�б�
	 * @return ��Ϻõ�string
	 */
	private String composite(List<String> strings)
	{
		StringBuilder sb = new StringBuilder();
		int size = strings.size();
		if (size == 0)
			return "";
		else if (size == 1)
		{
			sb.append(strings.get(0));
		}
		else if (size == 2)
		{
			sb.append(strings.get(0));
			sb.append("��");
			sb.append(strings.get(1));
		}
		else
		{
			for (int i = 0; i < size - 1; ++i)
			{
				sb.append(strings.get(i));
				sb.append("��");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("��");
			sb.append(strings.get(size - 1));
		}

		return sb.toString();
	}

	private class TableRow
	{
		private String mediaType;
		private BaseRow domestic;
		private BaseRow international;
		private BaseRow workAndStudy;
		private BaseRow fashion;
		private BaseRow consume;
		private BaseRow entertainment;
		private double bestPurposeValue;

		public TableRow()
		{
			domestic = new BaseRow();
			international = new BaseRow();
			workAndStudy = new BaseRow();
			fashion = new BaseRow();
			consume = new BaseRow();
			entertainment = new BaseRow();
		}

		public BaseRow getBestPurpose()
		{
			BaseRow bestPurpose = domestic;
			if (international.value > bestPurpose.value)
				bestPurpose = international;
			if (workAndStudy.value > bestPurpose.value)
				bestPurpose = workAndStudy;
			if (fashion.value > bestPurpose.value)
				bestPurpose = fashion;
			if (consume.value > bestPurpose.value)
				bestPurpose = consume;
			if (entertainment.value > bestPurpose.value)
				bestPurpose = entertainment;

			bestPurposeValue = bestPurpose.value;
			return bestPurpose;
		}

		public BaseRow getSecondPurpose()
		{
			BaseRow secondPurpose = new BaseRow();
			if (domestic.value > secondPurpose.value && domestic.value < bestPurposeValue)
				secondPurpose = domestic;
			if (international.value > secondPurpose.value && international.value < bestPurposeValue)
				secondPurpose = international;
			if (workAndStudy.value > secondPurpose.value && workAndStudy.value < bestPurposeValue)
				secondPurpose = workAndStudy;
			if (fashion.value > secondPurpose.value && fashion.value < bestPurposeValue)
				secondPurpose = fashion;
			if (consume.value > secondPurpose.value && consume.value < bestPurposeValue)
				secondPurpose = consume;
			if (entertainment.value > secondPurpose.value && entertainment.value < bestPurposeValue)
				secondPurpose = entertainment;
			return secondPurpose;
		}

		@Override
		public String toString()
		{
			return "mediaType:" + mediaType + ";domestic" + domestic + ";international" + international;
		}

	}

}
