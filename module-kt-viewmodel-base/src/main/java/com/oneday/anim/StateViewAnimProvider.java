package com.oneday.anim;

import android.view.animation.Animation;

/**
 * Desc: 状态根布局中，StateView切换时动画提供类
 *
 * @author OneD
 * @version 1.0
 * @since 2022/3/30 13:09
 */
public interface StateViewAnimProvider {

    /**
     * StateView显示时的动画
     *
     * @return StateView显示时的动画
     */
    Animation showAnimation();

    /**
     * StateView隐藏时的动画
     *
     * @return StateView隐藏时的动画
     */
    Animation hideAnimation();
}
