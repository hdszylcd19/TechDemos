package com.oneday.demo.designpatterns.singleton;

/**
 * Desc:lazy loading懒汉式；会存在线程安全问题。
 * 改为在方法内部使用同步代码块实现，在同步代码块中使用"double check"双重检测。
 * 此时还是会有一些小问题：由于cpu底层存在指令重排序的问题，在多线程极端情况下会返回
 * 一个"半初始化"的单例对象，导致调用出现问题。该问题不太容易复现......
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/16 0016 9:23
 */
public class Mgr05 {
    private static Mgr05 sInstance;
    int i = 5;

    private Mgr05() {
    }

    public static Mgr05 getInstance() {
        if (sInstance == null) {
            try {
                // 模拟线程耗时操作
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Mgr05.class) {
                // double check
                if (sInstance == null) {
                    sInstance = new Mgr05();
                }
            }
        }

        return sInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                Mgr05 instance = Mgr05.getInstance();
                System.out.println(instance.hashCode() + "_" + instance.i);
                if (instance.i == 0) {
                    System.exit(0);
                }
            }).start();
        }
    }
}
