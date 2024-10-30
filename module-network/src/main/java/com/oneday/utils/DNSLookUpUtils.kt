package com.oneday.utils

import okhttp3.internal.canParseAsIpAddress
import java.net.InetAddress
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit

/**
 *  DNS工具类
 */
object DNSLookUpUtils {

    /**
     * 支持LocalDNS获取IP时的超时设置
     */
    fun loadLocalDNS(hostname: String, timeout: Long = 10L): List<InetAddress> {
        try {
            val task = FutureTask {
                //返回去重结果
                InetAddress.getAllByName(hostname).toList().distinct()
            }
            Thread(task).start()
            return task.get(timeout, TimeUnit.SECONDS)
        } catch (e: Exception) {
        }
        return listOf()
    }

    /**
     * 检测是否符合IP的数据格式
     */
    fun checkIp(ip: String): Boolean {
        return if (ip.isEmpty()) {
            // 判断ip地址是否与正则表达式匹配
            ip.canParseAsIpAddress()
        } else {
            // ip为空是异常情况，此时返回true，不需要再走dns流程，防止递归
            true
        }
    }
}