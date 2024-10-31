package com.oneday.demo.utils

/**
 * Desc: 数字相关工具类
 *
 * @author OneD
 * @version 1.0
 * @since 2024/10/31 10:26
 */
object NumberUtils {
    private const val DEF_MIN = 0
    private const val DEF_MAX = 100
    private const val DEF_LENGTH = 10
    private const val DEF_IS_ORDER = true


    /**
     * 根据给定范围参数，随机生成一个随机Int类型的整数；
     * @param min 随机数的最小值
     * @param max 随机数的最大值
     */
    @JvmStatic
    fun generateRandomInt(min: Int = DEF_MIN, max: Int = DEF_MAX): Int {
        return (min..max).random()
    }

    /**
     * 根据给定参数，随机生成一个随机长度Int类型的数组；数组的长度在[1,max]之间。
     * @param min 随机数组元素的最小值
     * @param max 随机数组元素的最大值
     */
    @JvmStatic
    fun generateRandomIntArray(
        min: Int = DEF_MIN,
        max: Int = DEF_MAX
    ): IntArray {
        return generateRandomIntArray(min, max, (1..max).random())
    }

    /**
     * 根据给定参数，随机生成一个Int类型的数组
     * @param min 随机数组元素的最小值
     * @param max 随机数组元素的最大值
     * @param length 数组的长度
     */
    @JvmStatic
    fun generateRandomIntArray(
        min: Int = DEF_MIN,
        max: Int = DEF_MAX,
        length: Int = DEF_LENGTH
    ): IntArray {
        val intRange = (min..max)

        val array = IntArray(length)
        for (i in 0 until length) {
            array[i] = intRange.random()
        }

        return array
    }

    /**
     * 根据给定参数，随机生成一个有序的Int类型数组
     * @param min 随机数组元素的最小值
     * @param max 随机数组元素的最大值
     * @param length 数组的长度
     * @param isOrder true:正序 false:倒序
     */
    @JvmStatic
    fun generateRandomSortedIntArray(
        min: Int = DEF_MIN,
        max: Int = DEF_MAX,
        length: Int = DEF_LENGTH,
        isOrder: Boolean = DEF_IS_ORDER
    ): IntArray {
        val array = generateRandomIntArray(min, max, length)
        if (isOrder) array.sort() else array.sortDescending()
        return array
    }
}