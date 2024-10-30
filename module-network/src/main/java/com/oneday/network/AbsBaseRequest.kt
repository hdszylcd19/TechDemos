package com.oneday.network

import com.oneday.utils.CommonUtils.objectToString

/**
 * Desc: 网络请求抽象基类
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/2 10:41
 */
@Suppress("UNCHECKED_CAST")
abstract class AbsBaseRequest<C : AbsBaseRequest<C>> : INetRequest<C> {
    /**
     * 网络请求的url
     */
    protected var url: String = ""

    /**
     * 网络请求类型；默认为GET，详见[RequestMethod]
     */
    protected var method: String = RequestMethod.GET

    /**
     * 网络请求头参数集合
     */
    protected var headers: MutableMap<String, Any> = HashMap()

    /**
     * 网络请求查询参数集合
     */
    protected var queryParams: MutableMap<String, String> = HashMap()

    /**
     * 请求体类型
     */
    protected var requestBodyMediaType: String? = null

    /**
     * 请求体内容字符串
     */
    protected var requestBodyStr: String? = null

    /**
     * 连接超时时间；单位ms；默认超时时间为10s
     */
    protected var connectTimeout: Long = 10000

    /**
     * 读取超时时间；单位ms；默认超时时间为10s
     */
    protected var readTimeout: Long = 10000

    /**
     * 写入超时时间；单位ms；默认超时时间为10s
     */
    protected var writeTimeout: Long = 10000

    /**
     * 连接复用的keep-alive time配置；单位ms；接口动态下发，默认值为-1，即不配置
     */
    protected var connectionKeepAliveMillis: Long = -1L

    /**
     * 当该强制ip不为空时，这次请求的DNS解析结果只有这个ip；默认值为空
     */
    protected var forceIp: String = ""

    fun forceIp(forceIp: String): C {
        this.forceIp = forceIp
        return this as C
    }

    override fun url(url: String): C {
        this.url = url
        return this as C
    }

    override fun addHeader(key: String, value: Any): C {
        headers[key] = value
        return this as C
    }

    override fun addHeaders(headers: Map<String, Any>): C {
        this.headers.putAll(headers)
        return this as C
    }

    override fun setHeaders(headers: Map<String, Any>): C {
        this.headers.clear()
        this.headers.putAll(headers)
        return this as C
    }

    override fun method(method: String): C {
        this.method = method
        return this as C
    }

    override fun connectTimeout(timeout: Long): C {
        this.connectTimeout = timeout
        return this as C
    }

    override fun readTimeout(timeout: Long): C {
        this.readTimeout = timeout
        return this as C
    }

    override fun writeTimeout(timeout: Long): C {
        this.writeTimeout = timeout
        return this as C
    }

    override fun timeout(connectTimeout: Long, readTimeout: Long, writeTimeout: Long): C {
        connectTimeout(connectTimeout)
        readTimeout(readTimeout)
        writeTimeout(writeTimeout)
        return this as C
    }

    override fun addQueryParam(key: String, value: String): C {
        this.queryParams[key] = value
        return this as C
    }

    override fun addQueryParams(params: Map<String, String>): C {
        this.queryParams.putAll(params)
        return this as C
    }

    override fun setQueryParams(params: Map<String, String>): C {
        this.queryParams.clear()
        this.queryParams.putAll(params)
        return this as C
    }

    override fun setRequestBody(requestBodyObj: Any): C {
        return setRequestBody(objectToString(requestBodyObj) ?: "")
    }

    override fun setRequestBody(mediaType: String, requestBodyObj: Any): C {
        return setRequestBody(mediaType, objectToString(requestBodyObj) ?: "")
    }

    override fun setRequestBody(requestBodyStr: String): C {
        return setRequestBody(CONTENT_TYPE_JSON, requestBodyStr)
    }

    override fun setRequestBody(mediaType: String, requestBodyStr: String): C {
        this.requestBodyMediaType = mediaType
        this.requestBodyStr = requestBodyStr
        return this as C
    }
}