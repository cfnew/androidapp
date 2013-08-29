package com.example.bupt.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.bupt.R;
/*
 * 个性签名设置界面
 */
public class SettingSignature extends Activity {
	private EditText get_signature;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_signature);
		get_signature = (EditText)findViewById(R.id.my_setting_et_signature);
	}

	public void back_to_main(View v){
		finish();
	}
	
	public void save_signature(View v){
		String getString = get_signature.getText().toString();
		Intent fanhui_intent = new Intent(SettingSignature.this,SettingMain.class);
		fanhui_intent.putExtra("signature", getString);
		SettingSignature.this.setResult(5, fanhui_intent);
		SettingSignature.this.finish();
		
	}

}
