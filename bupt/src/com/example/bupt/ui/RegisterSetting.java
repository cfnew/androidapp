package com.example.bupt.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
/*
 * 用户资料设置界面
 * 用户将要是用的名字
 */
public class RegisterSetting extends BaseActivity {
	private Dialog loading;
	private int type;
	private String username_phone;
	private String password;
	private EditText getname;
	private String usrname;
	private static final int KILL_PAGE = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_setting);
		
		Intent get_data = this.getIntent();
		Bundle bundle = get_data.getExtras(); 
		type = bundle.getInt("type");
		username_phone = bundle.getString("phoneemail");
		password = bundle.getString("password");
		getname = (EditText)findViewById(R.id.register_et_name);
		
		

	}
	
	
	public void back_to_main(View v){
		finish();
	}
	
	
	public void next_step(View v){
		
		usrname = getname.getText().toString().trim();
		
//		RequestParams params = new RequestParams();
//		//填email||phone检查请求
//			params.put("uname", usrname);
//
//		// 发送post请求
//		HttpUtil.post(URLs.CHECK_UNAME, params, new JsonHttpResponseHandler() {
//
//			@Override
//			public void onStart() {
//				super.onStart();
//				loading=DialogUtil.getLoadingDialog(RegisterSetting.this,
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
//				Bundle bundle = new Bundle();
//				bundle.putInt("type", type);
//				bundle.putString("phoneemail", username_phone);
//				bundle.putString("password", password);
//				bundle.putString("uname", usrname);
//				Intent intent = new Intent(RegisterSetting.this,RegisterFinal.class);
//				intent.putExtras(bundle);
//				startActivityForResult(intent,KILL_PAGE);
//				}
//				else if( 0 == status){
//					ToastUtil.showMsg(RegisterSetting.this,"亲,名字被别人用了,改一个呗");
//					
//				}
//				
//			}
//
//			@Override
//			public void onFailure(Throwable e, JSONObject data) {
//				if (!baseApp.isNetworkConnected()) {
//					ToastUtil
//					.showMsg(RegisterSetting.this, R.string.network_none);
//				} else {
//					ToastUtil.showMsg(RegisterSetting.this,
//							R.string.network_error);
//				}
//				e.printStackTrace();
//			}
//
//		});
		
	}
	
	// 回调函数
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if(resultCode == KILL_PAGE){
				//杀掉之前的页面
				Intent fanhui_intent = new Intent(RegisterSetting.this,Register.class);
				setResult(KILL_PAGE, fanhui_intent);
				finish();
			}

		}



}
