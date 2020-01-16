package com.oneday.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/13 0013 9:48
 */
class MyViewModel : ViewModel() {
    private val strs: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>().also {
            loadStrs()
        }
    }

    fun getStrs(): LiveData<List<String>> {
        return strs
    }

    private fun loadStrs() {
        // Do an asynchronous operation to fetch strs.
        println("loadStrs")

        object : Thread() {
            override fun run() {
                val arrayList = ArrayList<String>()
                var index = 0
                while (index < 10) {
                    arrayList.add("MyViewModel LivaData : " + ('A' + index).toString())
                    strs.postValue(arrayList)
                    index++
                }
            }
        }.start()
    }
}