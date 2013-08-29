package com.example.bupt.share;

import android.view.View;
import android.widget.ImageView;

import com.example.bupt.R;

public class ViewCache {

    private View baseView;
    private ImageView imageView;

    public ViewCache(View baseView) {
        this.baseView = baseView;
    }

    public ImageView getImageView() {
        if (imageView == null) {
            imageView = (ImageView) baseView.findViewById(R.id.share_gird_item_pic);
        }
        return imageView;
    }

}