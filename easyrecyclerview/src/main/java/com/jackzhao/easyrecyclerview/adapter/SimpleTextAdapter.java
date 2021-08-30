package com.jackzhao.easyrecyclerview.adapter;

import com.jackzhao.easyrecyclerview.ViewHolder.OneLineTextViewHolder;
import com.jackzhao.easyrecyclerview.ViewHolder.SimpleTextViewHolder;
import com.jackzhao.easyrecyclerview.data.SimpleTextData;

public class SimpleTextAdapter extends BaseAdapter {
    @Override
    public void registerType() {
        registerViewHolderType(SimpleTextData.class, SimpleTextViewHolder.class);
        registerViewHolderType(String.class, OneLineTextViewHolder.class);
    }

}
