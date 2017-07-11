package cn.edu.xmu.software.binarykang.adult.chapter02.section04;

import java.util.List;

import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>2.4分类图书市场
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class ClassifyBookMarket extends AdultBaseAction
{
	private List<BaseRow> preferenceDegree;
	private List<BaseRow> purchaseRate;
	private List<BaseRow> marketGapDegree;
	private List<BaseRow> preOrderRate;

	public ClassifyBookMarket(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		preferenceDegree = ListFactory.getBaseRows();
		purchaseRate = ListFactory.getBaseRows();
		marketGapDegree = ListFactory.getBaseRows();
		preOrderRate = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, preferenceDegree, "分类图书市场占有情况（1）——偏好度");
		BaseRow.read(xlsx, purchaseRate, "分类图书市场占有情况（2）——购买率");
		BaseRow.read(xlsx, marketGapDegree, "分类图书市场占有情况（3）——市场空缺度");
		BaseRow.read(xlsx, preOrderRate, "分类图书市场占有情况（4）——预购率");
	}

	@Override
	protected void replace()
	{
		purchaseRate.remove(purchaseRate.size() - 1);
		preferenceDegree.remove(preferenceDegree.size() - 1);

		BaseRow.sort(preferenceDegree);
		tr("${best_preference_degree_key}", preferenceDegree.get(0).key);
		tr("${best_preference_degree_value}", pf(preferenceDegree.get(0).value));
		tr("${second_preference_degree_key}", preferenceDegree.get(1).key);
		tr("${second_preference_degree_value}", pf(preferenceDegree.get(1).value));
		tr("${third_preference_degree_key}", preferenceDegree.get(2).key);
		tr("${third_preference_degree_value}", pf(preferenceDegree.get(2).value));
		tr("${fourth_preference_degree_key}", preferenceDegree.get(3).key);
		tr("${fourth_preference_degree_value}", pf(preferenceDegree.get(3).value));
		tr("${fifth_preference_degree_key}", preferenceDegree.get(4).key);
		tr("${fifth_preference_degree_value}", pf(preferenceDegree.get(4).value));
		tr("${sixth_preference_degree_key}", preferenceDegree.get(5).key);
		tr("${seventh_preference_degree_key}", preferenceDegree.get(6).key);
		tr("${eighth_preference_degree_key}", preferenceDegree.get(7).key);
		tr("${ninth_preference_degree_key}", preferenceDegree.get(8).key);
		tr("${tenth_preference_degree_key}", preferenceDegree.get(9).key);
		tr("${eleventh_preference_degree_key}", preferenceDegree.get(10).key);
		
		BaseRow.sort(purchaseRate);
		tr("${best_purchase_rate_key}", purchaseRate.get(0).key);
		tr("${best_purchase_rate_value}", pf(purchaseRate.get(0).value));
		tr("${second_purchase_rate_key}", purchaseRate.get(1).key);
		tr("${third_purchase_rate_key}", purchaseRate.get(2).key);
		tr("${fourth_purchase_rate_key}", purchaseRate.get(3).key);
		tr("${second_purchase_rate_value}", pf(purchaseRate.get(1).value));
		tr("${third_purchase_rate_value}", pf(purchaseRate.get(2).value));
		tr("${fourth_purchase_rate_value}", pf(purchaseRate.get(3).value));
		tr("${fifth_purchase_rate_key}", purchaseRate.get(4).key);
		tr("${sixth_purchase_rate_key}", purchaseRate.get(5).key);
		tr("${seventh_purchase_rate_key}", purchaseRate.get(6).key);
		
		preOrderRate.remove(preOrderRate.size() - 1);
		BaseRow.sort(preOrderRate);
		tr("${best_pre_order_key}", preOrderRate.get(0).key);
		tr("${second_pre_order_key}", preOrderRate.get(1).key);
		tr("${third_pre_order_key}", preOrderRate.get(2).key);
		tr("${fourth_pre_order_key}", preOrderRate.get(3).key);
		marketGapDegree.remove(marketGapDegree.size() - 1);

		BaseRow.sort(marketGapDegree);
		tr("${best_market_gap_key}", marketGapDegree.get(0).key);
		tr("${best_market_gap_value}", pf(marketGapDegree.get(0).value));
		tr("${second_market_gap_key}", marketGapDegree.get(1).key);
		tr("${second_market_gap_value}", pf(marketGapDegree.get(1).value));
		tr("${third_market_gap_key}", marketGapDegree.get(2).key);
		tr("${third_market_gap_value}", pf(marketGapDegree.get(3).value));
		tr("${fourth_market_gap_key}", marketGapDegree.get(3).key);
		tr("${fourth_market_gap_value}", pf(marketGapDegree.get(3).value));
	}

	@Override
	protected void chart()
	{
		// 这种表格无法统一，故独立写出来
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < preferenceDegree.size(); i++)
		{
			BaseRow row = preferenceDegree.get(i);
			XMLFragment xmlFragment = null;
			if (i % 2 == 0)
			{
				xmlFragment = new XMLFragment(
						"Resource/adult/chapter02/classify_book_market_even_row.xml");
			}
			else
			{
				xmlFragment = new XMLFragment(
						"Resource/adult/chapter02/classify_book_market_even_row.xml");
			}

			xmlFragment.replace("${key}", row.key);
			xmlFragment.replace("${preference_degree}", pf(row.value));
			xmlFragment.replace("${purchase_rate}",
					pf(purchaseRate.get(i).value));
			xmlFragment.replace("${market_gap_degree}",
					pf(marketGapDegree.get(i).value));
			xmlFragment.replace("${pre_order_rate}",
					pf(preOrderRate.get(i).value));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

}
