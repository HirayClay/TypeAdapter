package com.hiray.typeadapter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;


import com.hiray.typeadapter.R;

import butterknife.BindDrawable;
import butterknife.ButterKnife;

/**
 * Created by hiray on 2017/10/27.
 *
 * @author hiray
 */

public class BetNumberTextView extends ShapeTextView {

    private static final String TAG = "BetNumberTextView";
    private final int extraColor;
    private final float extraTextSize;
    private String drawableText;

    @BindDrawable(R.drawable.betnum)
    Drawable drawable;
    private int extraSpace;
    TextPaint textPaint;

    public BetNumberTextView(Context context) {
        this(context, null);
    }

    public BetNumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BetNumberTextView);
        extraSpace = array.getDimensionPixelSize(R.styleable.BetNumberTextView_extra_space, 0);
        extraColor = array.getColor(R.styleable.BetNumberTextView_extra_color, -1);
        extraTextSize = array.getDimension(R.styleable.BetNumberTextView_extra_text_size, 0.7f * getTextSize());
        array.recycle();
        ButterKnife.bind(this);
        textPaint = new TextPaint(getPaint());
        textPaint.setTextSize(extraTextSize);
        textPaint.setColor(extraColor);
    }

    public void setDrawableText(String text) {
        this.drawableText = text;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(drawableText)) {

            Paint.FontMetrics f = textPaint.getFontMetrics();
            float w = textPaint.measureText(drawableText);
            float h = f.descent - f.ascent;
            int r = (int) Math.ceil(Math.sqrt(w * w / 4 + h * h / 4));
            drawable.setBounds(extraSpace, getHeight() - 2 * r - extraSpace, 2 * r + extraSpace, getHeight() - extraSpace);
            drawable.draw(canvas);
            canvas.drawText(drawableText, r - (w / 2) + extraSpace, (getHeight() - r - f.ascent - h / 2) - extraSpace, textPaint);
        }
      


    }
}
