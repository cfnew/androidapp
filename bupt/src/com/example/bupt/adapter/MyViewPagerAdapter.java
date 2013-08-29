package com.example.bupt.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @ClassName:     MyViewPagerAdapter
 * @Description:   给好友界面设置adapter
 * @author:        mouse
 * @date:          2013-6-25 下午2:49:01
 *
 */
public class MyViewPagerAdapter extends PagerAdapter 
{
    private List<View> mListViews;
    
    public MyViewPagerAdapter(List<View> mListViews) 
    {
        this.mListViews = mListViews;
    }
    
    @Override
    public int getCount() 
    {
        return mListViews == null ? 0 : mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) 
    {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) 
    {
        container.removeView(mListViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) 
    {
        container.addView(mListViews.get(position),0);
        return mListViews.get(position);
    }

}
