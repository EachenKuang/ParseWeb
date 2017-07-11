package cn.edu.xmu.software.binarykang.minor.util;



import org.w3c.dom.Node;



import cn.edu.xmu.software.binarykang.minor.parse.Constant;

import cn.edu.xmu.software.binarykang.word.Docx;

import cn.edu.xmu.software.binarykang.word.XMLFragment;



public class TableTool {

	public static void changeTable(int rowNum, int cellNum, String[] key,

			String[][] value, Docx docx, Class<?> tarClass, int... args) {

		String className = tarClass.getSimpleName();

		String packageName = tarClass.getPackage().getName();

		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);

		String sheet = "sheet";

		if (args.length > 0)

			sheet += args[0];

		else

			sheet += 2;



		Node tableNode = docx.getNodeByTagNameAndIndex("w:tbl",

				Constant.getTabelNum());

		Node lastNode = docx.getLastChild(tableNode.getChildNodes(), "w:tr");



		for (int i = 0; i < rowNum; ++i) {

			XMLFragment row = new XMLFragment(

					"Resource/minor/" + sheet + "/" + packageName + "/"

					+ className + "_row.xml");

			Node lastRow = docx.getLastChild(row.getRootNode().getChildNodes(),

					"w:tc");

			for (int j = 0; j < cellNum; ++j) {

				XMLFragment cell = new XMLFragment("Resource/minor/" + sheet + "/" + packageName + "/"

						+ className + "_cell.xml");

				cell.replace("${value}", value[i][j]);

				Node newNode = lastRow.getOwnerDocument().importNode(

						cell.getRootNode(), true);

				row.getRootNode().insertBefore(newNode, lastRow);

			}

			row.getRootNode().removeChild(lastRow);

			row.replace("${key}", key[i]);

			docx.insertBefore(tableNode, row.getRootNode(), lastNode);

		}

		tableNode.removeChild(lastNode);

	}

}
