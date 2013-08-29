package com.example.bupt.share;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.model.Share;
import com.example.bupt.ui.UiHelper;

public class ShareGirdView extends BaseActivity {
	private ImageAndTextListAdapter adapter;
	private GridView gridview ;
	private Share share;
	private List<Map<String,String>> paopaoShares;
	private int picNumber = -1;
	private int index = 0;
	private String[] pic_url = null;
	private static final int INIT = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_gird_view);
		gridview = (GridView)findViewById(R.id.share_gridview);
		Intent get_data = this.getIntent();
        Bundle bundle = get_data.getExtras(); 
		share = (Share) bundle.get("share");
		paopaoShares = share.getPaopaoShares();
		picNumber = paopaoShares.size();
		
		pic_url = new String[picNumber];
		for( index = 0 ; index != picNumber ; index++){
			pic_url[index] = paopaoShares.get(index).get("share_ori");
		}
		
		Message msg = new Message();
		msg.what = INIT;
		mHandler.sendMessage(msg);
		
		
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what == INIT){
				if(picNumber == 0) return;
				List<ImageAndText> listImageAndText = new ArrayList<ImageAndText>();
				for(int i = 0;i < pic_url.length ;i++){
					ImageAndText iat = new ImageAndText(pic_url[i], "图片名字");
					listImageAndText.add(iat);
				}
				adapter = new ImageAndTextListAdapter(ShareGirdView.this,ShareGirdView.this, listImageAndText,gridview);
				gridview.setAdapter(adapter);
				//添加列表项被单击的监听器
				gridview.setOnItemClickListener(new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> parent
						, View view, int position, long id)
					{
						UiHelper.showShareDetailPager(ShareGirdView.this,share,position);
					}
				});
			}
		}
	};
	
	public void paopao_detail_back(View v){
		finish();
	}

}
