package cn.edu.xmu.software.binarykang.minor.sheet2._4_2;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _4_2_3 extends MinorBaseAction{

	private final static String TABLE_KEY_EBOOK = "14-17岁电子书报刊阅读率 第一段文字中的电子书阅读率（当地）";
	private final static String TABLE_KEY_EMAGAZINE = "14-17岁电子书报刊阅读率 第一段文字中的电子期刊阅读率（当地）";
	private final static String TABLE_KEY_ENEWSPAPER = "14-17岁电子书报刊阅读率 第一段文字中的电子报阅读率（当地）";
	private final static String TABLE_KEY_LOCAL = "14-17岁电子书报刊阅读率 第一段文字中的人均阅读电子书数量（当地）";
	private final static String TABLE_KEY_COUNTRY = "14-17岁电子书报刊阅读率 第一段文字中的人均阅读电子书数量（全国）";
	private List<DataMap> tableEbook;
	private List<DataMap> tableEmagazine;
	private List<DataMap> tableEnewspaper;
	private List<DataMap> tableLocal;
	private List<DataMap> tableCountry;
	
	public _4_2_3(Docx docx, Xlsx xlsx) {
		super(docx, xlsx);
		tableEbook = MinorUtil.listMapFactory();
		tableEnewspaper = MinorUtil.listMapFactory();
		tableEmagazine = MinorUtil.listMapFactory();
		tableLocal = MinorUtil.listMapFactory();
		tableCountry = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData() {
		MinorUtil.readData(xlsx, TABLE_KEY_EBOOK, tableEbook, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_EMAGAZINE, tableEmagazine, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_ENEWSPAPER, tableEnewspaper, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_LOCAL, tableLocal, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_COUNTRY, tableCountry, BEGIN_COL + 1);
	}

	@Override
	protected void replace() {
		tr("${_4_2_1_ebook_read_rate}", perf.format(tableEbook.get(0).getRate()));
		tr("${_4_2_1_emagazine_read_rate}", perf.format(tableEmagazine.get(0).getRate()));
		tr("${_4_2_1_enewspaper_read_rate}", perf.format(tableEnewspaper.get(0).getRate()));
		tr("${_4_2_1_local_read_rate}", twof.format(tableLocal.get(0).getRate()));
		tr("${_4_2_1_country_read_rate}", twof.format(tableCountry.get(0).getRate()));
		tr("${_4_2_1_read_rate_local_country}", 
				tableLocal.get(0).getRate()>tableCountry.get(0).getRate()?"高" : "低");
		tr("${_4_2_1_read_rate_local_country_rate}",
				twof.format(Math.abs(tableLocal.get(0).getRate()-tableCountry.get(0).getRate())));
	}

	@Override
	protected void chart() {
		
		
	}

}
