package com.oneday.demo.kt_coroutine

import android.os.Looper
import kotlinx.coroutines.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

/**
 * Description :
 * 1. corePoolSize : 保留线程数;
 * 2. 仅当超过了队列上限时, 才会开辟新线程
 */
object ThreadPlugins {

    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()

    // --------------------------------------------------
    /**
     * 网络请求线程池
     */
    private val requestExecutorStub by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        ThreadExecutorStub(
            ThreadPoolExecutor(
                CPU_COUNT,
                CPU_COUNT * 2,
                30L,
                TimeUnit.SECONDS,
                LinkedBlockingQueue(),
                DefaultFactory("launcher_request")
            )
        )
    }
    val requestDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        requestExecutorStub.coroutineDispatcher
    }

    val myScope = MyCoroutineScope(SupervisorJob() + requestDispatcher)
    // --------------------------------------------------
    /**
     * 普通子线程池，用于一般子线程任务操作
     */
    private val multiExecutorStub by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        ThreadExecutorStub(
            ThreadPoolExecutor(
                CPU_COUNT + 2,
                CPU_COUNT * 2,
                60L,
                TimeUnit.SECONDS,
                LinkedBlockingQueue(),
                DefaultFactory("launcher_multi")
            )
        )
    }

    val multiDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        multiExecutorStub.coroutineDispatcher
    }

    // --------------------------------------------------
    /**
     * file操作线程
     */
    val fileExecutorStub by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        ThreadExecutorStub(
            ThreadPoolExecutor(
                1,
                2,
                3L,
                TimeUnit.SECONDS,
                LinkedBlockingQueue(),
                DefaultFactory("launcher_file")
            )
        )
    }
    val fileDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        fileExecutorStub.coroutineDispatcher
    }
    // --------------------------------------------------
    /**
     * db操作线程
     */
    private val ioExecutorStub by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        ThreadExecutorStub(
            ThreadPoolExecutor(
                CPU_COUNT,
                CPU_COUNT * 2,
                3L,
                TimeUnit.SECONDS,
                LinkedBlockingQueue(),
                DefaultFactory("launcher_io")
            )
        )
    }

    val ioDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        ioExecutorStub.coroutineDispatcher
    }
    // --------------------------------------------------

    /**
     * firewall操作线程
     */
    val firewallExecutorStub by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        ThreadExecutorStub(
            ThreadPoolExecutor(
                1,
                1,
                3L,
                TimeUnit.SECONDS,
                LinkedBlockingQueue(),
                DefaultFactory("launcher_firewall")
            )
        )
    }

    // --------------------------------------------------
    /**
     * 安装线程
     */
    val installExecutorStub by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        ThreadExecutorStub(
            ThreadPoolExecutor(
                1,
                1,
                3L,
                TimeUnit.SECONDS,
                LinkedBlockingQueue(),
                DefaultFactory("launcher_install")
            )
        )
    }

    // --------------------------------------------------


    /**
     * 在安装线程执行
     * [runnable] runnable
     */
    @JvmStatic
    fun runInInstallThread(runnable: Runnable) {
        installExecutorStub.execute(runnable)
    }

    // --------------------------------------------------
    /**
     * 在io线程执行
     * [runnable] runnable
     */
    @JvmStatic
    fun runInIoThread(runnable: Runnable) {
        ioExecutorStub.execute(runnable)
    }

    @JvmStatic
    fun isOnIOThread(): Boolean {
        return ioExecutorStub.isOnThread()
    }

    // --------------------------------------------------
    /**
     * 在File线程执行
     * [runnable] runnable
     */
    @JvmStatic
    fun runInFileThread(runnable: Runnable) {
        fileExecutorStub.execute(runnable)
    }
    // --------------------------------------------------
    /**
     * 在防火墙线程执行
     * [runnable] runnable
     */
    @JvmStatic
    fun runInFirewallThread(runnable: Runnable) {
        firewallExecutorStub.execute(runnable)
    }

    // --------------------------------------------------
    @JvmStatic
    fun runInMultiThread(runnable: Runnable) {
        multiExecutorStub.execute(runnable)
    }
    // --------------------------------------------------
    /**
     * 在Log线程执行
     * [runnable] runnable
     */
    @JvmStatic
    fun runInLogThread(runnable: Runnable) {
        fileExecutorStub.execute(runnable)
    }

    // --------------------------------------------------
    @JvmStatic
    fun isOnMainThread(): Boolean {
        return Looper.getMainLooper().thread == Thread.currentThread()
    }

    class MyCoroutineScope(override val coroutineContext: CoroutineContext) :
        CoroutineScope {

        fun launchInIO(block: suspend CoroutineScope.() -> Unit): Job = launch(ioDispatcher) {
            block.invoke(this)
        }

        fun launchInRequest(block: suspend CoroutineScope.() -> Unit): Job =
            launch(requestDispatcher) {
                block.invoke(this)
            }

        fun launchInFile(block: suspend CoroutineScope.() -> Unit): Job = launch(fileDispatcher) {
            block.invoke(this)
        }

        fun launchInMulti(block: suspend CoroutineScope.() -> Unit): Job = launch(multiDispatcher) {
            block.invoke(this)
        }
    }
}