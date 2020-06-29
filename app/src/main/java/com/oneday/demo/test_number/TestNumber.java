package com.oneday.demo.test_number;

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/6/19 0019 14:53
 */
public class TestNumber {
    public static void main(String[] args) {
        System.out.println("--------------float");
        System.out.println(" 1.0f  除以 0    = " + 1.0f / 0); //Infinity
        System.out.println(" 1.0f  除以 0.0f = " + 1.0f / 0.0f); //Infinity
        System.out.println("-1.0f  除以 0.0f = " + -1.0f / 0.0f); //-Infinity
        System.out.println(" 0.0f  除以 0.0f = " + 0.0f / 0.0f); //NaN
        System.out.println("-0.0f  除以 0.0f = " + -0.0f / 0.0f); //NaN
        System.out.println("--------------float");

        System.out.println();

        System.out.println("--------------double");
        System.out.println("1.0 除以 0 = " + 1.0 / 0); //Infinity
        System.out.println("--------------double");
    }
}
