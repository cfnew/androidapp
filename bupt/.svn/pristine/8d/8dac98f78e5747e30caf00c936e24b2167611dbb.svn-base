package com.example.bupt.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.bupt.R;
import com.example.bupt.base.BaseApp;
import com.example.bupt.ui.Main;

/**
 * 通知信息广播接收器,主要用来显示更新的数据数量以及播放声音
 */
public class BroadCast extends BroadcastReceiver {

	public final static int NOTIFICATION_ID = R.layout.main;
	public static String paopao ="泡";
	public static String atme ="@";
	public static String liuyan = "留";
	public static String pinglun ="评";
	public static String atmeAndliuyan ="留+@";

	private static int newpaopao = 0;
	private static int newpinglun = 0;

	private static boolean haspaopao = false;
	private static boolean hasatme = false;
	private static boolean hasliuyan = false;
	private static boolean haspinglun = false;

	public static int getNewpinglun() {
		return newpinglun;
	}

	public static void setNewpinglun(int newpinglun) {
		BroadCast.newpinglun = newpinglun;
	}
	
	public static int getNewpaopao() {
		return newpaopao;
	}

	public static void setNewpaopao(int newpaopao) {
		BroadCast.newpaopao = newpaopao;
	}

	public static boolean isHaspaopao() {
		return haspaopao;
	}

	public static void setHaspaopao(boolean haspaopao) {
		BroadCast.haspaopao = haspaopao;
	}

	public static boolean isHasatme() {
		return hasatme;
	}

	public static void setHasatme(boolean hasatme) {
		BroadCast.hasatme = hasatme;
	}

	public static boolean isHasliuyan() {
		return hasliuyan;
	}

	public static void setHasliuyan(boolean hasliuyan) {
		BroadCast.hasliuyan = hasliuyan;
	}

	public static boolean isHaspinglun() {
		return haspinglun;
	}

	public static void setHaspinglun(boolean haspinglun) {
		BroadCast.haspinglun = haspinglun;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String ACTION_NAME = intent.getAction();
		if("com.example.bupt.action.APPWIDGET_UPDATE".equals(ACTION_NAME)){
			int paopaoCount = intent.getIntExtra("paopaoCount", 0);       //泡泡
			int atmeCount = intent.getIntExtra("atmeCount", 0);           //@我
			int msgCount = intent.getIntExtra("msgCount", 0);             //留言
			int reviewCount = intent.getIntExtra("reviewCount", 0);       //评论
			int newFansCount = intent.getIntExtra("newFriendCount", 0);   //新好友
			//paopao
//			if(Main.bv_paopao != null){
//				if(paopaoCount > 0){
//					BroadCast.setNewpaopao(BroadCast.newpaopao+paopaoCount);
//					Main.bv_paopao.setText(paopao+BroadCast.getNewpaopao());
//					Main.bv_paopao.show();
//					BroadCast.setHaspaopao(true);
//				}
//			}
//
//			//评论
//			if(Main.bv_review != null){
//				if(reviewCount > 0){
//					BroadCast.setNewpinglun(BroadCast.newpinglun+reviewCount);
//					Main.bv_review.setText(pinglun+BroadCast.getNewpinglun());
//					Main.bv_review.show();
//					BroadCast.setHaspinglun(true);
//				}
//
//			}
//
//			//@我
//			if(Main.bv_atme_message != null){
//				if(atmeCount > 0){
//					BroadCast.setHasatme(true);
//					if(BroadCast.hasliuyan == true){
//						Main.bv_atme_message.setText(atmeAndliuyan);
//						Main.bv_atme_message.show();
//					}else{
//						Main.bv_atme_message.setText(atme);
//						Main.bv_atme_message.show();
//					}
//				}
//			}
//			
//			//留言
//			if(Main.bv_atme_message != null){
//				if(msgCount > 0){
//					BroadCast.setHasliuyan(true);
//					if(BroadCast.hasatme == true){
//						Main.bv_atme_message.setText(atmeAndliuyan);
//						Main.bv_atme_message.show();
//					}else{
//						Main.bv_atme_message.setText(liuyan);
//						Main.bv_atme_message.show();
//					}
//				}
//			}
			//通知栏显示
			this.notification(context);
		}
	}

	private void notification(Context context){		
		//创建 NotificationManager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		String contentTitle = "想ing";
		String contentText = "您有新信息到达";

		//创建通知 Notification
		Notification notification = null;
		notification = new Notification(R.drawable.icon, contentTitle, System.currentTimeMillis());

		//设置点击通知跳转
		Intent intent = new Intent(context, Main.class);
		intent.putExtra("NOTICE", true);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK); 

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

		//设置最新信息
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

		//设置点击清除通知
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		//设置通知方式
		notification.defaults |= Notification.DEFAULT_LIGHTS;

		//设置通知音-根据app设置是否发出提示音
		if(((BaseApp)context.getApplicationContext()).isAppSound())
			notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notificationsound);

		//设置振动 <需要加上用户权限android.permission.VIBRATE>
		//notification.vibrate = new long[]{100, 250, 100, 500};

		//发出通知
		notificationManager.notify(NOTIFICATION_ID, notification);		
	}

}
