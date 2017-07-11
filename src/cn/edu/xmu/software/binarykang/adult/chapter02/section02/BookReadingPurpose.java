package cn.edu.xmu.software.binarykang.adult.chapter02.section02;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.adult.parse.DocxHelper;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析2.2.1 ${city}居民读书目的认知
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 * 
 */
public final class BookReadingPurpose extends AdultBaseAction
{
	//private final String AREA_READING_PURPOSE_TABLE_KEY = "各区居民对读书目的的认知";

	private List<BookReadingPurpose.TableRow> data;
	private List<BaseRow> localReadingPurposeData;
	private List<BaseRow> countryReadingPurposeData;
	private List<DoubleValueRow> readingPurposeData;

	private List<DoubleValueRow> uvData;

	public BookReadingPurpose(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		localReadingPurposeData = ListFactory.getBaseRows();
		countryReadingPurposeData = ListFactory.getBaseRows();
		readingPurposeData = ListFactory.getDoubleValueRows();

		uvData = ListFactory.getDoubleValueRows();

		data = new ArrayList<BookReadingPurpose.TableRow>();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, localReadingPurposeData, "居民读书目的认知");
		BaseRow.read(xlsx, countryReadingPurposeData, "居民读书目的认知（全国）");
		DoubleValueRow.read(readingPurposeData, localReadingPurposeData,
				countryReadingPurposeData);

		DoubleValueRow.read(xlsx, uvData, "城乡居民读书目的差异性比较");

//		int areaReadingPurposeRowIndex = xlsx
//				.getRowByKey(AREA_READING_PURPOSE_TABLE_KEY);
//
//		// 读取平均阅读目的、地区阅读目的、城乡对比阅读目的的数据
//		XSSFRow areaReadingPurposeRow = null;
//		XSSFRow cityRow = xlsx.getRow(areaReadingPurposeRowIndex + 1);
//		for (int i = areaReadingPurposeRowIndex + 3; (areaReadingPurposeRow = xlsx
//				.getRow(i)).getLastCellNum() != -1; i++)
//		{
//			TableRow tableRow = new TableRow();
//			tableRow.key = xlsx
//					.getContent(areaReadingPurposeRow, BEGIN_COL + 1);
//			// 读取城市信息
//			for (int c = BEGIN_COL + 2; c < cityRow.getLastCellNum() && c < 20; c++)
//			{
//				tableRow.cityKey.add(xlsx.getContent(cityRow, c));
//			}
//			// 读取地区阅读目的数值
//			areaReadingPurposeRow = xlsx.getRow(i);
//			for (int c = BEGIN_COL + 2; c < areaReadingPurposeRow
//					.getLastCellNum() && c < 20; c++)
//			{
//				tableRow.cityValue.add(new Double(xlsx.getContent(
//						areaReadingPurposeRow, c)));
//			}
//
//			data.add(tableRow);
//		}
	}

	@Override
	protected void replace()
	{
		// 替换平均阅读目的相关的变量
//		BaseRow.sortExceptLast(localReadingPurposeData);
		
//		BaseRow.getRowByKey("增加知识，开阔眼界", localReadingPurposeData).value;
		tr("${best_reading_purpose_rate}",
				pf(BaseRow.getRowByKey("增加知识，开阔眼界", localReadingPurposeData).value));
		tr("${best_reading_purpose_key}", localReadingPurposeData.get(0).key);
		
		tr("${second_reading_purpose_rate}",
				pf(BaseRow.getRowByKey("打发时间/休闲消遣", localReadingPurposeData).value));
		tr("${second_reading_purpose_key}", localReadingPurposeData.get(1).key);		

		tr("${third_reading_purpose_rate}",
				pf(BaseRow.getRowByKey("满足兴趣爱好", localReadingPurposeData).value));
		tr("${third_reading_purpose_key}", localReadingPurposeData.get(2).key);
		
		tr("${fourth_reading_purpose_rate}",
				pf(BaseRow.getRowByKey("掌握一些实用技能", localReadingPurposeData).value));
		tr("${fourth_reading_purpose_key}", localReadingPurposeData.get(3).key);
		
		tr("${fiveth_reading_purpose_rate}",
				pf(BaseRow.getRowByKey("工作学习需要", localReadingPurposeData).value));
		tr("${fiveth_reading_purpose_key}", localReadingPurposeData.get(4).key);
		
		tr("${sixth_reading_purpose_rate}",
				pf(BaseRow.getRowByKey("提高修养", localReadingPurposeData).value));
		
		//DoubleValueRow.sortByV1(readingPurposeData);
		DoubleValueRow.sortByV1MinusV2(uvData);	//按照城乡差异最大判断
		tr("${best_urban_reading_purpose_key}", uvData.get(0).key);
		tr("${second_urban_reading_purpose_key}", uvData.get(1).key);
		tr("${third_urban_reading_purpose_key}", uvData.get(2).key);
		tr("${fourth_urban_reading_purpose_key}", uvData.get(3).key);
		tr("${fifth_urban_reading_purpose_key}", uvData.get(4).key);
		//DoubleValueRow.sortByV2(readingPurposeData);
		DoubleValueRow.sortByV2MinusV1(uvData);
		tr("${best_village_reading_purpose_key}", uvData.get(0).key);
		
		
	}

	@Override
	protected void chart()
	{
		// 生成平均阅读目的的表格
//		BaseRow.table(docx, localReadingPurposeData, "Resource/adult/chapter02/reading_purpose_odd_row.xml",
//				"Resource/adult/chapter02/reading_purpose_even_row.xml");
		
//		DoubleValueRow.table(docx, readingPurposeData,
//				"Resource/adult/chapter02/reading_purpose_odd_row.xml",
//				"Resource/adult/chapter02/reading_purpose_even_row.xml");
		BaseRow.sortExceptLast(localReadingPurposeData);
		
		BaseRow.table(docx,localReadingPurposeData, 
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
//		// 生成地区阅读目的的表格
//		Node areaReadingPurposeTableNode = docx.getNodeByTagNameAndIndex(
//				"w:tbl", DocxHelper.getTableIndex());
//		Node areaReadingPurposeTitleNode = docx.getChildByIndex(
//				areaReadingPurposeTableNode.getChildNodes(), "w:tr", 0);
//		Node areaReadingPurposeLastNode = docx.getLastChild(
//				areaReadingPurposeTableNode.getChildNodes(), "w:tr");
//
//		// 动态生成表头
//		Node areaReadingPurposeTitleLastCell = docx.getLastChild(
//				areaReadingPurposeTitleNode.getChildNodes(), "w:tc");
//		for (int i = 0; i < data.get(0).cityKey.size(); ++i)
//		{
//			XMLFragment xmlFragment = new XMLFragment(
//					"Resource/adult/chapter02/area_book_reading_purpose_title_cell.xml");
//			xmlFragment.replace("${area}", data.get(0).cityKey.get(i));
//			Node node = xmlFragment.getRootNode();
//			docx.insertBefore(areaReadingPurposeTitleNode, node,
//					areaReadingPurposeTitleLastCell);
//		}
//		areaReadingPurposeTitleNode
//				.removeChild(areaReadingPurposeTitleLastCell);
//		// 动态生成地区阅读目的表体
//		for (int i = 0; i < data.size(); ++i)
//		{
//			TableRow row = data.get(i);
//			XMLFragment xmlFragment = null;
//			if (i % 2 == 0)
//			{
//				xmlFragment = new XMLFragment(
//						"Resource/adult/chapter02/area_book_reading_purpose_odd_row.xml");
//			} else
//			{
//				xmlFragment = new XMLFragment(
//						"Resource/adult/chapter02/area_book_reading_purpose_even_row.xml");
//			}
//			Node lastCell = docx.getLastChild(xmlFragment.getRootNode()
//					.getChildNodes(), "w:tc");
//			for (int j = 0; j < row.cityValue.size(); ++j)
//			{
//				XMLFragment xmlCellFragment = null;
//				if (i % 2 == 0)
//				{
//					xmlCellFragment = new XMLFragment(
//							"Resource/adult/chapter02/area_book_reading_purpose_odd_cell.xml");
//				} else
//				{
//					xmlCellFragment = new XMLFragment(
//							"Resource/adult/chapter02/area_book_reading_purpose_even_cell.xml");
//				}
//				xmlCellFragment.replace("${rate}", pf(row.cityValue.get(j)));
//				Node newNode = lastCell.getOwnerDocument().importNode(
//						xmlCellFragment.getRootNode(), true);
//				xmlFragment.getRootNode().insertBefore(newNode, lastCell);
//			}
//			xmlFragment.getRootNode().removeChild(lastCell);
//			xmlFragment.replace("${purpose}", row.key);
//			docx.insertBefore(areaReadingPurposeTableNode,
//					xmlFragment.getRootNode(), areaReadingPurposeLastNode);
//		}
//		areaReadingPurposeTableNode.removeChild(areaReadingPurposeLastNode);

		 //生成城乡对比阅读目的的表格
//		DoubleValueRow
//				.table(docx,
//						uvData,
//						"Resource/adult/chapter02/urban_village_book_reading_purpose_odd_row.xml",
//						"Resource/adult/chapter02/urban_village_book_reading_purpose_even_row.xml");
		DoubleValueRow.sortExceptLastByV1(uvData);
		DoubleValueRow
		.table(docx,
				uvData,
				"Resource/doubleValueStandard.xml",
				"Resource/doubleValueStandard.xml");
		

	}

	private class TableRow
	{
		private String key;
		private List<String> cityKey;
		private List<Double> cityValue;

		public TableRow()
		{
			cityKey = ListFactory.getStringList();
			cityValue = ListFactory.getDoubleList();
		}
	}

}
