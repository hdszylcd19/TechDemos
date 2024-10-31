package com.oneday.demo.algorithm;

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
}
