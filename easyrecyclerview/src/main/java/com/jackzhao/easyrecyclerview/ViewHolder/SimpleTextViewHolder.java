package com.jackzhao.easyrecyclerview.ViewHolder;

import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jackzhao.easyrecyclerview.R;
import com.jackzhao.easyrecyclerview.data.SimpleTextData;

public class SimpleTextViewHolder extends BaseViewHolder<SimpleTextData> {
    TextView tvTitle;
    TextView tvContent;

    public SimpleTextViewHolder(ViewGroup viewGroup) {
        super(viewGroup);
    }


    @Override
    public ViewGroup getViewGroup(ViewGroup viewGroup) {
        return getViewGroupByResourceId(viewGroup, R.layout.item_simple_text);
    }


    @Override
    public void bindData(SimpleTextData data, ViewGroup viewGroup) {
        tvTitle = viewGroup.findViewById(R.id.tv_title);
        tvContent = viewGroup.findViewById(R.id.tv_content);
        tvTitle.setText(data.getTitle());
        tvContent.setText(data.getContent());
        viewGroup.setOnClickListener(view -> {
            Toast.makeText(viewGroup.getContext(),
                    data.getTitle() + " clicked.",
                    Toast.LENGTH_LONG).show();
        });
    }
}
