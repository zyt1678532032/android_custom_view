package com.example.customview.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.customview.R
import com.example.customview.databinding.TimeLineLayoutBinding
import com.example.customview.utils.ImageUtil
import com.example.customview.utils.dp

/**
 * 使用XML布局文件进行自定义View的构建
 */
class TimeLineView2(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private val _bind: TimeLineLayoutBinding

    init {
        _bind = TimeLineLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        circleImage()
    }

    // 图像裁剪 -- ImageView 显示为圆形图像
    private fun circleImage() {
        // 获取原图像的bitmap
        val bitmap =
            ResourcesCompat.getDrawable(resources, R.drawable.icon_avatar_deposit, null)?.toBitmap()

        // 创建圆形的bitmap
        val circleBitmap =
            Bitmap.createBitmap(bitmap!!.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(circleBitmap)
        val path = Path()
        path.addCircle(
            (bitmap.width / 2).toFloat(),
            (bitmap.height / 2).toFloat(),
            (Math.min(bitmap.width, bitmap.height) / 2).toFloat(),
            Path.Direction.CCW
        )
        canvas.clipPath(path)
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        // 设置bitmap
        _bind.image.setImageBitmap(circleBitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i("TimeLineView2", "-------")
    }


}