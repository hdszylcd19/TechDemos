package com.oneday.demo.algorithm.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Desc:给定一棵二叉树，返回所有重复的子树。对于同一类的重复子树，你只需要返回其中任意一棵的根结点即可。
 * <p>
 * 两棵树重复是指它们具有相同的结构以及相同的结点值。
 * <p>
 * 示例 1：
 *         1
 *        / \
 *       2   3
 *      /   / \
 *     4   2   4
 *        /
 *       4
 * 下面是两个重复的子树：
 *
 *       2
 *      /
 *     4
 * 和
 *
 *     4
 *
 * 因此，你需要以列表的形式返回上述重复子树的根结点。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-duplicate-subtrees
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/10/14 0014 13:51
 */
class FindDuplicateSubtreesTest {
    public static void main(String[] args) {

    }

    // 记录所有子树以及出现的次数
    private static HashMap<String, Integer> map;
    // 记录重复的子树根节点
    private static LinkedList<TreeNode> result;

    public static List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        result = new LinkedList<>();
        if (root != null) {
            map = new HashMap<>();
            traverse(root);
        }

        return result;
    }

    private static String traverse(TreeNode root) {
        if (root == null) {
            return "#";
        }

        String left = traverse(root.left);
        String right = traverse(root.right);
        String rootDes = left + "," + right + "," + root.value; //当前节点的字符串描述

        Integer count = map.get(rootDes);
        count = count == null ? 0 : count;

        // 多次重复的子树也只会被加入结果集一次
        if (count == 1) {
            result.add(root);
        }

        // 子树出现的次数+1
        map.put(rootDes, ++count);

        return rootDes;
    }
}
