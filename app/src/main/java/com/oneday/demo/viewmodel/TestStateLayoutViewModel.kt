package com.oneday.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oneday.base.BaseViewModel
import com.oneday.base.LiveDataState
import com.oneday.exception.AppNetWorkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Desc:
 *
 * @author OneD
 * @version 1.0
 * @since 2022/3/30 13:59
 */
class TestStateLayoutViewModel : BaseViewModel() {
    val dataListState: MutableLiveData<LiveDataState<List<String>>> =
        MutableLiveData<LiveDataState<List<String>>>()

    fun requestSuccess() {
        viewModelScope.launch(Dispatchers.IO) {
            dataListState.postValue(LiveDataState.onLoading())
            delay(2000)

            val list = ArrayList<String>(26)
            val c = 'A'
            for (i in 0 until 26) { //[0,26)
                list.add((c + i).toString())
            }

            dataListState.postValue(LiveDataState.onSuccess(list))
        }
    }

    fun requestSuccessEmpty() {
        viewModelScope.launch(Dispatchers.IO) {
            dataListState.postValue(LiveDataState.onLoading())
            delay(2000)

            dataListState.postValue(LiveDataState.onSuccessEmpty())
        }
    }

    fun requestError() {
        viewModelScope.launch(Dispatchers.IO) {
            dataListState.postValue(LiveDataState.onLoading())
            delay(2000)

            dataListState.postValue(LiveDataState.onAppException(AppNetWorkException))
        }
    }

    fun requestNoNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            dataListState.postValue(LiveDataState.onLoading())
            delay(2000)

            dataListState.postValue(LiveDataState.onNoNetwork())
        }
    }
}