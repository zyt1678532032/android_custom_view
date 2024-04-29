package com.zyt.customview

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.LAYER_TYPE_HARDWARE
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customview.databinding.ActivityMainBinding
import com.zyt.customview.adapter.MySlidingAdapter
import com.zyt.widgets.views.AvatarView
import com.zyt.widgets.views.FollowFingerView
import com.zyt.widgets.views.TimeLineView2
import kotlin.random.Random
import kotlin.random.nextInt


class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        // grayWindow()

        _binding.avatarView.run {
            setLabelType(AvatarView.LabelType.TYPE_OWNER)
            isShowBorder = true
        }
        _binding.avatarView2.run {
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

        _binding.rectView.size = 200
        _binding.rectView.color = Color.BLUE

        ValueAnimator.ofFloat(0f, 100f).apply {
            duration = 1000
            addUpdateListener { updatedAnimation ->
                _binding.btn.translationX = updatedAnimation.animatedValue as Float
            }
            start()
        }

        crossFade()

        // _binding.avatarView.apply {
        //     animate().scaleX(0.5f).scaleY(0.5f)
        // }

        _binding.avatarView2.apply {
            // 旋转
            ObjectAnimator.ofFloat(this, "rotationY", 0f, 360f).also {
                it.duration = 1000
                it.start()
            }

            setOnClickListener {
                // 缩放
                ObjectAnimator.ofFloat(this, "scaleX", "scaleY", Path().also {
                    it.lineTo(1f, 1f)
                }).apply {
                    interpolator = AccelerateInterpolator()
                    duration = 1000
                    start()
                }
            }
        }

        _binding.btn.setOnClickListener {
            val x = Random.nextInt(0..9)
            _binding.rectView.text = "00:4$x / 10:24"
        }
    }

    private fun crossFade() {
        _binding.avatarView.apply {
            alpha = 0f
            isVisible = true

            animate().alpha(1f).setDuration(1000).setListener(null)
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