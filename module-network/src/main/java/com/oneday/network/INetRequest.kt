package com.oneday.network

/**
 * Desc: 网络请求顶层接口
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/7 8:59
 */
interface INetRequest<C : INetRequest<C>> {
    /**
     * Desc: 设置网络请求url
     *
     * @param url  网络请求url
     */
    fun url(url: String): C

    /**
     * Desc: 添加网络请求头参数，增量添加，不会覆盖已有参数；
     *
     * @param key       请求头参数名
     * @param value     请求头参数值
     */
    fun addHeader(key: String, value: Any): C

    /**
     * Desc: 添加网络请求头批量参数，增量添加，不会覆盖已有参数；
     *
     * @param headers 请求头批量参数集合
     */
    fun addHeaders(headers: Map<String, Any>): C

    /**
     * Desc: 设置网络请求头批量参数，会覆盖已有参数，全量替换。
     *
     * @param headers 请求头批量参数集合
     */
    fun setHeaders(headers: Map<String, Any>): C

    /**
     * Desc: 设置网络请求类型；默认为GET
     *
     * @param method 请求类型；详见[RequestMethod]
     */
    fun method(method: String): C

    /**
     * 设置网络连接超时时间，单位ms
     * @param timeout 连接超时时间；单位ms
     *
     * @return 返回当前对象
     */
    fun connectTimeout(timeout: Long): C

    /**
     * 设置网络读取超时时间，单位ms
     * @param timeout 读取超时时间；单位ms
     *
     * @return 返回当前对象
     */
    fun readTimeout(timeout: Long): C

    /**
     * 设置网络写入超时时间，单位ms
     * @param timeout 写入超时时间；单位ms
     *
     * @return 返回当前对象
     */
    fun writeTimeout(timeout: Long): C

    /**
     * 统一设置超时时间，单位ms
     *
     * @param connectTimeout 连接超时时间；单位ms
     * @param readTimeout    读取超时时间；单位ms
     * @param writeTimeout   写入超时时间；单位ms
     *
     * @return 返回当前对象
     */
    fun timeout(connectTimeout: Long, readTimeout: Long, writeTimeout: Long): C

    /**
     * 添加查询参数，增量添加，不会覆盖已有参数；当key相同时，旧的value会被覆盖。
     * 当调用该方法添加查询参数时，框架内部会自动进行urlEncode。
     * 对于接入方自行在url上添加的查询参数，请自行对参数进行urlEncode
     * @param key   参数名
     * @param value 参数值
     *
     * @return 返回当前对象
     */
    fun addQueryParam(key: String, value: String): C

    /**
     * 批量添加查询参数，增量添加，不会覆盖已有参数；
     * 当调用该方法添加查询参数时，框架内部会自动进行urlEncode。
     * 对于接入方自行在url上添加的查询参数，请自行对参数进行urlEncode
     * @param params 查询参数集合
     *
     * @return 返回当前对象
     */
    fun addQueryParams(params: Map<String, String>): C

    /**
     * 设置查询参数，会覆盖已有参数；全量替换。
     * 当调用该方法添加查询参数时，框架内部会自动进行urlEncode。
     * 对于接入方自行在url上添加的查询参数，请自行对参数进行urlEncode
     * @param params 查询参数集合
     *
     * @return 返回当前对象
     */
    fun setQueryParams(params: Map<String, String>): C

    /**
     * 设置请求体
     *
     * @param requestBodyObj 请求体对象
     *
     * @return 返回当前对象
     */
    fun setRequestBody(requestBodyObj: Any): C

    /**
     * 设置请求体
     *
     * @param  mediaType        请求体类型
     * @param requestBodyObj    请求体对象
     *
     * @return 返回当前对象
     */
    fun setRequestBody(mediaType: String, requestBodyObj: Any): C

    /**
     * 设置请求体
     *
     * @param requestBodyStr 请求体字符串
     *
     * @return 返回当前对象
     */
    fun setRequestBody(requestBodyStr: String): C

    /**
     * 设置请求体
     *
     * @param  mediaType        请求体类型
     * @param requestBodyStr    请求体字符串
     *
     * @return 返回当前对象
     */
    fun setRequestBody(mediaType: String, requestBodyStr: String): C

    /**
     * 设置网络请求tag信息；
     *
     * @param type  tag的class类型
     * @param tag   tag信息；如果为null时，会清空已有的type类型的tag
     */
    fun <T> tag(type: Class<in T>, tag: T?): C

    /**
     * 获取网络请求tag信息；
     *
     * @param type tag的class类型
     */
    fun <T> tag(type: Class<out T>): T?

    /**
     * Desc: 取消当前网络请求
     */
    fun cancel()

    /**
     * Desc:当前网络请求是否已经取消
     *
     * @return true为已经取消，false为未取消
     */
    fun isCanceled(): Boolean
}
