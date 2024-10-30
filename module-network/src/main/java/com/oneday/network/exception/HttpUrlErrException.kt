package com.oneday.network.exception

import java.io.IOException

/**
 * Desc:网络请求url异常类
 *
 * @param errMsg    自定义错误信息
 * @param throwable 异常错误信息
 */
data class HttpUrlErrException(
    val errMsg: String? = null,
    val throwable: Throwable? = null
) : IOException(errMsg, throwable)

