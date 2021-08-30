package com.jackzhao.easyrecyclerview.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.jackzhao.easyrecyclerview.R;
import com.jackzhao.easyrecyclerview.utils.ReflectionUtils;

/**
 * Created by jackzhao on 2018/6/15.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private T mData = null;

    public BaseViewHolder(ViewGroup viewGroup) {
        super(viewGroup);
        viewGroup = getViewGroup(viewGroup);
        ReflectionUtils.setField(getClass(), "itemView", this, viewGroup);
    }


    public abstract ViewGroup getViewGroup(ViewGroup viewGroup);

    public abstract void bindData(T data, ViewGroup viewGroup);

    public void bindDataSafe(T data) {
        T t;
        try {
            t = data;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("can't bind " + data.getClass().toString() + " to " + this.getClass().toString());
        }
        mData = t;
        bindData(t, (ViewGroup) getView());
    }

    View getView() {
        return itemView;
    }

    public ViewGroup getViewGroupByResourceId(ViewGroup viewGroup, int resourceId) {
        return (ViewGroup) LayoutInflater.from(viewGroup.getContext())
                .inflate(resourceId, viewGroup, false);
    }
}
