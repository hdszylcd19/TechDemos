package com.oneday.dialog_fragment

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class MessageDialogHolder(builder: DialogProvider.Builder) : DialogViewHolder(builder) {
    private lateinit var dialogTitleTv: TextView
    private lateinit var dialogTitleSplitLine: View
    private lateinit var dialogMessageTv: TextView
    private lateinit var dialogSureBtn: TextView
    private lateinit var dialogCancelBtn: TextView
    private lateinit var dialogSplitLine: View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.message_dialog_layout, container, false)
        dialogTitleTv = view.findViewById(R.id.dialogTitleTv)
        dialogTitleSplitLine = view.findViewById<View>(R.id.dialogTitleSplitLine)
        dialogMessageTv = view.findViewById(R.id.dialogMessageTv)
        dialogSureBtn = view.findViewById(R.id.dialogSureBtn)
        dialogCancelBtn = view.findViewById(R.id.dialogCancelBtn)
        dialogSplitLine = view.findViewById(R.id.dialogSplitLine)
        return view
    }

    override fun onViewCreated(dialog: Dialog?) {
        builder.message?.let {
            dialogMessageTv.gravity = builder.messageGravity
            dialogMessageTv.text = it
        }
        builder.title?.let {
            dialogTitleTv.visibility = View.VISIBLE
            dialogTitleSplitLine.visibility = View.VISIBLE
            dialogTitleTv.text = it
        } ?: let {
            dialogTitleTv.visibility = View.GONE
            dialogTitleSplitLine.visibility = View.GONE
        }
        dialogSplitLine.visibility = View.VISIBLE
        builder.negative?.let {
            dialogCancelBtn.visibility = View.VISIBLE
            dialogCancelBtn.text = it
        } ?: let {
            dialogCancelBtn.visibility = View.GONE
            dialogSplitLine.visibility = View.GONE
        }
        builder.positive?.let {
            dialogSureBtn.visibility = View.VISIBLE
            dialogSureBtn.text = it
        } ?: let {
            dialogSureBtn.visibility = View.GONE
            dialogSplitLine.visibility = View.GONE
        }

        dialogSureBtn.setOnClickListener {
            if (dialog?.isShowing == true) {
                dialog.dismiss()
            }
            builder.positiveListener?.invoke(it)
        }
        dialogCancelBtn.setOnClickListener {
            if (dialog?.isShowing == true) {
                dialog.dismiss()
            }
            builder.negativeListener?.invoke(it)
        }
    }
}