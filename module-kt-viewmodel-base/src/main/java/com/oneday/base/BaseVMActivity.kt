package com.oneday.base

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.oneday.dialog.DialogLoadingViewHolder
import com.oneday.dialog.DialogProvider
import com.oneday.exception.AppException
import com.oneday.internal.createViewModel
import com.permissionx.guolindev.PermissionX

/**
 * Desc: ViewModelActivity基类
 *
 * @author OneD
 * @version 1.0
 * @since 2021/12/27 16:37
 */
abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var mViewModel: VM

    /*默认加载中dialogFragment*/
    protected val mLoadingDialog by lazy {
        val builder = DialogProvider.Builder().theme(R.style.LoadingDialog)
        builder.buildDialogFragment(DialogLoadingViewHolder(builder))
    }

    //---------------------------abstract methods-----------------------s

    /**
     * 初始化Activity布局资源id
     *
     * @return 布局资源id
     */
    @LayoutRes
    abstract fun getLayoutResId(): Int

    /**
     * onCreate()方法中初始化方法
     *
     * @param savedInstanceState onCreate()方法中的参数
     */
    abstract fun init(savedInstanceState: Bundle?)
    //---------------------------abstract methods-----------------------e

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = createViewModel(this)
        setContentView(getLayoutResId())
        init(savedInstanceState)
//        setImmersion(
//            isFullScreen(),
//            isLayoutFullScreen(),
//            isLightStatusBar(),
//            statusBarColor(),
//            getNavBarColor()
//        )


    }

    /**
     * 获取状态栏色值
     * color res id
     */
    protected open fun statusBarColor(): Int {
        return R.color.design_default_color_primary
    }

    /**
     * 获取状态栏色值
     * color res id
     * -1 不改变
     */
    protected open fun getNavBarColor(): Int {
        return -1
    }

    /**
     * 是否全屏
     */
    protected open fun isFullScreen(): Boolean {
        return false
    }

    /**
     * Layout是否全屏
     */
    protected open fun isLayoutFullScreen(): Boolean {
        return false
    }

    /**
     * 是否为浅色
     */
    protected open fun isLightStatusBar(): Boolean {
        val color = ContextCompat.getColor(this, statusBarColor())
        val darkness =
            1 - (0.2126 * Color.red(color) + 0.7152 * Color.green(color) + 0.0722 * Color.blue(color)) / 255
        return darkness < 0.3 || Color.alpha(color) < 0.3 * 255
    }

    /**
     * 请求动态权限
     * @param permissionList 动态权限数组
     * @param allGrantedCallback 所有权限都通过时的回调
     * @param deniedCallback 权限被拒绝时的回调
     */
    protected fun requestPermissions(
        permissionList: List<String>,
        allGrantedCallback: (() -> Unit)? = null,
        deniedCallback: ((List<String>) -> Unit)? = null
    ) {
        PermissionX.init(this)
            .permissions(permissionList)
            //设置完该值之后，当每次请求权限时，会优先进入onExplainRequestReason()方法，弹出解释权限申请原因的对话框，用户同意之后才会执行权限申请。
            .explainReasonBeforeRequest()
            .setDialogTintColor(Color.parseColor("#1972e8"), Color.parseColor("#8ab6f5"))
            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                // 所有被用户拒绝的权限会优先进入onExplainRequestReason()方法进行处理，应该在这个方法中再次解释申请这些权限的原因
                val message: String
                val positiveText: String
                val negativeText: String?
                if (beforeRequest) {
                    message = "我们的App运行必须依赖以下权限，请您务必同意！"
                    positiveText = "明白了"
                    negativeText = null
                } else {
                    message = "非常抱歉，这些权限是我们的App运行所必须的，还请您务必同意才行。"
                    positiveText = "同意"
                    negativeText = "滚，烦死了"
                }
                scope.showRequestReasonDialog(deniedList, message, positiveText, negativeText)
            }
            .onForwardToSettings { scope, deniedList ->
                // 所有被用户选择了“拒绝且不再询问”的权限都会进入到这个方法中处理，拒绝的权限都记录在deniedList中
                val message = "如下权限您已\"拒绝且不再询问\"，请在设置中打开这些权限，否则App将无法使用！"
                scope.showForwardToSettingsDialog(deniedList, message, "这就去", "我不")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    // 所有权限都授权通过时的成功回调
                    allGrantedCallback?.invoke()
                } else {
                    // 权限授权失败时回调
                    deniedCallback?.invoke(deniedList)
                }
            }
    }

    override fun getResources(): Resources {
        val resources = super.getResources()
        /*
            字体大小是否随系统改变可以通过Configuration类的fontScale变量来控制，
            fontScale变量默认为1，表示字体大小不随系统字体大小的改变而改变，那么我们只
            需要保证fontScale 始终为1即可。
         */
        if (resources.configuration.fontScale != 1f) { //fontScale不为1，需要强制设置为1
            val newConfig = Configuration()
            newConfig.setToDefaults() //设置成默认值，即fontScale为1
            resources.updateConfiguration(newConfig, resources.displayMetrics)
        }
        return resources
    }

    /**
     * 显示加载框
     */
    protected fun showProgress(vararg args: String?) {
        mLoadingDialog.show(supportFragmentManager)
    }

    /**
     * 隐藏加载框
     */
    protected fun hideProgress() {
        mLoadingDialog.dismiss()
    }

    /**
     * 解析LiveData状态数据
     * @param liveDataState     liveData数据状态
     * @param onSuccess         liveData成功回调
     * @param onSuccessEmpty    liveData成功数据为空回调
     * @param onNoNetwork       liveData没有网络错误回调
     * @param onError           liveData错误回调
     * @param onLoading         liveData加载中回调
     */
    protected fun <T> parseState(
        liveDataState: LiveDataState<T>,
        onSuccess: (T) -> Unit,
        onSuccessEmpty: (() -> Unit)? = null,
        onNoNetwork: (() -> Unit)? = null,
        onError: ((AppException) -> Unit)? = null,
        onLoading: (() -> Unit)? = null
    ) {
        when (liveDataState) {
            is LiveDataState.Loading -> {
                showProgress(liveDataState.loadingMsg)
                onLoading?.invoke()
            }
            is LiveDataState.Success -> {
                hideProgress()
                onSuccess(liveDataState.data)
            }
            is LiveDataState.SuccessEmpty -> {
                hideProgress()
                onSuccessEmpty?.invoke()
            }
            is LiveDataState.NoNetwork -> {
                hideProgress()
                onNoNetwork?.invoke()
            }
            is LiveDataState.Exception -> {
                hideProgress()
                onError?.invoke(liveDataState.error)
            }
        }
    }
}