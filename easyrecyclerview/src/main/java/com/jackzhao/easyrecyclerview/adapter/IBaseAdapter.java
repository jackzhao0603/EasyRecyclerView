package com.jackzhao.easyrecyclerview.adapter;

import com.jackzhao.easyrecyclerview.ViewHolder.BaseViewHolder;

import java.util.List;


/**
 * Created by jack_zhao on 2018/3/13.
 */

public interface IBaseAdapter {

    boolean isPrivatePosition(int position);

    int getInnerPosition(int position);

    int getOuterPosition(int position);

    List getDataList();

    void registerViewHolderType(Class data,  Class<BaseViewHolder>  viewHolder);

}
