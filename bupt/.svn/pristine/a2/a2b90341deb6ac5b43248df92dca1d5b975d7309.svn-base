package com.example.bupt.ui;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import cn.sharesdk.framework.ShareSDK;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.base.BaseApp;
import com.example.bupt.http.Api;
import com.example.bupt.model.ResultLogin;
import com.example.bupt.utils.PreferenceUtil;
import com.example.bupt.utils.ToastUtil;

public class UiSlash extends BaseActivity {

	private static final int SHOW_TIME_MIN = 2000;
	//跳转到欢迎界面
	private static final int INIT_STATUS = 0x00;
	private static final int GO_GUIDE = 0x01;
	private static final int FAILURE = 0x02;
	private static final int SUCCESS = 0x03;

	public static final String IS_FIRST_USE = "IS_FIRST_USE";
	private boolean isFirstUse = false;
	private boolean isNetworkOk = true;
	private boolean isLoginOk = false;
	private int result_status = INIT_STATUS;

	private String username = null;
	private String password = null;
	private int userid = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ShareSDK.initSDK(this);
		setContentView(R.layout.activity_slash);

		ImageView slash = (ImageView) findViewById(R.id.slash);
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(800);
		slash.setAnimation(animation);
		new AsyncTask<Void, Void, Integer>() {
			@Override
			protected void onPreExecute() {
				init();
			}
			@Override
			protected Integer doInBackground(Void... params) {
				result_status = INIT_STATUS; // 定义返回值
				long startTime = System.currentTimeMillis();
				if(isFirstUse){
					result_status = GO_GUIDE;
					return result_status;
				}
				if (username == null || password == null) {
					result_status = FAILURE;
				} else {
					if(isNetworkOk){
						login();
					}
				}
				long loadTime = System.currentTimeMillis() - startTime;
				if (loadTime < SHOW_TIME_MIN) {
					try {
						Thread.sleep(SHOW_TIME_MIN - loadTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
						return FAILURE;
					}
				}
				if(!isLoginOk){
					result_status = FAILURE;
				}else{
					result_status = SUCCESS;
				}
				return result_status;
			}
			@Override
			protected void onPostExecute(Integer result) {
				Intent intent = null;
				if(result == GO_GUIDE){				
					PreferenceUtil.setBooleanPreference(UiSlash.this,IS_FIRST_USE,false);
					intent = new Intent(UiSlash.this,UiGuide.class);
				} else if(result == SUCCESS){
					// 跳转至首页
					if (userid != 0)
						BaseApp.setUid(userid);
					intent = new Intent();
					intent.setClass(UiSlash.this, Main.class);
				}else{
					intent = new Intent(UiSlash.this, Login.class);
				}
				startActivity(intent);
				finish();
				return;
			}
		}.execute(new Void[] {});
	}

	private void init() {
		//判断是否是首次使用程序
		//isFirstUse = PreferenceUtil.getBooleanPreference(UiSlash.this, IS_FIRST_USE);
		if (!(isNetworkOk = baseApp.isNetworkConnected())) {
			ToastUtil.showMsg(UiSlash.this, R.string.network_none);
		}
		if(!isFirstUse){
			username = "282229234@qq.com";//PreferenceUtil.getStringPreference(UiSlash.this, "username");
			password = "000000";//PreferenceUtil.getStringPreference(UiSlash.this, "password");
			int uid = PreferenceUtil.getIntPreference(UiSlash.this, "userid");
			if(uid != 0){
				userid = uid;
			}
		}
	}

	/**
	 * 登陆方法
	 */
	private void login() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("account", username);
		params.put("password", password);
		ResultLogin res = Api.login(UiSlash.this, params);
		if(res != null && res.OK()){
			isLoginOk = true;
			result_status = SUCCESS;
			userid = res.getUid();
		}else{
			isLoginOk = false;
		}
	}

}