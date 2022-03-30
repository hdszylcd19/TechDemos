package com.oneday.exception

import java.io.IOException
import java.net.ConnectException
import javax.net.ssl.SSLException

/**
 * Desc: 异常统一处理工具类
 *
 * @author OneD
 * @version 1.0
 * @since 2021/12/30 9:17
 */
object AppExceptionHandler {
    fun handleException(e: Throwable?): AppException {
        return when (e) {
            is ConnectException -> {
                AppNetWorkException
            }
            is SSLException -> {
                AppSSLException
            }
            is java.net.SocketTimeoutException, is java.net.UnknownHostException -> {
                AppTimeOutException
            }
            is IOException -> {
                AppIOException
            }
            else -> {
                AppUnKnowException
            }
        }
    }
}