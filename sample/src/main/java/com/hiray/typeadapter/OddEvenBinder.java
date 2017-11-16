package com.hiray.typeadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiray.adapter.TypeBinder;
import com.hiray.typeadapter.event.BetNumberEvent;
import com.hiray.typeadapter.event.ItemNotifyEvent;
import com.hiray.typeadapter.event.Bus;
import com.hiray.typeadapter.model.OddEvenGrid;
import com.hiray.typeadapter.utility.FormatUtil;
import com.hiray.typeadapter.widget.BetNumberTextView;

import rx.Subscription;
import rx.functions.Action1;


public class OddEvenBinder extends TypeBinder<OddEvenGrid, OddEvenBinder.ViewHolder> {

    private final Subscription subscription;
    private ViewHolder holder;

    public OddEvenBinder() {
        subscription = Bus.getInstance().register(new Action1() {
            @Override
            public void call(Object o) {
                if (o instanceof ItemNotifyEvent) {
                    ItemNotifyEvent e = (ItemNotifyEvent) o;
                    if (e.type == 1)
                        holder.notifyOdd();
                    else if (e.type == 2)
                        holder.notifyEven();
                }
            }
        });
    }

    @Override
    public boolean claimPosition(int position) {
        return false;
    }

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        holder = new ViewHolder(LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(
                R.layout.item_csz_odd_even, parent, false
        ));
        return holder;
    }

    @Override
    protected void onBindViewHolder(OddEvenGrid oddEven, ViewHolder holder) {
        holder.odd.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(1, 0)));
        holder.even.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(2, 1)));
    }

    @Override
    protected int getItemViewType() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        BetNumberTextView odd;
        BetNumberTextView even;


        public ViewHolder(View itemView) {
            super(itemView);
            odd = itemView.findViewById(R.id.odd);
            even = itemView.findViewById(R.id.even);
            odd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bus.getInstance().post(new BetNumberEvent(1, 0, "1"));
                }
            });
            even.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bus.getInstance().post(new BetNumberEvent(2, 1, "2"));
                }
            });
        }

        public void notifyOdd() {
            holder.odd.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(1, 0)));
        }

        public void notifyEven() {
            holder.even.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(2, 1)));
        }
    }
}
