package com.example.bupt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bupt.base.BaseApp;
import com.example.bupt.utils.DateUtil;

public class DetailsSList extends Entity{
	
	public static final int USER_STATUS_TO_JOIN = 1; //未加入
	public static final int USER_STATUS_JOINED = 2; //已经加入
	public static final int USER_STATUS_TO_QUERE = 3; //未排队
	public static final int USER_STATUS_QUEUE = 4; //排队中
	
	private int result;
	private String info;
	private int itemsSize = 0;
	private List<DetailsSculpture> DSList = new ArrayList<DetailsSculpture>();
	
	private int feedID;
	private String tittle;
	private String content;
	private int need_num;
	private int join_num;
	private String location;
	private int from;
	private Date publish_time;
	private Date start_time;
	private String attach_small;
	private String attach_big;
	private String attach_ori;
	private String tag;
	private int joinStatus;
	private boolean curUserIsjoin = false;
	
	public int getJoin_num() {
		return join_num;
	}
	
	public int getFeedID() {
		return feedID;
	}
	public String getTittle() {
		return tittle;
	}
	public String getContent() {
		return content;
	}
	public int getNeed_num() {
		return need_num;
	}
	public String getLocation() {
		return location;
	}
	public int getFrom() {
		return from;
	}
	public Date getPublish_time() {
		return publish_time;
	}
	public Date getStart_time() {
		return start_time;
	}
	public String getAttach_small() {
		return attach_small;
	}
	public String getAttach_big() {
		return attach_big;
	}
	public String getAttach_ori() {
		return attach_ori;
	}
	public int getResult(){
		return this.result;
	}
	public String getInfo(){
		return this.info;
	}
	public int getItemsSize(){
		return this.itemsSize;
	}
	public List<DetailsSculpture> getDSList(){
		return DSList;
	}
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public static DetailsSList parse(JSONObject jsonObject){
		DetailsSList dslist = new DetailsSList();
		DetailsSculpture ds = null;
		JSONObject obj = null;
		try{
			dslist.result = jsonObject.getInt("status");
			dslist.info = jsonObject.getString("info");
			
			if(dslist.result == 1){
				JSONObject jsonob = jsonObject.getJSONObject("data");
				dslist.feedID = jsonob.getInt("feed_id");
				dslist.tittle = jsonob.getString("feed_title");
				dslist.content = jsonob.getString("feed_content");
				dslist.need_num = jsonob.getInt("need_num");
				dslist.join_num = jsonob.getInt("join_count");
				dslist.location = jsonob.getString("e_location");
				dslist.attach_small = jsonob.getString("attach_small");
				dslist.attach_big = jsonob.getString("attach_big");
				dslist.attach_ori = jsonob.getString("attach_ori");
				dslist.publish_time = DateUtil.parseTimeStamp(jsonob.getLong("publish_time"));
				dslist.start_time = DateUtil.parseTimeStamp(jsonob.getLong("start_time"));
				dslist.tag = jsonob.getString("tag");
				dslist.setJoinStatus(jsonob.getInt("queue")); //当前活动的参与情况
				JSONArray jArr = jsonob.getJSONArray("users");
				int length = jArr.length();
				if(length > 0){
					dslist.itemsSize = length;
					for(int i = 0; i < length; i++){
						obj = jArr.getJSONObject(i);
						ds = new DetailsSculpture();
						int joinedUid = obj.getInt("uid");
						if(BaseApp.getUid() == joinedUid) dslist.curUserIsjoin = true; //标识当前用户是否已经参与了活动
						ds.setUid(joinedUid);
						ds.setAvatar_middle(obj.getString("avatar_middle"));
						ds.setIsPoster(obj.getInt("type"));
						dslist.DSList.add(ds);
					}
				}
			}
			//通知消息
		}catch(JSONException e){
			e.printStackTrace();
		}
		return dslist;
	}

	public int getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(int joinStatus) {
		this.joinStatus = joinStatus;
	}

	public boolean isCurUserIsjoin() {
		return curUserIsjoin;
	}

	public void setCurUserIsjoin(boolean curUserIsjoin) {
		this.curUserIsjoin = curUserIsjoin;
	}

}
