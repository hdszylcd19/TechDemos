package com.xh.demo_rxjava;

import android.util.Log;

/**
 * 手机壳包装类具体实现
 */
public class PhoneShell extends PhoneDecorator {

    public PhoneShell(Appearance appearance) {
        super(appearance);
    }

    @Override
    public void structure() {
        super.structure();

        Log.i("TAG", "给手机套上手机壳");
    }
}