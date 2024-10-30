package com.oneday.demo.kt_coroutine


import kotlinx.coroutines.ExecutorCoroutineDispatcher
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import kotlin.coroutines.CoroutineContext


class ThreadExecutorStub(val executorService: ThreadPoolExecutor) {
    val coroutineDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        object : ExecutorCoroutineDispatcher() {
            override val executor: Executor
                get() = executorService

            override fun close() {
            }

            override fun toString(): String = executorService.toString()

            override fun dispatch(context: CoroutineContext, block: Runnable) {
                executorService.execute(block)
            }
        }
    }

    fun remove(runnable: Runnable) {
        executorService.remove(runnable)
    }

    fun execute(runnable: Runnable) {
        remove(runnable)
        executorService.execute(runnable)
    }

    fun isOnThread(): Boolean {
        return when (val threadFactory = executorService.threadFactory) {
            is DefaultFactory -> {
                Thread.currentThread().threadGroup?.name ?: "" == threadFactory.name
            }
            else -> {
                false
            }
        }
    }
}