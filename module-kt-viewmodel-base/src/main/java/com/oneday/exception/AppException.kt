package com.oneday.exception

/**
 * Desc:App异常信息
 *
 * @author OneD
 * @version 1.0
 * @since 2021/12/29 16:08
 */
open class AppException(
    val errorCode: Int = 0 /*错误码*/,
    val errorMsg: String? = "请求失败，请稍后再试"/*错误描述*/
) : Exception(errorMsg)

/**
 * 未知错误
 */
object AppUnKnowException : AppException(1000, "未知错误")

/**
 * 数据解析错误
 */
object AppParseException : AppException(1001, "数据解析错误")

/**
 * 网络错误
 */
object AppNetWorkException : AppException(1002, "网络连接错误，请稍后重试")

/**
 * 证书出错
 */
object AppSSLException : AppException(1003, "证书出错，请稍后再试")

/**
 * 连接超时
 */
object AppTimeOutException : AppException(1004, "网络连接超时，请稍后重试")

/**
 * IO异常
 */
object AppIOException : AppException(1005, "IO异常")