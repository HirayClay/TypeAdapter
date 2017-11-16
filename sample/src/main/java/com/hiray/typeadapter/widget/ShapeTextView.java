package com.hiray.typeadapter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.hiray.typeadapter.R;


/**
 * @author: hiray
 * @date 2017/10/31
 */
public class ShapeTextView extends AppCompatTextView {

    private static final String TAG = "TextDrawable";
    private float corner;
    private boolean left_top_radius;
    private boolean right_top_radius;
    private boolean left_bottom_radius;
    private boolean right_bottom_radius;
    private Paint paint;
    private Path path;
    private int backgroundColor;
    private int strokeWidth;
    private int strokeColor;
    private Path strokePath;
    private Paint strokePaint;

    public ShapeTextView(Context context) {
        super(context);
    }

    public ShapeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView);
        corner = (int) array.getDimension(R.styleable.ShapeTextView_shape_corner, 0);
        left_top_radius = array.getBoolean(R.styleable.ShapeTextView_radius_left_top, false);
        left_bottom_radius = array.getBoolean(R.styleable.ShapeTextView_radius_left_bottom, false);
        right_top_radius = array.getBoolean(R.styleable.ShapeTextView_radius_right_top, false);
        right_bottom_radius = array.getBoolean(R.styleable.ShapeTextView_radius_right_bottom, false);
        backgroundColor = array.getColor(R.styleable.ShapeTextView_scrim_color, Color.parseColor("#cccccc"));
        strokeWidth = array.getDimensionPixelSize(R.styleable.ShapeTextView_stroke_width, 0);
        strokeColor = array.getColor(R.styleable.ShapeTextView_stroke_color, -1);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        if (strokeWidth > 0 && strokeColor != -1) {
            strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setColor(strokeColor);
            strokePaint.setStrokeWidth(strokeWidth);
        }
        array.recycle();
    }

    public ShapeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        backgroundColor = color;
        paint.setColor(backgroundColor);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //给描边留下空间
        if (strokeWidth > 0 && strokeColor != -1)
            setPadding(strokeWidth, strokeWidth, strokeWidth, strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float left = strokeWidth;
        float top = strokeWidth;
        float right = getMeasuredWidth() - strokeWidth;
        float bottom = getMeasuredHeight() - strokeWidth;
        path = RoundedRect(left, top, right, bottom, corner, corner, left_top_radius, right_top_radius, right_bottom_radius, left_bottom_radius);
        canvas.drawPath(path, paint);
        if (strokeWidth > 0 && strokeColor != -1) {
            float rx = corner + strokeWidth / 2f;
            strokePath = RoundedRect(strokeWidth / 2f, strokeWidth / 2f, right + strokeWidth / 2f, bottom + strokeWidth / 2f, rx,
                    rx, left_top_radius, right_top_radius, right_bottom_radius, left_bottom_radius);
            canvas.drawPath(strokePath, strokePaint);
        }
        super.onDraw(canvas);
    }

    public Path RoundedRect(float left, float top, float right, float bottom, float rx, float ry,
                            boolean tl, boolean tr, boolean br, boolean bl) {
        Path path = new Path();
        if (rx < 0) {
            rx = 0;
        }
        if (ry < 0) {
            ry = 0;
        }
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) {
            rx = width / 2;
        }
        if (ry > height / 2) {
            ry = height / 2;
        }
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        if (tr)
            path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        else {
            path.rLineTo(0, -ry);
            path.rLineTo(-rx, 0);
        }
        path.rLineTo(-widthMinusCorners, 0);
        if (tl)
            path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        else {
            path.rLineTo(-rx, 0);
            path.rLineTo(0, ry);
        }
        path.rLineTo(0, heightMinusCorners);

        if (bl)
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
        else {
            path.rLineTo(0, ry);
            path.rLineTo(rx, 0);
        }

        path.rLineTo(widthMinusCorners, 0);
        if (br)
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        else {
            path.rLineTo(rx, 0);
            path.rLineTo(0, -ry);
        }

        path.rLineTo(0, -heightMinusCorners);
        path.close();//Given close, last lineto can be removed.

        return path;
    }

    public void setCorner(float corner) {
        this.corner = corner;
        postInvalidate();
    }

    public void setRadius(boolean lt, boolean lb, boolean rt, boolean rb) {
        left_top_radius = lt;
        left_bottom_radius = lb;
        right_top_radius = rt;
        right_bottom_radius = rb;
        postInvalidate();
    }
}
