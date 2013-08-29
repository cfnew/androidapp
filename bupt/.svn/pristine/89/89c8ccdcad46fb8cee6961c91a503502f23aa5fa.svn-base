package com.example.bupt.ui;

import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.adapter.ListViewFriendAdapter;
import com.example.bupt.adapter.ListViewMsgAdapter;
import com.example.bupt.adapter.ListViewPaopaoAdapter;
import com.example.bupt.adapter.ListViewShareAdapter;
import com.example.bupt.adapter.ListViewTimelineAdapter;
import com.example.bupt.base.AppException;
import com.example.bupt.base.BaseApp;
import com.example.bupt.base.BaseSlidingActivity;
import com.example.bupt.http.Api;
import com.example.bupt.model.ChooseItem;
import com.example.bupt.model.Friend;
import com.example.bupt.model.FriendList;
import com.example.bupt.model.Msg;
import com.example.bupt.model.MsgList;
import com.example.bupt.model.Notice;
import com.example.bupt.model.Paopao;
import com.example.bupt.model.PaopaoList;
import com.example.bupt.model.Result;
import com.example.bupt.model.Share;
import com.example.bupt.model.ShareList;
import com.example.bupt.model.Timeline;
import com.example.bupt.model.TimelineList;
import com.example.bupt.setting.MySetting;
import com.example.bupt.slidingmenu.SlidingMenu;
import com.example.bupt.utils.StringUtils;
import com.example.bupt.utils.ToastUtil;
import com.example.bupt.widget.BroadCast;
import com.example.bupt.widget.NewDataToast;
import com.example.bupt.widget.PullToRe;
import com.example.bupt.widget.ScrollLayout;
import com.example.bupt.widget.SideBar;

public class Main extends BaseSlidingActivity {
	
	public static final int QUICKACTION_DINNER = 0;
	public static final int QUICKACTION_BAR = 1;
	public static final int QUICKACTION_MOVIE = 2;
	public static final int QUICKACTION_KTV = 3;
	public static final int QUICKACTION_SPORT = 4;
	public static final int QUICKACTION_SHOW = 5;
	public static final int QUICKACTION_SALON = 6;
	public static final int QUICKACTION_CAFE = 7;
	public static final int QUICKACTION_OTHER = 8;
	
	private ScrollLayout mScrollLayout;
	//private RadioButton[] mButtons;
	private String[] mHeadTitles;
	private int mViewCount; //main里面listview的个数
	private int mCurSel;

	/**SlidingMenu***************************/ 
	private SlidingMenu sm;

	/**main_header***************************/ 
	private ImageView mHeadLogo;
	private TextView mHeadTitle;
	private ProgressBar mHeadProgress;
	private ImageButton mHeadUface;
	private ImageButton mHeadPost;
	private ImageButton mHeadFriendSearch;
	private View contentView;
	
	/**PopupWindowr***************************/ 
	private PopupWindow mPopupWindow;
	private Button timeButton;
	private Button activeButton;
	private Button tagButton;
	
	
	/**foot_bar**************************************/
	private ImageView paopaoPubBtn;
//	private RadioButton fbShare;
//	private RadioButton fbPaopao;
//	private RadioButton fbFriends;
//	private ImageView fbSetting;

	/**当前个ListView的类别**********************/
	private int curPaopaoType = PaopaoList.LIST_TYPE_LATEST_PUB;
	private int curShareType = ShareList.LIST_TYPE_LATEST;

	/**list_views*****************************************/
	private PullToRe lvPaopao;
	private PullToRe lvTimeline;
	private PullToRe lvShare;
	private PullToRe lvMsg;
	private ListView lvFriend;
	private SideBar  friendIndexBar; //好友的侧边栏
	private TextView friendDialogText; //好友侧边栏滑动时的索引

	/**adapters*************************************************/
	private ListViewPaopaoAdapter lvPaopaoAdapter;
	private ListViewTimelineAdapter lvTimelineAdapter;
	private ListViewShareAdapter lvShareAdapter;
	private ListViewMsgAdapter lvMsgAdapter;
	private ListViewFriendAdapter lvFriendAdapter;

	/**datas********************************************************/
	private List<Paopao> lvPaopaoData = new ArrayList<Paopao>();
	private List<Timeline> lvTimelineData = new ArrayList<Timeline>();
	private List<Share> lvShareData = new ArrayList<Share>();
	private List<Msg> lvMsgData = new ArrayList<Msg>();
	private List<Friend> lvFriendData = new ArrayList<Friend>();

	/**各listview handler************************************************/
	private Handler lvPaopaoHandler;
	private Handler lvTimelineHandler;
	private Handler lvShareHandler;
	private Handler lvMsgHandler;
	private Handler lvFriendHandler;

	/**对应列表现在的总数据量*****************************************/
	private int lvPaopaoSumData;
	private int lvTimelineSumData;
	private int lvShareSumData;
	private int lvMsgSumData;
	private int lvFriendSumData;

	/**列表footer************************************/
	private View lvPaopaoFooter;
	private View lvTimelineFooter;
	private View lvShareFooter;

	/**列表footer文字：更多***************************************/
	private TextView lvPaopaoFooterMore;
	private TextView lvTimelineFooterMore;
	private TextView lvShareFooterMore;
	private TextView lvFriendFooterMore;

	/**列表footer progressbar**********************************************/
	private ProgressBar lvPaopaoFooterLoading;
	private ProgressBar lvTimelineFooterLoading;
	private ProgressBar lvShareFooterLoading;

	/**message**************************************************/
//	public static BadgeView bv_paopao;  //活动
//	public static BadgeView bv_review; //评论
//	public static BadgeView bv_atme_message;  //@我+消息

	private boolean isClearNotice = false; //是否清除提示信息
	private int curClearNoticeType = 0;
	private QuickActionWidget pubQuickAction;// 快捷栏控件
	
	/**window manager*********************************************************************/
	private WindowManager mWindowManager;

	/**debug****************************************************/
	private final String DEBUGE = "DEBUGE_TEST";
	private final String VALUE = "TEST FOR　DEBUGE";

	//取消notification显示
	NotificationManager notificationManager = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/**添加sllidingmenu插件的部分*******************************/
		//设置标题栏的标题
		setTitle("Test");

		//设置是否能够使用ActionBar来滑动
		setSlidingActionBarEnabled(true);

		//初始化侧滑界面
		initSlidingMenu();

		setContentView(R.layout.main);
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mWindowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		
		this.initHeadView();
		this.initFootBtns();
		this.initPageScroll();
		//this.initFrameButton();
		this.initBadgeView();
		this.initQuickActionGrid();
		this.initFrameListView();
	}

	/**
	 * @author mouse
	 * 初始化侧滑界面
	 */
	private void initSlidingMenu() {
		setBehindContentView(R.layout.behind_slidingmenu);
		// customize the SlidingMenu
		sm = getSlidingMenu();
		//sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
		//sm.setShadowWidth(20);
		sm.setBehindScrollScale(0);
		if(sm != null){
			//初始化侧滑界面的选项列表
			initChooseListView();
		}
	}

	/**
	 * @author mouse
	 * 初始化侧滑界面的选项列表
	 */
	private void initChooseListView() {
		ListView  clv = (ListView)findViewById(R.id.behind_list_show);
		List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < ChooseItem.getLogos().length;i++){
			Map<String,Object> listItem = new HashMap<String,Object>();
			listItem.put("logo",ChooseItem.getLogos()[i]);
			listItem.put("fuction",ChooseItem.getFuntion()[i]);
			listItems.add(listItem);
		}
		SimpleAdapter sa = new SimpleAdapter(this,
											 listItems,
											 R.layout.behind_listitem,
											 new String[]{"logo","fuction"},
											 new int[]{R.id.logo,R.id.fuction});
		clv.setAdapter(sa);
		//clv.getChildAt(0).setBackgroundResource(R.drawable.behind_listitem_bg_home);
		//为选项列表添加事件绑定监听器
		clv.setOnItemClickListener(new OnItemClickListener() {
		    private int clickItemNum = 0;
			@SuppressLint("ResourceAsColor")
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				if(position == 3){
				    if(clickItemNum >= 0&&clickItemNum<=3)
				    {
    				    parent.getChildAt(clickItemNum).setBackgroundResource(R.color.slidemenubackcolor);
    				    view.setBackgroundResource(R.drawable.slide_menu_behide_set);
    				    clickItemNum = position;
				    }
				    
					Intent intent = new Intent(Main.this,MySetting.class);
					startActivity(intent);
				}else{
					if(position == 0){
					    if(clickItemNum >= 0&&clickItemNum<=3)
	                    {
	                        parent.getChildAt(clickItemNum).setBackgroundResource(R.color.slidemenubackcolor);
	                        view.setBackgroundResource(R.drawable.slide_menu_behind_home);
	                        clickItemNum = position;
	                    }
					    
						mScrollLayout.snapToScreen(0);
					}else if(position == 1){
					    if(clickItemNum >= 0&&clickItemNum<=3)
	                    {
	                        parent.getChildAt(clickItemNum).setBackgroundResource(R.color.slidemenubackcolor);
	                        view.setBackgroundResource(R.drawable.slide_menu_behide_meg);
	                        clickItemNum = position;
	                    }
					    
						mScrollLayout.snapToScreen(3);
					}else if(position == 2){
					    if(clickItemNum >= 0&&clickItemNum<=3)
	                    {
	                        parent.getChildAt(clickItemNum).setBackgroundResource(R.color.slidemenubackcolor);
	                        view.setBackgroundResource(R.drawable.slide_menu_behide_friend);
	                        clickItemNum = position;
	                    }
					    
						mScrollLayout.snapToScreen(4);
					}
					sm.showContent();
				}
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mWindowManager.removeView(friendDialogText);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	/**
	 * 初始化应用顶部视图
	 */
	private void initHeadView(){
		mHeadLogo = (ImageView)findViewById(R.id.main_head_logo);
		mHeadTitle = (TextView)findViewById(R.id.main_head_title);
		mHeadProgress = (ProgressBar)findViewById(R.id.main_head_progress);
		mHeadUface = (ImageButton)findViewById(R.id.main_head_uface);
		mHeadPost = (ImageButton)findViewById(R.id.main_head_share_post);
		mHeadFriendSearch = (ImageButton)findViewById(R.id.main_head_friend_search);
		
		if(mCurSel == 0)
		{
		    mHeadTitle.setBackgroundResource(R.drawable.mhead_title);
		}
		
		//点击“活动”弹出下拉菜单
		mHeadTitle.setOnClickListener(new OnClickListener() 
		{
            @Override
            public void onClick(View v) 
            {
                //主页"活动"按钮对应的下拉菜单
                if(mCurSel == 0)
                {
                    popMenuList();
                }
            }
        });
		
		//打开侧滑
		mHeadLogo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			    //点击此按钮弹出侧屏
			    sm.showMenu();
			}
		});
		
		//显示个人中心
		mHeadUface.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mScrollLayout.snapToScreen(1);
			}
		});
		//发布一个活动分享
		mHeadPost.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			    //发布分享
			    UiHelper.issueShare(v.getContext());
			}
		});
		//添加好友 
		mHeadFriendSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			    UiHelper.showFriendSearch(Main.this);
			}
		});
	}

	/**
     * @author mouse
     * 点击"活动"弹出下拉列表
     */
	protected void popMenuList() 
	{
	    contentView = getLayoutInflater().inflate(R.layout.pop_menu_list, null);
	    mPopupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT, true);
	    mPopupWindow.setBackgroundDrawable(new BitmapDrawable());//有了这句才可以点击返回（撤销）按钮dismiss()popwindow
	    mPopupWindow.setOutsideTouchable(true);
	    //mPopupWindow.setAnimationStyle(R.style.PopupAnimation)
	    mPopupWindow.showAsDropDown(mHeadLogo,40, 13);
	    
	    timeButton = (Button)contentView.findViewById(R.id.time_button);
	    activeButton = (Button)contentView.findViewById(R.id.active_button);
	    tagButton = (Button)contentView.findViewById(R.id.tag_button);
	    
	    timeButton.setOnClickListener(new OnClickListener() 
	    {
            @Override
            public void onClick(View v) 
            {
                //此处添加功能
                mPopupWindow.dismiss();
            }
        });
	    activeButton.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                //此处添加功能
                mPopupWindow.dismiss();
            }
        });
	    tagButton.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                //此处添加功能
                mPopupWindow.dismiss();
            }
        });
    }

    /**
	 * 初始化底部按钮及信息展示
	 */
	private void initFootBtns(){
		paopaoPubBtn = (ImageView) findViewById(R.id.paopao_pub_btn);
		paopaoPubBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 展示快捷栏,提供发布类型
				pubQuickAction.show(v);
			}
		});
	}
	
	/**
	 * 初始化发布类别
	 */
	private void initQuickActionGrid() {
		pubQuickAction = new QuickActionGrid(this);
		pubQuickAction.addQuickAction(new MyQuickAction(this, R.drawable.date_type_activities,
				R.string.date_type_activities));
		pubQuickAction.addQuickAction(new MyQuickAction(this, R.drawable.date_type_bar,
				R.string.date_type_bar));
		pubQuickAction.addQuickAction(new MyQuickAction(this,R.drawable.date_type_cafe, 
				R.string.date_type_cafe));
		pubQuickAction.addQuickAction(new MyQuickAction(this, R.drawable.date_type_dinner,
				R.string.date_type_dinner));
		pubQuickAction.addQuickAction(new MyQuickAction(this, R.drawable.date_type_ktv,
				R.string.date_type_ktv));
		pubQuickAction.addQuickAction(new MyQuickAction(this, R.drawable.date_type_movie,
				R.string.date_type_movie));
		pubQuickAction.addQuickAction(new MyQuickAction(this,R.drawable.date_type_show, 
				R.string.date_type_show));
		pubQuickAction.addQuickAction(new MyQuickAction(this, R.drawable.date_type_sport,
				R.string.date_type_sport));
		pubQuickAction.addQuickAction(new MyQuickAction(this, R.drawable.date_type_other,
				R.string.date_type_other));

		pubQuickAction.setOnQuickActionClickListener(mActionListener);
	}
	
	/**
	 * 发布栏item点击事件
	 */
	private OnQuickActionClickListener mActionListener = new OnQuickActionClickListener() {
		public void onQuickActionClicked(QuickActionWidget widget, int position) {
			UiHelper.showPaopaoIssue(Main.this, position+1);
		}
	};
	
	/**
	 * 初始化水平滚动翻页
	 */
	private void initPageScroll(){
		mScrollLayout = (ScrollLayout)findViewById(R.id.main_scrolllayout);
		//页面底部tab布局
		//LinearLayout linearLayout = (LinearLayout)findViewById(R.id.main_linearlayout_footer);
		//页面title数组
		mHeadTitles = getResources().getStringArray(R.array.head_titles);
		//main里ListView个数
		mViewCount = mScrollLayout.getChildCount();

		mCurSel = 0; //设置主屏
		mScrollLayout.SetOnViewChangeListener(new ScrollLayout.OnViewChangeListener() {
			public void OnViewChange(int viewIndex) {
				// 切换列表视图-如果列表数据为空：加载数据
				switch (viewIndex) {
				case 0:// 活动
					if(lvPaopaoData.isEmpty()){
						loadLvPaopaoData(curPaopaoType, 1, lvPaopaoHandler, UiHelper.LISTVIEW_ACTION_INIT);
					}
					break;
				case 1:
					if(lvTimelineData.isEmpty()){
						loadLvTimelineData(1, lvTimelineHandler, UiHelper.LISTVIEW_ACTION_INIT);
					}
					break;
				case 2:// 分享 
					if (lvShareData.isEmpty()) {
						//loadLvShareData(curShareType, 1, lvShareHandler, UiHelper.LISTVIEW_ACTION_INIT);
					}
					break;
				case 3:// 好友
					if(lvMsgData.isEmpty()){
						loadLvMsgData(1, lvMsgHandler, UiHelper.LISTVIEW_ACTION_INIT);
					}
					break;
				case 4:
					if(lvFriendData.isEmpty()){
						loadLvFriendData(lvFriendHandler,UiHelper.LISTVIEW_ACTION_INIT);
					}
					break;
				}
				setCurPoint(viewIndex);
			}
		});
	}

	/**
	 * 设置底部栏当前焦点以及顶部标题和按钮
	 * 
	 * @param index
	 */
	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount - 1 || mCurSel == index) return;

		mHeadTitle.setText(mHeadTitles[index]); //设置对应标题
		if(index == 0)
		{
		    mHeadTitle.setBackgroundResource(R.drawable.mhead_title);
		}
		else
		{
		    mHeadTitle.setBackgroundResource(0);
		}
		mCurSel = index; //将新的tab索引赋给mCurSel
		//首相将页顶几个按钮全部隐藏
		mHeadUface.setVisibility(View.GONE);
		mHeadPost.setVisibility(View.GONE);
		mHeadFriendSearch.setVisibility(View.GONE);
		// 根据不同的页面显示不同按钮
		if (index == 0) {
			mHeadUface.setVisibility(View.VISIBLE);
		} else if (index == 2) {
			mHeadPost.setVisibility(View.VISIBLE);
		} else if (index == 3) {
		} else if (index == 4){
			mHeadFriendSearch.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 初始化通知信息标签控件
	 */
	private void initBadgeView() {
//		bv_paopao = new BadgeView(this, fbPaopao);
//		bv_paopao.setBackgroundResource(R.drawable.widget_count_bg);
//		bv_paopao.setIncludeFontPadding(false);
//		bv_paopao.setGravity(Gravity.CENTER);
//		bv_paopao.setTextSize(8f);
//		bv_paopao.setTextColor(Color.WHITE);
//		if(BroadCast.isHaspaopao() == true){
//			bv_paopao.setText(BroadCast.paopao+BroadCast.getNewpaopao());
//			bv_paopao.show();
//		}
//
//		bv_review = new BadgeView(this, fbShare);
//		bv_review.setBackgroundResource(R.drawable.widget_count_bg);
//		bv_review.setIncludeFontPadding(false);
//		bv_review.setGravity(Gravity.CENTER);
//		bv_review.setTextSize(8f);
//		bv_review.setTextColor(Color.WHITE);
//
//		if(BroadCast.isHaspinglun() == true){
//			bv_review.setText(BroadCast.pinglun+BroadCast.getNewpinglun());
//			bv_review.show();
//		}
//
//		bv_atme_message = new BadgeView(this, fbFriends);
//		bv_atme_message.setBackgroundResource(R.drawable.widget_count_bg);
//		bv_atme_message.setIncludeFontPadding(false);
//		bv_atme_message.setGravity(Gravity.CENTER);
//		bv_atme_message.setTextSize(8f);
//		bv_atme_message.setTextColor(Color.WHITE);
//		if(BroadCast.isHasliuyan() == true && BroadCast.isHasatme() == true){
//			bv_atme_message.setText(BroadCast.liuyan+BroadCast.atme);
//			bv_atme_message.show();
//		}else{
//			
//			if(BroadCast.isHasliuyan() == true){
//				bv_atme_message.setText(BroadCast.liuyan);
//				bv_atme_message.show();
//			}
//			
//			if(BroadCast.isHasatme() == true){
//				bv_atme_message.setText(BroadCast.atme);
//				bv_atme_message.show();
//			}	
//		}
	}


	private void initFrameListView(){
		// 初始化listview控件
		this.initPaopaoListView();
		this.initTimelineListView();
		this.initShareListView();
		this.initMsgListView();
		this.initFriendListView();
		// 加载listview数据
		this.initListViewHandler();
	}

	/**
	 * 初始化活动视图
	 */
	private void initPaopaoListView() {
		//获取adapter
		lvPaopaoAdapter = new ListViewPaopaoAdapter(this, lvPaopaoData, R.layout.listitem_paopao);
		//listview页底
		lvPaopaoFooter = getLayoutInflater().inflate(R.layout.listview_footer, null);
		lvPaopaoFooterMore = (TextView)lvPaopaoFooter.findViewById(R.id.listview_foot_more);
		lvPaopaoFooterLoading = (ProgressBar)lvPaopaoFooter.findViewById(R.id.listview_foot_progress);
		//pulltorefreshview
		lvPaopao = (PullToRe)findViewById(R.id.frame_listview_paopao);
		lvPaopao.addFooterView(lvPaopaoFooter); //添加底部视图必须在setAdapter前
		lvPaopao.setAdapter(lvPaopaoAdapter);
		//添加点击,滚动和刷新事件
		lvPaopao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if(position == 0 || view == lvPaopaoFooter) return;
				Paopao pp = null;
				TextView tv = (TextView)view.findViewById(R.id.paopao_listitem_title);
				pp = (Paopao)tv.getTag();
				if(pp == null) return;
				UiHelper.showPaopaoDetail(view.getContext(), pp.getFeedId());
			}
		});
		lvPaopao.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvPaopao.onScrollStateChanged(view, scrollState);
				//没有数据直接返回
				if(lvPaopaoData.isEmpty()) return;
				//判断是否滚动到了底部
				boolean isBottom = false;
				try{
					if(lvPaopao.getPositionForView(lvPaopaoFooter) == view.getLastVisiblePosition())
						isBottom = true;
				}catch(Exception e){
					isBottom = false;
				}
				//判断当前页状态
				int lvDataState = StringUtils.toInt(lvPaopao.getTag());
				if(isBottom && lvDataState == UiHelper.LISTVIEW_DATA_MORE){
					//加载更多数据
					lvPaopao.setTag(UiHelper.LISTVIEW_DATA_LOADING);
					lvPaopaoFooterMore.setText(R.string.load_more);
					lvPaopaoFooterLoading.setVisibility(View.VISIBLE);
					//当前的pageIndex
					int pageIndex = lvPaopaoSumData/BaseApp.PAGE_SIZE + 1;
					//加载数据
					loadLvPaopaoData(curPaopaoType, pageIndex, lvPaopaoHandler, UiHelper.LISTVIEW_ACTION_SCROLL);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvPaopao.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		});
		lvPaopao.setonRefreshListener(new PullToRe.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadLvPaopaoData(curPaopaoType, 1, lvPaopaoHandler, UiHelper.LISTVIEW_ACTION_REFRESH);
			}
		});
	}

	/**
	 * 初始化时间轴视图
	 */
	private void initTimelineListView() {
		//获取Adapter
		lvTimelineAdapter = new ListViewTimelineAdapter(this,lvTimelineData,R.layout.listitem_timeline);
		//ListView 页底
		lvTimelineFooter = getLayoutInflater().inflate(R.layout.listview_footer, null);
		lvTimelineFooterMore = (TextView)lvTimelineFooter.findViewById(R.id.listview_foot_more);
		lvTimelineFooterLoading = (ProgressBar)lvTimelineFooter.findViewById(R.id.listview_foot_progress);
		//PullToRefreshView
		lvTimeline = (PullToRe)findViewById(R.id.frame_listview_timeline);
		lvTimeline.addFooterView(lvTimelineFooter); //添加底部视图必须在setAdapter前
		lvTimeline.setAdapter(lvTimelineAdapter);
		//为ListView添加点击，滚动和刷新事件
		lvTimeline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position == 0 || view == lvTimelineFooter) return;
				Timeline timeline = null;
				TextView title = (TextView)view.findViewById(R.id.timeline_listitem_title);
				timeline = (Timeline)title.getTag();
				if(timeline == null) return;
				UiHelper.showTimelineDetail(view.getContext(), timeline.getFeedId());
			}
		});
		lvTimeline.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvTimeline.onScrollStateChanged(view, scrollState);
				//没有数据直接返回
				if(lvTimelineData.isEmpty()) return;
				//判断是否滚动到了底部
				boolean isScrollEnd = false;
				try{
					if(lvTimeline.getPositionForView(lvTimelineFooter) == view.getLastVisiblePosition())
						isScrollEnd = true;
				}catch(Exception e){
					isScrollEnd = false;
				}
				//判断当前页 的状态，如果当前页的状态是已经加载的全部，就不用再请求数据了
				int lvDataState = StringUtils.toInt(lvTimeline.getTag());
				if(isScrollEnd && lvDataState == UiHelper.LISTVIEW_DATA_MORE){
					lvTimeline.setTag(UiHelper.LISTVIEW_DATA_LOADING);
					lvTimelineFooterMore.setText(R.string.load_more);
					lvTimelineFooterLoading.setVisibility(ProgressBar.VISIBLE);
					//当前的pageIndex
					int pageIndex = lvTimelineSumData / BaseApp.PAGE_SIZE + 1;
					loadLvTimelineData(pageIndex, lvTimelineHandler, UiHelper.LISTVIEW_ACTION_SCROLL);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvTimeline.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		});
		lvTimeline.setonRefreshListener(new PullToRe.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadLvTimelineData(1, lvTimelineHandler, UiHelper.LISTVIEW_ACTION_REFRESH);
			}
		});
	}

	/**
	 * 初始化分享页面视图
	 */
	private void initShareListView() {
		//分享列表的Adapter
		this.lvShareAdapter = new ListViewShareAdapter(this, lvShareData, R.layout.listitem_share);
		//分享列表页底
		this.lvShareFooter = getLayoutInflater().inflate(R.layout.listview_footer,null);
		this.lvShareFooterMore = (TextView)lvShareFooter.findViewById(R.id.listview_foot_more);
		this.lvShareFooterLoading = (ProgressBar)lvShareFooter.findViewById(R.id.listview_foot_progress);
		//分享列表本身
		this.lvShare = (PullToRe) findViewById(R.id.frame_listview_share);
		this.lvShare.addFooterView(lvShareFooter); //添加底部视图必须在setAdapter前
		this.lvShare.setAdapter(lvShareAdapter);
		//事件
		this.lvShare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position,
					long id) {
				//点击底部或头部无效
				if(position == 0 || view == lvShareFooter) return;
				Share share = null;
				TextView tv = (TextView) view.findViewById(R.id.share_listitem_title);
				share = (Share) tv.getTag();
				if(share == null) return;
				UiHelper.showShareDetail(view.getContext(), share);
			}
		});
		this.lvShare.setOnScrollListener(new AbsListView.OnScrollListener() {
			//处理页面滚动事件，当到达底部的时候加载更多
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvShare.onScrollStateChanged(view, scrollState);
				//数据不为空再继续向下执行
				if(lvShareData.isEmpty()) return;
				//是否滚动到了底部
				boolean isScrollEnd = false;
				try{
					if(lvShare.getPositionForView(lvShareFooter) == view.getLastVisiblePosition()){
						isScrollEnd = true;
					}
				}catch(Exception e){
					isScrollEnd = false;
				}
				int lvDataState = StringUtils.toInt(lvShare.getTag());
				//如果滚动到了底部并且为更多
				if(isScrollEnd && lvDataState == UiHelper.LISTVIEW_DATA_MORE){
					lvShare.setTag(UiHelper.LISTVIEW_DATA_LOADING);
					lvShareFooterMore.setText(R.string.load_ing);
					lvShareFooterLoading.setVisibility(View.VISIBLE);
					//当前page_index
					int pageIndex = lvShareSumData / BaseApp.PAGE_SIZE + 1;
					loadLvShareData(curShareType, pageIndex, lvShareHandler, UiHelper.LISTVIEW_ACTION_SCROLL);
				}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvShare.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		});
		lvShare.setonRefreshListener(new PullToRe.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadLvShareData(curShareType, 1, lvShareHandler, UiHelper.LISTVIEW_ACTION_REFRESH);
			}
		});
	}

	/**
	 * 初始化消息列表页
	 */
	private void initMsgListView() {
		//获取adapter
		lvMsgAdapter = new ListViewMsgAdapter(this, lvMsgData, R.layout.listitem_msg);
		//pulltorefreshview
		lvMsg = (PullToRe)findViewById(R.id.frame_listview_msg);
		lvMsg.setAdapter(lvMsgAdapter);
		//添加点击和刷新事件
		lvMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position == 0) return;
				Msg msg = null;
				TextView tv = (TextView)view.findViewById(R.id.message_listitem_username);
				msg = (Msg)tv.getTag();
				if(msg == null) return;
				UiHelper.showMessage(view.getContext(), msg);
			}
		});
		lvMsg.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Msg _msg = null;
				TextView tv = (TextView)view.findViewById(R.id.message_listitem_username);
				_msg = (Msg)tv.getTag();
				if(_msg == null) return false;
				final Msg tobeRemove = _msg;
				final Handler handler = new Handler() {
					public void handleMessage(Message msg) {
						if (msg.what == 1) {
							Result res = (Result) msg.obj;
							if (res.OK()) {
								lvMsgData.remove(tobeRemove);
								lvMsgAdapter.notifyDataSetChanged();
								ToastUtil.showMsg(Main.this, "操作成功");
							}else{
								ToastUtil.showMsg(Main.this, res.getInfo());
							}
							
						} else {
							((AppException) msg.obj).makeToast(Main.this);
						}
					}
				};
				Thread thread = new Thread() {
					public void run() {
						Message msg = new Message();
						try {
							Result res = Api.delMessage(Main.this,tobeRemove.getMsgId());
							msg.what = 1;
							msg.obj = res;
						} catch (AppException e) {
							e.printStackTrace();
							msg.what = -1;
							msg.obj = e;
						}
						handler.sendMessage(msg);
					}
				};
				UiHelper.showMessageListOptionDialog(Main.this, tobeRemove, thread);
				return true;
			}
		});
		lvMsg.setonRefreshListener(new PullToRe.OnRefreshListener() {
			public void onRefresh() {
				loadLvMsgData(1, lvMsgHandler, UiHelper.LISTVIEW_ACTION_REFRESH);
			}
		});
	}

	/**
	 * 初始化好友列表
	 */
	private void initFriendListView() {
		//获取adapter
		lvFriendAdapter = new ListViewFriendAdapter(this, lvFriendData, R.layout.listitem_friend);
		//pulltorefreshview
		lvFriend = (ListView)findViewById(R.id.frame_listview_friend);
		lvFriend.setAdapter(lvFriendAdapter);
		//添加点击和刷新事件
		lvFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView tv = (TextView)view.findViewById(R.id.friend_item_username);
				Friend friend = (Friend)tv.getTag();
				if(friend == null) return;
				UiHelper.showUserCenter(view.getContext(), friend.getFriendId());
			}
		});
		//side bar
		friendIndexBar = (SideBar)findViewById(R.id.friend_side_bar);
		friendIndexBar.setListView(lvFriend);
		friendDialogText = (TextView)LayoutInflater.from(this).inflate(R.layout.friend_index_position, null);
		friendDialogText.setVisibility(View.GONE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mWindowManager.addView(friendDialogText, lp);
        friendIndexBar.setTextView(friendDialogText);
	}

	/**
	 * 初始化所有ListView数据
	 */
	private void initListViewHandler(){
		lvPaopaoHandler = this.getLvHandler(lvPaopao, lvPaopaoAdapter, lvPaopaoFooterMore, lvPaopaoFooterLoading, BaseApp.PAGE_SIZE);
		lvTimelineHandler = this.getLvHandler(lvTimeline, lvTimelineAdapter, lvTimelineFooterMore, lvTimelineFooterLoading, BaseApp.PAGE_SIZE);
		lvShareHandler = this.getLvHandler(lvShare,lvShareAdapter,lvShareFooterMore,lvShareFooterLoading,BaseApp.PAGE_SIZE);
		lvMsgHandler = this.getLvHandler(lvMsg, lvMsgAdapter, null, null, BaseApp.PAGE_SIZE);
		lvFriendHandler = this.getLvFriendHandler(lvFriend, lvFriendAdapter);
		//这里应该首先加载活动数据
		if(lvPaopaoData.isEmpty()){
			loadLvPaopaoData(curPaopaoType, 1, lvPaopaoHandler, UiHelper.LISTVIEW_ACTION_INIT);
		}
	}
	
	private Handler getLvFriendHandler(final ListView lv, final BaseAdapter adapter){
		return new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what >= 0){ //取到了数据
					// listview数据处理
					Notice notice = handleLvData(msg.what, msg.obj, msg.arg2, msg.arg1);
					adapter.notifyDataSetChanged();
					// 发送通知广播
					if (notice != null) {
						UiHelper.sendBroadCast(lv.getContext(), notice);
					}
					// 是否清除通知信息
					if (isClearNotice) {
						ClearNotice(curClearNoticeType);
						isClearNotice = false;// 重置
						curClearNoticeType = 0;
					}
				}else if(msg.what == -1){
					//有异常，显示加载出错，并且弹出错误信息 
					lv.setTag(UiHelper.LISTVIEW_DATA_MORE);
					((AppException)msg.obj).makeToast(Main.this);
				}
//				if(adapter.getCount() == 0){
//					
//				}
			}
		};
	}

	/**
	 * 为各个ListView返回一个对应的handler
	 * @param lvPaopao2
	 * @param adapter
	 * @param more
	 * @param progress
	 * @param pageSize
	 * @return
	 */
	private Handler getLvHandler(final PullToRe lvPaopao2,final BaseAdapter adapter, 
			final TextView more,final ProgressBar progress, final int pageSize){
		return new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what >= 0){ //取到了数据
					// listview数据处理
					Notice notice = handleLvData(msg.what, msg.obj, msg.arg2, msg.arg1);
					if (msg.what < pageSize) { //取到的数据数量小于pagesize，说明没有数据了已经
						lvPaopao2.setTag(UiHelper.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						if(more != null)
							more.setText(R.string.load_full);
					} else if (msg.what >= pageSize) {
						lvPaopao2.setTag(UiHelper.LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						if(more != null)
							more.setText(R.string.load_more);
					}
					// 发送通知广播
					if (notice != null) {
						UiHelper.sendBroadCast(lvPaopao2.getContext(), notice);
					}
					// 是否清除通知信息
					if (isClearNotice) {
						ClearNotice(curClearNoticeType);
						isClearNotice = false;// 重置
						curClearNoticeType = 0;
					}
				}else if(msg.what == -1){
					//有异常，显示加载出错，并且弹出错误信息 
					lvPaopao2.setTag(UiHelper.LISTVIEW_DATA_MORE);
					if(more != null)
						more.setText(R.string.load_error); //加载数据出错
					((AppException)msg.obj).makeToast(Main.this);
				}
				if(adapter.getCount() == 0){
					lvPaopao2.setTag(UiHelper.LISTVIEW_DATA_EMPTY);
					if(more != null){
						if(more == lvFriendFooterMore){
							more.setText(R.string.load__friend_empty);
						}else{
							more.setText(R.string.load_empty);
						}
					}
				}
				if(progress != null)
					progress.setVisibility(ProgressBar.GONE); //loading隐藏
				mHeadProgress.setVisibility(ProgressBar.GONE); //头部loading隐藏
				//修改listview头部显示上次刷新时间
				if(msg.arg1 == UiHelper.LISTVIEW_ACTION_REFRESH){ //刷新动作
					lvPaopao2.onRefreshComplete();
					lvPaopao2.setSelection(0);
				}else if(msg.arg1 == UiHelper.LISTVIEW_ACTION_CHANGE_CATALOG){ //改变类别动作
					lvPaopao2.onRefreshComplete();
					lvPaopao2.setSelection(0);
				}
			}
		};
	}

	/**
	 * listview数据处理
	 * 
	 * @param what       数量
	 * @param obj        数据 ->**List
	 * @param objtype    数据类型 
	 * @param actiontype 操作类型
	 * @return notice    通知信息
	 */
	private Notice handleLvData(int what, Object obj, int objtype,int actiontype) {
		Notice notice = null;
		switch (actiontype) {
		case UiHelper.LISTVIEW_ACTION_INIT:
		case UiHelper.LISTVIEW_ACTION_REFRESH:
		case UiHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			switch (objtype) {
			case UiHelper.LISTVIEW_DATATYPE_PAOPAO:
				//清除“泡”提示
				BroadCast.setHaspaopao(false);
				BroadCast.setNewpaopao(0);
				//bv_paopao.hide();
				notificationManager.cancel(BroadCast.NOTIFICATION_ID);

				PaopaoList plist = (PaopaoList) obj;
				notice = plist.getNotice();
				lvPaopaoSumData = what;
				if (actiontype == UiHelper.LISTVIEW_ACTION_REFRESH) {
					if (lvPaopaoData.size() > 0) {
						for (Paopao pp1 : plist.getPaopaoList()) {
							boolean b = false;
							for (Paopao pp2 : lvPaopaoData) {
								if (pp1.getFeedId() == pp2.getFeedId()) {
									b = true;
									break;
								}
							}
							if (!b) newdata++;
						}
					} else {
						newdata = what;
					}
				}
				lvPaopaoData.clear();// 先清除原有数据
				lvPaopaoData.addAll(plist.getPaopaoList());
				break;
			case UiHelper.LISTVIEW_DATATYPE_TIMELINE:
				TimelineList tlList = (TimelineList) obj;
				notice = tlList.getNotice();
				lvTimelineSumData = what;
				if (actiontype == UiHelper.LISTVIEW_ACTION_REFRESH) {
					if (lvTimelineData.size() > 0) {
						for (Timeline t1 : tlList.getItemList()) {
							boolean add = true;
							for (Timeline t2 : lvTimelineData) {
								if (t1.getFeedId() == t2.getFeedId()) {
									add = false;
									break;
								}
							}
							if (add) newdata++;
						}
					} else {
						newdata = what;
					}
				}
				lvTimelineData.clear();// 先清除原有数据
				lvTimelineData.addAll(tlList.getItemList());
				break;
			case UiHelper.LISTVIEW_DATATYPE_SHARE:
				//清除“评论”提示
				BroadCast.setHaspinglun(false);
				BroadCast.setNewpinglun(0);
				//bv_review.hide();
				notificationManager.cancel(BroadCast.NOTIFICATION_ID);

				ShareList shareList = (ShareList)obj;
				notice = shareList.getNotice();
				lvShareSumData = what;
				if(actiontype == UiHelper.LISTVIEW_ACTION_REFRESH){
					if (lvShareData.size() > 0) {
						for (Share s1 : shareList.getShareList()) {
							boolean add = true;
							for (Share s2 : lvShareData) {
								if (s1.getPaopaoId() == s2.getPaopaoId()) {
									add = false;
									break;
								}
							}
							if (add) newdata++;
						}
					} else {
						newdata = what;
					}
				}
				lvShareData.clear();
				lvShareData.addAll(shareList.getShareList());
				break;
			case UiHelper.LISTVIEW_DATATYPE_MESSAGE:
				//清除“泡”提示
				//BroadCast.setHaspaopao(false);
				//BroadCast.setNewpaopao(0);
				//bv_paopao.hide();
				notificationManager.cancel(BroadCast.NOTIFICATION_ID);

				MsgList msglist = (MsgList) obj;
				notice = msglist.getNotice();
				lvMsgSumData = what;
				if (actiontype == UiHelper.LISTVIEW_ACTION_REFRESH) {
					if (lvMsgData.size() > 0) {
						for (Msg msg1 : msglist.getMsgList()) {
							boolean b = false;
							for (Msg msg2 : lvMsgData) {
								if (msg1.getId() == msg2.getId()) {
									b = true;
									break;
								}
							}
							if (!b) newdata++;
						}
					} else {
						newdata = what;
					}
				}
				lvMsgData.clear();// 先清除原有数据
				lvMsgData.addAll(msglist.getMsgList());
				break;
			case UiHelper.LISTVIEW_DATATYPE_FRIEND:
				FriendList frilist = (FriendList) obj;
				notice = frilist.getNotice();
				lvFriendSumData = what;
				if (actiontype == UiHelper.LISTVIEW_ACTION_REFRESH) {
					if (lvMsgData.size() > 0) {
						for (Friend f1 : frilist.getFriendList()) {
							boolean b = false;
							for (Friend f2 : lvFriendData) {
								if (f1.getFriendId() == f2.getFriendId()) {
									b = true;
									break;
								}
							}
							if (!b) newdata++;
						}
					} else {
						newdata = what;
					}
				}
				lvFriendData.clear();// 先清除原有数据
				lvFriendData.addAll(frilist.getFriendList());
				break;
			}
			if (actiontype == UiHelper.LISTVIEW_ACTION_REFRESH) {
				// 提示新加载数据
				if (newdata > 0) {
					NewDataToast
					.makeText(this,getString(R.string.new_data_toast_message,newdata), baseApp.isAppSound())
					.show();
				} else {
					NewDataToast
					.makeText(this,getString(R.string.new_data_toast_none), false)
					.show();
				}
			}
			break;
		case UiHelper.LISTVIEW_ACTION_SCROLL:
			switch (objtype) {
			case UiHelper.LISTVIEW_DATATYPE_PAOPAO:
				PaopaoList plist = (PaopaoList) obj;
				notice = plist.getNotice();
				lvPaopaoSumData += what;
				if (lvPaopaoData.size() > 0) {
					for (Paopao pp1 : plist.getPaopaoList()) {
						boolean add = true;
						for (Paopao pp2 : lvPaopaoData) {
							if (pp1.getFeedId() == pp2.getFeedId()) {
								add = false;
								break;
							}
						}
						if (add) lvPaopaoData.add(pp1);
					}
				} else {
					lvPaopaoData.addAll(plist.getPaopaoList());
				}
				break;
			case UiHelper.LISTVIEW_DATATYPE_TIMELINE:
				TimelineList tlList = (TimelineList)obj;
				notice = tlList.getNotice();
				lvTimelineSumData += what;
				if(lvTimelineData.size() > 0){
					for(Timeline t1 : tlList.getItemList()){
						boolean add = true;
						for(Timeline t2 : lvTimelineData){
							if(t1.getFeedId() == t2.getFeedId()){
								add = false;
								break;
							}
						}
						if(add) lvTimelineData.add(t1);
					}
				}else{
					lvTimelineData.addAll(tlList.getItemList());
				}
				break;
			case UiHelper.LISTVIEW_DATATYPE_SHARE:
				ShareList shareList = (ShareList)obj;
				notice = shareList.getNotice();
				lvShareSumData += what;
				if(lvShareData.size() > 0){
					for(Share s1 : shareList.getShareList()){
						boolean add = true;
						for(Share s2 : lvShareData){
							if(s1.getPaopaoId() == s2.getPaopaoId()){
								add = false;
								break;
							}
						}
						if(add) lvShareData.add(s1);
					}
				}else{
					lvShareData.addAll(shareList.getShareList());
				}
				break;
			}
			break;
		}
		return notice;
	}

	/**
	 * 异步加载活动数据
	 */
	private void loadLvPaopaoData(final int cat, final int pageIndex, final Handler handler, final int action) {
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread(){
			public void run(){
				Message msg = new Message();
				boolean isRefresh = false; //是否需要刷新数据
				if(action == UiHelper.LISTVIEW_ACTION_REFRESH || action == UiHelper.LISTVIEW_ACTION_SCROLL){
					isRefresh = true;
				}
				try {
					PaopaoList list = Api.getPaopaoList(Main.this,cat,pageIndex, isRefresh);
					msg.what = list.getPageSize();
					msg.obj = list;
				} catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UiHelper.LISTVIEW_DATATYPE_PAOPAO;
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 异步加载时间轴数据
	 */
	private void loadLvTimelineData(final int pageIndex, final Handler handler, final int action){
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread(){
			public void run(){
				Message msg = new Message();
				boolean isRefresh = false; //是否需要刷新数据
				if(action == UiHelper.LISTVIEW_ACTION_REFRESH || action == UiHelper.LISTVIEW_ACTION_SCROLL){
					isRefresh = true;
				}
				try {
					TimelineList list = Api.getTimelineList(Main.this,BaseApp.getUid(),pageIndex, isRefresh);
					msg.what = list.getPageSize();
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UiHelper.LISTVIEW_DATATYPE_TIMELINE;
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 加载分享数据
	 */
	private void loadLvShareData(final int cat, final int pageIndex, final Handler handler, final int action){
		mHeadProgress.setVisibility(ProgressBar.VISIBLE); //页顶显示正在加载的loading
		new Thread(){
			public void run(){
				Message msg = new Message();
				boolean isRefresh = false; //是否需要刷新数据
				if(action == UiHelper.LISTVIEW_ACTION_REFRESH || action == UiHelper.LISTVIEW_ACTION_SCROLL){
					isRefresh = true;
				}
				try {
					ShareList list = Api.getShareList(Main.this, cat, pageIndex, isRefresh);
					msg.what = list.getPageSize();
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UiHelper.LISTVIEW_DATATYPE_SHARE;
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 加载消息数据
	 */
	private void loadLvMsgData(final int pageIndex, final Handler handler, final int action){
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread(){
			public void run(){
				Message msg = new Message();
				boolean isRefresh = false; //是否需要刷新数据
				if(action == UiHelper.LISTVIEW_ACTION_REFRESH || action == UiHelper.LISTVIEW_ACTION_SCROLL){
					isRefresh = true;
				}
				try {
					MsgList list = Api.getMessageList(Main.this, isRefresh);
					msg.what = list.getPageSize();
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UiHelper.LISTVIEW_DATATYPE_MESSAGE;
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 加载好友数据(所有的)
	 */
	private void loadLvFriendData(final Handler handler, final int action){
		new Thread(){
			public void run(){
				Message msg = new Message();
				boolean isRefresh = false; //是否需要刷新数据
				if(action == UiHelper.LISTVIEW_ACTION_REFRESH || action == UiHelper.LISTVIEW_ACTION_SCROLL){
					isRefresh = true;
				}
				try {
					FriendList list = Api.getFriendList(Main.this, isRefresh);
					msg.what = list.getPageSize();
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UiHelper.LISTVIEW_DATATYPE_FRIEND;
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 通知信息处理
	 * 
	 * @param type 1.paopao消息 2:@我的信息 3:未读消息 4:评论个数 5:好友请求个数
	 */
	private void ClearNotice(final int type) {
		final int uid = baseApp.getUid();
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1 && msg.obj != null) {
					Result res = (Result) msg.obj;
					if (res.OK() && res.getNotice() != null) {
						UiHelper.sendBroadCast(Main.this, res.getNotice());
					}
				} else {
					((AppException) msg.obj).makeToast(Main.this);
				}
			}
		};
		new Thread() {
			public void run() {
//				Message msg = new Message();
//				try {
//					Result res = baseApp.noticeClear(uid, type);
//					msg.what = 1;
//					msg.obj = res;
//				} catch (AppException e) {
//					e.printStackTrace();
//					msg.what = -1;
//					msg.obj = e;
//				}
//				handler.sendMessage(msg);
			}
		}.start();
	}


	/**
	 * 禁用原生菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
	/**
	 * 侧滑按键监听
	 */
	public void to_my_setting(View v){
		Intent intent = new Intent(Main.this,MySetting.class);
		startActivity(intent);
	}

	/**
	 * 返回键退出应用
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		boolean flag = true;
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(mCurSel != 0){
				mScrollLayout.snapToScreen(0);
			}else{
				UiHelper.exit(this);
			}
		}else{
			flag = super.onKeyDown(keyCode, event);
		}
		return flag;
	}
	
}
