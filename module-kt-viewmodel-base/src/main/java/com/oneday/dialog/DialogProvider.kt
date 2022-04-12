package com.oneday.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.annotation.StyleRes
import com.oneday.base.R


class DialogProvider private constructor() {

    class Builder {
        /**
         * dialog样式style；默认值为透明样式
         */
        @StyleRes
        var theme = R.style.TransparentDialog

        /**
         * dialog是否可以点击外部取消；当该值为true时，表示可以取消；默认值为false不可取消；
         */
        var cancelable = false
        var applicationOverlay = false
        var message: CharSequence? = null
        var messageGravity: Int = Gravity.CENTER
        var positive: String? = null
        var positiveListener: ((v: View) -> Unit)? = null
        var negative: String? = null
        var negativeListener: ((v: View) -> Unit)? = null
        var title: String? = null
        var tag: String? = null
        var extras: Bundle? = null

        fun theme(@StyleRes theme: Int): Builder {
            this.theme = theme
            return this
        }

        fun cancelable(boolean: Boolean = true): Builder {
            this.cancelable = boolean
            return this
        }

        fun applicationOverlay(boolean: Boolean = true): Builder {
            this.applicationOverlay = boolean
            return this
        }

        fun message(message: CharSequence?, gravity: Int = Gravity.CENTER): Builder {
            this.message = message
            this.messageGravity = gravity
            return this
        }

        fun positive(
            positive: String = "确定",
            positiveListener: ((v: View) -> Unit)? = null
        ): Builder {
            this.positive = positive
            this.positiveListener = positiveListener
            return this
        }

        fun negative(
            negative: String = "取消",
            negativeListener: ((v: View) -> Unit)? = null
        ): Builder {
            this.negative = negative
            this.negativeListener = negativeListener
            return this
        }

        fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun tag(tag: String): Builder {
            this.tag = tag
            return this
        }

        fun extras(bundle: Bundle): Builder {
            this.extras = bundle
            return this
        }

        fun buildDialogFragment(viewHolder: DialogViewHolder = DialogMessageViewHolder(this)): BaseDialogFragment {
            return BaseDialogFragment().init(viewHolder)
        }

        fun buildDialog(
            context: Context,
            viewHolder: DialogViewHolder = DialogMessageViewHolder(this)
        ): CustomDialog {
            return CustomDialog(context, viewHolder)
        }
    }
}