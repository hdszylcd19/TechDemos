package com.oneday.demo.algorithm.array

import com.oneday.demo.algorithm.BaseAlgorithmTest
import com.oneday.demo.utils.NumberUtils

/**
 * Desc:将有序数组转换为“二叉搜索树”
 * 二叉搜索树（Binary Search Tree，简称 BST）是一种很常用的的二叉树。它的定义是：一个二叉树中，
 * 任意节点的值要大于等于左子树所有节点的值，且要小于等于右边子树的所有节点的值。
 *
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵平衡二叉搜索树。
 *
 * 示例 1：
 *
 *         0
 *        / \
 *      -3   9
 *      /   /
 *    -10  5
 * 输入：nums = [-10,-3,0,5,9]
 * 输出：[0,-3,9,-10,null,5]
 * 解释：[0,-10,5,null,-3,null,9] 也将被视为正确答案：
 *         0
 *        / \
 *      -10  5
 *        \   \
 *        -3   9
 *
 * 示例2：
 *         3       1
 *        /         \
 *       1           3
 * 输入：nums = [1,3]
 * 输出：[3,1]
 * 解释：[1,null,3] 和 [3,1] 都是高度平衡二叉搜索树。
 *
 * 提示：
 *
 * 1 <= nums.length <= 10⁴
 * -10⁴ <= nums[i] <= 10⁴
 * nums 按 严格递增 顺序排列
 *
 * https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/description/?envType=problem-list-v2&envId=array&difficulty=EASY
 * @author OneD
 * @version 1.0
 * @since 2024/11/7 13:58
 */
object SortedArrayToBST : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {
        val nums = NumberUtils.generateRandomSortedIntArray(
            min = -100000,
            max = 10000,
            length = (1..10000).random()
        )
        println("nums = ${nums.contentToString()}")
        println("sortedArrayToBST = ${sortedArrayToBST(nums)}")
    }

    fun sortedArrayToBST(nums: IntArray): TreeNode? {
        return sortedArrayToBST(nums, 0, nums.size - 1)
    }

    fun sortedArrayToBST(nums: IntArray, left: Int, right: Int): TreeNode? {
        if (left > right) {
            return null
        }

        // 总是选择中间位置左边的数字作为根节点
        val mid = (left + right) / 2
        val root = TreeNode(nums[mid])
        root.left = sortedArrayToBST(nums, left, mid - 1)
        root.right = sortedArrayToBST(nums, mid + 1, right)
        return root
    }
}

class TreeNode(var value: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}