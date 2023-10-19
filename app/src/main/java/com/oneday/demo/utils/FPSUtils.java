package com.oneday.demo.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.Choreographer;

/**
 * Desc:帧率工具类
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/4/17 0017 16:17
 */
public class FPSUtils {
    private static long mStartFrameTime = 0;
    private static int mFrameCount = 0;

    /**
     * 单次计算FPS时的时间间隔：160毫秒
     */
    private static final long MONITOR_INTERVAL = 160L;
    /**
     * 单次计算FPS时的时间间隔：160,000,000纳秒
     */
    private static final long MONITOR_INTERVAL_NANOS = MONITOR_INTERVAL * 1000L * 1000L;

    /**
     * 设置计算fps的单位时间间隔1000ms,即fps/s
     */
    private static final long MAX_INTERVAL = 1000L;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void logFPS() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (mStartFrameTime == 0) {
                    mStartFrameTime = frameTimeNanos;
                }
                long interval = frameTimeNanos - mStartFrameTime;
                if (interval > MONITOR_INTERVAL_NANOS) {
                    double fps = (((double) (mFrameCount * 1000L * 1000L)) / interval) * MAX_INTERVAL;
                    // log输出fps
                    Log.i("FPSUtils", "当前实时fps值为： " + fps);
                    mFrameCount = 0;
                    mStartFrameTime = 0;
                } else {
                    ++mFrameCount;
                }

                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }
}