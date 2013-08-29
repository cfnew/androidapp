package com.example.bupt.issue;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.bupt.R;
import com.example.bupt.R.layout;
import com.example.bupt.R.menu;
import com.example.bupt.setting.ScreenInfo;
import com.example.bupt.setting.SettingDate;
import com.example.bupt.setting.SettingMain;
import com.example.bupt.setting.SettingTime;
import com.example.bupt.utils.DateUtil;
import com.example.bupt.utils.LastClickUtil;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PaopaoIssuePrivate extends Activity {
	//获得之前选取的好友uid
	private ArrayList<Integer> lvFriendData_get = new ArrayList<Integer>();
	private TextView friendList = null;
	private static final int UPDATEFRIEND = 1;
	private static final int GETISSUEDETAILS = 2;
	private static final int FOR_LOCATION = 3;

	//点击显示日期和时间的两个下拉菜单
	private boolean timeVisible = false;
	RelativeLayout rl_calendar;
	RelativeLayout rl_time;
	private SettingDate wheelDate;
	private SettingTime wheelTime;
	private TextView tv_calendar;
	private TextView tv_time;
	
	//用户活动描述
	private String usrDetails = null;
	
	//地点
	private TextView tv_location = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paopao_issue_private);
		Intent get_data = this.getIntent();
		lvFriendData_get = get_data.getIntegerArrayListExtra("friend_selected");
		init();
	}

	private void init(){
		rl_calendar = (RelativeLayout) findViewById(R.id.paopao_issue_private_rl_cal);
		rl_time = (RelativeLayout) findViewById(R.id.paopao_issue_private_rl_time);
		rl_calendar.setVisibility(View.GONE);
		rl_time.setVisibility(View.GONE);
		tv_calendar = (TextView) findViewById(R.id.paopao_issue_private_tv_date);
		tv_time = (TextView) findViewById(R.id.paopao_issue_private_tv_time);

		friendList = (TextView) findViewById(R.id.paopao_issue_private_tv_friend);
		friendList.setText("已邀请"+lvFriendData_get.size()+"位好友");
		
		tv_location = (TextView) findViewById(R.id.paopao_issue_private_tv_location);
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

	public void paopao_issue_private_click_date(View v){
		if(LastClickUtil.onClick() == false) return;
		LayoutInflater inflater=LayoutInflater.from(PaopaoIssuePrivate.this);
		final View timepickerview=inflater.inflate(R.layout.setting_birthday, null);
		ScreenInfo screenInfo = new ScreenInfo(PaopaoIssuePrivate.this);
		wheelDate = new SettingDate(timepickerview);
		wheelDate.screenheight = screenInfo.getHeight();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		wheelDate.initDateTimePicker(year,month,day);
		new AlertDialog.Builder(PaopaoIssuePrivate.this)
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

	public void paopao_issue_private_click_time(View v){
		if(LastClickUtil.onClick() == false) return;
		LayoutInflater inflater=LayoutInflater.from(PaopaoIssuePrivate.this);
		final View timepickerview=inflater.inflate(R.layout.paopao_issue_time, null);
		ScreenInfo screenInfo = new ScreenInfo(PaopaoIssuePrivate.this);
		wheelTime = new SettingTime(timepickerview);
		wheelTime.screenheight = screenInfo.getHeight();
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTime.initDateTimePicker(hour,minute);
		new AlertDialog.Builder(PaopaoIssuePrivate.this)
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
	
	private void getLocationFromMap(Intent data){
		tv_location.setText(data.getStringExtra("location"));
	}

	public void call_friend_list(View v){
		if(LastClickUtil.onClick() == false) return;
		Intent intent = new Intent(PaopaoIssuePrivate.this,PaopaoIssueAddFriend.class);
		intent.putExtra("status", 1);
		intent.putIntegerArrayListExtra("friend_selected", lvFriendData_get);
		startActivityForResult(intent,UPDATEFRIEND);
	}

	public void call_paopao_details(View v){
		if(LastClickUtil.onClick() == false) return;
		Intent intent = new Intent(PaopaoIssuePrivate.this,PaopaoIssueDetails.class);
		intent.putExtra("details", usrDetails);
		startActivityForResult(intent,GETISSUEDETAILS);
	}
	
	public void call_for_location(View v){
		if(LastClickUtil.onClick() == false) return;
		Intent intent = new Intent(PaopaoIssuePrivate.this,PaopaoIssueLo.class);
		startActivityForResult(intent,FOR_LOCATION);
	}

	public void back_to_main(View v){
		if(LastClickUtil.onClick() == false) return;
		finish();
	}







	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case UPDATEFRIEND:
				if(data == null) return;
				lvFriendData_get = data.getIntegerArrayListExtra("friend_selected");
				friendList.setText("已邀请"+lvFriendData_get.size()+"位好友");
				break;
			case GETISSUEDETAILS:
				if(data == null) return;
				usrDetails = data.getStringExtra("details");
				break;
			case FOR_LOCATION:
				if(data == null) return;
				getLocationFromMap(data);
				break;

			}
		}
	}

}
