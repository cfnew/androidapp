package com.example.bupt.model;


public class Friend extends Entity{
	
	public static final String COL_FRI_ID = "friend_id";
	public static final String COL_FRI_NAME = "friend_name";
	public static final String COL_FRI_PINYIN = "friend_pinyin";
	public static final String COL_FRI_FIRST_CHAR = "friend_first_char";
	public static final String COL_FRI_FACE = "friend_face";
	public static final String COL_FRI_SEX = "friend_sex";
	public static final String COL_FRI_GROUP = "friend_group";

	private String face;
	private String faceBig;
	private int friendId;
	private String friendName;
	private String friendPinyin;
	private String firstChar;
	private int sex;
	private int age;
	private String star;
	private String location;
	private String desc;
	private int group=0; //默认没有分组
	private int isFriend=0;
	
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}	
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getFriendPinyin() {
		return friendPinyin;
	}
	public void setFriendPinyin(String friendPinyin) {
		this.friendPinyin = friendPinyin;
	}
	public String getFirstChar() {
		return firstChar;
	}
	public void setFirstChar(String firstChar) {
		this.firstChar = firstChar;
	}
	public String getFaceBig() {
		return faceBig;
	}
	public void setFaceBig(String faceBig) {
		this.faceBig = faceBig;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getIsFriend() {
		return isFriend;
	}
	public void setIsFriend(int isFriend) {
		this.isFriend = isFriend;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	
}
