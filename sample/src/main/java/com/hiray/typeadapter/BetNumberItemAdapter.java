package com.hiray.typeadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hiray.typeadapter.event.BetNumberEvent;
import com.hiray.typeadapter.event.Bus;
import com.hiray.typeadapter.utility.FormatUtil;
import com.hiray.typeadapter.widget.BetBoxTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: CJJ
 * @date 2017/10/27
 */
public class BetNumberItemAdapter extends RecyclerView.Adapter<BetNumberItemAdapter.ViewHolder> {


    LayoutInflater layoutInflater;
    Context context;
    List<String> data;
    List<String> multipliers;
    List<ViewHolder> holders = new ArrayList<>();
    int spanCount = 0;
    int type;


    public BetNumberItemAdapter(List<String> data, List<String> multipliers, int type) {
        this.type = type;
        this.data = data;
        this.multipliers = multipliers;
    }

    public void setData(List<String> data, List<String> multipliers, int type) {
        this.type = type;
        this.data = data;
        this.multipliers = multipliers;
    }

    public void notifyItem(int pos) {
        holders.get(pos).textView.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(type, pos)));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        spanCount = ((GridLayoutManager) ((RecyclerView) parent).getLayoutManager()).getSpanCount();
        if (layoutInflater == null) {
            context = parent.getContext().getApplicationContext();
            layoutInflater = LayoutInflater.from(context);
        }
        return new ViewHolder(R.layout.item_betnumber, parent);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
        if (holders.size() - 1 < position || holders.get(position) == null) {
            holders.add(position, holder);
        } else holders.set(position, holder);

        if (multipliers != null)
            holder.multiplier.setText(multipliers.get(position));
        holder.textView.setDrawableText(FormatUtil.formatBetNumber(BetHolder.query(type, position)));

    }


    @Override
    public int getItemCount() {

        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        BetBoxTextView textView;
        TextView multiplier;


        public ViewHolder(@LayoutRes int layoutId, ViewGroup parent) {
            this(layoutInflater.inflate(layoutId, parent, false));
        }

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.number);
            multiplier = itemView.findViewById(R.id.multiplier);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bus.getInstance().post(new BetNumberEvent(type, getAdapterPosition(), textView.getText().toString()));
                }
            });
        }
    }

}
