package com.aite.a.sqlbase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 首页缓存
 * 
 * @author Administrator
 *
 */
public class HomeCache extends SQLiteOpenHelper {
	// 数据库名字
	private static final String DATABASE_NAME = "cache.db";
	// 版本号
	private static final int DATABASE_VERSION = 1;
	// 表名
	public static final String TABLE_NAME = "cache";
	// ID
	public static final String COLUMN_ID = "_id";
	// 类型
	public static final String COLUMN_TYPE = "_type";
	// 值
	public static final String COLUMN_DATA = "_data";

	public HomeCache(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE_NAME + " ( " + COLUMN_ID
				+ " integer primary key autoincrement not null, " + COLUMN_TYPE
				+ " text , " + COLUMN_DATA + " text)";
		// 执行语句
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String slq = "drop table if exists " + TABLE_NAME;//删除表
		db.execSQL(slq);
	}
}
