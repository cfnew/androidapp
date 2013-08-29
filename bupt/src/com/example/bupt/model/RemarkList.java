package com.example.bupt.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RemarkList extends Entity
{

    private static final long serialVersionUID = 1L;
    
    private int result;
    private String info;
    private int pageSize = 0;
    private List<Remark> RemarkList = new ArrayList<Remark>();
    
    public int getResult(){
        return this.result;
    }
    public String getInfo(){
        return this.info;
    }
    public int getPageSize(){
        return this.pageSize;
    }
    
    public static RemarkList parse(JSONObject jsonObject){
        RemarkList reList = new RemarkList();
        Remark remark = null;
        JSONObject obj = null;
        try{
            reList.result = jsonObject.getInt("status");
            reList.info = jsonObject.getString("info");
            
            if(reList.result == 1){
                JSONArray jArr = jsonObject.getJSONArray("data");
                int length = jArr.length();
                if(length > 0){
                    reList.pageSize = length;
                    for(int i = 0; i < length; i++){
                        obj = jArr.getJSONObject(i);
                        remark = new Remark();
                        remark.setComment_id(obj.getInt("comment_id"));
                        remark.setShare_id(obj.getInt("share_id"));
                        remark.setShare_uid(obj.getInt("share_uid"));
                        remark.setUid(obj.getInt("uid"));
                        remark.setContent(obj.getString("content"));
                        remark.setCtime(obj.getLong("ctime"));
                        remark.setStorey(obj.getInt("storey"));
                        remark.setUface(obj.getString("uface"));
                        remark.setUname(obj.getString("uname"));
                        reList.RemarkList.add(remark);
                    }
                    Log.i("RemarkList", reList.RemarkList.toArray().toString());
                }
            }
            //通知消息
        }catch(JSONException e){
            e.printStackTrace();
        }
        return reList;
    }
    public List<Remark> getRemarkList() {
        return RemarkList;
    }
    public void setRemarkList(List<Remark> remarkList) {
        RemarkList = remarkList;
    }
}
