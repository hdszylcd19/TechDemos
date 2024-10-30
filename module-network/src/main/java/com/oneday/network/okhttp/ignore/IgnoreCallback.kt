package com.oneday.network.okhttp.ignore

/**
 * Desc: 网络请求Ignore回调接口
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/15 9:59
 */
interface IgnoreCallback {
    /**
     * 网络请求由于某些原因（如防抖动），需要ignore掉当前请求时回调该方法
     *
     * @param reason ignore的原因
     */
    fun onIgnore(reason: String)
}