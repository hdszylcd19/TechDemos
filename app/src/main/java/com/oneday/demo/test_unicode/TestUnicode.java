package com.oneday.demo.test_unicode;

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/7/9 0009 17:45
 */
class TestUnicode {
    public static void main(String[] args) {
        /*
         乍一看，代码都被注释掉了，当然不会输出任何东西，然而，它还是输出每个程序员都倍感亲切的Hello World，
         这是因为，unicode解码发生在代码编译之前，编译器将u样式的代码进行文本转义，即使是注释也是这样，
         然后u000a被解析为换行符，所以println代码得以正常执行。

         通过查阅https://www.unicode.org/charts/PDF/U0000.pdf可以发现：
         在unicode编码中，'\u000a'代表换行符（LINE FEED）；'\u000d'代表回车键（CARRIAGE RETURN）。
         */
        // \u000a System.out.println("Hello World!");
        String str = new String("\\u000a\\u000d");
        System.out.println(str);
    }
}
