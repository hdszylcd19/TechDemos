package com.oneday.livedatademo

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/8/28 0028 9:29
 */
class LiveDataSavedStateViewModel(private val mstate: SavedStateHandle) : ViewModel() {
}

/**
 * Factory for [LiveDataViewModel].
 */
//object LiveDataSavedStateVMFactory : AbstractSavedStateViewModelFactory() {
//    override fun <T : ViewModel?> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T {
//        TODO("Not yet implemented")
//    }
//}