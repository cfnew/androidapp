package com.example.bupt.model;

import java.io.File;
import java.util.Date;

public class Paopao extends Entity{
	
	private int feedId;
	private String feedPic;
	private String feedPicBig;
	private File feedPicFile;
	private String feedTitle;
	private String feedLocation;
	private int feedNeedNum; //活动需要人数
	private int feedJoinNum; //活动参加人数
	private Date feedStartTime; //活动开始时间
	private String feedDistance; //LBS
	private String feedTag;
	
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}
	public String getFeedPic() {
		return feedPic;
	}
	public void setFeedPic(String feedPic) {
		this.feedPic = feedPic;
	}
	public String getFeedPicBig() {
		return feedPicBig;
	}
	public void setFeedPicBig(String feedPicBig) {
		this.feedPicBig = feedPicBig;
	}
	public File getFeedPicFile() {
		return feedPicFile;
	}
	public void setFeedPicFile(File feedPicFile) {
		this.feedPicFile = feedPicFile;
	}
	public String getFeedTitle() {
		return feedTitle;
	}
	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}
	public String getFeedLocation() {
		return feedLocation;
	}
	public void setFeedLocation(String feedLocation) {
		this.feedLocation = feedLocation;
	}
	public int getFeedNeedNum() {
		return feedNeedNum;
	}
	public void setFeedNeedNum(int feedNeedNum) {
		this.feedNeedNum = feedNeedNum;
	}
	public int getFeedJoinNum() {
		return feedJoinNum;
	}
	public void setFeedJoinNum(int feedJoinNum) {
		this.feedJoinNum = feedJoinNum;
	}
	public String getFeedDistance() {
		return feedDistance;
	}
	public void setFeedDistance(String feedDistance) {
		this.feedDistance = feedDistance;
	}
	public Date getFeedStartTime() {
		return feedStartTime;
	}
	public void setFeedStartTime(Date feedStartTime) {
		this.feedStartTime = feedStartTime;
	}
	public String getFeedTag() {
		return feedTag;
	}
	public void setFeedTag(String feedTag) {
		this.feedTag = feedTag;
	}
	
}
