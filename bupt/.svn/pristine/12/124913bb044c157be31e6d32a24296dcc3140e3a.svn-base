package com.example.bupt.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.base.BaseApp;

/**
 * 各种对话框显示
 * 
 * @author Administrator
 * 
 */
public class DialogUtil extends BaseApp{

	/**
	 * 显示一个正在加载对话框
	 * 
	 * @param context
	 * @param msg
	 */
	public static Dialog getLoadingDialog(Context context, String msg) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 设置窗体布局
		View v = inflater.inflate(R.layout.loading_dialog, null);
		// 获取布局
		LinearLayout layout = (LinearLayout) v
				.findViewById(R.id.my_dialog_layout);
		// 布局中的ImageView和TextView
		ImageView imgView = (ImageView) v
				.findViewById(R.id.my_dialog_loading_img);
		TextView textView = (TextView) v.findViewById(R.id.my_dialog_show_text);
		// 加载动画
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.dialog_loading);
		// 使用ImageView显示动画
		imgView.startAnimation(animation);
		// 显示加载信息
		textView.setText(msg);
		// 创建自定义dialog
		NewDialog dialog = new NewDialog(context, R.style.loading_dialog);
		dialog.setCancelable(false);// 不可用返回键取消
		// 设置布局
		dialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		return dialog;
	}

}
