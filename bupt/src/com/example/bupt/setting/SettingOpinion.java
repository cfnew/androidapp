package com.example.bupt.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.bupt.R;

public class SettingOpinion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.setting_opinion);
	}
	
	public void setting_opinion_cal_back(View v){
		finish();
	}
	
	public void setting_opinion_cancel(View v){
		finish();
	}
	
	public void setting_opinion_ok(View v){
		finish();
	}

}
