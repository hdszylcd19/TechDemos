package com.oneday.demo.unsafe;

import java.lang.reflect.Method;

public class ReflectUtils {

    private static volatile ReflectUtils sInstance;

    private ReflectUtils() {
    }

    public static ReflectUtils getInstance() {
        if (sInstance == null) {
            synchronized (ReflectUtils.class) {
                // double check
                if (sInstance == null) {
                    sInstance = new ReflectUtils();
                }
            }
        }
        return sInstance;
    }

    public Method getMethod(Class<?> clazz, String methodName, Class<?>... values) {
        Method method = null;
        try {
            if (values != null) {
                method = clazz.getDeclaredMethod(methodName, values);
            } else {
                method = clazz.getDeclaredMethod(methodName);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    public Object invokeStaticMethod(Method method, Object... values) {
        return invokeMethod(method, null, values);
    }

    public Object invokeMethod(Method method, Object classValue, Object... values) {
        if (method != null) {
            try {
                return method.invoke(classValue, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}