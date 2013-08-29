package com.example.bupt.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.model.Paopao;
import com.example.bupt.ui.UiHelper;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.DateUtil;
import com.example.bupt.utils.StringUtils;


public class ListViewPaopaoAdapter extends MyBaseAdapter {
	
	private Context context; //上下文
	private List<Paopao> listItems; //数据集合
	private LayoutInflater inflater; //视图容器
	private int itemViewResource; //视图资源
	private BitmapUtil bitmapUtil;
	
	//自定义控件集合
	static class ListItemView
	{
	    public TextView paopaoTimeFlag;
		public ImageView paopaoPic;
		public TextView paopaoTitle;
		public TextView paopaoLocation;
		public TextView paopaoLBS;
		public TextView paopaoStartTime;
		public TextView paopaoJoinNeedNum;
	}
	
	/**
	 * 构造函数
	 * @param context
	 * @param listData
	 * @param resource
	 */
	public ListViewPaopaoAdapter(Context context, List<Paopao> listData, int resource){
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.itemViewResource = resource;
		this.listItems = listData;
		this.bitmapUtil = new BitmapUtil(BitmapFactory.decodeResource(context.getResources(),R.drawable.widget_pp_def));
		this.bitmapUtil.setContext(context);
	}
	

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		//自定义视图
		ListItemView listItemView = null;
		if(convertView == null)
		{
			convertView = inflater.inflate(this.itemViewResource, null);
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.paopaoTimeFlag = (TextView)convertView.findViewById(R.id.paopao_listitem_time_flag);
			listItemView.paopaoPic = (ImageView)convertView.findViewById(R.id.paopao_listitem_pic);
			listItemView.paopaoTitle = (TextView)convertView.findViewById(R.id.paopao_listitem_title);
			listItemView.paopaoLocation = (TextView)convertView.findViewById(R.id.paopao_listitem_location);
			listItemView.paopaoLBS = (TextView)convertView.findViewById(R.id.paopao_listitem_LBS);
			listItemView.paopaoStartTime = (TextView)convertView.findViewById(R.id.paopao_listitem_start_time);
			listItemView.paopaoJoinNeedNum = (TextView)convertView.findViewById(R.id.paopao_listitem_join_need_num);
			//设置空间集到convertView
			convertView.setTag(listItemView);
		}
		else
		{
			listItemView = (ListItemView)convertView.getTag();
		}
		//设置文字和图片
		Paopao pp = listItems.get(position);
		
		//设置背景图片
        setItemBackGround(pp.getFeedStartTime(),convertView,listItemView.paopaoTimeFlag);
        
		listItemView.paopaoTitle.setText(pp.getFeedTitle());
		listItemView.paopaoTitle.setTag(pp);//设置隐藏参数 
		listItemView.paopaoJoinNeedNum.setText("("+pp.getFeedJoinNum()+"/"+pp.getFeedNeedNum()+")");
		listItemView.paopaoStartTime.setText(DateUtil.format(pp.getFeedStartTime(),DateUtil.FORMAT_MIDDLE_CN));
		listItemView.paopaoLocation.setText(pp.getFeedLocation());
		listItemView.paopaoLBS.setText("距离最近想inger 0.2km");
		String picUrl = pp.getFeedPic(); //活动图片
		if(!StringUtils.isEmpty(picUrl)) {
			bitmapUtil.loadSquareBitmap(picUrl, listItemView.paopaoPic);
			listItemView.paopaoPic.setOnClickListener(imageClickListener);
			listItemView.paopaoPic.setTag(pp.getFeedPicBig());
		}else{
			listItemView.paopaoPic.setImageResource(R.drawable.widget_pp_def);
		}
		return convertView;
	}
	
	//根据时间设置每一个Item的背景图
    private void setItemBackGround(Date feedStartTime, View convertView, TextView paopaoTimeFlag) {
		int dis = DateUtil.getDistance(feedStartTime);
		switch(dis){
		case -1:
			paopaoTimeFlag.setText("结束");
            paopaoTimeFlag.setBackgroundResource(R.drawable.paopao_time_flag_11);
            convertView.setBackgroundResource(R.drawable.paopao_item_1_14);
			break;
		case 0:
			paopaoTimeFlag.setText("今天");
            paopaoTimeFlag.setBackgroundResource(R.drawable.paopao_time_flag_03);
            convertView.setBackgroundResource(R.drawable.paopao_item_1_03);
			break;
		case 1:
			paopaoTimeFlag.setText("明天");
            paopaoTimeFlag.setBackgroundResource(R.drawable.paopao_time_flag_07);
            convertView.setBackgroundResource(R.drawable.paopao_item_1_07);
			break;
		case 2:
			paopaoTimeFlag.setText("后天");
            paopaoTimeFlag.setBackgroundResource(R.drawable.paopao_time_flag_09);
            convertView.setBackgroundResource(R.drawable.paopao_item_1_11);
			break;
		default:
			paopaoTimeFlag.setText("N天");
            paopaoTimeFlag.setBackgroundResource(R.drawable.paopao_time_flag_11);
            convertView.setBackgroundResource(R.drawable.paopao_item_1_14);
			break;
		}
    }

    private View.OnClickListener imageClickListener = new View.OnClickListener(){
		public void onClick(View v) {
			UiHelper.showImageDialog(v.getContext(), (String)v.getTag());
		}
	};

}
