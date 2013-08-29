package com.example.bupt.widget;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bupt.R;
import com.example.bupt.ui.Login;

public class GuidePagerAdapter extends PagerAdapter {
	private List<View> views;
	private Activity activity;

	public GuidePagerAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(views.get(position));
	}

	@Override
	public void finishUpdate(View container) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0 == arg1);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(views.get(position), 0);
		if (position == views.size() - 1) {
			ImageView ivStartIng = (ImageView) container
					.findViewById(R.id.iv_start_ing);
			ivStartIng.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goMain();
				}
			});
		}
		return views.get(position);
	}

	public void goMain() {
		Intent intent =new Intent(activity,Login.class);
		activity.startActivity(intent);
		activity.finish();	
	}
}
