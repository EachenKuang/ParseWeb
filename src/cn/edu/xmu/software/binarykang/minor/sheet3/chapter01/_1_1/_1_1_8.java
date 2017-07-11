package cn.edu.xmu.software.binarykang.minor.sheet3.chapter01._1_1;

import java.util.List;

import cn.edu.xmu.software.binarykang.minor.MinorBaseAction;
import cn.edu.xmu.software.binarykang.minor.parse.DataMap;
import cn.edu.xmu.software.binarykang.minor.util.MinorUtil;
import cn.edu.xmu.software.binarykang.word.Docx;
import cn.edu.xmu.software.binarykang.xlsx.Xlsx;

public class _1_1_8 extends MinorBaseAction
{
	private final static String TABLE_KEY_TEACHER = "9-13岁老师对阅读课外书的态度";
	private final static String TABLE_KEY_TEACHER_OTHER = "1.1.8老师/家长对少年儿童阅读课外书的态度 第一段文字中全国的持赞成态度比例";
	private final static String TABLE_KEY_PARENTS = "9-13岁家长对阅读课外书的态度";
	private List<DataMap> tableTeacherLocal;
	private List<DataMap> tableTeacherOther;
	private List<DataMap> tableParents;
	private String other;

	public _1_1_8(Docx docx, Xlsx xlsx)
	{
		super(docx, xlsx);

		tableParents = MinorUtil.listMapFactory();
		tableTeacherLocal = MinorUtil.listMapFactory();
		tableTeacherOther = MinorUtil.listMapFactory();
	}

	@Override
	protected void readData()
	{
		MinorUtil
				.readData(xlsx, TABLE_KEY_PARENTS, tableParents, BEGIN_COL + 1);
		MinorUtil.readData(xlsx, TABLE_KEY_TEACHER, tableTeacherLocal,
				BEGIN_COL + 1);
		other = MinorUtil.readData(xlsx, TABLE_KEY_TEACHER_OTHER,
				tableTeacherOther, BEGIN_COL + 1, " ");
	}

	@Override
	protected void replace()
	{
		tr("${sheet3_1_1_8_other}", other);
		double approvalLocal = 0;
		double approvalOther = 0;
		double approvalParents = 0;
		double disapproval = 0;
		double disapprovalParents = 0;
		
		approvalLocal = tableTeacherLocal.get(0).getRate()+tableTeacherLocal.get(1).getRate();
		approvalOther = tableTeacherOther.get(0).getRate()+tableTeacherOther.get(1).getRate();
		approvalParents = tableParents.get(0).getRate()+tableParents.get(1).getRate();
		disapproval = tableTeacherLocal.get(3).getRate()+tableTeacherLocal.get(4).getRate();			
		disapprovalParents = tableParents.get(3).getRate()+tableTeacherLocal.get(4).getRate();
		
		double cut = approvalLocal - approvalOther;
		tr("${sheet3_1_1_8_attitude_teacher_approval_rate_local}",
				perf.format(approvalLocal));
		tr("${sheet3_1_1_8_attitude_teacher_approval_rate_other}",
				perf.format(approvalOther));
		tr("${sheet3_1_1_8_attitude_teacher_approval_judge}", cut > 0 ? "高"
				: "低");
		tr("${sheet3_1_1_8_attitude_teacher_approval_cut}",
				delLastChar(perf.format(Math.abs(cut))));
		tr("${sheet3_1_1_8_attitude_teacher_never_mind_rate}",
				perf.format(MinorUtil.getByKey("无所谓", tableTeacherLocal)
						.getRate()));
		tr("${sheet3_1_1_8_attitude_parents_approval_rate}",
				perf.format(approvalParents));
		tr("${sheet3_1_1_8_attitude_parents_never_mind_rate}",
				perf.format(MinorUtil.getByKey("无所谓", tableParents).getRate()));
		
		tr("${sheet3_1_1_8_attitude_teacher_unclear}",
				perf.format(tableTeacherLocal.get(5).getRate()));
		tr("${sheet3_1_1_8_attitude_teacher_disapproval_rate}",
				perf.format(disapproval));  //new add
		tr("${sheet3_1_1_8_attitude_parent_disapproval_rate}",
				perf.format(disapprovalParents));  //new add
	}

	public void process()
	{
		readData();
		chart();
		replace();
		trReplace();
	};
	
	@Override
	protected void chart()
	{
		MinorUtil.changeChart(tableTeacherLocal);
		
		MinorUtil.changeChart(tableParents);
	}

}
