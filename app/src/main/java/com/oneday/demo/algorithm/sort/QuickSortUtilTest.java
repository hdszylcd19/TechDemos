package com.oneday.demo.algorithm.sort;

import com.oneday.demo.algorithm.BaseAlgorithmTest;
import com.oneday.demo.utils.NumberUtils;

import java.util.Arrays;

/**
 * Desc: 快速排序可以形象地认为就是个二叉树的前序遍历
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/9/23 0023 10:18
 */
public class QuickSortUtilTest extends BaseAlgorithmTest {
    private static int count = 1;

    public static void main(String[] args) {
        int[] arr = NumberUtils.generateRandomIntArray(0, 200);
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        int[] arrCopy1 = Arrays.copyOf(arr, arr.length);
        System.out.println("排序前： " + Arrays.toString(arr));

        count = 1;
        before();
        quickSort(arr, 0, arr.length - 1);
        printTime("自定义快速排序耗时");
//        System.out.println("自定义快速排序后： " + Arrays.toString(arr));

        before();
        Arrays.sort(arrCopy);
        printTime("系统快速排序耗时");
//        System.out.println("系统快速排序后： " + Arrays.toString(arrCopy));

        System.out.println("自定义快速排序 VS 系统快速排序 结果是否相同：" + Arrays.equals(arr, arrCopy));

        before();
        quickSort1(arrCopy1, 0, arrCopy1.length - 1);
        printTime("三数中值分割法快速排序耗时");
        System.out.println("三数中值分割法快速排序 VS 系统快速排序 结果是否相同：" + Arrays.equals(arrCopy1, arrCopy));
    }

    /**
     * 快速排序的逻辑是，若要对nums[lo..hi]进行排序，我们先找一个分界点p，通过交换元素使得nums[lo..p-1]都小于等于nums[p]，
     * 且nums[p+1..hi]都大于nums[p]，然后递归地去nums[lo..p-1]和nums[p+1..hi]中寻找新的分界点，最后整个数组就被排序了。
     */
    public static void quickSort(int[] arr, int start, int end) {
        if (start >= end) {
            // 当start == end时，表示当前待排序的只有一个元素，直接返回
            return;
        }

        int left = start;
        int right = end;

        // 1.先找一个分界点
        int p = start;
        int tmp = arr[p];

        /* 2.通过交换元素使得arr[start..p-1]都小于等于arr[p]，且arr[p+1..end]都大于arr[p] */
        while (end > start) {
            // 从后往前遍历，直到找到小于分界点的索引为止
            while (tmp <= arr[end] && end > start) {
                end--;
            }

            // 交换元素使得arr[start..p-1]都小于等于arr[p]
            if (start < end) {
                arr[start] = arr[end];
                start++;
            }

            // 从前往后遍历，直到找到大于分界点的索引为止
            while (tmp >= arr[start] && start < end) {
                start++;
            }

            // 交换元素使得arr[p+1..end]都大于arr[p]
            if (start < end) {
                arr[end] = arr[start];
                end--;
            }
        }

        // 赋值分界点值
        arr[start] = tmp;

//        System.out.println("第" + count++ + "次排序后： " + Arrays.toString(arr) + ", start = " + start + ", end = " + end);

        // 3.递归地去arr[lo..p-1]和arr[p+1..hi]中寻找新的分界点，最后整个数组就被排序了
        quickSort(arr, left, start - 1);
        quickSort(arr, start + 1, right);
    }

    private static int partition(int[] arr, int left, int right) {
        // 采用三数中值分割法
        int mid = left + (right - left) / 2;
        // 保证左端较小
        if (arr[left] > arr[right])
            swap(arr, left, right);
        // 保证中间较小
        if (arr[mid] > arr[right])
            swap(arr, mid, right);
        // 保证中间最小，左右最大
        if (arr[mid] > arr[left])
            swap(arr, left, mid);
        int pivot = arr[left];
        while (right > left) {
            // 先判断基准数和后面的数依次比较
            while (pivot <= arr[right] && left < right) {
                --right;
            }
            // 当基准数大于了 arr[right]，则填坑
            if (left < right) {
                arr[left] = arr[right];
                ++left;
            }
            // 现在是 arr[right] 需要填坑了
            while (pivot >= arr[left] && left < right) {
                ++left;
            }
            if (left < right) {
                arr[right] = arr[left];
                --right;
            }
        }
        arr[left] = pivot;
        return left;
    }

    private static void quickSort1(int[] arr, int left, int right) {
        if (arr == null || left >= right || arr.length <= 1)
            return;
        int mid = partition(arr, left, right);
        quickSort1(arr, left, mid - 1);
        quickSort1(arr, mid + 1, right);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
