package com.example.bupt.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.adapter.ListViewSearchAdapter;
import com.example.bupt.base.AppException;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.http.Api;
import com.example.bupt.model.Friend;
import com.example.bupt.model.Notice;
import com.example.bupt.model.SearchFriList;
import com.example.bupt.utils.StringUtils;
import com.example.bupt.utils.ToastUtil;

/**
 * 搜索
 */
public class Search extends BaseActivity{
	private Button mSearchBtn;
	private EditText mSearchEditer;
	private ProgressBar mProgressbar;
	
	private ListView mlvSearch;
	private ListViewSearchAdapter lvSearchAdapter;
	private List<Friend> lvSearchData = new ArrayList<Friend>();
	private View lvSearch_footer;
	private TextView lvSearch_foot_more;
	private ProgressBar lvSearch_foot_progress;
    private Handler mSearchHandler;
    private int lvSumData;
	
	private int curLvDataState;
	private String curSearchContent = "";
    
	private InputMethodManager imm;
	
	private final static int DATA_LOAD_ING = 0x001;
	private final static int DATA_LOAD_COMPLETE = 0x002;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        this.initView();
        this.initData();
	}
	
    /**
     * 头部按钮展示
     * @param type
     */
    private void headButtonSwitch(int type) {
    	switch (type) {
    	case DATA_LOAD_ING:
    		mSearchBtn.setClickable(false);
			mProgressbar.setVisibility(View.VISIBLE);
			break;
		case DATA_LOAD_COMPLETE:
			mSearchBtn.setClickable(true);
			mProgressbar.setVisibility(View.GONE);
			break;
		}
    }
	
	//初始化视图控件
    private void initView(){
    	imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
    	
    	mSearchBtn = (Button)findViewById(R.id.search_btn);
    	mSearchEditer = (EditText)findViewById(R.id.search_editer);
    	mProgressbar = (ProgressBar)findViewById(R.id.search_progress);
    	
    	mSearchBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				mSearchEditer.clearFocus();
				curSearchContent = mSearchEditer.getText().toString();
				loadLvSearchData(1, mSearchHandler, UiHelper.LISTVIEW_ACTION_INIT);
			}
		});
    	mSearchEditer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){  
					imm.showSoftInput(v, 0);  
		        }else{  
		            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
		        }  
			}
		}); 
    	mSearchEditer.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_SEARCH) {
					if(v.getTag() == null) {
						v.setTag(1);
						mSearchEditer.clearFocus();
						curSearchContent = mSearchEditer.getText().toString();
						loadLvSearchData(1, mSearchHandler, UiHelper.LISTVIEW_ACTION_INIT);						
					}else{
						v.setTag(null);
					}
					return true;
				}
				return false;
			}
		});
    	
    	lvSearch_footer = getLayoutInflater().inflate(R.layout.listview_footer, null);
    	lvSearch_foot_more = (TextView)lvSearch_footer.findViewById(R.id.listview_foot_more);
    	lvSearch_foot_progress = (ProgressBar)lvSearch_footer.findViewById(R.id.listview_foot_progress);

    	lvSearchAdapter = new ListViewSearchAdapter(this, lvSearchData, R.layout.listitem_search_fri); 
    	mlvSearch = (ListView)findViewById(R.id.search_listview);
    	mlvSearch.setVisibility(ListView.GONE);
    	mlvSearch.addFooterView(lvSearch_footer);//添加底部视图  必须在setAdapter前
    	mlvSearch.setAdapter(lvSearchAdapter); 
    	
    	mlvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		//点击底部栏无效
        		if(view == lvSearch_footer) return;
        		Friend res = null;
        		//判断是否是TextView
        		TextView title = (TextView)view.findViewById(R.id.search_fri_username);
        		res = (Friend)title.getTag();
        		
        		if(res == null) return;
        		
        		//跳转
        		UiHelper.showUserCenter(view.getContext(), res.getFriendId());
        	}
		});
    	mlvSearch.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {				
				//数据为空--不用继续下面代码了
				if(lvSearchData.size() == 0) return;
				
				//判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if(view.getPositionForView(lvSearch_footer) == view.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}
				
				if(scrollEnd && curLvDataState==UiHelper.LISTVIEW_DATA_MORE){
					mlvSearch.setTag(UiHelper.LISTVIEW_DATA_LOADING);
					lvSearch_foot_more.setText(R.string.load_ing);
					lvSearch_foot_progress.setVisibility(View.VISIBLE);
					//当前pageIndex
					int pageIndex = lvSumData/20;
					loadLvSearchData( pageIndex, mSearchHandler, UiHelper.LISTVIEW_ACTION_SCROLL);
				}
			}
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
			}
		});
    }
    
    //初始化控件数据
  	private void initData()
  	{			
		mSearchHandler = new Handler()
		{
			public void handleMessage(Message msg) {
				
				headButtonSwitch(DATA_LOAD_COMPLETE);

				if(msg.what >= 0){						
					SearchFriList list = (SearchFriList)msg.obj;
					Notice notice = list.getNotice();
					//处理listview数据
					switch (msg.arg1) {
					case UiHelper.LISTVIEW_ACTION_INIT:
					case UiHelper.LISTVIEW_ACTION_REFRESH:
					case UiHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
						lvSumData = msg.what;
						lvSearchData.clear();//先清除原有数据
						lvSearchData.addAll(list.getFriendList());
						break;
					case UiHelper.LISTVIEW_ACTION_SCROLL:
						lvSumData += msg.what;
						if(lvSearchData.size() > 0){
							for(Friend f1 : list.getFriendList()){
								boolean b = false;
								for(Friend f2 : lvSearchData){
									if(f1.getFriendId() == f2.getFriendId()){
										b = true;
										break;
									}
								}
								if(!b) lvSearchData.add(f1);
							}
						}else{
							lvSearchData.addAll(list.getFriendList());
						}
						break;
					}	
					
					if(msg.what < 20){
						curLvDataState = UiHelper.LISTVIEW_DATA_FULL;
						lvSearchAdapter.notifyDataSetChanged();
						lvSearch_foot_more.setText(R.string.load_full);
					}else if(msg.what == 20){
						curLvDataState = UiHelper.LISTVIEW_DATA_MORE;
						lvSearchAdapter.notifyDataSetChanged();
						lvSearch_foot_more.setText(R.string.load_more);
					}
					//发送通知广播
					if(notice != null){
						UiHelper.sendBroadCast(Search.this, notice);
					}
				}else if(msg.what == -1){
					//有异常--显示加载出错 & 弹出错误消息
					curLvDataState = UiHelper.LISTVIEW_DATA_MORE;
					lvSearch_foot_more.setText(R.string.load_error);
					((AppException)msg.obj).makeToast(Search.this);
				}
				if(lvSearchData.size()==0){
					curLvDataState = UiHelper.LISTVIEW_DATA_EMPTY;
					lvSearch_foot_more.setText(R.string.load_empty);
				}
				lvSearch_foot_progress.setVisibility(View.GONE);
				if(msg.arg1 != UiHelper.LISTVIEW_ACTION_SCROLL){
					mlvSearch.setSelection(0);//返回头部
				}
			}
		};
  	}
  	
    /**
     * 线程加载查找好友数据
     * @param pageIndex 当前页数
     * @param handler 处理器
     * @param action 动作标识
     */
	private void loadLvSearchData(final int pageIndex,final Handler handler,final int action){  
		if(StringUtils.isEmpty(curSearchContent)){
			ToastUtil.showMsg(Search.this, "请输入要搜索的用户名");
			return;
		}
		
		headButtonSwitch(DATA_LOAD_ING);
		mlvSearch.setVisibility(ListView.VISIBLE);
		
		new Thread(){
			public void run() {
				Message msg = new Message();
				try {
					SearchFriList searchList = Api.getSearchFriList(Search.this,curSearchContent, pageIndex, 20);
					msg.what = searchList.getPageSize();
					msg.obj = searchList;
	            } catch (AppException e) {
	            	e.printStackTrace();
	            	msg.what = -1;
	            	msg.obj = e;
	            }
				msg.arg1 = action;
				handler.sendMessage(msg);
			}
		}.start();
	} 
	
}
