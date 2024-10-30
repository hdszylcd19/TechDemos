package com.oneday.demo.kt_coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Desc: 测试协程切换线程执行的奥秘
 *
 * @author OneD
 * @version 1.0
 * @since 2022/6/27 10:17
 */
class TestKtCoroutine {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            ThreadPlugins.myScope.launch {
                TestKtCoroutine().testCoroutine()
            }

            printWithThread("end launch")
            Thread.currentThread().join()
        }

        private fun printWithThread(msg: String? = "") {
            println("[${Thread.currentThread().name}] $msg")
        }
    }

    suspend fun testCoroutine() {
        printWithThread("start testCoroutine")
        val userName = getUserName()
        printWithThread("userName = $userName")
        val userFriendNames = getUserFriendNames(userName)
        printWithThread("userFriendNames = $userFriendNames")
        printWithThread("end testCoroutine")
    }

    private suspend fun getUserName(): String {
        return withContext(ThreadPlugins.ioDispatcher) {
            printWithThread("getUserName()")
            delay(1000)
            "张三"
        }
    }

    private suspend fun getUserFriendNames(userName: String): String {
        return withContext(ThreadPlugins.ioDispatcher) {
            printWithThread("getUserFriendNames()")
            delay(1000)
            "赵四、王五、李六"
        }
    }
}