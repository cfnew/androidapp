package com.example.bupt.issue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.bupt.R;
import com.example.bupt.base.BaseApp;
import com.example.bupt.utils.LastClickUtil;
import com.example.bupt.utils.ToastUtil;

public class PaopaoIssueLo extends Activity {
	
	private IssueToSend issue = new IssueToSend();

	//title2中的控件
	//百度地图
	private BMapManager mBMapMan = null;
	private MapView mMapView = null;
	MKSearch MKSearch = null;
	//map搜索控件edittext
	private AutoCompleteTextView search_area = null;
	private String get_area = null;
	private String Init_City = "北京";
	MapController mMapController = null;
	//用来保存及时查询的返回值 以便AutoCompleteTextView使用
	//这里修改了ArrayAdapter的源代码 让其可以搜索中间contain
	Array<String> adapter = null;
	private String loca_default = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paopao_issue_loc);
		mBMapMan=BaseApp.getmBMapMan();
		Message msg = new Message();
		msg.what = 0;
		mHandler.sendMessage(msg);
	}
	/*
	 * hander加载每一个页面的控件
	 */
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				InitBaiDuMap();
			}
		}
	};
	
	
	/*
	 * 初始化百度map
	 */
	private void InitBaiDuMap(){
		mMapView=(MapView)findViewById(R.id.paopao_map_baidu);
		mMapView.setBuiltInZoomControls(true);
		//设置启用内置的缩放控件
		mMapController=mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));
		//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.enableClick(true);
		mMapController.setCenter(point);//设置地图中心点
		mMapController.setZoom(13);//设置地图zoom级别
		MKSearch = new MKSearch();
		MKSearch.init(mBMapMan, new MySearchListener());
		search_area = (AutoCompleteTextView) findViewById(R.id.paopao_et_area);
		search_area.addTextChangedListener(mTextWatcher_where);
		search_area.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MKSearch.suggestionSearch(arg0.getItemAtPosition(arg2).toString());
				issue.setLocation(arg0.getItemAtPosition(arg2).toString());
				if(arg0.getItemAtPosition(arg2).toString() == null) return;
				//点击弹出item事件之后的工作
				get_area = arg0.getItemAtPosition(arg2).toString();
				MKSearch.poiSearchInCity(Init_City, get_area);
			}  

		});
		
	}
	
	/*
	 * 查询地点
	 */

	public void serch_where(View v){
		get_area = search_area.getText().toString();
		if(get_area == null) {Log.i("issue", "where null");return;}
		issue.setLocation(get_area);
		MKSearch.poiSearchInCity(Init_City, get_area);
	}
	
	
	/*
	 * 查询地图页面edittext
	 * 监控文字改变 以便查询
	 */
	TextWatcher mTextWatcher_where = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			String get_temp = s.toString();
			if(s.length() >= 2) MKSearch.suggestionSearch(get_temp);
			issue.setLocation(get_temp);

		}
	};
	
	
	/*
	 * 百度map监听api
	 */
	public class MySearchListener implements MKSearchListener {
		@Override
		public void onGetAddrResult(MKAddrInfo result, int iError) {

			//返回地址信息搜索结果
			if (iError != 0) {
				String str = String.format("错误号：%d", iError);
				Toast.makeText(PaopaoIssueLo.this, str, Toast.LENGTH_LONG).show();
				return;
			}
			//地图移动到该点
			mMapView.getController().animateTo(result.geoPt);
			if (result.type == MKAddrInfo.MK_REVERSEGEOCODE) {
				//反地理编码：通过坐标点检索详细地址及周边poi
				String strInfo = result.strAddr;
				loca_default = strInfo;
				Toast.makeText(PaopaoIssueLo.this, strInfo, Toast.LENGTH_LONG).show();
			}


		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError) {

			//返回驾乘路线搜索结果

		}

		@Override
		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
			if ( iError == MKEvent.ERROR_RESULT_NOT_FOUND){
				Toast.makeText(PaopaoIssueLo.this, "抱歉，未找到结果",Toast.LENGTH_LONG).show();
				return ;
			}
			else if (iError != 0 || result == null) {
				Toast.makeText(PaopaoIssueLo.this, "搜索出错啦..", Toast.LENGTH_LONG).show();
				return;
			}

			// 将poi结果显示到地图上
			MyPoiForTap poiOverlay = new MyPoiForTap(PaopaoIssueLo.this, mMapView,mMapController);
			poiOverlay.setData(result.getAllPoi());
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(poiOverlay);
			mMapView.refresh();

			//当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空
			for(MKPoiInfo info : result.getAllPoi() ){
				if ( info.pt != null ){
					mMapView.getController().animateTo(info.pt);
					break;
				}
			}
			//返回poi搜索结果
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result, int iError) {

			//返回公交搜索结果

		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError) {

			//返回步行路线搜索结果

		}

		@Override    
		public void onGetBusDetailResult(MKBusLineResult result, int iError) {

			//返回公交车详情信息搜索结果

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult result, int iError) {

			//返回联想词信息搜索结果
			if (iError!= 0 || result == null) {

				//Toast.makeText(PaopaoIssue.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show(); 
				return;

			}

			int nSize = result.getSuggestionNum();

			String[] mStrSuggestions = new String[nSize];

			for (int i = 0; i <nSize; i++){

				mStrSuggestions[i] = result.getSuggestion(i).key;

			}

			adapter = new Array<String>(PaopaoIssueLo.this, R.layout.list_items,mStrSuggestions);
			search_area.setAdapter(adapter);
			adapter.notifyDataSetChanged();

		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {

		}

	}
	
	//中心点game_location
	public void game_location(View v){
		if(LastClickUtil.onClick() == false) return;
		GeoPoint point = mMapView.getMapCenter();
		Drawable mark= getResources().getDrawable(R.drawable.ic_launcher);
		OverlayItem item = new OverlayItem(point,"我们在这里集合吧","我们在这里集合吧");
		item.setMarker(mark);
		MyOverlay itemOverlay = new MyOverlay(mark, mMapView);
		itemOverlay.removeAll();  
		mMapView.refresh(); 
		mMapView.getOverlays().clear();  
		mMapView.getOverlays().add(itemOverlay);  
		itemOverlay.addItem(item);
		mMapController.enableClick(true);
		mMapController.setCenter(point);//设置地图中心点
		mMapController.setZoom(18);//设置地图zoom级别
		mMapView.refresh();
		issue.setLatitude(String.valueOf(point.getLatitudeE6()));
		issue.setLongitude(String.valueOf(point.getLongitudeE6()));
		MKSearch.reverseGeocode(point);
	}
	
	public void paopao_issue_loca_baocun(View v){
		if(LastClickUtil.onClick() == false) return;
		if(issue.getLocation() == null) issue.setLocation(loca_default);
		if(issue.getLocation() == null || issue.getLongitude() == null){
			ToastUtil.showMsg(getApplicationContext(), "未保存地点，请重新设置");
			return;
		}
		Intent fanhui_intent = new Intent(PaopaoIssueLo.this,PaopaoIssue.class);
		fanhui_intent.putExtra("location", issue.getLocation());
		fanhui_intent.putExtra("longitude", issue.getLongitude());
		fanhui_intent.putExtra("latitude", issue.getLatitude());
		setResult(7, fanhui_intent);
		finish();	
	}
	
	public void paopao_issue_loca_back(View v){
		if(LastClickUtil.onClick() == false) return;
		finish();
	}
	
	protected void onDestroy() {
		if(mMapView != null)
			mMapView.destroy();
		if(mBMapMan!=null){
			mBMapMan=null;
		}
		super.onDestroy();          

	} 


}
