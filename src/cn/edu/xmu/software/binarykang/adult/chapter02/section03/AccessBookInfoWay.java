package cn.edu.xmu.software.binarykang.adult.chapter02.section03;

import java.util.Collections;
import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>2.3.1图书信息的获取途径
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class AccessBookInfoWay extends AdultBaseAction
{
	private List<BaseRow> data;

	public AccessBookInfoWay(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "购书者获取图书信息的途径");
	}

	@Override
	protected void replace()
	{
		
		tr("${internet_access_book_info}",
				pf(BaseRow.getRowByKey("互联网", data).value));
		tr("${tv_access_book_info}", pf(BaseRow.getRowByKey("电视", data).value));
		tr("${social_network_access_book_info}",
				pf(BaseRow.getRowByKey("朋友或他人推荐", data).value));
		tr("${newspaper_magazine_access_book_info}",
				pf(BaseRow.getRowByKey("报纸、期刊", data).value));
		tr("${ad_access_book_info}", 
				pf(BaseRow.getRowByKey("书店内广告宣传品", data).value));
		tr("${recommend_access_book_info}", 
				pf(BaseRow.getRowByKey("售书人员推荐", data).value));
		tr("${noway_access_book_info}",
				pf(BaseRow.getRowByKey("无获取渠道", data).value));
		
		
//		tr("${internet_access_book_info}",
//				pf(data.get(3).value));
//		tr("${tv_access_book_info}", pf(data.get(1).value));
//		tr("${social_network_access_book_info}",
//				pf(data.get(6).value));
//		tr("${newspaper_magazine_access_book_info}",
//				pf(data.get(0).value));
//		tr("${ad_access_book_info}", 
//				pf(data.get(4).value));
//		tr("${recommend_access_book_info}", 
//				pf(data.get(5).value));
//		tr("${noway_access_book_info}",
//				pf(data.get(8).value));
	}

	@Override
	protected void chart()
	{
		// 生成城乡不阅读原因的柱状图
		BaseRow noAccess = data.get(8);
		data.remove(8);
		BaseRow.sortExceptLast(data);
		data.add(noAccess);
		Collections.reverse(data);
		BaseRow.chart(data);// chart5
		Collections.reverse(data);
		BaseRow.sort(data);
	}

}
