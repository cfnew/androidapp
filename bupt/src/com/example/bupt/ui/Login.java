package com.example.bupt.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.base.BaseApp;
import com.example.bupt.http.Api;
import com.example.bupt.model.ResultLogin;
import com.example.bupt.utils.DialogUtil;
import com.example.bupt.utils.FileUtils;
import com.example.bupt.utils.JsonUtils;
import com.example.bupt.utils.PreferenceUtil;
import com.example.bupt.utils.StringUtils;
import com.example.bupt.utils.ToastUtil;

public class Login extends BaseActivity{

	private EditText usernameText;
	private EditText passwordText;
	private Button loginButton;
	private Button registerButton;
	private Button WeiBo;
	private Dialog loading;
	private int uid = 0;

	private static final String TAG = "LoginActivity";

	//weibo
	Platform platform = null; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		platform = ShareSDK.getPlatform (this, SinaWeibo.NAME);
		platform.setPlatformActionListener(new  PlatformActionListener() {
			public void onError(Platform  platform ,  int  action,  Throwable  t) {
				// 操作失败的处理代码
				t.printStackTrace();
				if(action == 1){
					Message msg = new Message();
					msg.what = 2;
					msg.arg1 = action;
					msg.obj = platform;
					mHandler.sendMessage(msg);
				}
			}

			public void onComplete(Platform  platform ,  int  action,  HashMap<String, Object>  res) {
				// 操作成功的处理代码
				if(action == 1){
					Message msg = new Message();
					msg.what = 1;
					msg.arg1 = action;
					msg.obj = platform;
					mHandler.sendMessage(msg);
				}

				if(action == 8){
					Message msg = new Message();
					msg.what = 4;
					msg.arg1 = action;
					JSONObject json = null;
					try {
						json = JsonUtils.getJSONObject(res);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					msg.obj = json;
					mHandler.sendMessage(msg);
				}
			}

			public void onCancel(Platform  platform , int  action) {
				// 操作取消的处理代码
				if(action == 1){
					Message msg = new Message();
					msg.what = 3;
					msg.arg1 = action;
					msg.obj = platform;
					mHandler.sendMessage(msg);
				}
			}
		});

		// 组件
		usernameText = (EditText) findViewById(R.id.edit_username);
		passwordText = (EditText) findViewById(R.id.edit_password);
		loginButton = (Button) findViewById(R.id.button_login);
		//registerButton = (Button) findViewById(R.id.button_register);
		//WeiBo = (Button) findViewById(R.id.button_weibo);
		// 登录按钮的触摸事件
		loginButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.color.darkblue);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(R.color.lightblue);
				}
				return false;
			}
		});
		// 登录按钮的点击事件
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
//		// 微博
//		WeiBo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				platform.authorize();
//			}
//
//		});
//		// 注册按钮的触摸事件
//				registerButton.setOnTouchListener(new OnTouchListener() {
//
//					@Override
//					public boolean onTouch(View v, MotionEvent event) {
//						if (event.getAction() == MotionEvent.ACTION_DOWN) {
//							v.setBackgroundResource(R.color.darkorange);
//						} else if (event.getAction() == MotionEvent.ACTION_UP) {
//							v.setBackgroundResource(R.color.lightorange);
//						}
//						return false;
//					}
//				});
//		// 注册按钮的点击事件
//		registerButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(Login.this, Register.class);
//				startActivity(intent);
//				finish();
//				// 淡入淡出
//				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//			}
//		});

	}

	/**
	 * 登陆方法
	 */
	private void login() {
		// 获取用户输入的账号和密码
		final String username = usernameText.getText().toString().trim();
		final String password = passwordText.getText().toString().trim();
		if (StringUtils.isEmpty(username)) {
			ToastUtil.showMsg(Login.this, R.string.need_username);
			return;
		}
		if (StringUtils.isEmpty(password)) {
			ToastUtil.showMsg(Login.this, R.string.need_password);
			return;
		}
		// 创建请求参数
		final Map<String,String> param = new HashMap<String,String>();
		param.put("account", username);
		param.put("password", password);
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				loading.dismiss();
				if(msg.obj != null){
					ResultLogin result = (ResultLogin)msg.obj;
					if(result.OK()){ //登陆成功
						uid = result.getUid();
						BaseApp.setUid(uid);
						// 为用户创建临时目录
						FileUtils.createDirectory(uid+"");
						saveLoginInfo();
						// 跳转至首页
						Intent intent = new Intent();
						intent.setClass(Login.this, Main.class);
						startActivity(intent);
						finish();
					}else{
						ToastUtil.showMsg(Login.this, result.getInfo());
					}
				}
			}
		};
		loading = DialogUtil.getLoadingDialog(Login.this,"登录中，请稍后...");
		loading.show();
		new Thread(){
			public void run() {
				Message msg = new Message();
				ResultLogin res = Api.login(Login.this,param);
				msg.obj = res;
				handler.sendMessage(msg);
			}
		}.start();
	}


	/**
	 * 保存用户登陆信息
	 */
	private void saveLoginInfo() {
		PreferenceUtil.clearAll(Login.this);
		PreferenceUtil.setStringPreferences(Login.this, "username", usernameText.getText().toString());
		PreferenceUtil.setStringPreferences(Login.this,"password", passwordText.getText().toString());
		PreferenceUtil.setIntPreference(Login.this, "userid", uid);
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: {
				platform.showUser(null);
			}
			break;
			case 4: {
				JSONObject json = (JSONObject) msg.obj;
				getUsrInfo_sina(json);
			}
			break;
			}

		}
	};

	private void getUsrInfo_sina(JSONObject json) {
		String usr_name_sina = null;
		String usr_id_sina = null;
		try {
			usr_name_sina = json.getString("name");
			usr_id_sina = json.getString("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.i("usr_name","usrname : "+usr_name_sina);
		Log.i("usr_id","usrid : "+usr_id_sina);
	}

}
