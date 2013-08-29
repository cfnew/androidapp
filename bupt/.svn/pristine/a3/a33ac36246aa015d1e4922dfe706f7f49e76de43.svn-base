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
import android.widget.EditText;

public class PaopaoIssueDetails extends Activity {
	private EditText paopaoDetails = null;
	private String details = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paopao_issue_details);
		paopaoDetails = (EditText) findViewById(R.id.paopao_issue_details);
		Intent intent = this.getIntent();
		details = intent.getStringExtra("details");
		if(details != null) {
			paopaoDetails.setText(details);
			paopaoDetails.setSelection(paopaoDetails.length());
		}
	}
	
	public void save_to_main(View v){
		if(LastClickUtil.onClick() == false) return;
		details = paopaoDetails.getText().toString();
		Intent intent = new Intent(PaopaoIssueDetails.this,PaopaoIssuePrivate.class);
		intent.putExtra("details", details);
		this.setResult(1, intent);
		finish();
	}
	
	public void back_to_main(View v){
		finish();
	}

}
