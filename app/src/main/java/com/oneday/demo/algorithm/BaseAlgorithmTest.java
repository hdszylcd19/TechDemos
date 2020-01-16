package com.oneday.demo.algorithm;

import java.util.Random;

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/14 0014 10:35
 */
public abstract class BaseAlgorithmTest {

    private static long s;

    protected static void before() {
        s = System.currentTimeMillis();
    }

    protected static void printTime() {
        System.out.println("耗时 : " + (System.currentTimeMillis() - s) + "毫秒");
    }

    protected static int[] getRandomArr() {
        Random random = new Random();
        int[] arr = new int[random.nextInt(10000)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10000);
        }

        return arr;
    }
}
