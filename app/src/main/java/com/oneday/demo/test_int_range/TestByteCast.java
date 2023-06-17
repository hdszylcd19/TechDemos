package com.oneday.demo.test_int_range;

/**
 * Desc: 测试byte强转
 *
 * @author OneD
 * @version 1.0
 * @since 2023/6/16 16:47
 */
class TestByteCast {
    public static void main(String[] args) {
        final int xFF = 0xFF;
        final byte bFF = (byte) xFF;

        /*
         * 0xFF int类型
         * 0000 0000 0000 0000 0000 0000 1111 1111 原码
         * 0000 0000 0000 0000 0000 0000 1111 1111 反码
         * 0000 0000 0000 0000 0000 0000 1111 1111 补码
         *
         * 0xFF强转成了byte类型后，符号位为1变成了负数
         * 1111 1111 补码
         * 1111 1110 反码
         * 1000 0001 原码
         *
         * 所以 bFF 最终的结果为：-1
         */
        System.out.println(bFF); // -1
        System.out.println(bFF & xFF); // 255


    }
}
