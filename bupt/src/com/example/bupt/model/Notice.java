package com.example.bupt.model;

import java.io.Serializable;

/**
 * 通知信息实体类
 */
public class Notice implements Serializable {
	
	public final static String UTF8 = "UTF-8";
	public final static String NODE_ROOT = "willing";
	
	public final static String TYPE_PAOPAO = "1";
	public final static String	TYPE_ATME = "2";
	public final static String	TYPE_MESSAGE = "3";
	public final static String	TYPE_COMMENT = "4";
	public final static String	TYPE_NEWFAN = "5";

	private int paopaoCount;
	private int atmeCount;
	private int msgCount;
	private int reviewCount;
	private int newFriendCount;
	
	/*****setter getter***********************************/
	public int getAtmeCount() {
		return atmeCount;
	}
	public void setAtmeCount(int atmeCount) {
		this.atmeCount = atmeCount;
	}
	public int getMsgCount() {
		return msgCount;
	}
	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public int getNewFriendCount() {
		return newFriendCount;
	}
	public void setNewFriendCount(int newFriendCount) {
		this.newFriendCount = newFriendCount;
	}	
	
	public int getPaopaoCount() {
		return paopaoCount;
	}
	public void setPaopaoCount(int paopaoCount) {
		this.paopaoCount = paopaoCount;
	}
}
