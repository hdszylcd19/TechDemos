package com.oneday.demo.algorithm.math

/**
 * Desc:整数反转
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 *
 * 如果反转后整数超过 32 位的有符号整数的范围 [-2^31,  2^31 − 1] ，就返回 0。
 *
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 * 示例 1：
 *
 * 输入：x = 123
 * 输出：321
 * 示例 2：
 *
 * 输入：x = -123
 * 输出：-321
 * 示例 3：
 *
 * 输入：x = 120
 * 输出：21
 * 示例 4：
 *
 * 输入：x = 0
 * 输出：0
 * 提示：
 *
 * -2^31 <= x <= 2^31 - 1
 *
 * https://leetcode.cn/problems/reverse-integer/description/
 *
 * @author OneD
 * @version 1.0
 * @since 2024/11/8 13:19
 */
object IntReverse {
    @JvmStatic
    fun main(args: Array<String>) {
        val x = 123
        println("x = $x，reverse = ${reverse(x)}")
        val x1 = -123
        println("x1 = $x1，reverse = ${reverse(x1)}")
        val x2 = 120
        println("x2 = $x2，reverse = ${reverse(x2)}")
        val x3 = 0
        println("x3 = $x3，reverse = ${reverse(x3)}")
        val x4 = Int.MIN_VALUE
        println("x4 = $x4，reverse = ${reverse(x4)}")
        val x5 = Int.MAX_VALUE
        println("x5 = $x5，reverse = ${reverse(x5)}")
    }

    fun reverse(x: Int): Int {
        val min = Int.MIN_VALUE / 10
        val max = Int.MAX_VALUE / 10
        var tmp = x
        var result = 0
        // 从个位向前计算出每个位上的值
        while (tmp != 0) {
            // 该题的难点在于：假设环境不允许存储 64 位整数（有符号或无符号），如果反转后整数超过 32 位的有符号整数的范围，就返回 0。
            // int最小值 = -2147483648 ；min = -214748364
            // int最小值 = 2147483647 ；max = 214748364
            if (result < min || result > max) {
                // 反转后可能溢出int范围的数，肯定是一个10位的整数，其它低于10位的整数怎么反转都不会溢出。
                // 又因为该方法接收的是一个int类型的整数，所以当参数传入的是10位整数的话，则首位只可能是2或1，其它数字int类型装不下。
                // 反转之后，末位也只可能是2或1。
                // 所以在计算最后一位数之前进行判断，[-214748364,214748364]之间的数 * 10 + (2或1)，肯定不会溢出。
                return 0
            }

            val digit = tmp % 10 //负数的余数也为负数
            tmp /= 10 //负数的商为负数
            result = result * 10 + digit
        }

        return result
    }
}