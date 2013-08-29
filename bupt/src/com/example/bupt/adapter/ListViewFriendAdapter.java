package com.example.bupt.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.model.Friend;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.StringUtils;

/**
 * 用户留言Adapter类
 */
public class ListViewFriendAdapter extends BaseAdapter implements SectionIndexer{
	private Context context;// 运行上下文
	private List<Friend> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源
	private BitmapUtil bitmapManager;

	static class ListItemView { // 自定义控件集合
		public TextView catalog; //目录
		public ImageView userface;
		public TextView username;
	}
	
	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewFriendAdapter(Context context, List<Friend> data, int resource) {
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.listItems = data;
		this.bitmapManager = new BitmapUtil(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_round));
		this.bitmapManager.setContext(context);
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Friend getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}
	
	@Override
	public String[] getSections() {
		return null;
	}

	@Override
	public int getPositionForSection(int section) {
		int len = listItems.size();
		for(int i=0; i<len; i++){
			String first = listItems.get(i).getFirstChar();
			char f = first.toUpperCase().charAt(0);
			if(f == section){
				return i;
			}
		}
		return -1;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if(convertView == null){
			convertView = listContainer.inflate(this.itemViewResource, null);
			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.catalog = (TextView) convertView.findViewById(R.id.friend_item_catalog);
			listItemView.userface = (ImageView) convertView.findViewById(R.id.friend_item_userface);
			listItemView.username = (TextView) convertView.findViewById(R.id.friend_item_username);
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView)convertView.getTag();
		}
		Friend friend = listItems.get(position);
		String curChar = friend.getFirstChar();
		if(position == 0){
			listItemView.catalog.setVisibility(View.VISIBLE);
			listItemView.catalog.setText(curChar);
		}else{
			Friend friendPre = listItems.get(position - 1);
			String preChar = friendPre.getFirstChar();
			if(curChar.equals(preChar)){
				listItemView.catalog.setVisibility(View.GONE);
			}else{
				listItemView.catalog.setVisibility(View.VISIBLE);
				listItemView.catalog.setText(curChar);
			}
		}
		listItemView.username.setText(friend.getFriendName());
		listItemView.username.setTag(friend);
		String faceUrl = friend.getFace();
		if(!StringUtils.isEmpty(faceUrl)){
			bitmapManager.loadRoundBitmap(faceUrl, listItemView.userface);
		}else{
			listItemView.userface.setImageResource(R.drawable.avatar_round);
		}
		return convertView;
	}
	
}