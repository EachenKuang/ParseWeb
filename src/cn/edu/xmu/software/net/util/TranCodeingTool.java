package cn.edu.xmu.software.net.util;

import java.io.UnsupportedEncodingException;

public class TranCodeingTool
{
	/**
	 * 对字符串进行转码
	 * ISO-8859-1 To UTF8
	 * @param str
	 * @return 转码后的字符串
	 */
	public static String codeString(String str) {
		String s = str;
		try {
			byte[] temp = s.getBytes("ISO-8859-1");
			s = new String(temp, "UTF-8");
			return s;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}
	
	/**
	 * 对字符串进行转码
	 * UTF-8 To ISO-8859-1
	 * @param str
	 * @return 转码后的字符串
	 */
	public static String reCodeString(String str) {
		String s = str;
		try {
			byte[] temp = s.getBytes("UTF-8");
			s = new String(temp, "ISO-8859-1");
			return s;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}
}
