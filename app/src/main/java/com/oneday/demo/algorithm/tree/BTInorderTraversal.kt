package com.oneday.demo.algorithm.tree

import com.oneday.demo.algorithm.BaseAlgorithmTest
import java.util.LinkedList

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
        val left = TreeNode(2)
        left.left = TreeNode(4)
        left.right = TreeNode(5)
        val root = TreeNode(1)
        root.left = left
        root.right = TreeNode(3)
        println("中序遍历[迭代]：${inorderTraversal(root)}")
        println("中序遍历[递归]：${inorderTraversal2(root)}")
        println("中序遍历[Morris]：${inorderTraversal3(root)}")
    }

    /**
     * 二叉树中序遍历 Morris 遍历算法；非常烧脑，放在这里，以备后续理解。
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。Morris 遍历中每个节点会被访问两次，因此总时间复杂度为 O(2n)=O(n)。
     * 空间复杂度：O(1)，只需要常数的空间存放指针。
     */
    fun inorderTraversal3(root: TreeNode?): List<Int> {
        val result = ArrayList<Int>()
        var current: TreeNode? = root
        var predecessor: TreeNode?

        while (current != null) {
            if (current.left != null) {
                // predecessor 节点就是当前 root 节点向左走一步，然后一直向右走至无法走为止
                predecessor = current.left
                while (predecessor?.right != null && predecessor.right != current) {
                    predecessor = predecessor.right
                }

                // 让 predecessor 的右指针指向 root，继续遍历左子树
                if (predecessor?.right == null) {
                    predecessor?.right = current
                    current = current.left
                } else {
                    // 说明左子树已经访问完了，我们需要断开链接
                    result.add(current.value)
                    predecessor.right = null
                    current = current.right
                }
            } else {
                // 如果没有左孩子，则直接访问右孩子
                result.add(current.value)
                current = current.right
            }
        }

        return result
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
        while (current != null || stack.isNotEmpty()) {
            /*
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

    /**
     * 二叉树中序遍历递归写法
     * 时间复杂度：O(n)，其中 n 为二叉树节点的个数。二叉树的遍历中每个节点会被访问一次且只会被访问一次。
     * 空间复杂度：O(n)，空间复杂度取决于递归的栈深度，而栈深度在二叉树为一条链的情况下会达到 O(n) 的级别。
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