package cn.edu.xmu.software.binarykang.adult.chapter04.section05;

import java.util.List;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>4.5.3上网行为
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class InternetBehavior extends AdultBaseAction
{
	private List<BaseRow> data;

	public InternetBehavior(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		data = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		BaseRow.read(xlsx, data, "网上从事的主要活动");
	}

	@Override
	protected void replace()
	{
		tr("${reading_newspaper_internet_behavior}",
				pf(BaseRow.getRowByKey("阅读新闻", data).value));
		tr("${search_infomation_internet_behavior}",
				pf(BaseRow.getRowByKey("查询各类信息", data).value));
		tr("${social_talk_internet_behavior}",
				pf(BaseRow.getRowByKey("网上聊天/交友", data).value));
		tr("${listen_music_internet_behavior}",
				pf(BaseRow.getRowByKey("在线听歌/下载歌曲和电影", data).value));
		tr("${watch_movie_internet_behavior}",
				pf(BaseRow.getRowByKey("看视频", data).value));
		tr("${play_computer_game_internet_behavior}",
				pf(BaseRow.getRowByKey("网络游戏", data).value));
		tr("${reading_book_internet_behavior}",
				pf(BaseRow.getRowByKey("阅读网络书籍、报刊", data).value));
	}

	@Override
	protected void chart()
	{
		BaseRow.sortExceptLast(data);
		BaseRow.table(docx, data,
//				"Resource/adult/chapter04/internet_behavior_odd_row.xml",
//				"Resource/adult/chapter04/internet_behavior_even_row.xml");
				"Resource/singleValueStandard.xml",
				"Resource/singleValueStandard.xml");
	}

}
