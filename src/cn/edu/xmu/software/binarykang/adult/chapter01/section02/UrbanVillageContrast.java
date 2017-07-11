package cn.edu.xmu.software.binarykang.adult.chapter01.section02;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析1.2成年人媒介使用目的
 * 
 * @author KangBin <bingyingsuolian@163.com>
 *
 */
public final class UrbanVillageContrast extends AdultBaseAction
{
	private final String DOMESTIC_TABLE_KEY = "城乡居民了解各类信息时借助的媒介——国内新闻时事";
	private final String INTERNATIONAL_TABLE_KEY = "城乡居民了解各类信息时借助的媒介——国内外观点和思潮";
	private final String WORK_AND_STUDY_TABLE_KEY = "城乡居民了解各类信息时借助的媒介——与工作学习有关的信息";
	private final String FASHION_TABLE_KEY = "城乡居民了解各类信息时借助的媒介——时尚流行趋势";
	private final String CONSUME_TABLE_KEY = "城乡居民了解各类信息时借助的媒介——生活/消费资讯";
	private final String ENTERTAINMENT_TABLE_KEY = "城乡居民了解各类信息时借助的媒介——休闲娱乐信息";
	private List<UrbanVillageContrast.TableRow> data;

	public UrbanVillageContrast(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = new ArrayList<UrbanVillageContrast.TableRow>();
	}

	@Override
	protected void readData()
	{
		// 读取替换表格的数据
		int domesticRowIndex = xlsx.getRowByKey(DOMESTIC_TABLE_KEY);
		int internationalRowIndex = xlsx.getRowByKey(INTERNATIONAL_TABLE_KEY);
		int workAndStudyRowIndex = xlsx.getRowByKey(WORK_AND_STUDY_TABLE_KEY);
		int fashionRowIndex = xlsx.getRowByKey(FASHION_TABLE_KEY);
		int consumeRowIndex = xlsx.getRowByKey(CONSUME_TABLE_KEY);
		int entertainmentRowIndex = xlsx.getRowByKey(ENTERTAINMENT_TABLE_KEY);

		XSSFRow domesticRow = null;
		XSSFRow internationalRow = null;
		XSSFRow wordAndStudyRow = null;
		XSSFRow fashionRow = null;
		XSSFRow consumeRow = null;
		XSSFRow entertainmentRow = null;

		for (int i = 3, j = domesticRowIndex + 3; (domesticRow = xlsx.getRow(j))
				.getLastCellNum() != -1; ++i, ++j)
		{
			TableRow tableRow = new TableRow();
			// 读取“国内新闻时事”表格的数据内容
			tableRow.mediaType = xlsx.getContent(domesticRow, BEGIN_COL + 1);
			tableRow.domestic.urban = new Double(xlsx.getContent(domesticRow,
					BEGIN_COL + 2));
			tableRow.domestic.village = new Double(xlsx.getContent(domesticRow,
					BEGIN_COL + 3));
			// 读取“国内外观点和思潮”表格的数据内容
			internationalRow = xlsx.getRow(internationalRowIndex + i);
			tableRow.international.urban = new Double(xlsx.getContent(
					internationalRow, BEGIN_COL + 2));
			tableRow.international.village = new Double(xlsx.getContent(
					internationalRow, BEGIN_COL + 3));
			// 读取“与工作学习有关的信息”表格的数据内容
			wordAndStudyRow = xlsx.getRow(workAndStudyRowIndex + i);
			tableRow.workAndStudy.urban = new Double(xlsx.getContent(
					wordAndStudyRow, BEGIN_COL + 2));
			tableRow.workAndStudy.village = new Double(xlsx.getContent(
					wordAndStudyRow, BEGIN_COL + 3));
			// 读取“时尚流行趋势”表格的数据内容
			fashionRow = xlsx.getRow(fashionRowIndex + i);
			tableRow.fashion.urban = new Double(xlsx.getContent(fashionRow,
					BEGIN_COL + 2));
			tableRow.fashion.village = new Double(xlsx.getContent(fashionRow,
					BEGIN_COL + 3));
			// 读取“生活/消费资讯”表格的数据内容
			consumeRow = xlsx.getRow(consumeRowIndex + i);
			tableRow.consume.urban = new Double(xlsx.getContent(consumeRow,
					BEGIN_COL + 2));
			tableRow.consume.village = new Double(xlsx.getContent(consumeRow,
					BEGIN_COL + 3));
			// 读取“休闲娱乐信息”表格的数据内容
			entertainmentRow = xlsx.getRow(entertainmentRowIndex + i);
			tableRow.entertainment.urban = new Double(xlsx.getContent(
					entertainmentRow, BEGIN_COL + 2));
			tableRow.entertainment.village = new Double(xlsx.getContent(
					entertainmentRow, BEGIN_COL + 3));
			data.add(tableRow);
		}
	}

	@Override
	protected void replace()
	{

	}

	@Override
	protected void chart()
	{
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < data.size(); ++i)
		{
			TableRow row = data.get(i);
			XMLFragment xmlFragment = new XMLFragment(
					"Resource/adult/chapter01/urban_village_contrast_row.xml");
			xmlFragment.replace("${media_type}", row.mediaType);
			xmlFragment.replace("${media_domestic_urban}",
					pf(row.domestic.urban));
			xmlFragment.replace("${media_domestic_village}",
					pf(row.domestic.village));
			xmlFragment.replace("${media_international_urban}",
					pf(row.international.urban));
			xmlFragment.replace("${media_international_village}",
					pf(row.international.village));
			xmlFragment.replace("${media_word_and_study_urban}",
					pf(row.workAndStudy.urban));
			xmlFragment.replace("${media_word_and_study_village}",
					pf(row.workAndStudy.village));
			xmlFragment
					.replace("${media_consume_urban}", pf(row.consume.urban));
			xmlFragment.replace("${media_consume_village}",
					pf(row.consume.village));
			xmlFragment
					.replace("${media_fashion_urban}", pf(row.fashion.urban));
			xmlFragment.replace("${media_fashion_village}",
					pf(row.fashion.village));
			xmlFragment.replace("${media_entertainment_urban}",
					pf(row.entertainment.urban));
			xmlFragment.replace("${media_entertainment_village}",
					pf(row.entertainment.village));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

	private class TableRow
	{
		private String mediaType;
		private Value domestic;
		private Value international;
		private Value workAndStudy;
		private Value fashion;
		private Value consume;
		private Value entertainment;

		public TableRow()
		{
			domestic = new Value();
			international = new Value();
			workAndStudy = new Value();
			fashion = new Value();
			consume = new Value();
			entertainment = new Value();
		}

		private class Value
		{
			private double urban;
			private double village;
		}
	}

}
