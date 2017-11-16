package com.hiray.adapter;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiray on 2017/11/13
 *
 * @author hiray
 */

public class TypeAdapter extends RecyclerView.Adapter {

    //the data to be bound
    private List data;
    private List<TypeBinder> binders = new ArrayList<>();
    private List<Class<?>> classTypes = new ArrayList<>();


    public void addBinder(TypeBinder binder) {
        binders.add(binder);
        classTypes.add(binder.getType());
    }

    public void addBinder(Class clazz, TypeBinder binder) {
        if (clazz.equals(binder.getType())) {
            binders.add(binder);
            classTypes.add(clazz);
        } else {
            throw new RuntimeException("the model type is " + clazz.getSimpleName() +
                    " but the type argument of binder:" + binder.getClass().getSimpleName() + " is " +
                    binder.getType().getSimpleName() + ", are you sure is it the right view binder for" +
                    "this model type?");
        }
    }

    public void setData(List data) {
        this.data = data;
    }

    //map according to position
    public static final int STRATEGY_POSITION = 0;
    //map according to model type
    public static final int STRATEGY_CLASS = 1;

    private int strategy = STRATEGY_CLASS;

    @IntDef({STRATEGY_POSITION, STRATEGY_CLASS})
    public @interface STRATEGY {
    }

    public void setStrategy(@STRATEGY int strategy) {
        this.strategy = strategy;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TypeBinder binder = getBinder(viewType);
        return binder.onCreateViewHolder(parent, viewType);

    }

    private TypeBinder getBinder(int viewType) {
        if (strategy == STRATEGY_POSITION) {
            for (TypeBinder binder :
                    binders) {
                if (binder.getItemViewType() == viewType)
                    return binder;

            }
            return null;
        } else return binders.get(viewType);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TypeBinder binder = getBinder(holder.getItemViewType());
        binder.onBindViewHolder(data.get(position), holder);
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return findViewTypeForPosition(position);
    }

    private int findViewTypeForPosition(int position) {
        if (strategy == STRATEGY_POSITION) {
            TypeBinder binder = binderForPosition(position);
            if (binder != null)
                return binder.getItemViewType();
            else throw new RuntimeException("no such type binder for position " + position + "" +
                    "did your really have a type binder claim the position" + position);
        } else {
            Object item = data.get(position);
            Class<?> itemClass = item.getClass();
            int index = classTypes.indexOf(itemClass);
            if (index != -1)
                return index;
            else throw new RuntimeException("no such type binder for " + itemClass);

        }
    }

    private TypeBinder binderForPosition(int pos) {
        if (binders == null)
            throw new IllegalArgumentException("are you sure you have set binders?");
        List<TypeBinder> conflictBinders = new ArrayList<>(binders.size());
        for (TypeBinder binder :
                binders) {
            if (binder.claimPosition(pos))
                conflictBinders.add(binder);
        }

        if (conflictBinders.size() > 1) {
            printConflictTypeMsg(conflictBinders);
        } else return conflictBinders.get(0);

        return null;
    }

    private void printConflictTypeMsg(List<TypeBinder> conflictBinders) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TypeBinder binder :
                conflictBinders) {

            stringBuilder.append(binder.getClass().getName());
            stringBuilder.append(',');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(" has same int view type ,please check your TypeBinder," +
                "make sure the different TypeBinder return different int view type or you can change" +
                "the type strategy by calling method TypeAdapter#setTypeStrategy");
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (binders != null)
            for (TypeBinder binder : binders) {
                binder.onDetachFromRecyclerView(recyclerView);
            }
    }


}
