package com.oneday.demo.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.oneday.demo.R
import kotlinx.android.synthetic.main.activity_test_bitmap.*

/**
 * 测试不同资源目录下，同一张bitmap资源图片占用内存大小
 *
 * 参考资料：
 * https://mp.weixin.qq.com/s/GkPrmlNm8p3fkeh4vo3Htg
 * https://www.jianshu.com/p/4a0b070d56af
 */
class TestBitmapActivity : AppCompatActivity() {
    companion object {
        val TAG: String = TestBitmapActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_bitmap)

        /*
            Bitmap内存占用，遵循如下公式：
            float scale = (float)targetDensity/density;
            int scaledWidth = int(图片原始宽 * scale + 0.5f)
            int scaledHeight = int(图片原始高 * scale + 0.5f)
            int byteCount = scaleWidth * scaledHeight * 每个像素占用几个字节; //bitmap实际内存占用，单位：byte

            其中，density是decodingBitmap的density，这个值跟这张图片的放置的目录有关（比如：hdpi是240，xxhdpi是480）；
            targetDensity实际上是我们加载图片的目标density，就是DisplayMetrics的densityDpi。

            例如，我们要计算在三星P200平板上（targetDensity：320），同一个图片资源，放在不同目录下分别占用多大内存：
            xhdpi资源目录（density：320）（原始目录）：
            float scale = (float)320/320 = 1.0f
            int scaledWidth = int(144 * 1.0f + 0.5f) = int(144.5f) = 144
            int scaledHeight = int(60 * 1.0f + 0.5f) = int(60.5f) = 60
            int byteCount = 144 * 60 * 4 = 34560

            mdpi资源目录（density：160）：
            float scale = (float)320/160 = 2.0f;
            int scaledWidth = int(144 * 2.0f + 0.5f) = int(288.5f) = 288
            int scaledHeight = int(60 * 2.0f + 0.5f) = int(120.5f) = 120
            int byteCount = 288 * 120 * 4 = 138240

            hdpi资源目录（density：240）：
            float scale = (float)320/240 = 1.333333333333333f; //除不尽
            int scaledWidth = int(144 * 1.333333333333333f + 0.5f) = int(192.5f) = 192
            int scaledHeight = int(60 * 1.333333333333333f + 0.5f) = int(80.5f) = 80
            int byteCount = 192 * 80 * 4 = 61440

            xxhdpi资源目录（density：480）：
            float scale = (float)320/480 = 0.6666666666666667f; //除不尽
            int scaledWidth = int(144 * 0.6666666666666667f + 0.5f) = int(96.5f) = 96
            int scaledHeight = int(60 * 0.6666666666666667f + 0.5f) = int(40.5f) = 40
            int byteCount = 96 * 40 * 4 = 15360

            xxxhdpi资源目录（density：640）：
            float scale = (float)320/640 = 0.5f;
            int scaledWidth = int(144 * 0.5f + 0.5f) = int(72.5f) = 72
            int scaledHeight = int(60 * 0.5f + 0.5f) = int(30.5f) = 30
            int byteCount = 72 * 30 * 4 = 8640
         */

        val targetDensity = resources.displayMetrics.densityDpi

        val sb = StringBuilder()
        sb.append("Bitmap内存占用，遵循如下公式：\n")
        sb.append("float scale = (float)targetDensity/density;\n")
        sb.append("int scaledWidth = int(图片原始宽 * scale + 0.5f)\n")
        sb.append("int scaledHeight = int(图片原始高 * scale + 0.5f)\n")
        sb.append("int byteCount = scaleWidth * scaledHeight * 每个像素占用几个字节; //单位：byte\n\n")
        sb.append(
            "其中，density是decodingBitmap的density，这个值跟这张图片的放置的目录有关（比如：hdpi是240，xxhdpi是480）；" +
                    "targetDensity实际上是我们加载图片的目标density，就是DisplayMetrics的densityDpi。\n"
        )
        sb.append("\n\n")

        sb.append("targetDensity = $targetDensity\n\n")

        val xhdpiBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_src_xhdpi)
        logBitmapSize("xhdpi", xhdpiBitmap)
        sb.append("xhdpi资源目录（density：320）（原始目录）：\n")
        sb.append("float scale = (float)$targetDensity/320 = 1.0f;\n")
        sb.append("int scaledWidth = int (144 * 1.0f + 0.5f) = int(144.5f) = 144;\n")
        sb.append("int scaledHeight = int (60 * 1.0f + 0.5f) = int(60.5f) = 60;\n")
        sb.append("int byteCount = 144 * 60 * 4 = 34560;\n")
        appendByteCount(sb, xhdpiBitmap)
        sb.append("\n\n")

        val mdpiBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_src_mdpi)
        logBitmapSize("mdpi", mdpiBitmap)
        sb.append("mdpi资源目录（density：160）：\n")
        sb.append("float scale = (float)$targetDensity/160 = 2.0f;\n")
        sb.append("int scaledWidth = int(144 * 2.0f + 0.5f) = int(288.5f) = 288;\n")
        sb.append("int scaledHeight = int (60 * 2.0f + 0.5f) = int(120.5f) = 120;\n")
        sb.append("int byteCount = 288 * 120 * 4 = 138240;\n")
        appendByteCount(sb, mdpiBitmap)
        sb.append("\n\n")

        val hdpiBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_src_hdpi)
        logBitmapSize("hdpi", hdpiBitmap)
        sb.append("hdpi资源目录（density：240）：\n")
        sb.append("float scale = (float)$targetDensity/240 = 1.333333333333333f; //除不尽\n")
        sb.append("int scaledWidth = int(144 * 1.333333333333333f + 0.5f) = int(192.5f) = 192;\n")
        sb.append("int scaledHeight = int(60 * 1.333333333333333f + 0.5f) = int(80.5f) = 80;\n")
        sb.append("int byteCount = 192 * 80 * 4 = 61440;\n")
        appendByteCount(sb, hdpiBitmap)
        sb.append("\n\n")

        val xxhdpiBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_src_xxhdpi)
        logBitmapSize("xxhdpi", xxhdpiBitmap)
        sb.append("xxhdpi资源目录（density：480）：\n")
        sb.append("float scale = (float)$targetDensity/480 = 0.6666666666666667f; //除不尽\n")
        sb.append("int scaledWidth = int(144 * 0.6666666666666667f + 0.5f) = int(96.5f) = 96;\n")
        sb.append("int scaledHeight = int(60 * 0.6666666666666667f + 0.5f) = int(40.5f) = 40;\n")
        sb.append("int byteCount = 96 * 40 * 4 = 15360;\n")
        appendByteCount(sb, xxhdpiBitmap)
        sb.append("\n\n")

        val xxxhdpiBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_src_xxxhdpi)
        logBitmapSize("xxxhdpi", xxxhdpiBitmap)
        sb.append("xxxhdpi资源目录（density：640）：\n")
        sb.append("float scale = (float)$targetDensity/640 = 0.5f;\n")
        sb.append("int scaledWidth = int(144 * 0.5f + 0.5f) = int(72.5f) = 72\n")
        sb.append("int scaledHeight = int(60 * 0.5f + 0.5f) = int(30.5f) = 30\n")
        sb.append("int byteCount = 72 * 30 * 4 = 8640;\n")
        appendByteCount(sb, xxxhdpiBitmap)

        tv.text = sb.toString()
    }

    private fun logBitmapSize(tag: String, bitmap: Bitmap) {
        Log.i("${TAG}_$tag", "bitmap所占内存大小：${bitmap.byteCount}，单位：byte")
    }

    private fun appendByteCount(sb: StringBuilder, bitmap: Bitmap) {
        sb.append("Bitmap.getByteCount() = ${bitmap.byteCount}\n")
    }
}