package com.oneday.dialog_fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager


class CustomDialog internal constructor(
    context: Context,
    private val customDialogHolder: DialogViewHolder
) {
    private val dialog = Dialog(context, customDialogHolder.builder.theme)

    init {
        customDialogHolder.onCreateDialog(dialog)
        val view = customDialogHolder.onCreateView(LayoutInflater.from(context), null)
        customDialogHolder.onViewCreated(dialog)
        dialog.setContentView(view)
    }

    fun show() {
        val context = dialog.context
        if (context is Activity && context.isFinishing) {
            return
        }
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        dialog.show()
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        customDialogHolder.measure(dialog)
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun isShowing(): Boolean {
        return dialog.isShowing
    }
}