package com.aite.a.utils;

import java.util.StringTokenizer;

public class AnalyticalStr {
	/**
	 * 使用StringTokenizer类将字符串按分隔符转换成字符数组
	 * 
	 * @param string
	 *            字符串
	 * @param divisionChar
	 *            分隔符
	 * @return 字符串数组
	 * @see [类、类#方法、类#成员]
	 */
	public static String[] stringAnalytical(String string, String divisionChar) {
		int i = 0;
		StringTokenizer tokenizer = new StringTokenizer(string, divisionChar);

		String[] str = new String[tokenizer.countTokens()];

		while (tokenizer.hasMoreTokens()) {
			str[i] = new String();
			str[i] = tokenizer.nextToken();
			i++;
		}

		return str;
	}

	/**
	 * 字符串解析，不使用StringTokenizer类和java.lang.String的split()方法 将字符串根据分割符转换成字符串数组
	 * 
	 * @param string
	 *            字符串
	 * @param c
	 *            分隔符
	 * @return 解析后的字符串数组
	 */
	public static String[] stringAnalytical(String string, char c) {
		// 字符串中分隔符的个数
		int count = 0;

		// 如果不含分割符则返回字符本身
		if (string.indexOf(c) == -1) {
			return new String[] { string };
		}

		char[] cs = string.toCharArray();

		// 过滤掉第一个和最后一个是分隔符的情况
		for (int i = 1; i < cs.length - 1; i++) {
			if (cs[i] == c) {
				count++; // 得到分隔符的个数
			}
		}

		String[] strArray = new String[count + 1];
		int k = 0, j = 0;
		String str = string;

		// 去掉第一个字符是分隔符的情况
		if ((k = str.indexOf(c)) == 0) {
			str = string.substring(k + 1);
		}

		// 检测是否包含分割符，如果不含则返回字符串
		if (str.indexOf(c) == -1) {
			return new String[] { str };
		}

		while ((k = str.indexOf(c)) != -1) {
			strArray[j++] = str.substring(0, k);
			str = str.substring(k + 1);
			if ((k = str.indexOf(c)) == -1 && str.length() > 0) {
				strArray[j++] = str.substring(0);
			}
		}

		return strArray;
	}

}