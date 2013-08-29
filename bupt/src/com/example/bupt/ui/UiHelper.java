package com.example.bupt.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;

import com.example.bupt.R;
import com.example.bupt.adapter.GridViewFaceAdapter;
import com.example.bupt.base.AppManager;
import com.example.bupt.base.BaseApp;
import com.example.bupt.issue.PaopaoIssue;
import com.example.bupt.issue.PaopaoIssueNew;
import com.example.bupt.model.Chat;
import com.example.bupt.model.Msg;
import com.example.bupt.model.Notice;
import com.example.bupt.model.Share;
import com.example.bupt.share.IssueShareAddContentActivity;
import com.example.bupt.share.IssueShareSelectActivity;
import com.example.bupt.share.ShareGirdView;
import com.example.bupt.share.SharePictureContentActivity;
import com.example.bupt.utils.StringUtils;

public class UiHelper {

	public static final int LISTVIEW_ACTION_INIT = 0x01;
	public static final int LISTVIEW_ACTION_REFRESH = 0x02;
	public static final int LISTVIEW_ACTION_SCROLL = 0x03;
	public static final int LISTVIEW_ACTION_CHANGE_CATALOG = 0x04;

	public static final int LISTVIEW_DATA_MORE = 0x01;
	public static final int LISTVIEW_DATA_LOADING = 0x02;
	public static final int LISTVIEW_DATA_FULL = 0x03;
	public static final int LISTVIEW_DATA_EMPTY = 0x04;

	public static final int LISTVIEW_DATATYPE_PAOPAO = 0x01;
	public static final int LISTVIEW_DATATYPE_TIMELINE = 0x02;
	public static final int LISTVIEW_DATATYPE_SHARE = 0x03;
	public static final int LISTVIEW_DATATYPE_USER_SHARE = 0x04;
	public static final int LISTVIEW_DATATYPE_MESSAGE = 0x05;
	public static final int LISTVIEW_DATATYPE_CHAT = 0x06;
	public static final int LISTVIEW_DATATYPE_FRIEND = 0x07;
	public static final int LISTVIEW_DATATYPE_COMMENT = 0x08;

	public final static int REQUEST_CODE_FOR_RESULT = 0x01;
	public final static int REQUEST_CODE_FOR_REPLY = 0x02;

	/** 表情图片匹配 */
	private static Pattern facePattern = Pattern.compile("\\[{1}([0-9]\\d*)\\]{1}");

	/** 全局web样式 */
	public final static String WEB_STYLE = "<style>* {font-size:16px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} "
			+ "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
			+ "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;} "
			+ "a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";

	/**
	 * 显示首页
	 * 
	 * @param activity
	 */
	public static void showHome(Activity activity) {
		Intent intent = new Intent(activity, Main.class);
		activity.startActivity(intent);
		activity.finish();
	}
	
	
	/**
	 * 显示图片对话框
	 * 
	 * @param context
	 * @param imgUrl
	 */
	public static void showImageDialog(Context context, String imgUrl) {
		Intent intent = new Intent(context, ImageDialog.class);
		intent.putExtra("img_url", imgUrl);
		context.startActivity(intent);
	}
	
	public static void showImageZoomDialog(Context context, String imgUrl) {
		Intent intent = new Intent(context, ImageZoomDialog.class);
		intent.putExtra("img_url", imgUrl);
		context.startActivity(intent);
	}
	
	/**
	 * 显示活动详情页
	 * @param context
	 * @param feedid
	 */
	public static void showPaopaoDetail(Context context, int feedid){
		Intent intent = new Intent(context, PaopaoDetail.class);
		Bundle b = new Bundle();
		b.putInt("feedID", feedid);
		intent.putExtras(b);
		context.startActivity(intent);
	}
	
	/**
	 * 显示事件轴详情
	 * @param context
	 * @param feedId
	 */
	public static void showTimelineDetail(Context context, int feedId) {
		
	}
	
	/**
	 * 显示paopao发布页面
	 */
	public static void showPaopaoIssue(Context context, int catId){
		Intent intent = new Intent(context,PaopaoIssueNew.class);
		intent.putExtra("cat_ids", catId);
		context.startActivity(intent);
	}
	
	/**
	 * 显示分享的详细页面
	 * @param context
     * @param feedId
	 */
	public static void showShareDetail(Context context, Share share)
	{    
		Intent intent = new Intent(context,ShareGirdView.class);
		intent.putExtra("share", share);
		context.startActivity(intent);
	}
	
	/**
	 * 显示分享的详细页面_viewpater
	 * @param context
     * @param feedId
	 */
	public static void showShareDetailPager(Context context, Share share,int index)
	{    
		Intent intent = new Intent(context,SharePictureContentActivity.class);              //ShareContentActivity.class
		intent.putExtra("share", share);
		intent.putExtra("index", index);
		context.startActivity(intent);
	}
	
	
	
	/**
     * 发布分享页面
     * @param context
     */
    public static void issueShare(Context context)
    {    
        Intent intent = new Intent(context,IssueShareSelectActivity.class);
        context.startActivity(intent);
    }
	
    public static void showAddIssueShare(Context context,String feed_id,String feed_title)
    {    
        Intent intent = new Intent(context,IssueShareAddContentActivity.class);
        intent.putExtra("feed_id", feed_id);
        intent.putExtra("feed_title", feed_title);
        context.startActivity(intent);
    }
    
	/**
	 * 显示消息回话的详细页面
	 */
	public static void showMessage(Context context, Msg msg){
		int type = msg.getMsgType();
		Intent intent = null;
		switch(type){
		case 1: //新好友申请
			intent = new Intent(context,UsrDetailsTimeLine.class);
			intent.putExtra("targetUid", msg.getFromId());
			intent.putExtra("flag", 2); //告诉个人页面是处理好友请求
			break;
		case 2: //新评论
			break;
		case 3: //新消息(聊天)
			intent = new Intent(context, Chatting.class);
			intent.putExtra("chat_id", msg.getFromId());
			intent.putExtra("chat_name", msg.getFromName());
			break;
		case 4: //新活动邀请
			break;
		case 5: //新动态(活动人员的变动等)
			break;
		}
		context.startActivity(intent);
	}
	
	/**
	 * 查看用户的时候，显示用户详情
	 */
	public static void showUserCenter(Context context, int uid){
		Intent intent = new Intent(context,UsrDetailsTimeLine.class);
		intent.putExtra("targetUid", uid);
		context.startActivity(intent);
	}
	
	/**
	 * 将[12]之类的字符串替换为表情
	 * 
	 * @param context
	 * @param content
	 */
	public static SpannableStringBuilder parseFaceByText(Context context, String content) {
		SpannableStringBuilder builder = new SpannableStringBuilder(content);
		Matcher matcher = facePattern.matcher(content);
		while (matcher.find()) {
			// 使用正则表达式找出其中的数字
			int position = StringUtils.toInt(matcher.group(1));
			int resId = 0;
			try {
				if (position > 65 && position < 102)
					position = position - 1;
				else if (position > 102)
					position = position - 2;
				resId = GridViewFaceAdapter.getImageIds()[position];
				Drawable d = context.getResources().getDrawable(resId);
				d.setBounds(0, 0, 35, 35);// 设置表情图片的显示大小
				ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
				builder.setSpan(span, matcher.start(), matcher.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			} catch (Exception e) {
			}
		}
		return builder;
	}
	
	/**
	 * 发送通知广播
	 * 
	 * @param context
	 * @param notice
	 */
	public static void sendBroadCast(Context context, Notice notice) {
		//如果没有登录或者消息为空，直接返回
		if (!((BaseApp) context.getApplicationContext()).isLogin() || notice == null) return;
		Intent intent = new Intent("com.example.bupt.action.APPWIDGET_UPDATE");
		intent.putExtra("paopaoCount", notice.getPaopaoCount());
		intent.putExtra("atmeCount", notice.getAtmeCount());
		intent.putExtra("msgCount", notice.getMsgCount());
		intent.putExtra("reviewCount", notice.getReviewCount());
		intent.putExtra("newFriendCount", notice.getNewFriendCount());
		context.sendBroadcast(intent);
	}
	
	/**
	 * 退出应用
	 * @param context
	 */
	public static void exit(final Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_menu_surelogout);
		builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				AppManager.getAppManager().AppExit(context);
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
	
	/**
	 * 消息详情操作选择框
	 * 
	 * @param context
	 * @param msg
	 * @param thread
	 */
	public static void showMessageDetailOptionDialog(final Activity context,
			final Chat chat, final Thread thread) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_dialog_menu);
		builder.setTitle(context.getString(R.string.select));
		builder.setItems(R.array.message_detail_options,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						switch (arg1) {
						case 0:// 转发
							showMessageForward(context, chat.getChatContent());
							break;
						case 1:// 删除
							thread.start();
							break;
						}
					}
				});
		builder.create().show();
	}
	
	/**
	 * 显示转发消息界面
	 * 
	 * @param context
	 * @param friendName 对方名称
	 * @param messageContent 消息内容
	 */
	public static void showMessageForward(Activity context, String messageContent) {
		Intent intent = new Intent();
		intent.putExtra("message_content", messageContent);
		intent.setClass(context, ChatForward.class);
		context.startActivity(intent);
	}
	
	/**
	 * 显示搜索好友界面
	 * 
	 * @param context
	 */
	public static void showFriendSearch(Context context) {
		Intent intent = new Intent(context, Search.class);
		context.startActivity(intent);
	}
	
	/**
	 * 消息列表操作选择框
	 * 
	 * @param context
	 * @param msg
	 * @param thread
	 */
	public static void showMessageListOptionDialog(final Activity context, final Msg msg, final Thread thread) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_dialog_menu);
		builder.setTitle(context.getString(R.string.select));
		builder.setItems(R.array.message_list_options,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						switch (arg1) {
						case 0:// 删除
							thread.start();
							break;
						}
					}
				});
		builder.create().show();
	}
	
	/**
	 * 获取LayoutInflater
	 * 
	 * @return LayoutInflater
	 */
	public static LayoutInflater getLayout(Context context) {
		return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 获取Layout View
	 * 
	 * @param layoutId
	 * @return View
	 */
	public static View getLayout(Context context,int layoutId) {
		return getLayout(context).inflate(layoutId, null);
	}

	/**
	 * 获取某个布局里面的某个组件
	 * 
	 * @param layoutId
	 * @param itemId
	 * @return View
	 */
	public static View getLayout(Context context,int layoutId, int itemId) {
		return getLayout(context,layoutId).findViewById(itemId);
	}

	/**
	 * 发送App异常崩溃报告
	 * 
	 * @param cont
	 * @param crashReport
	 */
	public static void sendAppCrashReport(final Context cont,
			final String crashReport) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_error);
		builder.setMessage(R.string.app_error_message);
		builder.setPositiveButton(R.string.submit_report,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 发送异常报告
						Intent i = new Intent(Intent.ACTION_SEND);
						// i.setType("text/plain"); //模拟器
						i.setType("message/rfc822"); // 真机
						i.putExtra(Intent.EXTRA_EMAIL,
								new String[] { "yuzhen99@yeah.net" });
						i.putExtra(Intent.EXTRA_SUBJECT,
								"想ing Android客户端 - 错误报告");
						i.putExtra(Intent.EXTRA_TEXT, crashReport);
						cont.startActivity(Intent.createChooser(i, "发送错误报告"));
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.setNegativeButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.show();
	}


}
