package com.example.bupt.model;
/*
 * 用作paopaodetails显示用户头像列表
 */
public class DetailsSculpture extends Entity{
	
	private int uid;
	private String avatar_middle;
	private int isPoster;
	
	
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getAvatar_middle() {
		return avatar_middle;
	}
	public void setAvatar_middle(String avatar_middle) {
		this.avatar_middle = avatar_middle;
	}
	public int getIsPoster() {
		return isPoster;
	}
	public void setIsPoster(int isPoster) {
		this.isPoster = isPoster;
	}

}
