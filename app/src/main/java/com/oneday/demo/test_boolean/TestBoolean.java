package com.oneday.demo.test_boolean;

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/6/29 0029 10:18
 */
class TestBoolean {
    public static void main(String[] args) {
        print(false, false);
        print(true, false);
        print(false, true);
        print(true, true);
    }

    private static void print(boolean flag, boolean mask) {
        System.out.println("flag = " + flag + ", mask = " + mask + "; =>>> " + (flag |= mask));
    }
}
