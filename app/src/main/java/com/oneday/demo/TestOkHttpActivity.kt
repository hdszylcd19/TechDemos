package com.oneday.demo

import android.os.Bundle
import android.view.View
import com.oneday.base.BaseVMActivity
import com.oneday.demo.viewmodel.TestOkHttpViewModel
import kotlinx.android.synthetic.main.activity_test_ok_http.*

class TestOkHttpActivity : BaseVMActivity<TestOkHttpViewModel>() {
    override fun getLayoutResId(): Int = R.layout.activity_test_ok_http

    override fun init(savedInstanceState: Bundle?) {
        mViewModel.responseContent.observe(this) {
            parseState(it, onSuccess = { content ->
                contentTv?.text = content
            })
        }
    }

    fun requestByOkHttp(view: View) {
        mViewModel.doRequestByOkHttp()
    }
}