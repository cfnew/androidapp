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
import com.example.bupt.model.DetailsSculpture;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.StringUtils;

public class ListViewDetailSculpture extends MyBaseAdapter{
	private Context context; //上下文
	private List<DetailsSculpture> listItems; //数据集合

	private LayoutInflater inflater; //视图容器
	private BitmapUtil bitmapUtil;

	static class ListItemView{  //自定义控件集合
		public ImageView DetailsSculpture_iv;
		public ImageView posterFlag;
		public TextView uName;
	}

	/**
	 * 构造函数
	 * @param context
	 * @param listData
	 * @param resource
	 */
	public ListViewDetailSculpture(Context context, List<DetailsSculpture> listData){
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.listItems = listData;
		this.bitmapUtil = new BitmapUtil(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_square));
		this.bitmapUtil.setContext(context);
	}

	public List<DetailsSculpture> getListItems() {
		return listItems;
	}
	
	public void setListItems(List<DetailsSculpture> listItems) {
		this.listItems = listItems;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		//自定义视图
		ListItemView listItemView = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.paopao_detail_sculpture, null);
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.DetailsSculpture_iv = (ImageView)convertView.findViewById(R.id.paopao_details_iv_ds);
			listItemView.posterFlag = (ImageView)convertView.findViewById(R.id.paopao_details_iv_poster);
			//设置空间集到convertView
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView)convertView.getTag();
		}
		//设置文字和图片
		DetailsSculpture ds = listItems.get(position);
		String picUrl = ds.getAvatar_middle(); //活动参与者头像
		if(!StringUtils.isEmpty(picUrl)) {
			bitmapUtil.loadRoundBitmap(picUrl, listItemView.DetailsSculpture_iv);
			listItemView.DetailsSculpture_iv.setTag(ds.getAvatar_middle());
		}else{
			listItemView.DetailsSculpture_iv.setImageResource(R.drawable.avatar_round);
		}
		listItemView.DetailsSculpture_iv.setTag(ds); //设置附加数据
		//显示发布者标识
		int isposter = ds.getIsPoster();
		if(isposter == 1){
			listItemView.posterFlag.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	


}
