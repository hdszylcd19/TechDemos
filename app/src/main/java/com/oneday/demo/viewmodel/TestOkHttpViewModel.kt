package com.oneday.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oneday.base.BaseViewModel
import com.oneday.base.LiveDataState
import com.oneday.exception.AppException
import com.oneday.network.okhttp.callback.OKHttpCallback
import com.oneday.network.okhttp.request.OKHttpRequestImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import java.io.IOException

/**
 * Desc:
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/2 15:13
 */
class TestOkHttpViewModel : BaseViewModel() {

    val responseContent = MutableLiveData<LiveDataState<String>>()

    fun doRequestByOkHttp() {
        viewModelScope.launch(Dispatchers.IO) {
            responseContent.postValue(LiveDataState.onLoading("开始请求数据"))

            OKHttpRequestImpl()
                .url("https://www.baidu.com")
                .requestAsync(object : OKHttpCallback {
                    override fun onUnexpectedFailure(call: okhttp3.Call?, e: Exception) {
                        responseContent.postValue(LiveDataState.onAppException(AppException(errorMsg = e.message)))
                    }

                    override fun onFailure(call: okhttp3.Call, e: IOException) {
                        responseContent.postValue(LiveDataState.onAppException(AppException(errorMsg = e.message)))
                    }

                    override fun onResponse(call: okhttp3.Call, response: Response) {
                        val data = response.body?.string() ?: "响应内容为空"
                        responseContent.postValue(LiveDataState.onSuccess(data))
                    }
                })
        }
    }
}