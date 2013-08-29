package com.example.bupt.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class SearchFriList extends Entity{
	
	private static final long serialVersionUID = 8942821726769265565L;
	private int status;
	private String info;
	private int pageSize = 0;
	private List<Friend> friendList = new ArrayList<Friend>();
	
	public int getStatus(){
		return this.status;
	}
	public String getInfo(){
		return this.info;
	}
	public int getPageSize(){
		return this.pageSize;
	}
	public List<Friend> getFriendList(){
		return friendList;
	}
	
	public static SearchFriList parse(JSONObject jsonObject){
		SearchFriList fList = new SearchFriList();
		Friend friend = null;
		JSONObject obj = null;
		try{
			fList.status = jsonObject.getInt("status");
			fList.info = jsonObject.getString("info");
			
			if(fList.status == 1){
				JSONArray jArr = jsonObject.getJSONArray("data");
				int length = jArr.length();
				Log.i("friends_length",length+"");
				if(length > 0){
					fList.pageSize = length;
					for(int i = 0; i < length; i++){
						obj = jArr.getJSONObject(i);
						friend = new Friend();
						friend.setFriendId(obj.getInt("uid"));
						friend.setFriendName(obj.getString("uname"));
						friend.setFace(obj.getString("uface"));
						friend.setFaceBig(obj.getString("uface_big"));
						friend.setSex(obj.getInt("sex"));
						fList.friendList.add(friend);
					}
				}
			}
			//通知消息
		}catch(JSONException e){
			e.printStackTrace();
		}
		return fList;
	}
	
}
