package com.oneday.demo.test_number;

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/7/30 0030 16:57
 */
class TestFloat {
    public static void main(String[] args) {
        /*超乎你的想象！！！以下代码f2所表示的数打印出来还是8388608.0*/
        float f1 = 8388608f;
        System.out.println("f1 = " + f1); //8388608.0
        float f2 = 8388608.5f;
        System.out.println("f2 = " + f2); //8388608.0
        float f3 = 8388609f;
        System.out.println("f3 = " + f3); //8388609.0
    }
}
