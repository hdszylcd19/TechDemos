package com.oneday.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * Desc: DialogFragment基类
 *
 * @author OneD
 * @version 1.0
 * @since 2021/12/15 14:30
 */
open class BaseDialogFragment : DialogFragment() {
    private var mDialogViewHolder: DialogViewHolder? = null
    private var mBuilder: DialogProvider.Builder? = null

    fun init(viewHolder: DialogViewHolder): BaseDialogFragment {
        this.mDialogViewHolder = viewHolder
        this.mBuilder = viewHolder.builder
        this.arguments = mBuilder?.extras
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mDialogViewHolder?.onCreateView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mDialogViewHolder?.onViewCreated(dialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        mBuilder?.let {
            isCancelable = it.cancelable
        }

        mDialogViewHolder?.onCreateDialog(dialog)
        return dialog
    }

    override fun getTheme(): Int {
        return mBuilder?.theme ?: super.getTheme()
    }

    override fun onStart() {
        super.onStart()
        mDialogViewHolder?.measure(dialog)
    }

    fun show(manager: FragmentManager) {
        super.show(manager, mBuilder?.tag)
    }
}