package com.oneday.livedatademo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.*

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/8/27 0027 13:29
 */
open class LiveDataViewModel : ViewModel() {
    val currentTime: MutableLiveData<Long> by lazy { MutableLiveData<Long>() }
    val currentTimeTransformed: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val mTimer = Timer()

    /*kotlin中没有volatile关键字，但是有“@Volatile”注解*/
    @Volatile
    var flag = true


    init {
//        updateTimeByTimer()

        object : Thread("Worker") {
            override fun run() {
                while (flag) {
                    val currentTimeMillis = System.currentTimeMillis()
                    currentTime.postValue(currentTimeMillis)
                    currentTimeTransformed.postValue(Date(currentTimeMillis).toString())
                    Log.i(TAG, "发送时间")
                    sleep(1000)
                    Log.i(TAG, "---------")
                }
                Log.i(TAG, ">>> 结束 <<<")
            }
        }.start()
    }

    private fun updateTimeByTimer() {
        Log.i(TAG, "1秒钟后，开始发送时间")
        mTimer.schedule(object : TimerTask() {
            override fun run() {
                val currentTimeMillis = System.currentTimeMillis()
                currentTime.postValue(currentTimeMillis)
                currentTimeTransformed.postValue(Date(currentTimeMillis).toString())
                Log.i(TAG, "发送时间")
            }
        }, 1000, 1000)
    }

    override fun onCleared() {
        flag = false //修改标记，退出循环
        mTimer.cancel()
        Log.i(TAG, "onCleared()")
    }
}

/**
 * Factory for [LiveDataViewModel].
 */
object LiveDataVMFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LiveDataViewModel() as T
    }
}

const val TAG = "LiveData"