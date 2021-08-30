package com.jackzhao.easyrecyclerviewapp;

import com.jackzhao.easyrecyclerview.ViewHolder.OneLineTextViewHolder;
import com.jackzhao.easyrecyclerview.ViewHolder.SimpleTextViewHolder;
import com.jackzhao.easyrecyclerview.adapter.BaseAdapter;
import com.jackzhao.easyrecyclerview.data.SimpleTextData;

public class SelfTextAdapter extends BaseAdapter {
    @Override
    public void registerType() {
        registerViewHolderType(SimpleTextData.class, SimpleTextViewHolder.class);
        registerViewHolderType(SelfTextData.class, SelfTextViewHolder.class);
    }

}
