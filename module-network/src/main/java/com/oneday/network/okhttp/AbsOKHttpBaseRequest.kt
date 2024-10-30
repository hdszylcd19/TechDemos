package com.oneday.network.okhttp

import com.oneday.network.AbsBaseRequest
import com.oneday.network.dns.BootstrapDNS
import com.oneday.network.okhttp.callback.OKHttpCallback
import com.oneday.network.okhttp.ignore.TagAntiShakeRequestIdentity
import com.oneday.utils.CommonUtils.objectToString
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Desc: 网络请求OKHttp实现抽象基类
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/14 10:16
 */
@Suppress("UNCHECKED_CAST")
abstract class AbsOKHttpBaseRequest<C : AbsOKHttpBaseRequest<C>> : AbsBaseRequest<C>(),
    IOKHttpRequest<C> {
    private val tag: String = javaClass.simpleName

    /**
     * 是否为异步请求；true为异步，false为同步；默认值为true
     */
    protected var isAsync: Boolean = true

    protected var requestBody: RequestBody? = null

    /**
     * 调用方设置的OkHttpClient
     */
    private var callerOkHttpClient: OkHttpClient? = null

    /**
     * 每个网络请求单独的网络拦截器，如果设置了，需要重新构建新的OkHttpClient对象并进行设置
     */
    private var networkInterceptors: MutableList<Interceptor> = mutableListOf()

    /**
     * 每个网络请求单独的拦截器，如果设置了，需要重新构建新的OkHttpClient对象并进行设置
     */
    private var interceptors: MutableList<Interceptor> = mutableListOf()

    /**
     * OkHttp网络请求Request构建器
     */
    private val okHttpRequestBuilder = OKHttpRequestBuilder()

    /**
     * OKHttp网络请求Request
     */
    protected var request: Request? = null

    /**
     * OKHttp网络请求Call
     */
    protected var call: Call? = null

    /**
     * 设置调用方自己的OkHttpClient
     */
    fun okHttpClient(callerOkHttpClient: OkHttpClient): C {
        this.callerOkHttpClient = callerOkHttpClient
        return this as C
    }

    private fun getInnerDefOkHttpClient(): OkHttpClient {
        // TODO: 2020/4/14 待构建默认OkHttpClient
        return OkHttpClient()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return callerOkHttpClient ?: getInnerDefOkHttpClient()
    }

    private fun tryBuildInterceptors(builder: OkHttpClient.Builder) {
        interceptors.forEach {
            builder.addInterceptor(it)
        }
    }

    private fun tryBuildNetworkInterceptors(builder: OkHttpClient.Builder) {
        networkInterceptors.forEach {
            builder.addNetworkInterceptor(it)
        }
    }

    private fun tryBuildTimeout(builder: OkHttpClient.Builder) {
        if (connectTimeout > 0) {
            builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
        }

        if (readTimeout > 0) {
            builder.readTimeout(readTimeout, TimeUnit.MILLISECONDS)
        }

        if (writeTimeout > 0) {
            builder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
        }
    }

    private fun tryBuildForceIp(builder: OkHttpClient.Builder) {
        if (forceIp.isNotEmpty()) {
            builder.dns(BootstrapDNS(forceIp))
        }
    }

    private fun tryBuildConnectionKeepALive(builder: OkHttpClient.Builder) {
        if (connectionKeepAliveMillis > 0) {
            builder.connectionPool(
                ConnectionPool(
                    TimeUnit.MINUTES.toMillis(5L).toInt(),
                    connectionKeepAliveMillis,
                    TimeUnit.MILLISECONDS
                )
            )
        } else if (connectionKeepAliveMillis == 0L) {
            addHeader("Connection", "close")
        }
    }

    protected fun buildOkHttpNewClient(): OkHttpClient {
        return if (interceptors.isEmpty() && networkInterceptors.isEmpty() &&
            connectTimeout <= 0 && readTimeout <= 0 && writeTimeout <= 0 &&
            connectionKeepAliveMillis == -1L && forceIp.isEmpty()
        ) {
            return getOkHttpClient()
        } else {
            val okHttpClient = getOkHttpClient()
            val builder = okHttpClient.newBuilder()
            tryBuildInterceptors(builder)
            tryBuildNetworkInterceptors(builder)
            tryBuildTimeout(builder)
            tryBuildForceIp(builder)
            tryBuildConnectionKeepALive(builder)
            builder.build()
        }
    }

    protected fun tryBuildHeaders(requestBuilder: OKHttpRequestBuilder) {
        headers.forEach {
            val headerValue = objectToString(it.value)
            if (!headerValue.isNullOrEmpty()) {
                requestBuilder.addHeader(it.key, headerValue)
            }
        }
    }

    protected fun tryBuildQueryParams(requestBuilder: OKHttpRequestBuilder) {
        queryParams.forEach {
            if (it.value.isNotEmpty()) {
                requestBuilder.addQueryParam(it.key, it.value, false)
            }
        }
    }

    protected fun buildRequest(): Request {
        okHttpRequestBuilder.init().url(url).method(method)
        tryBuildHeaders(okHttpRequestBuilder)
        tryBuildQueryParams(okHttpRequestBuilder)

        requestBody?.let {
            okHttpRequestBuilder.requestBody(it)
        }
        return okHttpRequestBuilder.build()
    }

    private fun newRequest(): Request {
        request = buildRequest()
        return request!!
    }

    private fun newCall(okHttpClient: OkHttpClient, request: Request): Call {
        call = okHttpClient.newCall(request)
        return call as Call
    }

    override fun setRequestBody(mediaType: String, requestBodyStr: String): C {
        super.setRequestBody(mediaType, requestBodyStr)
        requestBody = requestBodyStr.toRequestBody(mediaType.toMediaType())
        return this as C
    }

    override fun <T> tag(type: Class<in T>, tag: T?): C {
        okHttpRequestBuilder.tag(type, tag)
        return this as C
    }

    override fun <T> tag(type: Class<out T>): T? {
        return request?.tag(type)
    }

    override fun requestAsync(callback: OKHttpCallback) {
        try {
            isAsync = true
            newCall(buildOkHttpNewClient(), newRequest()).enqueue(callback)
        } catch (e: Exception) {
            e.printStackTrace()
            callback.onException(call, IOException(e.message))
        }
    }

    @Throws(IOException::class)
    override fun requestSync(): Response {
        isAsync = false
        return newCall(buildOkHttpNewClient(), newRequest()).execute()
    }

    override fun cancel() {
        call?.let {
            if (!it.isCanceled()) {
                it.cancel()
            }
        }
    }

    override fun isCanceled(): Boolean {
        return call?.isCanceled() ?: false
    }

    override fun addInterceptor(interceptor: Interceptor): C {
        interceptors.add(interceptor)
        return this as C
    }

    override fun addInterceptors(interceptors: List<Interceptor>): C {
        this.interceptors.addAll(interceptors)
        return this as C
    }

    override fun setInterceptors(interceptors: List<Interceptor>): C {
        this.interceptors.clear()
        this.interceptors.addAll(interceptors)
        return this as C
    }

    override fun addNetworkInterceptor(interceptor: Interceptor): C {
        this.networkInterceptors.add(interceptor)
        return this as C
    }

    override fun addNetworkInterceptors(interceptors: List<Interceptor>): C {
        this.networkInterceptors.addAll(interceptors)
        return this as C
    }

    override fun setNetworkInterceptors(interceptors: List<Interceptor>): C {
        this.networkInterceptors.clear()
        this.networkInterceptors.addAll(interceptors)
        return this as C
    }

    override fun antiShakeRequestIdentity(requestIdentity: String): C {
        tag(TagAntiShakeRequestIdentity::class.java, TagAntiShakeRequestIdentity(requestIdentity))
        return this as C
    }

    override fun isAlreadyRequested(requestIdentity: String): Boolean {
        val dispatcher = getOkHttpClient().dispatcher
        val runningCalls = dispatcher.runningCalls()
        val queuedCalls = dispatcher.queuedCalls()
        return isAlreadyRequested(runningCalls, requestIdentity) || isAlreadyRequested(
            queuedCalls,
            requestIdentity
        )
    }

    private fun isAlreadyRequested(calls: List<Call>, requestIdentity: String): Boolean {
        for (call in calls) {
            val tag = call.request().tag(TagAntiShakeRequestIdentity::class.java)
            if (tag != null && tag.requestIdentity == requestIdentity) {
                return true
            }
        }
        return false
    }
}