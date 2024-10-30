package com.oneday.network.okhttp.callback

import com.oneday.INetCallback
import okhttp3.Call
import okhttp3.Callback
import java.io.IOException

/**
 * Desc: OKHttp网络请求回调顶层接口；兼容Call为null的情况和其它非IOException的情况。
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/15 14:49
 */
interface OKHttpCallback : Callback, INetCallback {
    /**
     * 当网络请求发生异常时，回调该方法，在该方法内部进行异常分发。
     */
    fun onException(call: Call?, e: Exception) {
        call?.let {
            if (e is IOException) {
                onFailure(it, e)
            } else {
                onUnexpectedFailure(it, e)
            }
        } ?: onUnexpectedFailure(call, e)
    }

    /**
     * 当产生意想不到的的错误时，回调该方法。
     * 比如：
     * 1. 当call还为空时，就已经产生了其它错误；
     * 2. 当Exception类型不为IOException时，也会回调该方法。
     */
    fun onUnexpectedFailure(call: Call?, e: Exception)
}