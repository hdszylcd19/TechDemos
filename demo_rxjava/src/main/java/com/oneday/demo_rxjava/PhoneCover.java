package com.oneday.demo_rxjava;

import android.util.Log;

/**
 * 手机贴膜包装类具体实现
 */
public class PhoneCover extends PhoneDecorator {

    public PhoneCover(Appearance appearance) {
        super(appearance);
    }

    @Override
    public void structure() {
        super.structure();
        Log.i("TAG", "给手机贴上钢化膜");
    }
}