package com.example.bupt.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bupt.utils.DateUtil;
import com.example.bupt.utils.StringUtils;

public class PaopaoList extends Entity{
	
	public static final int LIST_TYPE_LATEST_PUB = 0; //最新发布的活动
	public static final int LIST_TYPE_LATEST_START = 1; //最近要开始的活动
	
	private int result;
	private String info;
	private int pageSize = 0;
	private List<Paopao> paopaoList = new ArrayList<Paopao>();
	
	public int getResult(){
		return this.result;
	}
	public String getInfo(){
		return this.info;
	}
	public int getPageSize(){
		return this.pageSize;
	}
	public List<Paopao> getPaopaoList(){
		return paopaoList;
	}
	
	public static PaopaoList parse(JSONObject jsonObject){
		PaopaoList ppList = new PaopaoList();
		Paopao pp = null;
		JSONObject obj = null;
		try{
			ppList.result = jsonObject.getInt("status");
			ppList.info = jsonObject.getString("info");
			
			if(ppList.result == 1){
				JSONArray jArr = jsonObject.getJSONArray("data");
				int length = jArr.length();
				if(length > 0){
					ppList.pageSize = length;
					for(int i = 0; i < length; i++){
						obj = jArr.getJSONObject(i);
						pp = new Paopao();
						pp.setFeedId(obj.getInt("feed_id"));
						pp.setFeedTitle(obj.getString("feed_title"));
						String a_small = obj.getString("attach_small");
						if(!StringUtils.isEmpty(a_small)) pp.setFeedPic(a_small);
						String a_ori = obj.getString("attach_ori");
						if(!StringUtils.isEmpty(a_ori)) pp.setFeedPicBig(a_ori);
						String loc = obj.getString("e_location");
						if(!StringUtils.isEmpty(loc)) pp.setFeedLocation(loc);
						pp.setFeedNeedNum(obj.getInt("need_num"));
						pp.setFeedJoinNum(obj.getInt("join_count"));
						pp.setFeedStartTime(DateUtil.parseTimeStamp(obj.getLong("start_time")));
						pp.setFeedTag(obj.getString("tag"));
						ppList.paopaoList.add(pp);
					}
				}
			}
			//通知消息
		}catch(JSONException e){
			e.printStackTrace();
		}
		return ppList;
	}
}
