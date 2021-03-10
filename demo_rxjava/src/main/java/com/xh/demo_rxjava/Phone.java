package com.xh.demo_rxjava;

import android.util.Log;

public class Phone implements Appearance {

    @Override
    public void structure() {
        // 手机属性：玻璃后盖，金属边框
        Log.i("TAG", "手机的属性：玻璃后盖，金属边框");
    }
}