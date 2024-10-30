package com.oneday.network

/**
 * 如果你不指定任何可见度修饰符, 默认会使用 public, 其含义是, 你声明的东西在任何位置都可以访问;
 * 如果你将声明的东西标记为 private, 那么它将只在同一个源代码文件内可以访问;
 * 如果标记为 internal, 那么它将在同一个模块(module)内的任何位置都可以访问;
 * 对于顶级(top-level)声明, protected 修饰符是无效的.
 */
/**
 * Desc: http网络请求常量
 *
 * @author OneD
 * @version 1.0
 * @since 2022/4/14 9:58
 */

const val CONTENT_TYPE_JSON = "application/json; charset=utf-8"
