package com.oneday.network.okhttp

import android.text.TextUtils
import com.oneday.network.RequestMethod
import com.oneday.network.RequestMethod.PATCH
import com.oneday.network.RequestMethod.POST
import com.oneday.network.RequestMethod.PUT
import com.oneday.network.exception.HttpUrlErrException
import com.oneday.utils.EncodeUtils
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * OKHttp的Request请求构建器
 */
class OKHttpRequestBuilder {
    private var method: String = RequestMethod.GET
    private var url: String? = null
    private var urlBuilder: HttpUrl.Builder? = null
    private var requestBuilder: Request.Builder = Request.Builder()
    private var hasRequestBody = false
    private var multipartBuilder: MultipartBody.Builder? = null
    private var formBuilder: FormBody.Builder? = null
    private var requestBody: RequestBody? = null

    @Throws(HttpUrlErrException::class)
    fun url(url: String?): OKHttpRequestBuilder {
        this.url = url
        urlBuilder = buildHttpUrl().newBuilder()
        return this
    }

    fun method(method: String): OKHttpRequestBuilder {
        this.method = method
        hasRequestBody = hasRequestBody(method)
        return this
    }

    /**
     * 初始化OkHttpRequestBuilder所有字段，为下一次构建做准备。
     *
     */
    fun init(): OKHttpRequestBuilder {
        method = RequestMethod.GET
        url = null
        requestBuilder = Request.Builder()
        hasRequestBody = false
        multipartBuilder = null
        formBuilder = null
        requestBody = null
        return this
    }

    fun <T> tag(type: Class<in T>, tag: T?) {
        requestBuilder.tag(type, tag)
    }

    private fun hasRequestBody(method: String): Boolean {
        return when (method) {
            PATCH, POST, PUT -> true
            else -> false
        }
    }

    fun addHeader(name: String, value: String) {
        requestBuilder.removeHeader(name)
        try {
            requestBuilder.addHeader(name, value)
        } catch (illegalArgumentException: IllegalArgumentException) {
            requestBuilder.addHeader(EncodeUtils.urlEncode(name), EncodeUtils.urlEncode(value))
        }
    }

    fun addQueryParam(name: String, value: String, encoded: Boolean) {
        urlBuilder ?: throw HttpUrlErrException("请首先调用url()方法，传递正确的url！")

        if (encoded) {
            urlBuilder!!.addEncodedQueryParameter(name, value)
        } else {
            urlBuilder!!.addQueryParameter(name, value)
        }
    }

    // Only called when isFormEncoded was true.
    fun addFormField(name: String, value: String, encoded: Boolean) {
        createFormBuilder()
        if (encoded) {
            formBuilder?.addEncoded(name, value)
        } else {
            formBuilder?.add(name, value)
        }
    }

    private fun createFormBuilder() {
        if (formBuilder == null) {
            // Will be set to 'body' in 'build'.
            formBuilder = FormBody.Builder()
        }
    }

    // Only called when isMultipart was true.
    fun addPart(headers: Headers?, body: RequestBody) {
        createMultipartBuilder()
        multipartBuilder?.addPart(headers, body)
    }

    // Only called when isMultipart was true.
    fun addPart(part: MultipartBody.Part) {
        createMultipartBuilder()
        multipartBuilder?.addPart(part)
    }

    fun addFormDataPart(name: String, value: String) {
        createMultipartBuilder()
        multipartBuilder?.addFormDataPart(name, value)
    }

    fun addFormDataPart(name: String, value: String, body: RequestBody) {
        createMultipartBuilder()
        multipartBuilder?.addFormDataPart(name, value, body)
    }

    private fun createMultipartBuilder() {
        if (null == multipartBuilder) {
            // Will be set to 'body' in 'build'.
            multipartBuilder = MultipartBody.Builder()
            multipartBuilder?.setType(MultipartBody.FORM)
        }
    }

    fun requestBody(requestBody: RequestBody?) {
        this.requestBody = requestBody
    }

    private fun addHttpPrefixIfUrlHaveNoProtocol(inputUrl: String): String {
        var resultUrl = inputUrl
        if (!haveProtocol(inputUrl)) {
            resultUrl = "http://$inputUrl"
        }
        return resultUrl
    }

    private fun haveProtocol(inputUrl: String): Boolean {
        return !TextUtils.isEmpty(inputUrl) && inputUrl.contains("://")
    }

    @Throws(HttpUrlErrException::class)
    private fun buildHttpUrl(): HttpUrl {
        if (url.isNullOrEmpty()) {
            throw HttpUrlErrException("url is null or empty")
        }

        try {
            url = addHttpPrefixIfUrlHaveNoProtocol(url!!)
            return url!!.toHttpUrl()
        } catch (e: Exception) {
            throw HttpUrlErrException("httpUrl is error, url = $url", e)
        }
    }

    @Throws(HttpUrlErrException::class)
    fun build(): Request {
        val httpUrl = if (urlBuilder != null) {
            urlBuilder!!.build()
        } else {
            buildHttpUrl()
        }

        var body = requestBody
        if (body == null) {
            // Try to pull from one of the builders.
            when {
                formBuilder != null -> {
                    body = formBuilder!!.build()
                }
                multipartBuilder != null -> {
                    body = multipartBuilder!!.build()
                }
                hasRequestBody -> {
                    // Body is absent, make an empty body.
                    body = ByteArray(0).toRequestBody()
                }
            }
        }
        return requestBuilder
            .url(httpUrl)
            .method(method, body)
            .build()
    }
}