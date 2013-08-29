package com.example.bupt.utils;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

public class DrawBitmapUtil 
{
    /**
     * 将缩放图片制成一张图
     * @Title: createBitmap
     * @param: @param src
     * @param: @param destWidth
     * @param: @param destHeigth  
     * @return: Bitmap   
     */
    public static Bitmap createNewBitmap(List<Bitmap> src, float destWidth, float destHeigth)
    {
        if(src == null)
        {
            return null;
        }
        if(src.size()==1)
        {
            return DrawBitmapUtil.zoomBitmap(src.get(0), destWidth, destHeigth); 
        }
        else if(src.size()<=4)
        {
            Bitmap newb = Bitmap.createBitmap((int)destWidth, (int)destHeigth, Config.ARGB_8888);// 创建一个新的和目标图片长度宽度一样的位图
            Canvas cv = new Canvas(newb);
            for(int i = 0 ; i!=src.size();i++)
            {
                Bitmap bm = zoomBitmap(src.get(i), destWidth/2, destHeigth/2);
                cv.drawBitmap(bm , 0+(destWidth/2)*(i%2), 0+(destHeigth/2)*(i/2), null);
            }
            cv.save(Canvas.ALL_SAVE_FLAG);                                            // 保存 
            cv.restore();                                                             // 存储 
            return newb; 
        }
        else if(src.size()<=9)
        {
            Bitmap newb = Bitmap.createBitmap((int)destWidth, (int)destHeigth, Config.ARGB_8888);// 创建一个新的和目标图片长度宽度一样的位图
            Canvas cv = new Canvas(newb);
            for(int i = 0 ; i!=src.size();i++)
            {
                Bitmap bm = zoomBitmap(src.get(i), destWidth/3, destHeigth/3);
                cv.drawBitmap(bm, 0+(destWidth/3)*(i%3), 0+(destHeigth/3)*(i/3), null);
            }
            cv.save(Canvas.ALL_SAVE_FLAG);                                            // 保存 
            cv.restore();                                                             // 存储 
            return newb; 
        }
        else
        {
            return DrawBitmapUtil.zoomBitmap(src.get(0), destWidth, destHeigth);
        }
    }
    /**
     * 缩放图片
     * @Title: zoomBitmap
     * @param: @param src
     * @param: @param destWidth
     * @param: @param destHeigth  
     * @return: Bitmap   
     */
    public static Bitmap zoomBitmap(Bitmap src, float destWidth, float destHeigth)
    {   
        if (src == null) 
        {  
            return null;
        }
        
        int w = src.getWidth();                        // 源文件的大小  
        int h = src.getHeight();  

        
        float scaleWidth = ((float) destWidth) / w;    // 宽度缩小比例  
        float scaleHeight = ((float) destHeigth) / h;  // 高度缩小比例  
        
        Matrix m = new Matrix();                       // 矩阵  
        m.postScale(scaleWidth, scaleHeight);          // 设置矩阵比例  
        Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0, w, h, m, true);         // 直接按照矩阵的比例把源文件画入进行  
        
        Log.i("Width",""+resizedBitmap.getWidth());
        Log.i("Heigth",""+resizedBitmap.getHeight());
        
        return resizedBitmap;  
    } 
}
