package com.hiray.typeadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hiray.adapter.TypeBinder;
import com.hiray.typeadapter.event.BetNumberEvent;
import com.hiray.typeadapter.event.ItemNotifyEvent;
import com.hiray.typeadapter.event.Bus;
import com.hiray.typeadapter.model.IrregularItem;
import com.hiray.typeadapter.utility.FormatUtil;
import com.hiray.typeadapter.widget.BetBoxTextView;
import com.hiray.typeadapter.widget.BetNumberTextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;


public class IrregularGridViewBinder extends TypeBinder<IrregularItem, IrregularGridViewBinder.ViewHolder> {

    private final Subscription subscription;
    private ViewHolder holder;

    public IrregularGridViewBinder() {
        subscription = Bus.getInstance().register(new Action1() {
            @Override
            public void call(Object o) {
                if (o instanceof ItemNotifyEvent) {
                    ItemNotifyEvent e = (ItemNotifyEvent) o;
                    if (e.type == 4 || e.type == 5) {
                        holder.notifyItem(e.position);
                    }
                }
            }
        });
    }

    @Override
    protected void onDetachFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachFromRecyclerView(recyclerView);
        subscription.unsubscribe();
    }

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        holder = new ViewHolder(LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(
                R.layout.item_irregular_grid, parent, false
        ));
        return holder;
    }

    @Override
    protected void onBindViewHolder(IrregularItem irregularGrid, ViewHolder holder) {
        holder.irregular1.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 0)));
        holder.irregular2.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 1)));
        holder.irregular3.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 2)));
        holder.irregular4.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 3)));
        holder.irregular5.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 4)));
        holder.irregular6.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 5)));
        holder.irregular7.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(5, 6)));


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        BetBoxTextView irregular1;
        BetBoxTextView irregular2;
        BetBoxTextView irregular3;
        BetBoxTextView irregular4;
        BetBoxTextView irregular5;
        BetBoxTextView irregular6;
        BetNumberTextView irregular7;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            irregular1 = itemView.findViewById(R.id.irregular_1);
            irregular2 = itemView.findViewById(R.id.irregular_2);
            irregular3 = itemView.findViewById(R.id.irregular_3);
            irregular4 = itemView.findViewById(R.id.irregular_4);
            irregular5 = itemView.findViewById(R.id.irregular_5);
            irregular6 = itemView.findViewById(R.id.irregular_6);
            irregular7 = itemView.findViewById(R.id.irregular_7);
        }

        @OnClick({R.id.irregular_1, R.id.irregular_2, R.id.irregular_3,
                R.id.irregular_4, R.id.irregular_5, R.id.irregular_6, R.id.irregular_7})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.irregular_1:
                    Bus.getInstance().post(new BetNumberEvent(4, 0, "111"));
                    break;
                case R.id.irregular_2:
                    Bus.getInstance().post(new BetNumberEvent(4, 1, "222"));

                    break;
                case R.id.irregular_3:
                    Bus.getInstance().post(new BetNumberEvent(4, 2, "333"));

                    break;
                case R.id.irregular_4:
                    Bus.getInstance().post(new BetNumberEvent(4, 3, "444"));

                    break;
                case R.id.irregular_5:
                    Bus.getInstance().post(new BetNumberEvent(4, 4, "555"));

                    break;
                case R.id.irregular_6:
                    Bus.getInstance().post(new BetNumberEvent(4, 5, "666"));

                    break;
                case R.id.irregular_7:
                    Bus.getInstance().post(new BetNumberEvent(4, 6, "999"));
                    break;
            }
        }

        public void notifyItem(int pos) {
            switch (pos) {
                case 0:
                    irregular1.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 0)));
                    break;
                case 1:
                    holder.irregular2.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 1)));
                    break;
                case 2:
                    holder.irregular3.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 2)));
                    break;
                case 3:
                    holder.irregular4.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 3)));
                    break;
                case 4:
                    holder.irregular5.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 4)));
                    break;
                case 5:
                    holder.irregular6.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(4, 5)));
                    break;
                case 6:
                    holder.irregular7.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(5, 6)));
                    break;
            }
        }
    }
}
