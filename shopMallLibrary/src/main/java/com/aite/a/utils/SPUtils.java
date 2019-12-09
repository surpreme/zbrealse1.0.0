package com.aite.a.utils;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences工具类：用于保存数据
 * 
 * @author crystal
 * @date 2015-1-1 23:21
 */
public class SPUtils {
	public static String FILE_NAME;
	public static Context context;

	/**
	 * SharedPreferences工具类：用于保存数据
	 * 
	 * @param FILE_NAME
	 *            保存在手机里的文件名
	 * @param context
	 */
	public SPUtils(String FILE_NAME, Context context) {
		SPUtils.FILE_NAME = FILE_NAME;
		SPUtils.context = context;
	}

	/**
	 * 保存数据
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            key
	 * @param obj
	 *            value
	 */
	public void put(String key, Object obj) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				context.MODE_PRIVATE);
		Editor editor = sp.edit();

		if (obj instanceof Boolean) {
			editor.putBoolean(key, (Boolean) obj);
		} else if (obj instanceof Float) {
			editor.putFloat(key, (Float) obj);
		} else if (obj instanceof Integer) {
			editor.putInt(key, (Integer) obj);
		} else if (obj instanceof Long) {
			editor.putLong(key, (Long) obj);
		} else {
			editor.putString(key, (String) obj);
		}
		editor.commit();
	}

	/**
	 * 获取指定的数据
	 * 
	 * @param context
	 * @param key
	 * @param defaultObj
	 * @return
	 */
	public Object get(String key, Object defaultObj) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				context.MODE_PRIVATE);

		if (defaultObj instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObj);
		} else if (defaultObj instanceof Float) {
			return sp.getFloat(key, (Float) defaultObj);
		} else if (defaultObj instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObj);
		} else if (defaultObj instanceof Long) {
			return sp.getLong(key, (Long) defaultObj);
		} else if (defaultObj instanceof String) {
			return sp.getString(key, (String) defaultObj);
		}
		return null;
	}

	/**
	 * 删除指定的数据
	 * 
	 * @param key
	 */
	public void remove(String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @return
	 */
	public Map<String, ?> getAll() {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				context.MODE_PRIVATE);
		Map<String, ?> map = sp.getAll();
		return map;
	}

	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public void clear() {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 检查是否存在此key对应的数据
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public boolean contains(String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				context.MODE_PRIVATE);
		return sp.contains(key);
	}

}
