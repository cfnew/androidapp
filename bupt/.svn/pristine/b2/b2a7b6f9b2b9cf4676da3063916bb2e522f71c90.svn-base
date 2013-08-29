package com.example.bupt.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.adapter.ListViewDetailSculpture;
import com.example.bupt.base.AppException;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.http.Api;
import com.example.bupt.model.DetailsSList;
import com.example.bupt.model.DetailsSculpture;
import com.example.bupt.model.Result;
import com.example.bupt.utils.CacheUtil;
import com.example.bupt.utils.DateUtil;
import com.example.bupt.utils.DialogUtil;
import com.example.bupt.utils.ToastUtil;
import com.example.bupt.widget.HorizontalListView;

public class PaopaoDetail extends BaseActivity{
	private ListViewDetailSculpture lvDetails;
	private HorizontalListView hlv;
	private List<DetailsSculpture> lvDetailsData = new ArrayList<DetailsSculpture>();
	private int feedID;
	private String feedTitle;
	private int queue=0;
	private boolean isJoin = false;
	private boolean isOutOfDate = false;
	
	private TextView detailTittle;
	private TextView detailTime;
	private TextView detailLocation;
	private TextView detailTag;
	private TextView gameDetails;
	private TextView detailTip;
	private TextView joinBtn;
	Dialog loading;
	
	private static int UPDATE = 1;
	
	private Handler mHandler = new Handler() {
		DetailsSList dObj = null;
		public void handleMessage(Message msg) {
			loading.dismiss();
			if(msg.what == UPDATE){
				dObj = (DetailsSList) msg.obj;
				initData(dObj);	
			}else if(msg.what == -1){
				ToastUtil.showMsg(PaopaoDetail.this, "网络连接不好啊亲，等等再试");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paopao_detail);
		Intent get_data = this.getIntent();
		Bundle bundle = get_data.getExtras(); 
		feedID = bundle.getInt("feedID");
		loading = DialogUtil.getLoadingDialog(PaopaoDetail.this,"获取数据中...");
		loading.show();
		this.initView();
	}
	private void initView(){
		detailTittle = (TextView)findViewById(R.id.paopao_detail_tv_title);
		detailTime = (TextView)findViewById(R.id.paopao_detail_tv_time);
		detailLocation = (TextView)findViewById(R.id.paopao_detail_tv_location);
		detailTag = (TextView)findViewById(R.id.paopao_detail_tv_tag);
		gameDetails = (TextView)findViewById(R.id.paopao_detail_tv_details);
		detailTip = (TextView)findViewById(R.id.paopao_detail_tip);
		joinBtn = (TextView)findViewById(R.id.paopao_detail_btn);
		joinBtn.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.color.darkblue);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(R.color.lightblue);
				}
				return false;
			}
		});
		joinBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(isOutOfDate) return;
				if(isJoin){ //已经参加了
					//跳转至会话页面
					Intent intent = new Intent(PaopaoDetail.this,Chatting.class);
					intent.putExtra("chat_id", 10);
					intent.putExtra("chat_name", feedTitle);
					startActivity(intent);
				}else{
					//加入活动
					paopao_detail_join();
				}
			}
		});
		if(lvDetailsData.isEmpty()) loadLvDetailsData(mHandler);
	}
	private void initData(DetailsSList dObj){
		lvDetailsData = dObj.getDSList();
		lvDetails = new ListViewDetailSculpture(this, lvDetailsData);
		hlv=(HorizontalListView)findViewById(R.id.paopao_detail_hlv_view);
		hlv.setAdapter(lvDetails);
		hlv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				ImageView iv = (ImageView)view.findViewById(R.id.paopao_details_iv_ds);
				DetailsSculpture sculpture = (DetailsSculpture)iv.getTag();
				UiHelper.showUserCenter(PaopaoDetail.this, sculpture.getUid());
			}
		});
		
		detailTittle.setText(dObj.getTittle()+" ("+dObj.getJoin_num()+"/"+dObj.getNeed_num()+")");
		feedTitle = dObj.getTittle();
		detailTime.setText(DateUtil.format(dObj.getStart_time(), DateUtil.FORMAT_LONG_CN));
		detailLocation.setText(dObj.getLocation());
		detailTag.setText(dObj.getTag());
		gameDetails.setText(dObj.getContent());
		//过期活动
		if(isOutOfDate = DateUtil.isOutOfDate(dObj.getStart_time())){
			joinBtn.setText("活动已结束");
		}else{
			queue = dObj.getJoinStatus();
			isJoin = dObj.isCurUserIsjoin();
			if(isJoin){
				joinBtn.setText("进入讨论组");
			}else{
				if(queue == 0){
					joinBtn.setText("加入该活动");
				}else{
					detailTip.setText("您正处于序列第"+queue+"位");
					joinBtn.setText("进行排队");
				}
			}
		}
	}
	
	/**
	 * 异步加载数据
	 */
	private void loadLvDetailsData(final Handler handler) {
		new Thread(){
			public void run(){
				Message msg = new Message();
				try {
					DetailsSList list = Api.getDetailsSList(PaopaoDetail.this, feedID);
					msg.what = UPDATE;
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	public void paopao_detail_back(View v){
		this.finish();
	}
	
	public void paopao_detail_join(){
		loading = DialogUtil.getLoadingDialog(PaopaoDetail.this,"提交请求中...");
		loading.show();
		final Handler handler = new Handler() {  
            public void handleMessage(Message msg) {  
            	loading.dismiss();
            	if(msg.what == 1){
            		if (msg.obj != null) {  
                        Result res = (Result) msg.obj;
                        if(res.OK()){
                        	isJoin = true;
                        	CacheUtil.clearObj(PaopaoDetail.this, "pp_detail_"+feedID);
                        	detailTip.setText("恭喜！加入活动成功！");
        					joinBtn.setText("进入讨论组");
                        }else{
                        	ToastUtil.showMsg(PaopaoDetail.this, res.getInfo());
                        }
                    } 
            	}else{
            		ToastUtil.showMsg(PaopaoDetail.this, (String)msg.obj);
            	}
            }  
        };  
  
        new Thread() {   
            public void run() {  
                Message message = Message.obtain();  
                try {
					message.obj = Api.joinPaopao(PaopaoDetail.this, feedID);
					message.what = 1;
				} catch (AppException e) {
					message.obj = "网络连接错误";
					message.what = -1;
				}  
                handler.sendMessage(message);  
            }  
        }.start();  
	}

}
