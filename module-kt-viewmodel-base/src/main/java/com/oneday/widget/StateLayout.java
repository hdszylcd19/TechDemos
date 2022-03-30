package com.oneday.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.DrawableRes;

import com.oneday.anim.ViewAnimProvider;
import com.oneday.helper.AnimationHelper;
import com.oneday.helper.StateLayoutHelper;
import com.oneday.holder.ItemViewHolder;

/**
 * Desc: 状态根布局
 *
 * @author OneD
 * @version 1.0
 * @since 2022/3/30 13:09
 */
public class StateLayout extends FrameLayout {
    public static final int CONTENT_STATE = 0; /*正常内容类型*/
    public static final int ERROR_STATE = 1; /*错误类型*/
    public static final int EMPTY_STATE = 2;
    public static final int TIMEOUT_STATE = 3;
    public static final int NO_NETWORK_STATE = 4;

    private View contentView;
    private View emptyView;
    private View errorView;
    private View timeOutView;
    private View notNetworkView;
    private View currentShowingView;

    private StateItemInfo errorItem;
    private StateItemInfo noNetworkItem;
    private StateItemInfo emptyItem;
    private StateItemInfo timeOutItem;

    private ViewAnimProvider viewAnimProvider;
    private boolean useAnimation = false;

    private OnViewRefreshListener mListener;

    //************ callBack ************//
    public interface OnViewRefreshListener {
        //刷新界面
        void refreshClick();
    }

    public OnViewRefreshListener getRefreshLListener() {
        return mListener;
    }

    public void setRefreshListener(OnViewRefreshListener listener) {
        this.mListener = listener;
    }

    public void setErrorItem(StateItemInfo errorItem) {
        this.errorItem = errorItem;
    }

    public void setNoNetworkItem(StateItemInfo noNetworkItem) {
        this.noNetworkItem = noNetworkItem;
    }

    public void setEmptyItem(StateItemInfo emptyItem) {
        this.emptyItem = emptyItem;
    }

    public void setTimeOutItem(StateItemInfo timeOutItem) {
        this.timeOutItem = timeOutItem;
    }


    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        StateLayoutHelper.parseAttr(context, attrs, this);

        LayoutInflater inflater = LayoutInflater.from(context);

        errorView = StateLayoutHelper.getErrorView(inflater, errorItem, this);

        emptyView = StateLayoutHelper.getEmptyView(inflater, emptyItem);

        notNetworkView = StateLayoutHelper.getNoNetworkView(inflater, noNetworkItem, this);

        timeOutView = StateLayoutHelper.getTimeOutView(inflater, timeOutItem, this);
    }

    private void checkIsContentView(View view) {
        if (contentView == null && view != errorView && view != notNetworkView
                && view != timeOutView && view != emptyView) {
            contentView = view;
            currentShowingView = contentView;
        }
    }

    //************ showView ************//

    /**
     * 展示错误的界面
     */
    public void showErrorView() {
        if (errorView.getParent() == null) {
            addView(errorView);
        }
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, errorView);
        currentShowingView = errorView;
    }

    /**
     * 展示错误的界面
     *
     * @param msgId 提示语
     */
    public void showErrorView(int msgId) {
        if (errorView.getParent() == null) {
            addView(errorView);
        }
        setTipText(ERROR_STATE, msgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, errorView);
        currentShowingView = errorView;
    }

    public View getEmptyView() {
        return emptyView;
    }

    /**
     * 展示错误的界面
     *
     * @param msg 提示语
     */
    public void showErrorView(String msg) {
        if (errorView.getParent() == null) {
            addView(errorView);
        }
        setTipText(ERROR_STATE, msg);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, errorView);
        currentShowingView = errorView;
    }

    /**
     * 展示错误的界面
     *
     * @param msgId 提示语
     * @param imgId 图片Id
     */
    public void showErrorView(int msgId, int imgId) {
        if (errorView.getParent() == null) {
            addView(errorView);
        }
        setTipText(ERROR_STATE, msgId);
        setTipImg(ERROR_STATE, imgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, errorView);
        currentShowingView = errorView;
    }

    /**
     * 展示错误的界面
     *
     * @param msg   提示语
     * @param imgId 图片Id
     */
    public void showErrorView(String msg, int imgId) {
        if (errorView.getParent() == null) {
            addView(errorView);
        }
        setTipText(ERROR_STATE, msg);
        setTipImg(ERROR_STATE, imgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, errorView);
        currentShowingView = errorView;
    }

    /**
     * 展示空数据的界面
     */
    public void showEmptyView() {
        if (emptyView.getParent() == null) {
            addView(emptyView);
        }
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, emptyView);
        currentShowingView = emptyView;
    }

    /**
     * 展示空数据的界面
     *
     * @param msgId 提示语
     */
    public void showEmptyView(int msgId) {
        if (emptyView.getParent() == null) {
            addView(emptyView);
        }
        setTipText(EMPTY_STATE, msgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, emptyView);
        currentShowingView = emptyView;
    }

    /**
     * 展示空数据的界面
     *
     * @param msg 提示语
     */
    public void showEmptyView(String msg) {
        if (emptyView.getParent() == null) {
            addView(emptyView);
        }
        setTipText(EMPTY_STATE, msg);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, emptyView);
        currentShowingView = emptyView;
    }

    /**
     * 展示空数据的界面
     *
     * @param msgId 提示语
     * @param imgId 图片Id
     */
    public void showEmptyView(int msgId, int imgId) {
        if (emptyView.getParent() == null) {
            addView(emptyView);
        }
        setTipText(EMPTY_STATE, msgId);
        setTipImg(EMPTY_STATE, imgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, emptyView);
        currentShowingView = emptyView;
    }

    /**
     * 展示空数据的界面
     *
     * @param msg   提示语
     * @param imgId 图片Id
     */
    public void showEmptyView(String msg, int imgId) {
        if (emptyView.getParent() == null) {
            addView(emptyView);
        }
        setTipText(EMPTY_STATE, msg);
        setTipImg(EMPTY_STATE, imgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, emptyView);
        currentShowingView = emptyView;
    }

    /**
     * 展示超时的界面
     */
    public void showTimeoutView() {
        if (timeOutView.getParent() == null) {
            addView(timeOutView);
        }
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, timeOutView);
        currentShowingView = timeOutView;
    }

    /**
     * 展示超时的界面
     *
     * @param msgId 提示语
     */
    public void showTimeoutView(int msgId) {
        if (timeOutView.getParent() == null) {
            addView(timeOutView);
        }
        setTipText(TIMEOUT_STATE, msgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, timeOutView);
        currentShowingView = timeOutView;
    }

    /**
     * 展示超时的界面
     *
     * @param msg 提示语
     */
    public void showTimeoutView(String msg) {
        if (timeOutView.getParent() == null) {
            addView(timeOutView);
        }
        setTipText(TIMEOUT_STATE, msg);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, timeOutView);
        currentShowingView = timeOutView;
    }

    /**
     * 展示超时的界面
     *
     * @param msgId 提示语
     * @param imgId 图片Id
     */
    public void showTimeoutView(int msgId, int imgId) {
        if (timeOutView.getParent() == null) {
            addView(timeOutView);
        }
        setTipText(TIMEOUT_STATE, msgId);
        setTipImg(TIMEOUT_STATE, imgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, timeOutView);
        currentShowingView = timeOutView;
    }

    /**
     * 展示超时的界面
     *
     * @param msg   提示语
     * @param imgId 图片Id
     */
    public void showTimeoutView(String msg, int imgId) {
        if (timeOutView.getParent() == null) {
            addView(timeOutView);
        }
        setTipText(TIMEOUT_STATE, msg);
        setTipImg(TIMEOUT_STATE, imgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, timeOutView);
        currentShowingView = timeOutView;
    }

    /**
     * 展示没有网络的界面
     */
    public void showNoNetworkView() {
        if (notNetworkView.getParent() == null) {
            addView(notNetworkView);
        }
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, notNetworkView);
        currentShowingView = notNetworkView;
    }

    /**
     * 展示没有网络的界面
     *
     * @param msgId 提示语
     */
    public void showNoNetworkView(int msgId) {
        if (notNetworkView.getParent() == null) {
            addView(notNetworkView);
        }
        setTipText(NO_NETWORK_STATE, msgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, notNetworkView);
        currentShowingView = notNetworkView;
    }

    /**
     * 展示没有网络的界面
     *
     * @param msg 提示语
     */
    public void showNoNetworkView(String msg) {
        if (notNetworkView.getParent() == null) {
            addView(notNetworkView);
        }
        setTipText(NO_NETWORK_STATE, msg);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, notNetworkView);
        currentShowingView = notNetworkView;
    }

    /**
     * 展示没有网络的界面
     *
     * @param msgId 提示语
     * @param imgId 图片Id
     */
    public void showNoNetworkView(int msgId, int imgId) {
        if (notNetworkView.getParent() == null) {
            addView(notNetworkView);
        }
        setTipText(NO_NETWORK_STATE, msgId);
        setTipImg(NO_NETWORK_STATE, imgId);
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, notNetworkView);
        currentShowingView = notNetworkView;
    }

    /**
     * 展示内容的界面
     */
    public void showContentView() {
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, contentView);
        currentShowingView = contentView;
    }

    /**
     * 添加用户自定义的View
     *
     * @param view 自定义View
     */
    public void showCustomView(View view) {
        if (view.getParent() == null) { //当前的view没有父类
            addView(view);
        } else {
            view.setLayoutParams(this.getLayoutParams());
        }
        AnimationHelper.switchViewByAnim(useAnimation, viewAnimProvider, currentShowingView, view);
        currentShowingView = view;
    }

    //************ update ************//

    /**
     * 修改提示文字
     *
     * @param type 传入修改哪个
     * @param text 文字
     */
    public void setTipText(int type, String text) {
        if (text == null) { //text is null
            return;
        }
        switch (type) {
            case ERROR_STATE:
                ((ItemViewHolder) errorView.getTag()).tvTip.setText(text);
                break;
            case EMPTY_STATE:
                ((ItemViewHolder) emptyView.getTag()).tvTip.setText(text);
                break;
            case TIMEOUT_STATE:
                ((ItemViewHolder) timeOutView.getTag()).tvTip.setText(text);
                break;
            case NO_NETWORK_STATE:
                ((ItemViewHolder) notNetworkView.getTag()).tvTip.setText(text);
                break;
        }
    }

    /**
     * 修改提示文字
     *
     * @param type   传入修改哪个
     * @param textId 文字资源id
     */
    public void setTipText(int type, int textId) {
        switch (type) {
            case ERROR_STATE:
                ((ItemViewHolder) errorView.getTag()).tvTip.setText(textId);
                break;
            case EMPTY_STATE:
                ((ItemViewHolder) emptyView.getTag()).tvTip.setText(textId);
                break;
            case TIMEOUT_STATE:
                ((ItemViewHolder) timeOutView.getTag()).tvTip.setText(textId);
                break;
            case NO_NETWORK_STATE:
                ((ItemViewHolder) notNetworkView.getTag()).tvTip.setText(textId);
                break;
            default:
                break;
        }
    }

    /**
     * 设置提示图片资源
     *
     * @param type     传入修改哪个，除了Loading
     * @param drawable 图片资源drawable
     */
    public void setTipImg(int type, Drawable drawable) {
        switch (type) {
            case ERROR_STATE:
                ((ItemViewHolder) errorView.getTag()).ivImg.setBackground(drawable);
                break;
            case EMPTY_STATE:
                ((ItemViewHolder) emptyView.getTag()).ivImg.setBackground(drawable);
                break;
            case TIMEOUT_STATE:
                ((ItemViewHolder) timeOutView.getTag()).ivImg.setBackground(drawable);
                break;
            case NO_NETWORK_STATE:
                ((ItemViewHolder) notNetworkView.getTag()).ivImg.setBackground(drawable);
                break;
            default:
                break;
        }
    }

    /**
     * 设置提示图片资源
     *
     * @param stateType 传入修改哪个，除了Loading
     * @param imgId     图片资源id
     */
    public void setTipImg(int stateType, @DrawableRes int imgId) {
        switch (stateType) {
            case ERROR_STATE:
                ((ItemViewHolder) errorView.getTag()).ivImg.setImageResource(imgId);
                break;
            case EMPTY_STATE:
                ((ItemViewHolder) emptyView.getTag()).ivImg.setImageResource(imgId);
                break;
            case TIMEOUT_STATE:
                ((ItemViewHolder) timeOutView.getTag()).ivImg.setImageResource(imgId);
                break;
            case NO_NETWORK_STATE:
                ((ItemViewHolder) notNetworkView.getTag()).ivImg.setImageResource(imgId);
                break;
        }
    }

    //************ animation ************//

    public void setViewSwitchAnimProvider(ViewAnimProvider animProvider) {
        if (animProvider != null) {
            this.viewAnimProvider = animProvider;
        }
    }

    public ViewAnimProvider getViewAnimProvider() {
        return viewAnimProvider;
    }

    public boolean isUseAnimation() {
        return useAnimation;
    }

    public void setUseAnimation(boolean useAnimation) {
        this.useAnimation = useAnimation;
    }

    //************ addView ************//
    @Override
    public void addView(View child) {
        checkIsContentView(child);
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        checkIsContentView(child);
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        checkIsContentView(child);
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }
}
