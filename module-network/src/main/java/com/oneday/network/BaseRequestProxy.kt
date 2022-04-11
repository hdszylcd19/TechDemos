package com.oneday.network

import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Desc:
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/2 10:41
 */
class BaseRequestProxy:INetRequest {
    private val okHttpClient = OkHttpClient()
    fun request(url: String): String? {
        val request = Request.Builder().url(url).build()
        return try {
            val newCall = okHttpClient.newCall(request)
            val response = newCall.execute()
            response.body?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun <C : INetRequest> url(url: String): C {
        TODO("Not yet implemented")
    }


}