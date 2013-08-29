package com.example.bupt.share;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.ui.UiHelper;
import com.example.bupt.utils.DialogUtil;
import com.example.bupt.utils.ToastUtil;

public class IssueShareSelectActivity extends BaseActivity
{

    private Activity ac;
    private List<Map<String,String>> paopaoList = null;

    private Dialog loading;
    private Handler myHandler = null;
    private int status = -1;
    private String info = null;
    
    private SimpleAdapter sa = null;
    private ListView lv = null;
    
    //传给IssueShareAddContentActivity的三个参数
    private boolean flag = false;
    private String feed_id = null;
    private String feed_title = null;
    
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issue_share_select_paopao);
        ac = this;
        paopaoList = new ArrayList<Map<String,String>>();
                
        initTitle();
        initPaopaoList();
        
        myHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) 
            {
                loading.dismiss();
                if(msg.what == -1)
                {
                    if(info!=null)
                        ToastUtil.showMsg(ac, info);
                    else
                        ToastUtil.showMsg(ac, "网络连接异常");
                }
                else if(msg.what == 0)
                {
                    paopaoList.remove(0);
                    Map<String,String>  paopao = new HashMap<String,String>();
                    feed_id  = "对不起，您没有参加任何活动";
                    feed_title = "请参加更多活动";
                    paopao.put("feed_id",feed_id);
                    paopao.put("feed_title",feed_title);
                    paopaoList.add(0, paopao);
                }
                else
                {
                    paopaoList.remove(0);
                    flag = true;
                }
                if(sa!=null)
                    sa.notifyDataSetChanged();
            }  
        };
        
        new Thread( new LoadingPaoPaoList()).start();
    }

    //显示活动列表
    @SuppressLint("HandlerLeak")
    private void initPaopaoList()
    {
        loading=DialogUtil.getLoadingDialog(ac,"获取服务器数据中...");
        loading.show();
        
        Map<String,String>  paopao = new HashMap<String,String>();
        feed_id  = "正在加载...";
        feed_title = "请稍候";
        paopao.put("feed_id",feed_id);
        paopao.put("feed_title",feed_title);
        paopaoList.add(paopao);
        
        
        sa = new SimpleAdapter(this,paopaoList,
                R.layout.issue_share_select_paopao_list,
                new String[]{"feed_id","feed_title"},
                new int[]{R.id.issue_share_pappao_list_item1,R.id.issue_share_pappao_list_item2});
        lv = (ListView)findViewById(R.id.issue_share_paopao_listview);
        lv.setAdapter(sa);
        
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positon,long id) 
            {
                if(flag == false)
                    return;
                else
                {
                    feed_id = paopaoList.get(positon).get("feed_id");
                    feed_title = paopaoList.get(positon).get("feed_title");
                    UiHelper.showAddIssueShare(ac, feed_id, feed_title);
                }
            }   
        });
    }

    //异步下载活动列表线程
    class LoadingPaoPaoList implements Runnable
    {
        @Override
        public void run() 
        {
            final Message msg = new Message();
            //如果已经连接网络
            if(baseApp.isNetworkConnected())
            {
//                RequestParams params = new RequestParams();
//                params.put("uid","10");//BaseApp.getUid()+""
//                
//                HttpUtil.get(URLs.HOME_GET_TO_SHARE, params, new JsonHttpResponseHandler()
//                {
//                    public void onSuccess(JSONObject jsonObj) 
//                    {
//                        try
//                        {
//                            status = jsonObj.getInt("status");
//                            info = jsonObj.getString("info");
//                            if(status == 1)
//                            {
//                                JSONArray jArr= null;
//                                jArr = jsonObj.getJSONArray("data");
//                                JSONObject obj = null;
//                                int length = jArr.length();
//                                if(length > 0)
//                                {
//                                    for(int i = 0; i < length; i++)
//                                    {
//                                        obj = jArr.getJSONObject(i);
//                                        Map<String,String>  paopao = new HashMap<String,String>();
//                                        feed_id  = obj.getInt("feed_id")+"";
//                                        feed_title = obj.getString("feed_title");
//                                        paopao.put("feed_id",feed_id);
//                                        paopao.put("feed_title",feed_title);
//                                        paopaoList.add(paopao);
//                                    }
//                                }  
//                                msg.what = paopaoList.size();
//                                msg.obj = paopaoList;
//                                myHandler.sendMessage(msg); //发出消息
//                            }
//                            else
//                            {
//                                msg.what = -1;
//                                myHandler.sendMessage(msg); //发出消息
//                            }
//                        }
//                        catch(Exception e)
//                        {
//                            e.printStackTrace();
//                            msg.what = -1;
//                            msg.obj = e;
//                            myHandler.sendMessage(msg); //发出消息
//                        }
//                    }
//                 });
                }
            else
            {
                msg.what = -1;
                myHandler.sendMessage(msg);
            }
       }
    }

    
    //设置返回键
    private void initTitle() 
    {
        ImageButton reImb = (ImageButton)findViewById(R.id.issue_share_select_return);
        
        reImb.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) 
            {
                ac.finish();
            } 
        });
    }

}
