package com.oneday.ext

import android.content.Context
import android.widget.Toast

/**
 * Desc: Toast扩展函数
 *
 * @author OneD
 * @version 1.0
 * @since 2021/12/28 11:07
 */

fun String?.showToast(context: Context) {
    if (this.isNullOrEmpty()) return
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}