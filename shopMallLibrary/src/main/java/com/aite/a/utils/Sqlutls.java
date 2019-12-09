package com.aite.a.utils;

import com.aite.a.sqlbase.HomeCache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

public class Sqlutls {
	/**
	 * 增删改查工具
	 */
	// 上下文
	private SQLiteDatabase db;

	public Sqlutls(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * 增加的方法
	 */
	public void add(String type, String data) {
		ContentValues values = new ContentValues();
		values.put(HomeCache.COLUMN_TYPE, type);
		values.put(HomeCache.COLUMN_DATA, data);
		/**
		 * 参数一：表名；参数二：可以为null；参数三：键值对(列名,值)
		 * 
		 * @return 返回值：返回执行成功的行数
		 */
		long insert = db.insert(HomeCache.TABLE_NAME, null, values);// 插入一个insert语句
//		if (insert > 0) {
//			System.out.println("----------------增加成功---" + insert);
//		} else {
//			System.out.println("----------------增加失败---" + insert);
//		}
	}

	/**
	 * 清空表
	 * 
	 */
	public void delete() {
		db.execSQL("DELETE FROM " + HomeCache.TABLE_NAME);
	}

	/**
	 * 查询方法
	 * @return 
	 */
	public String query(String type) {
		try {
			Cursor query = db.rawQuery("select * from " + HomeCache.TABLE_NAME
					+ " where _type =?", new String[] { type });
			while (query.moveToNext()) {
				
				return query.getString(query.getColumnIndex(HomeCache.COLUMN_DATA));
			}
		} catch (Exception e) {
			System.out.println("-------------------查询失败");
			return null;
		}
		// Cursor query = db.query(HomeCache.TABLE_NAME, null, null, null, null,
		// null, null);
		// while (query.moveToNext()) {
		// // 得到对应列名的下标
		// int columnIndex = query.getColumnIndex(HomeCache.COLUMN_ID);
		// // 得到当前行的columnIndex列的值
		// int id = query.getInt(columnIndex);
		// System.out.println("--------------全部---"
		// + "   "
		// + id
		// + "    "
		// + query.getString(query
		// .getColumnIndex(HomeCache.COLUMN_TYPE))
		// + "   "
		// + query.getString(query
		// .getColumnIndex(HomeCache.COLUMN_DATA)));
		// }
		return null;
	}
}
