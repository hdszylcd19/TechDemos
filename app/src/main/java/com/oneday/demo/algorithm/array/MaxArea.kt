package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest
import com.oneday.demo.utils.NumberUtils
import kotlin.math.max
import kotlin.math.min

/**
 * Desc: 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 *
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 返回容器可以储存的最大水量。
 *
 * 说明：你不能倾斜容器。
 *
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 * 示例 2：
 *
 * 输入：height = [1,1]
 * 输出：1
 *
 *
 * 提示：
 *
 * n == height.length
 * 2 <= n <= 105
 * 0 <= height[i] <= 10⁴
 *
 * https://leetcode.cn/problems/container-with-most-water/description/?envType=problem-list-v2&envId=array
 *
 * @author OneD
 * @version 1.0
 * @since 2024/11/4 15:00
 */
object MaxArea : BaseAlgorithmTest() {

    @JvmStatic
    fun main(args: Array<String>) {
        val array = NumberUtils.generateRandomIntArray(max = 10000, length = 10)
        println(array.contentToString())
        println("盛最多水的容量为：${maxArea(array)}")
    }

    /**
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
     *
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     *
     * 返回容器可以储存的最大水量。
     *
     * 说明：你不能倾斜容器
     *
     * 提示：
     *
     * n == height.length
     * 2 <= n <= 105
     * 0 <= height[i] <= 10⁴
     */
    fun maxArea(height: IntArray): Int {
        // 采用双指针解法
        var left = 0 //左指针
        var right = height.size - 1 //右指针
        var maxArea = 0 //最大盛水面积
        while (left < right) {
            // 计算当前首尾组成的矩形，可盛水的最大面积
            val x = height[left]
            val y = height[right]
            val area = min(x, y) * (right - left)
            maxArea = max(maxArea, area)
            // 移动组成容器的最短板
            if (x < y) {
                left++
            } else {
                right--
            }
        }

        /*
            双指针代表的是，可以作为容器边界的所有位置的范围。在一开始，双指针指向数组的左右边界，表示数组中的所有位置都可以
            作为容器的边界，因为我们还没有进行过任何尝试。在这之后，我们每次将对应的数字较小的那个指针往另一个指针的方向移动一个位置，
            就表示我们认为这个指针不可能再作为容器的边界了。

            为什么对应的数字较小的那个指针不可能再作为容器的边界了？

            假设左右指针指向的数分别为x和y，并且 x <= y，同时两个指针直接的距离为t，那么它们组成的容器容量为：
                min(x,y) * t = x * t
            我们可以断定，如果我们保持左指针的位置不变，那么无论右指针在哪里，这个新容器的容量都不会超过 x * t了。
            因为，此时右指针处在数组边界，只能向左移动。

            因此，我们每次移动较小的那个指针，此时的左右指针，就指向了一个新的、规模减少了数组的左右边界，我们可以继续像之前考虑第一步那样考虑这个问题。
            直到两个指针重合，此时，我们得到的就是最大的盛水容量。
         */

        return maxArea
    }
}