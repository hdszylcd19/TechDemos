package com.oneday.demo.algorithm.tree

import com.oneday.demo.algorithm.BaseAlgorithmTest
import java.util.LinkedList

/**
 * Desc:二叉树的最小深度
 * 给定一个二叉树，找出其最小深度。
 *
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 *
 * 说明：叶子节点是指没有子节点的节点。
 *
 * 示例 1：
 *         3
 *        / \
 *       9  20
 *         /  \
 *        15   7
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：2
 *
 * 示例 2：
 *         2
 *          \
 *           3
 *            \
 *             4
 *              \
 *               5
 *               \
 *                6
 * 输入：root = [2,null,3,null,4,null,5,null,6]
 * 输出：5
 *
 * 提示：
 * 树中节点数的范围在 [0, 105] 内
 * -1000 <= Node.val <= 1000
 *
 * https://leetcode.cn/problems/minimum-depth-of-binary-tree/description/?envType=problem-list-v2&envId=binary-tree
 * @author OneD
 * @version 1.0
 * @since 2024/11/14 14:16
 */
object MinDepth : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {

    }

    //记录最小深度（根节点到最近叶子节点的距离）
    private var minDepth = Int.MAX_VALUE
    fun minDepth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        // 从根节点深度为1开始遍历
        dfs(root, 1)
        return minDepth
    }

    /**
     * 深度优先搜索算法：DFS(Depth-First-Search)。
     * 每当遍历到一条树枝的叶子节点，就会更新最小深度，当遍历完整棵树后，就能算出整棵树的最小深度了。
     * 那么能不能在不遍历完整棵树的情况下，提前结束算法？不可以！因为你必须确切的知道每条树枝的深度（根节点到叶子节点的距离），才能找到最小的那个。
     */
    private fun dfs(root: TreeNode?, depth: Int) {
        if (root == null) return

        if (root.left == null && root.right == null) {
            // 遇到叶子节点，更新最小深度
            minDepth = minDepth.coerceAtMost(depth)
            return
        }

        // 深度+1，继续遍历左右子树
        dfs(root.left, depth + 1)
        dfs(root.right, depth + 1)
    }

    /**
     * 广度优先搜索算法：BFS(Breadth-First-Search)。
     * 由于BFS算法逐层遍历的逻辑，第一次遇到目标节点时，所经过的路径就是最短路径，算法可能并不需要遍历完所有节点就能提前结束。
     * 从时间复杂度的角度来看，两种算法在最坏情况下都会遍历所有节点，时间复杂度都是O(n)。但在一般情况下，显然，BFS算法的时间复杂度更优。
     * 所以在寻找最短路径的问题中，BFS算法是首选。
     */
    fun minDepth2(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        val list = LinkedList<TreeNode>()
        list.offer(root)

        // root本身就是一层，depth初始化为1
        var depth = 1
        while (list.isNotEmpty()) {
            val size = list.size
            //遍历当前层的节点
            for (i in 0 until size) {
                val node = list.poll() ?: continue
                if (node.left == null && node.right == null) {
                    // 首次遇到的叶子节点，就是最小深度，直接返回
                    return depth
                }

                // 将下一层节点加入队列
                node.left?.let { list.offer(it) }
                node.right?.let { list.offer(it) }
            }

            // 每层遍历结束后，深度+1
            depth++
        }
        return depth
    }
}