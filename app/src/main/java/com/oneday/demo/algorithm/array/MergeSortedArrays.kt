package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest

/**
 * 合并两个有序数组
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 *
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 *
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，
 * 其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 *
 * 示例 1：
 *
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
 * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
 * 示例 2：
 *
 * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
 * 输出：[1]
 * 解释：需要合并 [1] 和 [] 。
 * 合并结果是 [1] 。
 * 示例 3：
 *
 * 输入：nums1 = [0], m = 0, nums2 = [1], n = 1
 * 输出：[1]
 * 解释：需要合并的数组是 [] 和 [1] 。
 * 合并结果是 [1] 。
 * 注意，因为 m = 0 ，所以 nums1 中没有元素。nums1 中仅存的 0 仅仅是为了确保合并结果可以顺利存放到 nums1 中。
 *
 *
 * 提示：
 *
 * nums1.length == m + n
 * nums2.length == n
 * 0 <= m, n <= 200
 * 1 <= m + n <= 200
 * -10⁹ <= nums1[i], nums2[j] <= 10⁹
 *
 * Desc: https://leetcode.cn/problems/merge-sorted-array/description/?envType=problem-list-v2&envId=array
 *
 * @author OneD
 * @version 1.0
 * @since 2024/10/31 10:14
 */
object MergeSortedArrays : BaseAlgorithmTest() {

    @JvmStatic
    fun main(args: Array<String>) {
        val nums1 = intArrayOf(0, 1, 2, 2, 4, 5, 7, 8, 0, 0, 0, 0)
        val nums2 = intArrayOf(6, 9, 10, 11)
        val m = 8
        val n = nums2.size
        merge(nums1, m, nums2, n)
        println("nums1: ${nums1.contentToString()}")
    }

    /**
     * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。
     * 为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
     */
    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
        // 采用逆向双指针实现
        var p1 = m - 1 // nums1尾部指针
        var p2 = n - 1 // nums2尾部指针
        var tail = m + n - 1 // 最终数据尾部指针

        /*
        * 严格来说，在此遍历过程中的任意一个时刻，nums1数组中有 (m - 1 - p1) 个元素被放入nums1的后半部，nums2数组中
        * 有 (n - 1 - p2) 个元素被放入nums1的后半部，而在指针p1的后面，nums1数组中还有 (m + n - 1 - p1) 个位置。
        *
        * 需要满足：m + n - 1 - p1 >= m - 1 - p1 + n - 1 - p2
        * 换算一下就是：p2 >= -1，永远成立时，p1后面的位置永远足够容纳被插入的元素，不会产生p1的元素还没插入就被覆盖掉的情况。
        *
        * 更直白的理解就是，我们在把大值放入nums1中的末尾时，如果放的是nums1，则nums1空出一个位置，放的是nums2，才会占位置，而空出的位置刚好是够nums2用的。
        *
        * */

        // 从后往前进行遍历，每次遍历把最大值填充到nums1尾部
        while (p1 >= 0 || p2 >= 0) {
            nums1[tail--] = if (p1 == -1) {
                // nums1已遍历完毕，直接填充nums2剩余数据
                nums2[p2--]
            } else if (p2 == -1) {
                // nums2已遍历完毕，直接填充nums1剩余数据
                nums1[p1--]
            } else if (nums1[p1] > nums2[p2]) {
                // nums1当前元素大于nums2当前元素，填充nums1当前元素，p1指针向前移动
                nums1[p1--]
            } else {
                // nums1当前元素小于nums2当前元素，填充nums2当前元素，p2指针向前移动
                nums2[p2--]
            }
        }
    }
}