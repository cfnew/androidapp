package com.example.bupt.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.example.bupt.utils.StringUtils;

public class AppConfig {

	private final static String APP_CONFIG = "config";

	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
	
	public final static String CONF_COOKIE = "cookie";
	public final static String CONF_ACCESSTOKEN = "accessToken";
	public final static String CONF_ACCESSSECRET = "accessSecret";
	public final static String CONF_EXPIRESIN = "expiresIn";
	
	public final static String CONF_LOAD_IMAGE = "perf_loadimage";
	public final static String CONF_SCROLL = "perf_scroll";
	public final static String CONF_HTTPS_LOGIN = "perf_httpslogin";
	public final static String CONF_VOICE = "perf_voice";
	public final static String CONF_CHECKUP = "perf_checkup";

	public final static String SAVE_IMAGE_PATH = "save_image_path";
	public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory()
														+ File.separator
														+ "xianging"
														+ File.separator;

	private Context mContext;
	private static AppConfig appConfig;

	// 获取实例
	public static AppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}

	/**
	 * 获取Preference设置
	 * @param context
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * 是否加载显示文章图片
	 */
	public static boolean isLoadImage(Context context) {
		return getSharedPreferences(context).getBoolean(CONF_LOAD_IMAGE, true);
	}
	
	/**
	 * 获取cookie配置
	 */
	public String getCookie() {
		return get(CONF_COOKIE);
	}

	/**
	 * 设置cookie配置
	 * @param cookie
	 */
	public void setCookie(String cookie){
		set(CONF_COOKIE,cookie);
	}

	/**
	 * 获取AccessToken
	 */
	public String getAccessToken() {
		return get(CONF_ACCESSTOKEN);
	}
	
	/**
	 * 设置AccessToken
	 */
	public void setAccessToken(String accessToken) {
		set(CONF_ACCESSTOKEN, accessToken);
	}

	/**
	 * 获取AccessSecret
	 * @return
	 */
	public String getAccessSecret() {
		return get(CONF_ACCESSSECRET);
	}
	
	/**
	 * 设置AccessSecret
	 * @param accessSecret
	 */
	public void setAccessSecret(String accessSecret) {
		set(CONF_ACCESSSECRET, accessSecret);
	}

	/**
	 * 获取expireIn
	 * @return
	 */
	public long getExpiresIn() {
		return StringUtils.toLong(get(CONF_EXPIRESIN));
	}
	
	/**
	 * 设置expireIn
	 * @param expiresIn
	 */
	public void setExpiresIn(long expiresIn) {
		set(CONF_EXPIRESIN, String.valueOf(expiresIn));
	}

//	public void setAccessInfo(String accessToken, String accessSecret, long expiresIn) {
//		if (accessInfo == null)
//			accessInfo = new AccessInfo();
//		accessInfo.setAccessToken(accessToken);
//		accessInfo.setAccessSecret(accessSecret);
//		accessInfo.setExpiresIn(expiresIn);
//		// 保存到配置
//		this.setAccessToken(accessToken);
//		this.setAccessSecret(accessSecret);
//		this.setExpiresIn(expiresIn);
//	}
//
//	public AccessInfo getAccessInfo() {
//		if (accessInfo == null && !StringUtils.isEmpty(getAccessToken())
//				&& !StringUtils.isEmpty(getAccessSecret())) {
//			accessInfo = new AccessInfo();
//			accessInfo.setAccessToken(getAccessToken());
//			accessInfo.setAccessSecret(getAccessSecret());
//			accessInfo.setExpiresIn(getExpiresIn());
//		}
//		return accessInfo;
//	}
	
	/**
	 * 获取Propertites
	 * @return
	 */
	public Properties getProps() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			// 读取app_config目录下的config
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator + APP_CONFIG);
			props.load(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return props;
	}
	
	/**
	 * 设置配置信息
	 * @param p
	 */
	private void setProps(Properties p) {
		FileOutputStream fos = null;
		try {
			// 把config建在(自定义)app_config的目录下
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			File conf = new File(dirConf, APP_CONFIG);
			fos = new FileOutputStream(conf);

			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 根据key获取配置属性
	 * @param key
	 * @return
	 */
	public String get(String key) {
		Properties props = getProps();
		return (props != null) ? props.getProperty(key) : null;
	}

	public void set(Properties ps) {
		Properties props = getProps();
		props.putAll(ps);
		setProps(props);
	}

	public void set(String key, String value) {
		Properties props = getProps();
		props.setProperty(key, value);
		setProps(props);
	}

	public void remove(String... key) {
		Properties props = getProps();
		for (String k : key)
			props.remove(k);
		setProps(props);
	}
	
	
}
