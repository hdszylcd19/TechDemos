package com.oneday.demo_orientation

import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import androidx.appcompat.app.AppCompatActivity

class OrientationActivity : AppCompatActivity() {
    private var mOrientationListener: OrientationEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orientation)

        mOrientationListener =
            object : OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
                override fun onOrientationChanged(orientation: Int) {
                    if (orientation == ORIENTATION_UNKNOWN) {
                        //手机平放时，检测不到有效的角度
                        Log.i(TAG, "当前手机平放，检测不到有效的角度！")
                        return
                    }

                    //只检测是否有四个角度的改变
                    val validOrientation: Int
                    if (orientation > 350 || orientation < 10) { //0度
                        validOrientation = 0;
                    } else if (orientation > 80 && orientation < 100) { //90度
                        validOrientation = 90;
                    } else if (orientation > 170 && orientation < 190) { //180度
                        validOrientation = 180;
                    } else if (orientation > 260 && orientation < 280) { //270度
                        validOrientation = 270;
                    } else {
                        return;
                    }

                    Log.i(TAG, "Orientation changed to $validOrientation")
                }
            }
    }

    override fun onStart() {
        super.onStart()
        mOrientationListener?.enable()
        Log.i(TAG, "enable()")
    }

    override fun onStop() {
        super.onStop()
        mOrientationListener?.disable()
        Log.i(TAG, "disable()")
    }
}

const val TAG = "OrientationActivity"
