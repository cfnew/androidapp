package com.example.bupt.utils;

import android.util.Log;

public class LastClickUtil {
	private static long lastClickTime;
	static{
		lastClickTime = System.currentTimeMillis();
	}
	public static boolean onClick() { 
		long time = System.currentTimeMillis(); 
		long timeD = time - lastClickTime; 
		if ( 0 < timeD && timeD < 247) {
			Log.i("lianji","lianji");
			return false; 
		} 
		lastClickTime = time; 
		return true; 
	} 
}
