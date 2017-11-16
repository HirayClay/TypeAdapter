package com.hiray.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by hiray on 2017/11/13
 *
 * @author hiray
 */

/**
 * @param <T>  the model type
 * @param <VH> your own viewholder for specific viewbiner
 */
public abstract class TypeBinder<T, VH extends RecyclerView.ViewHolder> {

    /**
     * @param position the item position that you may want to claim
     * @return true if you want claim the position of item and then all of
     * the item's work of the position will be delegated here
     * <p>
     * only when set {@link TypeAdapter#strategy} to {@link TypeAdapter#STRATEGY_POSITION}，
     * you need to override this method return true as well as override {@link #getItemViewType()}
     */
    public boolean claimPosition(int position) {
        return false;
    }

    public Class<?> getType() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        try {
            Type paramType = parameterizedType.getActualTypeArguments()[0];
            //todo 在Android  的open jdk 中Type没有getTypeName方法,暂时或将一直使用这种投机取巧的方法 -_-||
            return Class.forName(paramType.toString().split(" ")[1]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected abstract VH onCreateViewHolder(ViewGroup parent, int viewType);


    protected abstract void onBindViewHolder(T t, VH holder);

    /**
     * be careful ,if you override {@link #claimPosition} and return true,
     * then you must override this method and return your item view type(be careful that different
     * viewbiner must return exclusive item view type)
     *
     * @return item view type
     */
    protected int getItemViewType() {
        return 0;
    }

    protected void onDetachFromRecyclerView(RecyclerView recyclerView) {

    }

    protected void onViewRecycled(VH holder) {

    }

    protected void onViewDetachedFromWindow(VH holder) {
    }
}
