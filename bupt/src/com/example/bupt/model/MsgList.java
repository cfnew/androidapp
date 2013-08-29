package com.example.bupt.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bupt.utils.DateUtil;

public class MsgList extends Entity{
	
	private int result;
	private String info;
	private int pageSize = 0;
	private List<Msg> msgList = new ArrayList<Msg>();
	
	public int getResult(){
		return this.result;
	}
	public String getInfo(){
		return this.info;
	}
	public int getPageSize(){
		return this.pageSize;
	}
	public List<Msg> getMsgList(){
		return msgList;
	}
	public void setMsgList(List<Msg> mList){
		this.msgList = mList;
	}
	
	public static MsgList parse(JSONObject jsonObject){
		MsgList mList = new MsgList();
		Msg msg = null;
		JSONObject obj = null;
		try{
			mList.result = jsonObject.getInt("status");
			mList.info = jsonObject.getString("info");
			
			if(mList.result == 1){
				JSONArray jArr = jsonObject.getJSONArray("data");
				int length = jArr.length();
				if(length > 0){
					mList.pageSize = length;
					for(int i = 0; i < length; i++){
						obj = jArr.getJSONObject(i);
						msg = new Msg();
						msg.setMsgId(obj.getInt("notify_id"));
						msg.setFromId(obj.getInt("from"));
						msg.setFromName(obj.getString("name"));
						msg.setFromFace(obj.getString("face"));
						msg.setContent(obj.getString("content"));
						msg.setCtime(DateUtil.parseTimeStamp(Long.parseLong(obj.getString("ctime"))));
						msg.setMsgType(obj.getInt("type"));
						msg.setMsgCount(obj.getInt("count"));
						mList.msgList.add(msg);
					}
				}
			}
			//通知消息
		}catch(JSONException e){
			e.printStackTrace();
		}
		return mList;
	}
}
