package com.oneday.demo.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * Desc: JOL(Java Object Layout)Java对象布局
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/1/15 0015 13:21
 */
public class HelloJOL {
    private static class Lock {
    }

    private static final Lock sLock = new Lock();

    /*
        com.oneday.demo.jol.HelloJOL$Lock object internals:
         OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
              0     4        (object header)                           10 f5 35 02 (00010000 11110101 00110101 00000010) (37090576)
              4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
              8     4        (object header)                           43 e1 e5 27 (01000011 11100001 11100101 00100111) (669376835)
             12     4        (loss due to the next object alignment)
        Instance size: 16 bytes
        Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

        拿37090576举例,这个值是怎么计算出来的呢？其实是: 02 35 f5 10组合而来,也就是00000010 00110101 11110101 00010000对应的值;
        具体可以通过little endian big endian了解.
        参考资料:https://blog.csdn.net/waitingbb123/article/details/80504093
     */

    public static void main(String[] args) {
        // 通过JOL打印对象布局信息
        System.out.println(ClassLayout.parseInstance(sLock).toPrintable());

        synchronized (sLock) {
            // 加锁之后,打印对象布局信息,观察对象头部信息变化
            System.out.println(ClassLayout.parseInstance(sLock).toPrintable());
        }

        // 锁释放之后,打印对象布局信息,观察对象头部信息变化
        System.out.println(ClassLayout.parseInstance(sLock).toPrintable());
    }
}
