package com.example.bupt.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.base.ShareGridView;
import com.example.bupt.model.Share;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.DrawBitmapUtil;
import com.example.bupt.utils.StringUtils;


public class ListViewShareAdapter extends MyBaseAdapter {
	
	private Context context; //上下文
	private List<Share> listItems; //数据集合
	private LayoutInflater inflater; //视图容器
	private int itemViewResource; //视图资源
	private BitmapUtil bitmapUtil;
	
	static class ListItemView{  //自定义控件集合
		public ImageView paopaoFaces;
		public TextView paopaoTitle;
		public TextView paopaoLocation;
		public ShareGridView paopaoShareImages;
	}

	/**
	 * 构造函数
	 * @param context
	 * @param listData
	 * @param resource
	 */
	public ListViewShareAdapter(Context context, List<Share> listData, int resource){
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

    @SuppressWarnings("deprecation")
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//自定义视图
		ListItemView listItemView = null;
		if(convertView == null){
			convertView = inflater.inflate(this.itemViewResource, null);
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.paopaoFaces = (ImageView)convertView.findViewById(R.id.share_listitem_userfaces);
			listItemView.paopaoTitle = (TextView)convertView.findViewById(R.id.share_listitem_title);
			listItemView.paopaoLocation = (TextView)convertView.findViewById(R.id.share_listitem_location);
			listItemView.paopaoShareImages = (ShareGridView)convertView.findViewById(R.id.share_listitem_grid);
			//设置空间集到convertView
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView)convertView.getTag();
		}
		//设置文字和图片
		Share share = listItems.get(position);
		listItemView.paopaoTitle.setText(share.getPaopaoTitle());
		listItemView.paopaoTitle.setTag(share);//设置隐藏参数 
		listItemView.paopaoLocation.setText(share.getPaopaoLocation());
		listItemView.paopaoShareImages.setAdapter(new ShareImagesAdapter(context, share.getPaopaoShares()));
		//获取所有参与者的头像信息
		List<Map<String, String>> paopaoFacesPic = share.getPaopaoFaces();
		//参与者头像集
		List<Bitmap> paticipateFaces = new ArrayList<Bitmap>();
		
		for(int i = 0; i<paopaoFacesPic.size();i++){
    		String poster = paopaoFacesPic.get(i).get("uface");
    		if(!StringUtils.isEmpty(poster)){
    		    Bitmap bitmap =  bitmapUtil.loadBitmap(poster);
    		    if(bitmap != null){
    		        paticipateFaces.add(bitmap); 
    		    } else{
    		        paticipateFaces.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_square));
    		    }
    		}else{
    		    paticipateFaces.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_square));
    		}
		}
		if(paticipateFaces.size() != 0){
		    DrawBitmapUtil.createNewBitmap(paticipateFaces, 100, 100);
		    listItemView.paopaoFaces.setBackgroundDrawable(new BitmapDrawable(DrawBitmapUtil.createNewBitmap(paticipateFaces, 100, 100)));
		}else{
		    listItemView.paopaoFaces.setVisibility(View.GONE);
		}
		return convertView;	
	}
	
}
