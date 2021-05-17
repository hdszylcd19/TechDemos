package com.oneday.demo.algorithm.advantage;

import com.oneday.demo.algorithm.BaseAlgorithmTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Desc:“优势洗牌”、“田忌赛马”算法题
 *
 * @author JiLin
 * @version 1.0
 * @since 2021/5/17 8:55
 */
/*
    优势洗牌：https://leetcode-cn.com/problems/advantage-shuffle/

    给定两个大小相等的数组 A 和 B，A 相对于 B 的优势可以用满足 A[i] > B[i] 的索引 i 的数目来描述。

    返回 A 的任意排列，使其相对于 B 的优势最大化。



    示例 1：

    输入：A = [2,7,11,15], B = [1,10,4,11]
    输出：[2,11,7,15]
    示例 2：

    输入：A = [12,24,8,32], B = [13,25,32,11]
    输出：[24,32,8,12]


    提示：

    1 <= A.length = B.length <= 10000
    0 <= A[i] <= 10^9
    0 <= B[i] <= 10^9

    该问题有点类似于“田忌赛马”。
    https://mp.weixin.qq.com/s/Rxx3BGxRLe_FZHqNS2ILsg
 */
public class AdvantageCountTest extends BaseAlgorithmTest {
    public static void main(String[] args) {
        int[] nums1 = getRandomArr(10, 1000);
        System.out.println("nums1 = " + Arrays.toString(nums1));
        int[] nums2 = getRandomArr(10, 1000);
        System.out.println("nums2 = " + Arrays.toString(nums2));
        int[] advantageNums = advantageCount2(nums1, nums2);
        System.out.println("advantageNums = " + Arrays.toString(advantageNums));
    }

    public static int[] advantageCount2(int[] nums1, int[] nums2) {
        int[][] src = new int[nums2.length][2];
        for (int i = 0; i < nums2.length; i++) {
            src[i] = new int[]{nums2[i], i};
        }
        // nums2降序排列；不能改变nums2中元素原有位置
        Arrays.sort(src, (o1, o2) -> o2[0] - o1[0]);

        // nums1升序排列
        Arrays.sort(nums1);

        //nums1[left]是nums1中的最小值，nums1[right]是nums1中的最大值
        int left = 0;
        int right = nums1.length - 1;
        int[] result = new int[nums1.length];
        for (int[] nums : src) {
            // maxValue是nums2中的最大值，position是对应索引
            int position = nums[1];
            int maxValue = nums[0];
            if (nums1[right] > maxValue) {
                // （田忌）上等马 VS （齐王）中下等马
                result[position] = nums1[right];
                right--;
            } else {
                // （田忌）下等马 VS （齐王）上等马
                result[position] = nums1[left];
                left++;
            }
        }

        return result;
    }

    public static int[] advantageCount(int[] nums1, int[] nums2) {
        List<Item> items = new ArrayList<>(nums2.length);
        for (int i = 0; i < nums2.length; i++) {
            items.add(new Item(i, nums2[i]));
        }
        // nums2降序排列；不能改变nums2中元素原有位置
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o2.value - o1.value;
            }
        });

        // nums1升序排列
        Arrays.sort(nums1);

        int left = 0;
        int right = nums1.length - 1;
        int[] result = new int[nums1.length];
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int position = item.position;
            int maxValue = item.value;
            if (nums1[right] > maxValue) {
                // （田忌）上等马VS（齐王）中下等马
                result[position] = nums1[right];
                right--;
            } else {
                // （田忌）下等马VS（齐王）上等马
                result[position] = nums1[left];
                left++;
            }
        }

        return result;
    }


    private static class Item {
        int position;
        int value;


        public Item(int position, int value) {
            this.position = position;
            this.value = value;
        }
    }
}
