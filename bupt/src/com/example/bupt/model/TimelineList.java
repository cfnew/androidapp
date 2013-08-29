package com.example.bupt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bupt.utils.DateUtil;

public class TimelineList extends Entity{
	
	private int result;
	private String info;
	private int pageSize = 0;
	private List<Timeline> itemList = new ArrayList<Timeline>();
	
	public int getResult(){
		return this.result;
	}
	public String getInfo(){
		return this.info;
	}
	public int getPageSize(){
		return this.pageSize;
	}
	public List<Timeline> getItemList(){
		return itemList;
	}
	
	public static TimelineList parse(JSONObject jsonObject){
		TimelineList tlList = new TimelineList();
		Timeline tl = null;
		JSONObject obj = null;
		try{
			tlList.result = jsonObject.getInt("status");
			tlList.info = jsonObject.getString("info");
			
			if(tlList.result == 1){
				JSONArray jArr = jsonObject.getJSONArray("data");
				int length = jArr.length();
				if(length > 0){
					tlList.pageSize = length;
					for(int i = 0; i < length; i++){
						obj = jArr.getJSONObject(i);
						tl = new Timeline();
						tl.setFeedId(obj.getInt("feed_id"));
						tl.setFeedTitle(obj.getString("feed_title"));
						tl.setFeedLocation(obj.getString("e_location"));
						tl.setJoinNum(obj.getInt("join_count"));
						tl.setNeedNum(obj.getInt("need_num"));
						Date start = DateUtil.parseTimeStamp(Long.parseLong(obj.getString("start_time")));
						tl.setFeedStartTime(start);
						if(DateUtil.isOutOfDate(start)){
							tl.setOver(true);
						}else{
							tl.setOver(false);
						}
						tlList.itemList.add(tl);
					}
				}
			}
			//通知消息
		}catch(JSONException e){
			e.printStackTrace();
		}
		return tlList;
	}
}
