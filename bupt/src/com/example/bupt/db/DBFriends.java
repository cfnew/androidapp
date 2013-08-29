package com.example.bupt.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.example.bupt.model.Friend;

public class DBFriends extends BaseSqlite{

	public DBFriends(Context context) {
		super(context);
	}

	@Override
	protected String tableName() {
		return DB_FRIENDS;
	}

	@Override
	protected String[] tableColumns() {
		String[] cols = {
				Friend.COL_FRI_ID,
				Friend.COL_FRI_NAME,
				Friend.COL_FRI_PINYIN,
				Friend.COL_FRI_FIRST_CHAR,
				Friend.COL_FRI_FACE,
				Friend.COL_FRI_SEX,
				Friend.COL_FRI_GROUP,
		};
		return cols;
	}

	/**
	 * 获得所有用户
	 */
	public List<Friend> findAllFriends() {
		List<Friend> friList = new ArrayList<Friend>();
		Friend fri = null;
		try{
			db = dbh.getReadableDatabase();
			Log.i("db.friend",db.toString());
			String sql = "select * from "+tableName()+" order by "+Friend.COL_FRI_PINYIN+" ASC;";
			cursor = db.rawQuery(sql,null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					fri = new Friend();
					fri.setFriendId(cursor.getInt(cursor.getColumnIndex(Friend.COL_FRI_ID)));
					fri.setFriendName(cursor.getString(cursor.getColumnIndex(Friend.COL_FRI_NAME)));
					fri.setFriendPinyin(cursor.getString(cursor.getColumnIndex(Friend.COL_FRI_PINYIN)));
					fri.setFirstChar(cursor.getString(cursor.getColumnIndex(Friend.COL_FRI_FIRST_CHAR)));
					fri.setFace(cursor.getString(cursor.getColumnIndex(Friend.COL_FRI_FACE)));
					fri.setSex(cursor.getInt(cursor.getColumnIndex(Friend.COL_FRI_SEX)));
					fri.setGroup(cursor.getInt(cursor.getColumnIndex(Friend.COL_FRI_GROUP)));
					friList.add(fri);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {  
			cursor.close();
			db.close();
		}  
		return friList;
	}
	
	
	/**
	 * 添加好友
	 * @param fList
	 */
	public void addUsers(List<Friend> fList){
		try{
			db = dbh.getWritableDatabase();
			String sql = "insert into "+tableName()+"("+Friend.COL_FRI_ID+","+Friend.COL_FRI_NAME+","+Friend.COL_FRI_PINYIN+","+Friend.COL_FRI_FIRST_CHAR+","+Friend.COL_FRI_FACE+","+Friend.COL_FRI_SEX+","+Friend.COL_FRI_GROUP+") values(?,?,?,?,?,?,?)";
			for(Friend fri:fList){
				db.execSQL(sql,
						new Object[]{fri.getFriendId(),fri.getFriendName(),fri.getFriendPinyin(),fri.getFirstChar(),fri.getFace(),fri.getSex(),fri.getGroup()});
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.close();
		}
	}

}
