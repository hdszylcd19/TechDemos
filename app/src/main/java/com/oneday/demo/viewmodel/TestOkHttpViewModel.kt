package com.oneday.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oneday.base.BaseViewModel
import com.oneday.base.LiveDataState
import com.oneday.network.BaseRequestProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Desc:
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/2 15:13
 */
class TestOkHttpViewModel : BaseViewModel() {
    private val requestProxy = BaseRequestProxy()
    val responseContent = MutableLiveData<LiveDataState<String>>()

    fun doRequestByOkHttp() {
        viewModelScope.launch(Dispatchers.IO) {
            responseContent.postValue(LiveDataState.onLoading("开始请求数据"))

            responseContent.postValue(
                LiveDataState.onSuccess(
                    requestProxy.request("https://www.baidu.com") ?: "请求错误"
                )
            )
        }
    }
}