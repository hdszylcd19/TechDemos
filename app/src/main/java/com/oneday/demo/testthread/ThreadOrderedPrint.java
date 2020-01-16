package com.oneday.demo.testthread;

/**
 * Desc:"两个线程，一个线程先打印1，一个线程再打印A；以此类推...最终输出1A2B3C...26Z"
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/16 0016 11:07
 */
public class ThreadOrderedPrint {
    private static volatile boolean sFlag = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            int i;

            @Override
            public void run() {
                while (i < 26) {
                    if (!sFlag) {
                        System.out.print(++i);
                        sFlag = true;
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            char c = 'A';

            @Override
            public void run() {
                while (c <= 'Z') {
                    if (sFlag) {
                        System.out.print(c++ + " ");
                        sFlag = false;
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }
}
