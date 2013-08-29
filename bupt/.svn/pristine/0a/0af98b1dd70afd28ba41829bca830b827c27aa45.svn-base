package com.example.bupt.share;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.bupt.R;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.StringUtils;

public class ImageAndTextListAdapter extends ArrayAdapter<ImageAndText> {

		private Context context; //上下文
        private BitmapUtil bitmapUtil;
        public ImageAndTextListAdapter(Activity activity,Context context, List<ImageAndText> imageAndTexts, GridView gridView1) {
            super(activity, 0, imageAndTexts);
            this.context = context;
            this.bitmapUtil = new BitmapUtil(BitmapFactory.decodeResource(context.getResources(),R.drawable.widget_pp_def));
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Activity activity = (Activity) getContext();

            // Inflate the views from XML
            View rowView = convertView;
            ViewCache viewCache;
            if (rowView == null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                rowView = inflater.inflate(R.layout.share_gird_view_item, null);
                viewCache = new ViewCache(rowView);
                rowView.setTag(viewCache);
            } else {
                viewCache = (ViewCache) rowView.getTag();
            }
            ImageAndText imageAndText = getItem(position);

            // Load the image and set it on the ImageView
            String imageUrl = imageAndText.getImageUrl();
            
            if(!StringUtils.isEmpty(imageUrl)) {
    			bitmapUtil.loadSquareBitmap(imageUrl, viewCache.getImageView());
    			viewCache.getImageView().setTag(imageUrl);
    			viewCache.getImageView().setVisibility(ImageView.VISIBLE);
    		}else{
    			viewCache.getImageView().setVisibility(ImageView.GONE);
    		}

            
            return rowView;
        }

}