package com.oneday.demo.algorithm.sort;

import com.oneday.demo.algorithm.BaseAlgorithmTest;
import com.oneday.demo.utils.NumberUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Desc:“烧饼排序”
 * 烧饼排序是个很有意思的实际问题：假设盘子上有 n 块面积大小不一的烧饼，你如何用一把锅铲进行若干次翻转，让这些烧饼的大小有序（小的在上，大的在下）？
 * <p>
 * 给定数组 A，我们可以对其进行煎饼翻转：我们选择一些正整数 k <= A.length，然后反转 A 的前 k 个元素的顺序。我们要执行零次或多次煎饼翻转（按顺序一次接一次地进行）以完成对数组 A 的排序。
 * <p>
 * 返回能使 A 排序的煎饼翻转操作所对应的 k 值序列。任何将数组排序且翻转次数在 10 * A.length 范围内的有效答案都将被判断为正确。
 * <p>
 * 示例 1：
 * <p>
 * 输入：[3,2,4,1]
 * 输出：[4,2,4,3]
 * 解释：
 * 我们执行 4 次煎饼翻转，k 值分别为 4，2，4，和 3。
 * 初始状态 A = [3, 2, 4, 1]
 * 第一次翻转后 (k=4): A = [1, 4, 2, 3]
 * 第二次翻转后 (k=2): A = [4, 1, 2, 3]
 * 第三次翻转后 (k=4): A = [3, 2, 1, 4]
 * 第四次翻转后 (k=3): A = [1, 2, 3, 4]，此时已完成排序。
 * 示例 2：
 * <p>
 * 输入：[1,2,3]
 * 输出：[]
 * 解释：
 * 输入已经排序，因此不需要翻转任何内容。
 * 请注意，其他可能的答案，如[3，3]，也将被接受。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/pancake-sorting
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/9/28 0028 15:01
 */
class PancakeSortTest extends BaseAlgorithmTest {
    public static void main(String[] args) {
        int[] arr = NumberUtils.generateRandomIntArray(0, 20, 10);
        System.out.println("烧饼排序前 = " + Arrays.toString(arr));

        pancakeSort(arr);
        System.out.println("烧饼排序后 = " + Arrays.toString(arr));
    }

    /*需要注意的是，数组索引从 0 开始，而我们要返回的结果是从 1 开始算的*/
    public static List<Integer> pancakeSort(int[] arr) {
        LinkedList<Integer> list = new LinkedList<>();
        if (arr == null || arr.length < 2) {
            return list;
        }

        sort(list, arr, arr.length);
        return list;
    }

    private static void sort(List<Integer> list, int[] cake, int n) {
        if (n == 1) {
            return;
        }

        //寻找最大饼索引
        int maxCake = Integer.MIN_VALUE;
        int maxCakeIndex = 0;
        for (int i = 0; i < n; i++) {
            if (cake[i] > maxCake) {
                maxCake = cake[i];
                maxCakeIndex = i;
            }
        }

        // 第一次翻转，将最大饼翻转到最上面
        reverse(cake, 0, maxCakeIndex);
        list.add(maxCakeIndex + 1);
        // 第二次翻转，将最大饼翻转到最下面
        reverse(cake, 0, n - 1);
        list.add(n);

        // 递归调用，排序其它饼
        sort(list, cake, n - 1);
    }

    private static void reverse(int[] cake, int start, int end) {
        if (start < 0 || end >= cake.length) {
            return;
        }

        while (start < end) {
            int tmp = cake[start];
            cake[start] = cake[end];
            cake[end] = tmp;
            start++;
            end--;
        }
    }
}
