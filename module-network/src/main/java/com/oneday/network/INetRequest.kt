package com.oneday.network

/**
 * Desc:
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/7 8:59
 */
interface INetRequest {
    fun <C : INetRequest> url(url: String): C
}
