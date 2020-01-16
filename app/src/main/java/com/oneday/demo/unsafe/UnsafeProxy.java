package com.oneday.demo.unsafe;

import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * "sun.misc.Unsafe"代理类,方法名和系统Unsafe类保持一致;
 */
public class UnsafeProxy {
    private static ReflectUtils sUtils;
    private static Class sUnsafeClass;
    private static Object sUnsafe;

    static {
        try {
            sUtils = ReflectUtils.getInstance();
            sUnsafeClass = Class.forName("sun.misc.Unsafe");
            Field unsafeField = sUnsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            sUnsafe = unsafeField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initAndroidUnsafe() {
        try {
            sUnsafeClass = Class.forName("sun.misc.Unsafe");
            if (Build.VERSION.SDK_INT >= KITKAT) {
                Field theUnsafeInstance = sUnsafeClass.getDeclaredField("theUnsafe");
                theUnsafeInstance.setAccessible(true);
                sUnsafe = theUnsafeInstance.get(null);
            } else {
                Class aqsClazz = Class.forName("java.util.concurrent.locks.AbstractQueuedSynchronizer");
                Field theUnsafeInstance = aqsClazz.getDeclaredField("unsafe");
                theUnsafeInstance.setAccessible(true);
                sUnsafe = theUnsafeInstance.get(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object allocateInstance(Class<?> params) {
        Method method = sUtils.getMethod(sUnsafeClass, "allocateInstance", Class.class);
        return sUtils.invokeMethod(method, sUnsafe, params);
    }

    public static long objectFieldOffset(Field field) {
        Method method = sUtils.getMethod(sUnsafeClass, "objectFieldOffset", Field.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, field);
        return obj == null ? 0 : (Long) obj;
    }

    public static long putLong(Object object, long offset, long newValue) {
        Method method = sUtils.getMethod(sUnsafeClass, "putLong", Object.class, long.class, long.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, object, offset, newValue);
        return obj == null ? 0 : (Long) obj;
    }

    public static long putInt(Object object, long offset, int newValue) {
        Method method = sUtils.getMethod(sUnsafeClass, "putInt", Object.class, long.class, int.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, object, offset, newValue);
        return obj == null ? 0 : (Long) obj;
    }

    public static long putObject(Object object, long offset, Object newValue) {
        Method method = sUtils.getMethod(sUnsafeClass, "putObject", Object.class, long.class, Object.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, object, offset, newValue);
        return obj == null ? 0 : (Long) obj;
    }

    public static int arrayIndexScale(Class clazz) {
        Method method = sUtils.getMethod(sUnsafeClass, "arrayIndexScale", Class.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, clazz);
        return obj == null ? 0 : (Integer) obj;
    }

    public static int arrayBaseOffset(Class clazz) {
        Method method = sUtils.getMethod(sUnsafeClass, "arrayBaseOffset", Class.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, clazz);
        return obj == null ? 0 : (Integer) obj;
    }


    //------------------------CAS底层实现,原子性操作---------------------------s

    /**
     * @param object    要操作的对象
     * @param offset    偏移量
     * @param expectInt 期望值
     * @param modifyInt 修改值
     * @return
     */
    public static boolean compareAndSwapInt(Object object, long offset, int expectInt, int modifyInt) {
        Method method = sUtils.getMethod(sUnsafeClass, "compareAndSwapInt", Object.class, long.class, int.class, int.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, object, offset, expectInt, modifyInt);
        return obj != null && (boolean) obj;
    }

    public static boolean compareAndSwapLong(Object object, long offset, long expectLong, long modifyLong) {
        Method method = sUtils.getMethod(sUnsafeClass, "compareAndSwapLong", Object.class, long.class, long.class, long.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, object, offset, expectLong, modifyLong);
        return obj != null && (boolean) obj;
    }

    public static boolean compareAndSwapObject(Object object, long offset, Object expectObj, Object modifyObj) {
        Method method = sUtils.getMethod(sUnsafeClass, "compareAndSwapObject", Object.class, long.class, Object.class, Object.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, object, offset, expectObj, modifyObj);
        return obj != null && (boolean) obj;
    }
    //------------------------CAS底层实现,原子性操作---------------------------e
}