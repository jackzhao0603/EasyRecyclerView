package com.jackzhao.easyrecyclerview.ViewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jackzhao.easyrecyclerview.R;

public class OneLineTextViewHolder extends BaseViewHolder<String> {

    TextView tvContent;

    public OneLineTextViewHolder(ViewGroup viewGroup) {
        super(viewGroup);
        tvContent = itemView.findViewById(R.id.tv_content);
    }

    @Override
    public ViewGroup getViewGroup(ViewGroup viewGroup) {
        return getViewGroupByResourceId(viewGroup, R.layout.item_one_line_text);
    }

    @Override
    public void bindData(String data, ViewGroup viewGroup) {
        tvContent = viewGroup.findViewById(R.id.tv_content);
        tvContent.setText(data);
        viewGroup.setOnClickListener(view -> {
            Toast.makeText(viewGroup.getContext(),
                    data + " clicked.",
                    Toast.LENGTH_LONG).show();
        });
    }
}
