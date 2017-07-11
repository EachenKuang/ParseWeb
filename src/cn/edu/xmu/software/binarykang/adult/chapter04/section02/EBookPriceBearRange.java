package cn.edu.xmu.software.binarykang.adult.chapter04.section02;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.SingleValue;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>4.2.3������۸��������
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 * 
 */
public final class EBookPriceBearRange extends AdultBaseAction
{
	private double averageLocalEBookPrice;
	private double averageCountryEBookPrice;
	private List<BaseRow> localData;
	private List<BaseRow> countryData;

	public EBookPriceBearRange(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		localData = ListFactory.getBaseRows();
		countryData = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		averageLocalEBookPrice = SingleValue.read(xlsx, "4.2.3������۸�������� ��һ�������еĿɽ���һ���������ƽ���۸�", 1, 2);
		averageCountryEBookPrice = SingleValue.read(xlsx, "4.2.3������۸�������� ��һ�������еĿɽ���һ���������ƽ���۸�ȫ����", 1, 2);

		BaseRow.read(xlsx, localData, "����������۸��������");
		BaseRow.read(xlsx, countryData, "4.2.3������۸����������һ�� �ܹ����ܸ��������Ķ��ı�����ȫ����");
	}

	@Override
	protected void replace()
	{
		tr("${average_ebook_price_bear_range_value}", tf(averageLocalEBookPrice));
		tr("${country_average_ebook_price_bear_range_value}", tf(averageCountryEBookPrice));
		tr("${local_minus_country_average_ebook_price_bear_range_key}",
				averageLocalEBookPrice > averageCountryEBookPrice ? "��" : "��");
		tr("${local_minus_country_average_ebook_price_bear_range_value}",
				tf(Math.abs(averageLocalEBookPrice - averageCountryEBookPrice)));
		
		tr("${can_accept_pay_ebook_rate}", pf(1 - localData.get(localData.size() - 1).value));
		tr("${country_can_accept_pay_ebook_rate}",pf(1 - countryData.get(localData.size() - 1).value));
		tr("${local_minus_country_can_accept_pay_ebook_rate_key}",
				countryData.get(countryData.size() - 1).value > localData.get(localData.size() - 1).value ? "��" : "��");
		tr("${local_minus_country_can_accept_pay_ebook_rate_value}",
				of(Math.abs(countryData.get(countryData.size() - 1).value - localData.get(localData.size() - 1).value)));
	}

	@Override
	protected void chart()
	{
		BaseRow.table(docx, localData, 
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
	}

}
