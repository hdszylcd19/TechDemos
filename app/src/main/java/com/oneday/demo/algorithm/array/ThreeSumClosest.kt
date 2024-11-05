package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest
import com.oneday.demo.utils.NumberUtils
import java.util.Arrays
import kotlin.math.abs

/**
 * Desc:最接近的三数之和
 *
 * 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
 *
 * 返回这三个数的和。
 *
 * 假定每组输入只存在恰好一个解。
 *
 *
 * 示例 1：
 *
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2)。
 * 示例 2：
 *
 * 输入：nums = [0,0,0], target = 1
 * 输出：0
 * 解释：与 target 最接近的和是 0（0 + 0 + 0 = 0）。
 *
 *
 * 提示：
 *
 * 3 <= nums.length <= 1000
 * -1000 <= nums[i] <= 1000
 * -10⁴ <= target <= 10⁴
 *
 * https://leetcode.cn/problems/3sum-closest/description/?envType=problem-list-v2&envId=array
 *
 * @author OneD
 * @version 1.0
 * @since 2024/11/5 13:23
 */
object ThreeSumClosest : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {
        val nums =
            NumberUtils.generateRandomIntArray(min = -1000, max = 1000, length = (3..1000).random())
        println("nums: ${nums.contentToString()}")
        val target = NumberUtils.generateRandomInt(-10000, 10000)
        println("target: $target")
        val closest = threeSumClosest(nums, target)
        println("closest: $closest")
    }

    fun threeSumClosest(nums: IntArray, target: Int): Int {
        //首先对数组进行排序，以简化该问题的复杂度
        Arrays.sort(nums)

        val n = nums.size
        var closest = Integer.MAX_VALUE
        for (i in 0 until n - 2) {
            // 优化之前已经计算出结果的双指针运算，避免重复计算
            val a = nums[i]
            if (i > 0 && a == nums[i - 1]) {
                continue
            }

            var left = i + 1
            var right = n - 1
            while (left < right) {
                val b = nums[left]
                val c = nums[right]
                val sum = a + b + c
                if (sum == target) {
                    // 完美找到目标值，直接返回
                    return sum
                } else if (sum < target) {
                    // 继续向右寻找到不重复的左边界
                    do {
                        left++
                    } while (left < right && b == nums[left])
                } else {
                    // 继续向左寻找到不重复的右边界
                    do {
                        right--
                    } while (left < right && c == nums[right])
                }

                // 更新最接近的三数之和
                if (abs(sum - target) < abs(closest - target)) {
                    closest = sum
                }
            }
        }

        return closest
    }
}