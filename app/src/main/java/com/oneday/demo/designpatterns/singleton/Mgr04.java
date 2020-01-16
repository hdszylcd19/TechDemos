package com.oneday.demo.designpatterns.singleton;

/**
 * Desc:lazy loading懒汉式；会存在线程安全问题。
 * 改为在方法内部使用同步代码块实现，发现线程安全问题还是存在。
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/16 0016 9:23
 */
public class Mgr04 {
    private static Mgr04 sInstance;

    private Mgr04() {
    }

    public static Mgr04 getInstance() {
        if (sInstance == null) {
            try {
                // 模拟线程耗时操作
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Mgr04.class) {
                sInstance = new Mgr04();
            }
        }

        return sInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(() -> System.out.println(Mgr04.getInstance().hashCode())).start();
        }
    }
}
