package com.oneday.demo.designpatterns.singleton;

/**
 * Desc:"饿汉式"单例设计模式.随着class文件的加载而加载,由JVM保证线程安全
 * 优点：简单实用
 * 缺点：冗余加载（当单例对象都没有使用时，都已经加载了）
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/16 0016 9:21
 */
public class Mgr01 {
    private static final Mgr01 INSTANCE = new Mgr01();

    private Mgr01() {
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(() -> System.out.println(INSTANCE.hashCode())).start();
        }
    }
}
