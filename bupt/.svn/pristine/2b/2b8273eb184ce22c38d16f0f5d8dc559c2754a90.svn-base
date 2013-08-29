package com.example.bupt.slidingmenu;

import java.lang.reflect.Method;

import com.example.bupt.R;
import com.example.bupt.slidingmenu.CustomViewAbove.OnPageChangeListener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


public class SlidingMenu extends RelativeLayout {

    private static final String TAG = "SlidingMenu";

    public static final int SLIDING_WINDOW = 0;
    public static final int SLIDING_CONTENT = 1;
    private boolean mActionbarOverlay = false;

    /**
     * 涓簊etTouchModeAbove()鏂规硶璁剧疆涓�釜甯搁噺鍊硷紝鍏佽婊戝姩鑿滃崟閫氳繃婊戝姩灞忓箷鐨勮竟缂樿鎵撳紑 
     */
    public static final int TOUCHMODE_MARGIN = 0;

    /** 
     * 涓簊etTouchModeAbove()鏂规硶璁剧疆涓�釜甯搁噺鍊硷紝鍏佽婊戝姩鑿滃崟閫氳繃婊戝姩灞忓箷鐨勪换浣曞湴鏂硅鎵撳紑
     */
    public static final int TOUCHMODE_FULLSCREEN = 1;

    /** 
     * 涓簊etTouchModeAbove()鏂规硶璁剧疆涓�釜甯搁噺鍊硷紝涓嶅厑璁告粦鍔ㄨ彍鍗曢�杩囨粦鍔ㄥ睆骞曡鎵撳紑
     */
    public static final int TOUCHMODE_NONE = 2;

    /** 
     * 涓簊etMode()鏂规硶璁剧疆涓�釜甯搁噺鍊硷紝鎶婃粦鍔ㄨ彍鍗曟斁鍦ㄥ乏杈�
     */
    public static final int LEFT = 0;

    /** 
     * 涓簊etMode()鏂规硶璁剧疆涓�釜甯搁噺鍊硷紝鎶婃粦鍔ㄨ彍鍗曟斁鍦ㄥ彸杈�
     */
    public static final int RIGHT = 1;

    /** 
     * 涓簊etMode()鏂规硶璁剧疆涓�釜甯搁噺鍊硷紝鎶婃粦鍔ㄨ彍鍗曟斁鍦ㄥ乏鍙充袱杈�
     */
    public static final int LEFT_RIGHT = 2;

    /**
     * 瀹氫箟涓婃柟瑙嗗浘瀵硅薄
     */
    private CustomViewAbove mViewAbove;

    /**
     * 瀹氫箟涓嬫柟瑙嗗浘瀵硅薄
     */
    private CustomViewBehind mViewBehind;

    /**
     * 瀹氫箟婊戝姩鑿滃崟鎵撳紑鐨勭洃鍚璞�
     */
    private OnOpenListener mOpenListener;

    /**
     * 瀹氫箟婊戝姩鑿滃崟鍏抽棴鐨勭洃鍚璞�
     */
    private OnCloseListener mCloseListener;

    /**
     * 婊戝姩鑿滃崟鎵撳紑鏃剁殑鐩戝惉浜嬩欢
     */
    public interface OnOpenListener {   
        public void onOpen();
    }

    /**
     * 鐩戞祴婊戝姩鑿滃崟鏄惁宸茬粡鎵撳紑鐨勭洃鍚簨浠�
     */
    public interface OnOpenedListener {
        public void onOpened();
    }

    /**
     * 婊戝姩鑿滃崟鍏抽棴鏃剁殑鐩戝惉浜嬩欢
     */
    public interface OnCloseListener {
        public void onClose();
    }

    /**
     * 鐩戞祴婊戝姩鑿滃崟鏄惁宸茬粡鍏抽棴鐨勭洃鍚簨浠�
     */
    public interface OnClosedListener {
        public void onClosed();
    }

    /**
     * The Interface CanvasTransformer.
     */
    public interface CanvasTransformer {

        /**
         * Transform canvas.
         *
         * @param canvas the canvas
         * @param percentOpen the percent open
         */
        public void transformCanvas(Canvas canvas, float percentOpen);
    }

    /**
     * 鍒濆鍖栨粦鍔ㄨ彍鍗�
     *
     * @param context the associated Context
     */
    public SlidingMenu(Context context) {
        this(context, null);
    }

    /**
     * 鍒濆鍖栨粦鍔ㄨ彍鍗�
     *
     * @param activity the activity to attach slidingmenu
     * @param slideStyle the slidingmenu style
     */
    public SlidingMenu(Activity activity, int slideStyle) {
        this(activity, null);
        this.attachToActivity(activity, slideStyle);
    }

    /**
     * 鍒濆鍖栨粦鍔ㄨ彍鍗�
     *
     * @param context the associated Context
     * @param attrs the attrs
     */
    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 鍒濆鍖栨粦鍔ㄨ彍鍗�
     *
     * @param context the associated Context
     * @param attrs the attrs
     * @param defStyle the def style
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        LayoutParams behindParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mViewBehind = new CustomViewBehind(context);
        addView(mViewBehind, behindParams);
        LayoutParams aboveParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mViewAbove = new CustomViewAbove(context);
        addView(mViewAbove, aboveParams);
        // register the CustomViewBehind with the CustomViewAbove
        mViewAbove.setCustomViewBehind(mViewBehind);
        mViewBehind.setCustomViewAbove(mViewAbove);
        mViewAbove.setOnPageChangeListener(new OnPageChangeListener() {
            public static final int POSITION_OPEN = 0;
            public static final int POSITION_CLOSE = 1;

            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) { }

            public void onPageSelected(int position) {
                if (position == POSITION_OPEN && mOpenListener != null) {
                    mOpenListener.onOpen();
                } else if (position == POSITION_CLOSE && mCloseListener != null) {
                    mCloseListener.onClose();
                }
            }
        });

        // now style everything!
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        // set the above and behind views if defined in xml
        int mode = ta.getInt(R.styleable.SlidingMenu_mode, LEFT);
        setMode(mode);
        int viewAbove = ta.getResourceId(R.styleable.SlidingMenu_viewAbove, -1);
        if (viewAbove != -1) {
            setContent(viewAbove);
        } else {
            setContent(new FrameLayout(context));
        }
        int viewBehind = ta.getResourceId(R.styleable.SlidingMenu_viewBehind, -1);
        if (viewBehind != -1) {
            setMenu(viewBehind); 
        } else {
            setMenu(new FrameLayout(context));
        }
        int touchModeAbove = ta.getInt(R.styleable.SlidingMenu_touchModeAbove, TOUCHMODE_MARGIN);
        setTouchModeAbove(touchModeAbove);
        int touchModeBehind = ta.getInt(R.styleable.SlidingMenu_touchModeBehind, TOUCHMODE_MARGIN);
        setTouchModeBehind(touchModeBehind);

        int offsetBehind = (int) ta.getDimension(R.styleable.SlidingMenu_behindOffset, -1);
        int widthBehind = (int) ta.getDimension(R.styleable.SlidingMenu_behindWidth, -1);
        if (offsetBehind != -1 && widthBehind != -1)
            throw new IllegalStateException("Cannot set both behindOffset and behindWidth for a SlidingMenu");
        else if (offsetBehind != -1)
            setBehindOffset(offsetBehind);
        else if (widthBehind != -1)
            setBehindWidth(widthBehind);
        else
            setBehindOffset(0);
        float scrollOffsetBehind = ta.getFloat(R.styleable.SlidingMenu_behindScrollScale, 0.33f);
        setBehindScrollScale(scrollOffsetBehind);
        int shadowRes = ta.getResourceId(R.styleable.SlidingMenu_shadowDrawable, -1);
        if (shadowRes != -1) {
            setShadowDrawable(shadowRes);
        }
        int shadowWidth = (int) ta.getDimension(R.styleable.SlidingMenu_shadowWidth, 0);
        setShadowWidth(shadowWidth);
        boolean fadeEnabled = ta.getBoolean(R.styleable.SlidingMenu_fadeEnabled, true);
        setFadeEnabled(fadeEnabled);
        float fadeDeg = ta.getFloat(R.styleable.SlidingMenu_fadeDegree, 0.33f);
        setFadeDegree(fadeDeg);
        boolean selectorEnabled = ta.getBoolean(R.styleable.SlidingMenu_selectorEnabled, false);
        setSelectorEnabled(selectorEnabled);
        int selectorRes = ta.getResourceId(R.styleable.SlidingMenu_selectorDrawable, -1);
        if (selectorRes != -1)
            setSelectorDrawable(selectorRes);
        ta.recycle();
    }

    /**
     * 鎶婃粦鍔ㄨ彍鍗曟坊鍔犺繘鎵�湁鐨凙ctivity涓�
     * 
     * @param activity the Activity
     * @param slideStyle either SLIDING_CONTENT or SLIDING_WINDOW
     */
    public void attachToActivity(Activity activity, int slideStyle) {
        attachToActivity(activity, slideStyle, false);
    }

    /**
     * 鎶婃粦鍔ㄨ彍鍗曟坊鍔犺繘鎵�湁鐨凙ctivity涓�
     * 
     * @param activity the Activity
     * @param slideStyle either SLIDING_CONTENT or SLIDING_WINDOW
     * @param actionbarOverlay whether or not the ActionBar is overlaid
     */
    public void attachToActivity(Activity activity, int slideStyle, boolean actionbarOverlay) {
        if (slideStyle != SLIDING_WINDOW && slideStyle != SLIDING_CONTENT)
            throw new IllegalArgumentException("slideStyle must be either SLIDING_WINDOW or SLIDING_CONTENT");

        if (getParent() != null)
            throw new IllegalStateException("This SlidingMenu appears to already be attached");

        // get the window background
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowBackground});
        int background = a.getResourceId(0, 0);
        a.recycle();

        switch (slideStyle) {
        case SLIDING_WINDOW:
            mActionbarOverlay = false;
            ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
            ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
            // save ActionBar themes that have transparent assets
            decorChild.setBackgroundResource(background);
            decor.removeView(decorChild);
            decor.addView(this);
            setContent(decorChild);
            break;
        case SLIDING_CONTENT:
            mActionbarOverlay = actionbarOverlay;
            // take the above view out of
            ViewGroup contentParent = (ViewGroup)activity.findViewById(android.R.id.content);
            View content = contentParent.getChildAt(0);
            contentParent.removeView(content);
            contentParent.addView(this);
            setContent(content);
            // save people from having transparent backgrounds
            if (content.getBackground() == null)
                content.setBackgroundResource(background);
            break;
        }
    }

    /**
     * 浠庡竷灞�祫婧愭枃浠朵腑璁剧疆涓婃柟鐨勮鍥惧唴瀹癸紝杩欎釜甯冨眬浼氳濉厖娣诲姞鍒版墍鏈夊浘灞傜殑鏈�笂鏂�
     */
    public void setContent(int res) {
        setContent(LayoutInflater.from(getContext()).inflate(res, null));
    }

    /**
     * 閫氳繃View鏉ヨ缃笂鏂圭殑瑙嗗浘鍐呭
     */
    public void setContent(View view) {
        mViewAbove.setContent(view);
        showContent();
    }

    /**
     * 寰楀埌涓婃柟鐨勮鍥惧唴瀹�
     */
    public View getContent() {
        return mViewAbove.getContent();
    }

    /**
     * 浠庡竷灞�祫婧愭枃浠朵腑璁剧疆涓嬫柟锛堟粦鍔ㄨ彍鍗曪級鐨勮鍥惧唴瀹癸紝杩欎釜甯冨眬浼氳濉厖娣诲姞鍒版墍鏈夊浘灞傜殑鏈�笅鏂�
     * 
     * @param res the new content
     */
    public void setMenu(int res) {
        setMenu(LayoutInflater.from(getContext()).inflate(res, null));
    }

    /**
     * 寰楀埌涓嬫柟锛堟粦鍔ㄨ彍鍗曪級鐨勮鍥惧唴瀹�
     *
     * @param view The desired content to display.
     */
    public void setMenu(View v) {
        mViewBehind.setContent(v);
    }

    /**
     * 寰楀埌涓嬫柟锛堟粦鍔ㄨ彍鍗曪級鐨勮鍥惧唴瀹�
     */
    public View getMenu() {
        return mViewBehind.getContent();
    }

    /**
     * 浠庡竷灞�祫婧愭枃浠朵腑璁剧疆涓嬫柟锛堝彸杈规粦鍔ㄨ彍鍗曪級鐨勮鍥惧唴瀹癸紝杩欎釜甯冨眬浼氳濉厖娣诲姞鍒版墍鏈夊浘灞傜殑鏈�笅鏂�
     */
    public void setSecondaryMenu(int res) {
        setSecondaryMenu(LayoutInflater.from(getContext()).inflate(res, null));
    }

    /**
     * 璁剧疆涓嬫柟锛堝彸杈规粦鍔ㄨ彍鍗曪級鐨勮鍥惧唴瀹�
     */
    public void setSecondaryMenu(View v) {
        mViewBehind.setSecondaryContent(v);
    }

    /**
     * 寰楀埌涓嬫柟锛堝彸杈规粦鍔ㄨ彍鍗曪級鐨勮鍥惧唴瀹�
     */
    public View getSecondaryMenu() {
        return mViewBehind.getSecondaryContent();
    }

    /**
     * 璁剧疆涓婃柟瑙嗗浘鏄惁鑳藉婊戝姩
     */
    public void setSlidingEnabled(boolean b) {
        mViewAbove.setSlidingEnabled(b);
    }

    /**
     * 妫�祴涓婃柟瑙嗗浘鏄惁鑳藉婊戝姩
     */
    public boolean isSlidingEnabled() {
        return mViewAbove.isSlidingEnabled();
    }

    /**
     * 璁剧疆婊戝姩鑿滃崟鍑虹幇鍦ㄨ鍥句腑鐨勪綅缃�
     * 
     * @param mode must be either SlidingMenu.LEFT or SlidingMenu.RIGHT
     */
    public void setMode(int mode) {
        if (mode != LEFT && mode != RIGHT && mode != LEFT_RIGHT) {
            throw new IllegalStateException("SlidingMenu mode must be LEFT, RIGHT, or LEFT_RIGHT");
        }
        mViewBehind.setMode(mode);
    }

    /**
     * 寰楀埌婊戝姩鑿滃崟鍦ㄨ鍥句腑鐨勪綅缃�
     * 
     * @return the current mode, either SlidingMenu.LEFT or SlidingMenu.RIGHT
     */
    public int getMode() {
        return mViewBehind.getMode();
    }

    /**
     * 璁剧疆婊戝姩鑿滃崟鏄惁鏄潤鎬佹ā寮�涓嶈兘澶熶娇鐢ㄦ粦鍔ㄨ彍鍗�
     */
    public void setStatic(boolean b) {
        if (b) {
            setSlidingEnabled(false);
            mViewAbove.setCustomViewBehind(null);
            mViewAbove.setCurrentItem(1);
            //          mViewBehind.setCurrentItem(0);  
        } else {
            mViewAbove.setCurrentItem(1);
            //          mViewBehind.setCurrentItem(1);
            mViewAbove.setCustomViewBehind(mViewBehind);
            setSlidingEnabled(true);
        }
    }

    /**
     * 鎵撳紑婊戝姩鑿滃崟骞舵樉绀鸿彍鍗曠殑瑙嗗浘
     */
    public void showMenu() {
        showMenu(true);
    }

    /**
     * 鏄惁浣跨敤鍔ㄧ敾鏁堟灉鎵撳紑婊戝姩鑿滃崟骞舵樉绀鸿彍鍗曠殑瑙嗗浘
     */
    public void showMenu(boolean animate) {
        mViewAbove.setCurrentItem(0, animate);
    }

    /**
     * 鎵撳紑鍙宠竟鐨勬粦鍔ㄨ彍鍗曞苟鏄剧ず鑿滃崟鐨勮鍥�
     */
    public void showSecondaryMenu() {
        showSecondaryMenu(true);
    }

    /**
     * 鏄惁浣跨敤鍔ㄧ敾鏁堟灉鎵撳紑鍙宠竟鐨勬粦鍔ㄨ彍鍗曞苟鏄剧ず鑿滃崟鐨勮鍥�
     */
    public void showSecondaryMenu(boolean animate) {
        mViewAbove.setCurrentItem(2, animate);
    }

    /**
     * 鍏抽棴鑿滃崟骞舵樉绀轰笂鏂圭殑瑙嗗浘
     */
    public void showContent() {
        showContent(true);
    }

    /**
     * 鏄惁浣跨敤鍔ㄧ敾鏁堟灉鍏抽棴鑿滃崟骞舵樉绀轰笂鏂圭殑瑙嗗浘
     */
    public void showContent(boolean animate) {
        mViewAbove.setCurrentItem(1, animate);
    }

    /**
     * 婊戝姩鑿滃崟鐨勫紑鍏�
     */
    public void toggle() {
        toggle(true);
    }

    /**
     * 鏄惁浣跨敤鍔ㄧ敾鏁堟灉鎵撳紑鎴栧叧闂粦鍔ㄨ彍鍗�
     */
    public void toggle(boolean animate) {
        if (isMenuShowing()) {
            showContent(animate);
        } else {
            showMenu(animate);
        }
    }

    /**
     * 妫�祴婊戝姩鑿滃崟鏄惁姝ｅ湪琚樉绀�
     */
    public boolean isMenuShowing() {
        return mViewAbove.getCurrentItem() == 0 || mViewAbove.getCurrentItem() == 2;
    }
    
    /**
     * 妫�祴鍙宠竟婊戝姩鑿滃崟鏄惁姝ｅ湪琚樉绀�
     */
    public boolean isSecondaryMenuShowing() {
        return mViewAbove.getCurrentItem() == 2;
    }

    /**
     * 寰楀埌涓嬫柟瑙嗗浘鐨勫亸绉婚噺
     */
    public int getBehindOffset() {
        return ((RelativeLayout.LayoutParams)mViewBehind.getLayoutParams()).rightMargin;
    }

    /**
     * 鏍规嵁鍍忕礌鐨勫�鏉ヨ缃笅鏂硅鍥剧殑鍋忕Щ閲�
     *
     * @param i The margin, in pixels, on the right of the screen that the behind view scrolls to.
     */
    public void setBehindOffset(int i) {        
        mViewBehind.setWidthOffset(i);
    }

    /**
     * 鏍规嵁dimension璧勬簮鏂囦欢鐨処D鏉ヨ缃笅鏂硅鍥剧殑鍋忕Щ閲�
     *
     * @param resID The dimension resource id to be set as the behind offset.
     * The menu, when open, will leave this width margin on the right of the screen.
     */
    public void setBehindOffsetRes(int resID) {
        int i = (int) getContext().getResources().getDimension(resID);
        setBehindOffset(i);
    }

    /**
     * 鏍规嵁鍍忕礌鐨勫�鏉ヨ缃笂鏂硅鍥剧殑鍋忕Щ閲�
     *
     * @param i the new above offset, in pixels
     */
    public void setAboveOffset(int i) {
        mViewAbove.setAboveOffset(i);
    }

    /**
     * 鏍规嵁dimension璧勬簮鏂囦欢鐨処D鏉ヨ缃笂鏂硅鍥剧殑鍋忕Щ閲�
     *
     * @param resID The dimension resource id to be set as the above offset.
     */
    public void setAboveOffsetRes(int resID) {
        int i = (int) getContext().getResources().getDimension(resID);
        setAboveOffset(i);
    }

    /**
     * 鏍规嵁鍍忕礌鐨勫�鏉ヨ缃笅鏂硅鍥剧殑瀹藉害
     *
     * @param i The width the Sliding Menu will open to, in pixels
     */
    public void setBehindWidth(int i) {
        int width;
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        try {
            Class<?> cls = Display.class;
            Class<?>[] parameterTypes = {Point.class};
            Point parameter = new Point();
            Method method = cls.getMethod("getSize", parameterTypes);
            method.invoke(display, parameter);
            width = parameter.x;
        } catch (Exception e) {
            width = display.getWidth();
        }
        setBehindOffset(width-i);
    }

    /**
     * 鏍规嵁dimension璧勬簮鏂囦欢鐨処D鏉ヨ缃笅鏂硅鍥剧殑瀹藉害
     *
     * @param res The dimension resource id to be set as the behind width offset.
     * The menu, when open, will open this wide.
     */
    public void setBehindWidthRes(int res) {
        int i = (int) getContext().getResources().getDimension(res);
        setBehindWidth(i);
    }

    /**
     * 寰楀埌涓嬫柟瑙嗗浘鐨勫湪婊氬姩鏃剁殑缂╂斁姣斾緥
     *
     * @return The scale of the parallax scroll
     */
    public float getBehindScrollScale() {
        return mViewBehind.getScrollScale();
    }
    
    /**
     * 璁剧疆涓嬫柟瑙嗗浘鐨勫湪婊氬姩鏃剁殑缂╂斁姣斾緥
     *
     * @param f The scale of the parallax scroll (i.e. 1.0f scrolls 1 pixel for every
     * 1 pixel that the above view scrolls and 0.0f scrolls 0 pixels)
     */
    public void setBehindScrollScale(float f) {
        if (f < 0 && f > 1)
            throw new IllegalStateException("ScrollScale must be between 0 and 1");
        mViewBehind.setScrollScale(f);
    }
    
    /**
     * 寰楀埌杈圭紭瑙︽懜鐨勪复鐣屽�
     */
    public int getTouchmodeMarginThreshold() {
        return mViewBehind.getMarginThreshold();
    }
    
    /**
     * 褰撹Е鎽哥殑鐨勬ā寮忎负杈圭紭瑙︽懜鏃讹紝璁剧疆杈圭紭瑙︽懜鐨勪复鐣屽�
     */
    public void setTouchmodeMarginThreshold(int touchmodeMarginThreshold) {
        mViewBehind.setMarginThreshold(touchmodeMarginThreshold);
    }

    /**
     * Sets the behind canvas transformer.
     *
     * @param t the new behind canvas transformer
     */
    public void setBehindCanvasTransformer(CanvasTransformer t) {
        mViewBehind.setCanvasTransformer(t);
    }

    /**
     * 寰楀埌涓婃柟瑙嗗浘鐨勮Е鎽告ā寮忕殑鍊�
     */
    public int getTouchModeAbove() {
        return mViewAbove.getTouchMode();
    }


    /**
     * 璁剧疆涓婃柟瑙嗗浘鐨勮Е鎽告ā寮忕殑鍊�
     */
    public void setTouchModeAbove(int i) {
        if (i != TOUCHMODE_FULLSCREEN && i != TOUCHMODE_MARGIN
                && i != TOUCHMODE_NONE) {
            throw new IllegalStateException("TouchMode must be set to either" +
                    "TOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
        }
        mViewAbove.setTouchMode(i);
    }

    /**
     * 璁剧疆涓嬫柟瑙嗗浘鐨勮Е鎽告ā寮忕殑鍊�
     */
    public void setTouchModeBehind(int i) {
        if (i != TOUCHMODE_FULLSCREEN && i != TOUCHMODE_MARGIN
                && i != TOUCHMODE_NONE) {
            throw new IllegalStateException("TouchMode must be set to either" +
                    "TOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
        }
        mViewBehind.setTouchMode(i);
    }

    /**
     * 鏍规嵁璧勬簮鏂囦欢ID鏉ヨ缃粦鍔ㄨ彍鍗曠殑闃村奖鏁堟灉
     *
     * @param resId the resource ID of the new shadow drawable
     */
    public void setShadowDrawable(int resId) {
        setShadowDrawable(getContext().getResources().getDrawable(resId));
    }

    /**
     * 鏍规嵁Drawable鏉ヨ缃粦鍔ㄨ彍鍗曠殑闃村奖鏁堟灉
     *
     * @param d the new shadow drawable
     */
    public void setShadowDrawable(Drawable d) {
        mViewBehind.setShadowDrawable(d);
    }

    /**
     * 鏍规嵁璧勬簮鏂囦欢ID鏉ヨ缃彸杈规粦鍔ㄨ彍鍗曠殑闃村奖鏁堟灉
     *
     * @param resId the resource ID of the new shadow drawable
     */
    public void setSecondaryShadowDrawable(int resId) {
        setSecondaryShadowDrawable(getContext().getResources().getDrawable(resId));
    }

    /**
     * 鏍规嵁Drawable鏉ヨ缃粦鍔ㄨ彍鍗曠殑闃村奖鏁堟灉
     *
     * @param d the new shadow drawable
     */
    public void setSecondaryShadowDrawable(Drawable d) {
        mViewBehind.setSecondaryShadowDrawable(d);
    }

    /**
     * 鏍规嵁dimension璧勬簮鏂囦欢鐨処D鏉ヨ缃槾褰辩殑瀹藉害
     *
     * @param resId The dimension resource id to be set as the shadow width.
     */
    public void setShadowWidthRes(int resId) {
        setShadowWidth((int)getResources().getDimension(resId));
    }

    /**
     * 鏍规嵁鍍忕礌鐨勫�鏉ヨ缃槾褰辩殑瀹藉害
     *
     * @param pixels the new shadow width, in pixels
     */
    public void setShadowWidth(int pixels) {
        mViewBehind.setShadowWidth(pixels);
    }

    /**
     * 璁剧疆鏄惁鑳藉浣跨敤婊戝姩鑿滃崟娓愬叆娓愬嚭鐨勬晥鏋�
     **/
    public void setFadeEnabled(boolean b) {
        mViewBehind.setFadeEnabled(b);
    }

    /**
     * 璁剧疆娓愬叆娓愬嚭鏁堟灉鐨勫�
     *
     * @param f the new fade degree, between 0.0f and 1.0f
     */
    public void setFadeDegree(float f) {
        mViewBehind.setFadeDegree(f);
    }

    /**
     * Enables or disables whether the selector is drawn
     *
     * @param b true to draw the selector, false to not draw the selector
     */
    public void setSelectorEnabled(boolean b) {
        mViewBehind.setSelectorEnabled(true);
    }

    /**
     * Sets the selected view. The selector will be drawn here
     *
     * @param v the new selected view
     */
    public void setSelectedView(View v) {
        mViewBehind.setSelectedView(v);
    }

    /**
     * Sets the selector drawable.
     *
     * @param res a resource ID for the selector drawable
     */
    public void setSelectorDrawable(int res) {
        mViewBehind.setSelectorBitmap(BitmapFactory.decodeResource(getResources(), res));
    }

    /**
     * Sets the selector drawable.
     *
     * @param b the new selector bitmap
     */
    public void setSelectorBitmap(Bitmap b) {
        mViewBehind.setSelectorBitmap(b);
    }

    /**
     * 娣诲姞琚拷鐣ョ殑瑙嗗浘
     */
    public void addIgnoredView(View v) {
        mViewAbove.addIgnoredView(v);
    }

    /**
     * 绉婚櫎琚拷鐣ョ殑瑙嗗浘
     */
    public void removeIgnoredView(View v) {
        mViewAbove.removeIgnoredView(v);
    }

    /**
     * 褰撴ā寮忎负Fullscreen妯″紡鏃讹紝瑙︽懜灞忓箷娓呴櫎鎵�湁琚拷鐣ョ殑瑙嗗浘
     */
    public void clearIgnoredViews() {
        mViewAbove.clearIgnoredViews();
    }

    /**
     * 璁剧疆鎵撳紑鐩戝惉浜嬩欢锛屽綋婊戝姩鑿滃崟琚墦寮�椂璋冪敤
     */
    public void setOnOpenListener(OnOpenListener listener) {
        mOpenListener = listener;
    }

    /**
     * 璁剧疆鍏抽棴鐩戝惉浜嬩欢锛屽綋婊戝姩鑿滃崟琚叧闂椂璋冪敤
     */
    public void setOnCloseListener(OnCloseListener listener) {
        //mViewAbove.setOnCloseListener(listener);
        mCloseListener = listener;
    }

    /**
     * 璁剧疆鎵撳紑鐩戝惉浜嬩欢锛屽綋婊戝姩鑿滃崟琚墦寮�繃涔嬪悗璋冪敤
     */
    public void setOnOpenedListener(OnOpenedListener listener) {
        mViewAbove.setOnOpenedListener(listener);
    }

    /**
     * 璁剧疆鍏抽棴鐩戝惉浜嬩欢锛屽綋婊戝姩鑿滃崟琚叧闂繃涔嬪悗璋冪敤
     */
    public void setOnClosedListener(OnClosedListener listener) {
        mViewAbove.setOnClosedListener(listener);
    }

    /**
     * 鍔熻兘鎻忚堪锛氫繚瀛樼姸鎬佺殑绫伙紝缁ф壙鑷狟aseSavedState
     */
    public static class SavedState extends BaseSavedState {
        private final int mItem;

        public SavedState(Parcelable superState, int item) {
            super(superState);
            mItem = item;
        }

        private SavedState(Parcel in) {
            super(in);
            mItem = in.readInt();
        }

        public int getItem() {
            return mItem;
        }

        /* (non-Javadoc)
         * @see android.view.AbsSavedState#writeToParcel(android.os.Parcel, int)
         */
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mItem);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    /* (non-Javadoc)
     * @see android.view.View#onSaveInstanceState()
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState, mViewAbove.getCurrentItem());
        return ss;
    }

    /* (non-Javadoc)
     * @see android.view.View#onRestoreInstanceState(android.os.Parcelable)
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        mViewAbove.setCurrentItem(ss.getItem());
    }

    /* (non-Javadoc)
     * @see android.view.ViewGroup#fitSystemWindows(android.graphics.Rect)
     */
    @SuppressLint("NewApi")
    @Override
    protected boolean fitSystemWindows(Rect insets) {
        int leftPadding = insets.left;
        int rightPadding = insets.right;
        int topPadding = insets.top;
        int bottomPadding = insets.bottom;
        if (!mActionbarOverlay) {
            Log.v(TAG, "setting padding!");
            setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
        }
        return true;
    }
    
    private Handler mHandler = new Handler();

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void manageLayers(float percentOpen) {
        if (Build.VERSION.SDK_INT < 11) return;

        boolean layer = percentOpen > 0.0f && percentOpen < 1.0f;
        final int layerType = layer ? View.LAYER_TYPE_HARDWARE : View.LAYER_TYPE_NONE;

        if (layerType != getContent().getLayerType()) {
            mHandler.post(new Runnable() {
                public void run() {
                    Log.v(TAG, "changing layerType. hardware? " + (layerType == View.LAYER_TYPE_HARDWARE));
                    getContent().setLayerType(layerType, null);
                    getMenu().setLayerType(layerType, null);
                    if (getSecondaryMenu() != null) {
                        getSecondaryMenu().setLayerType(layerType, null);
                    }
                }
            });
        }
    }

}