package com.example.bupt.setting;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.http.Api;
import com.example.bupt.model.ResultArea;
import com.example.bupt.utils.DialogUtil;

/*
 * 省份设置界面
 */
public class SettingProvince extends BaseActivity {
	
	private ListView lv;
	private ArrayAdapter<String> adapter;
	private String[] pDatas;
	private ArrayList<ArrayList<String>> provices = new ArrayList<ArrayList<String>>();
	
	private String select_p_id;
	private String select_p_name;
	private Dialog loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_province);
		//组件
		lv = (ListView)findViewById(R.id.my_setting_lv_province);
		loading=DialogUtil.getLoadingDialog(SettingProvince.this,"获取服务器数据中...");
		loading.show();
		//初始化数据
		initData();
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			loading.dismiss();
			if(msg.what > 0){
				int size = msg.what;
				provices = (ArrayList<ArrayList<String>>)msg.obj;
				pDatas = new String[size];
				for(int i=0; i<size; i++){
					pDatas[i] = provices.get(i).get(1);
				}
				initProvince();
			}
		}
	};
	
	private void initProvince(){
		adapter = new ArrayAdapter<String>(this,R.layout.list_items,pDatas);
		lv.setAdapter(adapter);
		lv.setCacheColorHint(Color.TRANSPARENT);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				select_p_id = provices.get(position).get(0);
				select_p_name = provices.get(position).get(1);
				
				Intent intent = new Intent(SettingProvince.this,SettingCity.class);
				intent.putExtra("province", select_p_id);
				startActivityForResult(intent,SettingMain.SETTING_AREA);
			}
		});
	}
	
	private void initData(){
		new Thread(){
			@Override
			public void run() {
				ResultArea a = Api.getProvinces(SettingProvince.this);
				Message msg = new Message();
				if(a != null && a.OK()){
					msg.what = a.getAreas().size();
					msg.obj = a.getAreas();
				}else{
					msg.what = -1;
					msg.obj = a.getInfo();
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	public void back_to_main(View v){
		finish();
	}

	// 回调函数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == SettingMain.SETTING_AREA){
			Intent re_province = new Intent(SettingProvince.this,SettingMain.class);
			re_province.putExtra("select_p_id", select_p_id+","+data.getStringExtra("select_c_id"));
			re_province.putExtra("select_p_name", select_p_name+" "+data.getStringExtra("select_c_name"));
			SettingProvince.this.setResult(SettingMain.SETTING_AREA, re_province);
			SettingProvince.this.finish();
		}
	}
}
