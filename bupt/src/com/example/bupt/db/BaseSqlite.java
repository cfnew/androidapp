package com.example.bupt.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bupt.model.Friend;
import com.example.bupt.model.Msg;
import com.example.bupt.model.User;
import com.example.bupt.update.DownloadInfo;

public abstract class BaseSqlite {

	protected static final String DB_NAME = "xianging.db";
	private static final int DB_VERSION = 1;
	
	protected static final String DB_USER = "xy_userinfo";
	protected static final String DB_FRIENDS = "xy_friends";
	protected static final String DB_MESSAGE = "xy_messages";
	protected static final String DB_DOWNLOAD = "xy_download";
	
	protected DbHelper dbh = null;
	protected SQLiteDatabase db = null;
	protected Cursor cursor = null;
	
	public BaseSqlite(Context context) {
		dbh = new DbHelper(context, DB_NAME, null, DB_VERSION);
	}

	public void create (ContentValues values) {
		try {
			db = dbh.getWritableDatabase();
			db.insert(tableName(), null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public void update (ContentValues values, String where, String[] params) {
		try {
			db = dbh.getWritableDatabase();
			db.update(tableName(), values, where, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public void delete (String where, String[] params) {
		try {
			db = dbh.getWritableDatabase();
			db.delete(tableName(), where, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public ArrayList<ArrayList<String>> select (String where, String[] params) {
		ArrayList<ArrayList<String>> rList = new ArrayList<ArrayList<String>>();
		try {
			db = dbh.getReadableDatabase();
			cursor = db.query(tableName(), tableColumns(), where, params, null, null, null);
			while (cursor.moveToNext()) {
				int i = cursor.getColumnCount();
				ArrayList<String> rRow = new ArrayList<String>();
				while (i >= 0) {
					rRow.add(i, cursor.getString(i));
				}
				rList.add(rRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
		return rList;
	}
	
	public int count (String where, String[] params) {
		try {
			db = dbh.getReadableDatabase();
			cursor = db.query(tableName(), tableColumns(), where, params, null, null, null);
			return cursor.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
		return 0;
	}
	
	public boolean exists (String where, String[] params) {
		boolean result = false;
		try {
			int count = this.count(where, params);
			if (count > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			cursor.close();
		}
		return result;
	}
	
	abstract protected String tableName ();
	abstract protected String[] tableColumns ();
	
	protected class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(createFriendSql());
			db.execSQL(createUserSql());
			db.execSQL(createMsgSql());
			db.execSQL(createDownloadSql());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+DB_FRIENDS);
			db.execSQL("DROP TABLE IF EXISTS "+DB_MESSAGE);
			db.execSQL("DROP TABLE IF EXISTS "+DB_USER);
			db.execSQL("DROP TABLE IF EXISTS "+DB_DOWNLOAD);
			onCreate(db);
		}
	}
	
	protected String createMsgSql() {
		return "CREATE TABLE IF NOT EXISTS " + DB_MESSAGE + "(" +
				Msg.MSG_ID + " integer primary key, " +
				Msg.MSG_FROM + " integer, " +
				Msg.MSG_NAME + " varchar(255), " +
				Msg.MSG_FACE + " text, " +
				Msg.MSG_CONTENT + " varchar(255), " +
				Msg.MSG_TIME + " varchar(255), " +
				Msg.MSG_TYPE + " integer)";
	}
	protected String createFriendSql() {
		return "CREATE TABLE IF NOT EXISTS " + DB_FRIENDS + "(" +
				Friend.COL_FRI_ID+" integer primary key, "+
				Friend.COL_FRI_NAME + " varchar(255), " +
				Friend.COL_FRI_PINYIN + " varchar(255), " +
				Friend.COL_FRI_FIRST_CHAR + " varchar(255), " +
				Friend.COL_FRI_FACE + " text, " +
				Friend.COL_FRI_SEX + " integer, " +
				Friend.COL_FRI_GROUP + " text)";
	}
	protected String createUserSql() {
		return "CREATE TABLE IF NOT EXISTS " + DB_USER + "(" +
				User.COL_UID+" integer primary key, "+
				User.COL_ACCOUNT + " varchar(255), " +
				User.COL_PASSWORD + " varchar(255), " +
				User.COL_NAME + " varchar(255), " +
				User.COL_SEX + " varchar(50), " +
				User.COL_BIRTHDAY + " varchar(50), " +
				User.COL_STAR + " varchar(50), " +
				User.COL_LOCATION + " varchar(255), " +
				User.COL_FACE + " BLOB, " +
				User.COL_DESC + " text);";
	}
	protected String createDownloadSql() {
		return "CREATE TABLE IF NOT EXISTS "+DB_DOWNLOAD+"(" +
				DownloadInfo.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				DownloadInfo.COL_THREAD+" integer, "+
				DownloadInfo.COL_START+" integer, " +
				DownloadInfo.COL_END+" integer, " +
				DownloadInfo.COL_COMPLETE+" integer," +
				DownloadInfo.COL_URL+" varchar(255))";
	}
}