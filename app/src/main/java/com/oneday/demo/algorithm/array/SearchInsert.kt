package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest

/**
 * Desc: 搜索插入位置
 *
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 *
 * 请必须使用时间复杂度为 O(log n) 的算法。
 *
 * 示例 1:
 * 输入: nums = [1,3,5,6], target = 5
 * 输出: 2
 *
 * 示例 2:
 * 输入: nums = [1,3,5,6], target = 2
 * 输出: 1
 *
 * 示例 3:
 * 输入: nums = [1,3,5,6], target = 7
 * 输出: 4
 *
 * 提示:
 *
 * 1 <= nums.length <= 10⁴
 * -10⁴ <= nums[i] <= 10⁴
 * nums 为 无重复元素 的 升序 排列数组
 * -10⁴ <= target <= 10⁴
 *
 * https://leetcode.cn/problems/search-insert-position/description/?envType=problem-list-v2&envId=array
 * @author OneD
 * @version 1.0
 * @since 2024/11/6 16:56
 */
object SearchInsert : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {
//        val nums = NumberUtils.generateRandomSortedUniqueIntArray(
//            min = -10000,
//            max = 10000,
//            length = 10000
//        )
//        val target = NumberUtils.generateRandomInt(min = -10000, max = 10000)
//        println(
//            "nums: ${nums.contentToString()}, target: $target, index = ${
//                searchInsert(
//                    nums,
//                    target
//                )
//            }"
//        )

        val nums2 = intArrayOf(1, 2, 3, 4, 5)
        val target2 = -1
        println(
            "nums2: ${nums2.contentToString()}, target2: $target2, index = ${
                searchInsert(
                    nums2,
                    target2
                )
            }"
        )

        val nums3 = intArrayOf(1, 2, 3, 4, 5)
        val target3 = 10
        println(
            "nums3: ${nums3.contentToString()}, target3: $target3, index = ${
                searchInsert(
                    nums3,
                    target3
                )
            }"
        )
    }

    /**
     * 二分查找
     * 时间复杂度：O(log n)
     * 空间复杂度：O(1)
     */
    fun searchInsert(nums: IntArray, target: Int): Int {
        var s = 0
        var e = nums.size - 1
        // 使用二分查找法搜索目标元素
        while (s <= e) {
            val m = (s + e) / 2
            val a = nums[m]
            if (a > target) {
                // 缩小右边界
                e = m - 1
            } else if (a < target) {
                // 缩小左边界
                s = m + 1
            } else {
                // 找到了直接返回索引
                return m
            }
        }

        // 如果原数组中没有找到目标值，则s就是target在数组中的插入位置
        return s
    }
}