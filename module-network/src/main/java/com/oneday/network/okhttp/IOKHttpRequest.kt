package com.oneday.network.okhttp

import com.oneday.network.INetRequest
import com.oneday.network.okhttp.callback.OKHttpCallback
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Desc: OkHttp网络请求顶层接口
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/13 13:54
 */
interface IOKHttpRequest<C : IOKHttpRequest<C>> : INetRequest<C> {
    /**
     * 添加OKHttp拦截器，增量添加，不会覆盖已有拦截器；
     *
     * @param interceptor 拦截器
     *
     * @return 返回当前对象
     */
    fun addInterceptor(interceptor: Interceptor): C

    /**
     * 批量添加OKHttp拦截器，增量添加，不会覆盖已有拦截器；
     *
     * @param  interceptors 拦截器集合
     *
     * @return  返回当前对象
     */
    fun addInterceptors(interceptors: List<Interceptor>): C

    /**
     * 设置OKHttp批量拦截器，会覆盖已有拦截器，全量替换。
     *
     * @param interceptors  拦截器集合
     *
     * @return  返回当前对象
     */
    fun setInterceptors(interceptors: List<Interceptor>): C

    /**
     * 添加OKHttp网络拦截器，增量添加，不会覆盖已有拦截器；
     *
     * @param interceptor 网络拦截器
     *
     * @return 返回当前对象
     */
    fun addNetworkInterceptor(interceptor: Interceptor): C

    /**
     * 批量添加OKHttp网络拦截器，增量添加，不会覆盖已有拦截器；
     *
     * @param interceptors 网络拦截器集合
     *
     * @return 返回当前对象
     */
    fun addNetworkInterceptors(interceptors: List<Interceptor>): C

    /**
     * 设置OKHttp批量网络拦截器，会覆盖已有拦截器，全量替换。
     *
     * @param interceptors  网络拦截器集合
     *
     * @return  返回当前对象
     */
    fun setNetworkInterceptors(interceptors: List<Interceptor>): C

    /**
     * 设置网络请求防抖标识；在前一个同类型标识的请求没有返回之前，后续同类型标识的请求直接ignore掉，
     * 通过onIgnore回调通知调用方。
     *
     * @param requestIdentity 网络防抖的请求标识
     */
    fun antiShakeRequestIdentity(requestIdentity: String): C

    /**
     * Desc:当前网络请求是否已经开启请求并且尚未返回；该方法主要用于网络防抖
     *
     * @param requestIdentity 网络请求的类型标识
     *
     * @return 已经开启请求尚未返回时返回true；否则返回false
     */
    fun isAlreadyRequested(requestIdentity: String): Boolean

    /**
     * Desc: 执行异步网络请求。
     *
     * @param  callback 当执行网络异步请求时，必须传递回调接口；否则该次请求将毫无意义
     */
    fun requestAsync(callback: OKHttpCallback)

    /**
     * Desc: 执行同步网络请求
     *
     * @return 返回同步网络请求结果Response对象
     */
    @Throws(IOException::class)
    fun requestSync(): Response
}