package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest
import com.oneday.demo.utils.NumberUtils
import java.util.Arrays

/**
 * Desc:三数之和
 *
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
 * 同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 示例 1：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
 * 注意，输出的顺序和三元组的顺序并不重要。
 *
 * 示例 2：
 * 输入：nums = [0,1,1]
 * 输出：[]
 * 解释：唯一可能的三元组和不为 0 。
 *
 * 示例 3：
 * 输入：nums = [0,0,0]
 * 输出：[[0,0,0]]
 * 解释：唯一可能的三元组和为 0 。
 *
 *
 * 提示：
 *
 * 3 <= nums.length <= 3000
 * -10⁵ <= nums[i] <= 10⁵
 *
 *
 * https://leetcode.cn/problems/3sum/description/?envType=problem-list-v2&envId=array
 *
 * @author OneD
 * @version 1.0
 * @since 2024/11/4 16:41
 */
object ThreeSum : BaseAlgorithmTest() {

    @JvmStatic
    fun main(args: Array<String>) {
        val intArray =
            NumberUtils.generateRandomIntArray(min = -100000, max = 100000, (3..3000).random())
        println(threeSum(intArray))
    }

    fun threeSum(nums: IntArray): List<List<Int>> {
        // 先对数组进行升序排列，以减少该问题的复杂度
        Arrays.sort(nums)

        // 核心算法逻辑，就一句话：先固定第一个数a，然后b、c只能从两边向中间靠（在a之后）。细节条件就是去重处理！
        val result = ArrayList<List<Int>>()
        val n = nums.size
        for (i in nums.indices) {
            val a = nums[i]
            if (i > 0 && a == nums[i - 1]) {
                continue
            }

            var l = i + 1
            var r = n - 1
            // 遍历剩余范围内的数组
            while (l < r) {
                val b = nums[l]
                val c = nums[r]
                if (a + b + c < 0) {
                    l++
                } else if (a + b + c > 0) {
                    r--
                } else {
                    result.add(listOf(a, b, c))
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