package com.example.bupt.issue;

import java.util.Calendar;

import com.example.bupt.R;
import com.example.bupt.R.layout;
import com.example.bupt.R.menu;
import com.example.bupt.setting.ScreenInfo;
import com.example.bupt.setting.SettingDate;
import com.example.bupt.setting.SettingNumber;
import com.example.bupt.setting.SettingTime;
import com.example.bupt.utils.LastClickUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PaopaoIssuePublic extends Activity {


	private static final int GETISSUEDETAILS = 2;


	//用户活动描述
	private String usrDetails = null;

	//活动频道
	private int tabNumber = 0;
	private TextView tabName = null;

	//点击显示日期和时间的两个下拉菜单
	private boolean timeVisible = false;
	RelativeLayout rl_calendar;
	RelativeLayout rl_time;
	private SettingDate wheelDate;
	private SettingTime wheelTime;
	private TextView tv_calendar;
	private TextView tv_time;

	//点击对伙伴的期望
	private boolean expectVisible = false;
	RelativeLayout rl_renshu;
	RelativeLayout rl_xingbie;
	RelativeLayout rl_nianling;
	
	//人数
	private TextView tv_renshu = null;
	private SettingNumber wheelNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paopao_issue_public);
		Intent intent = this.getIntent();
		tabNumber = intent.getIntExtra("tabnumber", 0);
		init();
	}

	private void init(){
		tabName = (TextView) findViewById(R.id.paopao_issue_public_tabname);
		tabName.setText(PaopaoTabDetails.getTabName(tabNumber));
		
		
		rl_calendar = (RelativeLayout) findViewById(R.id.paopao_issue_public_rl_cal);
		rl_time = (RelativeLayout) findViewById(R.id.paopao_issue_public_rl_time);
		rl_calendar.setVisibility(View.GONE);
		rl_time.setVisibility(View.GONE);
		timeVisible = false;
		tv_calendar = (TextView) findViewById(R.id.paopao_issue_public_tv_date);
		tv_time = (TextView) findViewById(R.id.paopao_issue_public_tv_time);


		rl_renshu = (RelativeLayout) findViewById(R.id.paopao_issue_public_rl_renshu);
		rl_xingbie = (RelativeLayout) findViewById(R.id.paopao_issue_public_rl_xingbie);
		rl_nianling = (RelativeLayout) findViewById(R.id.paopao_issue_public_rl_nianling);
		rl_renshu.setVisibility(View.GONE);
		rl_xingbie.setVisibility(View.GONE);
		rl_nianling.setVisibility(View.GONE);
		expectVisible = false;
		
		tv_renshu = (TextView) findViewById(R.id.paopao_issue_public_tv_renshu);

	}


	public void show_paopao_expect(View v){
		if(LastClickUtil.onClick() == false) return;
		if(expectVisible == false){
			expectVisible = true;
			Log.i("PaopaoIssuePrivate","点击show_expect  false");
			rl_renshu.setVisibility(View.VISIBLE);
			rl_xingbie.setVisibility(View.VISIBLE);
			rl_nianling.setVisibility(View.VISIBLE);
			return;
		}

		if(expectVisible == true){
			expectVisible = false;
			Log.i("PaopaoIssuePrivate","点击show_expect  true");
			rl_renshu.setVisibility(View.GONE);
			rl_xingbie.setVisibility(View.GONE);
			rl_nianling.setVisibility(View.GONE);
			return;
		}
	}
	public void show_time(View v){
		if(LastClickUtil.onClick() == false) return;
		Log.i("PaopaoIssuePrivate","点击show_time");

		if(timeVisible == false){
			timeVisible = true;
			Log.i("PaopaoIssuePrivate","点击show_time  false");
			rl_calendar.setVisibility(View.VISIBLE);
			rl_time.setVisibility(View.VISIBLE);
			return;
		}

		if(timeVisible == true){
			timeVisible = false;
			Log.i("PaopaoIssuePrivate","点击show_time  true");
			rl_calendar.setVisibility(View.GONE);
			rl_time.setVisibility(View.GONE);
			return;
		}
	}

	public void paopao_issue_public_click_date(View v){
		if(LastClickUtil.onClick() == false) return;
		LayoutInflater inflater=LayoutInflater.from(PaopaoIssuePublic.this);
		final View timepickerview=inflater.inflate(R.layout.setting_birthday, null);
		ScreenInfo screenInfo = new ScreenInfo(PaopaoIssuePublic.this);
		wheelDate = new SettingDate(timepickerview);
		wheelDate.screenheight = screenInfo.getHeight();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		wheelDate.initDateTimePicker(year,month,day);
		new AlertDialog.Builder(PaopaoIssuePublic.this)
		.setTitle("选择日期")
		.setView(timepickerview)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i("date",wheelDate.getTime());
				tv_calendar.setText(wheelDate.getTime());
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}

	public void paopao_issue_public_click_time(View v){
		if(LastClickUtil.onClick() == false) return;
		LayoutInflater inflater=LayoutInflater.from(PaopaoIssuePublic.this);
		final View timepickerview=inflater.inflate(R.layout.paopao_issue_time, null);
		ScreenInfo screenInfo = new ScreenInfo(PaopaoIssuePublic.this);
		wheelTime = new SettingTime(timepickerview);
		wheelTime.screenheight = screenInfo.getHeight();
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTime.initDateTimePicker(hour,minute);
		new AlertDialog.Builder(PaopaoIssuePublic.this)
		.setTitle("选择时间")
		.setView(timepickerview)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i("time",wheelTime.getTime());
				tv_time.setText(wheelTime.getTime());
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}

	public void call_paopao_details(View v){
		if(LastClickUtil.onClick() == false) return;
		Intent intent = new Intent(PaopaoIssuePublic.this,PaopaoIssueDetails.class);
		intent.putExtra("details", usrDetails);
		startActivityForResult(intent,GETISSUEDETAILS);
	}
	
	public void call_renshu(View v){
		if(LastClickUtil.onClick() == false) return;
		LayoutInflater inflater=LayoutInflater.from(PaopaoIssuePublic.this);
		final View timepickerview=inflater.inflate(R.layout.setting_number, null);
		ScreenInfo screenInfo = new ScreenInfo(PaopaoIssuePublic.this);
		wheelNumber = new SettingNumber(timepickerview);
		wheelNumber.screenheight = screenInfo.getHeight();
		int number = 0;
		wheelNumber.initDateTimePicker(number);
		new AlertDialog.Builder(PaopaoIssuePublic.this)
		.setTitle("选择人数")
		.setView(timepickerview)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				tv_renshu.setText(String.valueOf(wheelNumber.getNumber()));
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}

	public void call_tab_list(View v){
		if(LastClickUtil.onClick() == false) return;
		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("选择一个活动类别").
				setSingleChoiceItems(PaopaoTabDetails.getAllTab(), 0, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						tabNumber = which;
						Log.i("tabNumber",String.valueOf(tabNumber));
					}
				}).
				setPositiveButton("确认", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						tabName.setText(PaopaoTabDetails.getTabName(tabNumber));
					}
				}).
				setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).
				create();
		alertDialog.show();
	}

	public void back_to_main(View v){
		if(LastClickUtil.onClick() == false) return;
		finish();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case GETISSUEDETAILS:
				if(data == null) return;
				usrDetails = data.getStringExtra("details");
				break;

			}
		}
	}

}
