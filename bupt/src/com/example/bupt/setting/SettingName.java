package com.example.bupt.setting;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;

/*
 * 用户名设置界面
 */
public class SettingName extends BaseActivity {
	private EditText name_space;
	private String usrname;
	private Dialog loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_name);
		name_space = (EditText) findViewById(R.id.my_setting_et_name);
	}

	public void back_to_main(View v) {
		finish();
	}

	public void save_name(View v) {
		update_name();
	}

	private void update_name(){
		usrname = name_space.getText().toString().trim();

//		RequestParams params = new RequestParams();
//		//填写用户名 保存
//		String uid = String.valueOf(BaseApp.getUid());
//		params.put("uid", uid);
//		params.put("uname", usrname);
//
//		// 发送post请求
//		HttpUtil.post(URLs.SET_NAME, params, new JsonHttpResponseHandler() {
//
//			@Override
//			public void onStart() {
//				super.onStart();
//				loading=DialogUtil.getLoadingDialog(SettingName.this,
//						"检查用户昵称...");
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
//				int status=0;
//				String info = null;
//				try {
//					info = result.getString("info");
//					status=result.getInt("status");
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				Log.i("name_info",info);
//				if( 1 == status ){
//					Log.i("修改成功——name","修改成功——name");
//					Intent fanhui_intent = new Intent(SettingName.this,SettingMain.class);
//					String name = name_space.getText().toString();
//					fanhui_intent.putExtra("name", name);
//					SettingName.this.setResult(3, fanhui_intent);
//					SettingName.this.finish();
//					
//				}
//				
//				if( 0 == status){
//					ToastUtil.showMsg(SettingName.this,info);
//				}
//				else if( 0 == status){
//					ToastUtil.showMsg(SettingName.this,"估计是哪出错了，现在还不能修改名字");
//
//				}
//
//			}
//
//			@Override
//			public void onFailure(Throwable e, JSONObject data) {
//				if (!baseApp.isNetworkConnected()) {
//					ToastUtil
//					.showMsg(SettingName.this, R.string.network_none);
//				} else {
//					ToastUtil.showMsg(SettingName.this,
//							R.string.network_error);
//				}
//				e.printStackTrace();
//			}
//
//		});

	}
	
	public void setting_clear_name(View v){
		name_space.setText("");
	}

}
