package com.oneday.demo.unsafe;

import java.lang.reflect.Field;

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/14 0014 16:45
 */
public class TestUnsafe {
    int i = 0;
    private static TestUnsafe t = new TestUnsafe();

    public static void main(String[] args) throws Exception {
        //Unsafe unsafe = Unsafe.getUnsafe();

//        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
//        unsafeField.setAccessible(true);
//        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        Field f = TestUnsafe.class.getDeclaredField("i");
        long offset = UnsafeProxy.objectFieldOffset(f);
        System.out.println("offset = [" + offset + "]");

        boolean success = UnsafeProxy.compareAndSwapInt(t, offset, 0, 1);
        System.out.println(success);
        System.out.println(t.i);
    }
}
