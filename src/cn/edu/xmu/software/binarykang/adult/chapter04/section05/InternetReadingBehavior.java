package cn.edu.xmu.software.binarykang.adult.chapter04.section05;

import java.util.List;

import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.formatter.GF;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.MultiType;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.5.4网上阅读行为
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class InternetReadingBehavior extends AdultBaseAction
{
	private List<BaseRow> internetReadingRateData;
	//private List<BaseRow> internetReadingFrequencyData;

//	private List<BaseRow> searchBookInfo;
//	private List<BaseRow> searchBookContent;
//	private List<BaseRow> readingNews;
//	private List<BaseRow> readingEMagazine;
//	private List<BaseRow> readingENewspaper;
//	private List<BaseRow> readingEBook;
//	private List<List<BaseRow>> data;

	public InternetReadingBehavior(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		internetReadingRateData = ListFactory.getBaseRows();
		//internetReadingFrequencyData = ListFactory.getBaseRows();

//		searchBookInfo = ListFactory.getBaseRows();
//		searchBookContent = ListFactory.getBaseRows();
//		readingNews = ListFactory.getBaseRows();
//		readingEMagazine = ListFactory.getBaseRows();
//		readingENewspaper = ListFactory.getBaseRows();
//		readingEBook = ListFactory.getBaseRows();
//		data = ListFactory.getVarTypeRows();
//		data.add(searchBookInfo);
//		data.add(searchBookContent);
//		data.add(readingNews);
//		data.add(readingEMagazine);
//		data.add(readingENewspaper);
//		data.add(readingEBook);
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, internetReadingRateData, "网上从事的与阅读相关活动");
		// 读取上网从事阅读活动的频次相关的数据
//		BaseRow.read(xlsx, internetReadingFrequencyData,
//				xlsx.getRowByKey("上网从事阅读活动的频次") + 1, BEGIN_COL);
//
//		MultiType.read(xlsx, data, xlsx.getRowByKey("网民进行网上阅读活动的频率") + 1);
	}

	@Override
	protected void replace()
	{
		// 替换网上从事的与阅读相关活动 相关的变量
//		tr("${total_internet_reading_behavior_rate_value}",
//				pf(1 - internetReadingRateData.get(internetReadingRateData
//						.size() - 1).value));
		tr("${best_internet_reading_behavior_rate_key}",
				internetReadingRateData.get(0).key);
		tr("${best_internet_reading_behavior_rate_value}",
				pf(internetReadingRateData.get(0).value));
		tr("${second_internet_reading_behavior_rate_key}",
				internetReadingRateData.get(1).key);
		tr("${second_internet_reading_behavior_rate_value}",
				pf(internetReadingRateData.get(1).value));
		tr("${third_internet_reading_behavior_rate_key}",
				internetReadingRateData.get(2).key);
		tr("${third_internet_reading_behavior_rate_value}",
				pf(internetReadingRateData.get(2).value));
		tr("${fourth_internet_reading_behavior_rate_key}",
				internetReadingRateData.get(3).key);
		tr("${fourth_internet_reading_behavior_rate_value}",
				pf(internetReadingRateData.get(3).value));
//		tr("${fiveth_internet_reading_behavior_rate_key}",
//				internetReadingRateData.get(4).key);
//		tr("${fiveth_internet_reading_behavior_rate_value}",
//				pf(internetReadingRateData.get(4).value));
//		tr("${sixth_internet_reading_behavior_rate_key}",
//				internetReadingRateData.get(5).key);
//		tr("${sixth_internet_reading_behavior_rate_value}",
//				pf(internetReadingRateData.get(5).value));
		// 替换 上网从事阅读活动的频次 相关的变量
//		tr("${best_internet_reading_behavior_frequency_key}",
//				internetReadingFrequencyData.get(0).key);
//		tr("${best_internet_reading_behavior_frequency_value}",
//				tf(internetReadingFrequencyData.get(0).value));
//		tr("${second_internet_reading_behavior_frequency_key}",
//				internetReadingFrequencyData.get(1).key);
//		tr("${second_internet_reading_behavior_frequency_value}",
//				tf(internetReadingFrequencyData.get(1).value));
//		tr("${worst_internet_reading_behavior_frequency_key}",
//				internetReadingFrequencyData.get(internetReadingFrequencyData
//						.size() - 1).key);
//		tr("${worst_internet_reading_behavior_frequency_value}",
//				tf(internetReadingFrequencyData
//						.get(internetReadingFrequencyData.size() - 1).value));
		// 替换搜索图书信息相关的变量
//		tr("${at_least_once_a_month_search_book_info_value}",
//				pf(searchBookInfo.get(0).value + searchBookInfo.get(1).value
//						+ searchBookInfo.get(2).value
//						+ searchBookInfo.get(3).value));
//		BaseRow.sort(searchBookInfo);
//		tr("${best_search_book_info_key}", searchBookInfo.get(0).key);
//		tr("${best_search_book_info_value}", pf(searchBookInfo.get(0).value));
//		tr("${second_search_book_info_key}", searchBookInfo.get(1).key);
//		tr("${second_search_book_info_value}", pf(searchBookInfo.get(1).value));
//		tr("${third_search_book_info_key}", searchBookInfo.get(2).key);
//		tr("${third_search_book_info_value}", pf(searchBookInfo.get(2).value));
//		tr("${fourth_search_book_info_key}", searchBookInfo.get(3).key);
//		tr("${fourth_search_book_info_value}", pf(searchBookInfo.get(3).value));
//		// 替换搜索图书内容相关的变量
//		tr("${at_least_once_a_month_search_book_content_value}",
//				pf(searchBookContent.get(0).value
//						+ searchBookContent.get(1).value
//						+ searchBookContent.get(2).value
//						+ searchBookContent.get(3).value));
//		BaseRow.sort(searchBookContent);
//		tr("${best_search_book_content_key}", searchBookContent.get(0).key);
//		tr("${best_search_book_content_value}",
//				pf(searchBookContent.get(0).value));
//		tr("${second_search_book_content_key}", searchBookContent.get(1).key);
//		tr("${second_search_book_content_value}",
//				pf(searchBookContent.get(1).value));
//		tr("${third_search_book_content_key}", searchBookContent.get(2).key);
//		tr("${third_search_book_content_value}",
//				pf(searchBookContent.get(2).value));
//		tr("${fourth_search_book_content_key}", searchBookContent.get(3).key);
//		tr("${fourth_search_book_content_value}",
//				pf(searchBookContent.get(3).value));
//		// 替换网上阅读新闻相关的变量
//		tr("${no_more_than_once_a_month_search_book_content_value}",
//				pf(readingNews.get(4).value + readingNews.get(5).value
//						+ readingNews.get(6).value));
//		BaseRow.sort(readingNews);
//		tr("${best_reading_online_news_key}", readingNews.get(0).key);
//		tr("${best_reading_online_news_value}", pf(readingNews.get(0).value));
//		tr("${second_reading_online_news_key}", readingNews.get(1).key);
//		tr("${second_reading_online_news_value}", pf(readingNews.get(1).value));
//		tr("${third_reading_online_news_key}", readingNews.get(2).key);
//		tr("${third_reading_online_news_value}", pf(readingNews.get(2).value));
//		tr("${fourth_reading_online_news_key}", readingNews.get(3).key);
//		tr("${fourth_reading_online_news_value}", pf(readingNews.get(3).value));
//		// 替换阅读电子杂志、电子报纸、电子书相关的变量
//		tr("${almost_everyday_reading_emagazine_value}",
//				pf(readingEMagazine.get(0).value));
//		tr("${almost_everyday_reading_enewspaper_value}",
//				pf(readingENewspaper.get(0).value));
//		tr("${almost_everyday_reading_ebook_value}",
//				pf(readingEBook.get(0).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(internetReadingRateData);
		BaseRow.table(
				docx,
				internetReadingRateData,
//				"Resource/adult/chapter04/internet_reading_behavior_rate_odd_row.xml",
//				"Resource/adult/chapter04/internet_reading_behavior_rate_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
//		BaseRow.sort(internetReadingFrequencyData);
//		BaseRow.table(
//				docx,
//				internetReadingFrequencyData,
//				"Resource/adult/chapter04/internet_reading_behavior_frequency_odd_row.xml",
//				"Resource/adult/chapter04/internet_reading_behavior_frequency_even_row.xml",
//				GF.t);
//		// 生成网络阅读活动频率的表格
//		genLocalTable(
//				"Resource/adult/chapter04/internet_reading_activity_frequency_row.xml",
//				data);

	}

//	private void genLocalTable(String rowXML, List<List<BaseRow>> data)
//	{
//		Node tableNode = docx.getTableNode();
//		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
//		int rowNum = data.get(0).size();
//		int colNum = data.size();
//		for (int i = 0; i < rowNum; ++i)
//		{
//			XMLFragment xmlFragment = new XMLFragment(rowXML);
//			xmlFragment.replace("${key}", data.get(0).get(i).key);
//			for (int j = 0; j < colNum; ++j)
//			{
//				xmlFragment.replace(String.format("${value_%d}", j), pf(data
//						.get(j).get(i).value));
//			}
//			Node node = xmlFragment.getRootNode();
//			docx.insertBefore(tableNode, node, lastNode);
//		}
//		tableNode.removeChild(lastNode);
//	}

}
