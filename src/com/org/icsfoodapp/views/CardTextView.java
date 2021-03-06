package com.org.icsfoodapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CardTextView extends LinearLayout {
	public CardTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CardTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CardTextView(Context context) {
		super(context);
		init();
	}
	private void init(){
		TextView titleBar = new TextView(getContext());
		TextView subText = new TextView(getContext());
		titleBar.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
		titleBar.getLayoutParams().width = LayoutParams.MATCH_PARENT;
		titleBar.setClickable(true);
		titleBar.setFocusable(true);
		titleBar.setFocusableInTouchMode(true);
		subText.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
		subText.getLayoutParams().width = LayoutParams.MATCH_PARENT;
		subText.setVisibility(GONE);
		addView(titleBar, 0);
		addView(subText, 1);
	}
	
	public CardTextView setTitle(CharSequence text){
		((TextView) getChildAt(0)).setText(text);
		return this;
	}
	
	public CardTextView setSubText(CharSequence text){
		((TextView) getChildAt(1)).setText(text);
		//notifySubtreeAccessibilityStateChanged(getChildAt(1), this, AccessibilityEvent.CONTENT_CHANGE_TYPE_TEXT);
		return this;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 记录总高度  
        int mTotalHeight = 0;  
        // 遍历所有子视图  
        int childCount = getChildCount();  
        for (int i = 0; i < childCount; i++) {  
            View childView = getChildAt(i);  
  
            // 获取在onMeasure中计算的视图尺寸  
            int measureHeight = childView.getMeasuredHeight();  
            int measuredWidth = childView.getMeasuredWidth();  
  
            childView.layout(l, mTotalHeight, measuredWidth, mTotalHeight  
                    + measureHeight);  
            mTotalHeight += measureHeight;  
  
        }
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
        int measureWidth = measureWidth(widthMeasureSpec);  
        int measureHeight = measureHeight(heightMeasureSpec);  
        // 计算自定义的ViewGroup中所有子控件的大小  
        measureChildren(widthMeasureSpec, heightMeasureSpec);  
        // 设置自定义的控件MyViewGroup的大小  
        setMeasuredDimension(measureWidth, measureHeight); 
	}
	
	private int measureWidth(int pWidthMeasureSpec) {  
        int result = 0;  
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// 得到模式  
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// 得到尺寸  
  
        switch (widthMode) {  
        /** 
         * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY, 
         * MeasureSpec.AT_MOST。 
         *  
         *  
         * MeasureSpec.EXACTLY是精确尺寸， 
         * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid 
         * :layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。 
         *  
         *  
         * MeasureSpec.AT_MOST是最大尺寸， 
         * 当控件的layout_width或layout_height指定为WRAP_CONTENT时 
         * ，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可 
         * 。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。 
         *  
         *  
         * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView， 
         * 通过measure方法传入的模式。 
         */  
        case MeasureSpec.AT_MOST:  
        case MeasureSpec.EXACTLY:  
            result = widthSize;  
            break;  
        }  
        return result;  
    }  
  
    private int measureHeight(int pHeightMeasureSpec) {  
        int result = 0;  
  
        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);  
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);  
  
        switch (heightMode) {  
        case MeasureSpec.AT_MOST:  
        case MeasureSpec.EXACTLY:  
            result = heightSize;  
            break;  
        }  
        return result;  
    }  

}
