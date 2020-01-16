package com.oneday.demo.designpatterns.singleton;

/**
 * Desc:lazy loading懒汉式；会存在线程安全问题。
 * 改为在方法内部使用同步代码块实现，在同步代码块中使用"double check"双重检测，
 * 并且增加"volatile"关键字，解决指令重排序的问题。（懒汉式完美写法）
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/16 0016 9:23
 */
public class Mgr06 {
    private static volatile Mgr06 sInstance;
    int i = 5;

    private Mgr06() {
    }

    public static Mgr06 getInstance() {
        if (sInstance == null) {
            try {
                // 模拟线程耗时操作
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Mgr06.class) {
                // double check
                if (sInstance == null) {
                    sInstance = new Mgr06();
                }
            }
        }

        return sInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                Mgr06 instance = Mgr06.getInstance();
                System.out.println(instance.hashCode() + "_" + instance.i);
                if (instance.i == 0) {
                    System.exit(0);
                }
            }).start();
        }
    }
}
