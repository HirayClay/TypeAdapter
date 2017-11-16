package com.hiray.typeadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hiray.adapter.TypeBinder;
import com.hiray.typeadapter.model.ColumnItem;


/**
 * @author: hiray
 * @date 2017/10/30
 */
public class ColumnGridBinder extends TypeBinder<ColumnItem, ColumnGridBinder.ViewHolder> {

    private Context context;
    private ArrayMap<Integer, BetNumberItemAdapter> adapterArrayMap = new ArrayMap<>(3);


    public void notifyItem(int type, int position) {
        BetNumberItemAdapter betNumberItemAdapter = adapterArrayMap.get(type);
        if (betNumberItemAdapter != null)
            betNumberItemAdapter.notifyItem(position);
    }

    public ColumnGridBinder() {

    }

    @Override
    public boolean claimPosition(int position) {
        return false;
    }

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext().getApplicationContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_six_column, parent, false
        ));
    }

    @Override
    protected void onBindViewHolder(ColumnItem columnItem, ViewHolder holder) {
        holder.desc.setText(columnItem.desc);
        holder.multiplier.setText(columnItem.multiplier);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, columnItem.spanCount));
        BetNumberItemAdapter betNumberItemAdapter = adapterArrayMap.get(columnItem.type);
        if (betNumberItemAdapter == null) {
            betNumberItemAdapter = new BetNumberItemAdapter(columnItem.numbers, columnItem.multipliers, columnItem.type);
            adapterArrayMap.put(columnItem.type, betNumberItemAdapter);
        } else
            betNumberItemAdapter.setData(columnItem.numbers, columnItem.multipliers, columnItem.type);
        holder.recyclerView.setAdapter(betNumberItemAdapter);
    }

    @Override
    protected int getItemViewType() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView desc;
        TextView multiplier;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            desc = itemView.findViewById(R.id.desc);
            multiplier = itemView.findViewById(R.id.multiplier);
        }
    }

}
