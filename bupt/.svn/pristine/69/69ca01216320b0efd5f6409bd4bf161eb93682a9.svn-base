package com.example.bupt.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.db.DBUser;
import com.example.bupt.model.User;
import com.example.bupt.utils.AppUtil;
import com.example.bupt.utils.PreferenceUtil;
/*
 * 欢迎界面
 * 服务器注册用户
 * 跳转登录到主页
 */
@SuppressLint("SimpleDateFormat")
public class RegisterFinal extends BaseActivity {
	private Dialog loading;
	//用户数据段
	private int uid;
	private Integer type;
	private String username_phone;
	private String password;
	private String password_md5;
	private String usrname;
	private int sex = 2;
	private String ubirthday = "1970-01-01 18:18:18";
	private String ulocation = "北京市 北京市 海淀区";
	private String locids = "110000,110100,110108";
	private static final int KILL_PAGE = 100;
	//数据库
	private DBUser usrdao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_final);

		Intent get_data = this.getIntent();
		Bundle bundle = get_data.getExtras(); 
		type = bundle.getInt("type");
		username_phone = bundle.getString("phoneemail");
		password = bundle.getString("password");
		password_md5 = AppUtil.md5(password);
		usrname = bundle.getString("uname");

		usrdao = new DBUser(this);

	}

	public void final_to_start(View v) throws ParseException{

//		RequestParams params = new RequestParams();
//		//登录信息
//		params.put("type", type.toString());
//		if( 1 == type){
//			params.put("phone", username_phone);
//		}else if( 2 == type ){
//			params.put("email", username_phone);
//		}
//		params.put("password", password_md5);
//		params.put("uname", usrname);
//		params.put("sex", sex+"");
//		params.put("birthday",get_timestamp_s(ubirthday));
//		params.put("location", ulocation);
//		params.put("locids", locids);
//		System.out.println(type.toString()+"  "+username_phone+"  "+password_md5+"  "+usrname+"  "
//				+sex+"  "+get_timestamp_s(ubirthday)+"  "+ulocation+"  "+locids);
//
//		// 发送post请求
//		HttpUtil.post(URLs.FINAL_REGISTER, params, new JsonHttpResponseHandler() {
//
//			@Override
//			public void onStart() {
//				super.onStart();
//				loading=DialogUtil.getLoadingDialog(RegisterFinal.this,
//						"保存用户中...");
//				loading.show();
//			}
//
//			@Override
//			public void onFinish() {
//				super.onFinish();
//				loading.dismiss();
//			}
//
//			@Override
//			public void onSuccess(JSONObject result) {
//				int status = 0;
//				String info = null;
//				try {
//					status = result.getInt("status");
//					info = result.getString("info");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				System.out.println(info);
//				System.out.println(status);
//				if(status == 0){
//					if(BaseApp.getUid() > 0){//这里的判断要改成baseapp里面的是否登录
//						ToastUtil.showMsg(RegisterFinal.this,
//								"您已经登录，请退出后在注册");
//						toMain();
//					}else{
//						ToastUtil.showMsg(RegisterFinal.this,
//								"未知原因，无法登录。请稍后再试");
//						Intent fanhui_intent = new Intent(RegisterFinal.this,RegisterSetting.class);
//						setResult(KILL_PAGE, fanhui_intent);
//						finish();
//					}
//				}
//				if( status == 1){
//					try {
//						JSONObject dataObject = result.getJSONObject("data");
//						uid = dataObject.getInt("uid");
//						Log.i("Register", "uid_get =" + uid);
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//
//					//跳转之前保存全局变量uid作为用户的唯一标识
//					BaseApp.setUid(uid);
//
//					try {
//						savetoSQLite();
//						saveLoginInfo();
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					toMain();
//				}
//
//
//			}
//
//			@Override
//			public void onFailure(Throwable e, JSONObject data) {
//				if (!baseApp.isNetworkConnected()) {
//					ToastUtil
//					.showMsg(RegisterFinal.this, R.string.network_none);
//				} else {
//					ToastUtil.showMsg(RegisterFinal.this,
//							R.string.network_error);
//				}
//				e.printStackTrace();
//			}
//
//		});

	}
	/*
	 * 跳转至首页
	 */
	private void toMain(){
		//杀掉之前的页面
		Intent fanhui_intent = new Intent(RegisterFinal.this,RegisterSetting.class);
		setResult(KILL_PAGE, fanhui_intent);
		// 跳转至首页
		Intent intent = new Intent();
		intent.setClass(RegisterFinal.this, Main.class);
		startActivity(intent);
		RegisterFinal.this.finish();
	}

	/*
	 * 保存用户信息到本地数据库
	 */
	private void savetoSQLite() throws ParseException{
		User usr = new User();
		usr.setUid(uid);
		usr.setAccount(username_phone);
		usr.setPassword(password_md5);
		usr.setUname(usrname);
		usr.setUsex(sex+"");
		usr.setUbirthday(ubirthday);
		usr.setUstar("狮子座");
		usr.setUlocation(ulocation);
		usr.setUdesc("这家伙有点懒。。。");
		usrdao.insertUsr(usr);
	}
	/**
	 * 保存用户登陆信息
	 */
	private void saveLoginInfo() {
		// 首先清空原有SharedPreferences
		PreferenceUtil.clearAll(RegisterFinal.this);
		// 保存用户名
		PreferenceUtil.setStringPreferences(RegisterFinal.this, "username",
				username_phone);
		// 将用户密码加密保存
		PreferenceUtil.setStringPreferences(RegisterFinal.this,
				"password", password_md5);
	}

	/*
	 * 获得timestamp
	 * String型
	 */
	private static String get_timestamp_s(String birthday) throws ParseException{
		SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date date = format.parse(birthday);
		long timestamp = date.getTime();
		return Long.toString(timestamp);
	}
	/*
	 * 获得timestamp
	 * long型
	 */
	private static long get_timestamp_l(String birthday) throws ParseException{
		SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date date = format.parse(birthday);
		return date.getTime();
	}

	public void back_to_main(View v){
		finish();
	}

}
