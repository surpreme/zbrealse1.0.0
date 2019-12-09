package com.aite.a.utils;

import java.util.List;

public class StringUtils {
	/** * 判断字符串是否是整数 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String listToString(List<String> stringList, String separator) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(separator);
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	/** * 判断字符串是否是浮点数 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/** * 判断字符串是否是数字 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}
}
