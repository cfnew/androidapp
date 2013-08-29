package com.example.bupt.setting;

import android.view.View;

import com.example.bupt.R;
import com.example.bupt.issue.time.NumericWheelAdapter;
import com.example.bupt.issue.time.WheelView;


public class SettingTime {

	private View view;
	private WheelView wv_hour;
	private WheelView wv_minute;
	public int screenheight;
	private static int START_HOUR = 0, END_HOUR = 23;
	private static int START_MINUTE = 0, END_MINUTE = 59;
	
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public SettingTime(View view) {
		super();
		this.view = view;
		setView(view);
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	public void initDateTimePicker(int hour ,int minute) {

		// 时
		wv_hour = (WheelView) view.findViewById(R.id.setting_time_hour);
		wv_hour.setAdapter(new NumericWheelAdapter(START_HOUR, END_HOUR));// hour
		wv_hour.setCyclic(true);// 可循环滚动
		wv_hour.setLabel("时");// 添加文字
		wv_hour.setCurrentItem(hour);// 初始化时显示的数据

		// 分
		wv_minute = (WheelView) view.findViewById(R.id.setting_time_minute);
		wv_minute.setAdapter(new NumericWheelAdapter(START_MINUTE, END_MINUTE));
		wv_minute.setCyclic(true);
		wv_minute.setLabel("分");
		wv_minute.setCurrentItem(minute);

	}

	public String getTime() {
		StringBuffer sb = new StringBuffer();
		sb.append((wv_hour.getCurrentItem())).append(":")
				.append((wv_minute.getCurrentItem()));
		return sb.toString();
	}
}
