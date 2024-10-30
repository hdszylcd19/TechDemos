package com.oneday.demo.kt_coroutine;

/**
 * Desc:
 *
 * @author OneD
 * @version 1.0
 * @since 2022/6/27 11:20
 */
public class TestJavaLabel {
    public static void main(String[] args) {
        int i = 0;
        label1:
        {
            switch (i) {
                case 0:
                    i = 1;
                    System.out.println("i = " + i);
                    break label1;
                case 1:
                    i = 0;
                    System.out.println("i = " + i);
                    break label1;
            }

            System.out.println("finish switch！");
        }
    }
}
