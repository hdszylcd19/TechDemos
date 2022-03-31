package com.oneday.anim;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

/**
 * Desc:StateLayout状态根布局中，StateView切换时，逐渐消失效果的动画提供类
 *
 * @author OneD
 * @version 1.0
 * @since 2022/3/30 13:09
 */
public class FadeStateViewAnimProvider implements StateViewAnimProvider {

    @Override
    public Animation showAnimation() {
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(200);
        animation.setInterpolator(new DecelerateInterpolator());
        return animation;
    }

    @Override
    public Animation hideAnimation() {
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(200);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        return animation;
    }

}
