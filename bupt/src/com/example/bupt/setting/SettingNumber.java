package com.example.bupt.setting;

import android.view.View;

import com.example.bupt.R;
import com.example.bupt.issue.time.NumericWheelAdapter;
import com.example.bupt.issue.time.WheelView;

public class SettingNumber {
	private View view;
	private WheelView wv_number;
	public int screenheight;
	private static int START_NUMBER = 0, END_NUMBER = 99;
	
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public SettingNumber(View view) {
		super();
		this.view = view;
		setView(view);
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	public void initDateTimePicker(int number) {

		// 数字
		wv_number = (WheelView) view.findViewById(R.id.setting_number);
		wv_number.setAdapter(new NumericWheelAdapter(START_NUMBER, END_NUMBER));// hour
		wv_number.setCyclic(true);// 可循环滚动
		wv_number.setLabel("人数");// 添加文字
		wv_number.setCurrentItem(number);// 初始化时显示的数据

	}

	public int getNumber() {
		return wv_number.getCurrentItem();
	}

}
