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
    private static Random sRandom = new Random();

    protected static void before() {
        s = System.currentTimeMillis();
    }

    protected static void printTime() {
        printTime("");
    }

    protected static void printTime(String pre) {
        System.out.println(pre + "耗时 : " + (System.currentTimeMillis() - s) + "毫秒");
    }

    protected static int[] getRandomArr(int bound) {
        int[] arr = new int[sRandom.nextInt(bound)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sRandom.nextInt(bound);
        }

        return arr;
    }

    protected static int getRandomInt() {
        return getRandomInt(Integer.MAX_VALUE);
    }

    protected static int getRandomInt(int bound) {
        return sRandom.nextInt(bound);
    }
}
