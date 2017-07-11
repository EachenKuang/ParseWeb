package cn.edu.xmu.software.binarykang.adult.chapter07.section01;

import java.util.List;

import org.w3c.dom.Node;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.common.rowtype.MultiType;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.word.XMLFragment;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>7.1.2城镇居民对公共文化服务设施的使用率
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class UrbanPublicCultureServiceUseRate extends AdultBaseAction
{
	private List<BaseRow> useRate;
	private List<BaseRow> useWhenKnowRate;

	private List<BaseRow> libraryUseFrequency;
	private List<BaseRow> communityLibraryUseFrequency;
	private List<BaseRow> pressBarUseFrequency;
	private List<List<BaseRow>> useFrequency;

	public UrbanPublicCultureServiceUseRate(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		useRate = ListFactory.getBaseRows();
		useWhenKnowRate = ListFactory.getBaseRows();
		libraryUseFrequency = ListFactory.getBaseRows();
		communityLibraryUseFrequency = ListFactory.getBaseRows();
		pressBarUseFrequency = ListFactory.getBaseRows();
		useFrequency = ListFactory.getVarTypeRows();
		useFrequency.add(libraryUseFrequency);
		useFrequency.add(communityLibraryUseFrequency);
		useFrequency.add(pressBarUseFrequency);
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, useRate, "城镇居民对各类公共文化服务设施的使用率（2）——使用率");
		BaseRow.read(xlsx, useWhenKnowRate,
				xlsx.getRowByKey("城镇居民对各类公共文化服务设施的使用率（3）——知晓者平均使用频率") + 1,
				BEGIN_COL);
		MultiType.read(xlsx, useFrequency,
				xlsx.getRowByKey("城镇居民对各类公共文化服务设施的使用率（1）——使用频率") + 1);
	}

	@Override
	protected void replace()
	{
		tr("${library_use_rate}", pf(useRate.get(0).value));
		tr("${community_library_use_rate}", pf(useRate.get(2).value));
		tr("${press_bar_use_rate}", pf(useRate.get(4).value));
		tr("${library_use_frequency}", tf(useWhenKnowRate.get(0).value));
		tr("${community_library_use_frequency}",
				tf(useWhenKnowRate.get(1).value));
		tr("${press_bar_use_frequency}", tf(useWhenKnowRate.get(2).value));
	}

	@Override
	protected void chart()
	{
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getChildByIndex(tableNode.getChildNodes(), "w:tr",
				1);
		for (int i = 0; i < libraryUseFrequency.size(); ++i)
		{
			BaseRow row = libraryUseFrequency.get(i);
			XMLFragment xmlFragment = new XMLFragment(
					"Resource/adult/chapter07/community_services_use_rate_row.xml");

			xmlFragment.replace("${key}", row.key);
			xmlFragment.replace("${library_use_rate}", pf(row.value));
			xmlFragment.replace("${community_library_use_rate}",
					pf(communityLibraryUseFrequency.get(i).value));
			xmlFragment.replace("${press_bar_use_rate}",
					pf(pressBarUseFrequency.get(i).value));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
	}

}
