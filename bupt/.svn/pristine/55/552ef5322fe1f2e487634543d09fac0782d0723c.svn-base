package com.example.bupt.setting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;

/*
 * 性别设置界面
 */
public class SettingSex extends BaseActivity {
	private ImageView flag_male;
	private ImageView flag_female;
	private Dialog loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent get_sex = this.getIntent();
		Bundle bundle = get_sex.getExtras(); 
		String flag = bundle.getString("sex");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_sex);
		flag_male = (ImageView) findViewById(R.id.my_setting_iv_male);
		flag_female = (ImageView) findViewById(R.id.my_setting_iv_female);
		if(flag.equals("男")){
			flag_male.setVisibility(0);
			flag_female.setVisibility(4);
			}else{
				flag_male.setVisibility(4);
				flag_female.setVisibility(0);
			}
	}

	public void back_to_main(View v) {
		finish();
	}
	
	public void save_male(View v){
		update_sex(1);
	}
	
	public void save_female(View v){
		update_sex(2);
	}
	
	private void update_sex(int usex){
		final int flag = usex;
//		RequestParams params = new RequestParams();
//		//填写用户名 保存
//		String uid = String.valueOf(BaseApp.getUid());
//		params.put("uid", uid);
//		String sexToUpdate = null;
//		if(1 == usex) sexToUpdate = new String("1");
//		else sexToUpdate = new String("2");
//		params.put("sex",sexToUpdate);
//
//		// 发送post请求
//		HttpUtil.post(URLs.SET_SEX, params, new JsonHttpResponseHandler() {
//
//			@Override
//			public void onStart() {
//				super.onStart();
//				loading=DialogUtil.getLoadingDialog(SettingSex.this,
//						"保存信息中...");
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
//					Intent re_usex = new Intent(SettingSex.this,SettingMain.class);
//					int sex = 0;
//					if(1 == flag) sex = 1;
//					else sex = 2;
//					re_usex.putExtra("sex", sex);
//					SettingSex.this.setResult(4, re_usex);
//					SettingSex.this.finish();
//					
//				}
//				else if( 0 == status){
//					ToastUtil.showMsg(SettingSex.this,"估计是哪出错了，现在还不能修改性别");
//
//				}
//
//			}
//
//			@Override
//			public void onFailure(Throwable e, JSONObject data) {
//				if (!baseApp.isNetworkConnected()) {
//					ToastUtil
//					.showMsg(SettingSex.this, R.string.network_none);
//				} else {
//					ToastUtil.showMsg(SettingSex.this,
//							R.string.network_error);
//				}
//				e.printStackTrace();
//			}
//
//		});

		
	}

}
