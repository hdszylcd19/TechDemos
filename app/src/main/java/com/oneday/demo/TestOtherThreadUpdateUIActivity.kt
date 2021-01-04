package com.oneday.demo

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test_other_thread_update_u_i.*
import kotlin.concurrent.thread

/**
 * 测试子线程更新UI相关操作
 * 参考链接：
 * https://www.jianshu.com/p/1b2ccd3e1f1f
 * https://mp.weixin.qq.com/s/B5zIMIR1rPT8euTK-spnbg
 */
class TestOtherThreadUpdateUIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_other_thread_update_u_i)

        /*示例1：下面这段代码在子线程中直接更新UI，并不会报错*/
        thread {
            tv?.text = "子线程修改UI成功"
        }

        /*示例2：下面这段代码在子线程中先睡眠一会儿，再更新UI，就会报错*/
//        thread {
//            SystemClock.sleep(3000)
//            tv?.text = "子线程修改UI成功"
//        }

        /* 总结：通过查看源码发现，子线程不能更新UI的报错信息是在ViewRootImpl中的checkThread()方法中抛出的。
         * 当调用TextView的setText()方法时，触发了requestLayout()方法，请求重新测量布局；但是此时mParent还没有初始化，
         * 所以并没有触发ViewRootImpl中requestLayout()方法内部的checkThread()逻辑，因此示例1并不会报错。
         * 为什么示例2就报错了呢？因为，在子线程休眠了一段时间之后，mParent已经初始化完毕了，从这个时候开始就不能再在
         * 子线程更新UI了。
         *
         * 那么，mParent是在什么时候初始化的呢？
         * 简单来说，初始化流程如下：ActivityThread中handleResumeActivity() -> (WindowManagerImpl)wm.addView() ->
         * (WindowManagerGlobal)mGlobal.addView() -> (ViewRootImpl)root.setView() -> (DecorView)view.assignParent()
         * */
    }
}