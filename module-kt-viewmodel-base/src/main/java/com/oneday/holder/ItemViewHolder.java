package com.oneday.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oneday.base.R;


public class ItemViewHolder {

    public ImageView ivImg;
    public TextView tvTip;

    public ItemViewHolder(View view) {
        tvTip = (TextView) view.findViewById(R.id.tv_message);
        ivImg = (ImageView) view.findViewById(R.id.iv_img);
    }

}
