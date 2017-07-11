package cn.edu.xmu.software.binarykang.adult.chapter07.section01;

import java.util.List;

import org.w3c.dom.Node;

import com.sun.accessibility.internal.resources.accessibility;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.DoubleValueRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.MultiType;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>7.1.3城镇居民对公共文化服务设施的满意度
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class UrbanPublicCultureServiceSatisfactionRate extends
		AdultBaseAction
{
	private List<BaseRow> publicLibrarySatisfactionRate;
	private List<BaseRow> communityLibrarySatisfactionRate;
	private List<BaseRow> pressBarSatisfactionRate;
	private List<List<BaseRow>> satisfactionRate;
//	private List<DoubleValueRow> satisfacitonSort;

	public UrbanPublicCultureServiceSatisfactionRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		publicLibrarySatisfactionRate = ListFactory.getBaseRows();
		communityLibrarySatisfactionRate = ListFactory.getBaseRows();
		pressBarSatisfactionRate = ListFactory.getBaseRows();
		
//		satisfacitonSort = ListFactory.getDoubleValueRows();
		
		satisfactionRate = ListFactory.getVarTypeRows();
		satisfactionRate.add(publicLibrarySatisfactionRate);
		satisfactionRate.add(communityLibrarySatisfactionRate);
		satisfactionRate.add(pressBarSatisfactionRate);
	}

	@Override
	protected void readData()
	{
		MultiType.read(xlsx, satisfactionRate,
				xlsx.getRowByKey("城镇居民对各类公共文化服务设施的满意度") + 1);
//		satisfacitonSort.add("3",2,1);
	}

	@Override
	protected void replace()
	{  
//		公共图书馆  社区阅览室∕社区书屋  报刊栏
//		String library = "";
//		String room  = "";
//		String news = "";
//		a[library]=
//		publicLibrarySatisfactionRate.
		tr("${public_library_satisfaction_rate}",
				pf(publicLibrarySatisfactionRate.get(0).value
						+ publicLibrarySatisfactionRate.get(1).value));
		tr("${community_library_satisfaction_rate}",
				pf(communityLibrarySatisfactionRate.get(0).value
						+ communityLibrarySatisfactionRate.get(1).value));
		tr("${press_bar_library_satisfaction_rate}",
				pf(pressBarSatisfactionRate.get(0).value
						+ pressBarSatisfactionRate.get(1).value));
		
		tr("${public_library_not_satisfaction_rate}",
				pf(publicLibrarySatisfactionRate.get(3).value
						+ publicLibrarySatisfactionRate.get(4).value));
		tr("${community_library_not_satisfaction_rate}",
				pf(communityLibrarySatisfactionRate.get(3).value
						+ communityLibrarySatisfactionRate.get(4).value));
		tr("${press_bar_library_not_satisfaction_rate}",
				pf(pressBarSatisfactionRate.get(3).value
						+ pressBarSatisfactionRate.get(4).value));
	}

	@Override
	protected void chart()
	{
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < publicLibrarySatisfactionRate.size()-1; ++i)
		{
			BaseRow row = publicLibrarySatisfactionRate.get(i);
			XMLFragment xmlFragment = new XMLFragment(
					"Resource/adult/chapter07/community_services_satisfaction_rate_row.xml");

			xmlFragment.replace("${key}", row.key);
			xmlFragment.replace("${library_satisfaction_rate}", pf(row.value));
			xmlFragment.replace("${community_library_satisfaction_rate}",
					pf(communityLibrarySatisfactionRate.get(i).value));
			xmlFragment.replace("${press_bar_satisfaction_rate}",
					pf(pressBarSatisfactionRate.get(i).value));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
		tableNode.removeChild(lastNode);
	}

}
