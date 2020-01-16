package com.oneday.demo.designpatterns.singleton;

/**
 * Desc:lazy loading懒汉式；会存在线程安全问题。
 * 改为同步方法实现后，虽然可以解决线程安全问题，但是效率会降低。
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/16 0016 9:23
 */
public class Mgr03 {
    private static Mgr03 sInstance;

    private Mgr03() {
    }

    public static synchronized Mgr03 getInstance() {
        if (sInstance == null) {
            try {
                // 模拟线程耗时操作
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sInstance = new Mgr03();
        }

        return sInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(() -> System.out.println(Mgr03.getInstance().hashCode())).start();
        }
    }
}
