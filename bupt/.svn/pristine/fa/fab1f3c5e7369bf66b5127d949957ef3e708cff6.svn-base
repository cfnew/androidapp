package com.example.bupt.issue;

import com.example.bupt.R;
import com.example.bupt.R.layout;
import com.example.bupt.R.menu;
import com.example.bupt.issue.calendar.MyCalendar;
import com.example.bupt.utils.ToastUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class PaopaoIssueTab extends Activity {
	private static final int PAOPAO_TAB_INIT = 7;
	private int TabNumber = 0;

	//tab按钮
	private TextView tab[] = new TextView[12];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.paopao_issue_tab);
		Intent get_data = this.getIntent();
		Bundle bundle = get_data.getExtras(); 
		TabNumber = bundle.getInt("TabNameFlag");
		Message msg = new Message();
		msg.what = PAOPAO_TAB_INIT;
		mHandler.sendMessage(msg);
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			if(msg.what == PAOPAO_TAB_INIT){
				InitPaopaoTab();
			}
		}
	};

	private void InitPaopaoTab(){
		tab[0]=(TextView)findViewById(R.id.paopao_issue_tab_0);
		tab[1]=(TextView)findViewById(R.id.paopao_issue_tab_1);
		tab[2]=(TextView)findViewById(R.id.paopao_issue_tab_2);
		tab[3]=(TextView)findViewById(R.id.paopao_issue_tab_3);
		tab[4]=(TextView)findViewById(R.id.paopao_issue_tab_4);
		tab[5]=(TextView)findViewById(R.id.paopao_issue_tab_5);
		tab[6]=(TextView)findViewById(R.id.paopao_issue_tab_6);
		tab[7]=(TextView)findViewById(R.id.paopao_issue_tab_7);
	}
	public void paopao_tab_click(int index){
		Intent intent = new Intent(PaopaoIssueTab.this,PaopaoIssue.class);
		intent.putExtra("tab_back", index+1);
		setResult(9, intent);
		finish();
		overridePendingTransition(R.anim.fade_in,
				R.anim.fade_out);
	}
	public void paopao_issue_tab_click0(View v){
		paopao_tab_click(0);
	}
	public void paopao_issue_tab_click1(View v){
		paopao_tab_click(1);
	}
	public void paopao_issue_tab_click2(View v){
		paopao_tab_click(2);
	}
	public void paopao_issue_tab_click3(View v){
		paopao_tab_click(3);
	}
	public void paopao_issue_tab_click4(View v){
		paopao_tab_click(4);
	}
	public void paopao_issue_tab_click5(View v){
		paopao_tab_click(5);
	}
	public void paopao_issue_tab_click6(View v){
		paopao_tab_click(6);
	}
	public void paopao_issue_tab_click7(View v){
		paopao_tab_click(7);
	}
	public void paopao_issue_tab_click8(View v){
		paopao_tab_click(8);
	}
	public void paopao_issue_tab_click9(View v){
		paopao_tab_click(9);
	}
	public void paopao_issue_tab_back(View v){
		finish();
		overridePendingTransition(R.anim.fade_in,
				R.anim.fade_out);
	}

}


