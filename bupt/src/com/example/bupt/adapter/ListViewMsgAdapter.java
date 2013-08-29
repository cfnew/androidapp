package com.example.bupt.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.model.Msg;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.DateUtil;
import com.example.bupt.utils.StringUtils;

/**
 * 用户留言Adapter类
 */
public class ListViewMsgAdapter extends MyBaseAdapter {
	private Context context;// 运行上下文
	private List<Msg> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源
	private BitmapUtil bitmapUtil;

	static class ListItemView { // 自定义控件集合
		public ImageView userface;
		public TextView username;
		public TextView date;
		public TextView content;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewMsgAdapter(Context context, List<Msg> data, int resource) {
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.listItems = data;
		this.bitmapUtil = new BitmapUtil(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_round));
		this.bitmapUtil.setContext(context);
	}

	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// 自定义视图
		ListItemView listItemView = null;
		if (convertView == null) {
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);

			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.userface = (ImageView) convertView.findViewById(R.id.message_listitem_userface);
			listItemView.username = (TextView) convertView.findViewById(R.id.message_listitem_username);
			listItemView.date = (TextView) convertView.findViewById(R.id.message_listitem_date);
			listItemView.content = (TextView) convertView.findViewById(R.id.message_listitem_content);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置文字和图片
		Msg msg = listItems.get(position);
		listItemView.username.setText(msg.getFromName());
		listItemView.username.setTag(msg);// 设置隐藏参数(实体类)
		listItemView.date.setText(DateUtil.friendlyTime(msg.getCtime()));
		listItemView.content.setText(msg.getContent());
		String faceURL = msg.getFromFace();
		if (StringUtils.isEmpty(faceURL)) {
			listItemView.userface.setImageResource(R.drawable.avatar_round);
		} else {
			bitmapUtil.loadRoundBitmap(faceURL, listItemView.userface);
		}
		return convertView;
	}

}