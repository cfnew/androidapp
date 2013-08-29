package com.example.bupt.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.model.DetailsSculpture;
import com.example.bupt.model.Friend;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.StringUtils;

public class ListViewIssueFriendAdd extends MyBaseAdapter{
	private Context context; //上下文
	private List<Friend> listItems; //数据集合

	private LayoutInflater inflater; //视图容器
	private int itemViewResource; //视图资源
	private BitmapUtil bitmapUtil;
	private ListViewDetailSculpture lvDetails_f;
	DetailsSculpture ds;

	//获得数据
	ArrayList<Integer> lvFriendData_get = null;
	int status = 0;


	static class ListItemViewFriend{  //自定义控件集合
		public ImageView usrpic;
		public TextView uName;
		public CheckBox cbBoc;
	}

	/**
	 * 构造函数
	 * @param context
	 * @param listData
	 * @param resource
	 * @param status 
	 * @param lvFriendData_get 
	 */
	public ListViewIssueFriendAdd(Context context, List<Friend> listData, int resource,ListViewDetailSculpture lvDetails_to_change, ArrayList<Integer> lvFriendData_get, int status){
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.itemViewResource = resource;
		this.listItems = listData;
		this.lvDetails_f = lvDetails_to_change;
		this.lvFriendData_get = lvFriendData_get;
		this.status = status;
		this.bitmapUtil = new BitmapUtil(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_round));
		this.bitmapUtil.setContext(context);
	}



	public List<Friend> getListItems() {
		return listItems;
	}

	public void setListItems(List<Friend> listItems) {
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
		boolean flag = false;
		//自定义视图
		ListItemViewFriend listItemView = null;
		if(convertView == null){
			convertView = inflater.inflate(this.itemViewResource, null);
			listItemView = new ListItemViewFriend();
			//获取控件对象

			listItemView.usrpic = (ImageView) convertView.findViewById(R.id.paopao_issue_adf_iv_pic);
			listItemView.uName = (TextView) convertView.findViewById(R.id.paopao_issue_adf_tv_name);
			listItemView.cbBoc = (CheckBox) convertView.findViewById(R.id.paopao_issue_adf_cb_add);
			//设置空间集到convertView
			convertView.setTag(listItemView);
			Log.i("aaaaaa",String.valueOf(position));
			flag = true;
		}else{
			Log.i("bbbbbb",String.valueOf(position));
			listItemView = (ListItemViewFriend)convertView.getTag();
		}
		//设置文字和图片
		final Friend fd = listItems.get(position);
		String picUrl = fd.getFace(); //活动图片
		if(!StringUtils.isEmpty(picUrl)) {
			bitmapUtil.loadRoundBitmap(picUrl, listItemView.usrpic);
			listItemView.usrpic.setTag(picUrl);
			listItemView.usrpic.setVisibility(ImageView.VISIBLE);
		}else{
			listItemView.usrpic.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.header));
			listItemView.usrpic.setVisibility(ImageView.VISIBLE);
		}
		listItemView.uName.setText(fd.getFriendName());
		listItemView.cbBoc.setOnCheckedChangeListener(new OnCheckedChangeListener() {                        

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(true == isChecked){
					if(find_my_ds(fd.getFriendId(),lvDetails_f) == -1)
					{
						ds = new DetailsSculpture();
						ds.setUid(fd.getFriendId());
						ds.setAvatar_middle(fd.getFace());
						lvDetails_f.getListItems().add(0, ds);
						lvDetails_f.notifyDataSetChanged();
					}
				}
				if(false == isChecked){
					int position = find_my_ds(fd.getFriendId(),lvDetails_f);
					if(position != -1){
						try{
							lvDetails_f.getListItems().remove(position);
						}catch (Exception e){
							e.getStackTrace();
						}
						lvDetails_f.notifyDataSetChanged();
					}
				}
			}        
		});


		if(status == 1 && flag == true){
			if(is_in_ArrayList(fd.getFriendId(),lvFriendData_get) == true) {
				listItemView.cbBoc.setChecked(true);
			}
			if((position+1) == listItems.size()) status = 0;
		}

		return convertView;
	}

	private int find_my_ds(int targetUID,ListViewDetailSculpture lvDetails_f_target){
		int position = -1;
		int index = 0;
		for(index = 0;  index != lvDetails_f_target.getListItems().size() ; index++){
			if( lvDetails_f_target.getListItems().get(index).getUid() == targetUID) 
				position = index;
		}
		Log.i("targetID",String.valueOf(targetUID));
		Log.i("index",String.valueOf(index));
		Log.i("position",String.valueOf(position));
		return position;
	}

	private boolean is_in_ArrayList(int targetUID,ArrayList<Integer> lvFriendData_get){
		int index = 0;
		for(index = 0;index != lvFriendData_get.size() ; index++){
			Log.i("is_in_ArrayList",String.valueOf(targetUID)+"    "+String.valueOf(lvFriendData_get.get(index)));
			if(targetUID == lvFriendData_get.get(index)) return true;
		}
		return false;

	}

}
