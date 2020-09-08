package com.oneday.livedatademo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_livedata.*

class LiveDataActivity : AppCompatActivity() {
    // Obtain ViewModel
    private val mViewModel: LiveDataViewModel by viewModels { LiveDataVMFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.activity_livedata)

        val timeObserver = Observer<Long> { currentMillis ->
            // 更新时间毫秒值显示
            time.text = currentMillis.toString()
            Log.i(TAG, "更新时间毫秒值")
        }
        mViewModel.currentTime.observe(this, timeObserver)

        val timeTransformedObserver = Observer<String> { newTime ->
            // 更新时间显示
            time_transformed.text = newTime
            Log.i(TAG, "更新时间")
        }
        mViewModel.currentTimeTransformed.observe(this, timeTransformedObserver)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState()")
    }
}
