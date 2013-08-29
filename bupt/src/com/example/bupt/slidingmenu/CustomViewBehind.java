package com.example.bupt.slidingmenu;

import com.example.bupt.R;
import com.example.bupt.slidingmenu.SlidingMenu.CanvasTransformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;



public class CustomViewBehind extends ViewGroup {

    private static final String TAG = "CustomViewBehind";

    //杈圭紭婊戝姩鐨勪复鐣屽�
    private static final int MARGIN_THRESHOLD = 48; // dips
    
    //鍒濆鍖栬Е鎽哥殑妯″紡
    private int mTouchMode = SlidingMenu.TOUCHMODE_MARGIN;

    //瀹氫箟涓婃柟瑙嗗浘
    private CustomViewAbove mViewAbove;

    //瀹氫箟鍐呭瑙嗗浘
    private View mContent;
    private View mSecondaryContent;
    
    //瀹氫箟婊戝姩杈圭紭鐨勪复鐣屽�
    private int mMarginThreshold;
    
    //瀹藉害鐨勫亸绉婚噺
    private int mWidthOffset;
    
    private CanvasTransformer mTransformer;
    
    //鏄惁鑳藉浣跨敤瀛愯鍥�
    private boolean mChildrenEnabled;

    public CustomViewBehind(Context context) {
        this(context, null);
    }

    public CustomViewBehind(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMarginThreshold = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                MARGIN_THRESHOLD, getResources().getDisplayMetrics());
    }

    public void setCustomViewAbove(CustomViewAbove customViewAbove) {
        mViewAbove = customViewAbove;
    }

    public void setCanvasTransformer(CanvasTransformer t) {
        mTransformer = t;
    }

    /**
     * 璁剧疆瀹藉害鐨勫亸绉婚噺
     */
    public void setWidthOffset(int i) {
        mWidthOffset = i;
        requestLayout();
    }
    
    /**
     * 璁剧疆杈圭紭婊戝姩鐨勪复鐣屽�
     */
    public void setMarginThreshold(int marginThreshold) {
        mMarginThreshold = marginThreshold;
    }
    
    /**
     * 寰楀埌杈圭紭婊戝姩鐨勪复鐣屽�
     */
    public int getMarginThreshold() {
        return mMarginThreshold;
    }

    /**
     * 寰楀埌瑙嗗浘鐨勫搴�
     */
    public int getBehindWidth() {
        return mContent.getWidth();
    }

    /**
     * 璁剧疆瑙嗗浘鐨勫唴瀹�
     */
    public void setContent(View v) {
        if (mContent != null)
            removeView(mContent);
        mContent = v;
        addView(mContent);
    }

    /**
     * 寰楀埌瑙嗗浘鐨勫唴瀹�
     */
    public View getContent() {
        return mContent;
    }

    /**
     * 璁剧疆鍙宠竟婊戝姩鑿滃崟鐨勫唴瀹癸紝褰撴ā寮忚缃负LEFT_RIGHT妯″紡鏃�
     */
    public void setSecondaryContent(View v) {
        if (mSecondaryContent != null)
            removeView(mSecondaryContent);
        mSecondaryContent = v;
        addView(mSecondaryContent);
    }

    /**
     * 寰楀埌鍙宠竟婊戝姩鑿滃崟鐨勫唴瀹�
     */
    public View getSecondaryContent() {
        return mSecondaryContent;
    }

    /**
     * 璁剧疆鏄惁鑳藉浣跨敤瀛愯鍥�
     */
    public void setChildrenEnabled(boolean enabled) {
        mChildrenEnabled = enabled;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        if (mTransformer != null)
            invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return !mChildrenEnabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return !mChildrenEnabled;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mTransformer != null) {
            canvas.save();
            mTransformer.transformCanvas(canvas, mViewAbove.getPercentOpen());
            super.dispatchDraw(canvas);
            canvas.restore();
        } else
            super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = r - l;
        final int height = b - t;
        mContent.layout(0, 0, width-mWidthOffset, height);
        if (mSecondaryContent != null)
            mSecondaryContent.layout(0, 0, width-mWidthOffset, height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
        final int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, width-mWidthOffset);
        final int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, height);
        mContent.measure(contentWidth, contentHeight);
        if (mSecondaryContent != null)
            mSecondaryContent.measure(contentWidth, contentHeight);
    }

    //瀹氫箟妯″紡鐨勫�
    private int mMode;
    
    //鏄惁鑳藉浣跨敤娓愬叆娓愬嚭鏁堟灉
    private boolean mFadeEnabled;
    
    //瀹氫箟娓愬叆娓愬嚭鐨勫�
    private float mFadeDegree;
    
    //瀹氫箟娓愬叆娓愬嚭鏁堟灉鐢荤瑪
    private final Paint mFadePaint = new Paint();
    
    //瀹氫箟婊戝姩缂╂斁鐨勫�
    private float mScrollScale;
    
    //瀹氫箟婊戝姩鑿滃崟鐨勯槾褰�
    private Drawable mShadowDrawable;
    
    //瀹氫箟鍙宠竟婊戝姩鑿滃崟鐨勯槾褰卞浘鐗�
    private Drawable mSecondaryShadowDrawable;
    
    //瀹氫箟闃村奖鐨勫搴�
    private int mShadowWidth;
    
    /**
     * 璁剧疆妯″紡鐨勫�
     */
    public void setMode(int mode) {
        if (mode == SlidingMenu.LEFT || mode == SlidingMenu.RIGHT) {
            if (mContent != null)
                mContent.setVisibility(View.VISIBLE);
            if (mSecondaryContent != null)
                mSecondaryContent.setVisibility(View.INVISIBLE);
        }
        mMode = mode;
    }

    /**
     * 寰楀埌妯″紡鐨勫�
     */
    public int getMode() {
        return mMode;
    }

    /**
     * 璁剧疆婊戝姩缂╂斁鐨勫�
     */
    public void setScrollScale(float scrollScale) {
        mScrollScale = scrollScale;
    }

    /**
     * 寰楀埌婊戝姩缂╂斁鐨勫�
     */
    public float getScrollScale() {
        return mScrollScale;
    }

    /**
     * 璁剧疆婊戝姩鑿滃崟鐨勯槾褰�
     */
    public void setShadowDrawable(Drawable shadow) {
        mShadowDrawable = shadow;
        invalidate();
    }

    /**
     * 璁剧疆鍙宠竟婊戝姩鑿滃崟鐨勯槾褰�
     */
    public void setSecondaryShadowDrawable(Drawable shadow) {
        mSecondaryShadowDrawable = shadow;
        invalidate();
    }

    /**
     * 璁剧疆闃村奖鐨勫搴�
     */
    public void setShadowWidth(int width) {
        mShadowWidth = width;
        invalidate();
    }

    /**
     * 璁剧疆鑳藉惁浣跨敤娓愬叆娓愬嚭鏁堟灉
     */
    public void setFadeEnabled(boolean b) {
        mFadeEnabled = b;
    }

    /**
     * 璁剧疆娓愬叆娓愬嚭鐨勫�
     */
    public void setFadeDegree(float degree) {
        if (degree > 1.0f || degree < 0.0f)
            throw new IllegalStateException("The BehindFadeDegree must be between 0.0f and 1.0f");
        mFadeDegree = degree;
    }

    /**
     * 寰楀埌鑿滃崟椤甸潰
     */
    public int getMenuPage(int page) {
        page = (page > 1) ? 2 : ((page < 1) ? 0 : page);
        if (mMode == SlidingMenu.LEFT && page > 1) {
            return 0;
        } else if (mMode == SlidingMenu.RIGHT && page < 1) {
            return 2;
        } else {
            return page;
        }
    }

    /**
     * 婊戝姩涓嬫柟瑙嗗浘鍒拌揪鐨勪綅缃�
     */
    public void scrollBehindTo(View content, int x, int y) {
        int vis = View.VISIBLE;     
        if (mMode == SlidingMenu.LEFT) {
            if (x >= content.getLeft()) vis = View.INVISIBLE;
            scrollTo((int)((x + getBehindWidth())*mScrollScale), y);
        } else if (mMode == SlidingMenu.RIGHT) {
            if (x <= content.getLeft()) vis = View.INVISIBLE;
            scrollTo((int)(getBehindWidth() - getWidth() + 
                    (x-getBehindWidth())*mScrollScale), y);
        } else if (mMode == SlidingMenu.LEFT_RIGHT) {
            mContent.setVisibility(x >= content.getLeft() ? View.INVISIBLE : View.VISIBLE);
            mSecondaryContent.setVisibility(x <= content.getLeft() ? View.INVISIBLE : View.VISIBLE);
            vis = x == 0 ? View.INVISIBLE : View.VISIBLE;
            if (x <= content.getLeft()) {
                scrollTo((int)((x + getBehindWidth())*mScrollScale), y);                
            } else {
                scrollTo((int)(getBehindWidth() - getWidth() + 
                        (x-getBehindWidth())*mScrollScale), y);             
            }
        }
        if (vis == View.INVISIBLE)
            Log.v(TAG, "behind INVISIBLE");
        setVisibility(vis);
    }

    /**
     * 寰楀埌宸﹁竟鑿滃崟鐨勮鍥�
     */
    public int getMenuLeft(View content, int page) {
        if (mMode == SlidingMenu.LEFT) {
            switch (page) {
            case 0:
                return content.getLeft() - getBehindWidth();
            case 2:
                return content.getLeft();
            }
        } else if (mMode == SlidingMenu.RIGHT) {
            switch (page) {
            case 0:
                return content.getLeft();
            case 2:
                return content.getLeft() + getBehindWidth();    
            }
        } else if (mMode == SlidingMenu.LEFT_RIGHT) {
            switch (page) {
            case 0:
                return content.getLeft() - getBehindWidth();
            case 2:
                return content.getLeft() + getBehindWidth();
            }
        }
        return content.getLeft();
    }

    /**
     * 寰楀埌宸﹁竟妗嗚鍥�
     */
    public int getAbsLeftBound(View content) {
        if (mMode == SlidingMenu.LEFT || mMode == SlidingMenu.LEFT_RIGHT) {
            return content.getLeft() - getBehindWidth();
        } else if (mMode == SlidingMenu.RIGHT) {
            return content.getLeft();
        }
        return 0;
    }

    /**
     * 寰楀埌鍙宠竟妗嗚鍥�
     */
    public int getAbsRightBound(View content) {
        if (mMode == SlidingMenu.LEFT) {
            return content.getLeft();
        } else if (mMode == SlidingMenu.RIGHT || mMode == SlidingMenu.LEFT_RIGHT) {
            return content.getLeft() + getBehindWidth();
        }
        return 0;
    }

    /**
     * 鏄惁鍏佽瑙︽懜灞忓箷鐨勮竟缂�
     */
    public boolean marginTouchAllowed(View content, int x) {
        int left = content.getLeft();
        int right = content.getRight();
        if (mMode == SlidingMenu.LEFT) {
            return (x >= left && x <= mMarginThreshold + left);
        } else if (mMode == SlidingMenu.RIGHT) {
            return (x <= right && x >= right - mMarginThreshold);
        } else if (mMode == SlidingMenu.LEFT_RIGHT) {
            return (x >= left && x <= mMarginThreshold + left) || 
                    (x <= right && x >= right - mMarginThreshold);
        }
        return false;
    }

    /**
     * 璁剧疆瑙︽懜妯″紡鐨勫�
     */
    public void setTouchMode(int i) {
        mTouchMode = i;
    }

    /**
     * 鏄惁鍏佽閫氳繃瑙︽懜鎵撳紑婊戝姩鑿滃崟
     */
    public boolean menuOpenTouchAllowed(View content, int currPage, float x) {
        switch (mTouchMode) {
        case SlidingMenu.TOUCHMODE_FULLSCREEN:
            return true;
        case SlidingMenu.TOUCHMODE_MARGIN:
            return menuTouchInQuickReturn(content, currPage, x);
        }
        return false;
    }

    /**
     * 婊戝姩鑿滃崟蹇�杩斿洖
     */
    public boolean menuTouchInQuickReturn(View content, int currPage, float x) {
        if (mMode == SlidingMenu.LEFT || (mMode == SlidingMenu.LEFT_RIGHT && currPage == 0)) {
            return x >= content.getLeft();
        } else if (mMode == SlidingMenu.RIGHT || (mMode == SlidingMenu.LEFT_RIGHT && currPage == 2)) {
            return x <= content.getRight();
        }
        return false;
    }

    /**
     * 鏄惁鍏佽鍏抽棴婊戝姩鑿滃崟
     */
    public boolean menuClosedSlideAllowed(float dx) {
        if (mMode == SlidingMenu.LEFT) {
            return dx > 0;
        } else if (mMode == SlidingMenu.RIGHT) {
            return dx < 0;
        } else if (mMode == SlidingMenu.LEFT_RIGHT) {
            return true;
        }
        return false;
    }

    /**
     * 鏄惁鍏佽鎵撳紑婊戝姩鑿滃崟
     */
    public boolean menuOpenSlideAllowed(float dx) {
        if (mMode == SlidingMenu.LEFT) {
            return dx < 0;
        } else if (mMode == SlidingMenu.RIGHT) {
            return dx > 0;
        } else if (mMode == SlidingMenu.LEFT_RIGHT) {
            return true;
        }
        return false;
    }

    /**
     * 鐢绘粦鍔ㄨ彍鍗曠殑闃村奖
     */
    public void drawShadow(View content, Canvas canvas) {
        if (mShadowDrawable == null || mShadowWidth <= 0) return;
        int left = 0;
        if (mMode == SlidingMenu.LEFT) {
            left = content.getLeft() - mShadowWidth;
        } else if (mMode == SlidingMenu.RIGHT) {
            left = content.getRight();
        } else if (mMode == SlidingMenu.LEFT_RIGHT) {
            if (mSecondaryShadowDrawable != null) {
                left = content.getRight();
                mSecondaryShadowDrawable.setBounds(left, 0, left + mShadowWidth, getHeight());
                mSecondaryShadowDrawable.draw(canvas);
            }
            left = content.getLeft() - mShadowWidth;
        }
        mShadowDrawable.setBounds(left, 0, left + mShadowWidth, getHeight());
        mShadowDrawable.draw(canvas);
    }

    /**
     * 鐢诲嚭娓愬叆娓愬嚭鏁堟灉
     */
    public void drawFade(View content, Canvas canvas, float openPercent) {
        if (!mFadeEnabled) return;
        final int alpha = (int) (mFadeDegree * 255 * Math.abs(1-openPercent));
        mFadePaint.setColor(Color.argb(alpha, 0, 0, 0));
        int left = 0;
        int right = 0;
        if (mMode == SlidingMenu.LEFT) {
            left = content.getLeft() - getBehindWidth();
            right = content.getLeft();
        } else if (mMode == SlidingMenu.RIGHT) {
            left = content.getRight();
            right = content.getRight() + getBehindWidth();          
        } else if (mMode == SlidingMenu.LEFT_RIGHT) {
            left = content.getLeft() - getBehindWidth();
            right = content.getLeft();
            canvas.drawRect(left, 0, right, getHeight(), mFadePaint);
            left = content.getRight();
            right = content.getRight() + getBehindWidth();          
        }
        canvas.drawRect(left, 0, right, getHeight(), mFadePaint);
    }
    
    private boolean mSelectorEnabled = true;
    private Bitmap mSelectorDrawable;
    private View mSelectedView;
    
    public void drawSelector(View content, Canvas canvas, float openPercent) {
        if (!mSelectorEnabled) return;
        if (mSelectorDrawable != null && mSelectedView != null) {
            String tag = (String) mSelectedView.getTag(R.id.selected_view);
            if (tag.equals(TAG+"SelectedView")) {
                canvas.save();
                int left, right, offset;
                offset = (int) (mSelectorDrawable.getWidth() * openPercent);
                if (mMode == SlidingMenu.LEFT) {
                    right = content.getLeft();
                    left = right - offset;
                    canvas.clipRect(left, 0, right, getHeight());
                    canvas.drawBitmap(mSelectorDrawable, left, getSelectorTop(), null);     
                } else if (mMode == SlidingMenu.RIGHT) {
                    left = content.getRight();
                    right = left + offset;
                    canvas.clipRect(left, 0, right, getHeight());
                    canvas.drawBitmap(mSelectorDrawable, right - mSelectorDrawable.getWidth(), getSelectorTop(), null);
                }
                canvas.restore();
            }
        }
    }
    
    public void setSelectorEnabled(boolean b) {
        mSelectorEnabled = b;
    }

    public void setSelectedView(View v) {
        if (mSelectedView != null) {
            mSelectedView.setTag(R.id.selected_view, null);
            mSelectedView = null;
        }
        if (v != null && v.getParent() != null) {
            mSelectedView = v;
            mSelectedView.setTag(R.id.selected_view, TAG+"SelectedView");
            invalidate();
        }
    }

    private int getSelectorTop() {
        int y = mSelectedView.getTop();
        y += (mSelectedView.getHeight() - mSelectorDrawable.getHeight()) / 2;
        return y;
    }

    public void setSelectorBitmap(Bitmap b) {
        mSelectorDrawable = b;
        refreshDrawableState();
    }

}
