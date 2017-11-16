package com.hiray.typeadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hiray.adapter.TypeBinder;
import com.hiray.typeadapter.model.HistoryNumber;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HistoryBinder extends TypeBinder<HistoryNumber, HistoryBinder.ViewHolder> {

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(
                R.layout.item_number_history, parent, false
        ));
    }

    @Override
    protected void onBindViewHolder(HistoryNumber historyNumber, ViewHolder holder) {
        holder.one.setText(historyNumber.one);
        holder.two.setText(historyNumber.two);
        holder.three.setText(historyNumber.three);
        holder.four.setText(historyNumber.four);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.one)
        TextView one;
        @Bind(R.id.two)
        TextView two;
        @Bind(R.id.three)
        TextView three;
        @Bind(R.id.four)
        TextView four;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
