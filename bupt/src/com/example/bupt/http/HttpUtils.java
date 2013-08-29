package com.example.bupt.http;

import java.io.File;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.example.bupt.utils.StringUtils;

public class HttpUtils {
	
	public static final String CTWAP = "ctwap";
	public static final String CMWAP = "cmwap";
	public static final String WAP_3G = "3gwap";
	public static final String UNIWAP = "uniwap";
	// 网络不可用 */
	public static final int TYPE_NET_WORK_DISABLED = 0;
	//移动联通wap10.0.0.172 */
	public static final int TYPE_CM_CU_WAP = 4;
	//电信wap 10.0.0.200 */
	public static final int TYPE_CT_WAP = 5;
	//电信,移动,联通,wifi 等net网络 */
	public static final int TYPE_OTHER_NET = 6;
	public static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
	
	private static final int TRY_TIMES = 3;
	
	/**
	 * 利用HttpClient post获取数据
	 * @param context
	 * @param strUrl
	 * @param nameValuePairs
	 * @return
	 */
	public static JSONObject postByHttpClient(Context context,String strUrl,Map<String,String> nameValuePairs) {
		String res = CustomHttpClient.postByHttpClient(context,strUrl, nameValuePairs);
		JSONObject obj = null;
		if(null != res){
			try {
				obj = JsonUtil.parseResponse(res);
			} catch (JSONException e) {
				Log.e("HttpTool->postByHttpClient:", "json parse error");
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 利用HttpClient get获取数据
	 * @param context
	 * @param strUrl
	 * @param nameValuePairs
	 * @return
	 */
	public static JSONObject getByHttpClient(Context context, String strUrl,
			Map<String, String> nameValuePairs) {
		String res = CustomHttpClient.getByHttpClient(context, strUrl,
				nameValuePairs);
		JSONObject obj = null;
		if (null != res) {
			try {
				obj = JsonUtil.parseResponse(res);
			} catch (JSONException e) {
				Log.e("HttpTool->getByHttpClient:", "json parse error");
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	/**
	 * 获取网络图片
	 * @param url
	 * @return
	 */
	public static Bitmap getNetBitmap(Context context, String url){
		Bitmap bitmap = CustomHttpClient.getNetBitmap(context,url);
		return bitmap;
	}
	
	/**
	 * 上传一张图片
	 * @param upUrl
	 * @param upImage
	 * @return
	 */
	public static JSONObject uploadImage(String upUrl, File upImage){
		String res = CustomHttpURLConnection.uploadBitmap(upUrl, upImage);
		JSONObject obj = null;
		if(null != res){
			try {
				obj = JsonUtil.parseResponse(res);
			} catch (JSONException e) {
				Log.e("HttpTool->uploadImage:", "json parse error");
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	/**
	 * 网络是否已经连接
	 */
	public static boolean isNetworkConnected(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	/**
	 * 获取当前网络类型
	 * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
	 */
	public static int getNetworkType(Context context){
		try {
			final ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			final NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			if (networkInfo == null || !networkInfo.isAvailable()) {
				// 注意一：
				// NetworkInfo 为空或者不可以用的时候正常情况应该是当前没有可用网络，
				// 但是有些电信机器，仍可以正常联网，
				// 所以当成net网络处理依然尝试连接网络。
				// （然后在socket中捕捉异常，进行二次判断与用户提示）。
				return TYPE_NET_WORK_DISABLED;
			} else {
				// NetworkInfo不为null开始判断是网络类型
				int netType = networkInfo.getType();
				if (netType == ConnectivityManager.TYPE_WIFI) {
					// wifi net处理
					return TYPE_OTHER_NET;
				} else if (netType == ConnectivityManager.TYPE_MOBILE) {
					// 注意二：
					// 判断是否电信wap:
					// 不要通过getExtraInfo获取接入点名称来判断类型，
					// 因为通过目前电信多种机型测试发现接入点名称大都为#777或者null，
					// 电信机器wap接入点中要比移动联通wap接入点多设置一个用户名和密码,
					// 所以可以通过这个进行判断！
					final Cursor c = context.getContentResolver().query(
							PREFERRED_APN_URI, null, null, null, null);
					if (c != null) {
						c.moveToFirst();
						final String user = c.getString(c.getColumnIndex("user"));
						if (!StringUtils.isEmpty(user)) {
							if (user.startsWith(CTWAP)) {
								return TYPE_CT_WAP;
							}
						}
					}
					c.close();

					// 注意三：
					// 判断是移动联通wap:
					// 其实还有一种方法通过getString(c.getColumnIndex("proxy")获取代理ip
					// 来判断接入点，10.0.0.172就是移动联通wap，10.0.0.200就是电信wap，但在
					// 实际开发中并不是所有机器都能获取到接入点代理信息，例如魅族M9 （2.2）等...
					// 所以采用getExtraInfo获取接入点名字进行判断
					String netMode = networkInfo.getExtraInfo();
					if (netMode != null) {
						// 通过apn名称判断是否是联通和移动wap
						netMode = netMode.toLowerCase();
						if (netMode.equals(CMWAP) || netMode.equals(WAP_3G) || netMode.equals(UNIWAP)) {
							return TYPE_CM_CU_WAP;
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return TYPE_OTHER_NET;
		}
		return TYPE_OTHER_NET;
	}
	
}
