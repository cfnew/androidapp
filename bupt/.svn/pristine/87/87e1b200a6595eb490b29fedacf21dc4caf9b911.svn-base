package com.example.bupt.issue;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.bupt.R;
import com.example.bupt.adapter.ListViewDetailSculpture;
import com.example.bupt.adapter.ListViewIssueFriendAdd;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.base.BaseApp;
import com.example.bupt.http.Api;
import com.example.bupt.model.DetailsSculpture;
import com.example.bupt.model.Friend;
import com.example.bupt.model.FriendList;
import com.example.bupt.utils.LastClickUtil;
import com.example.bupt.utils.ToastUtil;
import com.example.bupt.widget.HorizontalListView;

public class PaopaoIssueAddFriend extends BaseActivity {
	//用来获得已选取的用户uid
	private int status = 0;
	ArrayList<Integer> lvFriendData_get = new ArrayList<Integer>();
	
	
	private ListViewIssueFriendAdd lvAddFriend = null;
	private ListView lvaddf = null;
	private List<Friend> lvFriendData = new ArrayList<Friend>();
	private int uid;
//	Dialog loading;
	private static int UPDATE = 1;
	private static int DOWNLOAD = 2;

	private ListViewDetailSculpture lvDetails_f = null;
	private HorizontalListView hlv_f = null;
	private List<DetailsSculpture> lvDetailsData_f = null;
	private DetailsSculpture ds = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paopao_issue_add_friend);
		Intent get_data = this.getIntent();
		status = get_data.getIntExtra("status", 0);
		if(status == 1){
			lvFriendData_get = get_data.getIntegerArrayListExtra("friend_selected");
			int index = 0;
			for(index = 0 ; index != lvFriendData_get.size(); index++) Log.i("lvFriendData_get",String.valueOf(lvFriendData_get.get(index)));
		}
		uid = BaseApp.getUid();
//		loading=DialogUtil.getLoadingDialog(PaopaoIssueAddFriend.this,
//				"获取服务器数据中...");
//		loading.show();
		this.init();
	}

	private void init(){
		if(lvFriendData.isEmpty()){
			Message msg = new Message();
			msg.what = DOWNLOAD;
			mHandler.sendMessage(msg);
		} 
	}

	protected void initList(FriendList dObj) {
		ds = new DetailsSculpture();
		lvDetailsData_f = new ArrayList<DetailsSculpture>();
		lvDetails_f = new ListViewDetailSculpture(this, lvDetailsData_f);
		hlv_f=(HorizontalListView)findViewById(R.id.paopao_issue_hlv_adf);
		hlv_f.setAdapter(lvDetails_f);

		lvFriendData = dObj.getFriendList();
		Log.i("lvFriendData",String.valueOf(lvFriendData.size()));
		lvAddFriend = new ListViewIssueFriendAdd(this,lvFriendData,R.layout.paopao_issue_add_friend_item,lvDetails_f,lvFriendData_get,status);
		lvaddf = (ListView) findViewById(R.id.paopao_issue_adf_lv_friend);
		lvaddf.setAdapter(lvAddFriend);
//		loading.dismiss();


	}

	private void loadFriendData(final Handler handler) {
		new Thread(){
			public void run(){
				final Message msg = new Message();
				try{
					//如果已经连接网络，并且是刷新数据或缓存不可读
					if(baseApp.isNetworkConnected()){

						FriendList friendList = Api.getFriendList(PaopaoIssueAddFriend.this, true);
						msg.what = UPDATE;
						msg.obj = friendList;
						handler.sendMessage(msg); //发出消息
					}else{
						FriendList friendList = Api.getFriendList(PaopaoIssueAddFriend.this, false);
						if(friendList == null)
							friendList = new FriendList();
						msg.what = UPDATE;
						msg.obj = friendList;
						handler.sendMessage(msg); //发出消息
					}
				}catch(Exception e){
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
					handler.sendMessage(msg); //发出消息
				}
			}
		}.start();
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		FriendList dObj = null;
		public void handleMessage(Message msg) {
			if(msg.what == UPDATE){
				dObj = (FriendList) msg.obj;
				initList(dObj);
				return;
			}

			if(msg.what == DOWNLOAD){
				loadFriendData(mHandler);
				return;
			}

			if(msg.what == -1){
				ToastUtil.showMsg(getBaseContext(), "网络连接不好啊亲，等等再试");
			}
		}
	};

	public void paopao_issue_tv_adf_back(View v){
		if(LastClickUtil.onClick() == false) return;
		if(status == 0 ){
		Intent intent = new Intent(PaopaoIssueAddFriend.this,PaopaoIssueNew.class);
		startActivity(intent);
		finish();
		return;
		}
		
		if(status == 1){
			finish();
			return;
		}
	}

	public void paopao_issue_ad_f_ok(View v){
		if(LastClickUtil.onClick() == false) return;
		if(status == 0){
		int index = 0;
		ArrayList<Integer> lvFriendData_send = new ArrayList<Integer>();
		for(index = 0; index <lvDetailsData_f.size();index++){
			lvFriendData_send.add(lvDetailsData_f.get(index).getUid());
		}
		Intent intent = new Intent(PaopaoIssueAddFriend.this,PaopaoIssuePrivate.class);
		intent.putIntegerArrayListExtra("friend_selected", lvFriendData_send);
		startActivity(intent);
		finish();
		return;
		}
		
		if(status == 1){
			int index = 0;
			ArrayList<Integer> lvFriendData_send = new ArrayList<Integer>();
			for(index = 0; index <lvDetailsData_f.size();index++){
				lvFriendData_send.add(lvDetailsData_f.get(index).getUid());
			}
			Log.i("lvDetailsData_f size",String.valueOf(lvFriendData_send.size()));
			Intent intent = new Intent(PaopaoIssueAddFriend.this,PaopaoIssuePrivate.class);
			intent.putIntegerArrayListExtra("friend_selected", lvFriendData_send);
			setResult(1, intent);
			finish();
			return;
		}
	}


}
