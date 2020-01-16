package com.oneday.demo.designpatterns.singleton;

/**
 * Desc:"Effective Java"推荐单例写法。可以解决Java反序列化问题。
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/16 0016 10:27
 */
public enum Mgr08 {
    INSTANCE;

    public void print() {
        System.out.println("enum INSTANCE");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                System.out.println(INSTANCE.hashCode());
                Mgr08.INSTANCE.print();
            }).start();
        }
    }
}
