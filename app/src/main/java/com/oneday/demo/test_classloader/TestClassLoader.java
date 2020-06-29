package com.oneday.demo.test_classloader;

import java.lang.reflect.Method;
import java.net.URL;

/**
 * Desc:测试类加载器
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/6/29 0029 14:56
 */
class TestClassLoader {
    public static void main(String[] args) {
        /*
        该方式不能直接获取，改用反射方式获取
        URL[] urLs = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL element : urLs) {
            System.out.println(element.toExternalForm());
        }
        */

        //1. 获取引导类加载器能加载哪些路径下的jar文件:
        try {
            Class<?> launcherClass = Class.forName("sun.misc.Launcher");
            Method getBootstrapClassPathMethod = launcherClass.getDeclaredMethod("getBootstrapClassPath");
            Object URLClassPathObj = getBootstrapClassPathMethod.invoke(null);

            Method getURLsMethod = URLClassPathObj.getClass().getDeclaredMethod("getURLs");
            URL[] urLs = (URL[]) getURLsMethod.invoke(URLClassPathObj);
            System.out.println("引导类加载器加载该路径下的jar文件：");
            for (URL element : urLs) {
                System.out.println(element.toExternalForm());
            }
            printSeparator();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2. 获取扩展类加载器所加载的jar文件:
        String extDirs = System.getProperty("java.ext.dirs");//获取一个或多个扩展目录的路径
        if (extDirs != null) {
            System.out.println("扩展类加载器所加载的jar文件：");
            for (String path : extDirs.split(";")) {
                System.out.println(path);
            }
        }
        printSeparator();

        //3. 系统类加载器
        ClassLoader classLoader = TestClassLoader.class.getClassLoader();
        System.out.println("系统类加载器：");
        System.out.println(classLoader);
    }

    private static void printSeparator() {
        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.println("--------------------------------------------------------");
        System.out.println();
    }
}
