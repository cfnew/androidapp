package com.example.bupt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.bupt.utils.PinyinUtil;
import com.example.bupt.utils.StringUtils;

public class FriendList extends Entity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8942821726769265565L;
	private int result;
	private String info;
	private int pageSize = 0;
	private List<Friend> friendList = new ArrayList<Friend>();
	
	private static Comparator<Friend> comparator = new Comparator<Friend>(){
		@Override
		public int compare(Friend f1, Friend f2) {
			 String str1 = f1.getFriendPinyin();
		     String str2 = f2.getFriendPinyin();
		     return str1.compareTo(str2);
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
	public List<Friend> getFriendList(){
		return friendList;
	}
	public void setFriendList(List<Friend> list){
		this.friendList = list;
	}
	
	public static FriendList parse(JSONObject jsonObject){
		FriendList fList = new FriendList();
		Friend friend = null;
		JSONObject obj = null;
		try{
			fList.result = jsonObject.getInt("status");
			fList.info = jsonObject.getString("info");
			
			if(fList.result == 1){
				JSONArray jArr = jsonObject.getJSONArray("data");
				int length = jArr.length();
				Log.i("friends_length",length+"");
				if(length > 0){
					fList.pageSize = length;
					for(int i = 0; i < length; i++){
						obj = jArr.getJSONObject(i);
						friend = new Friend();
						friend.setFriendId(obj.getInt("uid"));
						String uname = obj.getString("uname");
						friend.setFriendName(uname);
						friend.setFriendPinyin(PinyinUtil.getPinYin(uname).toUpperCase());
						friend.setFirstChar(PinyinUtil.convertToFirstSpell(uname).substring(0, 1).toUpperCase());
						friend.setFace(obj.getString("avatar_middle"));
						if(!StringUtils.isEmpty(obj.getString("sex"))){
							friend.setSex(obj.getInt("sex"));
						}
						fList.friendList.add(friend);
					}
					Collections.sort(fList.friendList, comparator);
				}
			}
			//通知消息
		}catch(JSONException e){
			e.printStackTrace();
		}
		return fList;
	}
	
}
