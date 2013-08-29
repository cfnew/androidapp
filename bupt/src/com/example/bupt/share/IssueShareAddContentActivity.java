package com.example.bupt.share;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.base.BaseApp;
import com.example.bupt.utils.ToastUtil;

public class IssueShareAddContentActivity extends BaseActivity
{
    private String fid = null;
    private String feed_title = null;
    private String uid = null;
    private String attach_id = null;
    private String body = null;
    private int editFlag = 0;           //监听编辑框是否是初次输入。
    private int picFlag = 0;            //监听是否是第一次输入图片
    private EditText et = null;
    private Activity ac = null;
    
    //============================
    private final String IMAGE_TYPE = "image/*";
    private final int IMAGE_CODE = 0;   //这里的IMAGE_CODE是自己任意定义的
    
    private final long LIMIT_PIC_SIZE = 2*1024*1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issue_share_add_content);
        
        Intent get_data = this.getIntent();
        Bundle bundle = get_data.getExtras(); 
        fid = bundle.getString("feed_id");
        feed_title = bundle.getString("feed_title");
        uid = BaseApp.getUid()+"";
        ac = this;
        
        initAddShareReturn();
        initAddShareLable();
        initAddShareDescribe();
        initAddShareUploadPictue();
        initAddShareSubmit();
        
    }
    
    //设置活动描述
    private void initAddShareLable() 
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.issue_share_add_share_label_parent);
        TextView tv1 = (TextView)ll.findViewById(R.id.issue_share_add_share_label);
        TextView tv2 = (TextView)ll.findViewById(R.id.issue_share_add_share_title);
        
        tv1.setText("活动：");
        tv2.setText(""+feed_title);
    }

    //设置返回键
    private void initAddShareReturn() 
    {
        ImageButton imb = (ImageButton)findViewById(R.id.issue_share_add_return);
        
        imb.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                ac.finish();
            }
        });
        
    }

    //分享信息输入框
    private void initAddShareDescribe() 
    {
        LinearLayout rl = (LinearLayout)findViewById(R.id.issue_share_add_get_pic_parent);
        et = (EditText)rl.findViewById(R.id.issue_share_add_discribe);
        
        et.setText("在此添加描述...");
        
        et.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) 
            {
                if(editFlag == 0)
                {
                    et.setText("");
                    editFlag = 1;
                }
            }
        });
    }
    
    //提交分享图片按钮
    private void initAddShareUploadPictue() 
    {
        Button bt = (Button)findViewById(R.id.issue_share_add_get_pic);
        
        bt.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                if(picFlag == 0)
                {
                    Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                    getAlbum.setType(IMAGE_TYPE);
                    startActivityForResult(getAlbum, IMAGE_CODE);
                }
                else
                {
                    ToastUtil.showMsg(IssueShareAddContentActivity.this, "您已经上传了图片");
                }
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        if (resultCode != RESULT_CANCELED) 
        {
            switch (requestCode) 
            {
                case IMAGE_CODE:
                    if(data!=null)
                    {
                        try
                        {
                            getImageToView(data);
                            if(attach_id == null)
                            {
                                ToastUtil.showMsg(IssueShareAddContentActivity.this, "图片上传失败");
                            }
                        }
                        catch(Exception e)
                        {
                            ToastUtil.showMsg(IssueShareAddContentActivity.this, "图片上传失败");
                        }
                    }
                    break;
            }
            
        }
        
    }

    //保存用户选择的图片
    private void getImageToView(Intent data) 
    {
        Bitmap photo = null;
        Uri originalUri = data.getData();
        Log.i("path",originalUri.getPath());
        String picPath = printPath(originalUri);
        photo = getDiskBitmap(picPath) ; 
        if(baseApp.isNetworkConnected())
        {
            //把返回的图片存到用户的临时目录下，供上传
            File imageFile = cacheNewImage(photo);
            if(imageFile == null)
            {
                return;
            }
            else
            {
                if(imageFile.length()>LIMIT_PIC_SIZE)
                {
                    ToastUtil.showMsg(IssueShareAddContentActivity.this, "对不起，您上传的图片太大");
                    return;
                }
                    
                uploadNewImage(imageFile);
            }
        }
    }

    private Bitmap getDiskBitmap(String pathString) 
    {
        Bitmap bitmap = null;  
        try  
        {  
            File file = new File(pathString);  
            if(file.exists())  
            {  
                bitmap = BitmapFactory.decodeFile(pathString);  
            }  
        } catch (Exception e)  
        {   
            ToastUtil.showMsg(IssueShareAddContentActivity.this, "图片上传出错");
        }   
        return bitmap;  
    }

    private String printPath(Uri uri) 
    {
        Uri uri2 = Uri.parse("content://media"+uri.getPath());
        ContentResolver cr = this.getContentResolver();
        Cursor cursor = cr.query(uri2, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(1);  
    }

    /**
     * 根据Bitmap，缓存新图片
     * @param drawable
     * @return 
     */
    public File cacheNewImage(Bitmap bitmap)
    {
        if(uid==null)
        {
            return null;
        }
        String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+uid+"temp_issue.jpg";
        System.out.println(imagePath);
        //FileUtils.createPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WoWilling/cache/"+uid);
        File imageFile=new File(imagePath);
        if(!imageFile.exists())
        {
            try 
            {
                imageFile.createNewFile();
            } 
            catch (IOException e1) 
            {
                e1.printStackTrace();
            }
        }
        FileOutputStream fos=null;
        try 
        {
            fos=new FileOutputStream(imageFile);
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        boolean sucess = false;
        if(imageFile.length()<LIMIT_PIC_SIZE)
        {
            sucess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }
        else
        {
            sucess = bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
        }
        try 
        {
            fos.flush();
            fos.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        System.out.println(sucess);
        if(sucess==true)
        {
            return imageFile;
        }
        else 
        {
            return null;
        }
    }
    
    /**
     * 上传图片并获得id号
     * 
     */
    public void uploadNewImage(File imageFile)
    {
        System.out.println(imageFile.getAbsolutePath());
//        RequestParams params=new RequestParams();
//        params.put("uid", uid);
//        try 
//        {
//            params.put("File",imageFile);
//        } 
//        catch (FileNotFoundException e) 
//        {
//            e.printStackTrace();
//        }
//        Log.i("imageFile",imageFile.length()+"");
//        Log.i("UpLoad","pic");
//        HttpUtil.post(URLs.ISSUE_PIC, params, new JsonHttpResponseHandler()
//        {
//            @Override
//            public void onFailure(Throwable arg0, JSONObject arg1) 
//            {
//                Log.i("mouse!!!!!!!!!!!!!!!!!!","test1");
//                super.onFailure(arg0, arg1);
//                ToastUtil.showMsg(IssueShareAddContentActivity.this, "图片上传失败，请稍后再试");
//            }
//            
//            @Override
//            public void onSuccess(JSONObject arg0) 
//            {
//                Log.i("mouse!!!!!!!!!!!!!!!!!!","test2");
//                super.onSuccess(arg0);
//                
//                int status=0;
//                String info=null;
//                String attachId=null;
//                JSONObject data=null;
//                try 
//                {
//                    status=arg0.getInt("status");
//                    info=arg0.getString("info");
//                    Log.i("status", String.valueOf(status));
//                    Log.i("info", info);
//                } 
//                catch (JSONException e) 
//                {
//                    e.printStackTrace();
//                }
//                if(status == 1) 
//                {
//                    try 
//                    {
//                        data=arg0.getJSONObject("data");
//                        attachId=data.getString("attachid");
//                    } 
//                    catch (JSONException e) 
//                    {
//                        e.printStackTrace();
//                    }
//                    Log.i("attachid", attachId);
//                    attach_id= attachId;
//                    picFlag =1;
//                    ToastUtil.showMsg(IssueShareAddContentActivity.this, "图片上传成功");
//                }
//            }
//        });
    }


    //向服务器提交分享数据
    private void initAddShareSubmit() 
    {
        Button submitBt = (Button)findViewById(R.id.issue_share_add_submit_share);
        submitBt.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                if(editFlag==0)
                {
                    body = "";       
                }
                else
                {
                    body = et.getText().toString().trim();
                }
                
                if(attach_id == null)
                {
                    ToastUtil.showMsg(IssueShareAddContentActivity.this, "请上传图片");
                    return;
                }
                
//                RequestParams params=new RequestParams();
//                params.put("uid", uid);
//                params.put("fid", fid);
//                params.put("attach_id", attach_id);
//                params.put("body", body);
//                
//                HttpUtil.post(URLs.SHARE_DO_SHARE, params, new JsonHttpResponseHandler()
//                {
//                    @Override
//                    public void onSuccess(JSONObject response) 
//                    {
//                        int status = 0;
//                        String info = null;
//                        try 
//                        {
//                            status = response.getInt("status");
//                            info = response.getString("info");
//                        } 
//                        catch (JSONException e1) 
//                        {
//                            e1.printStackTrace();
//                        }
//                        
//                        if(status == 1)
//                        {
//                            picFlag = 0;                                                //可以重新上传图片了
//                            ToastUtil.showMsg(IssueShareAddContentActivity.this, "分享成功");
//                        }
//                        else
//                        {
//                            ToastUtil.showMsg(IssueShareAddContentActivity.this, info);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable e, JSONObject errorResponse) 
//                    {
//                        ToastUtil.showMsg(IssueShareAddContentActivity.this, "太遗憾了，分享失败");
//                    }
//                    
//                });
            }
        });
    }

}
