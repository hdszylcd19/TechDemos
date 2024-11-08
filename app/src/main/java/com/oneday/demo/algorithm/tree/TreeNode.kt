package com.oneday.demo.algorithm.tree

/**
 * Desc:二叉树节点
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/9/18 0018 11:27
 */
class TreeNode(@JvmField var value: Int) {
    @JvmField
    var left: TreeNode? = null

    @JvmField
    var right: TreeNode? = null
}
