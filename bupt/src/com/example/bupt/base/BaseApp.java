package com.example.bupt.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.example.bupt.utils.StringUtils;
import com.example.bupt.utils.ToastUtil;

/**
 * 配置一些全局变量
 */
public class BaseApp extends Application {
	
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;

	public static final int PAGE_SIZE = 20;
	private boolean isLogin = false;
	private String saveImagePath;
	// 当前登录用户的uid
	private static int uid = 0;

	// baidumap
	private static BMapManager mBMapMan = null;
	private static String baiduID = "CDF62A74BA9F42D9D1E1634FF2B283552CB6B1A0";
	// 定位
	private static LocationClient mLocationClient = null;
	private static MyLocationListenner myListener = null;
	private static Location myLocation = null;

	// 获得用户位置信息
	public static Location getLocation() {
		return myLocation;
	}

	// 开始定位
	public static void StartLocation() {
		mLocationClient.start();
	}

	// 结束定位
	public static void StopLocation() {
		mLocationClient.stop();
	}

	public static BMapManager getmBMapMan() {
		return mBMapMan;
	}
	
	public static int getUid() {
		return uid;
	}

	public static void setUid(int userid) {
		uid = userid;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 注册APP异常崩溃处理器
		//Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
		initEnv();
		initMap();
	}

	/**
	 * 初始化环境
	 */
	public void initEnv() {
		//图片保存路径
		this.saveImagePath = this.getProperty(AppConfig.SAVE_IMAGE_PATH);
		if(StringUtils.isEmpty(this.saveImagePath)){
			this.setProperty(AppConfig.SAVE_IMAGE_PATH, AppConfig.DEFAULT_SAVE_IMAGE_PATH);
			this.saveImagePath = AppConfig.DEFAULT_SAVE_IMAGE_PATH;
		}
	}

	/*
	 * 初始化地图
	 */
	private void initMap() {
		// 初始化百度map
		mBMapMan = new BMapManager(getApplicationContext());
		mBMapMan.init(baiduID, null);
		// 位置初始化
		mLocationClient = new LocationClient(this);
		myListener = new MyLocationListenner();
		mLocationClient.registerLocationListener(myListener);
		setLocationOption();
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setPriority(LocationClientOption.GpsFirst); // 默认是gps优先
		option.setScanSpan(3000);

		option.setPoiNumber(10);
		option.disableCache(true);
		mLocationClient.setLocOption(option);
	}

	/*
	 * 位置监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			myLocation = new Location(location.getLatitude(),
					location.getLongitude(), location.getAddrStr());
			mLocationClient.stop();
			ToastUtil.showMsg(BaseApp.this, "成功更新您所在的位置！");
			Log.i("Base", myLocation.getAddrStr());
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
		}
	}
	
	
	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}
	
	//判断设置///////////////////////////////////////////////////////////////////
	
	/**
	 * 用户是否登录
	 * @return
	 */
	public boolean isLogin() {
		return isLogin;
	}
	
	/**
	 * 判断当前版本是否兼容目标版本的方法
	 * @param VersionCode
	 * @return
	 */
	public static boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}

	/**
	 * 网络是否已经连接
	 */
	public boolean isNetworkConnected(){
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	/**
	 * 当前系统声音是否正常
	 */
	public boolean isAudioNormal(){
		AudioManager mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
		return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
	}
	
	/**
	 * 应用是否开启提示音
	 */
	public boolean isVoice(){
		String pref_voice = this.getProperty(AppConfig.CONF_VOICE);
		//默认开启提示音
		if(StringUtils.isEmpty(pref_voice)) return true;
		else return StringUtils.toBool(pref_voice);
	}
	
	/**
	 * 设置是否发出提示音
	 * @param b
	 */
	public void setConfigVoice(boolean b){
		setProperty(AppConfig.CONF_VOICE, String.valueOf(b));
	}
	
	/**
	 * 应用程序是否发出提示音
	 */
	public boolean isAppSound(){
		return isAudioNormal() && isVoice();
	}
	
	/**
	 * 是否加载显示文章图片
	 */
	public boolean isLoadImage(){
		String perf_loadimage = getProperty(AppConfig.CONF_LOAD_IMAGE);
		//默认是加载的
		if(StringUtils.isEmpty(perf_loadimage)) return true;
		else return StringUtils.toBool(perf_loadimage);
	}
	
	/**
	 * 设置是否加载活动图片
	 * @param b
	 */
	public void setConfigLoadimage(boolean b){
		setProperty(AppConfig.CONF_LOAD_IMAGE, String.valueOf(b));
	}
	
	/**
	 * 是否启动检查更新
	 * @return
	 */
	public boolean isCheckUp(){
		String perf_checkup = getProperty(AppConfig.CONF_CHECKUP);
		//默认是开启
		if(StringUtils.isEmpty(perf_checkup)) return true;
		else return StringUtils.toBool(perf_checkup);
	}
	
	/**
	 * 设置启动检查更新
	 * @param b
	 */
	public void setConfigCheckUp(boolean b){
		setProperty(AppConfig.CONF_CHECKUP, String.valueOf(b));
	}
	
	/**
	 * 是否左右滑动
	 * @return
	 */
	public boolean isScroll(){
		String perf_scroll = getProperty(AppConfig.CONF_SCROLL);
		//默认是关闭左右滑动
		if(StringUtils.isEmpty(perf_scroll)) return false;
		else return StringUtils.toBool(perf_scroll);
	}
	
	/**
	 * 设置是否左右滑动
	 * @param b
	 */
	public void setConfigScroll(boolean b){
		setProperty(AppConfig.CONF_SCROLL, String.valueOf(b));
	}
	
	public void setProperty(String key,String value){
		AppConfig.getAppConfig(this).set(key, value);
	}
	public String getProperty(String key){
		return AppConfig.getAppConfig(this).get(key);
	}
	
	/**
	 * 获取内存中保存图片的路径
	 * @return
	 */
	public String getSaveImagePath() {
		return saveImagePath;
	}

	/**
	 * 设置内存中保存图片的路径
	 * @return
	 */
	public void setSaveImagePath(String saveImagePath) {
		this.saveImagePath = saveImagePath;
	}


}
