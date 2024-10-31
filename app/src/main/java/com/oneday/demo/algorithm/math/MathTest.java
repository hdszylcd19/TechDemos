package com.oneday.demo.algorithm.math;

import com.oneday.demo.algorithm.BaseAlgorithmTest;
import com.oneday.demo.utils.NumberUtils;

/**
 * Desc: 获取两个正整数的最大公约数
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/11/23 10:10
 */
public class MathTest extends BaseAlgorithmTest {
    public static void main(String[] args) {
        int a = NumberUtils.generateRandomInt(0, 10000);
        int b = NumberUtils.generateRandomInt(0, 10000);
        printGcd(a, b);
        printGcd(1024, 768);
        printGcd(1200, 1920);
        printGcd(1200, 2000);
    }

    private static void printGcd(int a, int b) {
        before();
        int gcd = gcd_division_recursive(a, b);
        printTime("gcd_division_recursive()");
        System.out.println("[a = " + a + ", b = " + b + ", gcd = " + gcd + "]");
    }

    /**
     * 获取两个正整数的最大公约数
     */
    public static int getGreatestCommonDivisor(int a, int b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        if (a == b) {
            return a;
        }

        int max;
        int min;
        if (a > b) {
            max = a;
            min = b;
        } else {
            max = b;
            min = a;
        }

        int r = max % min;
        return getGreatestCommonDivisor(r, min);
    }

    /*  辗转相除法证明过程：
        设g是a,b的公约数，则a,b可由g来表示：
        a = xg, b = yg (x,y为整数)
        又，a可由b表示（任意一个数都可以由另一个数来表示）：
        a = kb + r (k为整数，r为a除以b所得余数)
        => r = a - kb = xg - kyg = (x - ky)g
        即，g也是r的约数。
        这样，g就是(b, r)即(b, a mod b)的公约数。
     */
    // 辗转相除法（递归写法）
    public static int gcd_division_recursive(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd_division_recursive(b, a % b);
    }

    // 辗转相除法（迭代写法）
    public static int gcd_division_iteration(int a, int b) {
        while (b != 0) {// 为什么用b判断呢？因为b就是用来存a%b的，即上面算法步骤里的r的
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}
