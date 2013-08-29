package com.example.bupt.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.bupt.R;
import com.example.bupt.ui.UiHelper;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.StringUtils;

/**
 * 分享列表中多个图片的展示用的是GridView，
 * GridView里面的每个图片的tag保存了一个大图地址
 *
 */
public class ShareImagesAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<Map<String,String>> imageList;
	private BitmapUtil bitmapUtil; 
	
	public ShareImagesAdapter(Context context, List<Map<String,String>> images){
		this.mContext = context;
		this.imageList = images;
		this.bitmapUtil = new BitmapUtil(BitmapFactory.decodeResource(context.getResources(),R.drawable.widget_pp_def));
		this.bitmapUtil.setContext(mContext);
	}

	@Override
	public int getCount() {
		return imageList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = null;
		if(convertView == null){
			imageView = new ImageView(mContext);  
		    //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));  
		    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);  
		}else{
			imageView = (ImageView)convertView;
		}
		Map<String,String> imageItem = imageList.get(position);
		String smallPicUrl = imageItem.get("share_small");
		String bigPicUrl = imageItem.get("share_ori");
		if(!StringUtils.isEmpty(smallPicUrl)){
			bitmapUtil.loadSquareBitmap(smallPicUrl, imageView);
			imageView.setTag(bigPicUrl);
			imageView.setOnClickListener(imageClickListener);
			imageView.setVisibility(ImageView.VISIBLE);
		}else{
			imageView.setVisibility(ImageView.GONE);
		}
		return imageView;
	}
	
	private View.OnClickListener imageClickListener = new View.OnClickListener(){
		public void onClick(View v) {
			UiHelper.showImageDialog(v.getContext(), (String)v.getTag());
		}
	};
	
}
