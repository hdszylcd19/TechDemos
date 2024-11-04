package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest
import com.oneday.demo.utils.NumberUtils

/**
 * Desc:两数之和
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
 *
 * 你可以按任意顺序返回答案。
 *
 * 示例 1：
 *
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 示例 2：
 *
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * 示例 3：
 *
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 *
 * 提示：
 *
 * 2 <= nums.length <= 10⁴
 * -10⁹ <= nums[i] <= 10⁹
 * -10⁹ <= target <= 10⁹
 * 只会存在一个有效答案
 *
 *
 * 进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？
 *
 * https://leetcode.cn/problems/two-sum/description/
 *
 * @author OneD
 * @version 1.0
 * @since 2024/11/4 16:00
 */
object TwoSum : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {
        val intArray = NumberUtils.generateRandomIntArray()
        println("nums: ${intArray.contentToString()}")
        val target = NumberUtils.generateRandomInt()
        println("target: $target")
        val result = twoSum(intArray, target)
        println("result: ${result.contentToString()}")
    }


    /**
     * Desc:两数之和
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
     *
     * 你可以按任意顺序返回答案。
     * 没有找到目标答案时，返回 [-1, -1] 。
     */
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val result = intArrayOf(-1, -1)
        val map = LinkedHashMap<Int, Int>(nums.size)
        for (i in nums.indices) {
            val t = map[target - nums[i]]
            if (t != null) {
                result[0] = i
                result[1] = t
                break
            }

            map[nums[i]] = i
        }

        return result
    }
}