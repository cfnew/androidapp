package com.example.bupt.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.adapter.ListViewChatAdapter;
import com.example.bupt.base.AppConfig;
import com.example.bupt.base.AppException;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.base.BaseApp;
import com.example.bupt.base.URLs;
import com.example.bupt.http.Api;
import com.example.bupt.model.Chat;
import com.example.bupt.model.ChatList;
import com.example.bupt.model.Notice;
import com.example.bupt.model.Result;
import com.example.bupt.model.ResultChat;
import com.example.bupt.utils.DialogUtil;
import com.example.bupt.utils.HttpUtil;
import com.example.bupt.utils.StringUtils;
import com.example.bupt.utils.ToastUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Chatting extends BaseActivity{
	private Button btnBack;
	private TextView chatTitle;
	
	private ListView chatListView;
	private ListViewChatAdapter chatAdapter;
	private List<Chat> chatListData = new ArrayList<Chat>();
	private int chatSumData = 0;
	private Handler chatHandler;
	
	private Button btnSend;
	private EditText editTextContent;
	private String tmpMessageKey;
	private InputMethodManager imm;
	
	private Dialog loading = null;
	private ProgressDialog mProgress;
	
	private int curChatId;
    private String curChatName;
	private int curChatType;	
	private int curLvDataState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_detail);
		this.initView();
		this.initListener();
		this.initData();
	}
	
	private void initView(){
		curChatId = getIntent().getIntExtra("chat_id", 0);
		curChatName = getIntent().getStringExtra("chat_name");
		if(curChatId > 0){
			tmpMessageKey = "tmp_message_"+curChatId;
		}
		
		btnBack = (Button)findViewById(R.id.chat_back_button);
		chatTitle = (TextView)findViewById(R.id.chat_target_name);
		btnSend = (Button)findViewById(R.id.chat_pub_message);
		editTextContent = (EditText)findViewById(R.id.chat_editer);
		
		imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE); 
		
		//显示临时编辑内容到内容编辑框中
		showTempEditContent();
		chatTitle.setText(curChatName);
		
		chatAdapter = new ListViewChatAdapter(this, chatListData);
		chatListView = (ListView)findViewById(R.id.chat_lv);
		chatListView.setAdapter(chatAdapter);
		
		loading = DialogUtil.getLoadingDialog(Chatting.this,"获取数据中...");
		loading.show();
	}
	
	private void initListener(){
		//编辑器添加文本监听
		editTextContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){  
					imm.showSoftInput(v, 0);  
					chatListView.setSelection(chatSumData-1);
		        }else{  
		            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
		        }
			}
		});
		editTextContent.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//保存当前正在编辑文本
				AppConfig.getAppConfig(Chatting.this).set(tmpMessageKey, s.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = editTextContent.getText().toString();
				if(StringUtils.isEmpty(content)){
					ToastUtil.showMsg(Chatting.this, "消息内容不能为空");
					return;
				}
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				mProgress = ProgressDialog.show(v.getContext(), null, "发送中···",true,true); 
				
				final Handler handler = new Handler(){
					public void handleMessage(Message msg) {
						if(mProgress != null){
							mProgress.dismiss();
						}
						if(msg.what == 1){
							ResultChat res = (ResultChat)msg.obj;
							if(res.OK()){
								//发送通知广播
								if(res.getNotice() != null){
									UiHelper.sendBroadCast(Chatting.this, res.getNotice());
								}
								//恢复初始底部栏
								editTextContent.clearFocus();
								editTextContent.setText("");
								//显示刚刚发送的留言
								chatListData.add(res.getChat());
								chatSumData += 1;
								chatAdapter.notifyDataSetChanged();
								chatListView.setSelection(chatSumData-1);
					        	//清除之前保存的编辑内容
								AppConfig.getAppConfig(Chatting.this).remove(tmpMessageKey);
							}
						}else {
							((AppException)msg.obj).makeToast(Chatting.this);
						}
					}
				};
				new Thread(){
					public void run() {
						Message msg =new Message();
						try {
							ResultChat res = Api.replay(Chatting.this, curChatId, editTextContent.getText().toString());
							Chat chat = new Chat();
							chat.setChatId(curChatId);
							chat.setChatPubTime(new Date());
							chat.setChatContent(editTextContent.getText().toString());
							chat.setChatIsReceive(false);
							res.setChat(chat);
							msg.what = 1;
							msg.obj = res;
			            } catch (AppException e) {
			            	e.printStackTrace();
							msg.what = -1;
							msg.obj = e;
			            }
						handler.sendMessage(msg);
					}
				}.start();
			}
		});
		chatListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				TextView content = (TextView)view.findViewById(R.id.chat_item_content);
        		final Chat chat = (Chat)content.getTag();
				
        		//操作--删除         		
        		final Handler handler = new Handler(){
					@Override
					public void handleMessage(Message msg) {
						if(msg.what == 1){
							Result res = (Result)msg.obj;
							if(res.OK()){
								chatSumData--;
								chatListData.remove(chat);
								chatAdapter.notifyDataSetChanged();
							}
							ToastUtil.showMsg(Chatting.this, res.getInfo());
						}else{
							((AppException)msg.obj).makeToast(Chatting.this);
						}
					}        			
        		};
        		final Thread thread = new Thread(){
					public void run() {
						final Message msg = new Message();
						if(baseApp.isNetworkConnected()){
							RequestParams params = new RequestParams();
							params.put("uid", BaseApp.getUid()+"");
							params.put("msg_id", chat.getChatId()+"");
							HttpUtil.get(URLs.DEL_CHAT_ITEM, params, new JsonHttpResponseHandler(){
								@Override
								public void onSuccess(JSONObject jsonObj) {
									Result res = new Result();
									try {
										int status = jsonObj.getInt("status");
										String info = jsonObj.getString("info");
										res.setStatus(status);
										res.setInfo(info);
										msg.what = 1;
										msg.obj = res;
										handler.sendMessage(msg);
									} catch (JSONException e) {
									}
								}
								@Override
								public void onFailure(Throwable arg0,JSONObject arg1) {
									msg.what = -1;
					            	msg.obj = arg0;
					            	handler.sendMessage(msg);
								}
							});
						}else{
							ToastUtil.showMsg(Chatting.this, R.string.network_not_connected);
						}
					}        			
        		};
        		//显示操作选择对话框
        		UiHelper.showMessageDetailOptionDialog(Chatting.this, chat, thread);
				return true;
			}
		});
	}
	
	private void showTempEditContent() {
		String tempContent = AppConfig.getAppConfig(this).get(tmpMessageKey);
		if (!StringUtils.isEmpty(tempContent)) {
			SpannableStringBuilder builder = UiHelper.parseFaceByText(this,tempContent);
			editTextContent.setText(builder);
			editTextContent.setSelection(tempContent.length());// 设置光标位置
		}
	}
	
	private void initData(){
		chatHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(loading != null){
					loading.dismiss();
				}
				if(msg.what >= 0){
					ChatList list = (ChatList)msg.obj;
					Notice notice = list.getNotice();
					// 处理listview数据
					switch (msg.arg1) {
					case UiHelper.LISTVIEW_ACTION_INIT:
					case UiHelper.LISTVIEW_ACTION_REFRESH:
						chatSumData = msg.what;
						chatListData.clear();//先清除原有数据
						chatListData.addAll(list.getChatList());
						break;
					case UiHelper.LISTVIEW_ACTION_SCROLL:
						chatSumData += msg.what;
						if(chatListData.size() > 0){
							for(Chat ch1 : list.getChatList()){
								boolean b = false;
								for(Chat ch2 : chatListData){
									if(ch1.getChatId() == ch2.getChatId()){
										b = true;
										break;
									}
								}
								if(!b) chatListData.add(ch1);
							}
						}else{
							chatListData.addAll(list.getChatList());
						}
						break;
					}	
					
					//发送通知广播
					if(notice != null){
						UiHelper.sendBroadCast(Chatting.this, notice);
					}
					chatListView.setSelection(chatSumData-1);
					chatAdapter.notifyDataSetChanged();
				}else if(msg.what == -1){
					//有异常--
					((AppException)msg.obj).makeToast(Chatting.this);
				}
				if(chatListData.size()==0){
				}
			}
		};
		this.loadLvChatData(1, chatHandler);
	}
	
	public void loadLvChatData(final int pageIndex, final Handler handler){
		new Thread(){
			public void run(){
				Message msg = new Message();
				try {
					ChatList list = Api.getChatList(Chatting.this,curChatId);
					msg.what = list.getPageSize();
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = UiHelper.LISTVIEW_ACTION_INIT;
				msg.arg2 = UiHelper.LISTVIEW_DATATYPE_CHAT;
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	
}
