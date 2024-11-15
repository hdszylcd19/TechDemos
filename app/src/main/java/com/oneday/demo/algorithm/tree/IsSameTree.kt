package com.oneday.demo.algorithm.tree

import com.oneday.demo.algorithm.BaseAlgorithmTest
import java.util.LinkedList

/**
 * Desc:相同的树
 * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
 *
 * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
 *
 *
 * 示例 1：
 *         1            1
 *        / \          / \
 *       2   3        2   3
 * 输入：p = [1,2,3], q = [1,2,3]
 * 输出：true
 *
 * 示例 2：
 *         1            1
 *        /              \
 *       2                2
 * 输入：p = [1,2], q = [1,null,2]
 * 输出：false
 *
 * 示例 3：
 *         1            1
 *        / \          / \
 *       2   1        1   2
 * 输入：p = [1,2,1], q = [1,1,2]
 * 输出：false
 *
 *
 * 提示：
 *
 * 两棵树上的节点数目都在范围 [0, 100] 内
 * -104 <= Node.val <= 104
 *
 * https://leetcode.cn/problems/same-tree/description/?envType=problem-list-v2&envId=binary-tree
 * @author OneD
 * @version 1.0
 * @since 2024/11/15 19:43
 */
object IsSameTree : BaseAlgorithmTest() {


    @JvmStatic
    fun main(args: Array<String>) {
        val left1 = TreeNode(2)
        left1.left = TreeNode(4)
        left1.right = TreeNode(5)
        val root1 = TreeNode(1)
        root1.left = left1
        root1.right = TreeNode(3)

        val left2 = TreeNode(2)
        left2.left = TreeNode(4)
        left2.right = TreeNode(5)
        val root2 = TreeNode(1)
        root2.left = left2
        root2.right = TreeNode(3).apply {
            right = TreeNode(6)
        }

        println("root1 = ${inorderTraversal(root1)}")
        println("root2 = ${inorderTraversal(root2)}")
        println("isSame = ${isSameTree(root1, root2)}")
    }

    /**
     * 时间复杂度：O(min(m,n))，其中 m 和 n 分别是两个二叉树的节点数。对两个二叉树同时进行深度优先搜索，
     * 只有当两个二叉树中的对应节点都不为空时才会访问到该节点，因此被访问到的节点数不会超过较小的二叉树的节点数。
     *
     * 空间复杂度：O(min(m,n))，其中 m 和 n 分别是两个二叉树的节点数。空间复杂度取决于递归调用的层数，
     * 递归调用的层数不会超过较小的二叉树的最大高度，最坏情况下，二叉树的高度等于节点数。
     *
     */
    fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
        if (p == null && q == null) {
            return true
        }

        // 判断节点的值，以及其左右子树是否相同
        return p?.value == q?.value && isSameTree(p?.left, q?.left) && isSameTree(
            p?.right, q?.right
        )
    }

    /**
     * 二叉树中序遍历迭代写法；理解起来，稍微有些烧脑！
     * 时间复杂度：O(n)，其中 n 为二叉树节点的个数。二叉树的遍历中每个节点会被访问一次且只会被访问一次。
     * 空间复杂度：O(n)，空间复杂度取决于栈深度，而栈深度在二叉树为一条链的情况下会达到 O(n) 的级别。
     */
    fun inorderTraversal(root: TreeNode?): List<Int> {
        // 采用迭代写法，需要借助栈结构来实现
        // 前序遍历；出栈顺序：根-左-右；入栈顺序：右-左-根；
        // 中序遍历；出栈顺序：左-根-右；入栈顺序：右-根-左；
        // 后序遍历；出栈顺序：左-右-根；入栈顺序：根-右-左；
        val result = ArrayList<Int>()
        val stack = LinkedList<TreeNode>()
        var current: TreeNode? = root

        // 当current为空或者栈为空时，结束循环
        while (current != null || stack.isNotEmpty()) {/*
               从单个节点视角来看，每个节点所做的事情都是一样的。
               1.首先先将自己压入栈底，然后不断将左子节点压入栈中，直到左子节点为空。
               2.然后弹出栈顶元素，也就是左子树为空的节点，将该节点的值添加到结果中；
               3.最后将当前节点赋值为该节点的右子节点，然后重复外层循环；
             */

            // 1.先将当前节点的根和其左子树的所有左子节点压入栈中
            while (current != null) {
                stack.add(current)
                current = current.left
            }

            // 代码执行到此处，current为空，说明步骤1的current必定没有左子树；
            // 2.此时弹出的栈顶元素，也就是左子树为空的节点，我们暂且叫做：节点t；
            current = stack.removeLast()

            // 3.将节点t的值添加到结果中；
            result.add(current.value)

            // 4.将当前节点赋值为节点t的右子节点，然后重复外层循环；
            current = current.right
        }

        return result
    }
}