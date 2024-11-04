package com.oneday.demo.algorithm;

import com.oneday.demo.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Desc: 给定两个数组，编写一个函数来计算它们的交集。
 * 说明:
 * 输出结果中的每个元素一定是唯一的。
 * 我们可以不考虑输出结果的顺序。
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/14 0014 9:15
 */
public class IntersectAlgorithmTest extends BaseAlgorithmTest {
    public static void main(String[] args) {
        int[] arr1 = NumberUtils.generateRandomIntArray(0, 100, 10);
        int[] arr2 = NumberUtils.generateRandomIntArray(0, 100, 10);
        System.out.println("arr1 = " + Arrays.toString(arr1));
        System.out.println("arr2 = " + Arrays.toString(arr2));

        before();
        int[] intersect = intersect(arr1, arr2);
        printTime();
        Arrays.sort(intersect);
        System.out.println("intersect = " + Arrays.toString(intersect));

        before();
        int[] intersect2 = intersect2(arr1, arr2);
        printTime();
        System.out.println("intersect2 = " + Arrays.toString(intersect2));
    }

    /**
     * 标记法返回两个集合的交集(目前该方法效率最高)
     */
    public static int[] intersect(int[] arr1, int[] arr2) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        // 求出数组arr1中最大值与最小值
        for (int i = 0; i < arr1.length; i++) {
            max = Math.max(max, arr1[i]);
            min = Math.min(min, arr1[i]);
        }

        // 创建标记数组
        boolean[] markArr = new boolean[max - min + 1];
        for (int i = 0; i < arr1.length; i++) {
            //标记arr1中的元素对应索引位置为true(并能达到去重的目的)
            markArr[arr1[i] - min] = true;
        }

        int index = 0;
        int[] tempArray = new int[arr2.length];
        for (int i = 0; i < arr2.length; i++) {
            if (!(arr2[i] < min) && !(arr2[i] > max) && markArr[arr2[i] - min]) {
                // 找出arr2中的元素，标记改为false
                markArr[arr2[i] - min] = false;

                // 找出相同的数据
                tempArray[index++] = arr2[i];
            }
        }
        // 复制有数值位，舍弃默认数值位
        return Arrays.copyOf(tempArray, index);
    }


    /**
     * 排序法找出两个集合的交集
     */
    public static int[] intersect2(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);

        int indexA, indexB, mark;
        indexA = indexB = mark = 0;
        List<Integer> tmp = new ArrayList<>();
        while (indexA < a.length && indexB < b.length) {
            if (a[indexA] > b[indexB]) {
                indexB++;
            } else if (a[indexA] < b[indexB]) {
                indexA++;
            } else {
                if (tmp.size() == 0 || a[indexA] != mark) {
                    mark = a[indexA];
                    tmp.add(mark);
                }
                indexA++;
                indexB++;
            }
        }

        int[] intersect = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            intersect[i] = tmp.get(i);
        }

        return intersect;
    }
}
