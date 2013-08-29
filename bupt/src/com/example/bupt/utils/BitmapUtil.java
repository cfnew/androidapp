package com.example.bupt.utils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.example.bupt.http.HttpUtils;
/**
 * 异步线程加载图片工具类
 * 使用说明：
 * BitmapUtil bmpUtil;
 * bmpUtil = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.loading));
 * bmpUtil.loadBitmap(imageURL, imageView);
 */
public class BitmapUtil {  
	  
    private static HashMap<String, SoftReference<Bitmap>> memcache;  
    private static ExecutorService pool;
    private static Map<ImageView, String> imageViews;  
    private Bitmap defaultBmp;
    private Context ctx;
    
    static {  
        memcache = new HashMap<String, SoftReference<Bitmap>>(); 
        pool = Executors.newFixedThreadPool(5);  //固定线程池
        imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    }  
    
    public BitmapUtil(Bitmap def) {
    	this.defaultBmp = def;
    }
    
    /**
     * 设置默认图片
     * @param bmp
     */
    public void setDefaultBmp(Bitmap bmp) { 
    	defaultBmp = bmp;  
    }   
    
    public void setContext(Context ctx){
    	this.ctx = ctx;
    }
  
    public Bitmap loadBitmap(String url){
    	Bitmap bm = this.getBitmapFromMemCache(url);
    	if(null != bm){
    		return bm;
    	}else{
    		return this.downloadBitmap(url, 0, 0);
    	}
    }
    
    /**
     * 获取方形图片
     * @param url
     * @param imageView
     */
    public void loadSquareBitmap(String url, ImageView imageView) {  
    	personalBitmap(url, imageView, 0, 0, false);
    }
	
    /**
     * 获取圆形图片
     * @param url
     * @param imageView
     */
    public void loadRoundBitmap(String url, ImageView imageView){
    	personalBitmap(url, imageView, 0, 0, true);
    }
    
    /**
     * 加载图片-可指定显示图片的高宽
     * @param url
     * @param imageView 失败后的默认图片
     * @param width
     * @param height
     * @param isRound 是否变换为圆形
     */
    private void personalBitmap(String url, ImageView imageView, int width, int height, boolean isRound) {  
        imageViews.put(imageView, url);
        Bitmap bitmap = getBitmapFromMemCache(url);
//        Bitmap round_bm;//裁剪出的圆形头像,h
//        Bitmap mix_bm;//拼接后的头像,h
//        Bitmap user_bm;//带遮罩的圆形头像,h
//        int distance=1000;//用户距离,h
   
        if (bitmap != null) {
			//显示缓存图片
        	//round_bm = roRoundBitmap(bitmap);//h
        	//mix_bm = getFinalBitmap(round_bm,distance);//h
        	//user_bm = roRoundBitmap(mix_bm);//h
        	//imageView.setImageBitmap(user_bm);
        	if(isRound){
        		imageView.setImageBitmap(roRoundBitmap(bitmap));
        	}else{
        		imageView.setImageBitmap(bitmap);
        	}
        } else {
        	//加载SD卡中的图片缓存
        	String filename = FileUtils.getFileName(url);
        	String filepath = imageView.getContext().getFilesDir() + File.separator + filename;
    		File file = new File(filepath);
    		if(file.exists()){
				//显示SD卡中的图片缓存
    			Bitmap bmp = ImageUtil.getBitmap(imageView.getContext(), filename);
//    			round_bm = roRoundBitmap(bmp);//h
//    			mix_bm = getFinalBitmap(round_bm,distance);//hh
//    			user_bm = roRoundBitmap(mix_bm);//h
//    			imageView.setImageBitmap(user_bm);//h
    			if(isRound){
            		imageView.setImageBitmap(roRoundBitmap(bmp));
            	}else{
            		imageView.setImageBitmap(bmp);
            	}
        	}else{
				//显示默认图片
        		imageView.setImageBitmap(defaultBmp);
        		//线程加载网络图片
        		getBitmapFromNet(url, imageView, width, height, isRound);
        	}
        }  
    }  
  
    /**
     * 从静态缓存中获取图片
     * @param url
     */
    private Bitmap getBitmapFromMemCache(String url) {  
    	Bitmap bitmap = null;
        if (memcache.containsKey(url)) {
            bitmap = memcache.get(url).get();  
        }
        return bitmap;  
    }  
    
    /**
     * 从网络中加载图片
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    private void getBitmapFromNet(final String url, final ImageView imageView, final int width, final int height, final boolean isRound) {  
    	 /* Create handler in UI thread. */  
        final Handler handler = new Handler() {  
            public void handleMessage(Message msg) {  
                String tag = imageViews.get(imageView);  
                if (tag != null && tag.equals(url)) {  
                    if (msg.obj != null) {  
                    	Bitmap bmFromNet = (Bitmap)msg.obj;
                    	if(isRound){
                    		imageView.setImageBitmap(roRoundBitmap(bmFromNet)); 
                    	}else{
                    		imageView.setImageBitmap(bmFromNet); 
                    	}
                        try {
                        	//向SD卡中写入图片缓存,保存在 /data/data/PACKAGE_NAME/files 目录下
							ImageUtil.saveImage(imageView.getContext(), FileUtils.getFileName(url), bmFromNet);
						} catch (IOException e) {
							e.printStackTrace();
						}
                    } 
                }  
            }  
        };  
  
        pool.execute(new Runnable() {   
            public void run() {  
                Message message = Message.obtain();  
                message.obj = downloadBitmap(url, width, height);  
                handler.sendMessage(message);  
            }
        });  
    } 
    
    private Bitmap downloadBitmap(String url, int width, int height){
    	Bitmap bitmap = null;
        try {
			bitmap = HttpUtils.getNetBitmap(ctx,url);
			if(width > 0 && height > 0) {
				//指定显示图片的高宽
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			} 
			//放入缓存
			memcache.put(url, new SoftReference<Bitmap>(bitmap));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return bitmap;
    }
  

    /**
     * 裁剪一个图片为圆形
     * @param bitmap
     * @return
     */
    public Bitmap roRoundBitmap(Bitmap bitmap){
    	int width = bitmap.getWidth();
    	int height = bitmap.getHeight();
    	float roundPx ;
    	float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
    	if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
	    } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
	    }
    	Bitmap output = Bitmap.createBitmap(width,height, Config.ARGB_8888);
    	Canvas canvas = new Canvas(output);
    	final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);
         
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
    /**
     * 设置带有用户距离的圆形用户头像
     * @param bm1 用户圆形头像
     * @param distance 用户距离
     */
//    public Bitmap getFinalBitmap(Bitmap bm1,int distance)
//    {
//    	//下方小弧形所需要的图片
//    	Drawable drawable = ctx.getResources().getDrawable(R.drawable.roundimage_down);
//    	BitmapDrawable bitmapDrawable=(BitmapDrawable)drawable;
//    	Bitmap bm=bitmapDrawable.getBitmap();
//    	
//    	Bitmap bm2=roRoundBitmap(bm);
//    	
//    	Bitmap bitmap=null;
//    	bitmap=Bitmap.createBitmap(bm1);    	
//    	Canvas canvas=new Canvas(bitmap);
//    	Paint paint=new Paint();
//    	
//    	paint.setColor(Color.TRANSPARENT);
//    	canvas.drawRect(0,0,bm1.getWidth(),bm1.getHeight(),paint);
//    	paint=new Paint();
//    	paint.setAlpha(100);
//    	canvas.drawBitmap(bm2,(bm1.getWidth()-bm2.getWidth())/2,(float) (bm1.getHeight()*0.75), paint);
//    	
//    	String string=distance+"米";
//    	float length=paint.measureText(string);
//    	//居中显示用户的距离
//    	canvas.drawText(string,(float) ((bitmap.getWidth()-length)/2),(float)(bitmap.getHeight()*0.9), paint);    	
//    	
//    	canvas.save(Canvas.ALL_SAVE_FLAG);
//    	canvas.restore();    	
//    	
//		return bitmap;    	
//    }
    
    
}