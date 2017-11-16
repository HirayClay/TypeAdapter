package com.hiray.typeadapter.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @author: CJJ
 * @date 2017/10/31
 */
public class BetBoxTextView extends BetNumberTextView{

    public BetBoxTextView(Context context) {
        super(context);
    }

    public BetBoxTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        setMeasuredDimension(w, w);
    }
}
