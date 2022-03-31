package com.oneday.anim;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

/**
 * Desc:StateLayout状态根布局中，StateView切换时，逐渐消失并且缩放效果的动画提供类
 *
 * @author OneD
 * @version 1.0
 * @since 2022/3/30 13:09
 */
public class FadeScaleStateViewAnimProvider implements StateViewAnimProvider {

    public Animation showAnimation() {
        AnimationSet set = new AnimationSet(true);
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        Animation scaleAnimation = new ScaleAnimation(0.1f, 1f, 0.1f, 1f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        set.setDuration(200);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        return set;
    }

    @Override
    public Animation hideAnimation() {
        AnimationSet set = new AnimationSet(true);
        Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        Animation scaleAnimation = new ScaleAnimation(1.0f, 0.1f, 1.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        set.setDuration(200);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        return set;
    }
}
