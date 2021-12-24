package com.oneday.dialog_fragment

import android.app.Dialog
import android.os.Build
import android.view.*
import com.oneday.dialog_fragment.utils.ScreenUtils


abstract class DialogViewHolder(val builder: DialogProvider.Builder) {

    abstract fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View

    abstract fun onViewCreated(dialog: Dialog?)

    open fun onCreateDialog(dialog: Dialog) {
        dialog.setCancelable(builder.cancelable)
        dialog.window?.let { window ->
            val systemUiVisibility = ScreenUtils.addLayoutFullScreen(window, true)
            window.decorView.setOnSystemUiVisibilityChangeListener {
                window.decorView.systemUiVisibility = systemUiVisibility
            }
            if (builder.applicationOverlay) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
                } else {
                    window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
                }
                window.setGravity(Gravity.CENTER)
            }
        }
    }

    open fun measure(dialog: Dialog?) {
        dialog?.let {
            it.window?.setLayout(
                it.context.resources.getDimension(R.dimen.dialog_width).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
}