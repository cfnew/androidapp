package com.example.bupt.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class CustomHttpURLConnection {
	private static String TAG = "CustomHttpURLContecton";
	private static HttpURLConnection conn;
	private static int CONNECT_TIMEOUT = 5000;
	private static int READ_TIMEOUT = 5000;
	private static int RETRY_TIME = 3;
	
	private CustomHttpURLConnection(){
		
	}
	
	public static String getByHttpURLConnection(String myUrl, NameValuePair... nameValuePairs){
		InputStream is = null;
		try{
			String finalUrl = null;
			if(nameValuePairs != null && nameValuePairs.length > 0){
				//finalUrl = HttpTool.generateGetUrl(myUrl, nameValuePairs);
			}else{
				finalUrl = myUrl;
			}
			URL url = new URL(finalUrl);
			conn = (HttpURLConnection)url.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			//start the query
			conn.connect();
			int response = conn.getResponseCode();
			Log.d(TAG, "the response code is:"+response);
			is = conn.getInputStream();
			String res = readFromStream(is);
			conn.disconnect(); //断开连接
			return res;
		}catch(MalformedURLException e){
			Log.e(TAG, "getByHttpURLConnection:"+e.getMessage());
			e.printStackTrace();
			return null;
		}catch(IOException e){
			Log.e(TAG,"getByHttpURLConnection:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public static String postByHttpURLConnection(String myUrl, NameValuePair... nameValuePairs){
		InputStream is = null;
		try{
			URL url = new URL(myUrl);
			conn = (HttpURLConnection)url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false); //post方法不使用缓存
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			conn.connect();
			//DataOutputStream流  
            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());  
            //要上传的参数  
            StringBuilder sb = new StringBuilder();
            if(null != nameValuePairs && nameValuePairs.length > 0){
            	for(int i=0; i<nameValuePairs.length; i++){
            		if(i>0){
            			sb.append("&");
            		}
            		sb.append(URLEncoder.encode(nameValuePairs[i].getName(),"UTF-8"));
            		sb.append("=");
            		sb.append(URLEncoder.encode(nameValuePairs[i].getValue(),"UTF-8"));
            	}
            }
            //将要上传的内容写入流中
            outputStream.write(sb.toString().getBytes());  
            //刷新，关闭  
            outputStream.flush();  
            outputStream.close();  
			int response = conn.getResponseCode();
			Log.d(TAG, "the response code is:"+response);
			//取数据
			is = conn.getInputStream();
			String res = readFromStream(is);
			conn.disconnect(); //断开连接
			return res;
		}catch(IOException e){
			Log.e(TAG,"getByHttpURLConnection:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据网络地址获取网络图片
	 */
	public static Bitmap getNetBitmap(Context context, String picUrl){
		Log.i("net_url",picUrl);
		InputStream is = null;
		Bitmap bitmap = null;
		int time = 0;
		do{
			try 
			{
				URL url = new URL(picUrl);
				if(HttpUtils.getNetworkType(context) == HttpUtils.TYPE_CM_CU_WAP){
					Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("10.0.0.172", 80));
					conn = (HttpURLConnection)url.openConnection(proxy);
				}else{
					conn = (HttpURLConnection)url.openConnection();
				}
				conn.setConnectTimeout(CONNECT_TIMEOUT);
				conn.setReadTimeout(READ_TIMEOUT);
				conn.connect();
				is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				break;
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
			} finally {
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}while(time < RETRY_TIME);
		return bitmap;
	}
	
	public static String uploadBitmap(String upUrl, File uploadPic){
		String end ="\r\n";
        String twoHyphens ="--";
        String boundary ="=============";
        try{
        	URL url = new URL(upUrl);
        	HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        	conn.setDoInput(true);
        	conn.setDoOutput(true);
        	conn.setUseCaches(false);
        	conn.setRequestMethod("POST");
        	conn.setRequestProperty("Connection", "Keep-Alive");
        	conn.setRequestProperty("Charset", "UTF-8");
        	conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
        	// 设置DataOutputStream
        	DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
        	ds.writeBytes(twoHyphens + boundary + end);
        	ds.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"newuploadpic\""+ end);
        	ds.writeBytes(end);  
        	//取得文件的FileInputStream 
        	FileInputStream fStream = new FileInputStream(uploadPic);
        	//设置每次写入1024bytes
        	byte[] buffer = new byte[1024];
        	int length = -1;
        	//从文件读取数据至缓冲区
        	while((length = fStream.read(buffer)) != -1){
        		ds.write(buffer, 0, length);
        	}
        	ds.writeBytes(end);
        	ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
        	//close streams
        	fStream.close();
        	ds.flush();
        	ds.close();
        	//取得Response内容
        	InputStream is = conn.getInputStream();
        	if(null == is) return null;
        	String res = readFromStream(is);
        	return res;
        }catch(IOException e){
			Log.e(TAG,"getByHttpURLConnection:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	private static String readFromStream(InputStream stream) throws IOException{
		String result = "";
		InputStreamReader isr = new InputStreamReader(stream,"UTF-8");
		BufferedReader buffer = new BufferedReader(isr);
		String line = "";
		while((line = buffer.readLine()) != null){
			result += line;
		}
		buffer.close(); //关闭流
		return result;
	}
	
	
}
