package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest
import com.oneday.demo.utils.NumberUtils.generateRandomInt
import com.oneday.demo.utils.NumberUtils.generateRandomSortedIntArray
import kotlin.math.max
import kotlin.math.min
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * 寻找两个有序数组的中位数
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 *
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 *
 * 示例 1：
 *
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * 示例 2：
 *
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 *
 *
 * 提示：
 *
 * nums1.length == m
 * nums2.length == n
 * 0 <= m <= 1000
 * 0 <= n <= 1000
 * 1 <= m + n <= 2000
 * -10⁶ <= nums1[i], nums2[i] <= 10⁶
 *
 * Desc: 题目地址：https://leetcode.cn/problems/median-of-two-sorted-arrays/description/?envType=problem-list-v2&envId=array
 *
 * @author OneD
 * @version 1.0
 * @since 2024/10/30 16:11
 */
object FindMedianSortedArrays : BaseAlgorithmTest() {

    @OptIn(ExperimentalTime::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val nums1 = generateRandomSortedIntArray(length = generateRandomInt(1, 100))
        val nums2 = generateRandomSortedIntArray(length = generateRandomInt(1, 100))
        measureTime {
            val median = findMedianSortedArrays(nums1, nums2)
            println("中位数 = $median")
        }.apply {
            println("nums1[${nums1.size}] = ${nums1.contentToString()}")
            println("nums2[${nums2.size}] = ${nums2.contentToString()}")
            println("执行耗时：${this}")
        }
    }

    /**
     * 算法思路：为了使用划分的方法解决这个问题，需要理解「中位数的作用是什么」。
     * 在统计中，中位数被用来：将一个集合划分为两个长度相等的子集，其中一个子集中的元素总是大于另一个子集中的元素。
     * 如果理解了中位数的划分作用，就很接近答案了。
     * 首先，在任意位置 i 将 A 划分成两个部分：
     *            left_A            |          right_A
     *     A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
     *
     * 由于 A 中有 m 个元素， 所以有 m+1 种划分的方法（i∈[0,m]）。
     *
     * len(left_A) = i, len(right_A)= m − i.
     *
     * 注意：当 i=0 时，left_A 为空集， 而当 i=m 时, right_A 为空集。
     *
     * 采用同样的方式，在任意位置 j 将 B 划分成两个部分：
     *
     *            left_B            |          right_B
     *     B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
     *
     * 将 left_A 和 left_B 放入一个集合，并将 right_A 和 right_B 放入另一个集合。 再把这两个新的集合分别命名为 left_part 和 right_part：
     *           left_part          |         right_part
     *     A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
     *     B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
     *
     * 当 A 和 B 的总长度是偶数时，如果可以确认：
     *
     * len(left_part)=len(right_part)
     * max(left_part)≤min(right_part)
     * 那么，{A,B} 中的所有元素已经被划分为相同长度的两个部分，且前一部分中的元素总是小于或等于后一部分中的元素。中位数就是前一部分的最大值和后一部分的最小值的平均值：
     *
     * median = (max(left_part)+min(right_part)) / 2
     *
     * 当 A 和 B 的总长度是奇数时，如果可以确认：
     *
     * len(left_part)=len(right_part)+1
     * max(left_part)≤min(right_part)
     *
     */
    private fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        if (nums1.size > nums2.size) {
            // 因为我们要二分查找长度较短的数组，所以确保nums1的长度小于num2的长度
            return findMedianSortedArrays(nums2, nums1)
        }

        val m = nums1.size
        val n = nums2.size

        // median1:前一部分小值集合的最大值
        // median2:后一部分大值集合的最小值
        var median1 = 0
        var median2 = 0

        // 这里只去二分查找长度较短的数组
        var left = 0
        var right = m
        while (left <= right) {
            // 划分两个数组：我们按照前一部分比后一部分多一个元素进行划分（人为定义的，当然也可以定义为少一个元素，那么对应的公式需要修改），
            // 也就是说，当(m+n)的长度为偶数时，前一部分也比后一部分多一个元素，而不是两边元素相等。
            // 这样做的目的是可以忽略(m+n)的长度为偶数的情况，可以使用统一公式进行计算i和j的值。
            // i表示nums1的前一部分小值的元素数量，j表示nums2的前一部分小值的元素数量
            // 统一公式：i+j=m−i+n−j（当 m+n 为偶数）或 i+j=m−i+n−j+1（当 m+n 为奇数）。
            // 那么合同同类项后可知：i+j = (m + n + 1) / 2，因此 j = (m + n + 1) / 2 - i。

            // 前一部分包含 nums1[0 .. i-1] 和 num2[0 .. j-1]
            // 后一部分包含 nums1[i .. m-1] 和 num2[j .. n-1]
            val i = (left + right) / 2
            val j = (m + n + 1) / 2 - i


            // nums1[i-1]，nums1中前一部分的最大值
            val numsIm1 = if (i == 0) Int.MIN_VALUE else nums1[i - 1]
            // nums1[i]，nums1中后一部分的最小值
            val numsI = if (i == m) Int.MAX_VALUE else nums1[i]
            // nums2[j-1]，nums2中前一部分的最大值
            val numsJm1 = if (j == 0) Int.MIN_VALUE else nums2[j - 1]
            // nums2[j]，nums2中后一部分的最小值
            val numsJ = if (j == n) Int.MAX_VALUE else nums2[j]

            // 我们只需要比较i和j两边的元素，确保满足条件：nums1[i - 1] <= nums2[j] && nums2[j - 1] <= nums1[i]即可。
            // 等价于，在[0,m]中找到最大的i，使得：nums1[i - 1] <= nums2[j]。
            // 这是因为，当 i 从 0∼m 递增时，nums1[i−1] 递增，nums2[j] 递减，所以一定存在一个最大的 i 满足 nums1[i−1]<= nums2[j]；
            // 如果 i 是最大的，那么说明 i+1 不满足条件。

            // 在nums1中找到最大的i，使得nums1[i-1] <= nums2[j]
            if (numsIm1 <= numsJ) {
                //缩小左边界
                median1 = max(numsIm1.toDouble(), numsJm1.toDouble()).toInt()
                median2 = min(numsI.toDouble(), numsJ.toDouble()).toInt()
                left = i + 1
            } else {
                //缩小右边界
                right = i - 1
            }
        }

        return if ((m + n) % 2 == 0) (median1 + median2) / 2.0 else median1.toDouble()
    }
}