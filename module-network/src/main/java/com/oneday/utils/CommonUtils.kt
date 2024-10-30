package com.oneday.utils

import com.google.gson.Gson
import java.util.regex.Pattern

object CommonUtils {
    /**
     * 判断IP格式和范围
     */
    private val pat =
        Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}")
    private val gson = Gson()

    fun isIP(addr: String): Boolean {
        if (addr.length < 7 || addr.length > 15 || addr.isEmpty()) {
            return false
        }

        return pat.matcher(addr).find()
    }

    fun <E> objectToString(obj: E?): String? {
        var result: String? = null
        if (obj != null) {
            result = if (obj is String) {
                obj
            } else {
                gson.toJson(obj)
            }
        }
        return result
    }

    fun <T> checkNotNull(obj: T?, message: String?): T {
        if (obj == null) {
            throw NullPointerException(message)
        }
        return obj
    }
}