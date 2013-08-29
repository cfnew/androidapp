package com.example.bupt.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.model.Timeline;
import com.example.bupt.utils.DateUtil;


public class ListViewTimelineAdapter extends BaseAdapter {
	
	private Context context; //上下文
	private List<Timeline> listItems; //数据集合
	private LayoutInflater inflater; //视图容器
	private int itemViewResource; //视图资源
	static class ListItemView{  //自定义控件集合
		public TextView paopaoState; //活动状态图标
		public TextView paopaoTitle;  //活动标题
		public TextView paopaoLocation; //地理位置
		public TextView paopaoStartTime; //活动的时间
		public LinearLayout layout;
	}
	
	/**
	 * 构造函数
	 * @param context
	 * @param listData
	 * @param resource
	 */
	public ListViewTimelineAdapter(Context context, List<Timeline> listData, int resource){
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.itemViewResource = resource;
		this.listItems = listData;
	}
	
	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if(convertView == null){
			convertView = this.inflater.inflate(this.itemViewResource, null);
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
		Timeline timeLine = listItems.get(position);
		listItemView.paopaoTitle.setText(timeLine.getFeedTitle()+"（"+timeLine.getJoinNum()+"/"+timeLine.getNeedNum()+"）");
		listItemView.paopaoTitle.setTag(timeLine); //设置隐藏参数
		listItemView.paopaoStartTime.setText(DateUtil.friendlyTime(timeLine.getFeedStartTime()));
		listItemView.paopaoLocation.setText(timeLine.getFeedLocation());
		if(timeLine.isOver()){
			listItemView.layout.setBackgroundResource(R.drawable.user_timeline_oringe);
		}
		
		return convertView;
	}

}
