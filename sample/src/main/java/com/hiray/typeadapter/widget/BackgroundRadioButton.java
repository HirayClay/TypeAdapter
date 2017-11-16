package com.hiray.typeadapter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.hiray.typeadapter.R;


public class BackgroundRadioButton extends AppCompatRadioButton {

    float scaleRatio;
    private static final String TAG = "BackgroundRadioButton";

    public BackgroundRadioButton(Context context) {
        this(context, null);
    }

    public BackgroundRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgroundRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BackgroundRadioButton);
        int resourceId = array.getResourceId(R.styleable.BackgroundRadioButton_button_drawable, -1);
        if (resourceId != -1) {
            setBackground(AppCompatResources.getDrawable(getContext(), resourceId));
        }
        scaleRatio = array.getFloat(R.styleable.BackgroundRadioButton_scale_ratio, 1f);
        array.recycle();
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                float sx = isChecked?scaleRatio:1/scaleRatio;
                float sy = isChecked?scaleRatio:1/scaleRatio;
                animate().scaleX(sx).scaleY(sy)
                        .setDuration(150)
                        .start();
            }
        });
    }
}
