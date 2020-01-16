package com.oneday.demo.designpatterns.singleton;

/**
 * Desc: 使用静态内部类实现单例模式。（比单纯使用"饿汉式"更加完美）
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/16 0016 10:21
 */
public class Mgr07 {
    private Mgr07() {
    }

    private static class Holder {
        private static final Mgr07 INSTANCE = new Mgr07();
    }

    public static Mgr07 getInstance() {
        return Holder.INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(() -> System.out.println(Mgr07.getInstance().hashCode())).start();
        }
    }
}
