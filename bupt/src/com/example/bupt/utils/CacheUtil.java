package com.example.bupt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;

import android.content.Context;

import com.example.bupt.base.AppConfig;
import com.example.bupt.base.BaseApp;

public class CacheUtil {
	
	private static final int CACHE_TIME = 60 * 60000;
	private static Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();
	
	/**
	 * 清除保存的缓存
	 */
	public static void cleanCookie(Context context){
		AppConfig.getAppConfig(context).remove(AppConfig.CONF_COOKIE);
	}
	
	/**
	 * 判断缓存数据是否可读
	 * @param cachefile
	 * @return
	 */
	public static boolean isCacheReadable(Context context,String cachefile){
		return readObject(context,cachefile) != null;
	}
	
	/**
	 * 判断缓存是否存在
	 * @param cachefile
	 * @return
	 */
	public static boolean isCacheExist(Context context,String cachefile){
		boolean exist = false;
		File data = context.getFileStreamPath(cachefile);
		if(data.exists())
			exist = true;
		return exist;
	}
	
	/**
	 * 判断缓存是否失效
	 * @param cachefile
	 * @return
	 */
	public static boolean isCacheDataFailure(Context context,String cachefile){
		boolean failure = false;
		File data = context.getFileStreamPath(cachefile);
		if(data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME){
			failure = true;
		}else if(!data.exists()){
			failure = true;
		}
		return failure;
	}
	
	/**
	 * 清除app缓存
	 */
	public static void clearAppCache(Context context){
		//清除webview缓存
//		File file = CacheManager.getCacheFileBaseDir();
//		if (file != null && file.exists() && file.isDirectory()) {
//		    for (File item : file.listFiles()) {
//		    	item.delete();  
//		    }  
//		    file.delete();  
//		}
		//清除数据缓存
		clearCacheFolder(context.getFilesDir(),System.currentTimeMillis());
		clearCacheFolder(context.getCacheDir(),System.currentTimeMillis());
		//2.2版本才有将应用缓存转移到sd卡的功能
		if(BaseApp.isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){
			clearCacheFolder(MethodsCompat.getExternalCacheDir(context),System.currentTimeMillis());
		}
		//清除properties里面的内容
		Properties props = AppConfig.getAppConfig(context).getProps();
		for(Object key : props.keySet()) {
			String _key = key.toString();
			if(_key.startsWith("temp"))
				AppConfig.getAppConfig(context).remove(_key);
		}
	}	
	
	/**
	 * 清除缓存目录，给定时间点之前的文件全部删除
	 * @param dir 目录
	 * @param numDays 当前系统时间
	 * @return 删除的文件数量
	 */
	private static int clearCacheFolder(File dir, long curTime) {          
	    int deletedFiles = 0;         
	    if (dir!= null && dir.isDirectory()) {
	        try {                
	            for (File child : dir.listFiles()) {    
	                if (child.isDirectory()) {              
	                    deletedFiles += clearCacheFolder(child, curTime);          
	                }  
	                if (child.lastModified() < curTime) { 
	                    if (child.delete()) {
	                        deletedFiles++;           
	                    }
	                }    
	            }             
	        } catch(Exception e) {       
	            e.printStackTrace();    
	        }     
	    }       
	    return deletedFiles;     
	}
	
	
	
	/**
	 * 将对象保存到内存缓存中
	 * @param key
	 * @param value
	 */
	public static void setMemCache(String key, Object value) {
		memCacheRegion.put(key, value);
	}
	
	/**
	 * 从内存缓存中获取对象
	 * @param key
	 * @return
	 */
	public static Object getMemCache(String key){
		return memCacheRegion.get(key);
	}
	
	/**
	 * 保存磁盘缓存
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public static void setDiskCache(Context context, String key, String value) throws IOException {
		FileOutputStream fos = null;
		try{
			fos = context.openFileOutput("cache_"+key+".data", Context.MODE_PRIVATE);
			fos.write(value.getBytes());
			fos.flush();
		}finally{
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}
	
	/**
	 * 获取磁盘缓存数据
	 * @param key
	 * @return content
	 * @throws IOException
	 */
	public static String getDiskCache(Context context, String key) throws IOException {
		FileInputStream fis = null;
		try{
			fis = context.openFileInput("cache_"+key+".data");
			byte[] datas = new byte[fis.available()];
			fis.read(datas);
			return new String(datas);
		}finally{
			try {
				fis.close();
			} catch (Exception e) {}
		}
	}
	
	/**
	 * 保存对象
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public static boolean saveObject(Context context, Serializable ser, String fileName) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try {
				oos.close();
			} catch (Exception e) {}
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}
	
	/**
	 * 读取对象
	 * @param file
	 * @return obj
	 * @throws IOException
	 */
	public static Serializable readObject(Context context, String fileName){
		if(!isCacheExist(context,fileName)) return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = context.openFileInput(fileName);
			ois = new ObjectInputStream(fis);
			return (Serializable)ois.readObject();
		}catch(FileNotFoundException e){
		}catch(Exception e){
			e.printStackTrace();
			//反序列化失败 - 删除缓存文件
			if(e instanceof InvalidClassException){
				File data = context.getFileStreamPath(fileName);
				data.delete();
			}
		}finally{
			try {
				ois.close();
			} catch (Exception e) {}
			try {
				fis.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	public static void clearObj(Context context, String fileName){
		File f = new File(context.getFilesDir(),fileName);
		if(f.exists()){
			f.delete();
		}
	}
}
