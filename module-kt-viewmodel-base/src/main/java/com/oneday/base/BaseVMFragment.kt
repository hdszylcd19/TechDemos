package com.oneday.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.oneday.internal.createViewModel
import com.permissionx.guolindev.PermissionX

/**
 * Desc: ViewModelFragment基类
 *
 * @author OneD
 * @version 1.0
 * @since 2021/12/29 14:20
 */
abstract class BaseVMFragment<VM : BaseViewModel> : Fragment() {

    protected lateinit var mViewModel: VM
    protected var mRootView: View? = null

    //---------------------------abstract methods-----------------------s

    /**
     * 初始化Fragment布局资源id
     *
     * @return 布局资源id
     */
    @LayoutRes
    abstract fun getLayoutResId(): Int

    /**
     * onCreateView()方法中初始化方法
     *
     * @param savedInstanceState Bundle参数
     */
    abstract fun init(savedInstanceState: Bundle?)
    //---------------------------abstract methods-----------------------e

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = createViewModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(getLayoutResId(), container, false)
        mRootView = rootView
        init(savedInstanceState)
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
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
}