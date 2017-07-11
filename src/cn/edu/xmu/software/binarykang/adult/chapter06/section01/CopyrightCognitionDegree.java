package cn.edu.xmu.software.binarykang.adult.chapter06.section01;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import cn.edu.xmu.software.binarykang.adult.AdultBaseAction;
import cn.edu.xmu.software.binarykang.common.rowtype.BaseRow;
import cn.edu.xmu.software.binarykang.common.rowtype.ListFactory;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

/**
 * 解析=>6.1版权认知度
 * 
 * @author KangBin <bingyingsuolian@163.com>
 * @since 2014-08-15
 *
 */
public final class CopyrightCognitionDegree extends AdultBaseAction
{
	private double urbanCopyrightCognitionDegree;
	private double villageCopyrightCognitionDegree;
	private double localCopyrightCognitionDegree;
	private double maleCopyrightCognitionDegree;
	private double femaleCopyrightCognitionDegree;
//	private List<BaseRow> areaCopyrightCognitionDegree;

	public CopyrightCognitionDegree(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);
		//areaCopyrightCognitionDegree = ListFactory.getBaseRows();
	}

	@Override
	protected void readData()
	{
		XSSFRow urbanVillageRow = xlsx.getRow(xlsx
				.getRowByKey("6.1版权认知度 第一段文字中的版权认知度") + 3);
		localCopyrightCognitionDegree = new Double(xlsx.getContent(
				urbanVillageRow, BEGIN_COL + 2));
		maleCopyrightCognitionDegree = new Double(xlsx.getContent(
						urbanVillageRow, BEGIN_COL + 3));
		femaleCopyrightCognitionDegree = new Double(xlsx.getContent(
				urbanVillageRow, BEGIN_COL + 4));	
		urbanCopyrightCognitionDegree = new Double(xlsx.getContent(
				urbanVillageRow, BEGIN_COL + 5));
		villageCopyrightCognitionDegree = new Double(xlsx.getContent(
				urbanVillageRow, BEGIN_COL + 6));

//		int areaRowIndex = xlsx.getRowByKey("各城区版权认知度");
//		XSSFRow areaRow = xlsx.getRow(areaRowIndex + 1);
//		XSSFRow rateRow = xlsx.getRow(areaRowIndex + 3);
//		for (int i = BEGIN_COL + 2; i < areaRow.getLastCellNum(); ++i)
//		{
//			BaseRow baseRow = new BaseRow();
//			baseRow.key = xlsx.getContent(areaRow, i);
//			baseRow.value = new Double(xlsx.getContent(rateRow, i));
//			areaCopyrightCognitionDegree.add(baseRow);
//		}
	}

	@Override
	protected void replace()
	{
		tr("${local_copyright_cognition_degree}",
				pf(localCopyrightCognitionDegree));
		tr("${male_copyright_cognition_degree}",
				pf(maleCopyrightCognitionDegree));
		tr("${female_copyright_cognition_degree}",
				pf(femaleCopyrightCognitionDegree));
		tr("${urban_copyright_cognition_degree}",
				pf(urbanCopyrightCognitionDegree));
		tr("${village_copyright_cognition_degree}",
				pf(villageCopyrightCognitionDegree));
//		tr("${best_copyright_cognition_degree_area}",
//				areaCopyrightCognitionDegree.get(0).key);
//		tr("${best_copyright_cognition_degree_rate}",
//				pf(areaCopyrightCognitionDegree.get(0).value));
//		tr("${second_copyright_cognition_degree_area}",
//				areaCopyrightCognitionDegree.get(1).key);
	}

	@Override
	protected void chart()
	{
//		BaseRow.sortExceptLast(areaCopyrightCognitionDegree);
//		BaseRow.chart(areaCopyrightCognitionDegree);// chart39
	}

}
