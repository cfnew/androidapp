package com.example.bupt.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.bupt.R;

public class CustomHttpClient {
	private static String TAG = "CustomHttpClient";
	private static int POOL_CONNECT_TIMEOUT = 5000;
	private static int NET_CONNECT_TIMEOUT = 10000;
	private static int WIFI_CONNECT_TIMEOUT = 3000;
	private static int SO_TIMEOUT = 4000;
	private static int RETRY_TIME = 3;
	private static HttpClient client;
	
	private CustomHttpClient(){
		
	}
	
	/**
	 * post方法
	 * 
	 * @param url
	 * @param nameValuePairs
	 * @return
	 */
	public static String postByHttpClient(Context context, String myUrl,Map<String, String> nameValuePairs) {
		try {
			List<NameValuePair> params = getPostParams(nameValuePairs);
			UrlEncodedFormEntity urlEncoded = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			HttpPost httpPost = new HttpPost(myUrl);
			httpPost.setEntity(urlEncoded);
			HttpClient client = getHttpClient(context);
			HttpResponse response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException(context.getResources().getString(R.string.network_error));
			}
			HttpEntity resEntity = response.getEntity();
			return (resEntity == null) ? null : EntityUtils.toString(resEntity,HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			Log.w(TAG, e.getMessage());
			return null;
		} catch (ClientProtocolException e) {
			Log.w(TAG, e.getMessage());
			return null;
		} catch (IOException e) {
			throw new RuntimeException(context.getResources().getString(R.string.network_error), e);
		} 
	}
	
	/**
	 * get方法
	 * @param context
	 * @param myUrl
	 * @param nameValuePairs
	 * @return
	 */
	public static String getByHttpClient(Context context, String myUrl, Map<String, String> nameValuePairs){
		Log.w("getByHttpClient url = " , myUrl);
		try{
			String finalUrl = nameValuePairs==null ? myUrl : generateGetUrl(myUrl, nameValuePairs);
			//get方法
			HttpGet httpGet = new HttpGet(finalUrl);
			//取得HttpClient对象
			HttpClient client = getHttpClient(context);
			HttpResponse response = client.execute(httpGet);
			// 请求成功
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException(context.getResources().getString(R.string.network_error));
			}
			HttpEntity resEntity = response.getEntity();
			return (resEntity == null) ? null : EntityUtils.toString(resEntity,HTTP.UTF_8);
		}catch(ParseException e){
			throw new RuntimeException(context.getResources().getString(R.string.network_error),e);
		}catch(IOException e){ //在EntityUtils.toString 的时候会抛出IO异常
			Log.e("IOException ",e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(context.getResources().getString(R.string.network_error),e);
		}
	}
	
	
	/**
	 * 获取HttpClient实例
	 * @param context
	 * @return
	 */
	private static synchronized HttpClient getHttpClient(Context context){
		if(null == client){
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			/* 从连接池中取连接的超时时间 */
			ConnManagerParams.setTimeout(params, POOL_CONNECT_TIMEOUT);
			//连接超时
			int timeout = NET_CONNECT_TIMEOUT;
			if(HttpUtils.getNetworkType(context) == HttpUtils.TYPE_OTHER_NET){
				timeout = WIFI_CONNECT_TIMEOUT;
			}
			HttpConnectionParams.setConnectionTimeout(params, timeout);
			//请求超时
			HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
			// 设置HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
			client = new DefaultHttpClient(conMgr, params);
			//设置代理
			switch (HttpUtils.getNetworkType(context)) {
			case HttpUtils.TYPE_CT_WAP: {
				// 通过代理解决中国移动联通GPRS中wap无法访问的问题
				HttpHost proxy = new HttpHost("10.0.0.200", 80, "http");
				client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
			}
				break;
			case HttpUtils.TYPE_CM_CU_WAP: {
				// 通过代理解决中国移动联通GPRS中wap无法访问的问题
				HttpHost proxy = new HttpHost("10.0.0.172", 80, "http");
				client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
			}
				break;
			}
		}
		return client;
	}
	
	/**
	 * 根据参数生成访问url
	 * @param baseUrl
	 * @param nameValuePairs
	 * @return
	 */
	private static String generateGetUrl(String baseUrl, Map<String, String> params) {
		StringBuilder url = new StringBuilder(baseUrl);
		if(url.indexOf("?")<0)
			url.append('?');

		for(String name : params.keySet()){
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(params.get(name));
			//不做URLEncoder处理
			//url.append(URLEncoder.encode(String.valueOf(params.get(name)), UTF_8));
		}

		return url.toString().replace("?&", "?");
	}
	
	/**
	 * 将map类型的参数转换为 URL可用的参数类型 
	 * @param paramMap
	 * @return
	 */
	private static List<NameValuePair> getPostParams(Map<String, String> paramMap){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(String key : paramMap.keySet()){
			params.add(new BasicNameValuePair(key,paramMap.get(key)));
		}
		return params;
	}
	
	public static Bitmap getNetBitmap(Context context, String picUrl){
		if (picUrl == null || picUrl.equals("null")) return null;
		InputStream is = null;
		Bitmap bitmap = null;
		int time = 0;
		do{
			try 
			{
				HttpGet httpGet = new HttpGet(picUrl);
				//取得HttpClient对象
				HttpClient client = getHttpClient(context);
				HttpResponse response = client.execute(httpGet);
				if (response == null) continue;
				int statusCode = response.getStatusLine().getStatusCode();
				if(200 == statusCode){
					is = new BufferedInputStream(response.getEntity().getContent());
				}
				if (is == null) continue;
				bitmap = BitmapFactory.decodeStream(is);
				if(bitmap != null) break;
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
	
	
}
