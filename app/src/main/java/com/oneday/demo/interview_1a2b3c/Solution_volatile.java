package com.oneday.demo.interview_1a2b3c;

/**
 * Desc:"用两个线程，一个线程先打印1，一个线程再打印A；以此类推...最终输出1a2b3c...26z"
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/16 0016 11:07
 */
public class Solution_volatile {
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
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            char c = 'a';

            @Override
            public void run() {
                while (c <= 'z') {
                    if (sFlag) {
                        System.out.print(c++ + " ");
                        sFlag = false;
                    }
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
