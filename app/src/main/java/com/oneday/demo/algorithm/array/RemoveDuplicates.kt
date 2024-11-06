package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest
import com.oneday.demo.utils.NumberUtils

/**
 * Desc:删除有序数组中的重复项
 * 给你一个 非严格递增排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，
 * 返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
 *
 * 考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：
 *
 * 更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。nums 的其余元素与 nums 的大小不重要。
 * 返回 k 。
 * 判题标准:
 *
 * 系统会用下面的代码来测试你的题解:
 *
 * int[] nums = [...]; // 输入数组
 * int[] expectedNums = [...]; // 长度正确的期望答案
 *
 * int k = removeDuplicates(nums); // 调用
 *
 * assert k == expectedNums.length;
 * for (int i = 0; i < k; i++) {
 *     assert nums[i] == expectedNums[i];
 * }
 * 如果所有断言都通过，那么您的题解将被 通过。
 *
 * 示例 1：
 * 输入：nums = [1,1,2]
 * 输出：2, nums = [1,2,_]
 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 *
 * 示例 2：
 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
 * 输出：5, nums = [0,1,2,3,4]
 * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
 *
 *
 * 提示：
 *
 * 1 <= nums.length <= 3 * 10⁴
 * -10⁴ <= nums[i] <= 10⁴
 * nums 已按 非严格递增 排列
 *
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-array/description/?envType=problem-list-v2&envId=array
 *
 * @author OneD
 * @version 1.0
 * @since 2024/11/5 15:27
 */
object RemoveDuplicates : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {
        val nums = NumberUtils.generateRandomSortedIntArray(
            min = -10000,
            max = 10000,
            length = (1..10).random()
        )
        println("nums: ${nums.contentToString()}")
        val result = removeDuplicates(nums)
        println("result: $result，nums: ${nums.contentToString()}")

        val nums2 = intArrayOf(1, 1, 2)
        println("nums2: ${nums2.contentToString()}")
        val result2 = removeDuplicates(nums2)
        println("result2: $result2，nums2: ${nums2.contentToString()}")

        val nums3 = intArrayOf(0, 0, 1, 1, 1, 2, 2, 3, 3, 4)
        println("nums3: ${nums3.contentToString()}")
        val result3 = removeDuplicates(nums3)
        println("result3: $result3，nums3: ${nums3.contentToString()}")
    }

    fun removeDuplicates(nums: IntArray): Int {
        var k = 0 //始终指向 nums 中最后一个不重复元素的索引位置
        for (i in 1 until nums.size) {
            if (nums[k] != nums[i]) {
                // k自增后，把下一个不重复元素赋值给 nums[k]
                nums[++k] = nums[i]
            }
        }

        return k + 1
    }
}