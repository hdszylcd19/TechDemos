package com.oneday.base

import androidx.lifecycle.MutableLiveData
import com.oneday.exception.AppException
import com.oneday.exception.AppExceptionHandler

/**
 * Desc: LiveData数据加载状态
 *
 * @author OneD
 * @version 1.0
 * @since 2021/12/29 16:08
 */
sealed class LiveDataState<out T> {
    companion object {
        fun <T> onLoading(loadingMsg: String? = "加载中..."): LiveDataState<T> = Loading(loadingMsg)
        fun <T> onSuccess(data: T): LiveDataState<T> = Success(data)
        fun <T> onSuccessEmpty(): LiveDataState<T> = SuccessEmpty()
        fun <T> onNoNetwork(): LiveDataState<T> = NoNetwork()
        fun <T> onAppException(error: AppException): LiveDataState<T> = Exception(error)
    }

    data class Loading(val loadingMsg: String? = "加载中...") : LiveDataState<Nothing>()
    data class Success<T>(val data: T) : LiveDataState<T>()
    class SuccessEmpty<T> : LiveDataState<T>()
    class NoNetwork<T> : LiveDataState<T>()
    data class Exception(val error: AppException) : LiveDataState<Nothing>()

    /**
     * 成功结果处理
     */
    fun <T> MutableLiveData<LiveDataState<T>>.parseSuccess(result: T?) {
        if (result != null) {
            postValue(onSuccess(result))
        } else {
            postValue(onSuccessEmpty())
        }
    }

    fun <T> MutableLiveData<LiveDataState<T>>.appException(e: Throwable) {
        postValue(onAppException(AppExceptionHandler.handleException(e)))
    }
}