package com.oneday.demo.designpatterns.singleton;

/**
 * Desc:lazy loading懒汉式；会存在线程安全问题
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/16 0016 9:23
 */
public class Mgr02 {
    private static Mgr02 sInstance;

    private Mgr02() {
    }

    public static Mgr02 getInstance() {
        if (sInstance == null) {
            try {
                // 模拟线程耗时操作
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sInstance = new Mgr02();
        }

        return sInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(() -> System.out.println(Mgr02.getInstance().hashCode())).start();
        }
    }
}
