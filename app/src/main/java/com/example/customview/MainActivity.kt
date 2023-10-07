package com.example.customview

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.LAYER_TYPE_HARDWARE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ben_custom_view.views.AvatarView
import com.example.ben_custom_view.views.FollowFingerView
import com.example.ben_custom_view.views.TimeLineView2
import com.example.customview.adapter.MySlidingAdapter
import com.example.customview.databinding.ActivityMainBinding
import com.example.customview.databinding.ArticleHolderLayoutBinding


class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        // grayWindow()

        _binding.avatarView.run {
            setLabelType(AvatarView.LabelType.TYPE_OWNER)
            avatarDrawable =
                ResourcesCompat.getDrawable(resources, R.drawable.cat, null)
            isShowBorder = true
        }
        setAvatarGray()

        val moveViewByTouch = FollowFingerView(this@MainActivity)
        _binding.container.addView(moveViewByTouch)
        _binding.container.addView(TimeLineView2(this))

        val slidingAdapter = MySlidingAdapter().apply {
            data = listOf("2", "3", "5")
        }
        _binding.recycleView.run {
            adapter = slidingAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setAvatarGray() {
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val colorMatrixColorFilter = ColorMatrixColorFilter(colorMatrix)
        val paint = Paint()
        paint.colorFilter = colorMatrixColorFilter
        // _binding.avatarView.setLayerType(LAYER_TYPE_HARDWARE, paint)
    }

    // 页面全局置灰
    private fun grayWindow() {
        val grayPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        ColorMatrix().apply {
            setSaturation(0F)
            val colorFilter = ColorMatrixColorFilter(this)
            grayPaint.colorFilter = colorFilter
        }
        this.window.decorView.setLayerType(LAYER_TYPE_HARDWARE, grayPaint)
    }


}