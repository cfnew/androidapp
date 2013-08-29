package com.example.bupt.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bupt.R;

/**
 * 蓝色边框toast
 * 
 * @author Administrator
 * 
 */
public class ToastUtil {

	public static final int TOAST_REGISTER = 2000;

	public static void showMsg(Context context, String msg, int duration) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.my_toast, null);
		Toast toast = new Toast(context);
		toast.setView(view);
		// 定义显示位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		// 定义显示时间
		toast.setDuration(duration);
		// 设置要显示的文字
		TextView msgView = (TextView) view.findViewById(R.id.toast_msg);
		msgView.setText(msg);
		toast.show();
	}

	public static void showMsg(Context context, String msg) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.my_toast, null);
		Toast toast = new Toast(context);
		toast.setView(view);
		// 定义显示位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		// 定义显示时间
		toast.setDuration(TOAST_REGISTER);
		// 设置要显示的文字
		TextView msgView = (TextView) view.findViewById(R.id.toast_msg);
		msgView.setText(msg);
		toast.show();
	}

	public static void showMsg(Context context, int resId, int duration) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.my_toast, null);
		Toast toast = new Toast(context);
		toast.setView(view);
		// 定义显示位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		// 定义显示时间
		toast.setDuration(duration);
		// 设置要显示的文字
		TextView msgView = (TextView) view.findViewById(R.id.toast_msg);
		msgView.setText(resId);
		toast.show();
	}

	public static void showMsg(Context context, int resId) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.my_toast, null);
		Toast toast = new Toast(context);
		toast.setView(view);
		// 定义显示位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		// 定义显示时间
		toast.setDuration(TOAST_REGISTER);
		// 设置要显示的文字
		TextView msgView = (TextView) view.findViewById(R.id.toast_msg);
		msgView.setText(resId);
		toast.show();
	}

}
