package com.example.bupt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bupt.base.BaseApp;
import com.example.bupt.utils.DateUtil;

public class ChatList extends Entity{
	
	private int result;
	private String info;
	private int pageSize = 0;
	private List<Chat> chatList = new ArrayList<Chat>();
	
	private static Comparator<Chat> comparator = new Comparator<Chat>(){
		@Override
		public int compare(Chat c1, Chat c2) {
			 Date d1 = c1.getChatPubTime();
		     Date d2 = c2.getChatPubTime();
		     return d1.compareTo(d2);
		}
	};
	
	public int getResult(){
		return this.result;
	}
	public String getInfo(){
		return this.info;
	}
	public int getPageSize(){
		return this.pageSize;
	}
	public List<Chat> getChatList(){
		return chatList;
	}
	
	public static ChatList parse(JSONObject jsonObject){
		ChatList ccList = new ChatList();
		Chat chat = null;
		JSONObject obj = null;
		try{
			ccList.result = jsonObject.getInt("status");
			ccList.info = jsonObject.getString("info");
			
			if(ccList.result == 1){
				JSONArray jArr = jsonObject.getJSONArray("data");
				int length = jArr.length();
				if(length > 0){
					ccList.pageSize = length;
					for(int i = 0; i < length; i++){
						obj = jArr.getJSONObject(i);
						chat = new Chat();
						chat.setChatId(obj.getInt("message_id"));
						chat.setChatAuthorId(obj.getInt("from_uid"));
						chat.setChatFace(obj.getString("uface"));
						chat.setChatContent(obj.getString("content"));
						chat.setChatPubTime(DateUtil.parseTimeStamp(Long.parseLong(obj.getString("mtime"))));
						chat.setChatIsReceive(true); //默认是收到的信息
						if(obj.getInt("from_uid") == BaseApp.getUid()){
							chat.setChatIsReceive(false);
						}
						ccList.chatList.add(chat);
					}
					Collections.sort(ccList.chatList, comparator);
				}
			}
			//通知消息
		}catch(JSONException e){
			e.printStackTrace();
		}
		return ccList;
	}
}
