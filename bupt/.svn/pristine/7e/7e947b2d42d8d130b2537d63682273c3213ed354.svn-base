package com.example.bupt.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @author clownfish 查看当前网络是否可用
 * 
 */
public class NetworkUtil {

	// 没有可用的设备
	public static final int NETWORK_NONE = 0;
	// 可用
	public static final int NETWORK_WIFI = 1;
	public static final int NETWORK_MOBILE = 2;

	/**
	 * 获取网络状态
	 * 
	 * @param context
	 * @return
	 */
	public static int getNetworkState(Context context) {
		// 获取网络状态管理器
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return NETWORK_NONE;
		} else {
			NetworkInfo[] infos = manager.getAllNetworkInfo();
			if (infos != null) {
				for (NetworkInfo network : infos) {
					if (network.getState() == NetworkInfo.State.CONNECTED) {
						if (network.getType() == ConnectivityManager.TYPE_WIFI) {
							return NETWORK_WIFI;
						} else if (network.getType() == ConnectivityManager.TYPE_MOBILE) {
							return NETWORK_MOBILE;
						}
					}
				}
			} else {
				return NETWORK_NONE;
			}
		}
		return NETWORK_NONE;
	}
}
