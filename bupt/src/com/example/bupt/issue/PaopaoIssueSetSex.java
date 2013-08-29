package com.example.bupt.issue;

import com.example.bupt.R;
import com.example.bupt.R.layout;
import com.example.bupt.R.menu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class PaopaoIssueSetSex extends Activity {
	Button bt1 = null;
	Button bt2 = null;
	private int flagSex = 0;
	private int PAOPAO_SEX_INIT = 7;
	private String[] StringSex = new String[]{"随意","男","女"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.paopao_issue_sex);
		Intent get_data = this.getIntent();
		Bundle bundle = get_data.getExtras(); 
		flagSex = bundle.getInt("Sex");
		Message msg = new Message();
		msg.what = PAOPAO_SEX_INIT;
		mHandler.sendMessage(msg);
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			if(msg.what == PAOPAO_SEX_INIT){
				InitPaopaoSex();
			}
		}
	};
	
	private void InitPaopaoSex(){
		bt1 = (Button) findViewById(R.id.paopao_issue_set_sex1);
		bt2 = (Button) findViewById(R.id.paopao_issue_set_sex2);
		if(flagSex == 0){
			bt1.setText(StringSex[1]);
			bt2.setText(StringSex[2]);
		}
		if(flagSex == 1){
			bt1.setText(StringSex[0]);
			bt2.setText(StringSex[2]);
		}
		if(flagSex == 2){
			bt1.setText(StringSex[0]);
			bt2.setText(StringSex[1]);
		}
		
	}
	
	public void paopao_issue_sex_bt1(View v){
		Intent intent = new Intent(PaopaoIssueSetSex.this,PaopaoIssue.class);
		if(flagSex == 0) intent.putExtra("Sex", 1);
		if(flagSex == 1) intent.putExtra("Sex", 0);
		if(flagSex == 2) intent.putExtra("Sex", 0);
		setResult(10, intent);
		finish();
		overridePendingTransition(R.anim.fade_in,
				R.anim.fade_out);
	}
	
	public void paopao_issue_sex_bt2(View v){
		Intent intent = new Intent(PaopaoIssueSetSex.this,PaopaoIssue.class);
		if(flagSex == 0) intent.putExtra("Sex", 2);
		if(flagSex == 1) intent.putExtra("Sex", 2);
		if(flagSex == 2) intent.putExtra("Sex", 1);
		setResult(10, intent);
		finish();
		
	}
	public void paopao_issue_sex_back(View v){
		finish();
	}

}
