package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest
import com.oneday.demo.utils.NumberUtils
import java.util.Arrays
import kotlin.math.pow

/**
 * Desc:四数之和
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。
 * 请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
 *
 * 0 <= a, b, c, d < n
 * a、b、c 和 d 互不相同
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * 你可以按 任意顺序 返回答案 。
 *
 * 示例 1：
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * 示例 2：
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 *
 * 提示：
 *
 * 1 <= nums.length <= 200
 * -10⁹ <= nums[i] <= 10⁹
 * -10⁹ <= target <= 10⁹
 *
 * https://leetcode.cn/problems/4sum/description/?envType=problem-list-v2&envId=array
 * @author OneD
 * @version 1.0
 * @since 2024/11/6 9:25
 */
object FourSum : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {
        val nums = NumberUtils.generateRandomIntArray(
            min = 10.0.pow(-9.0).toInt(),
            max = 10.0.pow(9.0).toInt(),
            (1..200).random()
        )
        val target =
            NumberUtils.generateRandomInt(min = 10.0.pow(-9.0).toInt(), max = 10.0.pow(9.0).toInt())
        println("nums: ${nums.contentToString()}")
        println("target: $target")
        println("result: ${fourSum(nums, target)}")

        val nums2 = intArrayOf(0, 0, 0, 1000000000, 1000000000, 1000000000, 1000000000)
        println("nums2: ${nums2.contentToString()}")
        println("target2: 1000000000")
        println("result2: ${fourSum(nums2, 1000000000)}")
    }

    /**
     * 最终优化后算法；时间复杂度：O(N³)；空间复杂度：O(N)
     */
    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val n = nums.size
        if (n < 4) {
            return result
        }

        // 对数组进行排序，以简化该问题的复杂度
        Arrays.sort(nums)
        for (i in 0 until n - 3) {
            // 采用Long类型存储a，在后续计算中避免整型溢出
            val a: Long = nums[i].toLong()
            // 数组已经排过序可，避免重复的a出现在返回结果中的第一个元素，以避免重复计算
            if (i > 0 && a == nums[i - 1].toLong()) {
                continue
            }

            //在确定第一个数a后，优化1
            if (a + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                // 说明此时剩下的三个数无论取什么值，四数之和都大于目标值，直接跳出循环
                break
            }

            //在确定第一个数a后，优化2
            if (a + nums[n - 3] + nums[n - 2] + nums[n - 1] < target) {
                // 说明此时剩下的三个数无论取什么值，四数之和都小于目标值，直接进入下一次循环
                continue
            }

            // 以下采用了“三数之和”的解题思路
            val startIndex = i + 1
            val m = n - 2
            for (j in startIndex until m) {
                val b = nums[j]
                if (j > startIndex && b == nums[j - 1]) {
                    continue
                }

                //在确定前两个数a、b后，优化3
                if (a + b + nums[j + 1] + nums[j + 2] > target) {
                    // 说明此时剩下的两个数无论取什么值，四数之和都大于目标值，直接跳出循环
                    break
                }

                //在确定前两个数a、b后，优化4
                if (a + b + nums[n - 2] + nums[n - 1] < target) {
                    // 说明此时剩下的两个数无论取什么值，四数之和都小于目标值，直接进入下一次循环
                    continue
                }

                var k = j + 1
                var r = n - 1
                // 遍历剩余范围内的数组
                while (k < r) {
                    val c = nums[k]
                    val d = nums[r]
                    val sum = a + b + c + d
                    if (sum < target) {
                        k++
                    } else if (sum > target) {
                        r--
                    } else {
                        result.add(arrayListOf(a.toInt(), b, c, d))
                        // 找到不重复的左边界k
                        while (k + 1 < r && c == nums[++k]) {
                        }
                        // 找到不重复的右边界r
                        while (r - 1 > i && d == nums[--r]) {
                        }
                    }
                }
            }
        }

        return result
    }

    /**
     * 算法实现方式1：合并到一个方法中实现。
     */
    fun fourSum1(nums: IntArray, target: Int): List<List<Int>> {
        // 对数组进行排序，以简化该问题的复杂度
        Arrays.sort(nums)

        val n = nums.size
        val result = mutableListOf<List<Int>>()
        for (i in 0 until n - 3) {
            val a = nums[i]
            // 数组已经排过序可，避免重复的a出现在返回结果中的第一个元素，以避免重复计算
            if (i > 0 && a == nums[i - 1]) {
                continue
            }

            // 以下采用了“三数之和”的解题思路
            val startIndex = i + 1
            val m = n - 2
            for (j in startIndex until m) {
                val b = nums[j]
                if (j > startIndex && b == nums[j - 1]) {
                    continue
                }

                var k = j + 1
                var r = n - 1
                // 遍历剩余范围内的数组
                while (k < r) {
                    val c = nums[k]
                    val d = nums[r]
                    // 此处采用减法进行计算，避免整型溢出
                    if (0 < target.toLong() - a - b - c - d) {
                        k++
                    } else if (0 > target.toLong() - a - b - c - d) {
                        r--
                    } else {
                        result.add(arrayListOf(a, b, c, d))
                        // 找到不重复的左边界k
                        while (k + 1 < r && c == nums[++k]) {
                        }
                        // 找到不重复的右边界r
                        while (r - 1 > i && d == nums[--r]) {
                        }
                    }
                }
            }
        }

        return result
    }

    /**
     * 算法实现方式2：拆分两个方法实现
     */
    fun fourSum2(nums: IntArray, target: Int): List<List<Int>> {
        // 对数组进行排序，以简化该问题的复杂度
        Arrays.sort(nums)

        val n = nums.size
        val result = mutableListOf<List<Int>>()
        for (i in 0 until n - 3) {
            val a = nums[i]
            if (i > 0 && a == nums[i - 1]) {
                continue
            }

            val threeSum = threeSum2(nums, i + 1, (target - a).toLong())
            if (threeSum.isNotEmpty()) {
                threeSum.forEach {
                    it.add(0, a)
                }
                result.addAll(threeSum)
            }
        }

        return result
    }

    fun threeSum2(nums: IntArray, startIndex: Int, target: Long): ArrayList<ArrayList<Int>> {
        // 核心算法逻辑，就一句话：先固定第一个数a，然后b、c只能从两边向中间靠（在a之后）。细节条件就是去重处理！
        val result = ArrayList<ArrayList<Int>>()
        val n = nums.size
        for (i in startIndex..n - 3) {
            val a = nums[i]
            if (i > startIndex && a == nums[i - 1]) {
                continue
            }

            var l = i + 1
            var r = n - 1
            // 遍历剩余范围内的数组
            while (l < r) {
                val b = nums[l]
                val c = nums[r]
                if (0 < target - a - b - c) {
                    l++
                } else if (0 > target - a - b - c) {
                    r--
                } else {
                    result.add(arrayListOf(a, b, c))
                    // 找到不重复的左边界l
                    while (l + 1 < r && b == nums[++l]) {
                    }
                    // 找到不重复的右边界r
                    while (r - 1 > i && c == nums[--r]) {
                    }
                }
            }
        }

        return result
    }
}