package com.example.bupt.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.example.bupt.model.Msg;
import com.example.bupt.utils.DateUtil;

public class DBMsg extends BaseSqlite{

	public DBMsg(Context context) {
		super(context);
	}

	@Override
	protected String tableName() {
		return DB_MESSAGE;
	}

	@Override
	protected String[] tableColumns() {
		String[] cols = {
				Msg.MSG_ID,
				Msg.MSG_FROM,
				Msg.MSG_NAME,
				Msg.MSG_FACE,
				Msg.MSG_CONTENT,
				Msg.MSG_TIME,
				Msg.MSG_TYPE,
		};
		return cols;
	}

	/**
	 * 获得所有msg
	 */
	public List<Msg> findAllMsgs() {
		List<Msg> msgList = new ArrayList<Msg>();
		Msg msg = null;
		try{
			db = dbh.getReadableDatabase();
			Log.i("db.table",db.toString());
			String sql = "select * from "+tableName()+" order by "+Msg.MSG_ID+" ASC";
			cursor = db.rawQuery(sql,null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					msg = new Msg();
					msg.setMsgId(cursor.getInt(cursor.getColumnIndex(Msg.MSG_ID)));
					msg.setFromId(cursor.getInt(cursor.getColumnIndex(Msg.MSG_FROM)));
					msg.setFromName(cursor.getString(cursor.getColumnIndex(Msg.MSG_NAME)));
					msg.setFromFace(cursor.getString(cursor.getColumnIndex(Msg.MSG_FACE)));
					msg.setContent(cursor.getString(cursor.getColumnIndex(Msg.MSG_CONTENT)));
					msg.setCtime(DateUtil.parse(cursor.getString(cursor.getColumnIndex(Msg.MSG_TIME)), DateUtil.FORMAT_LONG));
					msg.setMsgType(cursor.getInt(cursor.getColumnIndex(Msg.MSG_TYPE)));
					msgList.add(msg);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			cursor.close();
			db.close();
		}  
		return msgList;
	}
	
	/**
	 * 新增msg
	 * @param user
	 */
	public void insertMsg(List<Msg> msgList) {
		try{
			db = dbh.getWritableDatabase();
			String sql = "insert into "+tableName()+"("+Msg.MSG_ID+","+Msg.MSG_FROM+","+Msg.MSG_NAME+","+Msg.MSG_FACE+","+Msg.MSG_CONTENT+","+Msg.MSG_TIME+","+Msg.MSG_TYPE+") values(?,?,?,?,?,?,?)";
			for(Msg _msg:msgList){
				db.execSQL(sql,
						new Object[]{_msg.getMsgId(),_msg.getFromId(),_msg.getFromName(),_msg.getFromFace(),_msg.getContent(),DateUtil.format(_msg.getCtime(), DateUtil.FORMAT_LONG),_msg.getMsgType()});
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.close();
		}
	}
	
	/**
	 * 根据msgid删除
	 * @param msgid
	 */
	public void deleteById(int msgid){
		this.delete(Msg.MSG_ID+"=?", new String[]{msgid+""});
	}
	
}
