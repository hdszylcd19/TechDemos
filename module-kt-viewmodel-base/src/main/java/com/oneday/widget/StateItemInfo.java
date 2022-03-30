package com.oneday.widget;

import androidx.annotation.DrawableRes;

public class StateItemInfo {
    @DrawableRes
    private int resId; /*图片资源id*/
    private String tip; /*提示文字*/

    @DrawableRes
    public int getResId() {
        return resId;
    }

    public void setResId(@DrawableRes int resId) {
        this.resId = resId;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public StateItemInfo() {
    }

    public StateItemInfo(String tip) {
        this.tip = tip;
    }

    public StateItemInfo(int resId, String tip) {
        this.resId = resId;
        this.tip = tip;
    }
}