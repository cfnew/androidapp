package com.example.bupt.setting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;

/*
 * 城市设置界面
 */
public class SettingCity extends BaseActivity {
	ListView lv;
	String city_id[] = null;
	String city_name[] = null;
	String select_c_id = new String();
	String select_c_name = new String();
	private Dialog loading;
	
	private static final int SETTING_AREA = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_city);
		//初始化数据
		Intent get_data = this.getIntent();
		Bundle bundle = get_data.getExtras(); 
		int arraylength;
		arraylength = bundle.getInt("arratlength");
		city_id = new String[arraylength];
		city_name = new String[arraylength];
		city_id = bundle.getStringArray("cities_id");
		city_name = bundle.getStringArray("city_name");
		lv = (ListView)findViewById(R.id.my_setting_lv_city);


		lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_items,city_name));
		lv.setCacheColorHint(Color.TRANSPARENT);


		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				select_c_id = new String(city_id[position]);
				select_c_name = new String(city_name[position]);
				// 从服务器获得areas的值
				// 创建请求参数
//				RequestParams params = new RequestParams();
//				params.put("pid", city_id[position]);
//				// 发送post请求
//				HttpUtil.post(URLs.GET_AREA, params, new JsonHttpResponseHandler() {
//
//					@Override
//					public void onStart() {
//						super.onStart();
//						loading=DialogUtil.getLoadingDialog(SettingCity.this,
//								"获取服务器数据中...");
//						loading.show();
//					}
//					
//					@Override
//					public void onFinish() {
//						super.onFinish();
//						loading.dismiss();
//					}
//
//					@Override
//					public void onSuccess(JSONObject result) {
//						//获得城市信息
//						JSONArray data = null;
//						try {
//							data = result.getJSONArray("data");
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//
//						int arraylength = data.length();
//						//area_id保存地区编号area_name保存地区名字
//						String[] area_id = new String[arraylength];
//						String[] area_name = new String[arraylength];
//						for(int i = 0; i<arraylength ; i++){
//							try {
//								area_id[i]= data.getJSONObject(i).getString("area_id");
//								area_name[i]= data.getJSONObject(i).getString("title");
//								//System.out.println(data.getJSONObject(i).getString("area_id")+data.getJSONObject(i).getString("title"));
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//						
//						Bundle bundle = new Bundle();
//						bundle.putStringArray("area_id", area_id);
//						bundle.putStringArray("area_name", area_name);
//						bundle.putInt("arratlength", arraylength);
//						Intent intent = new Intent(SettingCity.this,SettingAreas.class);
//						intent.putExtras(bundle);
//						startActivityForResult(intent,SETTING_AREA);
//
//					}
//
//					@Override
//					public void onFailure(Throwable e, JSONObject data) {
//						if (!baseApp.isNetworkConnected()) {
//							ToastUtil
//							.showMsg(SettingCity.this, R.string.network_none);
//						} else {
//							ToastUtil.showMsg(SettingCity.this,
//									R.string.network_error);
//						}
//						e.printStackTrace();
//					}
//
//				});
				
				

			}
		});

	}


	public void back_to_main(View v){
		finish();
	}

	// 回调函数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == SETTING_AREA){
			Intent re_city = new Intent(SettingCity.this,SettingProvince.class);
			re_city.putExtra("select_c_id", select_c_id+","+data.getStringExtra("select_a_id"));
			re_city.putExtra("select_c_name",select_c_name+" "+data.getStringExtra("select_a_name"));
			setResult(SETTING_AREA, re_city);
			finish();
		}

	}


}
