package com.hiray.typeadapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hiray.adapter.TypeBinder;
import com.hiray.typeadapter.model.EditionHeader;

import butterknife.Bind;
import butterknife.ButterKnife;


public class EditionHeaderBinder extends TypeBinder<EditionHeader,EditionHeaderBinder.ViewHolder> {


    @Override
    public boolean claimPosition(int position) {
        return false;
    }

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(
                R.layout.item_csz_yellow_header, parent, false
        ));
    }

    @Override
    protected void onBindViewHolder(EditionHeader editionHeader, ViewHolder holder) {
        holder.edition.setText("第"+editionHeader.edition+"期");
    }

    @Override
    protected int getItemViewType() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.edition)
        TextView edition;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
