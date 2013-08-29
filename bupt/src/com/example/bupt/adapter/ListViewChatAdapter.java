package com.example.bupt.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.model.Chat;
import com.example.bupt.ui.UiHelper;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.DateUtil;
import com.example.bupt.utils.StringUtils;

/**
 * 用户留言详情Adapter类
 */
public class ListViewChatAdapter extends BaseAdapter {
	
	public static interface MessageType{
		int MSG_RECE = 0; //收到的消息
		int MSG_SEND = 1; //发出的消息
	}
	
	private static final int MSG_TYPE_NUM = 2; //消息类型
	
	private Context 					context;//运行上下文
	private List<Chat> 				    listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private BitmapUtil 				bmpManager;
	
	static class ListItemView{				//自定义控件集合  
			public ImageView userface;
			public TextView content;  
		    public TextView date;  
		    public boolean isRec = true;
	 }
	
	/**
	 * 实例化Adapter
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewChatAdapter(Context context, List<Chat> data) {
		this.context = context;			
		this.listContainer = LayoutInflater.from(context);	//创建视图容器并设置上下文
		this.listItems = data;
		this.bmpManager = new BitmapUtil(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_round));
		this.bmpManager.setContext(context);
	}
	
	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return listItems.get(arg0);
	}

	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getViewTypeCount(){
		return MSG_TYPE_NUM;
	}
	
	@Override
	public int getItemViewType(int position){
		Chat chat = listItems.get(position);
		if(chat.getChatIsReceive()){
			return MessageType.MSG_RECE;
		}else{
			return MessageType.MSG_SEND;
		}
	}
	   
	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Chat chat = listItems.get(position);
		Chat cPre = null;
		if(position != 0){
			cPre = listItems.get(position-1);
		}
		boolean isReceive = chat.getChatIsReceive();
		boolean isShowTime = true;
		if(cPre != null ){
			long dis = chat.getChatPubTime().getTime()-cPre.getChatPubTime().getTime();
			if(dis < 300000){
				isShowTime = false;
			}
		}
		
		//自定义视图
		ListItemView  listItemView = null;
		
		if (convertView == null) {
			//获取list_item布局文件的视图
			if(isReceive){
				convertView = listContainer.inflate(R.layout.chat_item_left, null);
			}else{
				convertView = listContainer.inflate(R.layout.chat_item_right,null);
			}

			listItemView = new ListItemView();
			//获取控件对象
			listItemView.userface = (ImageView)convertView.findViewById(R.id.chat_item_userface);
			listItemView.content = (TextView)convertView.findViewById(R.id.chat_item_content);
			listItemView.date = (TextView)convertView.findViewById(R.id.chat_item_sendtime);
			listItemView.isRec = isReceive;
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}
		
		//设置文字和图片
		listItemView.content.setText(chat.getChatContent());
		listItemView.content.setTag(chat);//设置隐藏参数(实体类)
		if(isShowTime){
			listItemView.date.setText(DateUtil.friendlyTime(chat.getChatPubTime()));
			listItemView.date.setVisibility(View.VISIBLE);
		}else{
			listItemView.date.setVisibility(View.GONE);
		}
		
		String faceURL = chat.getChatFace();
		if(StringUtils.isEmpty(faceURL)){
			listItemView.userface.setImageResource(R.drawable.avatar_round);
		}else{
			bmpManager.loadRoundBitmap(faceURL, listItemView.userface);
		}
		
		return convertView;
	}
	
	private View.OnClickListener faceClickListener = new View.OnClickListener(){
		public void onClick(View v) {
			Chat chat = (Chat)v.getTag();
			UiHelper.showUserCenter(v.getContext(), chat.getChatAuthorId());
		}
	};
    
}