package cn.edu.xmu.software.binarykang.adult.chapter05.section01;

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
 * 解析=>5.3音像电子出版物的价格承受力
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class AudioPriceBearRange extends AdultBaseAction
{
	private List<BaseRow> acceptablePrice;
	private List<BaseRow> cd;
	private List<BaseRow> vcd;
	private List<BaseRow> casete;
	private List<BaseRow> cdrom;
	private List<List<BaseRow>> data;

	public AudioPriceBearRange(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		acceptablePrice = ListFactory.getBaseRows();
		cd = ListFactory.getBaseRows();
		vcd = ListFactory.getBaseRows();
		casete = ListFactory.getBaseRows();
		cdrom = ListFactory.getBaseRows();
		data = ListFactory.getVarTypeRows();
		data.add(cd);
		data.add(vcd);
		data.add(casete);
		data.add(cdrom);
	}

	@Override
	protected void readData()
	{
		MultiType.read(xlsx, data, xlsx.getRowByKey("可接受的音像电子出版物价格") + 1);
		BaseRow.read(xlsx, acceptablePrice,
				xlsx.getRowByKey("5.3音像电子出版物的价格承受力 第二段文字中的各音像出版物可接受价格") + 1,
				BEGIN_COL);
	}

	@Override
	protected void replace()
	{
		tr("${average_cd_audio_price_bear_range_value}",
				tf(acceptablePrice.get(0).value));
		tr("${average_vcd_dvd_audio_price_bear_range_value}",
				tf(acceptablePrice.get(1).value));
		tr("${average_casete_audio_price_bear_range_value}",
				tf(acceptablePrice.get(2).value));
		tr("${average_cdrom_audio_price_bear_range_value}",
				tf(acceptablePrice.get(3).value));
	}

	@Override
	protected void chart()
	{
		Node tableNode = docx.getTableNode();
		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");
		for (int i = 0; i < cd.size(); ++i)
		{
			BaseRow row = cd.get(i);
			XMLFragment xmlFragment = null;
			if (i % 2 == 0)
			{
				xmlFragment = new XMLFragment(
						"Resource/adult/chapter05/audio_price_bear_range_odd_row.xml");
			}
			else
			{
				xmlFragment = new XMLFragment(
						"Resource/adult/chapter05/audio_price_bear_range_even_row.xml");
			}

			xmlFragment.replace("${key}", row.key);
			xmlFragment.replace("${cd}", pf(row.value));
			xmlFragment.replace("${vcd}", pf(vcd.get(i).value));
			xmlFragment.replace("${casete}", pf(casete.get(i).value));
			xmlFragment.replace("${cdrom}", pf(cdrom.get(i).value));
			Node node = xmlFragment.getRootNode();
			docx.insertBefore(tableNode, node, lastNode);
		}
	}

}
