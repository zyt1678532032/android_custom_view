package com.zyt.widgets.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * 一个可以跟随手指移动的View，或者理解为可拖拽的View
 */
class FollowFingerView(context: Context, attributeSet: AttributeSet? = null) :
    View(context, attributeSet) {

    private var mLastX: Float = 200f
    private var mLastY: Float = 200f

    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mLastX = event.x
                    mLastY = event.y
                }

                MotionEvent.ACTION_MOVE -> {
                    val offsetX = event.x - mLastX
                    val offsetY = event.y - mLastY

                    // 使用ViewPropertyAnimator平滑移动View
                    animate().translationXBy(offsetX)
                        .translationYBy(offsetY)
                        .setDuration(0)
                        .start()

                    mLastX = event.x
                    mLastY = event.y
                }

                MotionEvent.ACTION_UP -> {}
                else -> {}
            }

            // //layout方法实现
            // when (event.action) {
            //     MotionEvent.ACTION_DOWN -> {}
            //     MotionEvent.ACTION_MOVE -> {
            //         //计算偏移量
            //         val offsetX = (event.x - mLastX).toInt()
            //         val offsetY = (event.y - mLastY).toInt()
            //         //重新布局
            //         layout(
            //             left + offsetX, top + offsetY,
            //             right + offsetX, bottom + offsetY
            //         )
            //     }
            //
            //     MotionEvent.ACTION_UP -> {}
            //     else -> {}
            // }
            // mLastX = event.x
            // mLastY = event.y
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(mLastX, mLastY, 50f, paint)
    }

}