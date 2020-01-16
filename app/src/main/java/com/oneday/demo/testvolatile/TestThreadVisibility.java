package com.oneday.demo.testvolatile;

/**
 * Desc: 测试线程可见性
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/15 0015 14:12
 */
public class TestThreadVisibility {
    // 当该标记值没有用volatile修饰时,则该程序无法正常中止
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    //do sth
                }
                System.out.println("end");
            }
        }, "server").start();


        Thread.sleep(1000);

        flag = false;
    }
}
