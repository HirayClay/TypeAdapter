package com.hiray.typeadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hiray.adapter.TypeBinder;

/**
 * Created by CJJ on 2017/11/16.
 *
 * @author CJJ
 */

public class EmptyBinder extends TypeBinder<Object, EmptyBinder.ViewHolder> {


    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindViewHolder(Object o, ViewHolder holder) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
