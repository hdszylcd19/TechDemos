package com.oneday.demo.interview_1a2b3c;

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/17 0017 8:57
 */
public class Solution_WaitNotify {
    private static final Object sLock = new Object();
    private static volatile boolean sFlag = false; //用来控制线程执行顺序

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            int i;

            @Override
            public void run() {
                while (!sFlag) {
                    sFlag = true; //自旋修改标记值为true
                }

                while (i < 26) {
                    synchronized (sLock) {
                        System.out.println("t1得到锁 " + (++i));
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

                System.out.println("t1结束执行 " + i);
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            char c = 'a';

            @Override
            public void run() {
                synchronized (sLock) {
                    while (!sFlag) {
                        try {
                            System.out.println("t2等待t1先执行...");
                            sLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                while (c <= 'z') {
                    synchronized (sLock) {
                        System.out.println("t2得到锁 " + (c++));
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

                System.out.println("t2结束执行 " + c);
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
