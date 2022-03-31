package com.oneday.helper;

import android.view.View;
import android.view.animation.Animation;

import com.oneday.anim.FadeScaleStateViewAnimProvider;
import com.oneday.anim.StateViewAnimProvider;

/**
 * Desc: 状态根布局中，StateView切换时动画帮助类
 *
 * @author OneD
 * @version 1.0
 * @since 2022/3/30 13:09
 */
public class StateLayoutAnimHelper {

    /**
     * 切换布局
     *
     * @param useAnim               是否使用动画；true表示使用
     * @param stateViewAnimProvider StateLayout动画提供类
     * @param hideView              需要隐藏的View
     * @param showView              需要显示的View
     */
    public static void switchViewByAnim(boolean useAnim, StateViewAnimProvider stateViewAnimProvider,
                                        final View hideView, View showView) {
        if (hideView == showView) {
            return;
        }

        if (useAnim) {
            if (stateViewAnimProvider == null) {
                //使用默认动画
                stateViewAnimProvider = new FadeScaleStateViewAnimProvider();
            }
            Animation hideAnimation = stateViewAnimProvider.hideAnimation();
            Animation showAnimation = stateViewAnimProvider.showAnimation();

            if (hideView != null) {
                if (hideAnimation != null) {
                    hideAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            hideView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    hideAnimation.setFillAfter(false);
                    hideView.startAnimation(hideAnimation);
                } else {
                    hideView.setVisibility(View.GONE);
                }
            }
            if (showView != null) {
                if (showView.getVisibility() != View.VISIBLE) {
                    showView.setVisibility(View.VISIBLE);
                }
                if (showAnimation != null) {
                    showAnimation.setFillAfter(false);
                    showView.startAnimation(showAnimation);
                }
            }
        } else {
            if (hideView != null) {
                hideView.setVisibility(View.GONE);
            }
            if (showView != null) {
                showView.setVisibility(View.VISIBLE);
            }
        }
    }
}
