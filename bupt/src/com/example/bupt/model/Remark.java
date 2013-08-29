package com.example.bupt.model;

public class Remark 
{
    private int comment_id;
    private int share_id;
    private int share_uid;
    private int uid;
    private String content;
    private long ctime;
    private int storey;
    private String uface;
    
    public int getComment_id() {
        return comment_id;
    }
    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }
    public int getShare_id() {
        return share_id;
    }
    public void setShare_id(int share_id) {
        this.share_id = share_id;
    }
    public int getShare_uid() {
        return share_uid;
    }
    public void setShare_uid(int share_uid) {
        this.share_uid = share_uid;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getCtime() {
        return ctime;
    }
    public void setCtime(long ctime) {
        this.ctime = ctime;
    }
    public int getStorey() {
        return storey;
    }
    public void setStorey(int storey) {
        this.storey = storey;
    }
    public String getUface() {
        return uface;
    }
    public void setUface(String uface) {
        this.uface = uface;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    private String uname;
    
    
}
