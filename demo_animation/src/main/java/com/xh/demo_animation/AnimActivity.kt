package com.xh.demo_animation

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnticipateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_anim.*

class AnimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)

        carIv?.setOnClickListener {
            Toast.makeText(this, "我在这儿呢", Toast.LENGTH_LONG).show()
        }
    }

    fun translate(view: View) {
        // 创建位移动画对象，从一个点移动到另一个点
        // Animation a = new TranslateAnimation(0, 200, 0, 200);
        // 创建位移动画对象,相对于谁，默认是绝对的，可以相对于自己或者父控件
        val a = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 2f
        )

        // 设置动画持续时间
        a.duration = 2000
        // 设置动画的重复次数
        a.repeatCount = 2
        // 设置重复模式，偶数次为反转模式
        a.repeatMode = Animation.REVERSE
        // 保持动画执行后的状态
        a.fillAfter = true
        // 设置A_B的状态 加速度过去
        a.interpolator = AnticipateInterpolator()
        // 执行动画
        carIv?.startAnimation(a)

//         拿到一个动画，通过XML文件转化而来
//
//         // 创建位移动画对象，从一个点移动到另一个点
//         // Animation a = new TranslateAnimation(0, 200, 0, 200);
//         // 创建位移动画对象,相对于谁，默认是绝对的，可以相对于自己或者父控件
//         Animation a = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
//         Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF, 0f,
//         Animation.RELATIVE_TO_SELF, 2f);
//
//         // 设置动画持续时间
//         a.setDuration(2000);
//         // 设置动画的重复次数
//         a.setRepeatCount(10);
//         // 设置重复模式，偶数次为反转模式
//         a.setRepeatMode(Animation.REVERSE);
//         // 保持动画执行后的状态
//         a.setFillAfter(true);
//         // 设置A_B的状态 加速度过去
//         a.setInterpolator(new AnticipateInterpolator());
//         // 执行动画
//         iv_car.startAnimation(a);
    }
}
