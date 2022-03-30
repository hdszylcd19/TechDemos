package com.oneday.demo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.oneday.base.BaseVMActivity
import com.oneday.demo.bitmap.TestBitmapActivity
import com.oneday.demo.viewmodel.PermissionViewModel
import com.oneday.ext.showToast

/**
 * Desc:
 *
 * @author OneD
 * @version 1.0
 * @since 2021/12/28 10:39
 */
class PermissionActivity : BaseVMActivity<PermissionViewModel>() {
    private val permissionList = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.RECORD_AUDIO
    )

    override fun getLayoutResId() = R.layout.activity_permission

    override fun init(savedInstanceState: Bundle?) {

    }

    fun requestPermission(view: View) {
        requestPermissions(permissionList,
            allGrantedCallback = {
                "所有权限都已授权".showToast(this)
                startActivity(Intent(this, TestBitmapActivity::class.java))
            },
            deniedCallback = {
                "以下权限被拒绝：$it".showToast(this)
            }
        )
    }
}