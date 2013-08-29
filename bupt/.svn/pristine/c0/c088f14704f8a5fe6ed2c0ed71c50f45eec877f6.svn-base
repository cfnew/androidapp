package com.example.bupt.issue;

import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;

import android.app.Activity;

public class MyPoiForTap extends PoiOverlay {
	MapController mMap;
	public MyPoiForTap(Activity activity, MapView mapView, MapController mMapController) {
		super(activity, mapView);
		mMap = mMapController;
	}
	
	@Override
	protected boolean onTap(int i) {
		mMap.setZoom(18);

		return super.onTap(i);//响应父类的点击事件
	}
	

	
}
