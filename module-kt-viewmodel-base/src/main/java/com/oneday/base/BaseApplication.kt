package com.oneday.base

import android.app.Application
import android.os.Handler
import android.os.Looper

/**
 * Desc:Application基类
 *
 * @author OneD
 * @version 1.0
 * @since 2021/12/29 11:24
 */
class BaseApplication : Application() {
    companion object {
        /*
        这里并不会造成内存泄漏，因为这是一个Application Context；如果你持有的是一个静态的Activity Context，
        那就会造成内存泄漏的。Android Studio并没有智能地判断出来这是Application Context，所以这是一个误报，并没有任何影响。
        如果你看着觉得不舒服，可以在字段上加个这个注解 @SuppressLint("StaticFieldLeak")
        */
        @JvmStatic
        lateinit var sApplication: Application

        /**
         * 主线程handler
         */
        @JvmStatic
        val sMainHandler: Handler = Handler(Looper.getMainLooper())
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }
}