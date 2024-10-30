package com.oneday.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {
    private ReflectUtils() {
    }

    public static <T> T newInstance(Class<T> ofClass, Class<?>[] argTypes, Object[] args) throws Throwable {
        Constructor<T> con = ofClass.getConstructor(argTypes);
        return con.newInstance(args);
    }

    public static Object invoke(Object obj, String methodName) throws Throwable {
        Method method = obj.getClass().getMethod(methodName, (Class[]) null);
        return method.invoke(obj, (Object[]) null);
    }

    public static Object invokeStatic(Object obj, String methodName) throws Throwable {
        Method method = ((Class) obj).getMethod(methodName, (Class[]) null);
        return method.invoke(obj, (Object[]) null);
    }

    public static Object invoke(Object obj, String methodName, Class<?> argType, Object arg) throws Throwable {
        Method method = obj.getClass().getMethod(methodName, argType);
        return method.invoke(obj, arg);
    }

    public static Object invoke(Object obj, String methodName, Class<?> argType1, Object arg1, Class<?> argType2, Object arg2)
            throws Exception {
        Method method = obj.getClass().getMethod(methodName, argType1, argType2);
        return method.invoke(obj, arg1, arg2);
    }

    public static Object getField(@NotNull Object obj, String fieldName) throws Exception {
        Field field = getFieldInner(obj.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(obj);
        }
        throw new NoSuchFieldException("className = " + obj.getClass() + " fieldName = " + fieldName);
    }

    public static long getFieldLong(@NotNull Object obj, String fieldName) throws Exception {
        Field field = getFieldInner(obj.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.getLong(obj);
        }
        throw new NoSuchFieldException("className = " + obj.getClass() + " fieldName = " + fieldName);
    }

    public static void setField(@NotNull Object obj, String fieldName, Object fieldValue) throws Exception {
        Field field = getFieldInner(obj.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            field.set(obj, fieldValue);
            return;
        }
        throw new NoSuchFieldException("className = " + obj.getClass() + " fieldName = " + fieldName);
    }

    private static Field getFieldInner(Class<?> objClass, String fieldName) {
        Field field = null;
        try {
            field = objClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ignore) {
        }

        if (field == null) {
            Class<?> superclass = objClass.getSuperclass();
            if (superclass != null) {
                field = getFieldInner(superclass, fieldName);
            }
        }
        return field;
    }

    public static boolean respondsTo(Object o, String methodName) {
        Method[] methods = o.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }
}