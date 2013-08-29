package com.example.bupt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppUtil {

	/* 获取耗费的内存 */
	public static long getUsedMemory() {
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		return total - free;
	}

	/**
	 * md5加密
	 */
	public static String md5(String str){
		MessageDigest algorithm=null;
		try {
			algorithm=MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(algorithm!=null){
			algorithm.reset();
			algorithm.update(str.getBytes());
			byte[] bytes=algorithm.digest();
			StringBuilder hexString=new StringBuilder();
			for(byte b:bytes){
				hexString.append(Integer.toHexString(0xFF&b));
			}
			return hexString.toString();
		}
		return "";
	}
	
	/**
	 * 首字母大写
	 */
	public static String ucfirst(String str){
		if(str!=null &&str!=""){
			str=str.substring(0,1).toUpperCase()+str.substring(1);
		}
		return str;
	}
	
	
}
