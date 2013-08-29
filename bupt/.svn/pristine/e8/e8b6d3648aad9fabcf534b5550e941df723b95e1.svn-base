package com.example.bupt.share;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.model.Share;

public class SharePictureContentActivity extends BaseActivity
{
    private TextView sharePicture;
    private ImageButton deleteButton;
    private ImageView sharePerson;
    private TextView remark;
    private TextView copy;
    private TextView start;
    
    private Share share;
    private int currentIndex;
    
    private int width;
    private int height;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_picture_content_layout);
        
        Intent get_data = this.getIntent();
        Bundle bundle = get_data.getExtras(); 
        share = (Share) bundle.get("share");
        currentIndex = (Integer) bundle.get("index");
        
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        width = wm.getDefaultDisplay().getWidth();//屏幕宽度

        height = wm.getDefaultDisplay().getHeight();//屏幕高度

        
        initPicturePage();
    }

    private void initPicturePage() 
    {
        sharePicture = (TextView)findViewById(R.id.share_picture);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) sharePicture.getLayoutParams();    // 取控件aaa当前的布局参数
        linearParams.height = height - 150;                                                                     // 当控件的高强制设成365象素
        sharePicture.setLayoutParams(linearParams);                                                             // 使设置好的布局参数应用到控件aaa 
        sharePicture.setBackgroundResource(R.drawable.pic);
        
        remark = (TextView)findViewById(R.id.share_picture_remark);
        LinearLayout.LayoutParams p1 = (LinearLayout.LayoutParams) remark.getLayoutParams();    // 取控件aaa当前的布局参数
        p1.height = 100;        // 当控件的高强制设成365象素
        p1.width = width/3;
        remark.setLayoutParams(p1); // 使设置好的布局参数应用到控件aaa
        
        copy = (TextView)findViewById(R.id.share_picture_copy);
        LinearLayout.LayoutParams p2 = (LinearLayout.LayoutParams) copy.getLayoutParams();    // 取控件aaa当前的布局参数
        p2.height = 100;        // 当控件的高强制设成365象素
        p2.width = width/3;
        copy.setLayoutParams(p2); // 使设置好的布局参数应用到控件aaa
        
        start = (TextView)findViewById(R.id.share_picture_start);
        LinearLayout.LayoutParams p3 = (LinearLayout.LayoutParams) start.getLayoutParams();    // 取控件aaa当前的布局参数
        p3.height = 100;        // 当控件的高强制设成365象素
        p3.width = width/3;
        start.setLayoutParams(p3); // 使设置好的布局参数应用到控件aaa
        
        remark.setOnClickListener(new OnClickListener() 
        {  
            @Override
            public void onClick(View v) 
            {
                
            }
        });
        
        copy.setOnClickListener(new OnClickListener() 
        {  
            @Override
            public void onClick(View v) 
            {
                
            }
        });
        
        start.setOnClickListener(new OnClickListener() 
        {  
            @Override
            public void onClick(View v) 
            {
                
            }
        });
    }
}
