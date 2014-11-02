package com.wyj.tea.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	// ���ݿ�����
	private static final String DB_NAME = "database.db";
	// ���ݿ�汾
	private static final int DB_VERSION = 1;
	// ���ݿ�ʵ�ʲ�������
	private SQLiteDatabase db;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		db = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// ɾ����
		String drop = "DROP TABLE IF EXISTS table_collect";
		db.execSQL(drop);

		StringBuffer sql = new StringBuffer();
		sql.append("CREATE TABLE IF NOT EXISTS table_collect(                     ");
		sql.append("  _id INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append("       id TEXT,             ");
		sql.append("       title TEXT,                           ");
		sql.append("       create_time INTEGER,                ");
		sql.append("       nickname INTEGER                  ");
		sql.append(")                                            ");
		// ִ��SQL���
		db.execSQL(sql.toString());
		
		drop = "DROP TABLE IF EXISTS table_history";
		db.execSQL(drop);
		sql.delete(0, sql.length());
		
		sql.append("CREATE TABLE IF NOT EXISTS table_history(                     ");
		sql.append("  _id INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append("       id TEXT,             ");
		sql.append("       title TEXT,                           ");
		sql.append("       create_time INTEGER,                ");
		sql.append("       nickname INTEGER                  ");
		sql.append(")                                            ");
		// ִ��SQL���
		db.execSQL(sql.toString());
		
		drop = "DROP TABLE IF EXISTS table_tea";
		db.execSQL(drop);
		sql.delete(0, sql.length());
		
		sql.append("CREATE TABLE IF NOT EXISTS table_tea(                     ");
		sql.append("  _id INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append("       id TEXT,             ");
		sql.append("       title TEXT,                           ");
		sql.append("       create_time INTEGER,                ");
		sql.append("       nickname INTEGER ,                 ");
		sql.append("       wap_thumb INTEGER                  ");
		sql.append(")                                            ");
		// ִ��SQL���
		db.execSQL(sql.toString());
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// �°汾�Ŵ��ھɰ汾�����ٴε���onCreate����
		if (newVersion > oldVersion) {
			onCreate(db);
		}
	}
	/**
	 * ִ��SQL��䣬��ִ�в��롢ɾ�����͸��²���
	 * @param sql SQL���
	 * @param bindArgs ���SQL�������ռλ�����������ò��������滻��������ֵ
	 */
	public void executeSQL(String sql ,Object...bindArgs) {
		db.execSQL(sql, bindArgs);
	}
	
	/**
	 * ִ�����ݿ��ѯ����
	 * @param sql SQL���
	 * @param selectionArgs ���SQL�������ռλ�����������ò��������滻��������ֵ
	 * @return Cursor
	 */
	public Cursor executeQuery(String sql,String...selectionArgs){
		return db.rawQuery(sql, selectionArgs);
	}

}
