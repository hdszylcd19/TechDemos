package com.oneday.demo.interview_1a2b3c;

import java.util.concurrent.locks.LockSupport;

/**
 * Desc:使用LockSupport解决线程间通信问题,其实底层实现使用的还是Unsafe实现的.
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/17 0017 8:57
 */
public class Solution_LockSupport {
    private static Thread t1;
    private static Thread t2;

    public static void main(String[] args) {
        t1 = new Thread(new Runnable() {
            int i;

            @Override
            public void run() {
                while (i < 26) {
                    System.out.print(++i);
                    LockSupport.unpark(t2); //每次打印完之后，唤醒t2
                    LockSupport.park(); //阻塞自己，等待t2将其唤醒
                }
            }
        }, "t1");

        t2 = new Thread(new Runnable() {
            char c = 'a';

            @Override
            public void run() {
                while (c <= 'z') {
                    LockSupport.park(); //t2如果先执行的话，会在这里阻塞，等待t1将其唤醒
                    System.out.print(c++ + " ");
                    LockSupport.unpark(t1); //每次打印完之后，唤醒t1
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
