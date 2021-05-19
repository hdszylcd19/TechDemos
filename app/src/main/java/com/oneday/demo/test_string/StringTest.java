package com.oneday.demo.test_string;

/**
 * Desc:
 *
 * @author JiLin
 * @version 1.0
 * @since 2021/5/18 9:14
 */
/*
 * 下面代码在Java（jdk8）最终会产生几个String对象：（）
 * String a = "没人";
 * String b = "比我";
 * String c = "更懂";
 * String d = "Java";
 * String s = a + b + c + d;
 *
 * A. 5
 * B. 6
 * C. 8
 * D. 7
 *
 * 该题的正确答案为：A
 *
 * 测试代码编译.class文件如下：
 *
 *  public static void main(String[] args) {
        String a = "没人";
        String b = "比我";
        String c = "更懂";
        String d = "Java";
        (new StringBuilder()).append(a).append(b).append(c).append(d).toString();
    }
 *
 * 通过上方class文件，我们发现在赋值s变量时，在jdk8中是使用StringBuilder来拼接的，只有在最后调用toString()方法时，
 * 才创建String对象。所以，结果为A，最终生成5个String对象。
 * */
public class StringTest {

    public static void main(String[] args) {
        String a = "没人";
        String b = "比我";
        String c = "更懂";
        String d = "Java";
        String s = a + b + c + d;
    }
}
