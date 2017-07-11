package cn.edu.xmu.software.binarykang.adult.chapter01.section01;

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
 * 解析1.1.2媒介接触时长及变化
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-14
 *
 */
public final class MediaContactTime extends AdultBaseAction
{
	private List<BaseRow> local;
	private List<BaseRow> country;
	private List<DoubleValueRow> lcData;

	private List<DoubleValueRow> traditionMedia;
	private List<DoubleValueRow> newMedia;

	public MediaContactTime(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		local = ListFactory.getBaseRows();
		country = ListFactory.getBaseRows();
		lcData = ListFactory.getDoubleValueRows();
		traditionMedia = ListFactory.getDoubleValueRows();
		newMedia = ListFactory.getDoubleValueRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, local, xlsx.getRowByKey("各媒介接触时长对比（当地）") + 1,
				BEGIN_COL);
		BaseRow.read(xlsx, country, xlsx.getRowByKey("各媒介接触时长对比（全国）") + 1,
				BEGIN_COL);
		DoubleValueRow.read(lcData, country, local);

		traditionMedia.add(DoubleValueRow.getRowByKey("图书", lcData));
		traditionMedia.add(DoubleValueRow.getRowByKey("报纸", lcData));
		traditionMedia.add(DoubleValueRow.getRowByKey("杂志", lcData));

		newMedia.add(DoubleValueRow.getRowByKey("互联网", lcData));
		newMedia.add(DoubleValueRow.getRowByKey("Pad（平板电脑）", lcData));
		newMedia.add(DoubleValueRow.getRowByKey("手机阅读", lcData));
		newMedia.add(DoubleValueRow.getRowByKey("电子阅读器", lcData));
	}

	@Override
	protected void replace()
	{
		DoubleValueRow.sortByV1(traditionMedia);
		tr("${best_tradition_media}", traditionMedia.get(0).key);

		DoubleValueRow bookRow = DoubleValueRow.getRowByKey("图书", lcData);
		tr("${local_book_time}", tf(bookRow.v2));
		tr("${country_book_time}", tf(bookRow.v1));
		tr("${local_minus_country_book}", bookRow.v2 > bookRow.v1 ? "多出" : "少");
		tr("${local_minus_country_book_time}",
				tf(Math.abs(bookRow.v1 - bookRow.v2)));

		DoubleValueRow newspaperRow = DoubleValueRow.getRowByKey("报纸", lcData);
		tr("${local_newspaper_time}", tf(newspaperRow.v2));
		tr("${country_newspaper_time}", tf(newspaperRow.v1));
		tr("${local_minus_country_newspaper}",
				newspaperRow.v2 > newspaperRow.v1 ? "多出" : "少");
		tr("${local_minus_country_newspaper_time}",
				tf(Math.abs(newspaperRow.v1 - newspaperRow.v2)));

		DoubleValueRow periodicalRow = DoubleValueRow.getRowByKey("杂志", lcData);
		tr("${local_periodical_time}", tf(periodicalRow.v2));
		tr("${country_periodical_time}", tf(periodicalRow.v1));
		tr("${local_minus_country_periodical}",
				periodicalRow.v2 > periodicalRow.v1 ? "多出" : "少");
		tr("${local_minus_country_periodical_time}",
				tf(Math.abs(periodicalRow.v2 - periodicalRow.v1)));

		DoubleValueRow tvRow = DoubleValueRow.getRowByKey("电视", lcData);
		tr("${local_tv_time}", tf(tvRow.v2));
		tr("${country_tv_time}", tf(tvRow.v1));
		tr("${tv_diff}", tvRow.v2 > tvRow.v1 ? "多出" : "少");
		tr("${tv_diff_time}", tf(Math.abs(tvRow.v2 - tvRow.v1)));

		DoubleValueRow broadcastRow = DoubleValueRow.getRowByKey("广播", lcData);
		tr("${local_broadcast_time}", tf(broadcastRow.v2));
		tr("${country_broadcast_time}", tf(broadcastRow.v1));
		tr("${broadcast_diff}", broadcastRow.v2 > broadcastRow.v1 ? "多出" : "低出");
		tr("${broadcast_diff_time}",tf(Math.abs(broadcastRow.v2 - broadcastRow.v1)));
		
		DoubleValueRow.sortByV1(newMedia);
		DoubleValueRow bestNewMedia = newMedia.get(0);
		tr("${local_best_new_media}", bestNewMedia.key);
		tr("${local_best_new_media_time}", tf(bestNewMedia.v2));
		tr("${country_best_new_media_time}", tf(bestNewMedia.v1));
		tr("${best_new_media_diff}", bestNewMedia.v2 > bestNewMedia.v1 ? "高出"
				: "低出");
		tr("${best_new_diff_time}",tf(Math.abs(bestNewMedia.v1 - bestNewMedia.v2)));

		DoubleValueRow secondNewMedia = newMedia.get(1);
		tr("${local_second_new_media}", secondNewMedia.key);
		tr("${local_second_new_media_time}", tf(secondNewMedia.v2));
		tr("${country_second_new_media_time}", tf(secondNewMedia.v1));
		tr("${second_new_media_diff}", secondNewMedia.v2 > secondNewMedia.v1 ? "高出"
				: "低出");
		tr("${second_new_diff_time}",tf(Math.abs(secondNewMedia.v1 - secondNewMedia.v2)));

		DoubleValueRow thirdNewMedia = newMedia.get(2);
		tr("${local_third_new_media}", thirdNewMedia.key);
		tr("${local_third_new_media_time}", tf(thirdNewMedia.v2));
		tr("${country_third_new_media_time}", tf(thirdNewMedia.v1));
		tr("${third_new_media_diff}", thirdNewMedia.v2 > thirdNewMedia.v1 ? "高出"
				: "低出");
		tr("${third_new_diff_time}",tf(Math.abs(thirdNewMedia.v1 - thirdNewMedia.v2)));
		
		DoubleValueRow fourthNewMedia = newMedia.get(3);
		tr("${local_fourth_new_media}", fourthNewMedia.key);
		tr("${local_fourth_new_media_time}", tf(fourthNewMedia.v2));
		tr("${country_fourth_new_media_time}", tf(fourthNewMedia.v1));
		tr("${fourth_new_media_diff}", fourthNewMedia.v2 > fourthNewMedia.v1 ? "高出"
				: "低出");
		tr("${fourth_new_diff_time}",tf(Math.abs(fourthNewMedia.v1 - fourthNewMedia.v2)));
	}

	@Override
	protected void chart()
	{
		Map<String, String> replaceData = new LinkedHashMap<String, String>();
		String city = xlsx.getContent(xlsx.getRow(0), 1);
		String country = xlsx.getContent(xlsx.getRow(0), 2);
		replaceData.put("${city}", city);
		replaceData.put("${country}", country);
		DoubleValueRow.chart(lcData, country, city, replaceData, 2);// chart1
	}

}
