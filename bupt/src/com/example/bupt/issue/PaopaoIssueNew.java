package com.example.bupt.issue;

import com.example.bupt.R;
import com.example.bupt.R.layout;
import com.example.bupt.R.menu;
import com.example.bupt.utils.LastClickUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class PaopaoIssueNew extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paopao_issue_new);
	}
	
	public void paopao_issue_privategame(View v){
		if(LastClickUtil.onClick() == false) return;
		Intent intent = new Intent(PaopaoIssueNew.this,PaopaoIssueAddFriend.class);
		intent.putExtra("status", 0);
		this.finish();
		startActivity(intent);
	}
	
	public void paopao_issue_publiucgame(View v){
		if(LastClickUtil.onClick() == false) return;
		Intent intent = new Intent(PaopaoIssueNew.this,PaopaoIssuePublicTab.class);
		this.finish();
		startActivity(intent);
	}
	
	public void back_to_main(View v){
		if(LastClickUtil.onClick() == false) return;
		this.finish();
	}

}
