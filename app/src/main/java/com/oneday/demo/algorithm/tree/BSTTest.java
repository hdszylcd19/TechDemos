package com.oneday.demo.algorithm.tree;

/**
 * Desc:二叉搜索树（Binary Search Tree，简称 BST）是一种很常用的的二叉树。它的定义是：一个二叉树中，
 * 任意节点的值要大于等于左子树所有节点的值，且要小于等于右边子树的所有节点的值。
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/9/18 0018 11:26
 */
class BSTTest {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(20);
        for (int i = 0; i < 100; i++) {
            insertIntoBST(root, i);
        }

        printBST(root);
        System.out.println();
        System.out.println("该二叉树有 " + count(root) + " 个节点");
    }

    static TreeNode insertIntoBST(TreeNode root, int val) {
        // 找到空位置插入新节点
        if (root == null) return new TreeNode(val);
        // if (root.val == val)
        //     BST 中一般不会插入已存在元素
        if (root.value < val)
            root.right = insertIntoBST(root.right, val);
        if (root.value > val)
            root.left = insertIntoBST(root.left, val);
        return root;
    }

    static boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    static boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) return true;
        if (min != null && root.value <= min.value) return false;
        if (max != null && root.value >= max.value) return false;
        return isValidBST(root.left, min, root)
                && isValidBST(root.right, root, max);
    }

    static void printBST(TreeNode root) {
        if (root == null) {
            return;
        }

        printBST(root.left);
        System.out.print("| " + root.value + " |");
        printBST(root.right);
    }

    /* 二叉树遍历框架 */
    private static void traverse(TreeNode root) {
        // 前序遍历 root.value写在这儿
        traverse(root.left);
        // 中序遍历 root.value写在这儿
        traverse(root.right);
        // 后序遍历 root.value写在这儿
    }

    // 定义：count(root) 返回以 root 为根的树有多少节点
    private static int count(TreeNode root) {
        // base case
        if (root == null) return 0;
        // 自己加上子树的节点数就是整棵树的节点数
        return 1 + count(root.left) + count(root.right);
    }
}
