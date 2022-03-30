package com.oneday.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.oneday.base.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * 创建ViewModel
 */
internal fun <VM : BaseViewModel> createViewModel(owner: ViewModelStoreOwner): VM {
    val vmClazz: Class<VM> = getVmClazz(owner)
    return ViewModelProvider(owner).get(vmClazz)
}

/**
 * 获取当前类绑定的泛型ViewModel-clazz
 */
@Suppress("UNCHECKED_CAST")
internal fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}