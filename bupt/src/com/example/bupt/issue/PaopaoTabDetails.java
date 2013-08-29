package com.example.bupt.issue;

public class PaopaoTabDetails {
	private static String[] TabName = new String[]{"吃饭","酒吧","电影","唱歌","活动","演出","沙龙","咖啡","其他"};

	public static String getTabName(int i) {
		return PaopaoTabDetails.TabName[i];
	}

	public static void setTabName(String[] tabName) {
		PaopaoTabDetails.TabName = tabName;
	}
	
	public static String[] getAllTab(){
		return TabName;
	}

}
