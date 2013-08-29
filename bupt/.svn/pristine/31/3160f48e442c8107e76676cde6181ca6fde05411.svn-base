package com.example.bupt.model;

import android.graphics.Bitmap;

public class User {
	
	public static final String COL_UID = "uid";
	public static final String COL_ACCOUNT = "account";
	public static final String COL_PASSWORD = "password";
	public static final String COL_NAME = "uname";
	public static final String COL_SEX = "usex";
	public static final String COL_BIRTHDAY = "ubirthday";
	public static final String COL_STAR = "ustar";
	public static final String COL_LOCATION = "ulocation";
	public static final String COL_FACE = "uface";
	public static final String COL_DESC = "udesc";
	

	private int uid; // 用户id
	private String account;// 用户账户，邮箱或手机
	private String password;// 用户密码
	private String uname;// 用户昵称
	private String usex;// 用户性别，1男2女
	private String ubirthday;// 生日,存转换之后的字符串，用SimpleDateFormat与String相互转换
	private String ustar;// 用户星座，根据用户生日自动生成
	private String ulocation;// 所在省市字符串，例：北京市 北京市 海淀区（中间用空格隔开）
	private Bitmap uface; // 用户头像
	private String udesc; // 用户描述

	public User() {
	}

	public User(int user_id, String user_name, String description) {
		this.uid = user_id;
		this.uname = user_name;
		this.udesc = description;
	}

	public User(int user_id, String user_name, Bitmap user_pic,String description) {
		this(user_id, user_name, description);
		this.uface = user_pic;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Bitmap getUface() {
		return uface;
	}

	public void setUface(Bitmap upic) {
		this.uface = upic;
	}

	public String getUdesc() {
		return udesc;
	}

	public void setUdesc(String udesc) {
		this.udesc = udesc;
	}

	public String getUsex() {
		return usex;
	}

	public void setUsex(String usex) {
		this.usex = usex;
	}

	public String getUbirthday() {
		return ubirthday;
	}

	public void setUbirthday(String ubirthday) {
		this.ubirthday = ubirthday;
	}

	public String getUstar() {
		return ustar;
	}

	public void setUstar(String ustar) {
		this.ustar = ustar;
	}

	public String getUlocation() {
		return ulocation;
	}

	public void setUlocation(String ulocation) {
		this.ulocation = ulocation;
	}


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [user_id=" + uid + ", user_name=" + uname
				+ ", user_pic=" + uface + ", description=" + udesc + "]";
	}

}
