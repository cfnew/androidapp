package com.example.bupt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static String FORMAT_SHORT = "yyyy-MM-dd";
	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 中文简写 如：2010年12月01日
	 */
	public static String FORMAT_SHORT_CN = "yyyy年MM月dd";

	/**
	 * 中文全称 如：12月01日 23:15
	 */
	public static String FORMAT_MIDDLE_CN = "MM月dd日  HH:mm";

	/**
	 * 中文全称 如：2010年12月01日 23:15
	 */
	public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH:mm";

	static{
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
	}

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDefaultDatePattern() {
		return FORMAT_LONG;
	}

	/**
	 * 获取默认格式的当前时间字符串
	 */
	public static String format() {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FULL, Locale.CHINA);
		Calendar calendar = Calendar.getInstance();
		return sdf.format(calendar.getTime());
	}

	/**
	 * 根据pattern获取当前时间字符串
	 * @param pattern
	 */
	public static String format(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.CHINA);
		Calendar calendar = Calendar.getInstance();
		return sdf.format(calendar.getTime());
	}

	/**
	 * 使用用户格式格式化日期
	 * @param date 日期
	 * @param pattern 日期格式
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
			returnValue = sdf.format(date);
		}
		return (returnValue);
	}

	/**
	 * 使用用户格式提取字符串日期
	 * @param strDate 日期字符串
	 * @param pattern 日期格式
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static  boolean isDate(String str_input,String rDateFormat){
		if (str_input != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			formatter.setLenient(false);
			try {
				formatter.format(formatter.parse(str_input));
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 获取当前Unix时间戳
	 */
	 public static long getUnixTimeStamp(){
		 long stamp = System.currentTimeMillis();
		 return stamp / 1000;
	 }

	 /**
	  * 根据时间戳（秒数）获取Date对象
	  * @param stamp
	  * @return
	  */
	 public static Date parseTimeStamp(long stamp){
		 return new Date(stamp*1000);
	 }

	 /**
	  * 根据生日时间戳，生成年龄
	  * @param stamp
	  * @return
	  */
	 public static int genAge(long stamp){
		 Date date = new Date(stamp*1000);
		 Calendar cal = Calendar.getInstance();
		 int nowYear = cal.get(Calendar.YEAR);
		 cal.setTime(date);
		 int birYear = cal.get(Calendar.YEAR);
		 return nowYear-birYear;
	 }

	 /**
	  * 获取未来的某天距离今天的天数
	  * @return
	  */
	 public static int getDistance(Date future){
		 if(isOutOfDate(future)) return -1; //活动过期返回-1
		 Calendar cal = Calendar.getInstance();
		 int nowDay = cal.get(Calendar.DAY_OF_YEAR);
		 cal.setTime(future);
		 int futDay = cal.get(Calendar.DAY_OF_YEAR);
		 if(futDay >= nowDay) {
			 return futDay-nowDay;
		 } else{
			 //只能是跨年了
			 cal.clear();
			 int total = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
			 return futDay + total - nowDay;
		 }
	 }

	 /**
	  * 是否过期，如果传递的参数小于现在的时间则认为过期
	  * @return >0 过期
	  */
	 public static boolean isOutOfDate(Date future){
		 boolean flag = false;
		 Calendar cal = Calendar.getInstance();
		 Date cur = cal.getTime();
		 try{
			 flag = cur.after(future);
		 }catch (Exception e){
			 flag = true;
		 }
		 return flag;
	 }

	 /**
	  * 以友好的方式显示时间
	  * @param sdate
	  * @return
	  */
	 public static String friendlyTime(Date time) {
		 if(time == null) {
			 return "Unknown";
		 }
		 String ftime = "";
		 Calendar cal = Calendar.getInstance();
		 String curDate = format(cal.getTime(),FORMAT_SHORT);
		 String paramDate = format(time,FORMAT_SHORT);
		 //判断是否是同一天
		 if(curDate.equals(paramDate)){
			 int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			 if(hour == 0){
				 if((cal.getTimeInMillis() - time.getTime())>=0){
					 ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
				 }else{
					 ftime = (-Math.min((cal.getTimeInMillis() - time.getTime()) / 60000,-1))+"分钟后";
				 }
			 }else {
				 if(hour>0){
					 ftime = hour+"小时前";
				 }else{
					 ftime = (-hour)+"小时后";
				 }
			 }
			 return ftime;
		 }else{
			 int days = (int)(cal.getTimeInMillis()-time.getTime())/86400000;
			 if(days == 1){
				 ftime = "昨天";
			 }else if(days == 2){
				 ftime = "前天";
			 }else if(days > 2 && days <= 10){
				 ftime = days+"天前";			
			 }else if(days == -1){
				 ftime = "明天";
			 }else if(days == -2){
				 ftime = "后天";
			 }else if(days <- 2 && days >= -10){ 
				 ftime = (-days)+"天后";          
			 }else if(days > 10){
				 //是否是同一年
				 int curYear = cal.get(Calendar.YEAR);
				 cal.setTime(time);
				 int pubYear = cal.get(Calendar.YEAR);
				 if(curYear == pubYear){
					 ftime = format(time,FORMAT_MIDDLE_CN);
				 }else{
					 ftime = format(time,FORMAT_LONG_CN);
				 }
			 }
		 }
		 return ftime;
	 }
}
