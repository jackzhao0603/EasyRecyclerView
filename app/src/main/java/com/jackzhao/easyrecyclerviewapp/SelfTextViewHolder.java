package com.jackzhao.easyrecyclerviewapp;

import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jackzhao.easyrecyclerview.ViewHolder.BaseViewHolder;

public class SelfTextViewHolder extends BaseViewHolder<SelfTextData> {
    TextView tvTitle;
    TextView tvContent;

    public SelfTextViewHolder(ViewGroup viewGroup) {
        super(viewGroup);
    }


    @Override
    public ViewGroup getViewGroup(ViewGroup viewGroup) {
        return getViewGroupByResourceId(viewGroup, R.layout.item_self_text);
    }


    @Override
    public void bindData(SelfTextData data, ViewGroup viewGroup) {
        tvTitle = viewGroup.findViewById(R.id.tv_title);
        tvContent = viewGroup.findViewById(R.id.tv_content);
        tvTitle.setText(data.getTitle());
        viewGroup.setOnClickListener(view -> {
            Toast.makeText(viewGroup.getContext(),
                    data.getTitle() + " clicked.",
                    Toast.LENGTH_LONG).show();
        });
    }
}
