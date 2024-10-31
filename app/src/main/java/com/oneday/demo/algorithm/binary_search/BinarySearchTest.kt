package com.oneday.demo.algorithm.binary_search;

import com.oneday.demo.algorithm.BaseAlgorithmTest;

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
public class BinarySearchTest extends BaseAlgorithmTest {
    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 2, 4, 5, 6, 6, 7, 8, 9};
        int target = 2;
        System.out.println("binarySearch() = " + binarySearch(arr, target));
        System.out.println("leftBoundBinarySearch() = " + leftBoundBinarySearch(arr, -1));
        System.out.println("rightBoundBinarySearch() = " + rightBoundBinarySearch(arr, 2));
    }

    /*基础二分查找*/
    public static int binarySearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int left = 0;
        int right = arr.length - 1;
        while (left <= right) { //搜索区间[left, right]
            int mid = left + (right - left) / 2; //该方式可以有效防止left和right太大直接相加导致int溢出。
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1; //搜索区间[mid+1,right]
            } else if (arr[mid] > target) {
                right = mid - 1; //搜索区间[left, mid-1]
            }
        }

        return -1;
    }

    /*寻找左侧边界的二分查找*/
    public static int leftBoundBinarySearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int left = 0;
        int right = arr.length - 1;
        while (left <= right) { //搜索区间[left, right]
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                right = mid - 1; //找到目标元素时，缩小右侧边界
            } else if (arr[mid] < target) {
                left = mid + 1; //搜索区间[mid+1,right]
            } else if (arr[mid] > target) {
                right = mid - 1; //搜索区间[left, mid-1]
            }
        }

        // 检查left越界情况
        if (left >= arr.length || arr[left] != target) {
            return -1;
        }

        return left;
    }

    /*寻找右侧边界的二分查找*/
    public static int rightBoundBinarySearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int left = 0;
        int right = arr.length - 1;
        while (left <= right) { //搜索区间[left, right]
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                left = mid + 1; //找到目标元素时，缩小左侧边界
            } else if (arr[mid] < target) {
                left = mid + 1; //搜索区间[mid+1,right]
            } else if (arr[mid] > target) {
                right = mid - 1; //搜索区间[left, mid-1]
            }
        }

        // 检查right越界情况
        if (right < 0 || arr[right] != target) {
            return -1;
        }

        return right;
    }
}
