package com.oneday.demo.test_number;

/**
 * Desc: 测试byte常量池逻辑
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/7/31 0031 10:34
 */
class TestByteCache {
    /*参照jdk版本：1.8.0_51*/

    //---------Byte包装类型常量池源码实现逻辑-----------------s
    /*private static class ByteCache {
        private ByteCache() {
        }

        static final Byte cache[] = new Byte[-(-128) + 127 + 1];

        static {
            for (int i = 0; i < cache.length; i++)
                cache[i] = new Byte((byte) (i - 128));
        }
    }*/
    //---------Byte包装类型常量池源码实现逻辑-----------------e

    //---------Short包装类型常量池源码实现逻辑-----------------s
    /*private static class ShortCache {
        private ShortCache() {
        }

        static final Short cache[] = new Short[-(-128) + 127 + 1];

        static {
            for (int i = 0; i < cache.length; i++)
                cache[i] = new Short((short) (i - 128));
        }
    }*/
    //---------Short包装类型常量池源码实现逻辑-----------------e

    //---------Integer包装类型常量池源码实现逻辑-----------------s
    /*private static class IntegerCache {
        static final int low = -128;
        static final int high;
        static final Integer cache[];

        static {
            // high value may be configured by property
            int h = 127;
            String integerCacheHighPropValue =
                    sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
            if (integerCacheHighPropValue != null) {
                try {
                    int i = parseInt(integerCacheHighPropValue);
                    i = Math.max(i, 127);
                    // Maximum array size is Integer.MAX_VALUE
                    h = Math.min(i, Integer.MAX_VALUE - (-low) - 1);
                } catch (NumberFormatException nfe) {
                    // If the property cannot be parsed into an int, ignore it.
                }
            }
            high = h;

            cache = new Integer[(high - low) + 1];
            int j = low;
            for (int k = 0; k < cache.length; k++)
                cache[k] = new Integer(j++);

            // range [-128, 127] must be interned (JLS7 5.1.7)
            assert IntegerCache.high >= 127;
        }

        private IntegerCache() {
        }
    }*/
    //---------Integer包装类型常量池源码实现逻辑-----------------e

    //---------Long包装类型常量池源码实现逻辑-----------------s
    /*private static class LongCache {
        private LongCache(){}

        static final Long cache[] = new Long[-(-128) + 127 + 1];

        static {
            for(int i = 0; i < cache.length; i++)
                cache[i] = new Long(i - 128);
        }
    }*/
    //---------Long包装类型常量池源码实现逻辑-----------------e

    public static void main(String[] args) {
        /*经查看源码发现：Byte、Short、Integer、Long整数类型包装类内部各有一套byte常量池的实现。*/

        Byte b1 = new Byte((byte) 100);
        Byte b2 = new Byte((byte) 100);
        System.out.println(b1 == b2); //false

        Byte b3 = 127;
        Byte b4 = 127;
        System.out.println(b3 == b4); //true

        Byte b5 = Byte.valueOf((byte) -128);
        Byte b6 = Byte.valueOf((byte) -128);
        System.out.println(b5 == b6); //true

        /*
            本质上直接赋值给包装类，就相当与调用对应包装类的valueOf()方法。
            Integer k1 = Integer.valueOf(127);
            Integer k2 = Integer.valueOf(127);
         */
        Integer k1 = 127;
        Integer k2 = 127;
        System.out.println(k1 == k2); //true；在byte常量池范围内，本质上是同一个对象

        Integer k3 = 128;
        Integer k4 = 128;
        System.out.println(k3 == k4); //false；超出byte常量池范围，本质上是两个对象
    }
}
