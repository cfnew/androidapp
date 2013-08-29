package com.example.bupt.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.base.BaseApp;
import com.example.bupt.http.Api;
import com.example.bupt.model.Friend;
import com.example.bupt.model.Result;
import com.example.bupt.model.ResultFriend;
import com.example.bupt.model.Timeline;
import com.example.bupt.model.TimelineList;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.DateUtil;
import com.example.bupt.utils.DialogUtil;
import com.example.bupt.utils.StringUtils;
import com.example.bupt.utils.ToastUtil;
import com.example.bupt.widget.PullToRe;

@SuppressLint("HandlerLeak")
public class UsrDetailsTimeLine extends BaseActivity {

    //时间轴数据
    private List<Timeline> lvTimelineData = new ArrayList<Timeline>();
    private PullToRe lvTimeline;
    private TimelineList timeLineList;
    //获取时间轴信息
    private static final int GET_TIMELINE_INFO_DONE = 6;
    
    static class ListItemView{  //自定义控件集合
        public TextView paopaoState; //活动状态图标
        public TextView paopaoTitle;  //活动标题
        public TextView paopaoLocation; //地理位置
        public TextView paopaoStartTime; //活动的时间
        public LinearLayout layout;
    }
    
    private LayoutInflater inflater = null;
    private BaseAdapter baseAdapter = null;
    
	//private static final int INIT = 0;
	// 0表示目标好友和当前用户不是好友 ,1目标用户和当前用户是好友,2表示确认好友申请
	private static int RELATION_STATUS = 0;
	//用户信息
	private static final int GET_USR_INFO_DONE = 7;
	//确认之后改变button和status的值
	private static final int ACCEPT_FRIEND_REQ = 8;
	private static final int REQ_FRIEND = 9;
	
	//点击之后获得的usr详情
	private int targetUid;
	private int uid;
	Friend friend = null;
	BitmapUtil btm = null;
	private Dialog loading = null;
	
	
	//头像
	private TextView title;
	private ImageView usrSculpture;
	private TextView usrName;
	private TextView usrAge;
	private ImageView usrSex;
	//private TextView usrInterval;
	private TextView addFriend;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
		    if(loading!=null)
		    {
		        loading.dismiss();
		    }
			if(msg.what == GET_USR_INFO_DONE){
				friend = (Friend) msg.obj;
				initUsrInfo();
			}
			if(msg.what == ACCEPT_FRIEND_REQ){
				ToastUtil.showMsg(UsrDetailsTimeLine.this, "成功添加好友");
				addFriend.setText("开始对话");
				RELATION_STATUS = 1;
			}
			if(msg.what == REQ_FRIEND){
				addFriend.setText("好友请求已发出，请等待");
			}
			if(msg.what == -1){
				ToastUtil.showMsg(getBaseContext(), (String)msg.obj);
			}
		}
	};
	
	@SuppressLint("HandlerLeak")
    private Handler lvTimelineHandler = new Handler() {
	    public void handleMessage(Message msg) {
	        if(loading!=null)
            {
                loading.dismiss();
            }
            if(msg.what == GET_TIMELINE_INFO_DONE)
            {
                timeLineList = (TimelineList) msg.obj;
                lvTimelineData = timeLineList.getItemList();
                
                lvTimeline.setTag(UiHelper.LISTVIEW_DATA_FULL);
                
                for(int i =0 ;i!=lvTimelineData.size();i++)
                {
                    Log.i("TimeLineTest",lvTimelineData.get(i).getFeedLocation());
                }
                
                if(RELATION_STATUS == 1)
                    baseAdapter.notifyDataSetChanged(); 
            }
            if(msg.what == -1)
            {
                ToastUtil.showMsg(getBaseContext(), (String)msg.obj);
            }
        }
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usr_details_time_line);
		
		inflater = getLayoutInflater();
		
		uid = BaseApp.getUid();
		friend = new Friend();
		btm = new BitmapUtil(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_square));
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras(); 
		targetUid = bundle.getInt("targetUid");
		try{
			RELATION_STATUS = bundle.getInt("flag");
		}catch (Exception e){
			RELATION_STATUS = 0;
		}
		loading = DialogUtil.getLoadingDialog(UsrDetailsTimeLine.this,"获取数据中...");
		loading.show();
		init();
	}

	private void init(){
		title = (TextView)findViewById(R.id.user_detail_title);
		usrSculpture = (ImageView) findViewById(R.id.user_detail_face);
		usrSculpture.setOnClickListener(imageClickListener);
		usrName = (TextView) findViewById(R.id.user_detail_name);
		usrAge = (TextView) findViewById(R.id.user_detail_age);
		usrSex = (ImageView) findViewById(R.id.user_detail_sex);
		//usrInterval = (TextView) findViewById(R.id.usr_time_line_tv_jiange);
		addFriend = (TextView) findViewById(R.id.user_detail_btn);
		addFriend.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.color.darkblue);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(R.color.lightblue);
				}
				return false;
			}
		});
		addFriend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addFriend();
			}
		});
		downLoadInfo(mHandler);
		
		initTimeLineList(); 
        
        if(RELATION_STATUS == 0||RELATION_STATUS == 1)
        {
            downLoadTimeLineData(lvTimelineHandler);
        }
	}

    /**
     * @author mouse
     * 初始化时间轴列表
     */
    private void initTimeLineList() {
        //PullToRefreshView
        lvTimeline = (PullToRe)findViewById(R.id.frame_listview_timeline1);
		//创建时间轴的adapter
        baseAdapter = new BaseAdapter()
        {
            @Override
            public int getCount() 
            {
                if(lvTimelineData == null)
                    return 0;
                else
                    return lvTimelineData.size();
            }

            @Override
            public Object getItem(int position) 
            {
                return null;
            }

            @Override
            public long getItemId(int position) 
            {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) 
            {
                ListItemView listItemView = null;
                if(convertView == null){
                    convertView = inflater.inflate(R.layout.listitem_timeline, null);
                    listItemView = new ListItemView();
                    listItemView.paopaoState = (TextView)convertView.findViewById(R.id.timeline_listitem_title_state);
                    listItemView.paopaoTitle = (TextView)convertView.findViewById(R.id.timeline_listitem_title);
                    listItemView.paopaoStartTime = (TextView)convertView.findViewById(R.id.timeline_listitem_startime);
                    listItemView.paopaoLocation = (TextView)convertView.findViewById(R.id.timeline_listitem_location);
                    listItemView.layout = (LinearLayout)convertView.findViewById(R.id.timeline_bg);
                    convertView.setTag(listItemView);
                }else{
                    listItemView = (ListItemView)convertView.getTag();
                }
                //设置图片和文字
                Timeline timeLine = lvTimelineData.get(position);
                listItemView.paopaoTitle.setText(timeLine.getFeedTitle()+"（"+timeLine.getJoinNum()+"/"+timeLine.getNeedNum()+"）");
                listItemView.paopaoTitle.setTag(timeLine); //设置隐藏参数
                listItemView.paopaoStartTime.setText(DateUtil.friendlyTime(timeLine.getFeedStartTime()));
                listItemView.paopaoLocation.setText(timeLine.getFeedLocation());
                if(timeLine.isOver()){
                    listItemView.layout.setBackgroundResource(R.drawable.user_timeline_oringe);
                } 
                return convertView;
            }   
        };
        lvTimeline.setAdapter(baseAdapter);
        //为ListView添加点击，滚动和刷新事件
        lvTimeline.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) return;
                Timeline timeline = null;
                TextView title = (TextView)view.findViewById(R.id.timeline_listitem_title);
                timeline = (Timeline)title.getTag();
                if(timeline == null) return;
                UiHelper.showTimelineDetail(view.getContext(), timeline.getFeedId());
            }
        });
    }

	/*
	 * 下载时间轴信息
	 */
	@SuppressWarnings("unused")
    private void downLoadTimeLineData(final Handler lvTimelineHandler) 
	{
        new Thread()
        {
            public void run()
            {
                try
                {
                    Message msg = new Message();
                    TimelineList list = Api.getTimelineList(UsrDetailsTimeLine.this,targetUid,1, true);
                    
                    if(list != null)
                    {
                        msg.what = GET_TIMELINE_INFO_DONE;
                        msg.obj = list;
                    }
                    else if(list != null)
                    {
                        msg.what = -1;
                        msg.obj = list.getInfo();
                    }
                    else
                    {
                        msg.what = -1;
                        msg.obj = "获取用户数据失败";
                    }
                    lvTimelineHandler.sendMessage(msg); //发出消息
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
        
    }

    /*
	 * 下载用户信息
	 */
	private void downLoadInfo(final Handler handler) {
		new Thread(){
			public void run(){
				Message msg = new Message();
				Log.i("to_get_user_id",targetUid+"");
				ResultFriend rf = Api.getUserInfo(UsrDetailsTimeLine.this, targetUid);
				if(rf != null && rf.OK()){
					friend = rf.getF();
					msg.what = GET_USR_INFO_DONE;
					msg.obj = rf.getF();
				}else if(rf != null){
					msg.what = -1;
					msg.obj = rf.getInfo();
				}else{
					msg.what = -1;
					msg.obj = "获取用户数据失败";
				}
				handler.sendMessage(msg); //发出消息
			}
		}.start();
	}

	/**
	 * 初始化用户信息
	 * @param dObj
	 */
	protected void initUsrInfo() {
		//设置用户名
		if(!StringUtils.isEmpty(friend.getFriendName())) {
			title.setText(friend.getFriendName());
			usrName.setText(friend.getFriendName());
		}
		//设置性别
		if(friend.getSex() == 1){
			usrSex.setBackgroundResource(R.drawable.male);
		}else{
			usrSex.setBackgroundResource(R.drawable.female);
		}
		//设置年龄
		if(friend.getAge() != 0){
			usrAge.setText(friend.getAge()+"");
		}
		if(!StringUtils.isEmpty(friend.getFace())) 
			btm.loadSquareBitmap(friend.getFace(), usrSculpture);
		else{
			usrSculpture.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.avatar_square));
		}
		Log.i("relation_status",RELATION_STATUS+"");
		if(RELATION_STATUS == 2){
			addFriend.setText("确认添加好友");
		}else{
			RELATION_STATUS = friend.getIsFriend();
			if(RELATION_STATUS == 0){
				addFriend.setText("加为好友");
			}else if(RELATION_STATUS == 1){
				addFriend.setText("开始对话");
			}
		}
	}

	public void addFriend(){
		
		//当状态为0时为添加好友
		if(RELATION_STATUS == 0){
			if(uid == targetUid){
				ToastUtil.showMsg(this, "不能添加自己为好友");
				return;
			}
			try{
				//如果已经连接网络
				if(baseApp.isNetworkConnected()){
					loading = DialogUtil.getLoadingDialog(UsrDetailsTimeLine.this,"提交数据中...");
					loading.show();
					new Thread(){
						public void run(){
							Result res = Api.addNewFriend(UsrDetailsTimeLine.this, uid, targetUid);
							Message msg = new Message();
							if(res != null && res.OK()){
								msg.what = REQ_FRIEND;
							}else if(res != null){
								msg.what = -1;
								msg.obj = res.getInfo();
							}
							mHandler.sendMessage(msg);
						}
					}.start();
				}else{
					ToastUtil.showMsg(this,R.string.network_not_connected);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//状态为1时为开始对话
		if(RELATION_STATUS == 1){
			finish();
			//跳转到对话页面
		}
		//确认好友邀请
		if(RELATION_STATUS == 2 ){
			try{
				//如果已经连接网络，并且是刷新数据或缓存不可读
				if(baseApp.isNetworkConnected()){
					loading = DialogUtil.getLoadingDialog(UsrDetailsTimeLine.this,"提交数据中...");
					loading.show();
					new Thread(){
						public void run(){
							Result res = Api.optFriendRequest(UsrDetailsTimeLine.this, targetUid, "accept");
							Message msg = new Message();
							if(res != null && res.OK()){
								msg.what = ACCEPT_FRIEND_REQ;
							}else if(res != null){
								msg.what = -1;
								msg.obj = res.getInfo();
							}
							mHandler.sendMessage(msg);
						}
					}.start();
				}else{
					ToastUtil.showMsg(this, R.string.network_not_connected);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void back_to_main(View v){
		this.finish();
	}

	private View.OnClickListener imageClickListener = new View.OnClickListener(){
		public void onClick(View v) {
			UiHelper.showImageDialog(v.getContext(), friend.getFaceBig());
		}
	};
}
