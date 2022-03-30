package com.oneday.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.oneday.base.R;
import com.oneday.holder.ItemViewHolder;
import com.oneday.widget.StateItemInfo;
import com.oneday.widget.StateLayout;


public class StateLayoutHelper {

    /**
     * 解析布局中的可选参数
     */
    public static void parseAttr(@NonNull Context context, AttributeSet attrs, StateLayout stateLayout) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateLayout);
        try {
            int errorImg = a.getResourceId(R.styleable.StateLayout_errorImg, -1);
            String errorText = a.getString(R.styleable.StateLayout_errorText);
            stateLayout.setErrorItem(new StateItemInfo(errorImg, errorText));

            int timeOutImg = a.getResourceId(R.styleable.StateLayout_timeOutImg, -1);
            String timeOutText = a.getString(R.styleable.StateLayout_timeOutText);
            stateLayout.setTimeOutItem(new StateItemInfo(timeOutImg, timeOutText));

            int emptyImg = a.getResourceId(R.styleable.StateLayout_emptyImg, -1);
            String emptyText = a.getString(R.styleable.StateLayout_emptyText);
            stateLayout.setEmptyItem(new StateItemInfo(emptyImg, emptyText));

            int noNetworkImg = a.getResourceId(R.styleable.StateLayout_noNetworkImg, -1);
            String noNetworkText = a.getString(R.styleable.StateLayout_noNetworkText);
            stateLayout.setNoNetworkItem(new StateItemInfo(noNetworkImg, noNetworkText));
        } finally {
            a.recycle();
        }
    }

    /**
     * 获取初始的错误View
     *
     * @param layoutInflater 布局填充器
     * @param item           错误bean
     * @param layout         容器
     * @return 错误View
     */
    public static View getErrorView(LayoutInflater layoutInflater, StateItemInfo item,
                                    final StateLayout layout) {
        View view = layoutInflater.inflate(R.layout.state_layout_error_def, null);
        return getView(item, layout, view);
    }

    private static View getView(StateItemInfo item, final StateLayout layout, View view) {
        if (item != null) {
            ItemViewHolder holder = new ItemViewHolder(view);
            view.setTag(holder);

            if (!TextUtils.isEmpty(item.getTip())) {
                holder.tvTip.setText(item.getTip());
            }
            if (item.getResId() != -1) {
                holder.ivImg.setImageResource(item.getResId());
            }
            view.setOnClickListener(v -> {
                if (layout.getRefreshLListener() != null) {
                    layout.getRefreshLListener().refreshClick();
                }
            });
        }
        return view;
    }

    /**
     * 获取初始的没有网络View
     *
     * @param layoutInflater 布局填充器
     * @param item           没有网络bean
     * @param layout         容器
     * @return 没有网络View
     */
    public static View getNoNetworkView(LayoutInflater layoutInflater, StateItemInfo item,
                                        final StateLayout layout) {
        View view = layoutInflater.inflate(R.layout.state_layout_no_network_def, null);
        return getView(item, layout, view);
    }

    /**
     * 获取初始的超时View
     *
     * @param layoutInflater 布局填充器
     * @param item           超时bean
     * @param layout         容器
     * @return 超时View
     */
    public static View getTimeOutView(LayoutInflater layoutInflater, StateItemInfo item,
                                      final StateLayout layout) {
        View view = layoutInflater.inflate(R.layout.state_layout_error_def, null);
        return getView(item, layout, view);
    }

    /**
     * 获取初始的空数据View
     *
     * @param layoutInflater 布局填充器
     * @param item           空数据bean
     * @return 空数据View
     */
    public static View getEmptyView(LayoutInflater layoutInflater, StateItemInfo item) {
        View view = layoutInflater.inflate(R.layout.state_layout_empty_def, null);
        if (item != null) {
            ItemViewHolder holder = new ItemViewHolder(view);
            view.setTag(holder);

            if (!TextUtils.isEmpty(item.getTip())) {
                holder.tvTip.setText(item.getTip());
            }
            if (item.getResId() != -1) {
                holder.ivImg.setImageResource(item.getResId());
            }
        }
        return view;
    }
}
