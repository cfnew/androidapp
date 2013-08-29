package com.example.bupt.setting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bupt.R;

/*
 * 地区设置界面
 */
public class SettingAreas extends Activity {
	ListView lv;
	String area_id[] = null;
	String area_name[] = null;
	String select_a_id = new String();
	String select_a_name = new String();
	
	private static final int SETTING_AREA = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_areas);
		//初始化数据
		Intent get_data = this.getIntent();
		Bundle bundle = get_data.getExtras(); 
		int arraylength;
		arraylength = bundle.getInt("arratlength");
		area_id = new String[arraylength];
		area_name = new String[arraylength];
		area_id = bundle.getStringArray("area_id");
		area_name = bundle.getStringArray("area_name");
		lv = (ListView)findViewById(R.id.my_setting_lv_area);


		lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_items,area_name));
		lv.setCacheColorHint(Color.TRANSPARENT);


		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				select_a_id = new String(area_id[position]);
				select_a_name = new String(area_name[position]);
				Intent re_area = new Intent(SettingAreas.this,SettingCity.class);
				re_area.putExtra("select_a_id",select_a_id);
				re_area.putExtra("select_a_name",select_a_name);
				setResult(SETTING_AREA, re_area);
				finish();


			}
		});
	}

	public void back_to_main(View v){
		finish();
	}


}
