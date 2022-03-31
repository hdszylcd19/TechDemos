package com.oneday.widget;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.oneday.anim.StateViewAnimProvider;
import com.oneday.base.R;
import com.oneday.helper.StateLayoutAnimHelper;

/**
 * Desc: 状态根布局
 *
 * @author OneD
 * @version 1.0
 * @since 2022/3/30 13:09
 */
public class StateLayout extends FrameLayout {
    public static final int CONTENT_STATE = 0; /*正常内容状态类型*/
    public static final int ERROR_STATE = 1; /*错误状态类型*/
    public static final int EMPTY_STATE = 2; /*数据为空状态类型*/
    public static final int TIMEOUT_STATE = 3; /*超时状态类型*/
    public static final int NO_NETWORK_STATE = 4; /*没有网络状态类型*/

    private final SparseIntArray mStateLayoutIds = new SparseIntArray();
    private final SparseArray<View> mStateViews = new SparseArray<>();
    private final SparseArray<StateInfo> mStateInfos = new SparseArray<>();

    private int mCurState = -1; //当前状态码
    private View mCurShowingView; //当前显示中的View

    private StateViewAnimProvider mStateViewAnimProvider;
    private boolean useAnimation = false;

    private StateRefreshListener mStateListener;

    //----------------inner class------------------s

    /**
     * 状态刷新监听接口
     */
    public interface StateRefreshListener {
        /**
         * 当点击异常状态刷新时，回调该方法
         *
         * @param stateLayout 状态根布局
         */
        void onStateRefresh(@NonNull StateLayout stateLayout);
    }

    /*状态布局描述信息*/
    private static class StateInfo {
        int state = -1; /*状态码；默认值为-1，表示无效状态码*/
        @DrawableRes
        int resId; /*图片资源id；默认值为0，表示为无效资源*/
        String tip; /*提示文字*/

        private StateInfo() {
        }

        private StateInfo(int state, int resId, String tip) {
            this.state = state;
            this.resId = resId;
            this.tip = tip;
        }
    }

    //----------------inner class------------------e

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttr(context, attrs);
    }

    /**
     * 解析布局中的可选参数
     */
    private void parseAttr(@NonNull Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateLayout);
        try {
            int defResId = 0;

            int errLayoutId = a.getResourceId(R.styleable.StateLayout_err_layout, R.layout.state_layout_error_def);
            mStateLayoutIds.put(ERROR_STATE, errLayoutId);

            int errorImg = a.getResourceId(R.styleable.StateLayout_err_img, defResId);
            String errorText = a.getString(R.styleable.StateLayout_err_tip);
            if (errorImg != defResId || !TextUtils.isEmpty(errorText)) {
                setErrorState(errorImg, errorText);
            }

            int timeoutLayoutId = a.getResourceId(R.styleable.StateLayout_timeout_layout, R.layout.state_layout_timeout_def);
            mStateLayoutIds.put(TIMEOUT_STATE, timeoutLayoutId);

            int timeoutImg = a.getResourceId(R.styleable.StateLayout_timeout_img, defResId);
            String timeOutText = a.getString(R.styleable.StateLayout_timeout_tip);
            if (timeoutImg != defResId || !TextUtils.isEmpty(timeOutText)) {
                setTimeOutState(timeoutImg, timeOutText);
            }

            int emptyLayoutId = a.getResourceId(R.styleable.StateLayout_empty_layout, R.layout.state_layout_empty_def);
            mStateLayoutIds.put(EMPTY_STATE, emptyLayoutId);

            int emptyImg = a.getResourceId(R.styleable.StateLayout_empty_img, defResId);
            String emptyText = a.getString(R.styleable.StateLayout_empty_tip);
            if (emptyImg != defResId || !TextUtils.isEmpty(emptyText)) {
                setEmptyState(emptyImg, emptyText);
            }

            int noNetworkLayoutId = a.getResourceId(R.styleable.StateLayout_no_network_layout, R.layout.state_layout_no_network_def);
            mStateLayoutIds.put(NO_NETWORK_STATE, noNetworkLayoutId);

            int noNetworkImg = a.getResourceId(R.styleable.StateLayout_no_network_img, defResId);
            String noNetworkText = a.getString(R.styleable.StateLayout_no_network_tip);
            if (noNetworkImg != defResId || !TextUtils.isEmpty(noNetworkText)) {
                setNoNetworkState(noNetworkImg, noNetworkText);
            }
        } finally {
            a.recycle();
        }
    }

    public int getCurState() {
        return mCurState;
    }

    public StateRefreshListener getStateRefreshLListener() {
        return mStateListener;
    }

    public void setStateRefreshListener(StateRefreshListener listener) {
        this.mStateListener = listener;
    }

    @NonNull
    private View getStateView(int state) {
        View stateView = mStateViews.get(state);
        if (stateView == null) {
            synchronized (this) {
                stateView = getStateView(state, mStateInfos.get(state));
                mStateViews.put(state, stateView);
            }
        }

        return stateView;
    }

    @NonNull
    private View getStateView(int state, StateInfo stateInfo) {
        View stateView = View.inflate(getContext(), getStateLayoutId(state), null);
        if (stateInfo != null) {
            ImageView stateIv = stateView.findViewById(R.id.state_iv_img);
            if (stateIv != null && stateInfo.resId != 0) {
                stateIv.setImageResource(stateInfo.resId);

                // 设置异常状态图片按钮点击监听
                stateIv.setOnClickListener(v -> {
                    if (mStateListener != null) {
                        mStateListener.onStateRefresh(StateLayout.this);
                    }
                });
            }

            TextView stateTv = stateView.findViewById(R.id.state_tv_msg);
            if (stateTv != null) {
                stateTv.setText(stateInfo.tip);
            }
        }

        // 设置状态码
        stateView.setTag(R.id.state_tag, state);
        return stateView;
    }

    private int getStateLayoutId(int state) {
        Integer layoutId = mStateLayoutIds.get(state);
        if (layoutId == null) {
            throw new IllegalArgumentException("[StateLayout]未知状态类型");
        }

        return layoutId;
    }

    @Nullable
    private StateInfo getStateInfo(int state) {
        return mStateInfos.get(state);
    }

    public void setErrorState(@DrawableRes int resId, String tip) {
        setStateInfo(ERROR_STATE, resId, tip);
    }

    public void setNoNetworkState(@DrawableRes int resId, String tip) {
        setStateInfo(NO_NETWORK_STATE, resId, tip);
    }

    public void setEmptyState(@DrawableRes int resId, String tip) {
        setStateInfo(EMPTY_STATE, resId, tip);
    }

    public void setTimeOutState(@DrawableRes int resId, String tip) {
        setStateInfo(TIMEOUT_STATE, resId, tip);
    }

    /**
     * @param state 状态码
     * @param resId 图片资源id
     * @param tip   提示文字
     */
    private void setStateInfo(int state, @DrawableRes int resId, String tip) {
        StateInfo stateInfo = mStateInfos.get(state);
        if (stateInfo == null) {
            synchronized (this) {
                stateInfo = new StateInfo(state, resId, tip);
                mStateInfos.put(state, stateInfo);
            }
        } else {
            synchronized (this) {
                stateInfo.resId = resId;
                stateInfo.tip = tip;
            }
        }
    }

    //************ showStateView ************//

    /**
     * 展示错误的界面
     *
     * @param msg 提示语
     */
    public void showErrorView(String msg) {
        showErrorView();
        setTipText(ERROR_STATE, msg);
    }

    /**
     * 展示错误的界面
     *
     * @param msgId 提示语
     */
    public void showErrorView(@StringRes int msgId) {
        showErrorView();
        setTipText(ERROR_STATE, msgId);
    }

    /**
     * 展示错误的界面
     *
     * @param msgId 提示语
     * @param imgId 图片Id
     */
    public void showErrorView(@StringRes int msgId, @DrawableRes int imgId) {
        showErrorView();
        setTipText(ERROR_STATE, msgId);
        setTipImg(ERROR_STATE, imgId);
    }

    /**
     * 展示错误的界面
     *
     * @param msg   提示语
     * @param imgId 图片Id
     */
    public void showErrorView(String msg, @DrawableRes int imgId) {
        showErrorView();
        setTipText(ERROR_STATE, msg);
        setTipImg(ERROR_STATE, imgId);
    }

    /**
     * 展示错误的界面
     */
    public void showErrorView() {
        showStateView(ERROR_STATE);
    }

    /**
     * 展示空数据的界面
     *
     * @param msgId 提示语
     */
    public void showEmptyView(@StringRes int msgId) {
        showEmptyView();
        setTipText(EMPTY_STATE, msgId);
    }

    /**
     * 展示空数据的界面
     *
     * @param msg 提示语
     */
    public void showEmptyView(String msg) {
        showEmptyView();
        setTipText(EMPTY_STATE, msg);
    }

    /**
     * 展示空数据的界面
     *
     * @param msgId 提示语
     * @param imgId 图片Id
     */
    public void showEmptyView(@StringRes int msgId, @DrawableRes int imgId) {
        showEmptyView();
        setTipText(EMPTY_STATE, msgId);
        setTipImg(EMPTY_STATE, imgId);
    }

    /**
     * 展示空数据的界面
     *
     * @param msg   提示语
     * @param imgId 图片Id
     */
    public void showEmptyView(String msg, @DrawableRes int imgId) {
        showEmptyView();
        setTipText(EMPTY_STATE, msg);
        setTipImg(EMPTY_STATE, imgId);
    }

    /**
     * 展示空数据的界面
     */
    public void showEmptyView() {
        showStateView(EMPTY_STATE);
    }

    /**
     * 展示超时的界面
     */
    public void showTimeoutView() {
        showStateView(TIMEOUT_STATE);
    }

    /**
     * 展示超时的界面
     *
     * @param msgId 提示语
     */
    public void showTimeoutView(@StringRes int msgId) {
        showTimeoutView();
        setTipText(TIMEOUT_STATE, msgId);
    }

    /**
     * 展示超时的界面
     *
     * @param msg 提示语
     */
    public void showTimeoutView(String msg) {
        showTimeoutView();
        setTipText(TIMEOUT_STATE, msg);
    }

    /**
     * 展示超时的界面
     *
     * @param msgId 提示语
     * @param imgId 图片Id
     */
    public void showTimeoutView(@StringRes int msgId, @DrawableRes int imgId) {
        showTimeoutView();
        setTipText(TIMEOUT_STATE, msgId);
        setTipImg(TIMEOUT_STATE, imgId);
    }

    /**
     * 展示超时的界面
     *
     * @param msg   提示语
     * @param imgId 图片Id
     */
    public void showTimeoutView(String msg, @DrawableRes int imgId) {
        showTimeoutView();
        setTipText(TIMEOUT_STATE, msg);
        setTipImg(TIMEOUT_STATE, imgId);
    }

    /**
     * 展示没有网络的界面
     */
    public void showNoNetworkView() {
        showStateView(NO_NETWORK_STATE);
    }

    /**
     * 展示没有网络的界面
     *
     * @param msgId 提示语
     */
    public void showNoNetworkView(@StringRes int msgId) {
        showNoNetworkView();
        setTipText(NO_NETWORK_STATE, msgId);
    }

    /**
     * 展示没有网络的界面
     *
     * @param msg 提示语
     */
    public void showNoNetworkView(String msg) {
        showNoNetworkView();
        setTipText(NO_NETWORK_STATE, msg);
    }

    /**
     * 展示没有网络的界面
     *
     * @param msgId 提示语
     * @param imgId 图片Id
     */
    public void showNoNetworkView(@StringRes int msgId, @DrawableRes int imgId) {
        showNoNetworkView();
        setTipText(NO_NETWORK_STATE, msgId);
        setTipImg(NO_NETWORK_STATE, imgId);
    }

    /**
     * 展示内容的界面
     */
    public void showContentView() {
        showStateView(CONTENT_STATE);
    }

    private void showStateView(int state) {
        View stateView = addStateView(state);
        StateLayoutAnimHelper.switchViewByAnim(useAnimation, mStateViewAnimProvider, mCurShowingView, stateView);
        mCurState = state;
        mCurShowingView = stateView;
    }

    //************ update ************//

    /**
     * 修改提示文字
     *
     * @param state  状态码
     * @param textId 文字资源id
     */
    public void setTipText(int state, @StringRes int textId) {
        setTipText(state, getResources().getString(textId));
    }

    /**
     * 修改提示文字
     *
     * @param state 传入修改哪个
     * @param text  文字
     */
    public void setTipText(int state, String text) {
        View stateView = getStateView(state);
        TextView stateTv = stateView.findViewById(R.id.state_tv_msg);
        if (stateTv != null) {
            stateTv.setText(text);
        }
    }

    /**
     * 设置提示图片资源
     *
     * @param state 状态码
     * @param imgId 图片资源id
     */
    public void setTipImg(int state, @DrawableRes int imgId) {
        View stateView = getStateView(state);
        ImageView stateIv = stateView.findViewById(R.id.state_iv_img);
        if (stateIv != null) {
            stateIv.setImageResource(imgId);
        }
    }

    /**
     * 设置提示图片资源
     *
     * @param state    状态码
     * @param drawable 图片资源drawable
     */
    public void setTipImg(int state, Drawable drawable) {
        View stateView = getStateView(state);
        ImageView stateIv = stateView.findViewById(R.id.state_iv_img);
        if (stateIv != null) {
            stateIv.setBackground(drawable);
        }
    }

    //************ animation ************//

    public void setViewSwitchAnimProvider(StateViewAnimProvider animProvider) {
        if (animProvider != null) {
            this.mStateViewAnimProvider = animProvider;
        }
    }

    public StateViewAnimProvider getViewAnimProvider() {
        return mStateViewAnimProvider;
    }

    public boolean isUseAnimation() {
        return useAnimation;
    }

    public void setUseAnimation(boolean useAnimation) {
        this.useAnimation = useAnimation;
    }

    //************ addView ************//

    // 除了异常状态，其它的都认为是内容状态码
    private boolean isContentState(int state) {
        switch (state) {
            case ERROR_STATE:
            case EMPTY_STATE:
            case TIMEOUT_STATE:
            case NO_NETWORK_STATE:
                return false;
            case CONTENT_STATE:
            default:
                return true;
        }
    }

    private void checkIsContentView(View view) {
        if (view != null) {
            Object tag = view.getTag(R.id.state_tag);
            if (tag == null || isContentState((Integer) tag)) {
                view.setTag(R.id.state_tag, CONTENT_STATE);
                mStateViews.put(CONTENT_STATE, view);
            }
        }
    }

    private View addStateView(int state) {
        View stateView = getStateView(state);
        if (stateView.getParent() == null) {
            // 异常状态布局位于根布局中间位置
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            super.addView(stateView, params);
        }

        return stateView;
    }

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
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params,
                                      boolean preventRequestLayout) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }
}
