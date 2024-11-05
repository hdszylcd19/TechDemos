package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest
import com.oneday.demo.utils.NumberUtils

/**
 * Desc:加一
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 *
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 *
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 * 示例 1：
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 *
 * 示例 2：
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 *
 * 示例 3：
 * 输入：digits = [9]
 * 输出：[1,0]
 * 解释：输入数组表示数字 9。
 * 加 1 得到了 9 + 1 = 10。
 * 因此，结果应该是 [1,0]。
 *
 *
 * 提示：
 *
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 *
 * https://leetcode.cn/problems/plus-one/description/?envType=problem-list-v2&envId=array
 *
 * @author OneD
 * @version 1.0
 * @since 2024/11/5 14:32
 */
object PlusOne : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {
        var digits: IntArray
        do {
            digits =
                NumberUtils.generateRandomIntArray(min = 0, max = 9, length = (1..100).random())
        } while (digits[0] == 0)

        println("digits: ${digits.contentToString()}")
        println("result: ${plusOne(digits).contentToString()}")
    }

    /**
     * 优化后的思路：简洁明了，在当前数组中就完成了操作
     */
    fun plusOne(digits: IntArray): IntArray {
        var result = digits
        for (i in digits.size - 1 downTo 0) {
            if (digits[i] == 9) { // 9 + 1 = 10，当前位置保存为：0，并进位1
                digits[i] = 0
            } else {
                digits[i] += 1
                return result
            }
        }

        result = IntArray(digits.size + 1)
        result[0] = 1

        return result
    }

    /**
     * 首次想到的思路
     */
    fun plusOne2(digits: IntArray): IntArray {
        var carry = 1 //进位
        var result = IntArray(digits.size)
        for (i in digits.size - 1 downTo 0) {
            val sum = digits[i] + carry
            result[i] = sum % 10
            carry = sum / 10
        }

        if (carry != 0) {
            result = IntArray(digits.size + 1)
            System.arraycopy(result, 0, result, 1, digits.size)
            result[0] = carry
        }

        return result
    }
}