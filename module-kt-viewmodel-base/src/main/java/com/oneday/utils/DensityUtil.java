package com.oneday.utils;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.NonNull;

import com.oneday.base.BaseApplication;

/**
 * Desc:
 *
 * @author OneD
 * @version 1.0
 * @since 2022/3/30 13:09
 */
public class DensityUtil {

    /**
     * dp 转出的 像素值
     *
     * @param dpVal 具体dp 值
     * @return dp 转出的 像素值
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, BaseApplication.sApplication.getResources().getDisplayMetrics());
    }

    /**
     * dp 转 px,需传 context 的重载方法
     *
     * @param context 上下文
     * @param dpVal   具体dp 值
     * @return dp 转出的 像素值
     */
    public static int dp2px(@NonNull Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal px值
     * @return dp值
     */
    public static float px2dp(float pxVal) {
        final float scale = BaseApplication.sApplication.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }


    /**
     * sp转px
     *
     * @param spVal sp值
     * @return px值
     */
    public static int sp2px(@NonNull Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px, 默认使用 XHBaseApplication 的上下文
     *
     * @param spVal sp值
     * @return px值
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, BaseApplication.sApplication.getResources().getDisplayMetrics());
    }


    /**
     * px转sp
     *
     * @param pxVal px值
     * @return sp值
     */
    public static float px2sp(float pxVal) {
        return (pxVal / BaseApplication.sApplication.getResources().getDisplayMetrics().scaledDensity);
    }
}
