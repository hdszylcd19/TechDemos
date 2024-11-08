package com.oneday.demo.algorithm.tree

import com.oneday.demo.algorithm.BaseAlgorithmTest

/**
 * 参考链接：https://github.com/labuladong/fucking-algorithm/blob/master/算法思维系列/二分查找详解.md
 * Desc: 二分查找必须是有序的集合或数组
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/9/10 0010 15:25
 */
object BinarySearchTest : BaseAlgorithmTest() {
    @JvmStatic
    fun main(args: Array<String>) {
        val arr = intArrayOf(1, 2, 2, 2, 4, 5, 6, 6, 7, 8, 9)
        val target = 2
        println("binarySearch() = " + binarySearch(arr, target))
        println("leftBoundBinarySearch() = " + leftBoundBinarySearch(arr, -1))
        println("rightBoundBinarySearch() = " + rightBoundBinarySearch(arr, 2))
    }

    /*基础二分查找*/
    fun binarySearch(arr: IntArray, target: Int): Int {
        if (arr.isEmpty()) {
            return -1
        }

        var left = 0
        var right = arr.size - 1
        while (left <= right) { //搜索区间[left, right]
            val mid = left + (right - left) / 2 //该方式可以有效防止left和right太大直接相加导致int溢出。
            if (arr[mid] == target) {
                return mid
            } else if (arr[mid] < target) {
                left = mid + 1 //搜索区间[mid+1,right]
            } else if (arr[mid] > target) {
                right = mid - 1 //搜索区间[left, mid-1]
            }
        }

        return -1
    }

    /*寻找左侧边界的二分查找*/
    fun leftBoundBinarySearch(arr: IntArray, target: Int): Int {
        if (arr.isEmpty()) {
            return -1
        }

        var left = 0
        var right = arr.size - 1
        while (left <= right) { //搜索区间[left, right]
            val mid = left + (right - left) / 2
            if (arr[mid] == target) {
                right = mid - 1 //找到目标元素时，缩小右侧边界
            } else if (arr[mid] < target) {
                left = mid + 1 //搜索区间[mid+1,right]
            } else if (arr[mid] > target) {
                right = mid - 1 //搜索区间[left, mid-1]
            }
        }

        // 检查left越界情况
        if (left >= arr.size || arr[left] != target) {
            return -1
        }

        return left
    }

    /*寻找右侧边界的二分查找*/
    fun rightBoundBinarySearch(arr: IntArray, target: Int): Int {
        if (arr.isEmpty()) {
            return -1
        }

        var left = 0
        var right = arr.size - 1
        while (left <= right) { //搜索区间[left, right]
            val mid = left + (right - left) / 2
            if (arr[mid] == target) {
                left = mid + 1 //找到目标元素时，缩小左侧边界
            } else if (arr[mid] < target) {
                left = mid + 1 //搜索区间[mid+1,right]
            } else if (arr[mid] > target) {
                right = mid - 1 //搜索区间[left, mid-1]
            }
        }

        // 检查right越界情况
        if (right < 0 || arr[right] != target) {
            return -1
        }

        return right
    }
}
