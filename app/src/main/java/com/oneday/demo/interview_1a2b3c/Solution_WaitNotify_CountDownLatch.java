package com.oneday.demo.interview_1a2b3c;

import java.util.concurrent.CountDownLatch;

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/17 0017 8:57
 */
public class Solution_WaitNotify_CountDownLatch {
    private static final Object sLock = new Object();

    public static void main(String[] args) {
        CountDownLatch startSignal = new CountDownLatch(1);

        Thread t1 = new Thread(new Runnable() {
            int i;

            @Override
            public void run() {
                while (i < 26) {
                    synchronized (sLock) {
                        System.out.print(++i);
                        startSignal.countDown();
                        sLock.notifyAll(); //唤醒所有等待这把锁的线程
                        try {
                            sLock.wait(); //当前线程等待,释放锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                synchronized (sLock) {
                    sLock.notifyAll(); //打印完成,唤醒所有等待这把锁的线程,避免程序无法中止
                }

                System.out.println();
                System.out.println("t1结束执行 " + i);
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            char c = 'a';

            @Override
            public void run() {
                try {
                    startSignal.await(); //t2在此处等待t1先打印完毕
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (c <= 'z') {
                    synchronized (sLock) {
                        System.out.print(c++ + " ");
                        sLock.notifyAll(); //唤醒所有等待这把锁的线程
                        try {
                            sLock.wait(); //当前线程等待,释放锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                synchronized (sLock) {
                    sLock.notifyAll(); //打印完成,唤醒所有等待这把锁的线程,避免程序无法中止
                }

                System.out.println();
                System.out.println("t2结束执行 " + c);
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
