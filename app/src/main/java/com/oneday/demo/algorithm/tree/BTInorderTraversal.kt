package com.oneday.demo.algorithm.tree

import com.oneday.demo.algorithm.BaseAlgorithmTest

/**
 * Desc: 二叉树的中序遍历
 *
 * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
 *
 * 示例 1：
 *         1
 *         \
 *           2
 *          /
 *         3
 * 输入：root = [1,null,2,3]
 * 输出：[1,3,2]
 *
 * 示例 2：
 * 输入：root = []
 * 输出：[]
 *
 * 示例 3：
 * 输入：root = [1]
 * 输出：[1]
 *
 * 提示：
 *
 * 树中节点数目在范围 [0, 100] 内
 * -100 <= Node.val <= 100
 *
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 *
 * https://leetcode.cn/problems/binary-tree-inorder-traversal/description/
 * @author OneD
 * @version 1.0
 * @since 2024/11/8 16:33
 */
object BTInorderTraversal : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {

    }

    /**
     * 二叉树中序遍历迭代写法
     */
    fun inorderTraversal(root: TreeNode?): List<Int> {
        val result = ArrayList<Int>()
        if (root == null) {
            return result
        }


        return result
    }

    /**
     * 二叉树中序遍历递归写法
     */
    fun inorderTraversal2(root: TreeNode?): List<Int> {
        val result = ArrayList<Int>()
        if (root == null) {
            return result
        }

        result.addAll(inorderTraversal(root.left))
        result.add(root.value)
        result.addAll(inorderTraversal(root.right))
        return result
    }
}