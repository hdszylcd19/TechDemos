package com.oneday.dialog_fragment

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Desc: 加载状态dialogViewHolder
 *
 * @author OneD
 * @version 1.0
 * @since 2022/3/30 15:35
 */
class DialogLoadingViewHolder(builder: DialogProvider.Builder) : DialogViewHolder(builder) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.dialog_loading_layout, container, false)
    }

    override fun onViewCreated(dialog: Dialog?) {

    }
}