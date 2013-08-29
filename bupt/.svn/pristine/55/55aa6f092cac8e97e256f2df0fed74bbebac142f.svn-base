package com.example.bupt.setting;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.base.BaseApp;
import com.example.bupt.base.PushService;
import com.example.bupt.ui.Main;
import com.example.bupt.update.DownLoader;
import com.example.bupt.utils.PreferenceUtil;
import com.example.bupt.utils.ToastUtil;

/*
 * 设置主界面
 */
@SuppressLint({ "HandlerLeak", "SdCardPath" })
public class MySetting extends BaseActivity {
	TextView push_service;
	private String mDeviceID;
	private static String pushString_start = "是否打开推送";
	private static String pushString_stop = "是否关闭推送";
	private String pushString;
	// 存放各个下载器
	private Map<String, DownLoader> downloaders = new HashMap<String, DownLoader>();
	String urlstr = "http://192.168.1.199/down/sjmr.mp3";
	String localfile = "/mnt/sdcard/sjmr.mp3";
	private int fileSize = 4291856;
	private NotificationManager	mNotifMan;
	private String NOTIF_TITLE_ING = "正在下载中，请稍后";
	private String NOTIF_TITLE_ED = "下载完成，点击安装更新";
	private int NOTIF_CONNECTED = 7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_setting);
		mDeviceID = String.valueOf(BaseApp.getUid());
		PreferenceUtil.setStringPreferences(getApplicationContext(), PushService.PREF_DEVICE_ID, mDeviceID);
		mNotifMan = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
		push_service = (TextView)findViewById(R.id.my_setting_push_tv_msg);
		if(PushService.ismStarted() == true){
			push_service.setText("已开启");
			pushString = pushString_stop;
		}else{
			push_service.setText("已关闭");
			pushString = pushString_start;
		}
	}

	/**
	 * 打开个人信息设置
	 * 
	 * @param v
	 */
	public void open_personal_set(View v) {
		Intent intent = new Intent(MySetting.this, SettingMain.class);
		startActivity(intent);
	}
	/**
	 * 版本更新
	 */
	public void update(View v){
		boolean check  = true;

		if(check == true){
			AlertDialog.Builder builder = new AlertDialog.Builder(MySetting.this);
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setTitle("发现新版本，请更新！");
			builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					new Thread(){
						public void run(){
					downlaod();
						}
					}.start();
					dialog.dismiss();
				}
			});
			builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.show();
		}
		else{
			ToastUtil.showMsg(getApplicationContext(), "您的版本已经为最新，无需更新");
		}

	}

	private void downlaod(){
		// 初始化一个downloader下载器
		DownLoader downloader = downloaders.get(urlstr);
		if (downloader == null) {
			downloader = new DownLoader(urlstr, localfile, this,mHandler);
			downloaders.put(urlstr, downloader);
		}
		if (downloader.isdownloading())
			return;
		// 得到下载信息类的个数组成集合
		if(downloader.getDownloaderInfors() == null) {
			
			//ToastUtil.showMsg(getApplicationContext(), "服务器忙，请稍后再试");
			Message message = Message.obtain();
			message.what = 5;
			mHandler.sendMessage(message);
			return;
		}
		// 调用方法开始下载
		downloader.download();
	}

	/*
	 * hander更新notification
	 */
	private Handler mHandler = new Handler() {
		int getFinish = 0;
		int hasFinish = 0;
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				int length = msg.arg1;
				getFinish = (int)((double)length*100/(double)fileSize);
				if((getFinish-hasFinish) >1){
					hasFinish = getFinish;
					showNotification(String.valueOf(hasFinish)+"%",NOTIF_TITLE_ING);
				}
			}
			if(msg.what == 4){
				showNotification("100%",NOTIF_TITLE_ED);
				String url = (String) msg.obj;
				downloaders.get(url).delete(url);
				downloaders.get(url).reset();
				downloaders.remove(url);
			}
			if(msg.what == 5){
				ToastUtil.showMsg(getApplicationContext(), "服务器忙，请稍后再试");
			}
		}
	};
	/*
	 * notification
	 */
	@SuppressWarnings("deprecation")
	private void showNotification(String text,String status) {
		Notification n = new Notification();
				
		n.flags |= Notification.FLAG_SHOW_LIGHTS;
      	n.flags |= Notification.FLAG_AUTO_CANCEL;

        n.defaults = Notification.DEFAULT_ALL;
      	
		n.icon = R.drawable.ic_launcher;
		n.when = System.currentTimeMillis();

		// Simply open the parent activity
		Intent intent = new Intent(this, Main.class);
		//intent.putExtra("noticeType", Notice.TYPE_MESSAGE);
		PendingIntent pi = PendingIntent.getActivity(this, 0,intent, 0);

		// Change the name of the notification here
		n.setLatestEventInfo(this, status, text, pi);

		mNotifMan.notify(NOTIF_CONNECTED, n);
	}

	/**
	 * 点击返回时调用的方法
	 */
	public void back_to_main(View v) {
		finish();
	}

	public void clear_caches(View v){
		PreferenceUtil.clearAll(getApplicationContext());
	}
	
	public void push_service(View v){
		AlertDialog.Builder builder = new AlertDialog.Builder(MySetting.this);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(pushString);
		builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(PushService.ismStarted() == false)
				{
				new Thread(){
					public void run(){
						PushService.actionStart(getApplicationContext());
					}
				}.start();
				push_service.setText("已开启");
				pushString = pushString_stop;
				}else if(PushService.ismStarted() == true){
					new Thread(){
						public void run(){
							PushService.actionStop(getApplicationContext());
						}
					}.start();
					push_service.setText("已关闭");
					pushString = pushString_start;					
				}
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.show();
	}
	
	public void call_opinion(View v){
		Intent intent = new Intent(MySetting.this,SettingOpinion.class);
		startActivity(intent);
	}

	/**
	 * 退出登录
	 * 
	 * @param v
	 */
	public void btn_setting_exit(View v) {
		finish();
	}

}
