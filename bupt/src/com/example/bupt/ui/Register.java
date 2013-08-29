package com.example.bupt.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.utils.ToastUtil;

/*
 * 注册界面1
 * 用户名密码
 */
public class Register extends BaseActivity {
	private Dialog loading;
	private EditText usernameText;
	private EditText passwordText;
	private Button nextsteps;
	private String username;
	private String password;
	//private String uid;
	private String check;
	public int type = 0;
	public static final String TAG = "RegisterTabActivity";
	public static final String REGISTER_PHONE = "手机注册";
	public static final String REGISTER_EMAIL = "邮箱注册";
	private static final int KILL_PAGE = 100;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		Log.i(TAG, "start onCreate");
		usernameText = (EditText) findViewById(R.id.register_username);
		passwordText = (EditText) findViewById(R.id.register_password);
		nextsteps = (Button) findViewById(R.id.register_bt_nextsteps);

		nextsteps.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.color.darkorange);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(R.color.lightorange);
				}
				return false;
			}
		});
		nextsteps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				register();
			}
		});
	}

	private void register() {
		username = usernameText.getText().toString().trim();
		password = passwordText.getText().toString().trim();
		if (username.equals("")) {
			ToastUtil.showMsg(this, R.string.register_need_username);
			return;
		}
		if (password.equals("")) {
			ToastUtil.showMsg(this, R.string.register_need_password);
			return;
		}
		if ((type = checkUsername()) == 0) {
			ToastUtil.showMsg(this, R.string.wrong_username);
			return;
		}
		if (!checkPassword()) {
			ToastUtil.showMsg(this, R.string.wrong_password);
			return;
		}


//		RequestParams params = new RequestParams();
//		//填email||phone检查请求
//		if (type == 1) {
//			params.put("phone", username);
//			check = URLs.CHECK_PHONE;
//		} else if (type == 2) {
//			params.put("email", username);
//			check = URLs.CHECK_EMAIL;
//		}
//		// 发送post请求
//		HttpUtil.post(check, params, new JsonHttpResponseHandler() {
//
//			@Override
//			public void onStart() {
//				super.onStart();
//				loading=DialogUtil.getLoadingDialog(Register.this,
//						"检查用户id...");
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
//				try {
//					status = result.getInt("status");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				if( 1 == status ){
//					Bundle bundle = new Bundle();
//					bundle.putInt("type", type);
//					bundle.putString("phoneemail", username);
//					bundle.putString("password", password);
//					Intent intent = new Intent(Register.this,RegisterSetting.class);
//					intent.putExtras(bundle);
//					startActivityForResult(intent,KILL_PAGE);
//				}else {
//					ToastUtil.showMsg(Register.this,"亲,登录名被别人用了,改一个呗");
//				}
//				
//
//			}
//
//			@Override
//			public void onFailure(Throwable e, JSONObject data) {
//				if (!baseApp.isNetworkConnected()) {
//					ToastUtil.showMsg(Register.this, R.string.network_none);
//				} else {
//					ToastUtil.showMsg(Register.this,
//							R.string.network_error);
//				}
//				e.printStackTrace();
//			}
//
//		});
	}






	private int checkUsername() {
		// username = usernameText.getText().toString().trim();
		String email = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		String phone = "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\d{8}$";
		Pattern patternEmial = Pattern.compile(email);
		Pattern patternPhone = Pattern.compile(phone);
		Matcher matcherEmail = patternEmial.matcher(username);
		Matcher matcherPhone = patternPhone.matcher(username);
		boolean isMatchedEmail = matcherEmail.matches();
		boolean isMatchedPhone = matcherPhone.matches();
		Log.i(TAG, "match email : " + String.valueOf(isMatchedEmail)
				+ " ; match phone : " + String.valueOf(isMatchedPhone));
		if (isMatchedPhone) {
			return 1;
		} else if (isMatchedEmail) {
			return 2;
		} else {
			return 0;
		}
	}

	private boolean checkPassword() {
		// password = passwordText.getText().toString().trim();
		String psw = "^[0-9A-Za-z]{6,}$";
		Pattern patternPsw = Pattern.compile(psw);
		Matcher matcherPsw = patternPsw.matcher(password);
		boolean isMatchedPsw = matcherPsw.matches();
		Log.i(TAG, "match password : " + String.valueOf(isMatchedPsw));
		return isMatchedPsw;
	}

	public void back_to_main(View v){
		Intent intent = new Intent(Register.this,com.example.bupt.ui.Login.class);
		startActivity(intent);
		finish();
	}

	// 回调函数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == KILL_PAGE){
			finish();
		}

	}
	
}
