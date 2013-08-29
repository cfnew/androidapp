package com.example.bupt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.bupt.base.AppConfig;

public class PreferenceUtil {

	/**
	 * SharedPreferences设置指定的字符串
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setStringPreferences(Context context, String key,String value) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * SharedPreferences获取指定的字符串
	 * 
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getStringPreference(Context context, String key) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		return sharedPreferences.getString(key, null);
	}

	/**
	 * SharedPreferences设置指定的long值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setLongPreference(Context context, String key, long value) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	/**
	 * SharedPreferences获取指定的long值
	 * 
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLongPreference(Context context, String key) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		return sharedPreferences.getLong(key, 0);
	}
	
	/**
	 * SharedPreferences获取指定的long值
	 * 
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLongPreference(Context context, String key, long def) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		return sharedPreferences.getLong(key, def);
	}
	
	/**
	 * SharedPreferences设置指定的long值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setIntPreference(Context context, String key, int value) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * SharedPreferences获取指定的long值
	 * 
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getIntPreference(Context context, String key) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		return sharedPreferences.getInt(key, 0);
	}

	/**
	 * SharedPreferences设置指定的long值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setBooleanPreference(Context context, String key, boolean value) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * SharedPreferences获取指定的boolean值
	 * 
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBooleanPreference(Context context, String key) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		return sharedPreferences.getBoolean(key, true);
	}

	/**
	 * 清空SharePreference里的所有数据
	 * 
	 * @param context
	 */
	public static void clearAll(Context context) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 删除SharePreference里指定key对应的数据项
	 * 
	 * @param context
	 * @param key
	 */
	public static void removeByKey(Context context, String key) {
		SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}

}
