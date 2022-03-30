package com.oneday.ext

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.oneday.utils.ScreenUtils

/**
 * Desc: AppCompatActivity扩展函数
 *
 * @author OneD
 * @version 1.0
 * @since 2021/12/27 17:31
 */

/**
 * 设置沉浸式
 * [isLayoutFullScreen] isFullScreen
 * [color] color
 */
fun AppCompatActivity.setImmersion(
    isFullScreen: Boolean = false,
    isLayoutFullScreen: Boolean = false,
    isLighting: Boolean = false,
    statusBarColor: Int,
    navBarColor: Int = -1
) {
    //
    var systemUiVisibility: Int
    when {
        isLayoutFullScreen -> {
            systemUiVisibility = ScreenUtils.getLayoutFullScreenFlag()
        }
        else -> {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
    }

    if (isFullScreen) {
        systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    // navBar
    when {
        navBarColor > 0 -> {
            window.navigationBarColor = ContextCompat.getColor(this, navBarColor)
        }
        isLayoutFullScreen -> {
            window.navigationBarColor = Color.TRANSPARENT
        }
        else -> {
            // ignore
        }
    }

    // statusBar
    window.statusBarColor = when {
        isLayoutFullScreen -> {
            Color.TRANSPARENT
        }
        !isLighting -> {
            ContextCompat.getColor(this, statusBarColor)
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            ContextCompat.getColor(this, statusBarColor)
        }
        else -> {
            Color.parseColor("#333333")
        }
    }

    window.decorView.systemUiVisibility = systemUiVisibility
}