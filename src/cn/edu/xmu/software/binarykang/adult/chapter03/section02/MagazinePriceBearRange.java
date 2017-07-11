package cn.edu.xmu.software.binarykang.adult.chapter03.section02;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����=>3.2.2.2 ${city}�����ڿ��۸��������
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class MagazinePriceBearRange extends AdultBaseAction
{
	private List<BaseRow> data;
	private List<DoubleValueRow> uvData;

	private double localAverageMagazinePriceBearRange;
	private double countryAverageMagazinePriceBearRange;
	private double urbanAverageMagazinePriceBearRange;
	private double villageAverageMagazinePriceBearRange;

	public MagazinePriceBearRange(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
		uvData = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "�����ڿ��۸��������");
		DoubleValueRow.read(xlsx, uvData, "�����ڿ��۸���������Ա�");

		XSSFRow localRow = xlsx.getRow(xlsx
				.getRowByKey("3.2.2.2 **�о����ڿ��۸�������� ��һ�������е��ڿ��ɽ��ܼ۸񣨵��أ�") + 3);
		XSSFRow countryRow = xlsx.getRow(xlsx
				.getRowByKey("3.2.2.2 **�о����ڿ��۸�������� ��һ�������е��ڿ��ɽ��ܼ۸�ȫ����") + 3);
		localAverageMagazinePriceBearRange = new Double(xlsx.getContent(
				localRow, BEGIN_COL + 1));
		urbanAverageMagazinePriceBearRange = new Double(xlsx.getContent(
				localRow, BEGIN_COL + 2));
		villageAverageMagazinePriceBearRange = new Double(xlsx.getContent(
				localRow, BEGIN_COL + 3));
		countryAverageMagazinePriceBearRange = new Double(xlsx.getContent(
				countryRow, BEGIN_COL + 1));
	}

	@Override
	protected void replace()
	{
		// �����滻
		tr("${local_magazine_price_bear_range_value}",
				tf(localAverageMagazinePriceBearRange));
		tr("${country_magazine_price_bear_range_value}",
				tf(countryAverageMagazinePriceBearRange));
		tr("${local_minus_country_magazine_price_bear_range_key}",
				localAverageMagazinePriceBearRange > countryAverageMagazinePriceBearRange ? "��"
						: "��");
		tr("${local_minus_country_magazine_price_bear_range_value}",
				tf(Math.abs(localAverageMagazinePriceBearRange-countryAverageMagazinePriceBearRange)));
		//
		tr("${urban_magazine_price_bear_range_value}",
				tf(urbanAverageMagazinePriceBearRange));
		tr("${village_magazine_price_bear_range_value}",
				tf(villageAverageMagazinePriceBearRange));
		tr("${urban_minus_village_magazine_price_bear_range_key}",
				urbanAverageMagazinePriceBearRange > villageAverageMagazinePriceBearRange ? "��"
						: "��");
		// �滻�����ڿ��۸����������صı���
		tr("${most_cheap_magazine_price_bear_range_value}",
				pf(1 - data.get(data.size() - 1).value - data.get(0).value));
		tr("${most_cheap_magazine_price_bear_range_key}",
				data.get(data.size() - 1).key);
		tr("${most_expensive_magazine_price_bear_range_value}",
				pf(data.get(data.size() - 1).value));
		BaseRow.sort(data);
		tr("${best_magazine_price_bear_range_key}", data.get(0).key);
		tr("${best_magazine_price_bear_range_value}", pf(data.get(0).value));
		tr("${from_3_to_6_magazine_price_bear_range_value}", pf(BaseRow.getRowByKey("3��6Ԫ", data).value));
		tr("${from_6_to_9_magazine_price_bear_range_value}", pf(BaseRow.getRowByKey("6��9Ԫ", data).value));
		tr("${from_9_to_19_magazine_price_bear_range_value}", pf(BaseRow.getRowByKey("9��19Ԫ", data).value));
		tr("${from_19_magazine_price_bear_range_value}", pf(BaseRow.getRowByKey("19Ԫ������", data).value));
		// �滻�����ڿ��۸���������Ա���صı���
		DoubleValueRow.sortByV1(uvData);
		tr("${best_urban_magazine_price_bear_range_key}", uvData.get(0).key);
		tr("${best_urban_magazine_price_bear_range_value}",
				pf(uvData.get(0).v1));
		tr("${best_urban_minus_village_magazine_price_bear_range_key}",
				uvData.get(0).v1 > uvData.get(0).v2 ? "��" : "��");
		tr("${best_urban_minus_village_magazine_price_bear_range_value}",
				of(Math.abs(uvData.get(0).v1 - uvData.get(0).v2)));

		DoubleValueRow.sortByV2(uvData);
		tr("${best_village_magazine_price_bear_range_key}", uvData.get(0).key);
		tr("${best_village_magazine_price_bear_range_value}",
				pf(uvData.get(0).v2));
		tr("${best_village_minus_urban_magazine_price_bear_range_key}",
				uvData.get(0).v2 > uvData.get(0).v1 ? "��" : "��");
		tr("${best_village_minus_urban_magazine_price_bear_range_value}",
				of(Math.abs(uvData.get(0).v2 - uvData.get(0).v1)));
	}

	@Override
	protected void chart()
	{
		BaseRow.chart(data);// chart16
		DoubleValueRow.reverseValue(uvData);
		//DoubleValueRow.chart(uvData);// chart17
		//DoubleValueRow.reverseValue(uvData);
	}

}
