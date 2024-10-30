package com.oneday.demo.kt

/**
 * Desc: 测试Kotlin代码初始化顺序
 *
 * @author OneD
 * @version 1.0
 * @since 2022/5/5 10:35
 */
class TestKtCodeSequence {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Child(Any())
        }

        open class Parent {
            private var parentFlag = 2

            constructor(any: Any) {
                println("Parent constructor")
                this.init()
            }

            init {
                println("Parent init")
            }

            protected open fun init() {

            }
        }

        class Child : Parent {
            private var flag = 1

            constructor(any: Any) : super(any) {
                println("Child constructor")
            }

            init {
                println("Child init")
                println("Child init flag = $flag")
            }

            override fun init() {
                super.init()
                println("Child override init flag = $flag")
            }
        }
    }

}