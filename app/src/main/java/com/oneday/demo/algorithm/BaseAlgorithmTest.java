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
        printTime("");
    }

    protected static void printTime(String pre) {
        System.out.println(pre + "耗时 : " + (System.currentTimeMillis() - s) + "毫秒");
    }

    protected static int[] getRandomArr(int bound) {
        Random random = new Random();
        int[] arr = new int[random.nextInt(bound)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(bound);
        }

        return arr;
    }
}
