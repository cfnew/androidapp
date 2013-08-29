package com.example.bupt.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareList extends Entity{
	
	public static final int LIST_TYPE_LATEST = 0;
	
	private int status;
	private String info;
	private int pageSize;
	private List<Share> shareList = new ArrayList<Share>();
	
	public int getPageSize(){
		return this.pageSize;
	}
	public List<Share> getShareList(){
		return shareList;
	}
	
	public static ShareList parse(JSONObject jsonObj){
		ShareList shareList = new ShareList();
		Share share = null;
		JSONObject jitem = null;
		JSONArray initem= null;
		try{
			shareList.status = jsonObj.getInt("status");
			shareList.info = jsonObj.getString("info");
			if(shareList.status == 1){
				JSONArray jarr = jsonObj.getJSONArray("data");
				int length = jarr.length();
				if(length > 0){
					shareList.pageSize = length; //将获取到的数据数量赋值
					for(int i=0; i<length; i++){
						jitem = jarr.getJSONObject(i); //一个Share
						share = new Share();
						share.setPaopaoId(jitem.getInt("feed_id"));
						share.setPaopaoTitle(jitem.getString("feed_title"));
						share.setPaopaoLocation(jitem.getString("feed_location"));
						//获取所有的参与者信息
						initem = jitem.getJSONArray("feed_user");
						List<Map<String, String>> uList = new ArrayList<Map<String, String>>();
						Map<String, String> uinfo;
						for(int j=0; j<initem.length(); j++){
							uinfo = new HashMap<String, String>();
							uinfo.put("uid", initem.getJSONObject(j).getString("uid"));
							uinfo.put("uface", initem.getJSONObject(j).getString("uface"));
							uinfo.put("is_poster", initem.getJSONObject(j).getString("is_poster"));
							uList.add(uinfo);
						}
						share.setPaopaoFaces(uList);
						//获取所有的分享图片信息
						initem = jitem.getJSONArray("share_data");
						List<Map<String, String>> sList = new ArrayList<Map<String, String>>();
						Map<String, String> sinfo;
						for(int j=0; j<initem.length(); j++){
							sinfo = new HashMap<String, String>();
							sinfo.put("uid", initem.getJSONObject(j).getInt("uid")+"");
							sinfo.put("uface", initem.getJSONObject(j).getString("uface"));
							sinfo.put("share_id", initem.getJSONObject(j).getInt("share_id")+"");
							sinfo.put("share_small", initem.getJSONObject(j).getString("share_attach_small"));
							sinfo.put("share_ori", initem.getJSONObject(j).getString("share_attach_ori"));
							sList.add(sinfo);
						}
						share.setPaopaoShares(sList);
						
						shareList.shareList.add(share);
					}
				}
			}
			//消息
			
		}catch(JSONException e){
			e.printStackTrace();
		}
		return shareList;
	}
	
}
