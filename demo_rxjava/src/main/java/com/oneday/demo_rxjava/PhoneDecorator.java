package com.oneday.demo_rxjava;

/**
 * 手机包装类，用来包装手机
 */
public abstract class PhoneDecorator implements Appearance {

    protected Appearance appearance;

    public PhoneDecorator(Appearance appearance) {
        this.appearance = appearance;
    }

    @Override
    public void structure() {
        appearance.structure();
    }
}