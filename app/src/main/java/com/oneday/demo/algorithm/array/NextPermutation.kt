package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest
import com.oneday.demo.utils.NumberUtils

/**
 * Desc: 下一个排列
 *
 * 整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
 *
 * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
 * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，
 * 那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
 *
 * 例如，arr = [1,2,3]，也就是比数字123大的的下一个排列是 [1,3,2] 。
 * 类似地，arr = [2,3,1]，也就是比数字231大的下一个排列是 [3,1,2] 。
 * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
 * 给你一个整数数组 nums ，找出 nums 的下一个排列。
 *
 * 必须 原地 修改，只允许使用额外常数空间。
 *
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[1,3,2]
 *
 * 示例 2：
 * 输入：nums = [3,2,1]
 * 输出：[1,2,3]
 *
 * 示例 3：
 * 输入：nums = [1,1,5]
 * 输出：[1,5,1]
 *
 * 提示：
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 9
 *
 * https://leetcode.cn/problems/next-permutation/description/?envType=problem-list-v2&envId=array
 *
 * @author OneD
 * @version 1.0
 * @since 2024/11/6 14:41
 */
object NextPermutation : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {
        val nums =
            NumberUtils.generateRandomIntArray(min = 0, max = 9, length = (1..100).random())
        println("nums: ${nums.contentToString()}")
        nextPermutation(nums)
        println("下一个序列：${nums.contentToString()}")
    }

    /**
     * 下一个排列最优算法；时间复杂度O(n)，空间复杂度O(1)
     *
     * 核心思路：
     * 我们注意到：“下一个排列”总是比当前排列要大，除非该排列已经是最大的排列。我们希望找到一种方法，能够找到一个大于当前序列的新序列，且变大的幅度尽可能小。具体地：
     *
     * 我们需要将一个左边的「较小数」与一个右边的「较大数」交换，以能够让当前排列变大，从而得到下一个排列。
     *
     * 同时我们要让这个「较小数」尽量靠右，而「较大数」尽可能小。当交换完成后，「较大数」右边的数需要按照升序重新排列。
     * 这样可以在保证新排列大于原来排列的情况下，使变大的幅度尽可能小。
     *
     */
    fun nextPermutation(nums: IntArray) {
        /*
        1.要想找到下一个紧邻的序列，我们需要从后往前遍历，找到第一对相邻元素nums[i]和nums[i+1]，满足nums[i] < nums[i+1]。
        此时，“较小数”即为nums[i]。

        2.如果i >= 0，说明我们找到了相邻的元素对，此时，[i+1,n)区间的元素必然是降序排列的。
        我们在[i+1,n)区间从后向前查找第一个元素，满足nums[i] < nums[j]，“较大数”即为nums[j]。

        3.交换nums[i]和nums[j]，此时，[i+1,n)区间的元素依然是降序排列的。

        4.此时，我们可以使用双指针反转区间[i+1,n)，使其变为升序，而无需对该区间进行排序。

        5.如果i < 0，说明整个数组已经是降序排列的，此时，我们只需要执行步骤4就可以了。
         */
        val n = nums.size
        // 步骤1
        var i = n - 2
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--
        }

        if (i >= 0) {
            // 步骤2
            var j = n - 1
            while (j > i && nums[j] <= nums[i]) {
                j--
            }

            //步骤3
            swap(nums, i, j)
        }

        //步骤4
        var k = i + 1
        var r = n - 1
        while (k < r) {
            swap(nums, k, r)
            k++
            r--
        }
    }

    /**
     * 交换数组中两个元素
     */
    private fun swap(nums: IntArray, i: Int, j: Int) {
        var a = nums[i]
        var b = nums[j]
        a = a xor b
        b = a xor b
        a = a xor b
        nums[i] = a
        nums[j] = b
    }
}