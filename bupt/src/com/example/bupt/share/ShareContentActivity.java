package com.example.bupt.share;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.adapter.ListViewShareRemarkAdapter;
import com.example.bupt.adapter.ShareAwesomePagerAdapter;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.model.Remark;
import com.example.bupt.model.RemarkList;
import com.example.bupt.model.Share;
import com.example.bupt.utils.StringUtils;
import com.example.bupt.utils.ToastUtil;

public class ShareContentActivity extends BaseActivity
{
    private ViewPager awesomePager;
    private ShareAwesomePagerAdapter awesomeAdapter;
    private List<ListViewShareRemarkAdapter> baList = null;
    private List<View> mListViews;
    private LayoutInflater mInflater;
    private List<Remark> remarks = null;
    private Handler myHandler = null;
    private int flag = -1;
    private int pageNum = 0;
    private int currentPage = 0;
    private Share share;
    private List<Map<String,String>> paopaoShares;
    private InputMethodManager input = null;
    private Activity ac = null;
    private int currentIndex = 0;
     
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_content);
        
        //获取ShareItem中的信息。尤其是获取分享项目中的图片数目
        Intent get_data = this.getIntent();
        Bundle bundle = get_data.getExtras(); 
        share = (Share) bundle.get("share");
        currentIndex = (Integer) bundle.get("index");
        paopaoShares = share.getPaopaoShares();
        pageNum = paopaoShares.size();
        ac = this;
        
        baList = new ArrayList<ListViewShareRemarkAdapter>();
        mListViews = new ArrayList<View>();   
        mInflater = getLayoutInflater();
        
        //初始化各个页面，每个页面对应一幅图和该图片的评论
        initPage(pageNum);
        initEditRemark(this);
        
        //设置viewpager显示界面的格式
        if(mListViews!=null)
        {
            awesomeAdapter = new ShareAwesomePagerAdapter(mListViews);  
            awesomePager = (ViewPager) findViewById(R.id.awesomepager); 
            awesomePager.setAdapter(awesomeAdapter);  
            awesomePager.setCurrentItem(currentIndex);
            awesomePager.setOnPageChangeListener(new OnPageChangeListener()
            {
                @Override
                public void onPageScrollStateChanged(int arg0) {              
                }
    
                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {    
                }
    
                @Override
                public void onPageSelected(int arg0) 
                {
                     currentPage = arg0;
//                     Log.d("currentPage",currentPage+"");
//                     Log.d("Test",paopaoShares.get(currentPage).get("share_id")+"mouse");
                     new Thread(new LoadingRemark()).start();
                }   
            });
        }
        
         myHandler = new Handler()
         {
            @Override
            public void handleMessage(Message msg) 
            {            
                if(msg.what == -1)
                {
                    baList.get(currentPage).setFlag(0);
                }
                else if(msg.what ==0)
                {
                    baList.get(currentPage).setFlag(1);
                    remarks = ((RemarkList)msg.obj).getRemarkList();
                    baList.get(currentPage).setRemarks(remarks);
                }
                else
                {
                    baList.get(currentPage).setFlag(2);
                    remarks = ((RemarkList)msg.obj).getRemarkList(); 
                    baList.get(currentPage).setRemarks(remarks);
                }
                if(baList.size()==pageNum)
                {
                    baList.get(currentPage).notifyDataSetChanged();
                }
            }           
         };
         
         new Thread(new LoadingRemark()).start();
        
    }

    private void initEditRemark(ShareContentActivity shareContentActivity) 
    {
        final EditText remarkText = (EditText)findViewById(R.id.edit_remark);
        Button remarkButton = (Button)findViewById(R.id.submit_remark);
        
        remarkButton.setOnClickListener(new OnClickListener() 
        {

            @Override
            public void onClick(View v) 
            {
                input = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(StringUtils.isEmpty(remarkText.getText().toString()))
                {
                    ToastUtil.showMsg(ShareContentActivity.this, "输入不能为空");
                }
                else
                {
                    //初始化评
//                    RequestParams params = new RequestParams();
//                    //添加参数
//                    params.put("uid", BaseApp.getUid()+"");
//                    params.put("share_id", paopaoShares.get(currentPage).get("share_id"));
//                    params.put("share_uid", paopaoShares.get(currentPage).get("uid"));
//                    params.put("content",remarkText.getText().toString());
//                    Log.i("share_comment",BaseApp.getUid()+"===="+paopaoShares.get(currentPage).get("share_id")+"===="+paopaoShares.get(currentPage).get("uid"));
//                    HttpUtil.post(URLs.COMMENT_ADD_COMMENT, params, new JsonHttpResponseHandler(){
//
//                        @Override
//                        public void onSuccess(JSONObject response) 
//                        {
//                            int status = 0;
//                            try {
//                                status = response.getInt("status");
//                            } catch (JSONException e1) {
//                                e1.printStackTrace();
//                            }
//                            String info = null;
//                            try {
//                                info = response.getString("info");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            if(status == 1)
//                            {
//                                ToastUtil.showMsg(ShareContentActivity.this, "评论成功");
//                                remarkText.setText("");
//                                input.hideSoftInputFromWindow(remarkText.getWindowToken(), 0); 
//                            }
//                            else
//                            {
//                                ToastUtil.showMsg(ShareContentActivity.this, info);
//                                remarkText.setText("");
//                                input.hideSoftInputFromWindow(remarkText.getWindowToken(), 0); 
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Throwable e,JSONObject errorResponse) 
//                        {
//                            ToastUtil.showMsg(ShareContentActivity.this, "太遗憾了，评论失败");
//                        }             
//                    });
                    
                    new Thread(new LoadingRemark()).start();
                }
            }
            
        });
    }

    /**
     * 初始化pagerView
     * 参数为 页面个数
     */
    private void initPage(int pageNum) 
    {
        TextView tv = (TextView)findViewById(R.id.share_pic_discribe);
        tv.setText("活动分享相册");
        ImageButton ib = (ImageButton)findViewById(R.id.share_pic__return);
        ib.setOnClickListener(new OnClickListener() 
        {              
            @Override
            public void onClick(View v) 
            {
                ac.finish();
            }
        });
        for(int i = 0 ; i != pageNum ; i++)
        {    
            View pageView = mInflater.inflate(R.layout.share_content_page, null);
            ListView lv = (ListView)pageView.findViewById(R.id.share_listview); 
            initShareListView(lv,i);
            mListViews.add(pageView);
        }
    }
    
    /**
     * 异步下载评论线程
     */
    class LoadingRemark implements Runnable
    {
        @Override
        public  void run() 
        {
            final Message msg = new Message();
            try{
                //如果已经连接网络
                if(baseApp.isNetworkConnected()){
                    //生成参数
//                    RequestParams params = new RequestParams();
//                    params.put("sid", share.getPaopaoShares().get(currentPage).get("share_id"));
//                    
//                    HttpUtil.get(URLs.SHARE_GET_REMARK, params, new JsonHttpResponseHandler(){
//                        public void onSuccess(JSONObject jsonObj) {
//                            RemarkList reList = RemarkList.parse(jsonObj);
//                            msg.what = reList.getPageSize(); //msg.what保存了列表的数量
//                            msg.obj = reList;
//                            myHandler.sendMessage(msg); //发出消息
//                        }
//                    });

                }else
                {
                    msg.what = -1;
                }
            }catch(Exception e){
                e.printStackTrace();
                msg.what = -1;
                msg.obj = e;
                myHandler.sendMessage(msg); //发出消息
            }
        }
        
    }
   
    
    /**初始化分享详情列表
     * 参数i为页面的索引值
     */
    private void initShareListView(ListView lv, int i) 
    {
      //加载评论数据
       remarks = null;
       ListViewShareRemarkAdapter ba = new ListViewShareRemarkAdapter(i,remarks,mInflater,flag,paopaoShares.get(i),pageNum,this);
       baList.add(ba);
       lv.setAdapter(ba);
       lv.setClickable(false);
    }
}
