package com.example.bupt.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.http.HttpUtils;
import com.example.bupt.model.Remark;
import com.example.bupt.ui.UiHelper;
import com.example.bupt.utils.DateUtil;

public class ListViewShareRemarkAdapter extends BaseAdapter
{
	private Map<String,String> paopaoPic;

	private Context ctx = null;
	private int index = -1;
	private int pageNum = 0;
	private List<Remark> remarks = null;
	private LayoutInflater mInflater = null;
	//private BitmapUtil bitmap = new BitmapUtil();
	private int flag = -1;                /**
	 *-1表示还没有开始加载
	 * 0表示加载失败
	 * 1表示加载之后的评论数为0
	 * 2表示正常
	 * @param paopaoShares 
	 */


	public ListViewShareRemarkAdapter(int index,List<Remark> remarks,LayoutInflater mInflater, int flag, Map<String, String> paopaoPic,int pageNum ,Context ctx)
	{
		this.index = index;
		this.remarks = remarks;
		this.mInflater = mInflater;
		this.flag = flag;
		this.paopaoPic = paopaoPic;
		this.pageNum = pageNum;
		this.ctx = ctx;
	}
	@Override
	public int getCount() 
	{
		if(flag == -1||flag == 0||flag == 1)                   //没有加载、加载失败、评论为零三种情况
			return 3;
		else
		{
			if (remarks != null)
				return remarks.size()+2;
			else 
				return 2;
		}
	}
	public void setRemarks(List<Remark> remarks) {
		this.remarks = remarks;
	}
	@Override
	public Object getItem(int position) 
	{
		return null;
	}
	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View Item = null;
		if(position == 0)
		{
			Item = mInflater.inflate(R.layout.share_picture, null);
			ImageView picTv = (ImageView)Item.findViewById(R.id.share_pic);
			//bitmap.loadBitmap(paopaoPic.get("share_ori"),picTv);
		}
		else if(position == 1)
		{
			Item = mInflater.inflate(R.layout.share_lable_remark, null);
			TextView lable = (TextView)Item.findViewById(R.id.share_lable_remark);
			lable.setText("第"+(index+1)+"张/共"+pageNum+"张");
		}
		else if (position == 2)
		{
			//loading加载评论数据
			Item = mInflater.inflate(R.layout.share_remark_loading, null);
			TextView loadTv = (TextView)Item.findViewById(R.id.remark_loading);
			if(flag == -1)  
			{
				loadTv.setText("正在加载...");
			}
			else if(flag == 0)
			{
				loadTv.setText("加载失败...");
			}
			else if(flag == 1)
			{
				loadTv.setText("暂时没有评论...");
			}
			else
			{
				Item = loadRemarkListItem(position);
			}
		}
		else 
		{
			Item = loadRemarkListItem(position);
		}

		return Item;
	}
	/**
	 * loadRemarkListItem
	 */
	private View loadRemarkListItem(final int position) {
		View Item;
		Item = mInflater.inflate(R.layout.remark_item, null);
		ImageView picIv = (ImageView)Item.findViewById(R.id.remark_uface);
		picIv.setClickable(true);
		picIv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				UiHelper.showUserCenter(v.getContext(), remarks.get(position-2).getUid());
			}     
		});
		try
		{
			picIv.setBackgroundDrawable(new BitmapDrawable(HttpUtils.getNetBitmap(ctx,remarks.get(position-2).getUface())));
		}
		catch(Exception e)
		{
			picIv.setBackgroundResource(R.drawable.avatar_round);
		}
		TextView reTv = (TextView)Item.findViewById(R.id.share_remark);       
		TextView inTv = (TextView)Item.findViewById(R.id.share_remark_index);
		TextView timeTv = (TextView)Item.findViewById(R.id.share_remark_time);
		reTv.setClickable(false);
		inTv.setClickable(false);
		timeTv.setClickable(false);
		reTv.setText(remarks.get(position-2).getContent());
		if(remarks.get(position-2).getStorey() == 1)
		{
			inTv.setText("沙发");
		}
		else if(remarks.get(position-2).getStorey() == 2)
		{
			inTv.setText("板凳");
		}
		else
		{
			inTv.setText(remarks.get(position-2).getStorey()+"楼");
		}
		timeTv.setText(DateUtil.parseTimeStamp(remarks.get(position-2).getCtime()).toLocaleString());
		return Item;
	}


	public void setFlag(int flag) 
	{
		this.flag = flag;
	}
	@Override
	public int getItemViewType(int position) 
	{
		return super.getItemViewType(position);
	}
}
