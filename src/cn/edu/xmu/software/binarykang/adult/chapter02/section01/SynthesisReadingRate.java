package cn.edu.xmu.software.binarykang.adult.chapter02.section01;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * ����2.1.2�ۺ��Ķ���
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class SynthesisReadingRate extends AdultBaseAction
{
	private List<BaseRow> localData;
	private List<BaseRow> countryData;
	//private List<BaseRow> localEData;
	//private List<BaseRow> countryEData;
	private List<BaseRow> areaData;
	private List<DoubleValueRow> lcData;

	public SynthesisReadingRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		localData = ListFactory.getBaseRows();
		countryData = ListFactory.getBaseRows();
		lcData = ListFactory.getDoubleValueRows();
		//localEData = ListFactory.getBaseRows();
		//countryEData = ListFactory.getBaseRows();
		areaData = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.readInterval(xlsx, localData, "��ý���Ķ�����ȫ��ƽ��ˮƽ�Ƚϣ����أ�");
		BaseRow.readInterval(xlsx, countryData, "��ý���Ķ�����ȫ��ƽ��ˮƽ�Ƚϣ�ȫ����");
		DoubleValueRow.read(lcData, countryData, localData);
//		BaseRow.readInterval(xlsx, localEData, "��28ҳ��һ���е����ֵ����顢������־�����ӱ��Ķ���");
//		BaseRow.readInterval(xlsx, countryEData,
//				"��28ҳ��һ���е����ֵ����顢������־�����ӱ��Ķ��ʣ�ȫ����");

		BaseRow.read(xlsx, areaData, xlsx.getRowByKey("�������ۺ��Ķ��ʱȽ�") + 3,
				BEGIN_COL + 1);
	}

	@Override
	protected void replace()
	{
		tr("${local_synthesis_reading_rate}", pf(localData.get(4).value));
		tr("${country_synthesis_reading_rate}", pf(countryData.get(4).value));
		tr("${local_minus_country_synthesis_reading_rate_key}",
				localData.get(4).value > countryData.get(4).value ? "��" : "��");
		tr("${local_minus_country_synthesis_reading_rate}",
				of(Math.abs(localData.get(4).value - countryData.get(4).value)));
		
		tr("${local_book_synthesis_reading_rate}", pf(localData.get(0).value));
		tr("${country_book_synthesis_reading_rate}", pf(countryData.get(0).value));
		tr("${local_minus_country_book_synthesis_reading_rate_key}",
				localData.get(0).value > countryData.get(0).value ? "��" : "��");
		tr("${local_minus_country_book_synthesis_reading_rate}",
				of(Math.abs(localData.get(0).value - countryData.get(0).value)));
		
		tr("${local_newspaper_synthesis_reading_rate}", pf(localData.get(1).value));
		tr("${country_newspaper_synthesis_reading_rate}", pf(countryData.get(1).value));
		tr("${local_minus_country_newspaper_synthesis_reading_rate_key}",
				localData.get(1).value > countryData.get(1).value ? "�߳�" : "�ͳ�");
		tr("${local_minus_country_newspaper_synthesis_reading_rate}",
				of(Math.abs(localData.get(1).value - countryData.get(1).value)));
		
		tr("${local_magazine_synthesis_reading_rate}", pf(localData.get(2).value));
		tr("${country_magazine_synthesis_reading_rate}", pf(countryData.get(2).value));
		tr("${local_minus_country_magazine_synthesis_reading_rate_key}",
				localData.get(2).value > countryData.get(2).value ? "�߳�" : "�ͳ�");
		tr("${local_minus_country_magazine_synthesis_reading_rate}",
				of(Math.abs(localData.get(2).value - countryData.get(2).value)));
		
		tr("${local_digit_synthesis_reading_rate}", pf(localData.get(3).value));
		tr("${country_digit_synthesis_reading_rate}", pf(countryData.get(3).value));
		tr("${local_minus_country_digit_synthesis_reading_rate_key}",
				localData.get(3).value > countryData.get(3).value ? "�߳�" : "�ͳ�");
		tr("${local_minus_country_digit_synthesis_reading_rate}",
				of(Math.abs(localData.get(3).value - countryData.get(3).value)));
		// �滻��28ҳ��һ���е����ֵ����顢������־�����ӱ��Ķ���
//		tr("${local_ebook_reading_rate}", pf(localEData.get(0).value));
//		tr("${local_emagazine_reading_rate}", pf(localEData.get(1).value));
//		tr("${local_enewspaper_reading_rate}", pf(localEData.get(2).value));
//		tr("${country_ebook_reading_rate}", pf(countryEData.get(0).value));
//		tr("${country_emagazine_reading_rate}", pf(countryEData.get(1).value));
//		tr("${country_enewspaper_reading_rate}", pf(countryEData.get(2).value));
		// �滻�������ۺ��Ķ��ʱȽ�
		BaseRow.sort(areaData);
		tr("${best_area_synthesis_reading_rate_key}", areaData.get(0).key);
		tr("${best_area_synthesis_reading_rate_value}",
				pf(areaData.get(0).value));
		
		int higherThanLocalAreaSynthesisReadingRateNum = 0;
		String higherThanLocalAreaSynthesisReadingRateKeys = "";
		for(int i=0; i<=areaData.size()-1; i++){
			if(areaData.get(i).value>localData.get(4).value){
				higherThanLocalAreaSynthesisReadingRateNum++;
				higherThanLocalAreaSynthesisReadingRateKeys += (areaData.get(i).key+"��");
			}
		}
		tr("${higher_than_local_area_synthesis_reading_rate_num}", tf(higherThanLocalAreaSynthesisReadingRateNum));
		tr("${higher_than_local_area_synthesis_reading_rate_keys}", 
				higherThanLocalAreaSynthesisReadingRateKeys.substring(0, higherThanLocalAreaSynthesisReadingRateKeys.length()-1));
	}

	@Override
	protected void chart()
	{
		// ����=>ͼ2��1��2 ${city}18��70��������ý���Ķ�����${country}ƽ��ˮƽ�Ƚ�
		Map<String, String> replaceData = new LinkedHashMap<String, String>();
		String city = xlsx.getContent(xlsx.getRow(0), 1);
		String country = xlsx.getContent(xlsx.getRow(0), 2);
		replaceData.put("${city}", city);
		replaceData.put("${country}", country);
		DoubleValueRow.chart(lcData, country, city, replaceData);// chart3

		// �滻�������ۺ��Ķ��ʱȽ�
		BaseRow.sort(areaData);
		BaseRow.tableWithIndex(docx, areaData,
				"Resource/adult/chapter02/synthesis_reading_rate_row.xml");
	}

}
